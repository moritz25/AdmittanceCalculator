package se.kth.eh2745.moritzv.assigment1;

import java.util.Collection;
import java.util.List;

import com.wphooper.number.Complex;

import se.kth.eh2745.moritzv.assigment1.exception.VoltageLevelNotFoundException;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;
import se.kth.eh2745.moritzv.assigment1.objects.BusbarSection;
import se.kth.eh2745.moritzv.assigment1.objects.CombinedBusbarSection;
import se.kth.eh2745.moritzv.assigment1.objects.CombinedConnectivityNode;
import se.kth.eh2745.moritzv.assigment1.objects.CombinedTerminal;
import se.kth.eh2745.moritzv.assigment1.objects.Equipment;
import se.kth.eh2745.moritzv.assigment1.objects.Terminal;

public class AdmittanceCalculator {
	static double sbase = 100;

	public static Complex[][] calculateAdmittanceMatrix() throws XmlStructureNotAsAssumedException {
		normalizeBusbarSections();
		List<BusbarSection> allBusbarSections = RdfLibary.getAllofType(BusbarSection.class);
		Complex[][] admittanceMatrix = new Complex[allBusbarSections.size()][allBusbarSections.size()];

		for (BusbarSection bus : allBusbarSections) {
			Collection<Equipment> connected = bus.getConnectedEquipment(true);
			for (Equipment equipmentConnected : connected) {

				Collection<Equipment> connectedBehind = equipmentConnected.getConnectedEquipment(bus, true);
				for (Equipment equipmentBehind : connectedBehind) {

					if (equipmentBehind instanceof BusbarSection) {
						int iBus1 = allBusbarSections.indexOf(bus);
						int iBus2 = allBusbarSections.indexOf(equipmentBehind);
						System.out.println("Adding value [" + iBus1 + "][" + iBus2 + "]= " + equipmentConnected);
						try {

							Complex admittanz = equipmentConnected.getAdmittanz(sbase);
							if (admittanceMatrix[iBus1][iBus2] == null) {
								admittanceMatrix[iBus1][iBus2] = admittanz.neg();
							} else {
								admittanceMatrix[iBus1][iBus2] = admittanceMatrix[iBus1][iBus2].minus(admittanz);
							}

							if (admittanceMatrix[iBus1][iBus1] == null) {
								admittanceMatrix[iBus1][iBus1] = admittanz;
							} else {
								admittanceMatrix[iBus1][iBus1] = admittanceMatrix[iBus1][iBus1].add(admittanz);
							}

						} catch (VoltageLevelNotFoundException e) {
							System.err.println("Could not found VoltageLevel for");
						}
					}
				}

			}
		}

		return admittanceMatrix;
	}

	public static void normalizeBusbarSections() throws XmlStructureNotAsAssumedException {
		List<BusbarSection> allBusbarSections = RdfLibary.getAllofType(BusbarSection.class);

		for (BusbarSection bus : allBusbarSections) {
			Collection<Equipment> connected = bus.getConnectedEquipment(true);
			for (Equipment equipmentConnected : connected) {
				if (equipmentConnected instanceof BusbarSection) {
					if (Config.verbose) {
						System.out.println("Combining Busbar " + bus.getName() + " and "
								+ ((BusbarSection) equipmentConnected).getName());
					}
					CombinedBusbarSection newBus = new CombinedBusbarSection(bus, (BusbarSection) equipmentConnected);
					Object[] terminals = newBus.getTerminals().toArray();
					if (terminals.length == 2) {

						new CombinedTerminal((Terminal) terminals[0], (Terminal) terminals[1]);
						new CombinedConnectivityNode(((Terminal) terminals[0]).getConnectivityNode(),
								((Terminal) terminals[1]).getConnectivityNode());

					} else {
						throw new XmlStructureNotAsAssumedException("Not one Terminal on buses" + newBus);
					}
					normalizeBusbarSections(); // Start again
					return;

				}
			}
		}
	}

	public static String printAdmittanceMatrix() throws XmlStructureNotAsAssumedException {
		Complex[][] admittanceMatrix = calculateAdmittanceMatrix(); // Has to be
																	// before
																	// getting
																	// the List
																	// of
																	// Busbars
																	// as they
																	// might
																	// change
		List<BusbarSection> allBusbarSections = RdfLibary.getAllofType(BusbarSection.class);
		String matrix = "";
		for (int i = 0; i < allBusbarSections.size(); i++) {
			matrix += " ( " + allBusbarSections.get(i).getName() + " )\t";
		}
		matrix += "\n";

		for (Complex[] row : admittanceMatrix) {
			for (Complex c : row) {
				if (c != null) {
					if (c.im() == 0) {
						matrix += "| " + String.format("%.4f\t", c.re()) + " \t";
					} else {
						if (c.im() > 0) {
							matrix += "| " + String.format("%.4f + i %.4f", c.re(), c.im()) + " \t";
						} else {
							matrix += "| " + String.format("%.4f - i %.4f", c.re(), -c.im()) + " \t";
						}

					}
				} else {
					matrix += "| 0 \t\t";
				}

			}
			matrix += "\t|\n";
		}

		return matrix;
	}
}
