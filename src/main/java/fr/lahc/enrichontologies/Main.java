package fr.lahc.enrichontologies;

import static fr.lahc.enrichontologies.CMDConfigurations.*;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.xml.bind.JAXBException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
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

public class Main {
	static final Logger LOG = LoggerFactory.getLogger("fr.lahc.enrichontologies" + ".Main");
	static boolean fileSelected = false;
	private static JTextArea txtConsole = new JTextArea("Select an ontology to enrich\n");
	private static JScrollPane scroll = new JScrollPane();
	private static JFrame frame = new JFrame();
	private static JPanel functionalitiesPanel = new JPanel(new GridLayout(0, 4));
	private static JPanel infoPanel = new JPanel();
	private static JPanel textAreaPanel = new JPanel();
	private static String lastPath;
	private static JFileChooser fileChooser;
	private static File directory;
	public static final String LOG_FILE_NAME = directory + File.separator + "output.log";
	private static Initialize pushOntology = new Initialize();
	static boolean initOntology;
	static boolean includeClasses;
	static boolean includeRelations;
	static boolean includeInstances;
	public static boolean isTheConsoleActive = false;

	public static void main(String[] args) throws IOException, ParseException {

		if (isTheConsoleActive == true) {
			openConsole();

		} else {

			CommandLine cl = CMDConfigurations.parseArguments(args);
			if (cl.getOptions().length == 0 || cl.hasOption(ARG_HELP)) {
				CMDConfigurations.displayHelp();
				return;
			}

			initOntology = cl.hasOption(ARG_PUSH);
			includeClasses = cl.hasOption(ARG_INCLUDE_CLASSES);
			includeRelations = cl.hasOption(ARG_INCLUDE_RELATIONS);
			includeInstances = cl.hasOption(ARG_INCLUDE_INSTANCES);

			String dirName = cl.getOptionValue(ARG_PUSH);
			directory = new File(dirName).getCanonicalFile();
			LOG.info(directory.getAbsolutePath());
			pushOntology.pushOntology(directory.getAbsolutePath());
			
			
			if(includeClasses) {
				EnrichOntologies obj= new EnrichOntologies();
				System.out.println(obj.toTitleCase("hello from the other side"));
			}

		}

	}

	private static void setLogAppenders() throws IOException {

		File logFile = new File(directory, LOG_FILE_NAME);
		Layout layout = new PatternLayout("%d{mm:ss,SSS} %t %-5p %c:%L - %m%n");
		org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
		rootLogger.addAppender(new org.apache.log4j.RollingFileAppender(layout, logFile.getAbsolutePath(), false));

	}

	private static void setGUIAppenders() throws IOException {

		JTextAreaAppender jTextAreaAppender = new JTextAreaAppender(txtConsole);

		org.apache.log4j.Logger appenderRootLogger = org.apache.log4j.Logger.getRootLogger();

		Layout GUIlayout = new PatternLayout("%d{mm:ss,SSS} %t %-5p %c:%L - %m%n");

		jTextAreaAppender.setLayout(GUIlayout);

		appenderRootLogger.addAppender(jTextAreaAppender);

	}

