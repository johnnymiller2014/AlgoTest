package me.lab.tree.avl;

import java.awt.Color;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;

import javax.imageio.ImageIO;

import org.StructureGraphic.v1.DSTreeNode;
import org.StructureGraphic.v1.DSutils;

public class AVLTree<K, V> {
	/**
	 * Amount of keys in the tree
	 */
	private int size;
	/**
	 * root node
	 */
	private Node<K, V> root;
	/**
	 * Comparator, to compare generic keys
	 */
	private Comparator<? super K> c;
	/**
	 * node, that has been found while latest searching, or null, if nothing has
	 * been found
	 */
	private Node<K, V> foundNode = null;

	public AVLTree(Comparator<? super K> c) {
		this.c = c;
	}

	public void clear() {
		root = null;
		foundNode = null;
		size = 0;
	}

	public void add(K key, V value) {
		// create root if does not exists
		if (null == root) {
			root = new Node<K, V>(key, value, null);
			size++;
			return;
		}
		Node<K, V> current = root;
		Node<K, V> nodeToRefactor =  null; 
		// search for node with node.key==key
		while (current!=null) {
			Node<K, V> next = null;
			int cmp = c.compare(key, current.key);
			if (cmp < 0) {
				next = current.getLeftChild();
				if(null==next){
					//add as left child
					current.setLeftChild(new Node<K, V>(key, value, current));
					nodeToRefactor = current.getLeftChild();
				}
			} else if (cmp == 0) {
				// if found, return. We don't allow duplicates
				return;
			} else {
				next = current.getRightChild();
				if(null==next){
					//add as right child
					current.setRightChild(new Node<K, V>(key, value, current));
					nodeToRefactor = current.getRightChild();
				}
			}
			current = next;
		}
		// increment tree.size
		this.size++;
		// rebalance up
		rebalanceAfterInsertion(nodeToRefactor);

	}

	/**
	 * @param nodeToRefactor
	 */
	private void rebalanceAfterInsertion(Node<K, V> nodeToRefactor) {
		// get up through tree up to root, correcting balance, continue while
		// corrected balance is -1 or +1
		// if corrected balance is -2 or +2 do rotate, then exit
		// if corrected balance is 0 we can exit
		while (nodeToRefactor!=null) {
			nodeToRefactor.updateHeight();
			if (nodeToRefactor.getBalance() <= -2) {
				correctDisbalancetoRight(nodeToRefactor);
			} else if (nodeToRefactor.getBalance() >= 2) {
				correctDisbalancetoLeft(nodeToRefactor);
			}
			nodeToRefactor = nodeToRefactor.parent;
		}

	}

	/**
	 * Find node with key equal to key. For performance reason, it's separated
	 * from {AVLTree{@link #internalFindForAdd(Node, Object)}
	 * 
	 * Sets AVLTree<K,V> {@link #foundNode} to node found or null.
	 * 
	 * @param startFrom
	 *            node where the method starts search
	 * @param key
	 *            to be searched
	 */
	private void internalFind(Node<K, V> startFrom, K key) {
		foundNode = null;
		if (null == startFrom) {
			return;
		}
		while (startFrom != null) {
			int cmp = c.compare(key, startFrom.key);
			if (cmp < 0) {
				startFrom = startFrom.getLeftChild();
			} else if (cmp == 0) {
				// if found, return. We don't allow duplicates
				foundNode = startFrom;
				break;
			} else {
				startFrom = startFrom.getRightChild();
			}
		}
	}

	public Node<K, V> find(K key) {
		internalFind(root, key);
		return foundNode;
	}
	public void remove(K key) {
		// find node to remove
		internalFind(root, key);
		if (null == foundNode) {
			return;// no node with this key
		}
		Node<K, V> nodeToRemoved = foundNode;
		Node<K, V> replacementNode = null;
		//find replacementNode
         replacementNode = getReplacementNode(nodeToRemoved);
         
         // Find the parent of the replacement node to re-factor the
         // height/balance of the tree
         Node<K, V> nodeToRefactor = null;
         if (replacementNode!=null){
        	 nodeToRefactor = replacementNode.parent;
         }
         if(nodeToRefactor==null){
        	 nodeToRefactor = nodeToRemoved.parent;
         }
         if(nodeToRefactor!=null && nodeToRefactor==nodeToRemoved){
        	 nodeToRefactor=replacementNode;
         }
         
         // Replace the node
         replaceNodeWithNode(nodeToRemoved, replacementNode);
         		
		// refactor tree starting from nodeToRefactor up to root
		rebalanceAfterDeletion(nodeToRefactor);
	}

