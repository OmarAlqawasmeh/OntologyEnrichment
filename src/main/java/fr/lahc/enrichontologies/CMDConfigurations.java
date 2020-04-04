/**
 * 
 */
package fr.lahc.enrichontologies;

/**
 * @author Omar Qawasmeh
 * 
 * @organization Ecole des Mines de Saint Etienne
 */
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author Omar Qawasmeh
 *
 */
public class CMDConfigurations {

	public static final String ARG_HELP = "h";
	public static final String ARG_HELP_LONG = "help";
	public static final String ARG_HELP_MAN = "Show help";
	
	public static final String ARG_PUSH = "p";
	public static final String ARG_PUSH_LONG = "push";
	public static final String ARG_PUSH_MAN = "Initialize and push an ontology to fuseki server";

	public static final String ARG_INCLUDE_CLASSES = "c";
	public static final String ARG_DIRECTORY_LONG = "class";
	public static final String ARG_INCLUDE_CLASSES_MAN = "Include classes";

	public static final String ARG_INCLUDE_RELATIONS = "r";
	public static final String ARG_INCLUDE_RELATIONS_LONG = "relations";
	public static final String ARG_INCLUDE_RELATIONS_MAN = "Include relations";

	public static final String ARG_INCLUDE_INSTANCES= "i";
	public static final String ARG_INCLUDE_INSTANCES_LONG = "instances";
	public static final String ARG_INCLUDE_INSTANCESY_MAN = "Include instances";
	
	

	public static CommandLine parseArguments(String[] args) throws ParseException {
		DefaultParser commandLineParser = new DefaultParser();
		CommandLine cl = commandLineParser.parse(getCMDOptions(), args);
		return cl;
	}

	public static Options getCMDOptions() {
		return new Options().addOption(ARG_HELP, ARG_HELP_LONG, false, ARG_HELP_MAN)
				.addOption(ARG_PUSH, ARG_PUSH_LONG, true, ARG_PUSH_MAN)
				.addOption(ARG_INCLUDE_CLASSES, ARG_DIRECTORY_LONG, false, ARG_INCLUDE_CLASSES_MAN)
				.addOption(ARG_INCLUDE_RELATIONS, ARG_INCLUDE_RELATIONS_LONG, false, ARG_INCLUDE_RELATIONS_MAN)
				.addOption(ARG_INCLUDE_INSTANCES, ARG_INCLUDE_INSTANCES_LONG, false, ARG_INCLUDE_INSTANCESY_MAN);
//				.addOption(ARG_IGNORE_TERMS, ARG_IGNORE_TERMS_LONG, false, ARG_IGNORE_TERMS_MAN)
//				.addOption(ARG_IGNORE_GIT, ARG_IGNORE_GIT_LONG, false, ARG_IGNORE_GIT_MAN)
//				.addOption(ARG_NO_SITE, ARG_NO_SITE_LONG, false, ARG_NO_SITE_MAN)
//				.addOption(ARG_INCLUDE_MASTER, ARG_INCLUDE_MASTER_LONG, false, ARG_INCLUDE_MASTER_MAN)
//				.addOption(ARG_VERBOSE, ARG_VERBOSE_LONG, false, ARG_VERBOSE_MAN);
	}

	public static void displayHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar enrich-ontology.jar", getCMDOptions());
		System.exit(-1);
	}

}
