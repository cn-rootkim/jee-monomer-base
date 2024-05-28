package net.rootkim.core.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ffmpeg
 *
 * @author RootKim[rootkim.net]
 * @since 2024/5/19
 */
@Slf4j
public class FfmpegUtil {

    /**
     * 合成ts为mp4
     * @param tsDirPath
     * @param mp4FilePath
     */
    public static void convertTsListToMp4(String tsDirPath, String mp4FilePath) {
        File tsDir = new File(tsDirPath);
        List<String> filePathList = new ArrayList<>();
        for (File file : tsDir.listFiles()) {
            filePathList.add(file.getName());
        }
        filePathList = filePathList.stream().sorted((fileName1, fileName2) -> {
            Integer no1 = Integer.parseInt(fileName1.split("\\.")[0]);
            Integer no2 = Integer.parseInt(fileName2.split("\\.")[0]);
            return no1.compareTo(no2);
        }).collect(Collectors.toList());
        String filePaths = filePathList.stream().map(fileName -> "file '" + tsDir.getPath() + "/" + fileName + "'").collect(Collectors.joining("\n"));
        File fileList = new File(tsDir.getPath() + "/fileList.txt");
        FileUtil.writeUtf8String(filePaths, fileList.getPath());
        //合成ts为mp4
        String result = RuntimeUtil.execForStr(StrUtil.format("ffmpeg -f concat -safe 0 -i {}  -c copy {}", fileList.getPath(), mp4FilePath));
        log.info("ffmpeg result=\n{}", result);
    }
}
