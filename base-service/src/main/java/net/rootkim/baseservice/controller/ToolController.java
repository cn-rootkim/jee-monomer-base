package net.rootkim.baseservice.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import io.swagger.annotations.*;
import net.rootkim.core.utils.CadUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/23
 */
@RestController
@RequestMapping("tool")
@Api(tags = "工具")
public class ToolController {

    private static final String basePath = System.getProperty("user.dir") + "/";

    @PostMapping("cadSign")
    @ApiOperation("cad签章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "signImgFile", value = "签章图片", required = true),
            @ApiImplicitParam(name = "cadFiles", value = "cad文件列表", required = true),
            @ApiImplicitParam(name = "postionX", value = "签章位置X", required = true),
            @ApiImplicitParam(name = "postionY", value = "签章位置Y", required = true)
    })
    public void cadSign(@RequestParam("signImgFile") MultipartFile signImgFile,
                        @RequestParam("cadFiles") MultipartFile[] cadFiles,
                        @RequestParam("postionX") Float postionX,
                        @RequestParam("postionY") Float postionY,
                        HttpServletResponse response) throws Exception {
        //本次请求工作的文件夹
        String dirPath = basePath + IdUtil.fastSimpleUUID() + "/";
        File dir = new File(dirPath);
        dir.mkdir();
        //下载签章图片
        String signImgPath = dirPath + "sign.png";
        FileUtil.writeFromStream(signImgFile.getInputStream(), signImgPath);
        //下载cad
        List<String> cadFilePathList = new ArrayList<>();
        for (MultipartFile file : cadFiles) {
            if (file.isEmpty()) {
                continue;
            }
            String cadFilePath = dirPath + file.getOriginalFilename();
            FileUtil.writeFromStream(file.getInputStream(), cadFilePath);
            cadFilePathList.add(cadFilePath);
        }
        //cad转为pdf
        List<String> pdfFilePathList = new ArrayList<>();
        for (String cadFilePath : cadFilePathList) {
            String cadFileName = cadFilePath.substring(0, cadFilePath.lastIndexOf(".")).replace(dirPath, "");
            String pdfFilePath = dirPath + cadFileName + ".pdf";
            pdfFilePathList.add(pdfFilePath);
            CadUtil.convertToPdf(cadFilePath, pdfFilePath);
        }
        //pdf签章
        String signDirPath = dirPath + "sign/";
        File signDir = new File(signDirPath);
        signDir.mkdir();
        for (String pdfFilePath : pdfFilePathList) {
            String signPdfFilePath = signDirPath + pdfFilePath.replace(dirPath, "");
            CadUtil.signPdf(pdfFilePath, signImgPath, signPdfFilePath, postionX, postionY);
        }
        StringBuilder zipFilePath = new StringBuilder(dirPath);
        zipFilePath.append(cadFiles.length);
        zipFilePath.append("个文件 ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        zipFilePath.append(simpleDateFormat.format(new Date()));
        zipFilePath.append(".zip");
        ZipUtil.zip(signDirPath, zipFilePath.toString());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(new File(zipFilePath.toString()).getName(), "UTF-8"));
        try (BufferedInputStream inputStream = FileUtil.getInputStream(zipFilePath.toString())) {
            IoUtil.copy(inputStream, response.getOutputStream());
        }
        FileUtil.del(dir);
    }
}
