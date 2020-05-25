# A semi-automatic approach for ontology enrichment using external knowledge bases

## Description:

A semi-automatic approach for ontology enrichment based on the usage of three external knowledge bases: DBpedia, WikiData, and NELL. The main aim of this approach is to suggest a set of classes, relations and instances to be used as a help during the evolution process of an ontology. 


## Usage:

1. Download and place the NELL knowledge base file inside the resources folder
   - `NELL.08m.1040.esv.csv:` http://rtw.ml.cmu.edu/resources/results/08m/NELL.08m.1040.cesv.csv.gz


2. To compile and run the jar file, run the following command:
   - Run `mvn package`
   - Inside `target` folder, run the following command to start: `java -jar OntologyEnrichment.jar help` .


## To cite please refer to:

- Qawasmeh, Omar, Maxime Lefranois, Antoine Zimmermann, and Pierre Maret. "Computer-assisted Ontology Construction System: Focus on Bootstrapping Capabilities." In European Semantic Web Conference, pp. 60-65. Springer, Cham, 2018.

