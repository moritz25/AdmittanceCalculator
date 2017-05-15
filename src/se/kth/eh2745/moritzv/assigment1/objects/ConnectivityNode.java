package se.kth.eh2745.moritzv.assigment1.objects;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class ConnectivityNode extends IdentifiedObject {
	public ConnectivityNode(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
	}

	protected ConnectivityNode() {

	}

	public static String getCimName() {
		return "cim:ConnectivityNode";

	}

	public Collection<Terminal> getTerminals() {
		Collection<Terminal> allTerminals = RdfLibary.getAllofType(Terminal.class);
		Collection<Terminal> terminalsAtThis = new ArrayList<Terminal>();
		for (Terminal terminal : allTerminals) {

			if (this.equals(terminal.getConnectivityNode())) {
				terminalsAtThis.add(terminal);
			}
		}
		return terminalsAtThis;
	}

}
