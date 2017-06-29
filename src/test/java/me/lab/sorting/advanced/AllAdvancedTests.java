package me.lab.sorting.advanced;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ HeapSortTest.class, MergeSortTest.class, QuickSortTest.class,
		QuickSortMedianTest.class })
public class AllAdvancedTests {

}
