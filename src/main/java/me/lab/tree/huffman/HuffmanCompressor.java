package me.lab.tree.huffman;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

import me.lab.tree.huffman.HuffmanHeader.CharacterFrequencyTuple;

import org.apache.log4j.Logger;

public class HuffmanCompressor {
	private static Logger log = Logger.getLogger(HuffmanCompressor.class);
	private long symbolsCount = 0;

	public void encode(RandomAccessFile input, RandomAccessFile output)
			throws IOException {
		// get the content to be encoded
		// count frequencies
		// build Huffman tree
		// create fast Huffman code lookup table
		// create output file
		// -serialize and write Huffman tree
		// -write compressed content

		SortedMap<Integer, Integer> charsFreqs = countFrequences(input);
		PriorityQueue<HuffmanTree> pq = buildPriorityQuee(charsFreqs);
		HuffmanTree tree = buildHuffmanTree(pq);

		Map<Integer, Stack<Boolean>> codeLookupTable = buildCodeLookupTable(
				charsFreqs, tree);
		// debugDumpCodelookupTable(codeLookupTable);
		writeFileHeader(output, charsFreqs);
		writeCompressedContent(input, output, codeLookupTable);
	}

	public void decode(RandomAccessFile input, RandomAccessFile output)
			throws IOException, InvalidFileHeaderException,
			InvalidContentException {
		// read file header
		HuffmanHeader header = new HuffmanHeader(input);
		if (!header.isValid()) {
			throw new InvalidFileHeaderException();
		}
		// get frequencies from file header
		CharacterFrequencyTuple[] fq = header.getFrequences();
		SortedMap<Integer, Integer> charsFreqs = buildCharacterFrquencyTablefromFileHeader(fq);
		PriorityQueue<HuffmanTree> pq = buildPriorityQuee(charsFreqs);
		HuffmanTree tree = buildHuffmanTree(pq);
		// debugDumpCodelookupTable(codeLookupTable);
		// read compressed content;
		BitInputStream bis = new BitInputStream(input);
		bis.seek(header.getHeaderSize());
		decompressContent(output, header, tree, bis);
	}

	private void decompressContent(RandomAccessFile output,
			HuffmanHeader header, HuffmanTree tree, BitInputStream bis)
			throws IOException, InvalidContentException {
		int readed = bis.readBit();
		HuffmanNode current = tree.root;
		long symbolsCount = 0;
		while (-1 != readed) {
			// not EOF
			// look for appropriate symbol in Huffman tree
			boolean goLeft = (0 == readed) ? true : false;
			current = tree.find(current, goLeft);
			if (null == current) {
				// ERROR in decode! The input file has bit sequence, that does
				// not has a corresponding symbol in our Huffman tree
				throw new InvalidContentException();
			}

			if (current.isLeafNode()) {
				// found a symbol
				symbolsCount++;
				/*
				 * Is it the best way to correctly decode? The issue is - we can
				 * have codes like "00". And our writes to file/reads from file
				 * are always byte aligned. It means, we can get last byte like
				 * 11111100, where last two zeroes are simply padding. We need a
				 * way to distinguish this padding from a real bit code
				 */
				if (symbolsCount > header.getSymbolsCount()) {
					break;
				}
				log.debug(current.key + "(" + Character.toChars(current.key)[0]
						+ ")");
				output.writeByte(current.key);
				current = tree.root;
			}
			// current is a non leaf node, restart search from this node for
			// next bit
			readed = bis.readBit();
		}
	}

	private SortedMap<Integer, Integer> buildCharacterFrquencyTablefromFileHeader(
			CharacterFrequencyTuple[] fq) {
		SortedMap<Integer, Integer> charsFreqs = new TreeMap<Integer, Integer>();
		for (int i = 0; i < fq.length; ++i) {
			charsFreqs.put(fq[i].character, fq[i].frequency);
		}
		return charsFreqs;
	}

	@SuppressWarnings("unused")
	// feel free to call this for debug
	private void debugDumpCodelookupTable(
			Map<Integer, Stack<Boolean>> codeLookupTable) {
		Iterator<Integer> iter = codeLookupTable.keySet().iterator();
		while (iter.hasNext()) {
			Integer key = iter.next();
			Stack<Boolean> bitCode = codeLookupTable.get(key);
			debugDumpBitCode(key, bitCode);
		}
	}

