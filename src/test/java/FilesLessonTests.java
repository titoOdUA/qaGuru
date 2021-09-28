import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesLessonTests {

    @Test
    void parseTxtFile() throws IOException {
        String result = "";

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dummy.txt")) {
            if (is != null) {
                result = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        }
        assertThat(result).contains("Bob", "Joel", "Anne");
    }

    @Test
    void parsePDFFile() throws IOException {
        String expectedText = "Dummy PDF file";

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dummy.pdf");
        PDF file = new PDF(inputStream);
        assertThat(file.text).contains(expectedText);
        assertThat(file.numberOfPages).isEqualTo(1);
    }

    @Test
    void parseXlSFile() {
        XLS file = new XLS(getClass().getClassLoader().getResourceAsStream("dummy.xls"));
        assertThat(file.excel.getSheetAt(0)
                .getRow(0)
                .getCell(0)
                .getStringCellValue())
                .isEqualTo("allo.ua");
        assertThat(file.excel.getNumberOfSheets()).isEqualTo(1);
    }

    @Test
    void parseZipFile() throws ZipException {
        ZipFile file = new ZipFile(getClass().getClassLoader().getResource("filesArchive.zip").getFile());
        assertThat(file.isValidZipFile()).isTrue();
        file.setPassword("1111");
        file.extractAll("src/test/resources");
    }

    @Test
    void readDocTest() throws Exception {
        String expectedString = "Hello world";

        try (FileInputStream fileInputStream = new FileInputStream(getClass().getClassLoader().getResource("dummy.docx").getFile())) {
            XWPFDocument document = new XWPFDocument(fileInputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            assertThat(extractor.getText()).contains(expectedString);
        }
    }
}