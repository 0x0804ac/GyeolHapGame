package com.nokhoon.gyeolhapgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameManager {
	private static GameManager instance = new GameManager();
	public static final Comparator<int[]> INT_ARRAY_COMPARATOR = (arr1, arr2) -> {
		if(arr1 == null || arr2 == null) throw new NullPointerException("this comparator does not permit null arguments");
		if(arr1.length != arr2.length) throw new IllegalArgumentException("arrays have different length");
		if(arr1.equals(arr2)) return 0;
		for(int i = 0; i < arr1.length; ++i) {
			if(arr1[i] != arr2[i]) return arr1[i] - arr2[i];
		}
		return 0;
	};
	public static final Color GREEN_TEXT;
	public static final Color RED_TEXT;
	
	static {
		GREEN_TEXT = new Color(0, 160, 0);
		RED_TEXT = new Color(224, 0, 0);
	}
	
	private ArrayList<int[]> allHap, foundHap;
	private BitSet selectedTiles;
	private MainPanel mainPanel;
	private JLabel textLabel;
	private TilePanel tilesPanel;
	private OptionPanel optionsPanel;
	private GameListener listener;
	
	private GameManager() {
		allHap = new ArrayList<int[]>(12);
		foundHap = new ArrayList<int[]>(12);
		selectedTiles = new BitSet(9);
		mainPanel = new MainPanel();
		listener = new GameListener();
		textLabel = mainPanel.getTextLabel();
		tilesPanel = mainPanel.getTilesPanel();
		optionsPanel = mainPanel.getOptionsPanel();
	}
	public static GameManager getInstance() { return instance; }
	
	public MainPanel getMainPanel() { return mainPanel; }
	public GameListener getListener() { return listener; }
	
	public int[] getSelectedTiles() {
		ArrayList<Integer> list = new ArrayList<Integer>(9);
		for(int i = 0; i < 9; ++i) {
			if(selectedTiles.get(i)) list.add(i);
		}
		int size = list.size();
		int[] arr = new int[size];
		for(int i = 0; i < size; ++i) arr[i] = list.get(i);
		return arr;
	}
	
	public void startNewGame() {
		allHap.clear();
		foundHap.clear();
		tilesPanel.generateTiles();
		selectedTiles.clear();
		optionsPanel.setGyeolButtonEnabled(true);
		optionsPanel.setHapButtonEnabled(false);
		optionsPanel.setNewGameButtonText(OptionPanel.GIVE_UP);
		for(int i = 0; i < 7; ++i) {
			for(int j = i + 1; j < 8; ++j) {
				for(int k = j + 1; k < 9; ++k) {
					if(TileButton.isHap(tilesPanel.getButton(i), tilesPanel.getButton(j), tilesPanel.getButton(k))) allHap.add(new int[] {i, j, k});
				}
			}
		}
		textLabel.setText(MainPanel.TITLE_TEXT);
		textLabel.setForeground(Color.BLACK);
		mainPanel.getTableModel().clear();
		mainPanel.getFoundHapTable().repaint();
	}
	
	public void showAnswer() {
		int total = allHap.size();
		String message = Integer.toString(total) + "개의 합이 있습니다.";
		String blank = "     ";
		String line = "\n";
		for(int i = 0; i < total; ++i) {
			int[] hap = allHap.get(i);
			if(i % 4 == 0) message += line;
			else message += blank;
			message += (hap[0] + 1) + " " + (hap[1] + 1) + " " + (hap[2] + 1);
			if(foundHap.indexOf(hap) == -1) message += "(미발견)";
		}
		JOptionPane.showMessageDialog(mainPanel, message, "정답 확인", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void resetSelection(boolean click) {
		for(TileButton button : tilesPanel.getButtons()) {
			if(button.isSelected()) {
				if(click) button.doClick();
				else button.setSelected(false);
			}
		}
	}
	
	public void guessGyeol() {
		int total = allHap.size();
		if(total == foundHap.size()) {
			resetSelection(false);
			optionsPanel.setGyeolButtonEnabled(false);
			optionsPanel.setHapButtonEnabled(false);
			optionsPanel.setNewGameButtonText(OptionPanel.NEW_GAME);
			textLabel.setForeground(GREEN_TEXT);
			textLabel.setText("결! (합 " + total + "개)");
		}
		else {
			textLabel.setForeground(RED_TEXT);
			textLabel.setText("결이 아닙니다.");
		}
	}
	
	public void guessHap() {
		int[] guess = getSelectedTiles();
		for(int[] hap : allHap) {
			if(INT_ARRAY_COMPARATOR.compare(hap, guess) == 0) {
				for(int[] found : foundHap) {
					if(INT_ARRAY_COMPARATOR.compare(found, hap) == 0) {
						textLabel.setForeground(Color.BLACK);
						textLabel.setText("이미 찾았습니다.");
						resetSelection(true);
						return;
					}
				}
				foundHap.add(hap);
				String display = " " + (guess[0] + 1) + " " + (guess[1] + 1) + " " + (guess[2] + 1);
				textLabel.setForeground(GREEN_TEXT);
				textLabel.setText("합!" + display);
				resetSelection(true);
				mainPanel.getTableModel().setValueAt(display + " ", foundHap.size() - 1);
				mainPanel.getFoundHapTable().repaint();
				return;
			}
		}
		textLabel.setForeground(RED_TEXT);
		textLabel.setText("합이 아닙니다.");
	}
	
	private class GameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if(optionsPanel.gyeolButtonIsEnabled()) {
				for(int i = 0; i < 9; ++i) {
					TileButton button = tilesPanel.getButton(i);
					if(source == button) {
						selectedTiles.set(i, button.isSelected());
						optionsPanel.setHapButtonEnabled(selectedTiles.cardinality() == 3);
						return;
					}
				}
			}
		}
	}
}
