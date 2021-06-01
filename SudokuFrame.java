
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {

	 JCheckBox checkBox;
	 JButton checkButton;
	 JTextArea problem;
	 JTextArea solution;


	 private void refresh() {
		 String result = "";
		 try {
			 Sudoku sudoku = new Sudoku(Sudoku.textToGrid(problem.getText()));
			 int count = sudoku.solve();
			 if(count<=0) {
				 result = "Entered board is not solvable \n";
			 }else{
				 result = sudoku.getSolutionText();
			 }
			 result += "solutions:" + count + " \n";
			 result += "elapsed:" + sudoku.getElapsed() + "ms " + " \n";
		 } catch (Exception ex) {
			 result = "Invalid data format";
		 }
		 solution.setText(result);

	 }


	 class problemListener implements DocumentListener{
		 public void insertUpdate(DocumentEvent e) {
			 if (checkBox.isSelected())
				 refresh();
		 }
		 public void removeUpdate(DocumentEvent e) {
			 if (checkBox.isSelected())
				 refresh();
		 }
		 public void changedUpdate(DocumentEvent e) {
		 	if(checkBox.isSelected()) {
				refresh();
			}
		 }
	 }
	
	public SudokuFrame() {
		super("Sudoku Solver");
		
		// YOUR CODE HERE
		setLayout(new BorderLayout(4, 4));
		problem = new JTextArea(15,33);
		solution = new JTextArea(15,33);

		problem.setBorder(new TitledBorder("Puzzle"));
		solution.setBorder(new TitledBorder("Solution"));

		solution.setEditable(false);

		checkButton = new JButton("Check");
		checkBox = new JCheckBox("Auto Check");

		Box check = Box.createHorizontalBox();
		check.add(checkButton);
		check.add(checkBox);

		problem.getDocument().addDocumentListener(new problemListener());
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});

		add(problem,BorderLayout.WEST);
		add(solution,BorderLayout.EAST);
		add(check,BorderLayout.SOUTH);

		setLocationByPlatform(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
