package me.lab.sorting.simple;

public class BubbleSortTest extends SorterTestBase {
	protected SorterBase getSorterClass() {
		return new BubbleSort();
	}
	protected int[] generateTestData() {
		//Bubble sort is by definition slow. We provide very small test data for it
		 return new int[] { 10000, 9, 888, 7, 6, 55, 94, 34, 2, 971 };
		
	}

}
