package me.lab.stack;

import junit.framework.Assert;

import org.junit.Test;

public class StackArrayTest {

	@Test
	public void testPush() {
		StackArray<Integer> st = new StackArray<Integer>();
		Integer[] test = getTestData();
		for (int i : test) {
			st.push(i);
		}
		Assert.assertEquals(test.length, st.size);
	}

	@Test
	public void testPop() {
		StackArray<Integer> st = new StackArray<Integer>();
		Integer[] test = getTestData();
		for (int i : test) {
			st.push(i);
		}
		Assert.assertEquals(test[test.length - 1], st.pop());
		Assert.assertEquals(test.length - 1, st.size);
		Assert.assertEquals(test[test.length - 2], st.pop());
		Assert.assertEquals(test.length - 2, st.size);
	}

	@Test
	public void testPeek() {
		StackArray<Integer> st = new StackArray<Integer>();
		Integer[] test = getTestData();
		for (int i : test) {
			st.push(i);
		}
		Assert.assertEquals(test[test.length - 1], st.peek());
		Assert.assertEquals(test.length, st.size);
		Assert.assertEquals(test[test.length - 1], st.peek());
		Assert.assertEquals(test.length, st.size);
	}

	@Test
	public void testResize() {
		StackArray<Integer> st = new StackArray<Integer>(5);
		Integer[] test = getTestData();
		for (int i : test) {
			st.push(i);
		}
		Assert.assertEquals(test[test.length - 1], st.peek());
		Assert.assertEquals(test.length, st.size);
		Assert.assertEquals(test[test.length - 1], st.peek());
		Assert.assertEquals(test.length, st.size);
	}

	Integer[] getTestData() {
		return new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	}

}
