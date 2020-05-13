package fr.lahc.enrichontologies;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;

import fr.lahc.outputFormatter.TestCase;

/**
 * 
 */

/**
 * @author Omar Qawasmeh
 * 
 * @organization Ecole des Mines de Saint Etienne
 */
public class EnrichOntologies {
	static String DBResults = null;

//	public static void main(String args[]) throws IOException {
//
//		askDbpedia(toTitleCase("wine"));
//		askWikidata(getWikidataID("wine"));
//		getIndividuals("wine");
//
//	}

//	public static String toTitleCase(String givenString, TestCase ts) {
//
//		String output = givenString.substring(0, 1).toUpperCase() + givenString.substring(1);
//		
//		ts.setSystemOut(output);
//
//		return output;
//
//	}
//
//	public static void askDbpedia(String cat) {
//		/*
//		 * prefix x: <dbpedia.org/ontology/> prefix rdf:
//		 * <http://www.w3.org/1999/02/22-rdf-syntax-ns#> PREFIX rdfs:
//		 * <http://www.w3.org/2000/01/rdf-schema#> PREFIX dbpedia-owl:
//		 * <http://dbpedia.org/ontology/>
//		 * 
//		 * SELECT ?uri ?label ?abstract ?type WHERE {
//		 * 
//		 * ?uri rdfs:label ?label .
//		 * 
//		 * ?uri dbpedia-owl:abstract ?abstract .
//		 * 
//		 * ?uri rdf:type ?type .
//		 * 
//		 * filter(?label="Wine"@en) .
//		 * 
//		 * FILTER langMatches( lang(?abstract), 'en') }
//		 */
//		String x = cat;
//
//		QueryExecution qe = null;
//		String service = "http://dbpedia.org/sparql";
//
//		String Sparql_query =
//
//				"prefix dbpediaont: <http://dbpedia.org/>\n" + "prefix x: <dbpedia.org/ontology/>"
//						+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
//						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//						+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
//
//						+ "SELECT ?uri ?label ?abstract ?type ?sub " + "WHERE { " + "?uri rdfs:label ?label  ."
//						+ "?uri dbpedia-owl:abstract ?abstract ." + "?uri rdf:type ?type ." + "filter(?label=\"" + x
//						+ "\"@en) ." + "FILTER langMatches( lang(?abstract), 'en') " + "}";
//
//		ParameterizedSparqlString query = new ParameterizedSparqlString(Sparql_query);
//		Query q = QueryFactory.create(query.toString());
//		qe = QueryExecutionFactory.sparqlService(service, q);
//		int k = 0;
//		ResultSet results = qe.execSelect();
//		int counter = 0;
//		String DBlabel = null;
//		String DBabs = null;
//		String DBuri = null;
//		String DBtype = null;
//		String FinalOutDBpedia = null;
//		if (results.hasNext()) {
//			for (; results.hasNext();) {
//				counter++;
//				QuerySolution sol = (QuerySolution) results.next();
//
//				RDFNode lbl = sol.get("?label");
//				RDFNode abs = sol.get("?abstract");
//				RDFNode uri = sol.get("?uri");
//				RDFNode type = sol.get("?type");
//				RDFNode subClassOf = sol.get("?sub");
//
//				DBlabel = lbl.toString();
//				DBabs = abs.toString();
//				DBuri = uri.toString();
//				DBtype = type.toString();
//
//			}
//
//		}
//
//		else {
//
//			DBlabel = "No output";
//			DBabs = "No output";
//			DBuri = "No output";
//			DBtype = "No output";
//			DBResults = "NoOutput";
//			String ResultChecker = "NO";
//
//		}
//		FinalOutDBpedia = FinalOutDBpedia + "<label>" + DBlabel + "</label>" + "\n" + "<abstract>" + DBabs
//				+ "</abstract>" + "\n" + "<type>" + DBtype + "</type>\n";
//
//		System.out.println(FinalOutDBpedia);
//	}

//	public static void askWikidata(String entity) {
//		boolean flag = true;
//		String wikiDataResults = "null,";
//		String ResultChecker = null;
//		String FinalWikidataResults = null;
//		if (entity != "null") {
//
//			String x = entity;
//
//			RDFNode lbl = null;
//			RDFNode lbl2 = null;
//			QueryExecution qe = null;
//			String service = "https://query.wikidata.org/sparql";
//			String Sparql_query =
//					// Modify the query
//					"prefix dbpediaont: <http://dbpedia.org/>\n"
//							+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
//							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//							+ "PREFIX wd: <http://www.wikidata.org/entity/> "
//							+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
//							+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
//
//							+ "SELECT ?s ?desc WHERE " + "{ ?s wdt:P279 wd:" + x + ". OPTIONAL" + " {"
//							+ " ?s rdfs:label " + "?desc filter (lang(?desc) = \"en\") ." + " }" + " }";
//
//			try {
//
//				ParameterizedSparqlString query = new ParameterizedSparqlString(Sparql_query);
//				Query q = QueryFactory.create(query.toString());
//				qe = QueryExecutionFactory.sparqlService(service, q);
//				int k = 0;
//				ResultSet results = qe.execSelect();
//				int counter = 0;
//				if (results.hasNext()) {
//					for (; results.hasNext();) {
//						counter++;
//						QuerySolution sol = (QuerySolution) results.next();
//						lbl = sol.get("?s");
//						lbl2 = sol.get("?desc");
//						wikiDataResults = wikiDataResults + lbl2.toString().replaceAll("@en", "") + ",";
//						if (lbl2.toString() == null) {
//							wikiDataResults = "No output";
//						}
//					}
//				} else {
//					ResultChecker = "NO";
//				}
//
//			} catch (Exception e) {
//
//			} finally {
//				lbl = null;
//				lbl2 = null;
//				flag = true;
//			}
//		}
//
//		String wikiOutput[] = wikiDataResults.split(",");
//		for (int i = 1; i < wikiOutput.length; i++) {
//			FinalWikidataResults = FinalWikidataResults + "<wikidata>" + wikiOutput[i] + "</wikidata>" + "\n";
//		}
//		System.out.println(FinalWikidataResults);
//	}
//
//	public static String getWikidataID(String Category) {
//
//		String x = Category;
//		RDFNode lbl = null;
//		String ID = null;
//
//		QueryExecution qe = null;
//
//		String service = "https://query.wikidata.org/sparql";
//
//		String Sparql_query =
//
//				"prefix dbpediaont: <http://dbpedia.org/>\n" + "prefix x: <dbpedia.org/ontology/>"
//						+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
//						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//						+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
//						+ "PREFIX wd: <http://www.wikidata.org/entity/>"
//						+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
//
//						+ "SELECT ?item WHERE { ?item rdfs:label \"" + x + "\"@en}";
//
//		try {
//			ParameterizedSparqlString query = new ParameterizedSparqlString(Sparql_query);
//
//			Query q = QueryFactory.create(query.toString());
//
//			qe = QueryExecutionFactory.sparqlService(service, q);
//
//			int k = 0;
//			ResultSet results = qe.execSelect();
//			int counter = 0;
//			for (; results.hasNext();) {
//				counter++;
//				QuerySolution sol = (QuerySolution) results.next();
//				lbl = sol.get("?item");
//				break;
//			}
//
//			ID = lbl.toString().replaceAll("http://www.wikidata.org/entity/", "");
//
//		}
//
//		catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return ID;
//
//	}

