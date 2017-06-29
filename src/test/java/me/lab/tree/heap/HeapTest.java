package me.lab.tree.heap;

import java.util.Arrays;
import java.util.Comparator;

import junit.framework.Assert;

import org.junit.Test;

public class HeapTest {

	public HeapTest() {

	}

	@Test
	public void testAdd() {
		Heap<Integer> h = new Heap<Integer>(10, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		Integer[] testData = getTestData();
		testHeap(h, testData);
		testData = getTestDataReversed();
		testHeap(h, testData);
		testData = getTestDataRandom();
		testHeap(h, testData);
	}

	@Test
	public void testHeapify() {

		Heap<Integer> h = new Heap<Integer>(Arrays.asList(getTestDataRandom()),
				new Comparator<Integer>() {
					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}
				});
		testHeapInternal(h, getTestDataRandom());

	}

	public void testHeap(Heap<Integer> h, Integer[] testData) {
		for (Integer in : testData) {
			h.add(in);
		}
		testHeapInternal(h, testData);
	}

	public void testHeapInternal(Heap<Integer> h, Integer[] testData) {
		Assert.assertEquals(
				"Heap.size() should be equal to number of elements",
				testData.length, h.size());
		Integer prev;
		Integer current;
		while (h.size() > 0) {
			prev = h.poll();
			current = h.poll();
			Assert.assertTrue("Elements in heap are ordered from Max to Min",
					(prev >= current) || current == null);
		}
	
	}

	private Integer[] getTestData() {
		return new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
	}

	private Integer[] getTestDataReversed() {
		return new Integer[] { 0, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
	}

	private Integer[] getTestDataRandom() {
		return new Integer[] { 1775723023, 1628195774, -771984030, 1645826,
				-879166677, 1701111618, -587169487, -1764621003, 1212632547,
				-2081186201 };
	}

}
