package com.component.client;

import java.io.InputStream;

/**
 * @author jinx
 */
public class FileReader {


    public static String getContentFromFile(String filename) throws Exception {
        try (InputStream in = FileReader.class.getClassLoader().getResourceAsStream(filename)) {
            assert in != null;
            return new String(in.readAllBytes());
        }

    }
}
