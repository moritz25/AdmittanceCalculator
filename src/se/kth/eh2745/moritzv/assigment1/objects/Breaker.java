package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class Breaker extends Equipment {

	protected boolean open;

	public Breaker(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

	}

	@Override
	public void updateFromXML(Element element) throws XmlStructureNotAsAssumedException {
		super.updateFromXML(element);
		this.open = XmlParser.ParseBooleanNodeContent(element, "cim:Switch.open");
	}

	public static String getCimName() {
		return "cim:Breaker";

	}

	public static boolean hasDbTable() {
		return true;
	}

	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = Equipment.getTabelFields();
		list.add(new MysqlField("open", MysqlFieldTypes.BOOLEAN));
		return list;

	}

	@Override
	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setBoolean(++index, isOpen());
		return index;
	}

	public boolean isOpen() {
		return open;
	}

}
