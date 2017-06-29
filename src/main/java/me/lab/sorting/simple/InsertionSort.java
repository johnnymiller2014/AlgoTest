package me.lab.sorting.simple;

public class InsertionSort extends SorterBase {
	@Override
	public void sortImplementation(int[] arr) {
		if (arr.length < 2) {
			return;
		}
		for (int i = 1; i < arr.length; ++i) {
			int temp = arr[i];
			int innerIdx = i;
			while (innerIdx > 0 && arr[innerIdx - 1] > temp) {
				comparisonCount++;
				swapsCount++;
				arr[innerIdx] = arr[innerIdx - 1];
				innerIdx--;
			}
			arr[innerIdx] = temp;
		}
	}
}
