package net.rootkim.baseservice.controller;

import io.swagger.annotations.*;
import net.rootkim.core.domain.vo.ResultVO;
import net.rootkim.core.utils.CadUtil;
import net.rootkim.core.utils.FileUtil;
import net.rootkim.core.utils.IDUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

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
            @ApiImplicitParam(name = "signImgFile", value = "签章图片", required = true, dataType = "__File"),
            @ApiImplicitParam(name = "cadFiles", value = "cad文件列表", required = true, dataType = "__File"),
            @ApiImplicitParam(name = "postionX", value = "签章位置X", required = true, dataType = "float"),
            @ApiImplicitParam(name = "postionY", value = "签章位置Y", required = true, dataType = "float")
    })
    public void cadSign(@RequestParam("signImgFile") MultipartFile signImgFile,
                        @RequestParam("cadFiles") MultipartFile[] cadFiles,
                        @RequestParam(value = "postionX", required = true) Float postionX,
                        @RequestParam(value = "postionY", required = true) Float postionY,
                        HttpServletResponse response) throws Exception {
        //本次请求工作的文件夹
        String dirPath = basePath + IDUtil.getUUID32() + "/";
        File dir = new File(dirPath);
        dir.mkdir();
        //下载签章图片
        String signImgPath = dirPath + "sign.png";
        FileUtil.writeStream(signImgPath, signImgFile.getInputStream());
        //下载cad
        List<String> cadFilePathList = new ArrayList<>();
        for (MultipartFile file : cadFiles) {
            if (file.isEmpty()) {
                continue;
            }
            String cadFilePath = dirPath + file.getOriginalFilename();
            FileUtil.writeStream(cadFilePath, file.getInputStream());
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
        try (
                FileOutputStream fos = new FileOutputStream(zipFilePath.toString());
                ZipOutputStream zos = new ZipOutputStream(fos);
        ) {
            FileUtil.zipFolder(signDir, signDir.getName(), zos);
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(new File(zipFilePath.toString()).getName(), "UTF-8"));
        FileUtil.readStream(response.getOutputStream(), zipFilePath.toString());
        FileUtil.delDir(dir);
    }
}
