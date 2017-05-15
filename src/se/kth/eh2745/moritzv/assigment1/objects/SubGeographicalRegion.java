package se.kth.eh2745.moritzv.assigment1.objects;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class SubGeographicalRegion extends GeographicalRegion {

	protected RdfLink<GeographicalRegion> region;
	public static  String getCimName() {
		return "cim:SubGeographicalRegion";

	}
	@SuppressWarnings("unchecked")
	public SubGeographicalRegion(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
		this.region = (RdfLink<GeographicalRegion>) XmlParser.ParseRdfLink(element, "cim:SubGeographicalRegion.Region", this);
		
	}

}
