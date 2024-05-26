package net.rootkim.core.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public static byte[] readByte(String path) throws IOException {
        try (
                FileInputStream fileInputStream = new FileInputStream(path);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ) {
            byte[] bytes = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(bytes);
            return bytes;
        }
    }

    public static void writeByte(String path, byte[] src) throws IOException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ) {
            bufferedOutputStream.write(src);
        }
    }

    public static void writeStream(String path, InputStream inputStream) throws IOException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        ) {
            byte[] bytes = new byte[1024];
            int length;
            while ((length = inputStream.read(bytes)) > 0) {
                bufferedOutputStream.write(bytes, 0, length);
            }
        }
    }

    public static void readStream(OutputStream outputStream, String path) throws IOException {
        try (
                FileInputStream inputStream = new FileInputStream(path);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        ) {
            byte[] bytes = new byte[1024];
            int length;
            while ((length = bufferedInputStream.read(bytes)) > 0) {
                bufferedOutputStream.write(bytes, 0, length);
            }
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteFileList(List<String> pathList) {
        pathList.forEach(FileUtil::deleteFile);
    }

    public static String encodePngToBase64(String pngPath) throws IOException {
        byte[] bytes = readByte(pngPath);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void zipFolder(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipFolder(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());
                zos.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zos.write(bytes, 0, length);
                }
            }
        }
    }

    public static void delDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    delDir(file);
                }
            }
        }
        dir.delete();
    }
}
