package com.example.javaadvanced.io.classTop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * DataInputStream/DataOutputStream 用于方便读写java的基本数据类型
 */
public class DataStreamTest {

	public static void main(String[] args) throws IOException {
		testDataOutPutStream();
		testDataInputStreamI();
	}
	
	private static void testDataOutPutStream() {
		
		try {
//			File file = new File("src/testtxt/tataStreamTest.txt");
			DataOutputStream out = new DataOutputStream(
					new	BufferedOutputStream(
					new FileOutputStream(new File(Constants.DATA_PATH+ "tataStreamTest.txt"))));
			
			out.writeBoolean(true);
            out.writeByte((byte)0x41);
            out.writeChar((char)0x4243);
            out.writeShort((short)0x4445);
            out.writeInt(0x12345678);
            out.writeLong(0x987654321L);

            out.writeUTF("abcdefghijklmnopqrstuvwxyz严12");
            out.writeLong(0x023433L);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testDataInputStreamI() {
		try {
			File file = new File(Constants.DATA_PATH+ "tataStreamTest.txt");
			DataInputStream in = new DataInputStream(
					new BufferedInputStream(
					new FileInputStream(file)));
			
			//System.out.println(Long.toHexString(in.readLong()));//如果不按写入的顺序读取数据，读出的数据将是错误的
			System.out.println(in.readBoolean());
			System.out.println(byteToHexString(in.readByte()));
			System.out.println(charToHexString(in.readChar()));
			System.out.println(shortToHexString(in.readShort()));
			System.out.println(Integer.toHexString(in.readInt()));
			System.out.println(Long.toHexString(in.readLong()));
			System.out.println(in.readUTF());
			System.out.println(Long.toHexString(in.readLong()));
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 打印byte对应的16进制的字符串
    private static String byteToHexString(byte val) {
        return Integer.toHexString(val & 0xff);
    }

	// 打印char对应的16进制的字符串
    private static String charToHexString(char val) {
        return Integer.toHexString(val);
    }

	// 打印short对应的16进制的字符串
    private static String shortToHexString(short val) {
        return Integer.toHexString(val & 0xffff);
    }

}
