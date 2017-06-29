package me.lab.stack;

import java.util.Arrays;

public class StackArray<E> implements Stack<E> {
	private static final int INITIAL_SIZE = 10;
	private Object[] data;
	/**
	 * index of top data element in the stack
	 */
	int indexOfTop = -1;
	int size = 0;

	public StackArray() {
		data = new Object[INITIAL_SIZE];
	}

	public StackArray(int initialSize) {
		if (initialSize < 0) {
			throw new IllegalArgumentException(
					" initialSize of a stack can't be negative");
		}
		data = new Object[initialSize];
	}

	@Override
	public void push(E value) {
		size++;
		if (size > data.length) {
			resizeUp();
		}
		data[++indexOfTop] = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E pop() {
		E ret = null;
		if (size > 0) {
			ret = (E) data[indexOfTop--];
			size--;
		}
		return ret;
	}

	@Override
	public long size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E peek() {
		E ret = null;
		if (size > 0) {
			ret = (E) data[indexOfTop];
		}
		return ret;
	}

	private void resizeUp() {
		Object[] dataNew = Arrays.copyOf(data, data.length * 2);
		data = dataNew;
	}

}
