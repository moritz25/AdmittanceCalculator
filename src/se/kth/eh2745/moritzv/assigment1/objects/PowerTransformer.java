package se.kth.eh2745.moritzv.assigment1.objects;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Element;

import com.wphooper.number.Complex;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.exception.VoltageLevelNotFoundException;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class PowerTransformer extends Equipment {

	public PowerTransformer(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

	}

	public static String getCimName() {
		return "cim:PowerTransformer";

	}

	public static boolean hasDbTable() {
		return true;
	}

	@Override
	public Complex getAdmittanz(double sBase) throws VoltageLevelNotFoundException {
		for (PowerTransformerEnd pte : getPowerTransformerEnds()) {
			if (pte.getTransformerR() != 0) {
				return new Complex(pte.getTransformerR() / getZBase(sBase), pte.getTransformerX() / getZBase(sBase))
						.inv();
			}

		}
		return null;

	}

	@Override
	public Complex getShunt(double sBase) throws VoltageLevelNotFoundException {
		for (PowerTransformerEnd pte : getPowerTransformerEnds()) {
			if (pte.getTransformerR() != 0) {
				return new Complex(pte.getG() / 2.0 * getZBase(sBase), pte.getB() / 2.0 * getZBase(sBase));
			}

		}
		return null;

	}

	public Collection<PowerTransformerEnd> getPowerTransformerEnds() {
		Collection<PowerTransformerEnd> allPowerTransformerEnd = RdfLibary.getAllofType(PowerTransformerEnd.class);
		Collection<PowerTransformerEnd> powerTransformerEndHere = new ArrayList<PowerTransformerEnd>();
		for (PowerTransformerEnd pte : allPowerTransformerEnd) {
			if (pte.getTransformer().equals(this)) {
				powerTransformerEndHere.add(pte);
			}
		}
		return powerTransformerEndHere;
	}

}
