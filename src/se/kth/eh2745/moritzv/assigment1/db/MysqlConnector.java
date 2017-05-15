package se.kth.eh2745.moritzv.assigment1.db;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import se.kth.eh2745.moritzv.assigment1.Config;
import se.kth.eh2745.moritzv.assigment1.RdfLibary;
import se.kth.eh2745.moritzv.assigment1.RdfObjectClasses;
import se.kth.eh2745.moritzv.assigment1.exception.ConnectionFailedException;
import se.kth.eh2745.moritzv.assigment1.exception.DatabaseCreationFailedException;
import se.kth.eh2745.moritzv.assigment1.exception.InsertDataFailedException;
import se.kth.eh2745.moritzv.assigment1.exception.MysqlException;
import se.kth.eh2745.moritzv.assigment1.exception.TableCreationFailedException;
import se.kth.eh2745.moritzv.assigment1.objects.BaseVoltage;
import se.kth.eh2745.moritzv.assigment1.objects.RdfObject;

public class MysqlConnector {

	Connection conn = null;

	private Connection getConn() throws ConnectionFailedException {
		return getConn(true);
	}

	private Connection getConn(boolean withDB) throws ConnectionFailedException {
		if (conn == null) {
			if (withDB) {
				makeConnection(Config.dbName);
			} else {

				makeConnection(null);
			}

		}
		return conn;
	}

	private void updateConn() throws ConnectionFailedException {
		makeConnection(Config.dbName);
	}

	private void makeConnection(String dbName) throws ConnectionFailedException {
		try {
			Class.forName(Config.JDBC_DRIVER);
			if (dbName != null) {
				conn = DriverManager.getConnection(Config.dbUrl + dbName, Config.user, Config.pass);
			} else {
				conn = DriverManager.getConnection(Config.dbUrl, Config.user, Config.pass);
			}
		} catch (Exception e) {
			throw new ConnectionFailedException(e);
		}
	}

	public void makeDatabase() throws MysqlException {
		try {

			String sql = "DROP DATABASE if exists  " + Config.dbName;
			PreparedStatement createDB = getConn(false).prepareStatement(sql);
			createDB.executeUpdate();
			sql = "CREATE DATABASE   " + Config.dbName;
			createDB = getConn(false).prepareStatement(sql);
			createDB.executeUpdate();
			updateConn();

		} catch (SQLException e) {
			throw new DatabaseCreationFailedException(e);
		}

	}

	public void makeTables() throws TableCreationFailedException {
		for (RdfObjectClasses classEnum : RdfObjectClasses.values()) {
			makeTablesForClass(classEnum.getClassvar());

		}
	}

	private void makeTablesForClass(Class<? extends RdfObject> cl) throws TableCreationFailedException {
		try {

			Method methodHasTable = cl.getMethod("hasDbTable", (Class[]) null);
			Boolean hasTable = (boolean) methodHasTable.invoke((Object[]) null, (Object[]) null);
			if (!hasTable) {
				return;
			}
			String sql = "CREATE TABLE " + cl.getSimpleName() + "(";

			Method methodFields = cl.getMethod("getTabelFields", (Class[]) null);
			@SuppressWarnings("unchecked")
			ArrayList<MysqlField> tabelFields = (ArrayList<MysqlField>) methodFields.invoke((Object[]) null,
					(Object[]) null);
			for (MysqlField field : tabelFields) {
				sql += field.getCreateString() + ", ";
			}

			sql += " primary key (" + BaseVoltage.getPrimaryKeyName() + "));";

			PreparedStatement createDB = getConn().prepareStatement(sql);
			createDB.executeUpdate();

		} catch (Exception e) {
			throw new TableCreationFailedException(e);
		}

	}

	public void saveData() throws Exception {
		for (RdfObjectClasses classEnum : RdfObjectClasses.values()) {
			insterDataForClass(classEnum.getClassvar());
		}
	}

	private void insterDataForClass(Class<? extends RdfObject> cl) throws Exception {
		try {

			Method methodHasTable = cl.getMethod("hasDbTable", (Class[]) null);
			Boolean hasTable = (boolean) methodHasTable.invoke((Object[]) null, (Object[]) null);
			if (!hasTable) {
				return;
			}
			String sql = "INSERT INTO " + cl.getSimpleName() + "(";
			String sql_values = "";
			Method methodFields = cl.getMethod("getTabelFields", (Class[]) null);
			@SuppressWarnings("unchecked")
			ArrayList<MysqlField> tabelFields = (ArrayList<MysqlField>) methodFields.invoke((Object[]) null,
					(Object[]) null);
			for (MysqlField field : tabelFields) {
				sql += field.getQuotedName() + ", ";
				sql_values += "?, ";
			}
			sql = sql.substring(0, sql.length() - 2); // Remove ", " for last
														// entry
			sql_values = sql_values.substring(0, sql_values.length() - 2);
			sql += ") VALUES (" + sql_values + ")";

			PreparedStatement insertStatment = getConn().prepareStatement(sql);
			for (RdfObject obj : RdfLibary.getAllofType(cl)) {
				obj.insertData(insertStatment);
				insertStatment.addBatch();

			}

			insertStatment.executeBatch();

		} catch (SQLException e) {
			throw new InsertDataFailedException(e);
		}

	}

}
