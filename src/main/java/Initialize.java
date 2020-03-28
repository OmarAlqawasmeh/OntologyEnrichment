import java.io.FileNotFoundException;

import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

/**
 * 
 */

/**
 * @author Omar Qawasmeh
 *
 * 
 */
public class Initialize {
	private static final String DS = "http://localhost:3030/ds1/";
	private static final Model config = ModelFactory.createDefaultModel();
	private RDFConnection conn = RDFConnectionFactory.connect(DS);

	public void pushOntology() throws FileNotFoundException {
		try {
			Model model = ModelFactory.createDefaultModel();
			model.read("/home/omar/Desktop/EMSE_work_tests/saref-core/ontology/saref.ttl");
			System.out.println("Load a file");
			conn.begin(ReadWrite.WRITE);
			conn.put(model);
			System.out.println("In write transaction");
			conn.commit();
			conn.close();
			System.out.println("Writing finished ...");

		} finally {

		}
	}
}
