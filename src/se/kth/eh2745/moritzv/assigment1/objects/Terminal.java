package se.kth.eh2745.moritzv.assigment1.objects;

import java.util.Collection;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class Terminal extends IdentifiedObject {

	protected RdfLink<Equipment> conductingEquipment;
	protected RdfLink<ConnectivityNode> connectivityNode;
	
	public Equipment getConductingEquipment() {
		return conductingEquipment.getObj();
	}

	public String getConductingEquipmentRdfId() {
		return conductingEquipment.getRdfId();
	}
	
	public ConnectivityNode getConnectivityNode() {
		return connectivityNode.getObj();
	}

	public String getConnectivityNodeRdfId() {
		return connectivityNode.getRdfId();
	}
	
	public Collection<Terminal> getConnectedTerminals(){
		Collection<Terminal> terminals = getConnectivityNode().getTerminals();
		terminals.remove(this);
		return terminals;
	}
	
	
	public static  String getCimName() {
		return "cim:Terminal";

	}
	
	
	@Override
	public RdfObject[] getLinks() {
		RdfObject[] objs={getConductingEquipment(),getConnectivityNode()};
		return objs;
	}
	
	@SuppressWarnings("unchecked")
	public Terminal(Element element) throws XmlStructureNotAsAssumedException {
		super(element);
		conductingEquipment=(RdfLink<Equipment>) XmlParser.ParseRdfLink(element, "cim:Terminal.ConductingEquipment",this);
		connectivityNode=(RdfLink<ConnectivityNode>) XmlParser.ParseRdfLink(element, "cim:Terminal.ConnectivityNode",this);
	}

	protected Terminal() {
		
	}

}
