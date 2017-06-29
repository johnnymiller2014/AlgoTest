package me.lab.sorting.simple;

public class BubbleSort extends SorterBase {
	/**
	 * Sorts array in place, using bubble sort
	 * 
	 * @param arr
	 */
	public void sortImplementation(int arr[]) {
		for (int i = arr.length; i > 0; i--) {
			for (int innerIdx = 0; innerIdx < i - 1; innerIdx++) {
				comparisonCount++;
				if (arr[innerIdx] > arr[innerIdx + 1]) {
					int tmp = arr[innerIdx + 1];
					arr[innerIdx + 1] = arr[innerIdx];
					arr[innerIdx] = tmp;
					swapsCount++;
				}
			}
		}
	}

}
