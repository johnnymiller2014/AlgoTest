package me.lab.hash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.Assert;
import me.lab.hash.HashChaining.PerformanceStat;

import org.junit.Before;
import org.junit.Test;

public class HashChainingTest {
	Set<String> etalon = new HashSet<String>();

	@Before
	public void setup() throws IOException {
		etalon.clear();
		etalon.addAll(getTestData());
	}

	@Test
	public void testAdd() throws IOException {
		HashChaining<String> h = new HashChaining<String>();
		Set<String> s = getTestData();
		Iterator<String> it = s.iterator();
		while (it.hasNext()) {
			h.add(it.next());
		}
		Assert.assertEquals("hash size must be same as etalon size",
				etalon.size(), h.size());
		Iterator<String> it2 = s.iterator();
		while (it2.hasNext()) {
			String str = it2.next();
			Assert.assertEquals("inserted item assumed to be in hash", str,
					h.get(str));
		}
		System.out.println("Elements in hash " + h.size());
		System.out.println("underlying array size " + h.capacity());
		@SuppressWarnings("rawtypes")
		PerformanceStat stat = h.performanceStat();
		System.out.println("max amount of items in same index "
				+ stat.maxClusterSize());
		System.out.println("avgItemsPerCell " + stat.avgItemsPerCell());
		System.out.println("Used Cells " + stat.UsedCells());

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
