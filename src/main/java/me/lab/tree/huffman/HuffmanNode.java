package me.lab.tree.huffman;

public class HuffmanNode {

	public int key;
	public int frequency;
	HuffmanNode leftChild, rightChild;

	public HuffmanNode() {
	}

	public HuffmanNode(int key, int value) {
		super();
		this.key = key;
		this.frequency = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + frequency;
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
		HuffmanNode other = (HuffmanNode) obj;
		if (frequency != other.frequency)
			return false;
		return true;
	}

	public boolean isLeafNode() {
		return null == leftChild && null == rightChild;
	}

}
