package me.lab.sorting.simple;

import java.util.Arrays;
import java.util.Random;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class SorterTestBase {

	private static final int MILLS_IN_SECOND = 1000000000;
	public static final int TEST_DATA_SIZE = 1000;
	protected int[] toBeSorted;
	protected int[] etalon;
	protected SorterBase sorter;

	public SorterTestBase() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		toBeSorted = generateTestData();
		etalon = Arrays.copyOf(toBeSorted, toBeSorted.length);
		sorter = getSorterClass();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSort() {
		Arrays.sort(etalon);
		long startTime = System.nanoTime();
		sorter.sort(toBeSorted);
		long executionTime = System.nanoTime() - startTime;
		boolean isEqual = Arrays.equals(etalon, toBeSorted);
		Assert.assertTrue("Sorting results by " + sorter.getClass()
				+ " sort and java.util.Arrays should be same", isEqual);
		System.out.println(sorter.getClass() + " array size "
				+ toBeSorted.length + ", comparisons="
				+ sorter.getComparisonCount() + ",swaps="
				+ sorter.getSwapsCount() + ",copy=" + sorter.getCopyCount()
				+ ", time=" + executionTime + " nanoseconds (" + executionTime
				/ MILLS_IN_SECOND + ")seconds");
	}

	protected int[] generateTestData() {
		return generateRandomTestData();
		// return new int[] { 10000, 9, 888, 7, 6, 55, 94, 34, 2, 971 };
		// return new int[] { 72,96,44,40,41,61,45,90,48,43, };
	}

	protected int[] generateRandomTestData() {
		Random r = new Random();
		int[] ints = new int[TEST_DATA_SIZE];
		for (int i = 0; i < TEST_DATA_SIZE; ++i) {
			ints[i] = r.nextInt(100);
		}
		return ints;
	}

	protected int[] generateReverseSortedTestData() {
		int[] data = generateSortedTestData();
		reverse(data);
		return data;
	}

	protected int[] generateSortedTestData() {
		int[] data = generateRandomTestData();
		Arrays.sort(data);
		return data;
	}

	protected abstract SorterBase getSorterClass();

	private void reverse(int[] ints) {
		int left = 0, right = ints.length - 1;
		while (left < right) {
			swap(ints, left, right);
			left++;
			right--;
		}
	}

	private void swap(int[] ints, int left, int right) {
		int tmp = ints[left];
		ints[left] = ints[right];
		ints[right] = tmp;
	}

}