	private void debugDumpBitCode(Integer key, Stack<Boolean> bitCode) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bitCode.size(); i++) {
			sb.append((bitCode.get(i)) ? 1 : 0);
		}
		log.debug(Character.toChars(key)[0] + "(" + key + ")=>" + sb.toString());
	}

	/**
	 * 
	 * @param input
	 * @param output
	 * @param codeLookupTable
	 * @throws IOException
	 */
	private void writeFileHeader(RandomAccessFile output,
			SortedMap<Integer, Integer> charsFreqs) throws IOException {

		BitOutputStream bitOutput = new BitOutputStream(output);
		HuffmanHeader header = new HuffmanHeader(charsFreqs, symbolsCount);
		header.write(bitOutput);
		bitOutput.flush();

	}

	private void writeCompressedContent(RandomAccessFile input,
			RandomAccessFile output,
			Map<Integer, Stack<Boolean>> codeLookupTable) throws IOException {
		int readed = -1;
		@SuppressWarnings("resource")
		// we shouldn't close output here, because we have not opened it here.
		BitOutputStream bitOutput = new BitOutputStream(output);
		while ((readed = input.read()) != -1) {
			Stack<Boolean> bitCode = codeLookupTable.get(readed);
			StringBuilder sb = new StringBuilder();
			sb.append("writing char:" + Character.toChars(readed)[0] + "("
					+ readed + ")Bitcode(");
			for (int i = 0; i < bitCode.size(); i++) {
				sb.append(bitCode.get(i) ? 1 : 0);
				bitOutput.writeBoolean(bitCode.get(i));
			}
			sb.append(")");
			log.debug(sb.toString());
			// debugDumpBitCode(readed, bitCode);
		}
		bitOutput.flush();
	}

	private Map<Integer, Stack<Boolean>> buildCodeLookupTable(
			SortedMap<Integer, Integer> charsFreqs, HuffmanTree tree) {
		final SortedMap<Integer, Stack<Boolean>> codeLookupTable = new TreeMap<Integer, Stack<Boolean>>();
		tree.visit(new LeafVisitListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void leafVisited(HuffmanNode node, Stack<Boolean> pathToLeaf) {
				codeLookupTable.put(node.key,
						(Stack<Boolean>) pathToLeaf.clone());
			}
		});

		return codeLookupTable;
	}

	private HuffmanTree buildHuffmanTree(PriorityQueue<HuffmanTree> pq) {
		HuffmanTree ret = null;
		while (!pq.isEmpty()) {
			HuffmanTree lowestTree = pq.poll();
			HuffmanTree biggerTree = pq.poll();
			if (null == biggerTree) {
				// we have only one element in the PQ, we got the Huffman tree
				// builded!
				ret = lowestTree;
				break;
			} else {
				// merge lowest and next elements and put back in the pq
				pq.add(lowestTree.mergeTree(biggerTree));
			}
		}
		return ret;
	}

	private PriorityQueue<HuffmanTree> buildPriorityQuee(
			SortedMap<Integer, Integer> charsFreqs) {
		Iterator<Integer> iter = charsFreqs.keySet().iterator();
		PriorityQueue<HuffmanTree> pq = new PriorityQueue<HuffmanTree>(
				charsFreqs.size(), new Comparator<HuffmanTree>() {

					@Override
					public int compare(HuffmanTree o1, HuffmanTree o2) {
						int ret = o1.root.frequency - o2.root.frequency;
						if (0 == ret) {
							// if we have different symbols with same frequency
							// - we must put these in Huffman tree in a
							// predefined and stable order.
							// it's necessary, because we want always have same
							// tree for same text.
							ret = o1.root.key - o2.root.key;
						}
						return ret;
					}
				});
		while (iter.hasNext()) {
			int character = (int) iter.next();
			int frequency = charsFreqs.get(character);
			HuffmanTree t = new HuffmanTree(character, frequency);
			pq.add(t);
		}
		return pq;
	}

	private SortedMap<Integer, Integer> countFrequences(RandomAccessFile input)
			throws IOException {
		int readed = -1;
		SortedMap<Integer, Integer> charsFreqs = new TreeMap<Integer, Integer>();
		while ((readed = input.read()) != -1) {
			this.symbolsCount++;
			Integer frequency = charsFreqs.get(readed);
			if (null == frequency) {
				// first time we got this symbol
				frequency = 1;
			} else {
				frequency++;
			}
			charsFreqs.put(readed, frequency);
		}
		input.seek(0);
		return charsFreqs;
	}
}
