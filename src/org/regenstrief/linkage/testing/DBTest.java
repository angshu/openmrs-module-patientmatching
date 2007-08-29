package org.regenstrief.linkage.testing;

/*
 * Class written to test the linkage database management code.
 */

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.regenstrief.linkage.Record;
import org.regenstrief.linkage.db.RecordDBManager;
import org.regenstrief.linkage.io.DataSourceReader;
import org.regenstrief.linkage.io.OrderedCharDelimFileReader;
import org.regenstrief.linkage.io.OrderedDataBaseReader;
import org.regenstrief.linkage.util.DataColumn;
import org.regenstrief.linkage.util.MatchingConfig;
import org.regenstrief.linkage.util.RecMatchConfig;
import org.regenstrief.linkage.util.ScorePair;
import org.regenstrief.linkage.util.XMLTranslator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DBTest {

	
	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("usage: java DBTest <config file>");
			System.exit(0);
		}
		File config = new File(args[0]);
		if(!config.exists()){
			System.out.println("config file does not exist, exiting");
			System.exit(0);
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			// Load the XML configuration file
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(config);
			RecMatchConfig rmc = XMLTranslator.createRecMatchConfig(doc);
			MatchingConfig test_mc = rmc.getMatchingConfigs().get(0);
			RecordDBManager ldbm = new RecordDBManager(rmc.getLinkDataSource1());
			
			if(ldbm.connect()){
				System.out.println("connected to db link data source");
				
			} else {
				System.out.println("error connecting to db");
			}
			System.out.println("created a config of:\n" + test_mc);
			
			Record test_insert = new Record();
			test_insert.addDemographic("fn", "test");
			test_insert.addDemographic("ln", "o'brien");
			
			if(ldbm.addRecordToDB(test_insert)){
				System.out.println("adding test record succeeded");
			} else {
				System.out.println("adding test record failed");
			}
			
			
		}
		catch(ParserConfigurationException pce){
			System.out.println("error making XML parser: " + pce.getMessage());
		}
		catch(SAXException spe){
			System.out.println("error parsing config file: " + spe.getMessage());
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		
		
	}

}