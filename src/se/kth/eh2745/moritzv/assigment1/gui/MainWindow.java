package se.kth.eh2745.moritzv.assigment1.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import se.kth.eh2745.moritzv.assigment1.AdmittanceCalculator;
import se.kth.eh2745.moritzv.assigment1.XmlParser;

public class MainWindow extends JPanel implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = -8900980526587595357L;
	static private final String newline = "\n";
	private JButton eqButton, sshButton, treeButton;
	private JTextArea log;
	private JTextArea admittance;
	private JFileChooser fc;

	public MainWindow() {
		super(new GridBagLayout());
		GridBagConstraints c = DrawHelpers.makeConstraints();

		JTextField heading = DrawHelpers.makeTextField("Admittance Calculator");
		heading.setHorizontalAlignment(JTextField.CENTER);
		heading.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 40));

		c.weightx = 0.5;
		c.weighty = 0.2;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;

		add(heading, c);

		log = new JTextArea(5, 20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		JTextField logHeading = DrawHelpers.makeTextField("log");

		c.weightx = 0.5;
		c.weighty = 0.5;

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		add(logHeading, c);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		add(logScrollPane, c);

		admittance = new JTextArea(5, 20);
		admittance.setMargin(new Insets(5, 5, 5, 5));
		admittance.setEditable(false);
		admittance.setText("EQ and SSH Data Missing");
		JScrollPane admittanceScrollPane = new JScrollPane(admittance);

		JTextField admittanceHeading = DrawHelpers.makeTextField("Admittance (Y)");

		c.weightx = 0.5;
		c.weighty = 0.5;

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		add(admittanceHeading, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		add(admittanceScrollPane, c);

		fc = new JFileChooser();

		eqButton = new JButton("Load EQ-File");
		eqButton.addActionListener(this);

		sshButton = new JButton("Load SSH-File");

		sshButton.addActionListener(this);

		treeButton = new JButton("Print CIM-Tree");

		treeButton.addActionListener(this);
		updateState();

		c.weightx = 0.5;
		c.weighty = 0.2;

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		add(eqButton, c);
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		add(sshButton, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;

		add(treeButton, c);

	}

	private void updateState() {
		if (XmlParser.isReadEq()) {
			eqButton.setEnabled(false);
			sshButton.setEnabled(true);
			treeButton.setEnabled(true);
			admittance.setText("SSH Data Missing");
			if (XmlParser.isReadSsh()) {
				try {
					admittance.setText(AdmittanceCalculator.printAdmittanceMatrix());
				} catch (Exception e) {
					admittance.setText("Error Calulating Admittance" + e.getMessage());
				}
			}
		} else {
			sshButton.setEnabled(false);
			treeButton.setEnabled(false);
			admittance.setText("EQ and SSH Data Missing");
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Handle open button action.
		if (e.getSource() == eqButton) {
			int returnVal = fc.showOpenDialog(MainWindow.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				log.append("Opening EQ: " + file.getName() + "." + newline);
				try {
					log.append(XmlParser.parseFile(file, false));
				} catch (Exception exception) {
					log.append(exception.toString());
				}
				updateState();

			}
			log.setCaretPosition(log.getDocument().getLength());

			// Handle save button action.
		} else if (e.getSource() == sshButton) {
			int returnVal = fc.showSaveDialog(MainWindow.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				log.append("Opening SSH: " + file.getName() + "." + newline);
				try {
					log.append(XmlParser.parseFile(file, true));
				} catch (Exception exception) {
					log.append(exception.toString());
				}

				updateState();

			}
			log.setCaretPosition(log.getDocument().getLength());
		} else if (e.getSource() == treeButton) {

			JFrame frame = new JFrame("CIM-Tree");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			// Add content to the window.
			frame.add(new TreeWindow());
			frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
			// Display the window.
			frame.pack();
			frame.setVisible(true);
		}

	}

}
