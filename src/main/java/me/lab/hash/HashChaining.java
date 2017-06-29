package me.lab.hash;

public class HashChaining<E> {
	private static int INITIAL_CAPACITY = 16;
	private static float loadFactor = 0.75f;
	private int size = 0;
	@SuppressWarnings("unchecked")
	private Entry<E> table[] = new Entry[INITIAL_CAPACITY];

	public void add(E item) {
		if (size >= table.length * loadFactor) {
			resize(table.length * 2);
		}
		Entry<E> e = table[getIndex(item, table.length)];
		Entry<E> newEntry = new Entry<E>(item, e);
		table[getIndex(item, table.length)] = newEntry;
		size++;
	}

	public E get(E item) {
		Entry<E> e;
		for (e = table[getIndex(item, table.length)]; e != null; e = e.next()) {
			if (e.key == item) {
				break;
			}
		}
		return (E) (e == null ? null : e.key);
	}

	public void remove(E item) {
		Entry<E> e, previous = null;
		for (e = table[getIndex(item, table.length)]; e != null; previous = e, e = e
				.next()) {

			if (e.key == item) {
				if (null == previous) {
					table[getIndex(item, table.length)] = e.next();
				} else {
					previous.next = e.next();
				}
				break;
			}
		}
	}

	public int size() {
		return size;
	}

	public int capacity() {
		return table.length;
	}

	/*
	 * For performance statistic
	 */
	public PerformanceStat performanceStat() {
		int maxClusterSize = 0;
		float avgItemsPerCell = size() / capacity();
		int usedCells = 0;
		for (int i = 0; i < table.length; ++i) {
			Entry<E> e = table[i];
			int localClusterSize = 0;
			if (null != e) {
				usedCells++;
			}
			while (e != null) {
				localClusterSize++;
				e = e.next();
			}
			if (localClusterSize > maxClusterSize) {
				maxClusterSize = localClusterSize;
			}
		}
		return new PerformanceStat(maxClusterSize, avgItemsPerCell, usedCells);
	}

	private void resize(int newSize) {
		@SuppressWarnings("unchecked")
		Entry<E>[] newTable = new Entry[newSize];
		for (int i = 0; i < table.length; ++i) {
			Entry<E> e = table[i];
			while (null != e) {
				// transfer e
				int index = getIndex(e.key(), newTable.length);
				Entry<E> newTableEntry = newTable[index];
				newTable[index] = new Entry<E>(e.key, newTableEntry);
				e = e.next();
			}
		}
		table = newTable;
	}

	private int getIndex(E key, int tableLength) {
		int hash = key.hashCode();
		// make sure the hash code is NOT negative
		hash = hash & Integer.MAX_VALUE;
		return hash % tableLength;
	}

	class PerformanceStat {
		private int maxClusterSize;
		private float avgItemsPerCell1;
		private int usedCells;

		public PerformanceStat(int maxClusterSize, float avgItemsPerCell1,
				int usedCells) {
			super();
			this.maxClusterSize = maxClusterSize;
			this.avgItemsPerCell1 = avgItemsPerCell1;
			this.usedCells = usedCells;
		}

		public int UsedCells() {
			return usedCells;
		}

		int maxClusterSize() {
			return maxClusterSize;
		};

		float avgItemsPerCell() {
			return avgItemsPerCell1;
		};

	}

	@SuppressWarnings("hiding")
	class Entry<E> {
		private Entry<E> next;
		private E key;

		Entry(E key, Entry<E> next) {
			this.key = key;
			this.next = next;
		}

		public E key() {
			return key;
		}

		public Entry<E> next() {
			return next;
		}
	}
}
