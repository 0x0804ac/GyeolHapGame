package com.nokhoon.gyeolhapgame;

import javax.swing.JFrame;

public class Driver {
	public static void main(String[] args) {
		JFrame frame = new JFrame("결합게임");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		GameManager game = GameManager.getInstance();
		game.startNewGame();
		frame.getContentPane().add(game.getMainPanel());
		
		frame.pack();
		frame.setVisible(true);
	}
}
