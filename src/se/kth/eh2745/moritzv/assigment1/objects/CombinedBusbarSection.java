package se.kth.eh2745.moritzv.assigment1.objects;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;

public class CombinedBusbarSection extends BusbarSection {

	public CombinedBusbarSection(BusbarSection bus1, BusbarSection bus2) {
		super();
		this.equipmentContainer = new RdfLink<EquipmentContainer>(bus1.equipmentContainer.getRdfId(), this);
		this.name = bus1.name + "/" + bus2.name;
		
		this.rdfId= bus1.rdfId + bus2.rdfId ;
		bus1.name= "Replaced "+bus1.name;
		bus2.name= "Replaced "+bus2.name;
	
		RdfLink.replaceLink(bus1.rdfId, this);
		RdfLink.replaceLink(bus2.rdfId, this);
		RdfLibary.replace(this, bus1, bus2);
		
		
	}

}
