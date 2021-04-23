package com.nokhoon.gyeolhapgame;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JPanel;

public class TilePanel extends JPanel {
	private static final long serialVersionUID = 1748844416057105196L;
	private ArrayList<TileButton> buttons;
	
	public TilePanel() {
		super(null);
		setPreferredSize(new Dimension(320, 320));
		buttons = new ArrayList<TileButton>(9);
	}
	
	public ArrayList<TileButton> getButtons() { return buttons; }
	
	public TileButton getButton(int index) {
		if(index < 0 || index >= buttons.size()) return null;
		return buttons.get(index);
	}
	
	public void generateTiles() {
		if(!buttons.isEmpty()) {
			for(TileButton button : buttons) {
				button.setVisible(false);
				button.removeActionListener(button.getActionListeners()[0]);
			}
			buttons.clear();
			removeAll();
		}
		ArrayList<TileButton> list = (ArrayList<TileButton>) TileButton.TILES.clone();
		Random rng = new Random(System.currentTimeMillis() ^ 0x0804ac);
		Collections.shuffle(list, rng);
		for(int i = 0; i < 9; ++i) {
			TileButton btn = list.remove(rng.nextInt(list.size()));
			buttons.add(btn);
			btn.setBounds(100 * (i % 3) + 15, 100 * (i / 3) + 15, 90, 90);
			btn.setSelected(false);
			btn.setVisible(true);
			btn.addActionListener(GameManager.getInstance().getListener());
			add(btn);
		}
	}
}
