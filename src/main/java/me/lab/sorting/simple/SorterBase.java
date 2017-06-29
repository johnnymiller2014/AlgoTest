package me.lab.sorting.simple;

public abstract class SorterBase {

	protected long comparisonCount;
	protected long swapsCount;
	protected long copyCount;

	public long getCopyCount() {
		return copyCount;
	}

	public SorterBase() {
		super();
	}

	public long getComparisonCount() {
		return comparisonCount;
	}

	public long getSwapsCount() {
		return swapsCount;
	}

	public void sort(int[] arr) {
		if (null == arr) {
			throw new IllegalArgumentException(
					"array to be sorted can't be null");
		}
		sortImplementation(arr);
	}

	protected abstract void sortImplementation(int[] arr);

}