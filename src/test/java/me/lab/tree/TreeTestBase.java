package me.lab.tree;

import java.util.Comparator;

public class TreeTestBase {

	protected static final int NANOSECONDS_IN_SEC = 1000000000;

	public TreeTestBase() {
		super();
	}

	protected int[] getTestDataAscending(int amount) {
		int[] test = new int[amount];
		for (int i = 0; i < amount; ++i) {
			test[i] = i;
		}
		return test;
	}

	protected int[] getTestDataDescending(int amount) {
		int[] test = new int[amount];
		int value = 0;
		for (int i = 0; i < amount; ++i) {
			test[i] = value--;
		}
		return test;
	}

	protected int logbase2(int x) {
		return (int) (Math.log(x) / Math.log(2));
	}

	protected Comparator<Integer> getIntegerComparator() {
		return new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}

		};
	}

}