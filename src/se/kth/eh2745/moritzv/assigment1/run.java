package se.kth.eh2745.moritzv.assigment1;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.commons.cli.Options;

import se.kth.eh2745.moritzv.assigment1.gui.MainWindow;
import se.kth.eh2745.moritzv.assigment1.objects.BusbarSection;
import se.kth.eh2745.moritzv.assigment1.objects.RdfObject;

public class run {

	public static void main(String[] args) {
		Options options;
		try {
			options = Config.defineOptions();
			Config.parseArgs(options, args);
		} catch (Exception e1) {
			System.err.println("Error in main");
			e1.printStackTrace();

			return;
		}

	}

	protected static void printInfo(String id) {

		BusbarSection bb = (BusbarSection) RdfLibary.getByID(id);
		System.out.println("-" + bb);
		for (RdfObject obj : bb.getConnectedEquipment(true)) {
			System.out.println("  -" + obj);
		}

	}

	public static void startGui() {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", false);
				// Create and set up the window.
				JFrame frame = new JFrame("Power System");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// Add content to the window.
				frame.add(new MainWindow());

				// Display the window.
				frame.pack();
				frame.setVisible(true);
			}
		});

	}

}
