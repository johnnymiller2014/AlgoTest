package me.lab.hash.consistent;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {
		private  SortedMap<Integer,T> circle =new TreeMap<Integer,T>();
		private int numberOfReplicas;
		private HashFunction hashFunction;
		
		
		public ConsistentHash(HashFunction hashFunction,
			    int numberOfReplicas, Collection<T> nodes){
				this.hashFunction = hashFunction;
				this.numberOfReplicas =numberOfReplicas;
				for(T node: nodes){
					add(node);
				}
		}
		public void add(T node){
			for(int i=0;i<numberOfReplicas;++i){
				int hash = hashFunction.hash(node.toString()+1);
				circle.put(hash,node);
			}
		}
		
		public void remove(T node){
			for(int i=0;i<numberOfReplicas;++i){
				int hash = hashFunction.hash(node.toString()+1);
				circle.remove(hash);
			}
		}
		
		public T get(Object key){
			if(circle.isEmpty()){
				return null;
			}
			int hash  = hashFunction.hash(key);
			if(!circle.containsKey(hash)){
			  SortedMap<Integer,T> tailMap = circle.tailMap(hash);
			  if(tailMap.isEmpty()){
				  hash = circle.firstKey();
			  }	else{
				  hash = tailMap.firstKey();
			  }
			}
			return circle.get(hash);
			
		}
		
}
class HashFunction{
	public int hash(Object value){
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] hash = digest.digest(((String) value).getBytes(StandardCharsets.UTF_8));
		return hash.toString().hashCode();
	
	}
}
