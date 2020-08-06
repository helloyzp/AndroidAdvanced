package com.example.javaadvanced.io.test;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.Reader;

public class IOtest {


    public static void main(String[] args) throws IOException {
        //byte stream 字节流，OutputStream，InputStream

        FilterOutputStream filterOutputStream = new FilterOutputStream(new FileOutputStream(new File("")));

        DataOutputStream dataOutputStream = new DataOutputStream(filterOutputStream);
        dataOutputStream.writeInt(20);

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(filterOutputStream);
        bufferedOutputStream.write(1);

        //character-stream 字符流
        Reader reader = new Reader() {
            @Override
            public int read(char[] cbuf, int off, int len) throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {

            }
        };

    }

}