	protected void replaceNodeWithNode(Node<K, V> nodeToRemoved,
			Node<K, V> replacementNode) {
		if(replacementNode!=null){
			
			// Save for later
            Node<K,V> replacementNodeLeft = replacementNode.leftChild;
            Node<K,V> replacementNodeRight = replacementNode.rightChild;
			
            // Replace replacementNode's branches with nodeToRemove's branches
            Node<K,V> nodeToRemoveLeft = nodeToRemoved.leftChild;
            
            if (nodeToRemoveLeft != null && !nodeToRemoveLeft.equals(replacementNode)) {
                replacementNode.leftChild = nodeToRemoveLeft;
                nodeToRemoveLeft.parent = replacementNode;
            }
            Node<K,V> nodeToRemoveRight = nodeToRemoved.rightChild;
            if (nodeToRemoveRight != null && !nodeToRemoveRight.equals(replacementNode)) {
                replacementNode.rightChild = nodeToRemoveRight;
                nodeToRemoveRight.parent = replacementNode;
            }
            
         // Remove link from replacementNode's parent to replacement
            Node<K,V> replacementParent = replacementNode.parent;
            if (replacementParent != null && !replacementParent.equals(nodeToRemoved)) {
                Node<K,V> replacementParentLeft = replacementParent.leftChild;
                Node<K,V> replacementParentRight = replacementParent.rightChild;
                if (replacementParentLeft != null && replacementParentLeft.equals(replacementNode)) {
                    replacementParent.leftChild = replacementNodeRight;
                    if (replacementNodeRight != null) replacementNodeRight.parent = replacementParent;
                } else if (replacementParentRight != null && replacementParentRight.equals(replacementNode)) {
                    //this branch should never happen, because we always take successor for replacement.
                	//but let's keep it to have more general usable method
                	replacementParent.rightChild = replacementNodeLeft;
                    if (replacementNodeLeft != null) replacementNodeLeft.parent = replacementParent;
                }
            }
    
			
		};
	     // Update the link in the tree from the nodeToRemoved to the
        // replacementNode
        Node<K,V> parent = nodeToRemoved.parent;
        if (parent == null) {
            // Replacing the root node
            root = replacementNode;
            if (root != null) root.parent = null;
        } else if (parent.leftChild != null && (c.compare(parent.leftChild.getKey(),nodeToRemoved.getKey()) == 0)) {
            parent.leftChild = replacementNode;
            if (replacementNode != null) replacementNode.parent = parent;
        } else if (parent.rightChild != null && (c.compare(parent.rightChild.getKey(),nodeToRemoved.getKey()) == 0)) {
            parent.rightChild = replacementNode;
            if (replacementNode != null) replacementNode.parent = parent;
        }
        size--;
		
	}

	/**
	 * @param nodeToRemoved
	 * @return Node<K, V>, that can be used as replacement, or null, if the nodeToRemoved is a leaf
	 */
	protected Node<K, V> getReplacementNode(Node<K, V> nodeToRemoved) {
		Node<K, V> replacementNode;
		if(nodeToRemoved.getLeftChild()==null && nodeToRemoved.getRightChild()!=null){//only right child
			replacementNode = nodeToRemoved.getRightChild();
		}else if(nodeToRemoved.getRightChild()==null && nodeToRemoved.getLeftChild()!=null){//only left child
			replacementNode = nodeToRemoved.getLeftChild();
		}else{
			//both childs
			replacementNode = getLeast(nodeToRemoved.rightChild);
			if(null==replacementNode){
				replacementNode=nodeToRemoved.rightChild;
			}
		}
		return replacementNode;
	}

