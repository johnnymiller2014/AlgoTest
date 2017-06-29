package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;

public class MergeSort extends SorterBase {

	@Override
	protected void sortImplementation(int[] arr) {
		if (1 == arr.length) {
			return;
		}
		int half = arr.length / 2;
		int[] sortedPartLeft = mergeSortR(arr, 0, half);
		int[] sortedPartRight = mergeSortR(arr, half, arr.length - half);
		int[] sortedMerged = merge(sortedPartLeft, sortedPartRight);
		for (int i = 0; i < arr.length; ++i) {
			arr[i] = sortedMerged[i];
		}
	}

	private int[] mergeSortR(int arr[], int startIdx, int length) {
		if (1 == length) {
			return new int[] { arr[startIdx] };
		}
		int half = length / 2;
		int[] sortedPartLeft = mergeSortR(arr, startIdx, half);
		int[] sortedPartRight = mergeSortR(arr, startIdx + half, length - half);

		return merge(sortedPartLeft, sortedPartRight);
	}

	private int[] merge(int[] sortedPartLeft, int[] sortedPartRight) {
		int[] ret = new int[sortedPartLeft.length + sortedPartRight.length];
		int retIdx = 0, leftIdx = 0, rightIdx = 0;
		while (retIdx < ret.length) {
			if (leftIdx < sortedPartLeft.length
					&& rightIdx < sortedPartRight.length) {
				comparisonCount++;
				if (sortedPartLeft[leftIdx] < sortedPartRight[rightIdx]) {
					ret[retIdx++] = sortedPartLeft[leftIdx++];
					copyCount++;
				} else {
					ret[retIdx++] = sortedPartRight[rightIdx++];
					copyCount++;
				}
			} else if (leftIdx < sortedPartLeft.length) {
				ret[retIdx++] = sortedPartLeft[leftIdx++];
				copyCount++;
			} else {
				ret[retIdx++] = sortedPartRight[rightIdx++];
				copyCount++;
			}
		}
		return ret;
	}

}
