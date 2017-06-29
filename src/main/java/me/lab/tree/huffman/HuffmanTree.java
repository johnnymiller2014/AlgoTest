package me.lab.tree.huffman;

import java.util.Stack;

public class HuffmanTree implements Comparable<HuffmanTree> {
	public HuffmanNode root;

	public HuffmanTree(int key, int frequency) {
		root = new HuffmanNode(key, frequency);
	}

	public HuffmanTree() {

	}

	public HuffmanTree mergeTree(HuffmanTree toMerge) {
		HuffmanTree mergedTree = new HuffmanTree();
		mergedTree.root = new HuffmanNode(0, 0);
		if (toMerge.root.frequency < root.frequency) {
			mergedTree.root.leftChild = toMerge.root;
			mergedTree.root.rightChild = root;
		} else {
			mergedTree.root.leftChild = root;
			mergedTree.root.rightChild = toMerge.root;
		}
		mergedTree.root.frequency = mergedTree.root.leftChild.frequency
				+ mergedTree.root.rightChild.frequency;
		return mergedTree;
	}

	public void visit(LeafVisitListener listener) {
		Stack<Boolean> stack = new Stack<Boolean>();
		visitR(root, stack, listener);

	}

	/**
	 * 
	 * @param startFrom
	 * @param goLeft
	 * @return null if not found. A Leaf HuffmanNode if found. An non leaf
	 *         HuffmanNode if search to be continued
	 */
	public HuffmanNode find(HuffmanNode startFrom, boolean goLeft) {
		HuffmanNode current = null;
		if (null == startFrom) {
			return null;// tree is empty
		}
		if (goLeft) {
			current = startFrom.leftChild;
		} else {
			current = startFrom.rightChild;
		}
		return current;
	}

	private void visitR(HuffmanNode current, Stack<Boolean> pathToLeaf,
			LeafVisitListener listener) {
		if (null == current) {
			return;
		}
		pathToLeaf.push(false);
		visitR(current.leftChild, pathToLeaf, listener);
		pathToLeaf.pop();
		// if current node is leaf
		if (current.isLeafNode()) {
			listener.leafVisited(current, pathToLeaf);
		}
		pathToLeaf.push(true);
		visitR(current.rightChild, pathToLeaf, listener);
		pathToLeaf.pop();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HuffmanTree other = (HuffmanTree) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!(root.frequency == other.root.frequency))
			return false;
		return true;
	}

	@Override
	public int compareTo(HuffmanTree o) {
		if (null == o) {
			throw new NullPointerException();
		}

		HuffmanTree rightSide =  o;
		if (rightSide.root.frequency < root.frequency) {
			return 1;
		} else if (rightSide.root.frequency == root.frequency) {
			return 0;
		} else {
			return -1;
		}
	}

}