	private static boolean openConsole() throws IOException {
		setGUIAppenders();
		setLogAppenders();

		frame = new JFrame("Ontology enrichment");
		frame.setBounds(100, 100, 800, 400);
		SwingUtilities.updateComponentTreeUI(frame);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblWelcomeToSarefpipeline = new JLabel("Ontology enrichment using existing knowledge bases");
		infoPanel.add(lblWelcomeToSarefpipeline);

		frame.getContentPane().add(functionalitiesPanel, BorderLayout.SOUTH);

		JButton startBtn = new JButton("Start the process");

		JButton fileChooserBtn = new JButton("Select an ontology");
		fileChooserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (lastPath != null) {

					fileChooser = new JFileChooser(lastPath);

				} else {
					fileChooser = new JFileChooser();
				}

				// fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = fileChooser.showOpenDialog(frame);
				if (option == JFileChooser.APPROVE_OPTION) {

					directory = fileChooser.getSelectedFile();

					try {
						pushOntology.pushOntology(directory.getAbsolutePath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						LOG.error(e.getMessage());
					}

					fileSelected = true;
					startBtn.setEnabled(true);
					lblWelcomeToSarefpipeline
							.setText("Ontology enrichment using existing knowledge bases for ontology" + directory);

					// lastPath=directory.getParentFile();

					lastPath = directory.getAbsolutePath();

				} else {
					LOG.info("Open command canceled");
				}

			}

		});
		functionalitiesPanel.add(fileChooserBtn);

		startBtn.setEnabled(false);
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		functionalitiesPanel.add(startBtn);

		JLabel emptylbl1 = new JLabel();
		functionalitiesPanel.add(emptylbl1);

		JLabel emptylbl2 = new JLabel();
		functionalitiesPanel.add(emptylbl2);

		JLabel lbl = new JLabel("What you would like to do:");
		functionalitiesPanel.add(lbl);

		JLabel emptylbl3 = new JLabel();
		functionalitiesPanel.add(emptylbl3);

		JLabel emptylbl4 = new JLabel();
		functionalitiesPanel.add(emptylbl4);

		JLabel emptylbl5 = new JLabel();
		functionalitiesPanel.add(emptylbl5);

//		JCheckBox ignoreGit_checkBox = new JCheckBox("No-git");
//		ignoreGit_checkBox.setToolTipText("Only check the current state of the repository");
//		ignoreGit_checkBox.setSelected(true);
//		functionalitiesPanel.add(ignoreGit_checkBox);

		JCheckBox classes_checkBox = new JCheckBox("Suggest new classes to add");
		classes_checkBox.setToolTipText("Do not check the directory itself.\n"
				+ " Only consider the repositories listed in the `.saref-repositories.yml` document.\n"
				+ " Used to generate the website for several extensions.");
		functionalitiesPanel.add(classes_checkBox);

		JCheckBox relation_checkBox = new JCheckBox("Suggest new relations to add");
		relation_checkBox.setToolTipText(
				"Check the master branches of the remote repositories listed in the `.saref-repositories.yml` document");
		functionalitiesPanel.add(relation_checkBox);

		JCheckBox instanses_checkBox = new JCheckBox("Suggest new instanses to add");
		instanses_checkBox.setToolTipText("Do not generate the static website");
		functionalitiesPanel.add(instanses_checkBox);

//		JCheckBox ignoreExamples_checkBox = new JCheckBox("Ignore examples");
//		ignoreExamples_checkBox.setToolTipText("Only check the SAREF extension ontology. Ignore the examples");
//		functionalitiesPanel.add(ignoreExamples_checkBox);
//
//		JCheckBox verbose_checkBox = new JCheckBox("Verbose");
//		verbose_checkBox.setToolTipText("Use verbose mode.\n"
//				+ "(For example, use SPARQL-Generate in --debug-template mode when generating the documentation)");
//		functionalitiesPanel.add(verbose_checkBox);
//
//		JCheckBox ignoreTerms_checkBox = new JCheckBox("Ignore terms");
//		ignoreTerms_checkBox.setToolTipText("Do not generate the website for terms");
//		functionalitiesPanel.add(ignoreTerms_checkBox);

		FlowLayout flowLayout_1 = (FlowLayout) infoPanel.getLayout();
		flowLayout_1.setVgap(20);
		flowLayout_1.setHgap(20);
		frame.getContentPane().add(infoPanel, BorderLayout.NORTH);

		frame.getContentPane().add(textAreaPanel, BorderLayout.CENTER);
		txtConsole.setColumns(70);
		txtConsole.setRows(15);
		txtConsole.setEditable(false);

		textAreaPanel.add(txtConsole);

		scroll = new JScrollPane(txtConsole, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.add(scroll);

		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		return true;
	}

}
