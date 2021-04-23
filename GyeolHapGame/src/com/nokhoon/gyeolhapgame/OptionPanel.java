package com.nokhoon.gyeolhapgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class OptionPanel extends JPanel {
	private static final long serialVersionUID = -1363140212037515947L;
	
	private static final Color TEXT_RED = new Color(224, 32, 0);
	private static final Color TEXT_GREEN = new Color(0, 128, 0);
	private static final Color BUTTON_COLOR = new Color(192, 192, 192);
	private static final Font TEXT_FONT = new Font("빙그레체", Font.PLAIN, 20);
	public static final String NEW_GAME = "새 게임";
	public static final String GIVE_UP = "포기";
	public static final String HELP_MESSAGE = "각각의 타일은 배경색, 도형의 모양과 색의 3가지 속성을 가지고 있습니다.\n"
			+ "배경색: 흰색, 회색, 검은색 / 모양: 원, 사각형, 삼각형 / 도형 색: 빨강, 노랑, 파랑\n"
			+ "각각의 속성이 \'모두 같음\' 또는 \'모두 다름\'을 모두 만족하는 타일 3개는 합입니다.\n"
			+ "(예시: 배경색이 모두 다르고, 도형의 색이 모두 다르고, 모양이 모두 사각형)\n"
			+ "주어진 9개의 타일에서 가능한 모든 합을 찾은 상태는 결입니다.";
	public static final String GIVE_UP_MESSAGE = "정답을 확인하시겠습니까?\n"
			+ "모든 합이 공개됩니다.";
	
	private JButton help;
	private JButton gyeol, hap;
	private JButton newGame;
	
	public OptionPanel() {
		super(null);
		setPreferredSize(new Dimension(400, 60));
		
		help = new JButton("게임 방법");
		help.setFont(TEXT_FONT);
		help.setBounds(10, 10, 125, 40);
		help.setBackground(BUTTON_COLOR);
		help.addActionListener(new HelpButtonListener());
		add(help);
		
		gyeol = new JButton("결!");
		gyeol.setFont(TEXT_FONT);
		gyeol.setBounds(140, 10, 65, 40);
		gyeol.setBackground(BUTTON_COLOR);
		gyeol.setForeground(TEXT_GREEN);
		gyeol.addActionListener(new GyeolButtonListener());
		add(gyeol);
		
		hap = new JButton("합!");
		hap.setFont(TEXT_FONT);
		hap.setBounds(210, 10, 65, 40);
		hap.setBackground(BUTTON_COLOR);
		hap.setForeground(TEXT_GREEN);
		hap.addActionListener(new HapButtonListener());
		hap.setEnabled(false);
		add(hap);
		
		newGame = new JButton(GIVE_UP);
		newGame.setFont(TEXT_FONT);
		newGame.setBounds(280, 10, 110, 40);
		newGame.setBackground(BUTTON_COLOR);
		newGame.setForeground(Color.RED);
		newGame.addActionListener(new NewGameButtonListener());
		add(newGame);
	}
	
	public boolean gyeolButtonIsEnabled() { return gyeol.isEnabled(); }
	public void setGyeolButtonEnabled(boolean flag) {
		gyeol.setEnabled(flag);
	}
	public boolean hapButtonIsEnabled() { return hap.isEnabled(); }
	public void setHapButtonEnabled(boolean flag) {
		hap.setEnabled(flag);
	}
	
	public String getNewGameButtonText() { return newGame.getText(); }
	public void setNewGameButtonText(String text) {
		if(text == GIVE_UP) newGame.setText(GIVE_UP);
		else if(text == NEW_GAME) newGame.setText(NEW_GAME);
	}
	
	private class HelpButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(help, HELP_MESSAGE, "게임 방법", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class GyeolButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameManager.getInstance().guessGyeol();
		}
	}
	
	private class HapButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GameManager.getInstance().guessHap();
		}
	}
	
	private class NewGameButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = newGame.getText();
			if(text == GIVE_UP) {
				int result = JOptionPane.showConfirmDialog(newGame, GIVE_UP_MESSAGE, "게임 포기", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				switch(result) {
				case JOptionPane.YES_OPTION -> {
					GameManager.getInstance().resetSelection(false);
					gyeol.setEnabled(false);
					hap.setEnabled(false);
					newGame.setText(NEW_GAME);
					GameManager.getInstance().getMainPanel().getTextLabel().setForeground(TEXT_RED);
					GameManager.getInstance().getMainPanel().getTextLabel().setText("포기했습니다.");
					GameManager.getInstance().showAnswer();
				}
				case JOptionPane.NO_OPTION -> GameManager.getInstance().startNewGame();
				default -> { }
				}
			}
			else if(text == NEW_GAME) {
				GameManager.getInstance().startNewGame();
			}
			else newGame.setText(NEW_GAME);
		}
	}
}
