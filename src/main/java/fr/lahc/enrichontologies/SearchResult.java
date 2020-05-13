/**
 * 
 */
package fr.lahc.enrichontologies;

import java.util.List;


/**
 * @author Omar Qawasmeh
 * 
 * @organization Ecole des Mines de Saint Etienne
 */
public class SearchResult {
    
    public String search;
    public int count;
    public int offset;
    public List<SearchResultSummary> results;
    
    public static class SearchResultSummary {
        public String score;
        public String uri;
        public String label;
        public String comment;
        public String graph;
    }
}