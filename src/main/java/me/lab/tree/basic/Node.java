package me.lab.tree.basic;

public class Node {
	public Node leftChild;
	public Node rightChild;
	public int key;
	public Object value;

	public Node(int key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
}
