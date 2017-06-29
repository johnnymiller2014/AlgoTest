package me.lab.search.simple;

public class BinarySearch {
	/**
	 * Search for a value in array Precondition - array should be sorted.
	 * 
	 * @param testArr
	 *            where to search
	 * @param value
	 *            to be searched
	 * @return -1 if not found, index of found element otherwise.
	 */
	public int binarySearch(int[] testArr, int value) {
		if (null == testArr) {
			throw new IllegalArgumentException(
					"Array to search in should be not null");
		}
		int foundIndex = -1;
		int lowerIndex = 0;
		int upperIndex = testArr.length - 1;
		while (upperIndex >= lowerIndex) {
			int mediumIndex = lowerIndex + ((upperIndex - lowerIndex) / 2);
			if (value == testArr[mediumIndex]) {
				foundIndex = mediumIndex;
				break;
			}
			if (value < testArr[mediumIndex]) {
				upperIndex = mediumIndex - 1;
			} else {
				lowerIndex = mediumIndex + 1;
			}
		}
		;
		return foundIndex;
	}

}
