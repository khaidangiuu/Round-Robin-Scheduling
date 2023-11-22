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
import java.util.ArrayList;

public class MyProcess implements Comparable<MyProcess> {
	private String name;
	private int startTime;
	private int runTime;
	private int runTimeItem;
	private int waitTime;
	private int saveTime;
	private int doneTime;
	private Color color;
	private ArrayList<MyTimeLine> listTimeLine;

	public MyProcess() {
	}

	public MyProcess(String name, int startTime, int runTime) {
		this.name = name;
		this.startTime = startTime;
		this.runTime = runTime;
		this.runTimeItem = runTime;
		listTimeLine = new ArrayList<MyTimeLine>();
	}

	@Override
	public int compareTo(MyProcess process) {
		int compareStartTime = ((MyProcess) process).getStartTime();
		return startTime - compareStartTime;
	}

	public String toString() {
		String string = getName() + " - " + getStartTime() + " - "
				+ getRunTime() + "\n" + "\tTimeLine:\n";
		for (int i = 0; i < getListTimeLine().size(); i++) {
			string += "\t" + getListTimeLine().get(i).getStartTimeItem()
					+ " - " + getListTimeLine().get(i).getRunTimeItem() + "\n";
		}
		return string;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	public int getRunTimeItem() {
		return runTimeItem;
	}

	public void setRunTimeItem(int runTimeItem) {
		this.runTimeItem = runTimeItem;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(int saveTime) {
		this.saveTime = saveTime;
	}

	public int getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(int doneTime) {
		this.doneTime = doneTime;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<MyTimeLine> getListTimeLine() {
		return listTimeLine;
	}

	public void setListTimeLine(ArrayList<MyTimeLine> listTimeLine) {
		this.listTimeLine = listTimeLine;
	}
}

