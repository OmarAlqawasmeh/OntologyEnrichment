/**
 *
 */
package fr.lahc.outputFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Omar Qawasmeh
 *
 *
 */
@XmlRootElement(name = "enrichment")
@XmlType(propOrder = { "disabled", "time", "name", "testcase" })
public class TestSuite implements Serializable {

	private int tests;
	private boolean disabled;
	private float time;
	private String name;
	private TestCase[] testCase;

	public TestCase[] getTestcase() {
		return testCase;
	}

	public void setTestcase(TestCase[] testCase) {
		this.testCase = testCase;
	}

	@XmlAttribute
	public int getTests() {
		return tests;
	}

	public void setTests(int tests) {

		this.tests = tests;

	}

	@XmlAttribute
	public boolean getDisabled() {

		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;

	}

	@XmlAttribute
	public float getTime() {
		
		
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static void jaxbObjectToXML(TestSuite ts) {
		try {
			// Create JAXB Context
			JAXBContext jaxbContext = JAXBContext.newInstance(TestSuite.class);

			// Create Marshaller
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// Required formatting??
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Store XML to File
			File file = new File("outputFile.xml");

			// Writes XML file to file-system
			jaxbMarshaller.marshal(ts, file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}