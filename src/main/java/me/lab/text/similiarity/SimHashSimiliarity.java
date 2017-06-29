package me.lab.text.similiarity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.StringTokenizer;

public class SimHashSimiliarity {
	public double similiarity(String doc1, String doc2) {
		List<String> tokens1 = tokenize(doc1);
		List<String> tokens2 = tokenize(doc2);
		BitSet simHash1 = getSimHash(tokens1);
		BitSet simHash2 = getSimHash(tokens2);
		return calcHammingDistance(simHash1, simHash2);

	}

	private double calcHammingDistance(BitSet simHash1, BitSet simHash2) {
		simHash1.xor(simHash2);
		return 1.0 - (double) simHash1.cardinality() / simHash1.size();
	}

	protected List<String> tokenize(String doc) {
		StringTokenizer st = new StringTokenizer(doc);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			tokens.add(st.nextToken());
		}
		return tokens;
	}

	protected BitSet getSimHash(List<String> tokens) {
		ArrayList<BitSet> tokensHashes = new ArrayList<BitSet>();
		for (String t : tokens) {
			String key = t.toString();
			byte[] bytes = null;
			bytes = calcualteNormalHash(key, bytes);
			BitSet ret = convertBytesToBitSet(bytes);
			tokensHashes.add(ret);

		}
		long features[] = new long[tokensHashes.get(0).size()];
		for (int i = 0; i < tokensHashes.size(); ++i) {
			BitSet hash = tokensHashes.get(i);
			for (int index = 0; index < hash.size(); index++) {
				if (hash.get(index)) {
					features[index]++;// features[index]+item.weight; //can be extended to change weight per token
				} else {
					features[index]--;
				}
			}
		}
		BitSet ret = new BitSet();
		for (int idx = 0; idx < features.length; ++idx) {
			if (features[idx] > 0) {
				ret.set(idx);
			}
		}
		return ret;
	}

	private BitSet convertBytesToBitSet(byte[] bytes) {
		BitSet ret = new BitSet(bytes.length * 8);
		for (int k = 0; k < bytes.length * 8; k++) {
			if (bitTest(bytes, k))
				ret.set(k);
		}
		return ret;
	}

	private byte[] calcualteNormalHash(String key, byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(key.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytes;
	}

	private static boolean bitTest(byte[] data, int k) {
		int i = k / 8;
		int j = k % 8;
		byte v = data[i];
		int v2 = v & 0xFF;
		return ((v2 >>> (7 - j)) & 0x01) > 0;
	}
}
