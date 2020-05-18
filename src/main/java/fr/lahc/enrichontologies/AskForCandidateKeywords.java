package fr.lahc.enrichontologies;

import java.io.FileNotFoundException;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.query.text.EntityDefinition;
import org.apache.jena.query.text.TextDatasetFactory;
import org.apache.jena.query.text.TextIndexConfig;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDFS;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
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
public class AskForCandidateKeywords {
	static final Logger LOG = LoggerFactory.getLogger(AskForCandidateKeywords.class);

	public static Dataset createCode() {

		Dataset ds1 = DatasetFactory.create();

		// EntityDefinition entDef = new EntityDefinition("uri", "text",
		// RDFS.label) ;
		EntityDefinition entDef = new EntityDefinition("uri", "text");
		entDef.setPrimaryPredicate(RDFS.label.asNode());
		entDef.setPrimaryPredicate(RDFS.comment.asNode());

		Directory dir = new RAMDirectory();

		Dataset ds = TextDatasetFactory.createLucene(ds1, dir, new TextIndexConfig(entDef));

		return ds;
	}

	public static void loadData(Dataset dataset, String TtlFileLocation) {

		// dataset.begin(ReadWrite.WRITE);
		try {
			Model m = dataset.getDefaultModel();
			RDFDataMgr.read(m, TtlFileLocation);
			dataset.commit();
		} finally {
			dataset.end();
		}

	}

	public static String getCandidateKeywords(Dataset dataset, String kw) {
		String queryResults;
		String queryString = "PREFIX ex: <http://example.org/>\n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
				+ "PREFIX text: <http://jena.apache.org/text#>\n" 
				+ "SELECT DISTINCT ?s ?kwLabel ?score \n"
				+ "WHERE {\n" 
				+ "   ( ?s ?score ?literal ) text:query \'"
				+ kw
				+"\' .\n" 
				+ "  ?s rdfs:label ?kwLabel \n"
				+ "  OPTIONAL { ?s rdfs:comment ?comment }\n" 
				+ "}\n" 
				+ "ORDER BY DESC ( ?score )\n" 
				+ "LIMIT 3";

		try {
			org.apache.jena.query.Query q = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.create(q, dataset);
			ResultSet qresults = qexec.execSelect();
			queryResults = ResultSetFormatter.asText(qresults);

		} finally {
		}


		return queryResults;
	}

	@SuppressWarnings("unused")
	public String askLuceneforKeyword(String ontologyURL, String hitKW) throws FileNotFoundException {

		final String DS = ontologyURL;
		final Model config = ModelFactory.createDefaultModel();
		RDFConnection conn = RDFConnectionFactory.connect(DS);
		Dataset ds;

		ds = createCode();
		loadData(ds, DS);

		return getCandidateKeywords(ds, hitKW);

	}

}
