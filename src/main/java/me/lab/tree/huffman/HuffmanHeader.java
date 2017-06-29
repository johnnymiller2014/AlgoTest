package me.lab.tree.huffman;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * <pre>
 * File header format:
 * Version:java int
 * NumOfSymbols in CharacterFrequencyTuple array: java Int
 * CharacterFrequencyTuple, represented as set of tuples
 * [code, frequency] , where code is java int, frequency is java int.
 * </pre>
 * 
 * @author Filipp_Zuev
 * 
 */
public class HuffmanHeader {
	public static final Integer VERSION = new Integer(1);
	@SuppressWarnings("unused")
	private int version;
	private Integer NumOfFrequences;
	private long numOfSymbols;
	private CharacterFrequencyTuple[] characterFrequencyTuples;

	/**
	 * Build a file header to write it later to a file
	 * 
	 * @param charsFreqs
	 */
	public HuffmanHeader(SortedMap<Integer, Integer> charsFreqs,
			long symbolsCount) {
		characterFrequencyTuples = new CharacterFrequencyTuple[charsFreqs
				.size()];
		numOfSymbols = symbolsCount;
		Iterator<Integer> iter = charsFreqs.keySet().iterator();
		int index = 0;
		while (iter.hasNext()) {
			Integer character = iter.next();
			Integer freq = charsFreqs.get(character);
			characterFrequencyTuples[index] = new CharacterFrequencyTuple(
					character, freq);
			index++;
		}
		this.NumOfFrequences = characterFrequencyTuples.length;
	}

	/**
	 * 
	 * @return header size in bytes
	 */
	public int getHeaderSize() {
		return 4 + 4 + 8 + (characterFrequencyTuples.length * CharacterFrequencyTuple.SIZE);
	}

	public HuffmanHeader(RandomAccessFile input) throws IOException {
		version = input.readInt();
		NumOfFrequences = input.readInt();
		numOfSymbols = input.readLong();
		// TODO add data validation. Check for DOS attack. Check for size
		// limits. Reject all except valid
		characterFrequencyTuples = new CharacterFrequencyTuple[NumOfFrequences];
		for (int i = 0; i < NumOfFrequences; ++i) {
			int character = input.readInt();
			int freq = input.readInt();
			characterFrequencyTuples[i] = new CharacterFrequencyTuple(
					character, freq);
		}

	}

	public long getSymbolsCount() {
		return numOfSymbols;
	}

	/**
	 * Check, is the header valid. If true, the header can be used to decode
	 * compressed data.
	 */
	public boolean isValid() {
		// TODO implement
		return true;
	}

	public void write(BitOutputStream output) throws IOException {
		// BIG ENDIAN
		output.writeInt(VERSION);
		output.writeInt(NumOfFrequences);
		output.writeLong(numOfSymbols);
		for (int i = 0; i < NumOfFrequences; ++i) {
			output.write(characterFrequencyTuples[i].getBytes());
		}

	}

	public CharacterFrequencyTuple[] getFrequences() {
		if (null == characterFrequencyTuples) {
			return null;
		}
		return characterFrequencyTuples.clone();
	}

	public static byte[] getBytesFromInt(int integer) {
		byte bytes[] = new byte[4];
		bytes[0] = (byte) ((integer >>> 24) & 0xFF);
		bytes[1] = (byte) ((integer >>> 16) & 0xFF);
		bytes[2] = (byte) ((integer >>> 8) & 0xFF);
		bytes[3] = (byte) ((integer >>> 0) & 0xFF);
		return bytes;
	}

	public class CharacterFrequencyTuple {
		int character;
		int frequency;
		/**
		 * Size of the structure in bytes
		 */
		public static final int SIZE = (Integer.SIZE / 8) + (Integer.SIZE / 8);

		public CharacterFrequencyTuple(int character, int frequency) {
			super();
			this.character = character;
			this.frequency = frequency;
		}

		public byte[] getBytes() {

			byte[] ch = getBytesFromInt(character);
			byte[] fq = getBytesFromInt(frequency);
			byte bytes[] = new byte[SIZE];
			System.arraycopy(ch, 0, bytes, 0, ch.length);
			System.arraycopy(fq, 0, bytes, ch.length, fq.length);
			return bytes;
		}

	}

}
