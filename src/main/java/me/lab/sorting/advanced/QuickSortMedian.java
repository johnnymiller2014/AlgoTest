package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;

public class QuickSortMedian extends SorterBase {

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
		if ((indexLeft + indexRight) >= 2) {
			// use median pivot when possible
			// arr[0], arr[n], arr[medium]
			// if
			if (arr[indexLeft] > arr[indexPivot]) {
				swap(arr, indexLeft, indexPivot);
			}
			if (arr[indexPivot] > arr[indexRight]) {
				swap(arr, indexPivot, indexRight);
			}
			if (arr[indexLeft] > arr[indexPivot]) {
				swap(arr, indexLeft, indexPivot);
			}// now arr[indexPivot] is median, it shold make our quick sort more
				// logariphic
		}
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
				swap(arr, indexLeft, indexRight);
				indexLeft++;
				indexRight--;
			}
		}
		return indexLeft;
	}

	public void swap(int[] arr, int indexOne, int indexTwo) {
		swapsCount++;
		int tmp = arr[indexOne];
		arr[indexOne] = arr[indexTwo];
		arr[indexTwo] = tmp;
	}

}
