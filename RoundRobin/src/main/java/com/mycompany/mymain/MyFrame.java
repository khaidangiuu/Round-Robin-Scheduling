/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mymain;

/**
 *
 * @author Khai-Dangiuu
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MyFrame extends JFrame implements ActionListener {
	private JButton btnRun, btnNew, btnAdd, btnClear, btnUpdate, btnDelete;
	private String authorName = "Nhóm: Đình Khải - Hải Nam - Nhất Long. \n Copyright: Nguyễn Văn Quân"; 
	private String[] columnInput = { "Process name", "Arrival Time", "Burst Time" };
	private String[] strInput = { "Quantum Time", "Process name", "Arrival Time",
			"Burst Time" };
	private String[] columnOutput = { "Process name", "Wait time", "Save time",
			"Done time" };
	private Color[] color = { Color.blue, Color.red, Color.green, Color.pink,
			Color.cyan, Color.orange, Color.yellow, Color.gray, Color.magenta,
			Color.white };
	private ArrayList<Color> listColor = new ArrayList<Color>();
	private JTextField tfNameProcess, tfStartTime, tfRunTime, tfRoundRobin;
	private JTable tbInput, tbOutput;
	private int width = 800, height = 600;
	private JList<MyProcess> listProcessNote;
	private MyGraphics graphicsPanel;
	private ArrayList<MyProcess> listProcess;
	private RoundRobin roundRobin;
	private int roundRobinTime;
	private boolean isUpdate;
	private JLabel lbOutAvgWait;
	private JLabel lbOutAvgSave;

	// create frame
	public MyFrame() {
		add(cretaeMainPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Round Robin");
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
		tfRoundRobin.requestFocus();
	}

	// create main panel
	private JPanel cretaeMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createControlPanel(), BorderLayout.PAGE_START);
		panel.add(createContentPanel(), BorderLayout.CENTER);
		panel.add(createStatusPanel(), BorderLayout.PAGE_END);
		return panel;
	}

	// create run panel on top of main panel
	private JPanel createControlPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 0, 5, 5));
		panel.setBorder(new EmptyBorder(5, width * 3 / 8, 0, width * 3 / 8));
		panel.add(btnRun = createButton("Run"));
		panel.add(btnNew = createButton("New"));
		return panel;
	}

	// create content panel on center of main panel
	private JPanel createContentPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.add(createInputPanel(), BorderLayout.PAGE_START);
		panel.add(createOutputPanel(), BorderLayout.CENTER);
		return panel;
	}

	// create status panel on bottom of main panel
	private JPanel createStatusPanel() {
		JLabel lb = new JLabel(authorName);
		lb.setForeground(Color.blue);

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBackground(Color.lightGray);
		panel.add(lb);
		return panel;
	}

	// create input panel on top of content panel
	private JPanel createInputPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setPreferredSize(new Dimension(width, height / 3));
		panel.setBorder(new TitledBorder("Input"));
		panel.add(createTablePanel(0), BorderLayout.CENTER);
		panel.add(createInforPanel(), BorderLayout.EAST);
		return panel;
	}

	// create output panel on bottom of content panel
	private JPanel createOutputPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new TitledBorder("Output"));
		panel.add(createGraphicsPanel(), BorderLayout.PAGE_START);
		panel.add(createTablePanel(1), BorderLayout.CENTER);
		panel.add(createRightPanel(), BorderLayout.EAST);
		return panel;
	}

	/**
	 * create table with type typeTable = 0 is input table, 1 output table
	 */
	private JPanel createTablePanel(int typeTable) {
		JTable table;
		if (typeTable == 0) {
			tbInput = createTable(columnInput);
			tbInput.getSelectionModel().addListSelectionListener(
					new RowListener());
			table = tbInput;
		} else {
			tbOutput = createTable(columnOutput);
			table = tbOutput;
		}
		JScrollPane scrollPanel = new JScrollPane(table);
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.add(scrollPanel);
		return panel;
	}

	private JPanel createRightPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 0, 5, 5));
		panel.setPreferredSize(new Dimension(width / 3, 0));
		panel.add(createOutPanel());
		panel.add(createNotePanel());
		return panel;
	}

	// create infor panel for input information of process
	private JPanel createInforPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setPreferredSize(new Dimension(width / 3, height / 3));
		panel.add(createInputRoundRobin(), BorderLayout.PAGE_START);
		panel.add(createInforProcessPanel(), BorderLayout.CENTER);
		return panel;
	}

	// create Graphics Panel
	private JPanel createGraphicsPanel() {
		graphicsPanel = new MyGraphics();
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(graphicsPanel);
		return panel;
	}

	private JPanel createOutPanel() {
		JLabel lbAvgWait = new JLabel("AVG time wait");
		JLabel lbAvgSave = new JLabel("AVG time save");
		lbOutAvgWait = new JLabel("0.00");
		lbOutAvgSave = new JLabel("0.00");
		lbAvgWait.setHorizontalAlignment(JLabel.CENTER);
		lbAvgSave.setHorizontalAlignment(JLabel.CENTER);
		lbOutAvgWait.setHorizontalAlignment(JLabel.CENTER);
		lbOutAvgSave.setHorizontalAlignment(JLabel.CENTER);

		JPanel panel = new JPanel(new GridLayout(8, 1, 5, 5));
		panel.setBorder(new TitledBorder(""));
		panel.add(lbAvgWait);
		panel.add(lbOutAvgWait);
		panel.add(lbAvgSave);
		panel.add(lbOutAvgSave);
		return panel;
	}

	// create note panel, color of process
	private JPanel createNotePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(""));
		panel.add(new JLabel("Note"), BorderLayout.PAGE_START);
		panel.add(new JScrollPane(listProcessNote = createListProcessNote()),
				BorderLayout.CENTER);
		return panel;
	}

	private JPanel createInputRoundRobin() {
		JPanel panel = new JPanel(new BorderLayout(15, 5));
		panel.setBorder(new EmptyBorder(0, 5, 0, 5));
		panel.add(new JLabel("Quantum Time"), BorderLayout.WEST);
		panel.add(tfRoundRobin = createTextField(), BorderLayout.CENTER);
		return panel;
	}

	private JPanel createInforProcessPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(new TitledBorder("Process Infor"));
		panel.add(createLabelPanel(strInput), BorderLayout.WEST);
		panel.add(createTextFieldPanel(), BorderLayout.CENTER);
		panel.add(createButtonInfoPanel(), BorderLayout.PAGE_END);
		return panel;
	}

	private JPanel createLabelPanel(String[] lbArray) {
		JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
		for (int i = 1; i < lbArray.length; i++) {
			panel.add(new JLabel(lbArray[i]));
		}
		return panel;
	}

	private JPanel createTextFieldPanel() {
		// tfRoundRobin = createTextField();
		tfNameProcess = createTextField();
		tfStartTime = createTextField();
		tfRunTime = createTextField();

		JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
		// panel.add(tfRoundRobin);
		panel.add(tfNameProcess);
		panel.add(tfStartTime);
		panel.add(tfRunTime);
		return panel;
	}

	private JPanel createButtonInfoPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 0, 5, 5));
		panel.setBorder(new EmptyBorder(1, 1, 5, 1));
		panel.add(btnAdd = createButton("Add"));
		panel.add(btnClear = createButton("Clear"));
		panel.add(btnUpdate = createButton("Update"));
		panel.add(btnDelete = createButton("Delete"));
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		return panel;
	}

	private JTable createTable(String[] columnName) {
		DefaultTableModel tbModel = new DefaultTableModel(null, columnName) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		JTable table = new JTable(tbModel);
		return table;
	}

	// create a button
	private JButton createButton(String btnName) {
		JButton btn = new JButton(btnName);
		btn.addActionListener(this);
		btn.setMargin(new Insets(5, 0, 5, 0));
		return btn;
	}

	private JTextField createTextField() {
		JTextField tf = new JTextField(10);
		return tf;
	}

	private JList<MyProcess> createListProcessNote() {
		JList<MyProcess> list = new JList<MyProcess>();
		list.setCellRenderer(new MyListRenderer());
		// list.setFixedCellWidth(width / 6);
		list.setBackground(null);
		list.setBorder(null);
		return list;
	}

	private void run() {
		if (listProcess != null) {
			roundRobin = new RoundRobin(listProcess, roundRobinTime);
			listProcess = roundRobin.getOutListProcess();
			graphicsPanel.setListProcess(listProcess);
			graphicsPanel.repaint();
			loadListNote(listProcess);
			loadOutLabel(listProcess);
			tbOutput.setModel(loadDataOutput());
		} else {
			JOptionPane.showMessageDialog(null, "Must enter data!",
					"Run error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addProcess(MyProcess process) {
		if (process != null) {
			if (listProcess.size() < 10) {
				process.setColor(color[listProcess.size()]);
				listColor.add(color[listProcess.size()]);
			} else {
				Color color = createColor();
				if (!listColor.contains(color)) {
					listColor.add(color);
				}
				process.setColor(color);
			}
			listProcess.add(process);
			clearProcess();
		}
	}

	private void clearProcess() {
		tfNameProcess.setText("");
		tfStartTime.setText("");
		tfRunTime.setText("");
		if (tbInput.getSelectedRow() < 0) {
			btnUpdate.setEnabled(false);
			btnDelete.setEnabled(false);
		} else {
			btnUpdate.setText("Update");
		}
		if (!btnAdd.isEnabled()) {
			btnAdd.setEnabled(true);
		}
		tfNameProcess.requestFocus();
	}

	private void updateProcess() {
		isUpdate = true;
		showSelection(tbInput.getSelectedRow());
		btnAdd.setEnabled(false);
		btnUpdate.setText("Ok");
	}

	private void deleteProcess() {
		int index = tbInput.getSelectedRow();
		if (index >= 0) {
			int select = JOptionPane.showOptionDialog(null,
					"Are you sure delete " + listProcess.get(index).getName(),
					"Delete Process", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (select == 0) {
				listProcess.remove(tbInput.getSelectedRow());
				tbInput.setModel(loadDataInput());
				btnDelete.setEnabled(false);
			}
		}
	}

	private void newProcess() {
		listProcess = null;
		roundRobinTime = 0;
		tfRoundRobin.setText("");
		clearProcess();
		tfRoundRobin.requestFocus();
		graphicsPanel.setListProcess(listProcess);
		graphicsPanel.repaint();
		tbInput.setModel(loadDataInput());
		tbOutput.setModel(loadDataOutput());
		loadListNote(listProcess);
		loadOutLabel(listProcess);
	}

	private void updated() {

		int row = tbInput.getSelectedRow();
		System.out.println("row = " + row);
		MyProcess process = getProcess();
		System.out.println(process.toString());

		listProcess.clear();

		System.out.println("number row: " + tbInput.getRowCount());
		System.out.println("list table");
		for (int i = 0; i < tbInput.getRowCount(); i++) {
			String name = (String) tbInput.getValueAt(i, 0);
			int startTime = (Integer) tbInput.getValueAt(i, 1);
			int runTime = (Integer) tbInput.getValueAt(i, 2);
			addProcess(new MyProcess(name, startTime, runTime));
			System.out.println(listProcess.get(i).toString());
		}
		tbInput.setModel(loadDataInput());
		showSelection(row);

		if (process != null) {
			btnAdd.setEnabled(true);
			btnDelete.setEnabled(true);
			btnUpdate.setText("Update");
			process.setColor(listProcess.get(row).getColor());
			System.out.println("row = " + row);

			System.out.println(" --------------------- ");

			for (int i = 0; i < listProcess.size(); i++) {
				System.out.println(listProcess.get(i).toString());
			}

			System.out.println(" ------- ");
			listProcess.set(row, process);

			for (int i = 0; i < listProcess.size(); i++) {
				System.out.println(listProcess.get(i).toString());
			}

			System.out.println(" --------------------- ");

			tbInput.setModel(loadDataInput());
			clearProcess();
		} else {
			System.out.println("Null");
		}
	}

	private MyProcess getProcess() {
		if (checkInput()) {
			if (listProcess == null) {
				listProcess = new ArrayList<MyProcess>();
				roundRobinTime = Integer
						.parseInt(tfRoundRobin.getText().trim());
			}
			if (roundRobinTime != 0
					&& roundRobinTime != Integer.parseInt(tfRoundRobin
							.getText().trim())) {
				int select = JOptionPane.showOptionDialog(null,
						"Are you want change quantum time?",
						"Change quantum time", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (select == 0) {
					roundRobinTime = Integer.parseInt(tfRoundRobin.getText());
				}
			}
			String name = tfNameProcess.getText().trim();
			int startTime = Integer.parseInt(tfStartTime.getText().trim());
			int runTime = Integer.parseInt(tfRunTime.getText().trim());
			System.out.println("getProcess:" + name + " - " + startTime + " - "
					+ runTime);
			return new MyProcess(name, startTime, runTime);
		}
		return null;
	}

	private boolean checkInput() {
		// check tfRoundRobin
		if (!checkTextFiled(tfRoundRobin)) {
			return false;
		}
		// check tfNameProcess
		if (tfNameProcess.getText().trim().equals("")) {
			tfNameProcess.requestFocus();
			JOptionPane.showMessageDialog(null, "Enter process name",
					"Input error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// check tfStartTime
		if (!checkTextFiled(tfStartTime)) {
			return false;
		}
		// check tfRunTime
		if (!checkTextFiled(tfRunTime)) {
			return false;
		}
		return true;
	}

	private boolean checkTextFiled(JTextField tf) {
		String str = tf.getText().trim();
		if (str.equals("")) {
			tf.requestFocus();
			JOptionPane.showMessageDialog(null, "Enter a integer number",
					"Input error", JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			try {
				Integer.parseInt(str);
			} catch (Exception e) {
				tf.requestFocus();
				JOptionPane.showMessageDialog(null, "Enter a integer number",
						"Input error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private DefaultTableModel loadDataInput() {
		DefaultTableModel tbModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbModel.setColumnIdentifiers(columnInput);
		if (listProcess != null) {
			for (int i = 0; i < listProcess.size(); i++) {
				MyProcess process = listProcess.get(i);
				Object[] row = { process.getName(), process.getStartTime(),
						process.getRunTime() };
				tbModel.addRow(row);
			}
		}
		return tbModel;
	}

	private DefaultTableModel loadDataOutput() {
		DefaultTableModel tbModel = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tbModel.setColumnIdentifiers(columnOutput);
		if (listProcess != null) {
			for (int i = 0; i < listProcess.size(); i++) {
				MyProcess process = listProcess.get(i);
				Object[] row = { process.getName(), process.getWaitTime(),
						process.getSaveTime(), process.getDoneTime() };
				tbModel.addRow(row);
			}
		}
		return tbModel;
	}

	private class RowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) {
				return;
			}
			btnUpdate.setEnabled(true);
			btnDelete.setEnabled(true);
		}
	}

	private void showSelection(int row) {
		tfNameProcess.setText(tbInput.getValueAt(row, 0) + "");
		tfStartTime.setText(tbInput.getValueAt(row, 1) + "");
		tfRunTime.setText(tbInput.getValueAt(row, 2) + "");
		btnUpdate.setEnabled(true);
		btnDelete.setEnabled(true);
	}

	private void loadListNote(ArrayList<MyProcess> list) {
		DefaultListModel<MyProcess> model = new DefaultListModel<MyProcess>();
		if (list != null) {
			for (int i = list.size() - 1; i >= 0; i--) {
				model.addElement(list.get(i));
			}
		}
		listProcessNote.setModel(model);
	}

	private Color createColor() {
		Random random = new Random();
		int rgb = random.nextInt(255);
		Color color = new Color(rgb);
		return color;
	}

	private void loadOutLabel(ArrayList<MyProcess> list) {
		String sumTimeWait = "0.00";
		String sumTimeSave = "0.00";
		if (list != null) {
			double sumWait = 0;
			double sumSave = 0;
			for (int i = 0; i < list.size(); i++) {
				sumWait += list.get(i).getWaitTime();
				sumSave += list.get(i).getSaveTime();
			}
			sumTimeWait = String.format("%.2f", sumWait / list.size());
			sumTimeSave = String.format("%.2f", sumSave / list.size());
		}
		lbOutAvgWait.setText(sumTimeWait);
		lbOutAvgSave.setText(sumTimeSave);
	}

	// action

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRun) {
			System.out.println("Run");
			run();
		}
		if (e.getSource() == btnAdd) {
			System.out.println("Add");
			addProcess(getProcess());
			tbInput.setModel(loadDataInput());
		}
		if (e.getSource() == btnClear) {
			System.out.println("Clear");
			clearProcess();
		}
		if (e.getActionCommand() == "Update") {
			System.out.println("Update");
			updateProcess();
		}
		if (e.getActionCommand() == "Ok") {
			System.out.println("Ok");
			updated();
		}
		if (e.getSource() == btnDelete) {
			System.out.println("Delete");
			deleteProcess();
		}
		if (e.getSource() == btnNew) {
			System.out.println("New");
			newProcess();
		}

	}
}

