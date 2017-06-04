package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class PowerTransformerEnd extends IdentifiedObject {
	protected double transformerR;
	protected double transformerX;
	protected double b;
	protected double g;

	protected RdfLink<RdfObject> transformer;

	@SuppressWarnings("unchecked")
	public PowerTransformerEnd(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

		this.transformerR = XmlParser.ParseDoubleNodeContent(element, "cim:PowerTransformerEnd.r");
		this.transformerX = XmlParser.ParseDoubleNodeContent(element, "cim:PowerTransformerEnd.x");
		this.b = XmlParser.ParseDoubleNodeContent(element, "cim:PowerTransformerEnd.b");
		this.g = XmlParser.ParseDoubleNodeContent(element, "cim:PowerTransformerEnd.g");
		this.transformer = (RdfLink<RdfObject>) XmlParser.ParseRdfLink(element,
				"cim:PowerTransformerEnd.PowerTransformer", this);
	}

	public static String getCimName() {
		return "cim:PowerTransformerEnd";

	}

	public static boolean hasDbTable() {
		return true;
	}

	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = EquipmentContainer.getTabelFields();
		list.add(new MysqlField("transformerR", MysqlFieldTypes.FLOAT));
		list.add(new MysqlField("transformerX", MysqlFieldTypes.FLOAT));
		return list;

	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setDouble(++index, getTransformerR());
		statment.setDouble(++index, getTransformerX());
		return index;
	}

	public double getTransformerR() {
		return transformerR;
	}

	public double getTransformerX() {
		return transformerX;
	}

	public double getB() {
		return b;
	}

	public double getG() {
		return g;
	}

	public RdfObject getTransformer() {
		return transformer.getObj();
	}

	public String getTransformerRdfId() {
		return transformer.getRdfId();
	}

}
