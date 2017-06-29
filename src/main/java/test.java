import java.util.Arrays;
import java.util.Random;

public class test {
	int sumOfValues = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Random r = new Random();
		int[] arr = new int[1000000];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt();
		}
		test t= new test();
		long startTime = System.nanoTime();
		int largestValue = t.getLargestValue(arr);
		long totalTime = System.nanoTime() - startTime;
		System.out.println("t.getLargestValue(arr) returns" + largestValue
				+ " in " + totalTime + " nanoseconds");
		startTime = System.nanoTime();
		largestValue = t.getLargestValueLn(arr);
		totalTime = System.nanoTime() - startTime;
		System.out.println("t.getLargestValueLn(arr) returns" + largestValue
				+ " in " + totalTime + " nanoseconds");
	}

	public void printFrom1To100() {
		
		
		
		for (int i = 1; i <= 100; ++i) {
			if (((i % 3) == 0) && ((i % 5) == 0)) {
				System.out.println("FooBar");
			} else if ((i % 3) == 0) {
				System.out.println("Foo");
			} else if ((i % 5) == 0) {
				System.out.println("Bar");
			} else {
				System.out.println(i);
			}
		}
	}

	public int getLargestValue(int[] arr) {
		// for performance and to reduce RAM usage, we don�t use standard
		// Arrays.sort(arr) to sort the array and get the largest.
		// we have linear performance, zero swap operations,
		int indexOfLargest = 0;
		for (int currentIndex = 0; currentIndex < arr.length; currentIndex++) {
			if (arr[currentIndex] > arr[indexOfLargest]) {
				indexOfLargest = currentIndex;
			}
		}
		return arr[indexOfLargest];
	}
	
	
	public int getLargestValueLn(int[] arr) {
		Arrays.sort(arr);
		return arr[arr.length - 1];
	}

	private int getLeftChild(int arr[], int index) {
		int ind = (index * 2) + 1;
		if (ind >= arr.length) {
			// the node does not exists
			ind = -1;
		}
		return ind;
	}

	private int getRightChild(int arr[], int index) {
		int ind = (index * 2) + 2;
		if (ind >= arr.length) {
			// the node does not exists
			ind = -1;
		}
		return ind;
	}

	public void countSumWithoutLoop() {
		int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int currentIndex = 0;
		traverseArrayAsTreeR(arr, currentIndex);
		System.out.print(sumOfValues);
	}

	private void traverseArrayAsTreeR(int arr[], int currentIndex) {
		if (currentIndex >= arr.length || currentIndex == -1) {
			return;
		}
		traverseArrayAsTreeR(arr, getLeftChild(arr, currentIndex));
		sumOfValues += arr[currentIndex];
		System.out.println("Accessing array arr[" + currentIndex + "]");
		traverseArrayAsTreeR(arr, getRightChild(arr, currentIndex));
	}
}
