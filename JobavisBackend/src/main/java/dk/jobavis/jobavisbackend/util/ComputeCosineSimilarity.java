package dk.jobavis.jobavisbackend.util;

import java.util.Map;

public class ComputeCosineSimilarity {

// Cosine similarity:
    // cosineSimilarity(A,B) = (A . B) / (||A|| * ||B||)
   public double computeCosine(Map<String,Double> docVector,Map<String,Double> queryVector){
        //"dot product"
       double dot = 0.0;
       for(Map.Entry<String,Double> entryMap:docVector.entrySet()){
           String term = entryMap.getKey();
           double w1 = entryMap.getValue();
           double w2 = queryVector.getOrDefault(term,0.0);
           dot += (w1 * w2);
       }

       //norm

       double normA = 0.0;
       for(double val: docVector.values()){
           normA += val * val;
       }
       normA = Math.sqrt(normA);
       double normB = 0.0;
       for (double val:queryVector.values()){
           normB += val* val;
       }
       normB = Math.sqrt(normB);

       if(normA == 0.0 || normB == 0.0){
           return 0.0;
       }
       return dot/(normA*normB);
   }

}
