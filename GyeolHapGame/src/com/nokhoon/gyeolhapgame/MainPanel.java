package com.nokhoon.gyeolhapgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel {
	public static final String TITLE_TEXT = "결! 합!";
	private static final Font TITLE_FONT = new Font("빙그레체", Font.BOLD, 40);
	private static final Color BACKGROUND_COLOR = new Color(224, 255, 224);
	
	private JLabel textLabel;
	private TilePanel tilesPanel;
	private OptionPanel optionsPanel;
	private JTable foundTable;
	private CustomTableModel tableModel;
	
	public MainPanel() {
		super(null);
		setPreferredSize(new Dimension(450, 600));
		setBackground(BACKGROUND_COLOR);
		
		textLabel = new JLabel(TITLE_TEXT);
		textLabel.setFont(TITLE_FONT);
		textLabel.setBounds(30, 20, 390, 60);
		textLabel.setBackground(BACKGROUND_COLOR);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(textLabel);
		
		tilesPanel = new TilePanel();
		tilesPanel.setBounds(65, 90, 320, 320);
		tilesPanel.setBackground(BACKGROUND_COLOR);
		add(tilesPanel);
		
		optionsPanel = new OptionPanel();
		optionsPanel.setBounds(25, 410, 400, 60);
		optionsPanel.setBackground(BACKGROUND_COLOR);
		add(optionsPanel);
		
		tableModel = new CustomTableModel();
		foundTable = new JTable(tableModel);
		foundTable.setBounds(75, 480, 300, 100);
		foundTable.setRowHeight(24);
		foundTable.setBackground(BACKGROUND_COLOR);
		foundTable.setSelectionBackground(BACKGROUND_COLOR);
		foundTable.setCellSelectionEnabled(false);
		foundTable.setFont(new Font("빙그레체", Font.PLAIN, 20));
		add(foundTable);
	}
	
	public JLabel getTextLabel() { return textLabel; }
	public TilePanel getTilesPanel() { return tilesPanel; }
	public OptionPanel getOptionsPanel() { return optionsPanel; }
	public JTable getFoundHapTable() { return foundTable; }
	public CustomTableModel getTableModel() { return tableModel; }
	
	public class CustomTableModel extends AbstractTableModel {
		private String[] data;
		
		public CustomTableModel() {
			data = new String[16];
		}
		
		@Override
		public int getRowCount() { return 4; }
		@Override
		public int getColumnCount() { return 4; }
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			int index = convertToIndex(rowIndex, columnIndex);
			return data[index];
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}
		
		@Override
		public String getColumnName(int column) {
			return "합";
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			int index = convertToIndex(rowIndex, columnIndex);
			data[index] = aValue.toString();
		}
		
		public void setValueAt(String value, int index) {
			if(index < 0 || index >= data.length) throw new IndexOutOfBoundsException(index);
			data[index] = value;
		}
		
		public void clear() {
			for(int i = 0; i < data.length; ++i) data[i] = null;
		}
		
		private int convertToIndex(int row, int column) {
			if(row < 0 || row >= getRowCount()) throw new IllegalArgumentException("row index out of range");
			if(column < 0 || column >= getColumnCount()) throw new IllegalArgumentException("column index out of range");
			return row * 4 + column;
		}
	}
}
