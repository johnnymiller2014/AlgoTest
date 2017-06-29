package me.lab.tree.huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Main {
	private File inputFile = null;
	private File outputFile = null;

	public enum Mode {
		ENCODE, DECODE, INVALID
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws InvalidFileHeaderException
	 * @throws InvalidContentException
	 */
	public static void main(String[] args) throws IOException,
			InvalidFileHeaderException, InvalidContentException {
		Main main = new Main();
		main.start(args);
	}

	public void start(String[] args) throws IOException,
			InvalidFileHeaderException, InvalidContentException {
		Mode m = Mode.INVALID;
		m = parseCommandLine(args);
		try {
			switch (m) {
			case ENCODE:
				encode(inputFile, outputFile);
				break;
			case DECODE:
				decode(inputFile, outputFile);
				break;
			case INVALID:
				printErrorAndExit();
				break;
			default:
				break;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Mode parseCommandLine(String[] args) {
		Mode m = Mode.INVALID;
		if (args.length != 3) {
			printErrorAndExit();
		} else {
			if (args[0].equals("-encode")) {
				m = Mode.ENCODE;
			} else if (args[0].equals("-decode")) {
				m = Mode.DECODE;
			}
			if (m.equals(Mode.INVALID)) {
				printErrorAndExit();
			} else {
				inputFile = new File(args[1]);
				if (!inputFile.isFile() || !inputFile.canRead()) {
					printErrorAndExit();
				}
				try {
					outputFile = new File(args[2]);
					if (outputFile.exists()) {
						if (outputFile.isDirectory()) {
							printErrorAndExit();
						} else {

							if (outputFile.delete()) {
								if (!outputFile.createNewFile()) {
									printErrorAndExit();
								}
							}
						}
					}

				} catch (IOException e) {
					printErrorAndExit();
				}
			}
		}
		return m;
	}

	public void encode(File inputFile, File outputFile) throws IOException {
		RandomAccessFile inf = new RandomAccessFile(inputFile, "r");
		RandomAccessFile outf = new RandomAccessFile(outputFile, "rw");
		HuffmanCompressor compressor = new HuffmanCompressor();
		compressor.encode(inf, outf);
		inf.close();
		outf.close();
	}

	public void decode(File inputFile, File outputFile) throws IOException,
			InvalidFileHeaderException, InvalidContentException {
		RandomAccessFile inf = new RandomAccessFile(inputFile, "r");
		RandomAccessFile outf = new RandomAccessFile(outputFile, "rw");
		HuffmanCompressor compressor = new HuffmanCompressor();
		compressor.decode(inf, outf);
		inf.close();
		outf.close();
	}

	private static void printErrorAndExit() {
		System.out
				.println("usage me.lab.tree.huffman.Main -encode|-decode input_filename output_filename");
		System.exit(-1);
	}

}
