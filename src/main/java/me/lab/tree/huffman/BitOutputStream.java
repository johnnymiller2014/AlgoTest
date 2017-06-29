package me.lab.tree.huffman;

import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

class BitOutputStream extends OutputStream {
	private static Logger log = Logger.getLogger(BitOutputStream.class);
	private int buffer = 0;
	private byte bufferPos = 0;
	private RandomAccessFile raf;

	public BitOutputStream(RandomAccessFile raf) {
		this.raf = raf;
	}

	public void writeBit(int bit) throws IOException {
		if (bit != 0x0 && bit != 0x1) {
			throw new IllegalArgumentException("bit must 1 or 0");
		}
		buffer = ((buffer << 1) | (bit & 0x1));
		bufferPos++;
		if (bufferPos == 8) {
			internalFlush();
		}
	}

	public void writeBoolean(boolean value) throws IOException {
		if (value) {
			writeBit(1);
		} else {
			writeBit(0);
		}
	}

	public void writeByte(byte value) throws IOException {
		for (int i = 0; i < 8; ++i) {
			byte tmp = (byte) (value << i);
			if (tmp < 0) {
				writeBit(1);
			} else {
				writeBit(0);
			}
		}
	}

	public void writeInt(int value) throws IOException {
		writeByte((byte) ((value >> 24) & 0xff));
		writeByte((byte) ((value >> 16) & 0xff));
		writeByte((byte) ((value >> 8) & 0xff));
		writeByte((byte) ((value >> 0) & 0xff));
	}

	public void writeLong(long value) throws IOException {
		writeInt((int) ((value >> 32) & 0xffffffff));
		writeInt((int) ((value >> 0) & 0xffffffff));
	}

	private void internalFlush() throws IOException {
		if (!(bufferPos == 0)) {
			log.debug("writing byte: 0x"
					+ Integer.toHexString(buffer).toUpperCase());
			raf.writeByte(buffer);
		}
		buffer = 0;
		bufferPos = 0;
	}

	public void flush() throws IOException {
		// if the method called after less than 8 bits have been written, we
		// need to shift bits in buffer left.
		if (bufferPos != 0) {
			while (bufferPos < 8) {
				buffer = buffer << 1;
				bufferPos++;
			}
			internalFlush();
		}
	}

	public void close() throws IOException {
		flush();

		raf.close();
	}

	@Override
	public void write(int value) throws IOException {
		// get low order 8 bits
		writeByte((byte) (value & 0xff));
	}
}