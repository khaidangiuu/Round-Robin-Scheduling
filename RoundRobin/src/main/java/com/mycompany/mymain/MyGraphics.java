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
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MyGraphics extends JPanel {

	private int heightPanel = 60;
	private int widthPanel = 800;
	private int bound = 30;
	private int heightGraphics = heightPanel - bound;
	private int widthGraphics = widthPanel - 2 * bound;
	private int sumTime = 0;
	private int startHeight = (heightPanel - heightGraphics) / 2;
	private ArrayList<MyProcess> listProcess;

	public MyGraphics() {
		setPreferredSize(new Dimension(widthPanel, heightPanel));
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		System.out.println("repaint");
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, widthPanel, heightPanel);
		g.setColor(Color.black);
		g.drawString(0 + "", bound, startHeight);
		g.drawRect(bound, startHeight, widthGraphics, heightGraphics);
		sumTime = getSumTime();
		if (listProcess != null) {
			System.out.println("sumTime = " + sumTime);
			for (int i = 0; i < listProcess.size(); i++) {
				MyProcess process = listProcess.get(i);
				process.setColor(process.getColor());
				int startTime = 0, startWidth = 0, runTime = 0, width = 0, endTime = 0, endWidth = 0;
				for (int j = 0; j < process.getListTimeLine().size(); j++) {
					MyTimeLine timeLine = process.getListTimeLine().get(j);

					startTime = timeLine.getStartTimeItem();
					startWidth = convertTimeToWidth(
							timeLine.getStartTimeItem(), sumTime, true);
					runTime = timeLine.getRunTimeItem();
					width = convertTimeToWidth(timeLine.getRunTimeItem(),
							sumTime, false);
					endTime = startTime + runTime;
					endWidth = startWidth + width;

					g.setColor(process.getColor());
					g.fillRect(startWidth, startHeight, width, heightGraphics);

					g.setColor(Color.black);
					g.drawLine(startWidth, startHeight, startWidth, startHeight
							+ heightGraphics);

					g.drawString(startTime + "", startWidth, startHeight);
				}
				g.drawString(endTime + "", endWidth, startHeight);
			}
			g.drawString(0 + "", bound, startHeight);
			g.drawRect(bound, startHeight, widthGraphics, heightGraphics);
		}

	}

	private int convertTimeToWidth(int time, int sumTime, boolean haveBound) {
		int width = 0;
		width += (int) (((double) time * widthGraphics / sumTime));
		if (!haveBound) {
			return width;
		}
		width += bound;
		return width;
	}

	private int getSumTime() {
		int sumTime = 0;
		if (listProcess != null) {
			MyProcess process = listProcess.get(listProcess.size() - 1);
			MyTimeLine timeLine = process.getListTimeLine().get(
					process.getListTimeLine().size() - 1);
			sumTime = timeLine.getStartTimeItem() + timeLine.getRunTimeItem();
		}
		return sumTime;
	}

	public ArrayList<MyProcess> getListProcess() {
		return listProcess;
	}

	public void setListProcess(ArrayList<MyProcess> listProcess) {
		this.listProcess = listProcess;
	}

}
