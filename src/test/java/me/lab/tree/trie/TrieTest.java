package me.lab.tree.trie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;
import me.lab.tree.trie.Trie;
import org.junit.Test;

import junit.framework.Assert;

public class TrieTest {
	@Test
	public void testAdd(){
		Trie trie = new Trie();
		trie.add("test".toCharArray());
		trie.add("tester".toCharArray());
		Assert.assertEquals(true,trie.contains("test".toCharArray()));
		Assert.assertEquals(true,trie.contains("tester".toCharArray()));
		Assert.assertEquals(false,trie.contains("not_exists".toCharArray()));
		trie.add("testing".toCharArray());
		Assert.assertEquals(true,trie.contains("testing".toCharArray()));
		trie.add("zero".toCharArray());
		Assert.assertEquals(true,trie.contains("zero".toCharArray()));
		Assert.assertEquals(true,trie.contains("testing".toCharArray()));
	}
	
	public void testLoad() throws IOException{
		Trie trie = new Trie();
		Set<String> testWords= getTestData();
		while(testWords.iterator().hasNext()){
			trie.add(testWords.iterator().next().toCharArray());
		}
		Assert.assertEquals(true,trie.contains("test".toCharArray()));
		Assert.assertEquals(true,trie.contains("tester".toCharArray()));
		Assert.assertEquals(false,trie.contains("not_exists".toCharArray()));
		
	}
	protected Set<String> getTestData() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream("Data.txt")));
		Set<String> s = new TreeSet<String>();
		String line;
		while ((line = in.readLine()) != null) {
			s.add(line);
		}
		return s;
	}
}
