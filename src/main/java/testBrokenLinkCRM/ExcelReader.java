package testBrokenLinkCRM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	/*public FileInputStream fis= null;
	String filePath = null;
	private XSSFWorkbook wb = null;
	private XSSFSheet sh= null;

	public ExcelReader() throws IOException {
		try {
			filePath = System.getProperty("user.dir")+"testData\\loginData.xlsx";


		} catch (Exception e) {
			e.printStackTrace();
		}
		//open file - workbook
		//File testDataFile = new File(filePath);
		fis= new FileInputStream(filePath);
		wb= new XSSFWorkbook(fis);
		sh= wb.getSheetAt(0);

	}

	public int getSheetRows(String sheetName) {
		int index= wb.getSheetIndex(sheetName);
		sh= wb.getSheetAt(index);
		return(sh.getLastRowNum()+1);
	}
	
	//get test data from test data sheet in hashmap based on row number
	public HashMap<String, String> getTestDataInMap(int rowNum) throws Exception {
		//read data row by row and put in map
		HashMap<String, String> hm = new HashMap<String, String>();

		for (int i = 0; i < sh.getRow(0).getLastCellNum(); i++) {
			sh.getRow(rowNum).getCell(i).setCellType(CellType.STRING);
			hm.put(sh.getRow(0).getCell(i).toString(), sh.getRow(rowNum).getCell(i).toString());
		}	
		return hm;
	}

	//get row count
	public int getRowCount() {		
		return sh.getLastRowNum();
	}

	//ger column count
	public int getColCount() {
		return sh.getRow(0).getLastCellNum(); 

	} */
	
	String filePath;
	Sheet sh;
	
	public ExcelReader(String sheetName) {
		 try {
		filePath = System.getProperty("user.dir")+PropertiesOperations.getPropertyValueByKey("testDataLocation");
		//	 filePath= "C:\\Users\\dayan\\eclipse-workspace\\testBrokenLinkCRM\\testData\\loginData.xlsx";
			// filePath = System.getProperty("user.dir")+"\\testData\\loginData.xlsx";
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
//		 Properties prop = new Properties();
//
//		 FileInputStream ip = null;
//		try {
//			ip = new FileInputStream(("C:\\Users\\dayan\\eclipse-workspace\\testBrokenLinkCRM\\testData\\loginData.xlsx"));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		 try {
//			prop.load(ip);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		 	//open file - workbook
//		 
			File testDataFile = new File(filePath);
			Workbook wb = null;
			try {
				wb = WorkbookFactory.create(testDataFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			 sh = wb.getSheet(sheetName);
	}
	
	//get test data from test data sheet in hashmap based on row number
	public HashMap<String, String> getTestDataInMap(int rowNum) throws Exception {
				//read data row by row and put in map
		HashMap<String, String> hm = new HashMap<String, String>();
		
		for (int i = 0; i < sh.getRow(0).getLastCellNum(); i++) {
			sh.getRow(rowNum).getCell(i).setCellType(CellType.STRING);
			hm.put(sh.getRow(0).getCell(i).toString(), sh.getRow(rowNum).getCell(i).toString());
		}	
		return hm;
	}
	
	//get row count
	public int getRowCount() {		
		return sh.getLastRowNum();
	}
	
	//ger column count
	public int getColCount() {
		return sh.getRow(0).getLastCellNum();
		
	}

}
