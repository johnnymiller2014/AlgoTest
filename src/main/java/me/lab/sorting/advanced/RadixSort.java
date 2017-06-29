package me.lab.sorting.advanced;


import me.lab.sorting.simple.SorterBase;

public class RadixSort extends SorterBase {

	@Override
	protected void sortImplementation(int[] arr) {
		radixSortR(arr, 0, arr.length - 1, 0);

	}

	private void radixSortR(int[] arr, int lboud, int ubound, int level) {
		if ((ubound - lboud) < 1) {
			return;// one element in array. already sorted.
		}
		// setup 2 double linked lists (2 based radix sort)
		// consider level to shift and copy all items from array to lists
		// accordingly to most significant "level" bits
		//
		// copy items from lists to array, in the same order, as items were
		// inserted in the list(we want stable sort)
		//
		// TODO implement

	}

}
