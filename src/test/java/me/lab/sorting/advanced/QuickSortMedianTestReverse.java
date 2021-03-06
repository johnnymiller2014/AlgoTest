package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;
import me.lab.sorting.simple.SorterTestBase;

public class QuickSortMedianTestReverse extends SorterTestBase {

	@Override
	protected SorterBase getSorterClass() {
		return new QuickSortMedian();
	}

	protected int[] generateTestData() {
		return super.generateReverseSortedTestData();
	}
}
