package me.lab.hash.consistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import junit.framework.Assert;

public class ConsistentHashTest {
	@Test
	public void test(){
		List<String> nodes = java.util.Arrays.asList(new String[]{"Node1","Node2","Node3","Node4","Node5"});
		ConsistentHash<String> consistentHash= new ConsistentHash<String>(new HashFunction(),100,nodes);
		List<String> keys = getTestData();
		Map<String, Integer> freq = new HashMap<String, Integer>();
		for(String key: keys){
			String node=consistentHash.get(key);
			int count = freq.containsKey(node) ? freq.get(node) : 0;
			freq.put(node, count +1);
		}
		System.out.println(freq);
		
	}
	ArrayList<String> getTestData(){
		int max=10000;
		ArrayList<String> testData=new ArrayList<String>();
		Random rnd = new Random();
		for(int i=0;i<max;++i){
			testData.add( String.valueOf(rnd.nextInt()));
		}
		return testData;
	}
	
}
