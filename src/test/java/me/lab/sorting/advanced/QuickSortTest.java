package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;
import me.lab.sorting.simple.SorterTestBase;

public class QuickSortTest extends SorterTestBase {

	@Override
	protected SorterBase getSorterClass() {
		return new QuickSort();
	}
}
