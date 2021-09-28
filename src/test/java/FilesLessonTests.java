import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesLessonTests {

    @Test
    void parseTxtFile() throws IOException {
        String result = "";

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("file.txt")) {
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
                .getCell(1)
                .getStringCellValue())
                .isEqualTo("allo.ua");
        assertThat(file.excel.getNumberOfSheets()).isEqualTo(1);
    }
}
