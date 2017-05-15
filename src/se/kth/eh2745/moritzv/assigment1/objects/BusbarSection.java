package se.kth.eh2745.moritzv.assigment1.objects;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class BusbarSection extends Equipment {

	public BusbarSection(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

	}

	protected BusbarSection() {
		super();

	}

	public static String getCimName() {
		return "cim:BusbarSection";

	}

	public Substation getSubstation() {
		EquipmentContainer eqC = getEquipmentContainer();
		if (eqC instanceof Substation) {

			return (Substation) eqC;
		}
		if (eqC instanceof VoltageLevel) {
			return ((VoltageLevel) eqC).getSubstation();
		}
		return null;

	}

}