	/**
	 * Takes node to start rebalancing from.
	 * 
	 * @param nodeToRefactor
	 * @return
	 */
	private void rebalanceAfterDeletion(Node<K, V> nodeToRefactor) {
		// rebalance - retrace from replacementNode node(that you put instead of
		// removed) up to the root.
		// if after rebalancing:
		// 1. a parent balance becomes -1 or +1. stop rebalancing and exit
		// 2. a parent balance becomes zero. Continue rebalancing
		// 3. a parent balance becomes is -2 or +2. rotate, after ti continue rebalancing

		// If the rotation leaves the subtree's balance factor at 0 then the
		// retracing towards
		// the root must continue since the height of this subtree has decreased
		// by one.
		// This is in contrast to an insertion where a rotation resulting in a
		// balance factor of 0
		// indicated that the subtree's height has remained unchanged.

		while (nodeToRefactor != null) {
			//update balance of nodeToRefactor
			nodeToRefactor.updateHeight();
			//balance after delete
			if (nodeToRefactor.getBalance() <= -2) {
				correctDisbalancetoRight(nodeToRefactor);
			} else if (nodeToRefactor.getBalance() >= 2) {
				correctDisbalancetoLeft(nodeToRefactor);
			}
			//continue to parent
			nodeToRefactor = nodeToRefactor.parent;

		}
	}

	public int size() {
		return size;
	}


	public Node<K, V> getRoot() {
		return root;
	}

	public int getTreeHeight() {
		int height = 0;
		Node<K, V> current = root;
		while (current != null) {
			height++;
			if (null == current.rightChild && null != current.leftChild) {
				current = current.leftChild;
			} else if (null == current.leftChild && null!=current.rightChild) {
				current = current.rightChild;
			} else if (null != current.leftChild && null != current.rightChild) {
				if(current.leftChild.height<current.rightChild.height){
					current = current.rightChild;
				}else{
					current = current.leftChild;
				}
			}else{
				//both childs are null
				break;
			}
		}
		return height;
	}

	public int getMinTreeHeight() {
		int height = 0;
		Node<K, V> current = root;
		while (current != null) {
			height++;
			if (null == current.rightChild && null != current.leftChild) {
				current = current.leftChild;
			} else if (null == current.leftChild && null!=current.rightChild) {
				current = current.rightChild;
			} else if (null != current.leftChild && null != current.rightChild) {
				if(current.leftChild.height<current.rightChild.height){
					current = current.leftChild;
				}else{
					current = current.rightChild;
				}
			}else{
				//both childs are null
				break;
			}
		}
		return height;
	}

	/**
	 * find the smallest node in the (sub)tree or null
	 * @param startingNode
	 * @return
	 */
	private Node<K, V> getLeast(Node<K, V> startingNode) {
		if(null==startingNode){
			return null;
		}
		Node<K, V> lesser = startingNode.leftChild;
		while (lesser!=null){
			Node<K, V> node = lesser.leftChild;
			if(node!=null){
				lesser = node;
			}else{
				break;
			}
		}
		return lesser;
	}

	private Node<K, V> correctDisbalancetoRight(Node<K, V> node) {
		assert node.getBalance() == -2;
		Node<K, V> newRootOfSubtree = null;
		Node<K, V> top=null;
		Node<K, V> left = null;
		// rotate and fix balance
		assert node.leftChild.getBalance() == -1
				|| node.leftChild.getBalance() == 1;
		if (node.leftChild.getBalance() == -1) {
			// left left
			top = node;
			left = node.leftChild;
			rotateRight(top, left);
			newRootOfSubtree=left;
		} else {
			// left right
			top = node;
			left = node.leftChild;
			Node<K, V> leftRight = left.rightChild;
			//
			rotateLeft(left, leftRight);
			// do left rotation with top as root
			rotateRight(top, top.leftChild);
			newRootOfSubtree=leftRight;
		}
		newRootOfSubtree.rightChild.updateHeight();
		newRootOfSubtree.leftChild.updateHeight();
		newRootOfSubtree.updateHeight();
		return newRootOfSubtree;
	}

	private Node<K, V> correctDisbalancetoLeft(Node<K, V> node) {
		assert node.getBalance() == 2;
		Node<K, V> newRootOfSubtree = null;//
		Node<K, V> top = null;
		Node<K, V> right=null;
		// rotate and fix balance
		assert node.rightChild.getBalance() == 1
				|| node.rightChild.getBalance() == -1;
		if (node.rightChild.getBalance() == 1) {
			// right right
			top = node;
			right = node.rightChild;
			rotateLeft(top, right);
			newRootOfSubtree=right;
		} else {
			// right left
			top = node;
			right = node.rightChild;
			Node<K, V> rightLeft = right.leftChild;
			
			// first, do right rotation with right as root
			rotateRight(right, rightLeft);
			// do left rotation with top as root
			rotateLeft(top, top.rightChild);
			newRootOfSubtree=rightLeft;
		}
		newRootOfSubtree.rightChild.updateHeight();
		newRootOfSubtree.leftChild.updateHeight();
		newRootOfSubtree.updateHeight();
		return newRootOfSubtree;

	}

