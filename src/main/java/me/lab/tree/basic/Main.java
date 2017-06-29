package me.lab.tree.basic;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Main {
	public static void main(String argv[]) {
		Main main = new Main();
		main.test();

	}

	public void test() {
		Tree t = new Tree();
		Map<Integer, String> testData = getTestData();
		Iterator<Integer> iter = testData.keySet().iterator();
		while (iter.hasNext()) {
			int key = iter.next();
			t.insert(key, testData.get(key));
		}
		t.insert(5, "5");
		t.insert(5, "5");
		t.traverse(new Main.Listener());

		int keyToBeRemoved = 5;

		System.out.println("find(5):" + t.find(keyToBeRemoved).value);
		System.out.println("minimum:" + t.minimum().value);
		System.out.println("maximum:" + t.maximum().value);
		t.remove(keyToBeRemoved);
		System.out.println("t.remove(5)");
		System.out.println("find(5):" + t.find(keyToBeRemoved));
		t.remove(keyToBeRemoved);
		System.out.println("t.remove(5)");
		System.out.println("find(5):" + t.find(keyToBeRemoved));
		t.traverse(new Main.Listener());

	}

	private Map<Integer, String> getTestData() {
		Map<Integer, String> map = new TreeMap<Integer, String>();
		for (int i = 0; i < 5; ++i) {
			map.put(i, String.valueOf(i));
		}
		return map;
	}

	public class Listener implements TraverseListener {

		@Override
		public void visit(Node node) {
			System.out.println(node.value);

		}

	}

}
