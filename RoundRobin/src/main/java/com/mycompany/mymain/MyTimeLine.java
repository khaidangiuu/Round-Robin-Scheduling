/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mymain;

/**
 *
 * @author Khai-Dangiuu
 */
public class MyTimeLine implements Comparable<MyTimeLine> {
	private int startTimeItem;
	private int runTimeItem;

	public MyTimeLine(int startTimeItem, int runTimeItem) {
		super();
		this.startTimeItem = startTimeItem;
		this.runTimeItem = runTimeItem;
	}

	// for sort with startTimeItem
	public int compareTo(MyTimeLine compareMyTimeLine) {
		int compareStartTimeItem = ((MyTimeLine) compareMyTimeLine)
				.getStartTimeItem();
		return startTimeItem - compareStartTimeItem;
	}

	public int getStartTimeItem() {
		return startTimeItem;
	}

	public void setStartTimeItem(int startTimeItem) {
		this.startTimeItem = startTimeItem;
	}

	public int getRunTimeItem() {
		return runTimeItem;
	}

	public void setRunTimeItem(int runTimeItem) {
		this.runTimeItem = runTimeItem;
	}
}

