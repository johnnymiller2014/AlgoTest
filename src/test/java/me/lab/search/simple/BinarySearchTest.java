package me.lab.search.simple;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTest {
	public static final int TEST_SIZE = 20;
	public static final int TEST_MAX_VAL = 100;
	private Random r = new java.util.Random();
	private int[] testArr = null;
	private BinarySearch bs = null;

	@Before
	public void setUp() throws Exception {
		bs = new BinarySearch();
		testArr = generateTestArray();
		Arrays.sort(testArr);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBinarySearch() {
		int toSearchNotExisting = TEST_MAX_VAL;
		// System.out.println("searching in array length "+testArr.length+" for value "+toSearchNotExisting);
		int found = bs.binarySearch(testArr, toSearchNotExisting);
		assertTrue("search for non existiing element should return -1",
				-1 == found);

		int toSearchExisting = testArr[r.nextInt(TEST_SIZE) % testArr.length];
		found = bs.binarySearch(testArr, toSearchExisting);
		assertTrue(
				"search for non existing element should return index of that element",
				(testArr[found] == toSearchExisting));
	}

	public void printArrray(int[] arr) {
		if (null != arr) {
			for (int i = 0; i < arr.length; ++i) {
				System.out.println(arr[i]);
			}
		}
	}

	private int[] generateTestArray() {
		int[] arr = new int[TEST_SIZE];

		for (int i = 0; i < TEST_SIZE; ++i) {
			arr[i] = r.nextInt(TEST_MAX_VAL);
		}
		return arr;
	}

}
