package csvCreate;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CsvCreator {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		File rawFile = new File("NYC_City_Hall_Library_Publications.xml");
		File output = new File("INTEGRATED-DATASET");
		
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document doc = dbBuilder.parse(rawFile);
		
		NodeList row = doc.getDocumentElement().getChildNodes();
		NodeList rows = ((Element)row.item(0)).getChildNodes();
		
		StringBuilder content = new StringBuilder();
		
		for (int i=0; i<rows.getLength();i++ ) {
			String category = ((Element)rows.item(i)).getElementsByTagName("category").item(0).getTextContent();
			String category_2 = ((Element)rows.item(i)).getElementsByTagName("category_2").item(0).getTextContent();
			String category_3 = ((Element)rows.item(i)).getElementsByTagName("category_3").item(0).getTextContent();
			content.append(category + "," + category_2 + "," + category_3 + "\n");
		}
		
		FileWriter fw = new FileWriter(output.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content.toString());
		bw.close();
	}
	

}
