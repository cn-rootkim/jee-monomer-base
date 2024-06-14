package net.rootkim.core.utils;

import cn.hutool.core.io.FileUtil;
import com.aspose.cad.Color;
import com.aspose.cad.Image;
import com.aspose.cad.License;
import com.aspose.cad.imageoptions.CadRasterizationOptions;
import com.aspose.cad.imageoptions.PdfOptions;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/5/23
 */
public class CadUtil {

    public static void convertToPdf(String dwgFile, String pdfFile) throws Exception {
        try (InputStream is = License.class.getResourceAsStream("/lisence.xml")) {
            License license = new License();
            license.setLicense(is);
            DWGFileToPDF(dwgFile, pdfFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signPdf(String source, String signImg, String target, float absoluteX, float absoluteY) throws Exception {
        byte[] bytes = FileUtil.readBytes(source);
        PdfReader pdfReader = null;
        PdfStamper pdfStamper = null;
        try {
            pdfReader = new PdfReader(bytes);
            pdfStamper = new PdfStamper(pdfReader, Files.newOutputStream(Paths.get(target)));
            pdfStamper.setEncryption("".getBytes(), "".getBytes(), PdfWriter.AllowCopy | PdfWriter.AllowPrinting, PdfWriter.STANDARD_ENCRYPTION_40);
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(signImg);
            //设置签字图片宽高
//        image.scaleAbsolute(200, 200);
            int pageCount = pdfReader.getNumberOfPages();
            for (int i = 1; i <= pageCount; i++) {
                PdfContentByte content = pdfStamper.getUnderContent(i);
                image.setAbsolutePosition(absoluteX, absoluteY);
                content.addImage(image);
            }
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (pdfStamper != null) {
                pdfStamper.close();
            }
            if (pdfReader != null) {
                pdfReader.close();
            }
        }
    }

    /**
     * @param srcFile 选择dwg文件路径
     * @param dataDir 保存文件路径
     */
    private static void DWGFileToPDF(String srcFile, String dataDir) {

        try (Image objImage = Image.load(srcFile)) {
            CadRasterizationOptions rasterizationOptions = new CadRasterizationOptions();
            rasterizationOptions.setBackgroundColor(Color.getWhite());
            rasterizationOptions.setPageWidth(1600);
            rasterizationOptions.setPageHeight(1600);

            // Create an instance of PdfOptions
            PdfOptions pdfOptions = new PdfOptions();
            // Set the VectorRasterizationOptions property
            pdfOptions.setVectorRasterizationOptions(rasterizationOptions);
            // Export the DWG to PDF
            objImage.save(dataDir, pdfOptions);
        }
    }
}
