package me.lab.tree.avl;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import junit.framework.Assert;
import me.lab.tree.TreeTestBase;

import org.StructureGraphic.v1.DSutils;
import org.junit.Test;

public class AVLTreeTest extends TreeTestBase {
	private static final int TREE_ORDER = 2;
	public static final int TEST_DATA_SIZE =100;
	public void testTreeSetPerf(){
		Set<Integer> t = new TreeSet<Integer>(new Comparator<Integer>(){

			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		
		long startTime = System.nanoTime();
		for(int i=0;i<TEST_DATA_SIZE;++i){
			t.add(i);
		}
		long endTime = System.nanoTime();
		System.out.println("TreeSet<Integer> "+TEST_DATA_SIZE + " inserts took: "
				+ (endTime - startTime) + " ns,(" + (endTime - startTime)
				/ NANOSECONDS_IN_SEC + " seconds)");
		
		
		startTime = System.nanoTime();
		for(int i=0;i<TEST_DATA_SIZE;++i){
			t.contains(i);
		}
		endTime = System.nanoTime();
		System.out.println("TreeSet<Integer> "+TEST_DATA_SIZE + " finds in tree with " + TEST_DATA_SIZE
				+ " elements took: " + (endTime - startTime) + " ns,("
				+ (endTime - startTime) / NANOSECONDS_IN_SEC + " seconds)");
	}
	
	@Test
	public void testInsertAndFindSorted() throws IOException{
		testInsertAndFind(getTestDataAscending(TEST_DATA_SIZE));
	}
	@Test
	public void testInsertAndFindReverseSorted() throws IOException{
		testInsertAndFind(getTestDataDescending(TEST_DATA_SIZE));
	}
	
	private void testInsertAndFind(int test[]) throws IOException {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		fillTree(t, test);
		//drawTreeToFile(t, "AVLTree.InsertAndFind.png");
		for (int i = 0; i < test.length; ++i) {
			Assert.assertEquals((Integer) test[i], (Integer) t.find(test[i])
					.getKey());
		}
		Assert.assertNull(t.find(test.length));
		Assert.assertEquals(test.length, t.size());
		assertTreeHeight(t, TEST_DATA_SIZE);
		
	}
	@Test
	public void testLeftRightRebalanceAfterInsert() throws IOException {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());

		t.add(-1,"-1");
		t.add(-7,"-7");
		t.add(-5,"-5");

		assertTreeHeight(t, TEST_DATA_SIZE);
		//drawTreeToFile(t, "AVLTree.testLeftRightRebalanceAfterInsert.png");
	}

	@Test
	public void testRightRight() {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		t.add(1, "first");
		t.add(2, "second");
		t.add(3, "third");
		checkBalance(t);
	}

	@Test
	public void testRightLeft() {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		t.add(1, "first");
		t.add(3, "second");
		t.add(2, "third");
		checkBalance(t);
	}

	@Test
	public void testLeftLeft() {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		t.add(3, "first");
		t.add(2, "second");
		t.add(1, "third");
		checkBalance(t);
	}

	@Test
	public void testLeftRight() throws IOException {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		t.add(3, "first");
		t.add(2, "second");
		t.add(1, "third");
		//drawTreeToFile(t, "testLeftRight.png");
		checkBalance(t);
	}

	@Test
	public void testArbitrary() {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		t.add(3, "first");
		t.add(2, "second");
		t.add(1, "third");
		t.add(4, "first");
		t.add(5, "second");
		t.add(6, "third");
		t.add(7, "seventh");
	}

	@Test
	public void testRemoveLeaf() throws IOException {
		//System.out.println(calcNumberOfNodesForTree(4));
		testRemove(27,26,"removeLeaf");
	}
	@Test
	public void testRemoveOneChild() throws IOException {
		testRemove(26,24,"RemoveOneChild");
	}

	@Test
	public void testRemoveBothChildsRightChild() throws IOException {
		testRemove(26,23,"RemoveBothChildsRightChild");
	}

	@Test
	public void testRemoveBothChildsWithGrandChilds() throws IOException {
		testRemove(26,11,"RemoveBothChildsWithGrandChilds");
	}
	
	@Test
	public void testBalanceAfterDeletionToRight() throws IOException {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		t.add(15, "15");
		t.add(10, "10");
		t.add(5, "5");
		t.add(4, "4");
		//drawTreeToFile(t, "BalanceAfterDeletionToRight.before.png");
		t.remove(15);
		Assert.assertEquals(3, t.size());
		assertTreeHeight(t, 3);
		//drawTreeToFile(t, "BalanceAfterDeletionToRight.after.png");
	}

	@Test
	public void testBalanceAfterDeletionToLeft() throws IOException {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		t.add(15, "15");
		t.add(10, "10");
		t.add(5, "5");
		t.add(20, "20");
		//drawTreeToFile(t, "BalanceAfterDeletionToLeft.before.png");
		t.remove(5);
		Assert.assertEquals(3, t.size());
		assertTreeHeight(t, 3);
		//drawTreeToFile(t, "BalanceAfterDeletionToLeft.after.png");
	}
	
	@Test
	public void testCLear(){
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());
		for(int i=0;i<10;i++){
			t.add(i, String.valueOf(i));
		}
		Assert.assertEquals(10, t.size());
		Assert.assertEquals(1, t.find(1).getKey().intValue());
		t.clear();
		Assert.assertEquals(0, t.size());
		Assert.assertEquals(null, t.find(1));
	}

