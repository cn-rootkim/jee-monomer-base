package net.rootkim.core.utils;

import java.io.*;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/19
 */
public class FileUtil {

    public static void createAndWriteTxt(String path, String content) throws IOException {
        try (
                FileWriter fileWriter = new FileWriter(path);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            bufferedWriter.write(content);
        }
    }

    public static byte[] readByte(String path) throws IOException{
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                ) {
            byte[] bytes = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(bytes);
            return bytes;
        }
    }

    public static void writeByte(String path, byte[] src) throws IOException{
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ) {
            bufferedOutputStream.write(src);
        }
    }

}
