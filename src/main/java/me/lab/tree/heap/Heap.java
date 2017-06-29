package me.lab.tree.heap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class Heap<E> {
	private Comparator<? super E> comparator;
	private Object queue[];
	/**
	 * Amount of elements in queue
	 */
	private int size;

	public Heap(int initialCapacity, Comparator<? super E> comparator) {
		this.comparator = comparator;
		queue = new Object[initialCapacity];

	}

	public Heap(@SuppressWarnings("rawtypes") Collection collection,
			Comparator<? super E> comparator) {
		this.comparator = comparator;
		queue = collection.toArray();
		size = queue.length;

		heapify();
	}

	public void add(E value) {
		// check space and grow if need
		grow();
		size++;
		queue[size - 1] = value;
		correctHeapFromBottom(size - 1);
	}

	public void grow() {
		if (queue.length == size) {
			int newCapacity = queue.length * 2;
			queue = Arrays.copyOf(queue, newCapacity);
		}
	}

	public E poll() {
		if (0 == size) {
			return null;
		} else {
			@SuppressWarnings("unchecked")
			E ret = (E) queue[0];
			size--;
			queue[0] = queue[size];
			queue[size] = null;
			correctHeapFromTop(0);
			;
			return ret;
		}
	}

	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	private void correctHeapFromBottom(int index) {
		while (index > 0) {
			E val = (E) queue[index];
			int parentIndex = (index - 1) >>> 1;
			if (comparator.compare((E) queue[parentIndex], val) < 0) {
				queue[index] = queue[parentIndex];
				queue[parentIndex] = val;
			}
			index = parentIndex;
		}

	}

	@SuppressWarnings("unchecked")
	private void correctHeapFromTop(int index) {
		while (index < size) {
			int indexLeftChild = (index << 1) + 1;
			int indexRightChild = (index << 1) + 2;
			int nextIndexToExchange = 0;
			if (indexLeftChild < size) {
				nextIndexToExchange = indexLeftChild;
				if (indexRightChild < size
						&& (comparator.compare((E) queue[indexLeftChild],
								(E) queue[indexRightChild]) <= 0)) {
					nextIndexToExchange = indexRightChild;
				}
				if (comparator.compare((E) queue[index],
						(E) queue[nextIndexToExchange]) <= 0) {
					// exchange
					E tmp = (E) queue[index];
					queue[index] = queue[nextIndexToExchange];
					queue[nextIndexToExchange] = tmp;
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
		for (int i = (size >>> 1) - 1; i >= 0; i--) {
			correctHeapFromTop(i);
		}
	}
}
