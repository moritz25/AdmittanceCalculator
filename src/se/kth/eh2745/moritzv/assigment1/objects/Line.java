package se.kth.eh2745.moritzv.assigment1.objects;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class Line extends EquipmentContainer {

	protected RdfLink<GeographicalRegion> region;

	@SuppressWarnings("unchecked")
	public Line(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
		this.region = (RdfLink<GeographicalRegion>) XmlParser.ParseRdfLink(element, "cim:Line.Region", this);

	}

	public static String getCimName() {
		return "cim:Line";
	}
}
