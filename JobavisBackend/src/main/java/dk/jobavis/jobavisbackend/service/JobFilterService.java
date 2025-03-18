package dk.jobavis.jobavisbackend.service;
import dk.jobavis.jobavisbackend.dto.JSearchResponse;
import dk.jobavis.jobavisbackend.dto.JobData;
import dk.jobavis.jobavisbackend.util.ComputeCosineSimilarity;
import dk.jobavis.jobavisbackend.util.JobDataScore;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * ****COMMENTS AND DOCUMENTATION BY CHATGBT****
 * Service for filtering and ranking job postings using a TF-IDF based textual analysis.
 *
 * <p>This service converts a list of job postings (each represented by a {@link JobData} object)
 * into documents by concatenating the job title and description. It then tokenizes these documents,
 * computes term frequencies and document frequencies, and constructs TF-IDF vectors for each document.
 * Similarly, it builds a TF-IDF vector for a user-provided query (which may include keywords and query text).
 * Finally, it calculates the cosine similarity between each job's TF-IDF vector and the query vector,
 * sorts the jobs in descending order by similarity, and returns the sorted list.
 */
@Service
public class JobFilterService {
    /**
     * Filters and ranks a list of job postings using a TF-IDF approach.
     *
     * <p>This method performs the following steps:
     * <ol>
     *   <li>Builds a document for each job by concatenating its title and description, and converts it to lowercase.</li>
     *   <li>Tokenizes each document into individual words using whitespace as the delimiter.</li>
     *   <li>Computes the term frequency (TF) for each token in each document and the document frequency (DF)
     *       for each unique token across all documents.</li>
     *   <li>Constructs a TF-IDF vector for each document based on its term frequencies and the overall document frequencies.</li>
     *   <li>Constructs a TF-IDF vector for the user query by tokenizing and counting tokens in the query string.</li>
     *   <li>Calculates the cosine similarity between the TF-IDF vector of each job and the query vector using
     *       a helper class ({@link ComputeCosineSimilarity}).</li>
     *   <li>Sorts the jobs in descending order by their cosine similarity score.</li>
     * </ol>
     *
     * @param jobList the list of job postings to be ranked
     * @param keyWordsAndQuery a combined string containing both the query and any additional keywords;
     *                          this text is used to compute the query's TF-IDF vector for relevance scoring
     * @return a list of {@link JobData} objects sorted by their relevance to the query,
     *         with the most relevant jobs first
     */
//comments due to the complexity of this subject
    public JSearchResponse tfidfFilter(List<JobData> jobList, String keyWordsAndQuery) {
        // 1. Build documents from job title + job description (all in lowercase)
        List<String> documents = jobList.stream()
                .map(job -> (job.getJob_title() + " " + job.getJob_description()).toLowerCase())
                .toList();

        // 2. Tokenize the documents into lists of words
        List<List<String>> tokenizedDocs = documents.stream()
                .map(doc -> Arrays.asList(doc.split("\\s+")))
                .toList();

        // 3. Compute document frequency for each term across all documents
        Map<String, Integer> docFrequency = new HashMap<>();
        List<Map<String, Integer>> termCountsPerDoc = new ArrayList<>();

        for (List<String> tokens : tokenizedDocs) {
            Map<String, Integer> termCount = new HashMap<>();
            Set<String> uniqueTokensInThisDoc = new HashSet<>();
            for (String token : tokens) {
                termCount.put(token, termCount.getOrDefault(token, 0) + 1);
                uniqueTokensInThisDoc.add(token);
            }
            termCountsPerDoc.add(termCount);
            for (String uniqueToken : uniqueTokensInThisDoc) {
                docFrequency.put(uniqueToken, docFrequency.getOrDefault(uniqueToken, 0) + 1);
            }
        }

        // 4. Build a TF-IDF vector for each document
        List<Map<String, Double>> docVectors = new ArrayList<>();
        int totalDocs = tokenizedDocs.size();
        for (int i = 0; i < tokenizedDocs.size(); i++) {
            Map<String, Integer> termCount = termCountsPerDoc.get(i);
            int docSize = tokenizedDocs.get(i).size();
            Map<String, Double> tfidfVector = new HashMap<>();
            for (Map.Entry<String, Integer> entryMap : termCount.entrySet()) {
                String term = entryMap.getKey();
                int count = entryMap.getValue();
                double tf = (double) count / docSize;
                double idf = Math.log((double) totalDocs / docFrequency.get(term));
                tfidfVector.put(term, tf * idf);
            }
            docVectors.add(tfidfVector);
        }

        // 5. Build TF-IDF vector for the user query
        String[] queryTokens = keyWordsAndQuery.toLowerCase().split("\\s+");
        Map<String, Integer> queryTermCount = new HashMap<>();
        for (String token : queryTokens) {
            queryTermCount.put(token, queryTermCount.getOrDefault(token, 0) + 1);
        }
        int querySize = queryTokens.length;
        Map<String, Double> queryVector = new HashMap<>();
        for (Map.Entry<String, Integer> entryMap : queryTermCount.entrySet()) {
            String term = entryMap.getKey();
            int count = entryMap.getValue();
            double tf = (double) count / querySize;
            double dfVal = docFrequency.getOrDefault(term, 1);
            double idf = Math.log((double) totalDocs / dfVal);
            queryVector.put(term, tf * idf);
        }

        // 6. Compute cosine similarity between each document vector and the query vector
        ComputeCosineSimilarity ccs = new ComputeCosineSimilarity();
        List<JobDataScore> scoredDocs = new ArrayList<>();
        for (int i = 0; i < jobList.size(); i++) {
            double score = ccs.computeCosine(docVectors.get(i), queryVector);
            scoredDocs.add(new JobDataScore(jobList.get(i), score));
        }
        // Sort jobs in descending order based on their similarity score
        scoredDocs.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        // 7. Extract the sorted list of JobData
        List<JobData> sortedJobData = scoredDocs.stream()
                .map(JobDataScore::getJobData)
                .toList();

        // 8. Build a JSearchResponse with the sorted job postings.
        JSearchResponse response = new JSearchResponse();
        response.setData(sortedJobData);


        return response;
    }

}
