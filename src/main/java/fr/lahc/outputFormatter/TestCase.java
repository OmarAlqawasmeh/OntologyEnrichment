/**
 * 
 */
package fr.lahc.outputFormatter;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author Omar Qawasmeh
 *
 *
 */


@XmlRootElement(name = "keyword")
@XmlType(propOrder = { "systemErr", "systemOut", "info","classes","relations","instances","kwAbstract","kwLabel","kwType"})
public class TestCase {

	private String name;
	private String className;
	private String systemErr;
	private String systemOut;
	private String status; //either pass or failure
	private String info;
	
	private String kwAbstract;
	private String kwLabel;
	private String kwType;
	
	private List<String> classes = new ArrayList<>();
	private List<String> relations = new ArrayList<>();
	private List<String> instances = new ArrayList<>();
	
	
	/**
	 * @return the kwLabel
	 */
	@XmlAttribute
	public String getKwLabel() {
		return kwLabel;
	}
	
	/**
	 * @param kwLabel the kwLabel to set
	 */
	
	public void setKwLabel(String kwLabel) {
		this.kwLabel = kwLabel;
	}
	
	/**
	 * @return the kwType
	 */
	@XmlAttribute
	public String getKwType() {
		return kwType;
	}
	
	/**
	 * @param kwType the kwType to set
	 */
	public void setKwType(String kwType) {
		this.kwType = kwType;
	}
	
	
	
	
	/**
	 * @return the classes
	 */
	public List<String> getClasses() {
		return classes;
	}
	/**
	 * @param classes the classes to set
	 */
	public void setClasses(List<String> classes) {
		this.classes = classes;
	}
	
	/**
	 * @return the relations
	 */
	public List<String> getRelations() {
		return relations;
	}
	/**
	 * @param relations the relations to set
	 */
	public void setRelations(List<String> relations) {
		this.relations = relations;
	}
	/**
	 * @return the instances
	 */
	public List<String> getInstances() {
		return instances;
	}
	
	/**
	 * @param instances the instances to set
	 */
	public void setInstances(List<String> instances) {
		this.instances = instances;
	}
	
	/**
	 * @return the kwAbstract
	 */
	public String getKwAbstract() {
		return kwAbstract;
	}
	/**
	 * @param kwAbstract the kwAbstract to set
	 */
	public void setKwAbstract(String kwAbstract) {
		this.kwAbstract = kwAbstract;
	}
	
	
	/**
	 * @return the status
	 */
	@XmlAttribute
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

	@XmlAttribute
	public String getClassName() {
		return className;
	}
	
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * @return the systemErr
	 */
	public String getSystemErr() {
		return systemErr;
	}
	
	/**
	 * @param systemErr the systemErr to set
	 */
	public void setSystemErr(String systemErr) {
		this.systemErr = systemErr;
	}
	
	/**
	 * @return the systemOut
	 */
	public String getSystemOut() {
		return systemOut;
	}
	
	/**
	 * @param systemOut the systemOut to set
	 */
	public void setSystemOut(String systemOut) {
		this.systemOut = systemOut;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	
	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
}
