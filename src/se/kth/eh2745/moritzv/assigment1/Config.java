package se.kth.eh2745.moritzv.assigment1;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;

import se.kth.eh2745.moritzv.assigment1.db.MysqlConnector;
import se.kth.eh2745.moritzv.assigment1.exception.ConfigLoadFailedException;

public class Config {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static String dbUrl = "jdbc:mysql://localhost/";
	// Database credentials
	public static String user;
	public static String pass;
	public static String dbName;
	public static boolean verbose = false;

	public static Options defineOptions() {
		Options options = new Options();

		options.addOption("v", "verbose", false, "Be verbose");
		options.addOption("P", "pass", true, "Password for Mysql (overrids config.xml)");
		options.addOption("U", "user", true, "User for Mysql (overrids config.xml)");
		options.addOption("D", "db-name", true, "Database Name for Mysql (overrids config.xml)");
		options.addOption("L", "db-url", true, "Database Url for Mysql (overrids config.xml)");
		options.addOption("N", "no-config", false, "Don't load config.xml");
		options.addOption("s", "ssh", true, "Load SSH-File");
		options.addOption("e", "eq", true, "Load EQ-File");
		options.addOption("g", "gui", false, "Start GUI");
		options.addOption("c", "create-database", false, "Create Database");
		options.addOption("d", "database", false, "Save Data to Database");
		options.addOption("i", "info", true, "Print information about Object with RdfId id");
		options.addOption("a", "admittance", false, "Print Admittance Matrix");
		options.addOption("h", "help", false, "Print this help");

		return options;
	}

	public static void parseArgs(Options options, String[] args) throws Exception {
		MysqlConnector mysqlCon = null;
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("verbose")) {
				Config.verbose = true;
			}
			if (!cmd.hasOption("no-config")) {
				Config.loadXML();
			}
			if (cmd.hasOption("user")) {
				Config.user = cmd.getOptionValue('u');
			}
			if (cmd.hasOption("db-name")) {
				Config.dbName = cmd.getOptionValue("db-name");
			}
			if (cmd.hasOption("db-url")) {
				Config.dbUrl = cmd.getOptionValue("db-url");
			}
			if (cmd.hasOption("pass")) {
				Config.pass = cmd.getOptionValue('p');
			}

			if (cmd.hasOption("eq")) {
				String eqInfo = XmlParser.parseFile(cmd.getOptionValue('e'), false);
				if (Config.verbose) {
					System.out.println(eqInfo);
				}
			}
			if (cmd.hasOption("ssh")) {
				String sshInfo = XmlParser.parseFile(cmd.getOptionValue('s'), true);
				if (Config.verbose) {
					System.out.println(sshInfo);
				}
			}

			if (cmd.hasOption("create-database")) {
				mysqlCon = new MysqlConnector();
				mysqlCon.makeDatabase();
				mysqlCon.makeTables();

			}
			if (cmd.hasOption("database")) {
				if (mysqlCon == null) {
					mysqlCon = new MysqlConnector();
				}
				mysqlCon.saveData();
			}

			if (cmd.hasOption("info")) {
				run.printInfo(cmd.getOptionValue("info"));

			}
			if (cmd.hasOption("a")) {
				System.out.println(AdmittanceCalculator.printAdmittanceMatrix());
			}

			if (cmd.hasOption("gui")) {
				run.startGui();
			}
			if (cmd.hasOption("h")) {
				printHelp(options);
			}
		} catch (ParseException | ConfigLoadFailedException e1) {

			e1.printStackTrace();
			printHelp(options);
			return;
		}

		if (args.length == 0) {
			printHelp(options);
		}
	}

	private static void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("AdmittanceCalculator.jar", options);
	}

	public static void loadXML() throws ConfigLoadFailedException {
		try {

			File XmlFile = new File("config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(XmlFile);

			dbUrl = doc.getElementsByTagName("db-url").item(0).getTextContent();
			user = doc.getElementsByTagName("db-user").item(0).getTextContent();
			pass = doc.getElementsByTagName("db-pass").item(0).getTextContent();
			dbName = doc.getElementsByTagName("db-name").item(0).getTextContent();

		} catch (Exception e) {
			System.err.println("Could not load config.xml, trying Commandline values");
		}
	}

}
