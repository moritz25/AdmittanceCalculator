package se.kth.eh2745.moritzv.assigment1.gui;

import java.awt.Choice;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.TreePrinter;
import se.kth.eh2745.moritzv.assigment1.objects.RdfObject;

public class TreeWindow extends JPanel implements  ItemListener {


	private static final long serialVersionUID = -255043417266059781L;
	private JTextArea tree;
	private Choice treeChoice;

	public TreeWindow() {
		super(new GridBagLayout());
		GridBagConstraints c = DrawHelpers.makeConstraints();

		JTextField heading = DrawHelpers.makeTextField("CIM-Tree");
		heading.setHorizontalAlignment(JTextField.CENTER);
		heading.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 40));

		c.weightx = 0.5;
		c.weighty = 0.2;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;

		add(heading, c);


		
		tree = new JTextArea(5, 20);
		tree.setMargin(new Insets(5, 5, 5, 5));
		tree.setEditable(false);
		JScrollPane treeScrollPane = new JScrollPane(tree);

		JTextField treeHeading = DrawHelpers.makeTextField("tree");

		c.weightx = 0.2;
		c.weighty = 1;

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		add(treeHeading, c);
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		add(treeScrollPane, c);

		

		

		treeChoice = new Choice();
		treeChoice.addItemListener(this);
		setTreeChoiceObejcts();
		c.weightx = 0.5;
		c.weighty = 0.1;


		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;

		add(treeChoice, c);

		
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == treeChoice) {

			String selected = treeChoice.getSelectedItem();
			for (RdfObject obj : RdfLibary.getAll()) {
				if (obj.toString().equals(selected)) {

					tree.setText(TreePrinter.printTree(obj));
					continue;
				}

			}
			

			
		}

	}
	private void setTreeChoiceObejcts() {
		treeChoice.removeAll();
		treeChoice.add("Select Object to print Tree");
		for (RdfObject obj : RdfLibary.getAll()) {
			treeChoice.add(obj.toString());
		}
	}


}
