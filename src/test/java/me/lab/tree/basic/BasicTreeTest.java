package me.lab.tree.basic;

import junit.framework.Assert;

import org.junit.Test;

public class BasicTreeTest {
	private static final int NANOSECONDS_IN_SEC = 1000000000;

	//@Test(expected = StackOverflowError.class)
	public void crashTestShouldFail() {
		Tree t = new Tree();
		int test[] = getTestDataAscending(1000000);
		long startTime = System.nanoTime();
		for (int i = 0; i < test.length; ++i) {
			t.insert(test[i], test[i]);
		}
		long endTime = System.nanoTime();
		System.out.println(test.length + " inserts took: "
				+ (endTime - startTime) + " ns,(" + (endTime - startTime)
				/ NANOSECONDS_IN_SEC + " seconds)");
		startTime = System.nanoTime();
		for (int i = 0; i < test.length; ++i) {
			Assert.assertEquals((Integer) test[i],
					(Integer) t.find(test[i]).key);
		}
		endTime = System.nanoTime();
		System.out.println(test.length + " finds in tree with " + test.length
				+ " elements took: " + (endTime - startTime) + " ns,("
				+ (endTime - startTime) / NANOSECONDS_IN_SEC + " seconds)");
	}

	private int[] getTestDataAscending(int amount) {
		int[] test = new int[amount];
		for (int i = 0; i < amount; ++i) {
			test[i] = i;
		}
		return test;
	}

}
