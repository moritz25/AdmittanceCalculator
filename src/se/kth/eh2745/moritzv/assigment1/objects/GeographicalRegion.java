package se.kth.eh2745.moritzv.assigment1.objects;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class GeographicalRegion extends IdentifiedObject {

	public GeographicalRegion(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

	}

	public static String getCimName() {
		return "cim:GeographicalRegion";

	}

}
