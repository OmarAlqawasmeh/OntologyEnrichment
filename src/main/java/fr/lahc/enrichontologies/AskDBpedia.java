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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lahc.outputFormatter.TestCase;

/**
 * @author Omar Qawasmeh
 * 
 * @organization Ecole des Mines de Saint Etienne
 */
public class AskDBpedia {

	static final Logger LOG = LoggerFactory.getLogger(AskDBpedia.class);

	public String toTitleCase(String givenString) {
		String output = givenString.substring(0, 1).toUpperCase() + givenString.substring(1);
		return output;
	}

	public void askDbpedia(String kw, TestCase ts) {
		LOG.info("Extracting general information for the keyword\t \"" + kw+"\"");

		/*
		 * prefix x: <dbpedia.org/ontology/> prefix rdf:
		 * <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX rdfs:
		 * <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbpedia-owl:
		 * <http://dbpedia.org/ontology/>
		 * 
		 * SELECT ?uri ?label ?abstract ?type WHERE {
		 * 
		 * ?uri rdfs:label ?label .
		 * 
		 * ?uri dbpedia-owl:abstract ?abstract .
		 * 
		 * ?uri rdf:type ?type .
		 * 
		 * filter(?label="Wine"@en) .
		 * 
		 * FILTER langMatches( lang(?abstract), 'en') }
		 */

		QueryExecution qe = null;
		String service = "http://dbpedia.org/sparql";

		String Sparql_query =

				"prefix dbpediaont: <http://dbpedia.org/>\n" + "prefix x: <dbpedia.org/ontology/>"
						+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
						+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "

						+ "SELECT ?uri ?label ?abstract ?type ?sub " + "WHERE { " + "?uri rdfs:label ?label  ."
						+ "?uri dbpedia-owl:abstract ?abstract ." + "?uri rdf:type ?type ." + "filter(?label=\"" + kw
						+ "\"@en) ." + "FILTER langMatches( lang(?abstract), 'en') " + "}";

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

				RDFNode lbl = sol.get("?label");
				RDFNode abs = sol.get("?abstract");
				RDFNode uri = sol.get("?uri");
				RDFNode type = sol.get("?type");
				RDFNode subClassOf = sol.get("?sub");

				ts.setKwLabel(lbl.toString());
				ts.setKwType(type.toString());
				ts.setKwAbstract(abs.toString());


			}

		}

		else {

			ts.setKwLabel("null");
			ts.setKwType("null");
			ts.setKwAbstract("null");

			List<String> listStrings = new ArrayList<String>();
			listStrings.add("One");
			listStrings.add("Two");
			listStrings.add("Three");
			listStrings.add("Four");
			System.out.println(listStrings);
			ts.setClasses(listStrings);


		}
LOG.info("Finish extracting general information for the keyword\t \"" + kw+"\"");
	
	}

}
