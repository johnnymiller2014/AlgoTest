package me.lab.tree.huffman;

import java.util.Stack;

public interface LeafVisitListener {
	public void leafVisited(HuffmanNode node, Stack<Boolean> pathToLeaf);

}
