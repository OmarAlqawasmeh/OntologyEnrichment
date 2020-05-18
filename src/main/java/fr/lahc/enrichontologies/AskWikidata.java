/**
 * 
 */
package fr.lahc.enrichontologies;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.logging.Log;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lahc.outputFormatter.*;

/**
 * @author Omar Qawasmeh
 * 
 * @organization Ecole des Mines de Saint Etienne
 */
public class AskWikidata {
	static final Logger LOG = LoggerFactory.getLogger(AskWikidata.class);

	public void askWikidataForClasses(String entity, TestCase tstcase) {

		LOG.info("preparing list of suggested classes for the keyword \t" + entity);

		boolean flag = true;
		String wikiDataResults = "null,";
		String ResultChecker = null;
		String FinalWikidataResults = null;

		List<String> listofClasses = new ArrayList<String>();

		if (entity != "null") {

			String x = entity;

			RDFNode lbl = null;
			RDFNode lbl2 = null;
			QueryExecution qe = null;
			String service = "https://query.wikidata.org/sparql";
			String Sparql_query =
					// Modify the query
					"prefix dbpediaont: <http://dbpedia.org/>\n"
							+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
							+ "PREFIX wd: <http://www.wikidata.org/entity/> "
							+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
							+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "

							+ "SELECT ?s ?desc WHERE " + "{ ?s wdt:P279 wd:" + x + ". OPTIONAL" + " {"
							+ " ?s rdfs:label " + "?desc filter (lang(?desc) = \"en\") ." + " }" + " }";

			try {

				ParameterizedSparqlString query = new ParameterizedSparqlString(Sparql_query);
				Query q = QueryFactory.create(query.toString());
				qe = QueryExecutionFactory.sparqlService(service, q);
				int k = 0;
				ResultSet results = qe.execSelect();
				int counter = 0;
				if (results.hasNext()) {
					for (; results.hasNext();) {
						counter++;
						QuerySolution sol = (QuerySolution) results.next();
						lbl = sol.get("?s");
						lbl2 = sol.get("?desc");

						wikiDataResults = wikiDataResults + lbl2.toString().replaceAll("@en", "") + ",";

						listofClasses.add(lbl2.toString());

						if (lbl2.toString() == null) {
							wikiDataResults = "No output";
						}
					}
				} else {
					ResultChecker = "NO";
					listofClasses.add("null");
				}

			} catch (Exception e) {

			} finally {
				lbl = null;
				lbl2 = null;
				flag = true;
			}
		}

		String wikiOutput[] = wikiDataResults.split(",");
		for (int i = 1; i < wikiOutput.length; i++) {
			FinalWikidataResults = FinalWikidataResults + "<wikidata>" + wikiOutput[i] + "</wikidata>" + "\n";
		}
		System.out.println(FinalWikidataResults);
		tstcase.setClasses(listofClasses);
	}

	public String getWikidataID(String entity) {

		String lbl = null;
		String Sparql_query =

				"prefix dbpediaont: <http://dbpedia.org/>\n" + "prefix x: <dbpedia.org/ontology/>"
						+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
						+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
						+ "PREFIX wd: <http://www.wikidata.org/entity/>"
						+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "

						+ "SELECT ?item WHERE { ?item rdfs:label \"" + entity + "\"@en}";

		QueryExecution qExe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", Sparql_query);
		ResultSet result = qExe.execSelect();
		while (result.hasNext()) {
			QuerySolution s = result.next();
			lbl = s.get("?item").toString();
			break;
		}
		qExe.close();

		return lbl.toString().replaceAll("http://www.wikidata.org/entity/", "");

	}

	public static String getLabelFromID(String entity) {
		String ID = entity.replaceAll("http://www.wikidata.org/entity/", "");
		String lbl = null;
		String Sparql_query = "prefix dbpediaont: <http://dbpedia.org/>\n"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX wd: <http://www.wikidata.org/entity/>" + "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ "PREFIX wikibase: <http://wikiba.se/ontology#>" + "SELECT ?label WHERE { " + " wd:" + ID
				+ " rdfs:label ?label .  FILTER (langMatches( lang(?label), \"en\" ) ) }";

		QueryExecution qExe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", Sparql_query);
		ResultSet result = qExe.execSelect();
		while (result.hasNext()) {
			QuerySolution s = result.next();
			lbl = s.get("?label").toString();
		}
		qExe.close();

		return lbl;

	}

	public void getProp(String kw, TestCase tstcase) {
		List<String> listofProp = new ArrayList<String>();

		String Sparql_query =

				"prefix dbpediaont: <http://dbpedia.org/>\n"
						+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
						+ "PREFIX wd: <http://www.wikidata.org/entity/>"
						+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
						+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
						+ "PREFIX wikibase: <http://wikiba.se/ontology#>"

						+ "SELECT ?property ?count WHERE {" + "SELECT ?property (COUNT(?item) AS ?count) WHERE {"
						+ " ?item ?statement" + " wd:" + kw + " ."
						+ " ?property wikibase:statementProperty ?statement ." + "} GROUP BY ?property "
						+ "} ORDER BY DESC(?count)";
		QueryExecution qExe = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", Sparql_query);
		ResultSet result = qExe.execSelect();
		while (result.hasNext()) {
			QuerySolution s = result.next();
			s.get("?property").toString();

			listofProp.add(getLabelFromID(s.get("?property").toString()));
		}
		qExe.close();
		tstcase.setRelations(listofProp);

	}
}