	private void testRemove(int testDataSize,int keyToRemove,String logName) throws IOException {
		AVLTree<Integer, String> t = new AVLTree<Integer, String>(
				getDefaultComparator());

		
		Set<Integer> testList = new TreeSet<Integer>();
		for (int i = 0; i < testDataSize; ++i) {
			testList.add(i);
		}
		
		for (Integer it : testList) {
			t.add(it, it.toString());
		}
		//drawTreeToFile(t, "AVLTree."+logName+".before.png");
		t.remove(keyToRemove);
		//drawTreeToFile(t, "AVLTree."+logName+".after.png");
		testList.remove(keyToRemove);
		Assert.assertEquals(testList.size(), t.size());
		Assert.assertEquals("removed element should not be in the tree", null,
				t.find(keyToRemove));
		for (Integer it : testList) {
			Assert.assertEquals(
					"not removed elements should not disappear from the tree",
					it, t.find(it).getKey());
		}
		assertTreeHeight(t, testDataSize);
		
	}
	/**
	 * Creates file in PNG format with tree visualisation
	 * @param t
	 * @throws IOException
	 */
	private void drawTreeToFile(AVLTree<Integer, String> t,String fileName) throws IOException {
		RenderedImage im=DSutils.fromDS(t.getRoot(), 35, 35);
		File output = new File(fileName);
		ImageIO.write( im, "png",output);
	}
	
	/**
	 * @param t
	 */
	private void checkBalance(AVLTree<Integer, String> t) {
		Assert.assertEquals(2, (int) t.getRoot().getKey());
		Assert.assertEquals(0, (int) t.getRoot().getBalance());
		Assert.assertEquals(1, (int) t.getRoot().getLeftChild().getKey());
		Assert.assertEquals(0, (int) t.getRoot().getLeftChild().getBalance());
		Assert.assertEquals(3, (int) t.getRoot().getRightChild().getKey());
		Assert.assertEquals(0, (int) t.getRoot().getRightChild().getBalance());
	}
	/**
	 * @param t
	 * @param test
	 */
	private void fillTree(AVLTree<Integer, String> t, int[] test) {
		for (int i = 0; i < test.length; ++i) {
			t.add(test[i], String.valueOf(test[i]));
		}
	}
	/**
	 * @param t
	 * @throws IOException 
	 */
	private void assertTreeHeight(AVLTree<Integer, String> t,
			int testDataSize) throws IOException {
		Assert.assertTrue(t.getTreeHeight() <= logbase2(testDataSize) + 1);
		int maxDiffBetweenSubtreesHeight=Math.abs(t.getTreeHeight()-t.getMinTreeHeight());
		Assert.assertTrue(maxDiffBetweenSubtreesHeight==0||maxDiffBetweenSubtreesHeight==1);
	}
	private int calcNumberOfNodesForTree(int levels){
		int number=1;
		if(levels>1){
			for(int i=1 ;i<levels;++i){
				number= number+number*TREE_ORDER;
			}
		}
		return number;
	}

	/**
	 * @return
	 */
	private Comparator<Integer> getDefaultComparator() {
		return new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};
	}

}
