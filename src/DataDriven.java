import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataDriven {

	@SuppressWarnings("resource")
	public ArrayList<String> getExcelData(String sheetName, String testCaseName) throws IOException {

		ArrayList<String> a = new ArrayList<String>();

		String firstCellValue = "Test cases";
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//SeleniumDataDrivenWorkbook.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheets = workbook.getNumberOfSheets();
		for (int i = 0; i < sheets; i++) {

			if (workbook.getSheetName(i).matches(sheetName)) {

				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				Row firstRow = rows.next();
				Iterator<Cell> cells = firstRow.cellIterator();
				int k = 0;
				int column = 0;
				while (cells.hasNext()) {
					Cell value = cells.next();
					if (value.getStringCellValue().equalsIgnoreCase(firstCellValue)) {

						column = k;
						break;

					}
					k++;
				}

				// System.out.println(column);

				while (rows.hasNext()) {

					Row r = rows.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {

						Iterator<Cell> firstCell = r.cellIterator();
						while (firstCell.hasNext()) {

							Cell c = firstCell.next();
							if (c.getCellType() == CellType.STRING) {
								a.add(c.getStringCellValue());
							} else {
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
						break;

					}

				}

			}

		}
		return a;

	}

}
