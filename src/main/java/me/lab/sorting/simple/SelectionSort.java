package me.lab.sorting.simple;

public class SelectionSort extends SorterBase {
	public void sortImplementation(int arr[]) {

		for (int i = arr.length - 1; i > 0; i--) {
			int indexOfBiggest = 0;
			for (int innerIdx = 0; innerIdx <= i; innerIdx++) {
				comparisonCount++;
				if (arr[indexOfBiggest] < arr[innerIdx]) {
					indexOfBiggest = innerIdx;
				}
			}

			int tmp = arr[i];
			arr[i] = arr[indexOfBiggest];
			arr[indexOfBiggest] = tmp;
			swapsCount++;
		}
	}

}
