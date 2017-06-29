package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;
import me.lab.sorting.simple.SorterTestBase;

public class QuickSortTestReverse extends SorterTestBase {

	@Override
	protected SorterBase getSorterClass() {
		return new QuickSort();
	}

	protected int[] generateTestData() {
		return super.generateReverseSortedTestData();
	}

}
