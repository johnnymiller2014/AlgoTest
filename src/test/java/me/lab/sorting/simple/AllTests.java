package me.lab.sorting.simple;

import me.lab.sorting.advanced.HeapSortTest;
import me.lab.sorting.advanced.MergeSortTest;
import me.lab.sorting.advanced.QuickSortTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BubbleSortTest.class, InsertionSortTest.class,
		SelectionSortTest.class, MergeSortTest.class, HeapSortTest.class,
		QuickSortTest.class })
public class AllTests {

}
