package se.kth.eh2745.moritzv.assigment1.objects;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public abstract class EquipmentContainer extends IdentifiedObject {

	public EquipmentContainer(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
	}

}