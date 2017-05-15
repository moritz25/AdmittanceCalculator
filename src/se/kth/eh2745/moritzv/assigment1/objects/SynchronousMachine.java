package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class SynchronousMachine extends Equipment {

	protected double ratedS;
	protected RdfLink<GeneratingUnit> genUnit;
	protected RdfLink<RegulatingControl> regControl;
	protected double p;
	protected double q;

	public static String getCimName() {
		return "cim:SynchronousMachine";

	}
	
	public static boolean hasDbTable() {
		return true;
	}


	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = Equipment.getTabelFields();
		list.add(new MysqlField("ratedS", MysqlFieldTypes.FLOAT));
		list.add(new MysqlField("genUnit_rdf:ID", MysqlFieldTypes.VARCHAR,50));
		list.add(new MysqlField("regControl_rdf:ID", MysqlFieldTypes.VARCHAR,50));
		list.add(new MysqlField("P", MysqlFieldTypes.FLOAT));
		list.add(new MysqlField("Q", MysqlFieldTypes.FLOAT));
		return list;

	}

	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setDouble(++index, getRatedS());
		statment.setString(++index, getGenUnitRefId());
		statment.setString(++index, getEquipmentContainerRdfId());
		statment.setDouble(++index, getP());
		statment.setDouble(++index, getQ());
		return index;
	}

	@SuppressWarnings("unchecked")
	public SynchronousMachine(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

		this.ratedS = XmlParser.ParseDoubleNodeContent(element, "cim:RotatingMachine.ratedS");
		this.genUnit = (RdfLink<GeneratingUnit>) XmlParser.ParseRdfLink(element, "cim:RotatingMachine.GeneratingUnit",this);
		this.regControl = (RdfLink<RegulatingControl>) XmlParser.ParseRdfLink(element, "cim:RegulatingCondEq.RegulatingControl",this);
		
	
	}
	public void updateFromXML(Element element) throws XmlStructureNotAsAssumedException {
		super.updateFromXML(element);
		this.p = XmlParser.ParseDoubleNodeContent(element, "cim:RotatingMachine.p");
		this.q = XmlParser.ParseDoubleNodeContent(element, "cim:RotatingMachine.q");
	}

	
	public double getRatedS() {
		return ratedS;
	}

	public GeneratingUnit getGenUnit() {
		return genUnit.getObj();
	}

	public String getGenUnitRefId() {
		return genUnit.getRdfId();
	}

	public RegulatingControl getRegControl() {
		return regControl.getObj();
	}

	public String getRegControlRdfId() {
		return regControl.getRdfId();
	}
	
	public double getP() {
		return p;
	}

	public double getQ() {
		return q;
	}

	

}
