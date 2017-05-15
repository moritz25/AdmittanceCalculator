package se.kth.eh2745.moritzv.assigment1.objects;

import java.util.ArrayList;
import java.util.Collection;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;

public class RdfLink<T extends RdfObject> {

	static Collection<RdfLink<RdfObject>> list = new ArrayList<RdfLink<RdfObject>>();
	private String rdfId;
	protected T obj;

	@SuppressWarnings("unchecked")
	public RdfLink(String rdfId, RdfObject parent) {
		this.rdfId = rdfId;
		this.obj = null;
		RdfLibary.addToReferenceList(rdfId, parent);
		list.add((RdfLink<RdfObject>) this);
	}

	public String getRdfId() {
		return rdfId;
	}

	@SuppressWarnings("unchecked")
	public T getObj() {
		if (obj == null) {
			obj = (T) RdfLibary.getByID(rdfId);
		}
		return obj;
	}
	
	protected static void replaceLink(String oldId, RdfObject newObj) {
		for (RdfLink<RdfObject> rdfLink : list) {
			if(rdfLink.rdfId.equals(oldId)){
				rdfLink.rdfId= newObj.getRdfId();
				rdfLink.obj = RdfLibary.getByID(rdfLink.rdfId);
			}
			
		}
		
	}

}
