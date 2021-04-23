package com.nokhoon.gyeolhapgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

public class TileButton extends JToggleButton {
	private static final long serialVersionUID = 3924646492108299749L;
	public static final Color WHITE = Color.WHITE;
	public static final Color GRAY = new Color(128, 128, 128);
	public static final Color BLACK = Color.BLACK;
	public static final Color RED = Color.RED;
	public static final Color YELLOW = Color.YELLOW;
	public static final Color BLUE = Color.BLUE;
	public static final int ID_CIRCLE = 0;
	public static final int ID_SQUARE = 1;
	public static final int ID_TRIANGLE = 2;
	public static final Polygon TRIANGLE = new Polygon(new int[] {15, 45, 75}, new int[] {70, 20, 70}, 3);
	
	private Color background;
	private Color foreground;
	private int shape;
	
	public static final ArrayList<TileButton> TILES;
	
	static {
		TILES = new ArrayList<TileButton>(27);
		for(int i = 0; i < 27; ++i) TILES.add(new TileButton(i / 9, (i % 9) / 3, i % 3));
	}
	
	private TileButton(int backgroundColor, int innerColor, int shape) {
		setPreferredSize(new Dimension(90, 90));
		switch(backgroundColor) {
		case 0 -> background = WHITE;
		case 1 -> background = GRAY;
		case 2 -> background = BLACK;
		default -> throw new IllegalArgumentException("invalid background ID");
		}
		switch(innerColor) {
		case 0 -> foreground = RED;
		case 1 -> foreground = YELLOW;
		case 2 -> foreground = BLUE;
		default -> throw new IllegalArgumentException("invalid foreground ID");
		}
		if(shape < 0 || shape > 2) throw new IllegalArgumentException("invalid shape ID");
		this.shape = shape;
		setBackground(background);
		setBorder(BorderFactory.createLineBorder(background, 5));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(foreground);
		if(shape == ID_CIRCLE) g.fillOval(20, 20, 50, 50);
		else if(shape == ID_SQUARE) g.fillRect(20, 20, 50, 50);
		else if(shape == ID_TRIANGLE) g.fillPolygon(TRIANGLE);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((background == null) ? 0 : background.hashCode());
		result = prime * result + ((foreground == null) ? 0 : foreground.hashCode());
		result = prime * result + shape;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileButton other = (TileButton) obj;
		if (background == null) {
			if (other.background != null) return false;
		}
		else if (!background.equals(other.background)) return false;
		if (foreground == null) {
			if (other.foreground != null) return false;
		}
		else if (!foreground.equals(other.foreground)) return false;
		if (shape != other.shape) return false;
		return true;
	}
	
	@Override
	public String toString() {
		String first = "TileButton(";
		String second = "";
		if(background.equals(WHITE)) second = "White BG + ";
		else if(background.equals(GRAY)) second = "Gray BG + ";
		else if(background.equals(BLACK)) second = "Black BG + ";
		String third = "";
		if(foreground.equals(RED)) third = "Red ";
		else if(foreground.equals(YELLOW)) third = "Yellow ";
		else if(foreground.equals(BLUE)) third = "Blue ";
		String fourth = switch (shape) {
		case ID_CIRCLE: yield "Circle)";
		case ID_SQUARE: yield "Square)";
		case ID_TRIANGLE: yield "Triangle)";
		default: yield ")";
		};
		return first + second + third + fourth;
	}
	
	public boolean isHap(TileButton btn1, TileButton btn2) { return TileButton.isHap(this, btn1, btn2); }

	public static boolean isHap(TileButton btn1, TileButton btn2, TileButton btn3) {
		if(btn1.equals(btn2) || btn2.equals(btn3)) return false;
		else if(!isPartiallyHap(btn1.background, btn2.background, btn3.background)) return false;
		else if(!isPartiallyHap(btn1.foreground, btn2.foreground, btn3.foreground)) return false;
		int s1 = btn1.shape, s2 = btn2.shape, s3 = btn3.shape;
		if(s1 == s2 && s2 == s3) return true;
		else if(s1 != s2 && s2 != s3 && s3 != s1) return true;
		return false;
	}
	
	private static boolean isPartiallyHap(Color c1, Color c2, Color c3) {
		if(c1.equals(c2) && c2.equals(c3)) return true;
		else if(!c1.equals(c2) && !c2.equals(c3) && !c3.equals(c1)) return true;
		return false;
	}
}
