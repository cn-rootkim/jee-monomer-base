package net.rootkim.core.utils;

import ws.schild.jave.process.ProcessWrapper;
import ws.schild.jave.process.ffmpeg.DefaultFFMPEGLocator;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ffmpeg
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/19
 */
public class FfmpegUtil {
    public static void main(String[] args) throws IOException {
        String folderPath = "E:\\videos\\";
        File folder = new File(folderPath);
        List<String> filePathList = new ArrayList<>();
        for (File file : folder.listFiles()) {
            filePathList.add(file.getName());
        }
        filePathList = filePathList.stream().sorted((fileName1, fileName2) -> {
            Integer no1 = Integer.parseInt(fileName1.split("\\.")[0]);
            Integer no2 = Integer.parseInt(fileName2.split("\\.")[0]);
            return no1.compareTo(no2);
        }).collect(Collectors.toList());
        String filePaths = filePathList.stream().map(fileName -> "file '" + folderPath + fileName + "'").collect(Collectors.joining("\n"));
        File fileList = new File(folderPath + "fileList.txt");
        FileUtil.createAndWriteTxt(fileList.getPath(), filePaths);
        File mp4 = new File(folderPath + "result.mp4");
        //合成ts为mp4
        ProcessWrapper ffmpeg = new DefaultFFMPEGLocator().createExecutor();
        ffmpeg.addArgument("-f");
        ffmpeg.addArgument("concat");
        ffmpeg.addArgument("-safe");
        ffmpeg.addArgument("0");
        ffmpeg.addArgument("-i");
        ffmpeg.addArgument(fileList.getPath());
        ffmpeg.addArgument("-c");
        ffmpeg.addArgument("copy");
        ffmpeg.addArgument(mp4.getPath());
        ffmpeg.execute();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(ffmpeg.getErrorStream()))) {
            String line;
            // 该方法阻塞线程，直至合成成功
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
