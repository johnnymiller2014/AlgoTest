package score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class TfIdfScore {
	private Map<String,SortedSet<DocPostings>> invertedIndex = new TreeMap<String,SortedSet<DocPostings>>();
	private Map<String,Double> idf=new TreeMap<String,Double>();
	int documentsCount=0;
	public TfIdfScore(){
	
		String text="New York Times";
		int addedDocId=1;
		addDocToIndex(text, addedDocId);
		text="New York Post";
		addedDocId=2;
		addDocToIndex(text, addedDocId);
		text="Los Angeles Times";
		addedDocId=3;
		addDocToIndex(text, addedDocId);
		updateIDFWeights();
	}
	private void addDocToIndex(String text, int addedDocId) {
		String[] words=tokenizeDocument(text);
		Map<String,Integer> termsToFrequencyInThisDoc = new HashMap<String,Integer>();
		countTermsFrequencesInText(words, termsToFrequencyInThisDoc);
		for (Map.Entry<String, Integer> entry :termsToFrequencyInThisDoc.entrySet()){
			String word=entry.getKey();
			if(!this.invertedIndex.containsKey(word)){
				this.invertedIndex.put(word, new TreeSet<DocPostings>());
			}
			SortedSet<DocPostings> postings=this.invertedIndex.get(word);
			Iterator<DocPostings> it = postings.iterator();
			DocPostings current;
			boolean found=false;
			while(it.hasNext()) {
				current = it.next();
				if(current.docId==addedDocId){
					found=true;
					current.termFrequency+=entry.getValue();					
				}
			}
			if(!found){
				postings.add(new DocPostings(addedDocId, entry.getValue()));
			}
		}
		this.documentsCount++;


	}
	private void countTermsFrequencesInText(String[] words, Map<String, Integer> termsToFrequencyInThisDoc) {
		for( String word :words){
			if(!termsToFrequencyInThisDoc.containsKey(word)){
				termsToFrequencyInThisDoc.put(word, 1);
				continue;
			}
			int freq = termsToFrequencyInThisDoc.get(word);
			termsToFrequencyInThisDoc.replace(word, (freq+1));
			
		}
	}
	private String[] tokenizeDocument(String text) {
		return text.toLowerCase().split("[ .,;:]+");
	}
	private void updateIDFWeights() {
		for(Entry<String, SortedSet<DocPostings>> entry: this.invertedIndex.entrySet()){
			String word=entry.getKey();
			int numOfDocsHavinthThisTerm=this.invertedIndex.get(word).size();
			double idfWeight= (double)documentsCount/(double)numOfDocsHavinthThisTerm;
			idfWeight=Math.log(idfWeight) / Math.log(2);
			idf.put(entry.getKey(), idfWeight);
		}
	}
public Double getCosineSimiliarity(String searchQuery,int DocId){
	
		//gets terms from query
		String[] terms = this.tokenizeDocument(searchQuery);
		
		//adjust terms weight per terms frequency in query and in collection
		//adjust terms weight per IDFs
		Map<String,Integer> termsToFrequencyInThisQuery = new HashMap<String,Integer>();
		countTermsFrequencesInText(terms, termsToFrequencyInThisQuery);
		Map<String, Double> termsWeightsInQuery = calcTermsWeigtsPerTFIDF(termsToFrequencyInThisQuery);

		//calculate "length" of query terms vector
		double queryLength =calculateVectorLength(termsWeightsInQuery);
		String[] termsInDoc=getMatchedTermsForDocAndQuery(DocId,searchQuery);
		
		//repeat same process for document we want compare to query
		
		Map<String,Integer> termsToFrequencyInThisDoc = new HashMap<String,Integer>();
		countTermsFrequencesInText(termsInDoc, termsToFrequencyInThisDoc);
		Map<String, Double> termsWeightsInDoc = calcTermsWeigtsPerTFIDF(termsToFrequencyInThisDoc);

		//calculate "length" of query terms vector
		double docLength =calculateVectorLength(termsWeightsInQuery);
		//calc cosineSimiliarity by division of inner product by multiplication of lengths
		double totalLength=docLength*queryLength;
		double innerProduct=0;
		for(Entry<String, Double> entry  :termsWeightsInQuery.entrySet()){
			//does the doc have this term? if not - add zero
			if(termsWeightsInDoc.containsKey(entry.getKey())){
				innerProduct+=entry.getValue()*termsWeightsInDoc.get(entry.getKey()).doubleValue();
			}
		}
		double cosineSimiliarity=innerProduct/totalLength;
		return cosineSimiliarity;
		//repeat all above for all documents
		//sort docs by cosine Similiarity
		
	}
	/**
	 * @todo REMOVE HARDCODE
	 * @param docId
	 * @param query
	 * @return
	 */
	private String[] getMatchedTermsForDocAndQuery(int docId,String query){

		List<String> returns = new ArrayList<String>();
		String words[] = new HashSet<String>(Arrays.asList(this.tokenizeDocument(query))).toArray(new String[0]);

		for(int i=0;i<words.length;++i){
			String word=words[i];
			SortedSet<DocPostings> postings= this.invertedIndex.get(word);
			for(DocPostings doc: postings){
				if(docId==doc.docId){
					returns.add(word);
				}
			}
		}
		String[] array = returns.toArray(new String[0]);
		return array;
	}
	private double calculateVectorLength(Map<String, Double> termsWeightsInQuery) {
		double vectorLength=0;
		for (Entry<String, Double> weight:termsWeightsInQuery.entrySet()){
			vectorLength+=Math.pow(weight.getValue(), 2);
		}
		vectorLength=Math.sqrt(vectorLength);
		return vectorLength;
	}
	private Map<String, Double> calcTermsWeigtsPerTFIDF(Map<String, Integer> termsToFrequencyInThisQuery) {
		Map<String,Double> termsWeightsInQuery = new HashMap<String,Double>();
		for(Entry<String, Integer> word:termsToFrequencyInThisQuery.entrySet()){
			double TermFreqInQuery=termsToFrequencyInThisQuery.get(word.getKey()).intValue();
			double TermFreqInIndex=this.invertedIndex.get(word.getKey()).size();
			double termWeight=TermFreqInQuery/TermFreqInIndex;
			termWeight*=this.idf.get(word.getKey()).doubleValue();
			termsWeightsInQuery.put(word.getKey(), termWeight);
		}
		return termsWeightsInQuery;
	}
	public static void main(String argv[]){
		TfIdfScore score = new TfIdfScore();
		score.printTestDataScore("Los Angeles Post");
	}
	public void printTestDataScore(String q){
		SortedMap<Double,Integer> scoresToDocs=new TreeMap<Double,Integer>(); 
		scoresToDocs.put(getCosineSimiliarity(q,1),1);
		scoresToDocs.put(getCosineSimiliarity(q,2),2);
		scoresToDocs.put(getCosineSimiliarity(q,3),3);
		System.out.println(scoresToDocs.toString());
	}
}
/**
 * @todo add document length, for normalization
 * @author philip
 *
 */
class DocPostings implements Comparable<DocPostings>{
	public int docId;
	public int termFrequency;
	public DocPostings(int docId,int termFrequency){
		this.docId=docId;
		this.termFrequency=termFrequency;
	}
	@Override
	public int compareTo(DocPostings o) {
		return this.docId-o.docId;
	}
	
	
}
class Document{
	private int id;
	private String text;
	public Document(int id, String text){
		
	}
	public int getID(){
		return this.id;
	}
	public String getText()
	{
		return this.text;
	}
	
}
