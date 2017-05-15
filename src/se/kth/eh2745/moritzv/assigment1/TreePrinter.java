package se.kth.eh2745.moritzv.assigment1;

import java.util.ArrayList;
import java.util.HashSet;

import se.kth.eh2745.moritzv.assigment1.objects.RdfObject;

public class TreePrinter {

	private static HashSet<String> printedObjs;
	private static int maxDepth = 5;

	public static String printTree(RdfObject obj) {
		if (obj == null) {
			return null;
		}
		printedObjs = new HashSet<String>();
		printedObjs.add(obj.getRdfId());
		return obj + "\n" + printTree(obj, "", maxDepth);
	}

	private static String printTree(RdfObject head, String intention, int depth) {

		String status = "";
		for (RdfObject obj : head.getLinks()) {
			status += printObject(new String(new char[maxDepth - depth]).replace("\0", "   ") + "->", obj, depth);
		}
		ArrayList<RdfObject> refs = RdfLibary.getReferences(head.getRdfId());
		if (refs != null) {
			for (RdfObject obj : refs) {
				status += printObject(new String(new char[maxDepth - depth]).replace("\0", "   ") + " ^", obj, depth);
			}
		}

		return status;
	}

	private static String printObject(String intention, RdfObject obj, int depth) {
		String status = "";
		if (obj == null) {
			status += intention + "Not implemented Object\n";
		} else {

			if (printedObjs.contains(obj.getRdfId())) {

				status += intention + "(repeated) " + obj + "\n";
			} else {
				printedObjs.add(obj.getRdfId());
				status += intention + "" + obj + "\n";
				if (depth > 0) {
					status += printTree(obj, intention, --depth);
				}
			}
		}
		return status;
	}
}
