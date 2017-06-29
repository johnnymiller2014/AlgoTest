package me.lab.search.tree;

interface Node {
	public static final Node EMPTY_NODE = new Node() {

		@Override
		public char getChar() {
			return 0;
		}

		@Override
		public boolean hasChild(char c) {
			return false;
		}

		@Override
		public Node getChild(char c) {
			return this;
		}

		public void addChild(char c) {
			// no op
		}

		@Override
		public long childsCount() {
			return 0;
		}

	};

	char getChar();

	boolean hasChild(char c);

	Node getChild(char c);

	void addChild(char c);

	long childsCount();
}