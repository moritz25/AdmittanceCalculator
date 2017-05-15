package se.kth.eh2745.moritzv.assigment1.gui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;

public class DrawHelpers {
	protected static JTextField makeTextField(String text) {
		JTextField textField = new JTextField(text);
		textField.setEditable(false);
		textField.setBorder(null);
		return textField;
	}

	protected static GridBagConstraints makeConstraints() {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.CENTER;
		return c;
	}
}
