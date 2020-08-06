package com.example.javaadvanced.io.classTop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

public class WriterAndStream {

	private static void testWriterAndStream(){
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(
//					new FileWriter("src/testtxt/writerAndStream.txt"));
					new OutputStreamWriter(
							new FileOutputStream(
									new File("src/testtxt/writerAndStream.txt")),"GBK"));
			
			bufferedWriter.write("我 爱你中国，亲爱的母亲");
			bufferedWriter.flush();
			bufferedWriter.close();
			System.out.println("end");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void testFileWriter() throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(
				new FileWriter("src/testtxt/writerAndStream.txt"));

	}

	public static void main(String[] args) {
		testWriterAndStream();
	}


}
