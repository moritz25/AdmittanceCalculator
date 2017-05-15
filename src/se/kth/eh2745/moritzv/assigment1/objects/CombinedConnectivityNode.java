package se.kth.eh2745.moritzv.assigment1.objects;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;

public class CombinedConnectivityNode extends ConnectivityNode {

	public CombinedConnectivityNode(ConnectivityNode con1,ConnectivityNode con2)  {
		super();
		this.name =  con1.name+"/"+con2.name;
		this.rdfId = con1.rdfId+ con2.rdfId;
		
		RdfLink.replaceLink(con1.rdfId, this);
		RdfLink.replaceLink(con2.rdfId, this);
		RdfLibary.replace(this, con1, con2);
	}

}
