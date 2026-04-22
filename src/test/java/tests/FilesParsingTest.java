package tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.AnimalCard;
import org.junit.jupiter.api.*;

import java.io.InputStreamReader;
import java.io.Reader;
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

            if (zis.getNextEntry() == null) {
                System.err.println("ФАЙЛ НЕ НАЙДЕН!");
                Assertions.fail("Zip file qa_guru_Zip.zip not found in classpath");
            }

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

            if (zis.getNextEntry() == null) {
                System.err.println("ФАЙЛ НЕ НАЙДЕН!");
                Assertions.fail("Zip file qa_guru_Zip.zip not found in classpath");
            }

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

            if (zis.getNextEntry() == null) {
                System.err.println("ФАЙЛ НЕ НАЙДЕН!");
                Assertions.fail("Zip file qa_guru_Zip.zip not found in classpath");
            }

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

    @Test
    void jsonFileParsingTest() throws Exception{
        try (Reader reader = new InputStreamReader(
                cl.getResourceAsStream("animal.json")
        )) {
            ObjectMapper mapper = new ObjectMapper();
            AnimalCard actual = mapper.readValue(reader, AnimalCard.class);

            // animal
            Assertions.assertEquals("2024-04-08T00:00:00.000Z", actual.getAnimal().getBirthdate());
            Assertions.assertEquals("бп", actual.getAnimal().getBreed());
            Assertions.assertEquals("pet", actual.getAnimal().getCategory());
            Assertions.assertEquals("кремовый", actual.getAnimal().getColor());
            Assertions.assertNull(actual.getAnimal().getDeregisteredAt());
            Assertions.assertEquals("male", actual.getAnimal().getGender());
            Assertions.assertEquals(26230, actual.getAnimal().getId());
            Assertions.assertEquals(true, actual.getAnimal().getIsPhotoRecognized());
            Assertions.assertEquals("Барсик", actual.getAnimal().getName());
            Assertions.assertNull(actual.getAnimal().getManipulations());
            Assertions.assertEquals("http://cdn.srv.org.ru/paws-animal-photo/802fd4df-e341-4aba-9e76-926545b30246.jpg", actual.getAnimal().getPhoto());
            Assertions.assertEquals("802fd4df-e341-4aba-9e76-926545b30246", actual.getAnimal().getPhotoId());
            Assertions.assertEquals("67К000026230", actual.getAnimal().getRegistrationNumber());
            Assertions.assertNull(actual.getAnimal().getSpecialSigns());
            Assertions.assertEquals("cat", actual.getAnimal().getSpecies());
            Assertions.assertEquals("2026-04-08T15:59:55.829Z", actual.getAnimal().getCreatedAt());

            //marking
            Assertions.assertEquals(7322, actual.getMarking().getId());
            Assertions.assertNull(actual.getMarking().getDate());
            Assertions.assertNull(actual.getMarking().getInjectionSite());
            Assertions.assertEquals("4634635", actual.getMarking().getNumber());
            Assertions.assertEquals("brand", actual.getMarking().getType());
        }
    }
}
