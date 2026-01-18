package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    public static Object[][] getSheetData(String filePath, String sheetName) {

        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            //  CRITICAL SAFETY CHECK
            if (sheet == null) {
                throw new RuntimeException(
                        " Sheet not found: " + sheetName +
                                " in file: " + filePath
                );
            }

            int rows = sheet.getPhysicalNumberOfRows();
            int cols = sheet.getRow(0).getLastCellNum();

            Object[][] data = new Object[rows - 1][cols];
            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i < rows; i++) {
                var row = sheet.getRow(i);

                //  ROW NULL SAFETY
                if (row == null) continue;

                for (int j = 0; j < cols; j++) {
                    var cell = row.getCell(j);

                    //  CELL NULL SAFETY
                    data[i - 1][j] = (cell == null)
                            ? ""
                            : formatter.formatCellValue(cell);
                }
            }
            return data;

        } catch (IOException e) {
            throw new RuntimeException(
                    "❌ Excel Read Error → " + e.getMessage(), e
            );
        }
    }
}
