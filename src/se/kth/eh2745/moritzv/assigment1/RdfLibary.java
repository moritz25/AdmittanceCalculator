package se.kth.eh2745.moritzv.assigment1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import se.kth.eh2745.moritzv.assigment1.objects.RdfObject;

public class RdfLibary {
	/**
	 * map List of all objects by their ID
	 */
	protected static HashMap<String, RdfObject> map;
	/**
	 * mapReference List which Objects references the Id
	 */
	protected static HashMap<String, ArrayList<RdfObject>> mapReference;

	public static void addToList(RdfObject obj) {
		if (map == null) {
			map = new HashMap<String, RdfObject>();
		}
		map.put(obj.getRdfId(), obj);
	}

	public static void addToReferenceList(String referenceRdfId, RdfObject obj) {
		if (mapReference == null) {
			mapReference = new HashMap<String, ArrayList<RdfObject>>();
		}
		if (mapReference.get(referenceRdfId) == null) {
			ArrayList<RdfObject> tmpArrayList = new ArrayList<RdfObject>();
			tmpArrayList.add(obj);
			mapReference.put(referenceRdfId, tmpArrayList);
		} else {
			ArrayList<RdfObject> tmpArrayList = mapReference.get(referenceRdfId);
			tmpArrayList.add(obj);
			mapReference.put(referenceRdfId, tmpArrayList);
		}

	}

	public static RdfObject getByID(String id) {
		return map.get(id);

	}

	public static List<RdfObject> getAll() {
		if (map == null) {
			map = new HashMap<String, RdfObject>();
		}

		List<RdfObject> list = new LinkedList<RdfObject>();
		list.addAll(map.values());
		Collections.sort(list);

		return list;

	}

	public static <T extends RdfObject> List<T> getAllofType(Class<T> classToSearch) {
		return filterCollectionType(classToSearch, getAll());

	}

	@SuppressWarnings("unchecked")
	public static <T extends RdfObject> List<T> filterCollectionType(Class<? extends T> classToSearch,
			Collection<? extends RdfObject> c) {

		List<T> typeValues = new LinkedList<T>();
		for (RdfObject obj : c) {
			if (classToSearch.isInstance(obj)) {
				typeValues.add((T) obj);
			}
		}
		return typeValues;
	}

	public static ArrayList<RdfObject> getReferences(String referenceRdfId) {
		ArrayList<RdfObject> refs = mapReference.get(referenceRdfId);
		if (refs == null) {
			refs = new ArrayList<RdfObject>();
		}
		return refs;
	}

	public static void replace(RdfObject newObj, RdfObject old1, RdfObject old2) {

		if (map.remove(old1.getRdfId()) == null) {
			System.err.println("Could not remove item from map " + old1);
		}
		if (map.remove(old2.getRdfId()) == null) {
			System.err.println("Could not remove item from map " + old2);
		}
		addToList(newObj);

		ArrayList<RdfObject> newRefList = new ArrayList<RdfObject>();
		ArrayList<RdfObject> list1 = mapReference.get(old1.getRdfId());
		ArrayList<RdfObject> list2 = mapReference.get(old2.getRdfId());
		if (list1 != null) {
			newRefList.addAll(list1);
		}
		if (list2 != null) {
			newRefList.addAll(list2);
		}

		mapReference.put(newObj.getRdfId(), newRefList);
		mapReference.put(old1.getRdfId(), null);
		mapReference.put(old2.getRdfId(), null);
		for (String key : mapReference.keySet()) {
			ArrayList<RdfObject> references = getReferences(key);
			if (references.contains(old1)) {
				references.remove(old1);
				if (!references.contains(newObj)) {
					references.add(newObj);
				}
			}
			if (references.contains(old2)) {
				references.remove(old2);
				if (!references.contains(newObj)) {
					references.add(newObj);
				}
			}

			mapReference.put(key, references);
		}
	}

}
