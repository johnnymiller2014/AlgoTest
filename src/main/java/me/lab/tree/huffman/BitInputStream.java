package me.lab.tree.huffman;

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

public class BitInputStream {
	private static Logger log = Logger.getLogger(BitInputStream.class);
	private byte buffer = 0;
	private byte bufferPos = 0;
	private RandomAccessFile raf;

	public BitInputStream(RandomAccessFile raf) {
		super();
		this.raf = raf;
	}

	public void seek(long pos) throws IOException {
		raf.seek(pos);
	}

	public int readBit() throws IOException {
		int ret = 0;
		if (0 == bufferPos) {
			try {
				buffer = raf.readByte();
				log.debug("readed:" + buffer);
			} catch (EOFException e) {
				return -1;
			}
		}
		bufferPos++;
		if (8 == bufferPos) {
			bufferPos = 0;
		}
		if (buffer < 0) {
			ret = 1;
		} else {
			ret = 0;
		}
		buffer = (byte) (buffer << 1);
		return ret;
	}
	/**
	 * 
	 * @return
	 */

}
