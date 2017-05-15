package se.kth.eh2745.moritzv.assigment1.objects;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.Element;

import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.XmlParser;
import se.kth.eh2745.moritzv.assigment1.db.MysqlField;
import se.kth.eh2745.moritzv.assigment1.db.MysqlFieldTypes;
import se.kth.eh2745.moritzv.assigment1.exception.XmlStructureNotAsAssumedException;

public class VoltageLevel extends EquipmentContainer {
	protected RdfLink<Substation> substation;
	protected RdfLink<BaseVoltage> baseVoltage;

	public Substation getSubstation() {
		return substation.getObj();
	}

	public String getSubstationRdfId() {
		return substation.getRdfId();
	}

	public BaseVoltage getBaseVoltage() {
		return baseVoltage.getObj();
	}

	public String getBaseVoltageRdfId() {
		return baseVoltage.getRdfId();
	}

	public static String getCimName() {
		return "cim:VoltageLevel";

	}
	
	public Collection<Equipment> getEquipment() {
		Collection<Equipment> allEquipment = RdfLibary.getAllofType(Equipment.class);
		Collection<Equipment> equipmentAtThis= new ArrayList<Equipment>();
		for (Equipment voltageLevel : allEquipment) {
			if(voltageLevel.getEquipmentContainer().equals(this)){
				equipmentAtThis.add( voltageLevel);
			}
		}
		return equipmentAtThis;
	}
	
	public <T extends Equipment> Collection<T> getEqipmentOfType(Class<T> classToSearch) {
		return RdfLibary.filterCollectionType(classToSearch, getEquipment());
		
	}
	
	public static boolean hasDbTable() {
		return true;
	}


	public static ArrayList<MysqlField> getTabelFields() {

		ArrayList<MysqlField> list = EquipmentContainer.getTabelFields();
		list.add(new MysqlField("substation_rdf:ID", MysqlFieldTypes.VARCHAR, 50));
		list.add(new MysqlField("baseVoltage_rdf:ID", MysqlFieldTypes.VARCHAR, 50));
		return list;

	}

	public int insertData(PreparedStatement statment) throws SQLException {
		int index = super.insertData(statment);
		statment.setString(++index, getSubstationRdfId());
		statment.setString(++index, getBaseVoltageRdfId());
		return index;
	}

	
	@SuppressWarnings("unchecked")
	public VoltageLevel(Element element) throws XmlStructureNotAsAssumedException {
		super(element);

		this.substation = (RdfLink<Substation>) XmlParser.ParseRdfLink(element, "cim:VoltageLevel.Substation",this);
		this.baseVoltage = (RdfLink<BaseVoltage>) XmlParser.ParseRdfLink(element, "cim:VoltageLevel.BaseVoltage",this);

	}

	
}
