package se.kth.eh2745.moritzv.assigment1.objects;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;

public class CombinedTerminal extends Terminal {

	public CombinedTerminal(Terminal  t1, Terminal  t2)  {
		super();
		this.conductingEquipment = new RdfLink<Equipment>(t1.conductingEquipment.getRdfId(), this);
		this.connectivityNode = new RdfLink<ConnectivityNode>(t1.getConnectivityNodeRdfId(), this);
		this.name = t1.name+"/"+t2.name;
		this.rdfId = t1.name+t2.name;
		t1.name="Replaced "+t1.name;
		t2.name="Replaced "+t2.name;
		
		RdfLink.replaceLink(t1.rdfId, this);
		RdfLink.replaceLink(t2.rdfId, this);
		RdfLibary.replace(this, t1, t2);
	}

}
