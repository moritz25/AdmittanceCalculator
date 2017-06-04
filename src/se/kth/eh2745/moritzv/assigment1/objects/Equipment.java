package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import org.w3c.dom.Element;

import com.wphooper.number.Complex;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.VoltageLevelNotFoundException;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public abstract class Equipment extends IdentifiedObject {

	protected RdfLink<EquipmentContainer> equipmentContainer;

	@SuppressWarnings("unchecked")
	public Equipment(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
		this.equipmentContainer = (RdfLink<EquipmentContainer>) XmlParser.ParseRdfLink(element,
				"cim:Equipment.EquipmentContainer", this);

	}

	protected Equipment() {
		super();
	}

	public static ArrayList<MysqlField> getTabelFields() {
		ArrayList<MysqlField> list = IdentifiedObject.getTabelFields();
		list.add(new MysqlField("equipmentContainer_rdf:ID", MysqlFieldTypes.VARCHAR, 50));
		return list;
	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setString(index + 1, getEquipmentContainerRdfId());

		return index + 1;
	}

	public Complex getAdmittanz(double sbase) throws VoltageLevelNotFoundException {
		return new Complex(0);
	}

	public Complex getShunt(double sbase) throws VoltageLevelNotFoundException {
		return new Complex(0);
	}

	public Collection<Terminal> getTerminals() {
		Collection<Terminal> allTerminals = RdfLibary.getAllofType(Terminal.class);
		Collection<Terminal> terminalsAtThis = new ArrayList<Terminal>();
		for (Terminal terminal : allTerminals) {

			if (this.equals(terminal.getConductingEquipment())) {
				terminalsAtThis.add(terminal);
			}
		}
		return terminalsAtThis;
	}

	public Collection<Equipment> getConnectedEquipment() {
		return getConnectedEquipment(new ArrayList<Equipment>(), false);
	}

	public Collection<Equipment> getConnectedEquipment(Equipment ignore) {
		Collection<Equipment> list = new ArrayList<Equipment>();
		list.add(ignore);
		return getConnectedEquipment(list, false);
	}

	public Collection<Equipment> getConnectedEquipment(boolean skipBreaker) {

		return getConnectedEquipment(new ArrayList<Equipment>(), true);
	}

	public Collection<Equipment> getConnectedEquipment(Equipment ignore, boolean skipBreaker) {
		Collection<Equipment> list = new ArrayList<Equipment>();
		list.add(ignore);
		return getConnectedEquipment(list, skipBreaker);
	}

	public Collection<Equipment> getConnectedEquipment(Collection<Equipment> ignore, boolean skipBreaker) {

		Collection<Equipment> equipment = new LinkedHashSet<>(); // To have
																	// unique
																	// values

		for (Terminal terminal_here : this.getTerminals()) {

			Collection<Equipment> connected = getEquipmentBehandTerminal(ignore, terminal_here, skipBreaker);
			// returns null if ignore is connected to this Terminal (=> we
			// are going back)
			if (connected != null) {
				equipment.addAll(connected);
			}

		}

		return equipment;
	}

	private Collection<Equipment> getEquipmentBehandTerminal(Collection<Equipment> ignore, Terminal terminal_start,
			boolean skipBreaker) {
		Collection<Equipment> tempList = new ArrayList<Equipment>();
		for (Terminal terminal_afterConNode : terminal_start.getConnectedTerminals()) {
			Equipment currentObj = terminal_afterConNode.getConductingEquipment();
			if (ignore.contains(currentObj)) {
				return null; // We are going back, return
								// nothing
			}
			if (skipBreaker && currentObj instanceof Breaker) {
				if (!((Breaker) currentObj).isOpen()) {
					ignore.add(this);
					Collection<Equipment> connected = currentObj.getConnectedEquipment(ignore, true);
					if (!connected.isEmpty()) {
						tempList.addAll(connected);
					} else {
						return null; // Going back via breaker, skipping
					}
				}
			} else {
				if (currentObj != null) {
					tempList.add(currentObj);
				}
			}

		}
		return tempList;
	}

	public double getNominalVoltage() throws VoltageLevelNotFoundException {
		if (getEquipmentContainer() instanceof VoltageLevel) {
			return ((VoltageLevel) getEquipmentContainer()).getBaseVoltage().getNominalVoltage();
		}

		Collection<Equipment> connectedEq = getConnectedEquipment(true);
		for (Equipment equipment : connectedEq) {
			if (equipment.getEquipmentContainer() instanceof VoltageLevel) {
				return ((VoltageLevel) equipment.getEquipmentContainer()).getBaseVoltage().getNominalVoltage();
			}
		}
		throw new VoltageLevelNotFoundException(this.toString());
	}

	public double getZBase(double sBase) throws VoltageLevelNotFoundException {
		double vBase = getNominalVoltage();
		return vBase * vBase / sBase;
	}

	public EquipmentContainer getEquipmentContainer() {
		return equipmentContainer.getObj();
	}

	public String getEquipmentContainerRdfId() {
		return equipmentContainer.getRdfId();
	}

}