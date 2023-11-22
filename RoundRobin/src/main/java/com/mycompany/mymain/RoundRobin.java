/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mymain;

/**
 *
 * @author Khai-Dangiuu
 */
import java.util.ArrayList;
import java.util.Collections;

public class RoundRobin {
	private ArrayList<MyProcess> inListProcess;
	private ArrayList<MyProcess> readyListProcess;
	private ArrayList<MyProcess> outListProcess;
	private int roundRobinTime;

	public RoundRobin(ArrayList<MyProcess> listProcess, int roundRobinTime) {
		this.inListProcess = listProcess;
		this.roundRobinTime = roundRobinTime;
		readyListProcess = new ArrayList<MyProcess>();
		outListProcess = new ArrayList<MyProcess>();
		resetInputData();
		roundRobin();
		out(outListProcess);
	}

	private void resetInputData() {
		if (inListProcess != null) {
			for (int i = 0; i < inListProcess.size(); i++) {
				inListProcess.get(i).getListTimeLine().clear();
				inListProcess.get(i).setRunTimeItem(
						inListProcess.get(i).getRunTime());
			}
		}
	}

	public RoundRobin() {
		roundRobinTime = 5;
		inListProcess = new ArrayList<MyProcess>();
		readyListProcess = new ArrayList<MyProcess>();
		outListProcess = new ArrayList<MyProcess>();

		inListProcess.add(new MyProcess("P1", 0, 8));
		inListProcess.add(new MyProcess("P2", 1, 7));
		inListProcess.add(new MyProcess("P3", 3, 6));
		inListProcess.add(new MyProcess("P4", 8, 9));

		out(inListProcess);
		roundRobin();
		sortProcess();
		out(outListProcess);
	}

	public void roundRobin() {
		if (inListProcess.size() == 0) {
			return;
		}
		sortProcess();
		int time = 0;
		time = loadReadyList(time, null);
		while (!readyListProcess.isEmpty() || !inListProcess.isEmpty()) {

			MyProcess process = readyListProcess.get(0);
			readyListProcess.remove(0);
			if (roundRobinTime < process.getRunTimeItem()) {
				process.getListTimeLine().add(
						new MyTimeLine(time, roundRobinTime));
				process.setRunTimeItem(process.getRunTimeItem()
						- roundRobinTime);
				time += roundRobinTime;
				// if (!inListProcess.isEmpty()
				// && time > inListProcess.get(0).getStartTime()) {
				// time = loadReadyList(time);
				// readyListProcess.add(process);
				// } else {
				// readyListProcess.add(process);
				// time = loadReadyList(time);
				// }
				time = loadReadyList(time, process);

			} else {
				process.getListTimeLine().add(
						new MyTimeLine(time, process.getRunTimeItem()));
				time += process.getRunTimeItem();
				process.setDoneTime(time);
				process.setSaveTime(process.getDoneTime()
						- process.getStartTime());
				process.setWaitTime(process.getSaveTime()
						- process.getRunTime());
				outListProcess.add(process);
				time = loadReadyList(time, null);
			}
		}
		System.out.println("ROund Robin  done...");
	}

	private int loadReadyList(int time, MyProcess p) {
		boolean checkAdd = false;
		if (!inListProcess.isEmpty()) {
			if (readyListProcess.isEmpty()) {
				if (p == null && time < inListProcess.get(0).getStartTime()) {
					time = inListProcess.get(0).getStartTime();
				}
			}
			for (int i = 0; i < inListProcess.size(); i++) {
				MyProcess process = inListProcess.get(i);
				if (time >= process.getStartTime()) {
					if (p != null && time == process.getStartTime()
							&& !checkAdd) {
						readyListProcess.add(p);
						checkAdd = true;
					}
					readyListProcess.add(process);
					inListProcess.remove(i);
					i--;
				}
			}
		}
		if (!checkAdd && p != null) {
			readyListProcess.add(p);
		}
		return time;
	}

	private void sortProcess() {
		Collections.sort(inListProcess);
	}

	public void out(ArrayList<MyProcess> list) {
		for (int i = 0; i < list.size(); i++) {
			MyProcess process = list.get(i);
			System.out.print(process.toString());
		}
	}

	public ArrayList<MyProcess> getOutListProcess() {
		return outListProcess;
	}

	public void setOutListProcess(ArrayList<MyProcess> outListProcess) {
		this.outListProcess = outListProcess;
	}
}

