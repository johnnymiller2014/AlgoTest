package me.lab.sorting.advanced;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ QuickSortTest.class, QuickSortTestSorted.class,
		QuickSortTestReverse.class, QuickSortMedianTest.class,
		QuickSortMedianTestSorted.class, QuickSortMedianTestReverse.class })
public class AllQuickSortTests {

}
