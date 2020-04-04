package fr.lahc.enrichontologies;
import java.io.FileNotFoundException;

import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

import com.github.andrewoma.dexx.collection.List;

/**
 * 
 */

/**
 * @author Omar Qawasmeh
 *
 * 
 */
public class RetrieveKeywords {
	
	private static final String DS = "http://localhost:3030/ds1/data";
	
	private List<String> candidateSetOfKeywords;
	/**
	 * @param candidateSetOfKeywords the candidateSetOfKeywords to set
	 */
	public void setCandidateSetOfKeywords(List<String> candidateSetOfKeywords) {
		this.candidateSetOfKeywords = candidateSetOfKeywords;
	}
	/**
	 * @return the candidateSetOfKeywords
	 */
	public List<String> getCandidateSetOfKeywords() {
		return candidateSetOfKeywords;
	}
	
	public void giveMeSomeKeywords(){
		
	}
}
