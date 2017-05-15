package se.kth.eh2745.moritzv.assigment1;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import se.kth.eh2745.moritzv.assigment1.exception.XmlParsingException;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;
import se.kth.eh2745.moritzv.assigment1.objects.RdfLink;
import se.kth.eh2745.moritzv.assigment1.objects.RdfObject;

public class XmlParser {

	private static boolean readEq = false;
	private static boolean readSsh = false;

	public static String parseFile(String filename, boolean updateTree) throws Exception {
		String status = "";
		File XmlFile = new File(filename);
		status = parseFile(XmlFile, updateTree);

		return status;

	}

	public static String parseFile(File XmlFile, boolean updateTree) throws Exception {
		String status = "";
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XmlFile);

		// normalize XML file
		doc.getDocumentElement().normalize();

		for (RdfObjectClasses classEnum : RdfObjectClasses.values()) {
			if (updateTree) {
				status += updateObjectsForClass(doc, classEnum.getClassvar());
			} else {
				status += createObjectsForClass(doc, classEnum.getClassvar());
			}
		}
		if (updateTree) {
			readSsh = true;
		} else {
			readEq = true;
		}

		return status;

	}

	public static boolean isReadEq() {
		return readEq;
	}

	public static boolean isReadSsh() {
		return readSsh;
	}

	private static String updateObjectsForClass(Document doc, Class<? extends RdfObject> cl)
			throws XmlParsingException {
		String status = "";
		try {

			NodeList list = getNodesForClass(doc, cl);

			for (int i = 0; i < list.getLength(); i++) {

				RdfObject obj = XmlParser.updateRdfObject((Element) list.item(i));
				if (obj != null) {
					status += "Updated " + obj.toString() + "\n";
				}

			}

		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof XmlParsingException) {
				throw (XmlParsingException) cause;
			} else {
				cause.printStackTrace();
			}
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			status += "Error in Class definitions for " + cl + "\n";
			status += e.toString() + "\n";
		}
		return status;

	}

	private static String createObjectsForClass(Document doc, Class<? extends RdfObject> cl)
			throws XmlParsingException {
		String status = "";
		try {

			NodeList list = getNodesForClass(doc, cl);

			for (int i = 0; i < list.getLength(); i++) {
				@SuppressWarnings("rawtypes")
				Class[] cArg = { Element.class };
				Constructor<? extends RdfObject> con = cl.getConstructor(cArg);
				RdfObject obj = con.newInstance((Element) list.item(i));

				status += "Created " + obj.toString() + "\n";

			}

		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();
			if (cause instanceof XmlParsingException) {
				throw (XmlParsingException) cause;
			} else {
				cause.printStackTrace();
			}

		} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException
				| InstantiationException e) {
			e.printStackTrace();
		}
		return status;
	}

	private static NodeList getNodesForClass(Document doc, Class<? extends RdfObject> cl)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Method method = cl.getMethod("getCimName", (Class<?>[]) null);
		String cimName = (String) method.invoke((Object) null, (Object[]) null);

		NodeList list = doc.getElementsByTagName(cimName);
		return list;
	}

	public static RdfObject updateRdfObject(Element element) throws XmlStructureNotAsAssumedException {
		RdfObject obj;
		String id = null;
		id = ParseRdfIDAbout(element);
		obj = RdfLibary.getByID(id);
		if (obj == null) {
			if (Config.verbose) {
				System.err.println("Found id:" + id + " which was not in the EQ-File, not using this information.");
			}
		} else {
			obj.updateFromXML(element);
		}
		return obj;

	}

	public static String ParseStringNodeContent(Element element, String tagName)
			throws XmlStructureNotAsAssumedException {
		try {

			String node = element.getElementsByTagName(tagName).item(0).getTextContent();

			return node;
		} catch (Exception e) {
			throw new XmlStructureNotAsAssumedException(element + tagName);
		}

	}

	public static boolean ParseBooleanNodeContent(Element element, String tagName)
			throws XmlStructureNotAsAssumedException {
		try {
			return Boolean.parseBoolean(XmlParser.ParseStringNodeContent(element, tagName));
		} catch (Exception e) {
			System.out.println(XmlParser.ParseStringNodeContent(element, tagName));
			throw new XmlStructureNotAsAssumedException(tagName);
		}
	}

	public static Double ParseDoubleNodeContent(Element element, String tagName)
			throws XmlStructureNotAsAssumedException {
		try {
			return Double.parseDouble(XmlParser.ParseStringNodeContent(element, tagName));
		} catch (Exception e) {
			throw new XmlStructureNotAsAssumedException(tagName);
		}
	}

	public static RdfLink<? extends RdfObject> ParseRdfLink(Element element, String tagName, RdfObject parent)
			throws XmlStructureNotAsAssumedException {

		String id = ((Element) element.getElementsByTagName(tagName).item(0)).getAttribute("rdf:resource").substring(1);
		if (id == null) {
			throw new XmlStructureNotAsAssumedException(tagName);
		}
		return new RdfLink<RdfObject>(id, parent);
	}

	public static String ParseRdfID(Element element) throws XmlStructureNotAsAssumedException {

		String id = element.getAttribute("rdf:ID");
		if (id == null) {
			throw new XmlStructureNotAsAssumedException("id");
		}
		return id;

	}

	public static String ParseRdfIDAbout(Element element) throws XmlStructureNotAsAssumedException {

		String id = element.getAttribute("rdf:about");
		if (id == null) {
			throw new XmlStructureNotAsAssumedException("about");
		}
		return id.substring(1);

	}

}
