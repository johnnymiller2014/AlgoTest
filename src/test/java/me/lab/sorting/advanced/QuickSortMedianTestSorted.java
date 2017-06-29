package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;
import me.lab.sorting.simple.SorterTestBase;

public class QuickSortMedianTestSorted extends SorterTestBase {

	@Override
	protected SorterBase getSorterClass() {
		return new QuickSortMedian();
	}

	protected int[] generateTestData() {
		return super.generateSortedTestData();

	}

}
