package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.qa.base.TestBase;

public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT=60;
	public static long IMPLICIT_WAIT=60;
	public static Workbook book;
	public static Sheet sheet;
	public void switchToFrame() {
		driver.switchTo().frame("mainpanel");
	}
	
	
	public static Object[][] getData(String sheetName){
		FileInputStream file=null;
		try {
			file=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/testdata/TestDataSheet.xlsx");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		try {
			book=WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sheet=book.getSheet(sheetName);

		
		Object[][] data=new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		
		
		
		
		
		for(int i=0;i<sheet.getLastRowNum();i++) {
			for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
				data[i][j]=sheet.getRow(i+1).getCell(j).toString();
				System.out.println(data[i][j]+"---");
				
			}
		}
		
		return data;
		
	}
	
	public static void takeScreenshot(){

		//decide name - time stamp
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		String path=AppConstants.SCREENSHOT_PATH+"//"+screenshotFile;

		File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try{
			FileUtils.copyFile(srcFile, new File(path));
		}catch (IOException e){
			e.printStackTrace();
		}

		//test.log(LogStatus.INFO, test.addScreenCapture(path));
	}


}
