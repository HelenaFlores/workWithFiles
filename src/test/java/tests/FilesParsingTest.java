package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.*;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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

    @Test
    void xlsxFileParsingTest() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(
                cl.getResourceAsStream("qa_guru_Zip.zip")
        )) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.endsWith(".xlsx")) {
                    byte[] actualXlsxContent = zis.readAllBytes();
                    XLS xls = new XLS(actualXlsxContent);
                    String headerA = xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
                    String headerB = xls.excel.getSheetAt(0).getRow(0).getCell(1).getStringCellValue();
                    String headerC = xls.excel.getSheetAt(0).getRow(0).getCell(2).getStringCellValue();
                    String headerD = xls.excel.getSheetAt(0).getRow(0).getCell(3).getStringCellValue();
                    String headerE = xls.excel.getSheetAt(0).getRow(0).getCell(4).getStringCellValue();
                    String headerF = xls.excel.getSheetAt(0).getRow(0).getCell(5).getStringCellValue();
                    String headerG = xls.excel.getSheetAt(0).getRow(0).getCell(6).getStringCellValue();
                    String headerH = xls.excel.getSheetAt(0).getRow(0).getCell(7).getStringCellValue();
                    String headerI = xls.excel.getSheetAt(0).getRow(0).getCell(8).getStringCellValue();

                    Assertions.assertTrue(headerA.contains("Дата добавления"));
                    Assertions.assertTrue(headerB.contains("Вид"));
                    Assertions.assertTrue(headerC.contains("Пол"));
                    Assertions.assertTrue(headerD.contains("id карточки"));
                    Assertions.assertTrue(headerE.contains("Администрация"));
                    Assertions.assertTrue(headerF.contains("Дата последнего отлова"));
                    Assertions.assertTrue(headerG.contains("Дата последнего выпуска"));
                    Assertions.assertTrue(headerH.contains("Вид маркировки"));
                    Assertions.assertTrue(headerI.contains("Номер маркировки"));
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
                    byte[] actualCsvContent = zis.readAllBytes();
                    String csvString = new String(actualCsvContent, "Windows-1251");
                    CSVReader csvReader = new CSVReader(new StringReader(csvString));

                    List<String[]> data = csvReader.readAll();
                    Assertions.assertEquals(7, data.size());
                    Assertions.assertArrayEquals(
                            new String[] {"Заявлено на отлов", "5"},
                            data.get(0)
                    );
                    Assertions.assertArrayEquals(
                            new String[] {"Всего отловлено","3"},
                            data.get(1)
                    );
                    Assertions.assertArrayEquals(
                            new String[] {"Отловлено кошек","2"},
                            data.get(2)
                    );
                    Assertions.assertArrayEquals(
                            new String[] {"Отловлено собак","1"},
                            data.get(3)
                    );
                    Assertions.assertArrayEquals(
                            new String[] {"Отловлено агрессивных","1"},
                            data.get(4)
                    );
                    Assertions.assertArrayEquals(
                            new String[] {"Отловлено раненых","2"},
                            data.get(5)
                    );
                    Assertions.assertArrayEquals(
                            new String[] {"Отловлено беременных","0"},
                            data.get(6)
                    );
                }
                zis.closeEntry();
            }
        }
    }
}
