/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mymain;

/**
 *
 * @author Khai-Dangiuu
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

public class MyListRenderer extends JPanel implements
		ListCellRenderer<MyProcess> {
	JLabel lbName;
	JLabel lbColor;
	int bound = 3;

	public MyListRenderer() {
		setLayout(new GridLayout(1, 2, 5, 5));
		setBorder(new EmptyBorder(bound, bound, bound, bound));
		lbName = createJLable();
		lbColor = createJLable();

		add(lbColor);
		add(lbName);
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends MyProcess> list, MyProcess process, int index,
			boolean isSelected, boolean cellHasFocus) {

		lbName.setText(process.getName());
		lbColor.setBackground(process.getColor());

		if (isSelected) {
			lbName.setBackground(list.getSelectionBackground());
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			lbName.setBackground(list.getBackground());
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}

	private JLabel createJLable() {
		JLabel lb = new JLabel();
		lb.setBackground(Color.white);
		lb.setOpaque(true);
		return lb;
	}
}

