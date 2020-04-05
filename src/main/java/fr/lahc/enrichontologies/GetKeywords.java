package fr.lahc.enrichontologies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 */

/**
 * @author Omar Qawasmeh
 *
 * 
 */
public class GetKeywords {
	static final Logger LOG = LoggerFactory.getLogger(GetKeywords.class);

	private static final String DS = "http://localhost:3030/ds1/";
	private static final Model config = ModelFactory.createDefaultModel();
	private RDFConnection conn = RDFConnectionFactory.connect(DS);

	public void askLucene() {
		LOG.info("to be implemnted...");
	}
	public void pushOntology(String ontologyurl) throws FileNotFoundException {

		LOG.info("Initilizing ontology " + ontologyurl + " and pushing it to fuseki server to extract keywords");

		try {
			Model model = ModelFactory.createDefaultModel();
			// model.read("/home/omar/Desktop/EMSE_work_tests/saref-core/ontology/saref.ttl");
			// model.read(ontologyurl);

			try (FileInputStream input = new FileInputStream(ontologyurl)) {
				model.read(input,null,"TTL");
			} catch (Exception ex) {
				
			}
			conn.begin(ReadWrite.WRITE);
			conn.put(model);
			LOG.info("In write transaction");
			conn.commit();
			conn.close();
			LOG.info("Writing finished ...");

		} catch (Exception e) {

			LOG.error(e.getMessage());
		}

		finally {

		}
	}
}
