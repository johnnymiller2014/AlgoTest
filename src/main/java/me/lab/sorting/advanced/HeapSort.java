package me.lab.sorting.advanced;

import me.lab.sorting.simple.SorterBase;

public class HeapSort extends SorterBase {

	int[] toBeSorted;

	@Override
	protected void sortImplementation(int[] arr) {

		this.toBeSorted = arr;

		heapify();

		int heapSize = toBeSorted.length;
		for (int i = 0; i < toBeSorted.length; ++i) {
			int extracted = toBeSorted[0];
			heapSize--;
			toBeSorted[0] = toBeSorted[heapSize];
			toBeSorted[heapSize] = extracted;
			correctHeapFromTop(0, heapSize);
		}

	}

	private void correctHeapFromTop(int index, int size) {
		while (index < size) {
			int indexLeftChild = (index << 1) + 1;
			int indexRightChild = (index << 1) + 2;
			int nextIndexToExchange = 0;
			if (indexLeftChild < size) {
				nextIndexToExchange = indexLeftChild;
				comparisonCount++;
				if (indexRightChild < size
						&& (toBeSorted[indexLeftChild] <= toBeSorted[indexRightChild])) {
					nextIndexToExchange = indexRightChild;
				}
				comparisonCount++;
				if (toBeSorted[index] <= toBeSorted[nextIndexToExchange]) {
					// exchange
					swapsCount++;
					int tmp = toBeSorted[index];
					toBeSorted[index] = toBeSorted[nextIndexToExchange];
					toBeSorted[nextIndexToExchange] = tmp;
					index = nextIndexToExchange;
				} else {
					break;
				}
			} else {
				break;
			}
		}
	}

	/**
	 * Reorders queue[] to be heap. Assumes nothing about order elements in
	 * queue[] before start
	 */
	private void heapify() {
		for (int i = (toBeSorted.length >>> 1) - 1; i >= 0; i--) {
			correctHeapFromTop(i, toBeSorted.length);
		}
	}

}
