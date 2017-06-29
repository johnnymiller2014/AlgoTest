package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;

public class QuickSort extends SorterBase {

	@Override
	protected void sortImplementation(int[] arr) {

		quickSort(arr, 0, arr.length - 1);
	}

	private void quickSort(int[] arr, int lBound, int uBound) {
		// select pivot
		// make sure all items left from pivot are less, and all items right
		// from pivot are bigger
		// repeat recursively
		// if our pivot is in the middle - we have N Ln N. If not - we can have
		// N^2
		int indexLeft = lBound, indexRight = uBound;
		if ((indexRight - indexLeft) < 1) {
			return;// size is 1,it's already sorted
		}
		indexLeft = partition(arr, indexLeft, indexRight);
		if (lBound < (indexLeft - 1)) {
			quickSort(arr, lBound, indexLeft - 1);
		}
		quickSort(arr, indexLeft, uBound);
	}

	public int partition(int[] arr, int indexLeft, int indexRight) {
		int indexPivot = (indexLeft + indexRight) / 2;
		int pivot = arr[indexPivot];
		while (indexLeft <= indexRight) {
			while (arr[indexLeft] < pivot) {
				comparisonCount++;
				indexLeft++;
			}
			while (arr[indexRight] > pivot) {
				comparisonCount++;
				indexRight--;
			}
			if (indexLeft <= indexRight) {
				swapsCount++;
				int tmp = arr[indexLeft];
				arr[indexLeft] = arr[indexRight];
				arr[indexRight] = tmp;
				indexLeft++;
				indexRight--;
			}
		}
		return indexLeft;
	}

}
