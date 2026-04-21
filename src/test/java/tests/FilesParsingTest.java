package tests;

import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class FilesParsingTest{

    private ClassLoader cl = FilesParsingTest.class.getClassLoader();


    @Test
    void pdfFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("qa_guru_Zip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".pdf")) {
                    byte[] expectedPdfBytes =
                            Files.readAllBytes(Paths.get("src/test/resources/actCaught.pdf"));
                    byte[] actualPdfContent = zis.readAllBytes();
                    PDF expectedPdf = new PDF(expectedPdfBytes);
                    PDF actualPdf = new PDF(actualPdfContent);
                    Assertions.assertEquals(expectedPdf.text, actualPdf.text);
                }
                zis.closeEntry();
            }
        }
    }

    @Disabled("")
    @Test
    void xlsxFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("qa_guru_Zip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".xlsx")) {
                    System.out.println(entry.getName());
                    byte[] expectedPdfBytes = Files.readAllBytes(Paths.get("src/test/resources/actCaught.pdf"));
                    byte[] actualPdfContent = zis.readAllBytes();
                    PDF expectedPdf = new PDF(expectedPdfBytes);
                    PDF actualPdf = new PDF(actualPdfContent);
                    Assertions.assertEquals(expectedPdf.text, actualPdf.text);
                }
                zis.closeEntry();
            }
        }
    }

    @Test
    void csvFileParsingTest() throws Exception{
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("qa_guru_Zip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".csv")) {
                    System.out.println(entry.getName());
                    byte[] actualCsvContent = zis.readAllBytes();
                    CSVReader csvReader = new CSVReader(new InputStreamReader(actualCsvContent));
                 //   byte[] expectedPdfBytes = Files.readAllBytes(Paths.get("src/test/resources/actCaught.pdf"));


                    PDF actualPdf = new PDF(actualPdfContent);
                    Assertions.assertEquals(expectedPdf.text, actualPdf.text);
                }
                zis.closeEntry();
            }
        }
    }
}
