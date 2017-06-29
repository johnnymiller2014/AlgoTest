package me.lab.tree.trie;

import java.util.HashMap;
import java.util.Map;

public class Trie{
	private  Map<Character, TrieNode> rootNodes = new HashMap<Character, TrieNode>();
	public Trie(){
		
	}
	public void add(char []wordChars){
		 Map<Character, TrieNode> currentNodes=rootNodes;
		  
		  for ( int i=0 ; i<wordChars.length;i++){
			    char symbol= wordChars[i];
				if (currentNodes.containsKey(symbol)){
					//do nothing
				}else{
					currentNodes.put(symbol,new TrieNode());
				}
				if(i+1 == wordChars.length){
					currentNodes.get(symbol).setEndOfWord(true);
				}else{ 
					currentNodes = currentNodes.get(symbol).getChilds();
				}
			}
	}
	public String[] getWordsByPrefix(char []wordChars){
		
		 Map<Character, TrieNode> currentNodes=rootNodes;
		  boolean found= false;
		  for ( int i=0; i<wordChars.length;++i){
			   char symbol= wordChars[i] ;
		  		if(!currentNodes.containsKey(symbol)){
		  			break;
		  		}
		  		if(i+1== wordChars.length){
					found = true;
				}else{ 
					currentNodes = currentNodes.get(symbol).getChilds();
				}
		  }
		  if(!found){
			  return new String[]{};
		  }else{
			 
			  return null;
		  }
		
	}
	public boolean contains(char []wordChars){
		  boolean found=false;
		  Map<Character, TrieNode> currentNodes=rootNodes;
		  
		  for ( int i=0; i<wordChars.length;++i){
			   char symbol= wordChars[i] ;
		  		if(!currentNodes.containsKey(symbol)){
		  			break;
		  		}
		  		if(i+1== wordChars.length){
					found = true;
				}else{ 
					currentNodes = currentNodes.get(symbol).getChilds();
				}
		  }
		  return found;
	}
	class TrieNode{
		private boolean isEndOfWord = false;
		char symbol;
		private Map<Character, TrieNode> childs = new HashMap<Character, TrieNode>();
		boolean has(char symbol){
			return this.symbol==symbol;
		}
		Map<Character, TrieNode> getChilds(){
			return childs;
		}
		void put(char symbol){
			this.symbol=symbol;
		}
		void setEndOfWord(boolean isEndOfWord){
			this.isEndOfWord=isEndOfWord;
		}

		
	}
}