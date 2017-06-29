package me.lab.stack;

public interface Stack<E> {
	void push(E value);

	E pop();

	E peek();

	long size();
}
