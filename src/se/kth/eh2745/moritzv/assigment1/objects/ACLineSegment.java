package se.kth.eh2745.moritzv.assigment1.objects;

import org.w3c.dom.Element;

import com.wphooper.number.Complex;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.exception.VoltageLevelNotFoundException;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class ACLineSegment extends Equipment {

	protected double r;

	public ACLineSegment(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

		this.r = XmlParser.ParseDoubleNodeContent(element, "cim:ACLineSegment.r");
	}

	public static String getCimName() {
		return "cim:ACLineSegment";
	}

	@Override
	public Complex getAdmittanz(double sBase) throws VoltageLevelNotFoundException {
		return new Complex(r * getZBase(sBase)).inv();
	}

}
