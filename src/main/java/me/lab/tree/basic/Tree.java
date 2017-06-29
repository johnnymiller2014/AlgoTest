package me.lab.tree.basic;

public class Tree {
	private Node root;

	public void insert(int key, Object value) {
		Node node = new Node(key, value);
		if (null == root) {
			root = node;
			return;
		}
		insertR(root, node);

	}

	private void insertR(Node current, Node newNode) {
		if (newNode.key < current.key) {
			if (null == current.leftChild) {
				current.leftChild = newNode;
			} else {
				insertR(current.leftChild, newNode);
			}
		} else {
			if (null == current.rightChild) {
				current.rightChild = newNode;
			} else {
				insertR(current.rightChild, newNode);
			}
		}
	}

	public Node find(int key) {
		return findR(root, key);
	}

	private Node findR(Node startFromNode, int key) {
		Node current = startFromNode;

		while (current != null) {
			if (key == current.key) {
				return current;
			}
			if (key < current.key) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
		}
		return null;

	}

	public void traverse(TraverseListener listener) {
		traverseInorderR(root, listener);
	}

	private void traverseInorderR(Node currentNode, TraverseListener listener) {
		if (null == currentNode) {
			return;
		}
		traverseInorderR(currentNode.leftChild, listener);
		listener.visit(currentNode);
		traverseInorderR(currentNode.rightChild, listener);
	}

	public Node minimum() {
		Node current = root;
		return minimumR(current);
	}

	private Node minimumR(Node current) {
		while (null != current) {
			if (null == current.leftChild) {
				return current;
			}
			current = current.leftChild;
		}
		return null;
	}

	public Node maximum() {
		Node current = root;
		while (null != current) {
			if (null == current.rightChild) {
				return current;
			}
			current = current.rightChild;
		}
		return null;
	}

	public void remove(int key) {
		Node parentOfNodeToBeRemoved = null;// can be null, if we are removing
											// root
		Node toBeRemoved = null;// can be null if not found
		Node nextInOrder = null; // next in order node - can be null
		Node ParentOfNextInOrder = null;
		Node current = root;
		while (current != null) {
			if (key == current.key) {
				toBeRemoved = current;
				break;
			}
			parentOfNodeToBeRemoved = current;
			if (key < current.key) {
				current = current.leftChild;
			} else {
				current = current.rightChild;
			}
		}
		if (null == toBeRemoved) {
			return;// trying to remove not existing node
		}
		if (null != toBeRemoved.rightChild) {
			// have to find next in order node
			ParentOfNextInOrder = toBeRemoved;
			current = toBeRemoved.rightChild;
			while (null != current) {
				if (null == current.leftChild) {
					nextInOrder = current;
					break;
				}
				ParentOfNextInOrder = current;
				current = current.leftChild;
			}
			// got next order. It's possible, it's toBeRemoved.rightChild
			if (ParentOfNextInOrder != toBeRemoved) {
				ParentOfNextInOrder.leftChild = nextInOrder.rightChild;
				nextInOrder.rightChild = null;
			}
		}
		// here is the node to be put in place of removed
		Node toBePutInPlaceOfRemoved = null;
		if (null == toBeRemoved.rightChild) {
			toBePutInPlaceOfRemoved = toBeRemoved.leftChild;
		} else {
			toBePutInPlaceOfRemoved = nextInOrder;
			if (toBeRemoved.rightChild != nextInOrder) {
				nextInOrder.rightChild = toBeRemoved.rightChild;
			}
			nextInOrder.leftChild = toBeRemoved.leftChild;
		}
		if (toBeRemoved == root) {
			root = toBePutInPlaceOfRemoved;
			return;
		} else {
			if (parentOfNodeToBeRemoved.leftChild == toBeRemoved) {
				parentOfNodeToBeRemoved.leftChild = toBePutInPlaceOfRemoved;
			} else {
				parentOfNodeToBeRemoved.rightChild = toBePutInPlaceOfRemoved;
			}
			return;
		}
	}
}
