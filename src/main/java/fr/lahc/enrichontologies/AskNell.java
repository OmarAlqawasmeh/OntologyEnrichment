/**
 * 
 */
package fr.lahc.enrichontologies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.atlas.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.lahc.outputFormatter.TestCase;

/**
 * @author Omar Qawasmeh
 * 
 * @organization Ecole des Mines de Saint Etienne
 */
public class AskNell {
	static final Logger LOG = LoggerFactory.getLogger(AskNell.class);

	private static final String DBResults = null;

	public static void getIndividuals(String entity, TestCase tstcase) throws IOException {
		List<String> listofIndividuals = new ArrayList<String>();
		
		
		
		LOG.info("inside NELL");

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
								LOG.info(AllIndividuals);
								listofIndividuals.add(line[6] + "," + line[2]);
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
		
		tstcase.setInstances(listofIndividuals);
		
		LOG.info("Finish extracting from NELL");
	}
}