	/**
	 * @param top
	 * @param node
	 */
	private void rotateRight(Node<K, V> top, Node<K, V> node) {
		top.leftChild = node.rightChild;
		if (null != top.leftChild) {
			top.leftChild.parent = top;
		}
		node.rightChild = top;

		setParent(top, node);
	}

	/**
	 * rotates left, changes tree.root if top==tree.root.
	 * 
	 * Ignores balance. Correct balance after this method
	 * 
	 * @param top
	 * @param node
	 */
	private void rotateLeft(Node<K, V> top, Node<K, V> node) {
		top.rightChild = node.leftChild;
		if (null != top.rightChild) {
			top.rightChild.parent = top;
		}
		node.leftChild = top;
		setParent(top, node);
	}

	/**
	 * disconnects node from its parent and puts childOfNode on it's place Sets
	 * node.parent=childOfNode, to be used for rotation
	 * 
	 * @param node
	 *            a node, that we want to disconnect from it's parent
	 * @param childOfNode
	 *            A child of top or null
	 */
	private void setParent(Node<K, V> node, Node<K, V> childOfNode) {
		if (node == root) {
			root = childOfNode;
			root.parent = null;
		} else if (node.parent.leftChild == node) {
			node.parent.leftChild = childOfNode;
			if (null != childOfNode) {
				childOfNode.parent = node.parent;
			}
		} else {
			node.parent.rightChild = childOfNode;
			if (null != childOfNode) {
				childOfNode.parent = node.parent;
			}
		}
		node.parent = childOfNode;
	}
	/**
	 * For debug only
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void drawTreeToFile() throws IOException {
		RenderedImage im = DSutils.fromDS(root, 35, 35);
		File output = new File("AVLTree.current.png");
		ImageIO.write(im, "png", output);
	}

	@SuppressWarnings("hiding")
	class Node<K, V> implements DSTreeNode {
		private int height=1;
		private K key;
		private V value;

		public V getValue() {
			return value;
		}

		private Node<K, V> leftChild, rightChild, parent;

		Node(K key, V value, Node<K, V> parent) {
			this.key = key;
			this.value = value;
			this.parent = parent;
		}

		/**
         * Updates the height of this node based on it's children.
         */
        protected void updateHeight() {
            int leftHeight = 0;
            int rightHeight = 0;
            if (leftChild != null) {
                leftHeight = leftChild.height;
            }
            if (rightChild != null) {
                rightHeight = rightChild.height;
            }
            if (leftHeight > rightHeight) {
                height = leftHeight + 1;
            } else {
                height = rightHeight + 1;
            }
        }

        /**
         * Get the balance factor for this node.
         * 
         * @return An integer representing the balance factor for this node. It
         *         will be negative if the left branch is longer than the
         *         right branch.
         */
        protected int getBalance() {
            int leftHeight = 0;
            int rightHeight = 0;
            if (leftChild != null) {
                leftHeight = leftChild.height;
            }
            if (rightChild != null) {
                rightHeight = rightChild.height;
            }
            return rightHeight - leftHeight;
        }
		public K getKey() {
			return key;
		}

		public Node<K, V> getLeftChild() {
			return leftChild;
		}

		void setLeftChild(Node<K, V> leftChild) {
			this.leftChild = leftChild;
		}

		public Node<K, V> getRightChild() {
			return rightChild;
		}

		public void setRightChild(Node<K, V> rightChild) {
			this.rightChild = rightChild;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{key:");
			sb.append(key);
			sb.append(",balance:");
			sb.append(getBalance());
			sb.append(",left:");
			sb.append(leftChild == null ? null : leftChild.key);
			sb.append(",right:");
			sb.append(rightChild == null ? null : rightChild.key);
			sb.append("}");
			return sb.toString();
		}

		@Override
		public DSTreeNode[] DSgetChildren() {

			return new DSTreeNode[] { leftChild, rightChild };
		}

		@Override
		public Color DSgetColor() {
			return Color.GREEN;
		}

		@Override
		public Object DSgetValue() {
			return "" + key + "(" + getBalance() + ")";
		}
	}
}