	public static void getIndividuals(String entity) throws IOException {
		
//		FILENAME ="/media/omar/SanDisk_MyData/PhD_Projects_Data/NELL/nellFiles/relations.csv";//args[2]; //"/media/omar/SanDisk_MyData/PhD_Projects_Data/NELL/nellFiles/relations.csv";
//		FILENAME2="/media/omar/SanDisk_MyData/PhD_Projects_Data/NELL/nellFiles/categoryNELL.csv";//args[3];
		
		
		
///media/omar/SanDisk_MyData/PhD_Projects_Data/NELL/nellFiles/
		String IndividualsFileNELL = "/data/PhD/NELL/nellFiles/NELL.08m.1040.esv.csv";
		String AllIndividuals = null;
		String candidateEntityFromNELL = null;
		BufferedReader br = null;
		FileReader fr = null;

		PrintWriter writer = new PrintWriter("/home/omar/Desktop/initconstTool/output.xml", "UTF-8");
		System.out.println("start");

		String arr[] = { entity };

		for (int i = 0; i < arr.length; i++) {

			String line[] = null;
			int c = 0;
			int k = 0;
			try {

				fr = new FileReader(IndividualsFileNELL);
				br = new BufferedReader(fr);
				String sCurrentLine;
				br = new BufferedReader(new FileReader(IndividualsFileNELL));
				while ((sCurrentLine = br.readLine()) != null) {
					if (sCurrentLine.contains("concept:" + arr[i])
							&& sCurrentLine.contains("concept:haswikipediaurl")) {

						line = sCurrentLine.split("	");

						if (Double.parseDouble(line[4]) > 0.94 && line[10].contains(arr[i])) {

							if (c < 1000000) {

								AllIndividuals = AllIndividuals + "<individual>" + line[6] + "," + line[2]
										+ "</individual>\n";

//										+ "<individualURI>" + line[2] + "</individualURI>\n" + "<confidence>" + line[4]
//										+ "</confidence>\n";

								if (DBResults == "NoOutput") {

									line[2] = line[2].replaceAll("http://en.wikipedia.org/wiki/", "");
									line[2] = line[2].replaceAll("%20", " ");

									candidateEntityFromNELL = candidateEntityFromNELL + "," + line[2];

								}
								c++;

							} else
								break;

						}

					}

				}
			}

			catch (IOException e) {

				e.printStackTrace();

			}

			finally {
				br.close();
			}

		}
System.out.println(candidateEntityFromNELL);
	}
	
	
//	public static String getLabelFromID(String entity) {
//		String ID = entity.replaceAll("/entity/", "");
//
//		RDFNode label = null;
//
//		QueryExecution qe = null;
//
//		String service = "https://query.wikidata.org/sparql";
//
//		String Sparql_query = "prefix dbpediaont: <http://dbpedia.org/>\n"
//				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
//				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//				+ "PREFIX wd: <http://www.wikidata.org/entity/>" + "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
//				+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
//				+ "PREFIX wikibase: <http://wikiba.se/ontology#>" + "SELECT ?label WHERE { " + " wd:" + ID
//				+ " rdfs:label ?label .  FILTER (langMatches( lang(?label), \"en\" ) ) }";
//
//		try {
//
//			ParameterizedSparqlString query = new ParameterizedSparqlString(Sparql_query);
//
//			Query q = QueryFactory.create(query.toString());
//
//			qe = QueryExecutionFactory.sparqlService(service, q);
//
//			int k = 0;
//			ResultSet results = qe.execSelect();
//			int counter = 0;
//			for (; results.hasNext();) {
//				counter++;
//				QuerySolution sol = (QuerySolution) results.next();
//				label = sol.get("?label");
//				break;
//
//			}
//
//		}
//
//		catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return label.toString();
//	}

//	public static void getCountForProperty(String entity) {
//
//		if (entity != "null") {
//
//			String x = entity;
//			RDFNode property = null;
//			RDFNode propCount = null;
//			QueryExecution qe = null;
//			String service = "https://query.wikidata.org/sparql";
//			String Sparql_query =
//
//					"prefix dbpediaont: <http://dbpedia.org/>\n"
//							+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
//							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
//							+ "PREFIX wd: <http://www.wikidata.org/entity/>"
//							+ "PREFIX wdt: <http://www.wikidata.org/prop/direct/> "
//							+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
//							+ "PREFIX wikibase: <http://wikiba.se/ontology#>"
//
//							+ "SELECT ?property ?count WHERE {" + "SELECT ?property (COUNT(?item) AS ?count) WHERE {"
//							+ " ?item ?statement" + " wd:" + x + " ."
//							+ " ?property wikibase:statementProperty ?statement ." + "} GROUP BY ?property "
//							+ "} ORDER BY DESC(?count)";
//
//
//			try {
//				ParameterizedSparqlString query = new ParameterizedSparqlString(Sparql_query);
//				Query q = QueryFactory.create(query.toString());
//				qe = QueryExecutionFactory.sparqlService(service, q);
//				int k = 0;
//				ResultSet results = qe.execSelect();
//				int counter = 0;
//				if (results.hasNext()) {
//					for (; results.hasNext();) {
//						counter++;
//
//						QuerySolution sol = (QuerySolution) results.next();
//
//						
//						property = sol.get("?property");
//						
//
//						propCount = sol.get("?count");
//						
//						wikiDataResultsForRelations = wikiDataResultsForRelations + property.toString() + ","
//								+ propCount.toString() + ",,";
//
//					}
//				} else {
//					ResultChecker = "NO";
//				}
//
//			} catch (Exception e) {
//
//			} finally {
//				property = null;
//				propCount = null;
//				flag = true;
//			}
//
//
//		}
//
//
//		wikiDataResultsForRelations = wikiDataResultsForRelations.replaceAll("null,", "");
//
//
//		String wikiOutput[] = wikiDataResultsForRelations.split(",,");
//
//		for (int i = 0; i < wikiOutput.length; i++) {
//
//			String labelforID = getLabelFromID(
//					wikiOutput[i].substring(wikiOutput[i].indexOf("/entity/"), wikiOutput[i].indexOf(",")));
//
//
//			relationSetForEntity = relationSetForEntity + "<Relations>" + labelforID + ","
//					+ wikiOutput[i].replaceAll("\\^\\^http://www.w3.org/2001/XMLSchema#integer", "") + "</Relations>"
//					+ "\n";
//
//
//		}
//
//	}

}
