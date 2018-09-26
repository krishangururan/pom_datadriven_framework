package com.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;

import com.qa.base.TestBase;


public class readAndWriteData {
	
	public File f;
	public FileInputStream fis;
	public XSSFWorkbook wb;
	public XSSFSheet sh;
	static String sheetName;
	public Xls_Reader xls=new Xls_Reader(AppConstants.DATASHEET_PATH);
	
	

	
	
	public boolean isRunnable(String test_name){

	f = new File(System.getProperty("user.dir")+"/Test_Suite_Data.xlsx");
	try {
		fis=new FileInputStream(f);
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	try {
		wb=new XSSFWorkbook(fis);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	sh=wb.getSheet("runner");
	
	int row_count=sh.getLastRowNum();
	for(int i=0;i<=row_count;i++){
		String test=sh.getRow(i).getCell(0).getStringCellValue();
		
		if(test.equalsIgnoreCase(test_name)){
			
			String runmode=sh.getRow(i).getCell(1).getStringCellValue();
			System.out.println(runmode);
			if(runmode.equalsIgnoreCase("Y")){
				//System.out.println("Inside if block of runmode verification");
				return true;
			} else {
				//System.out.println("Inside else block of runmode verification");
				return false;
			}
		}
	}



return false;
	}
	

	public void runmodeCheck( Hashtable<String,String> data){
		System.out.println(TestBase.className);
		if(!isRunnable(TestBase.className)) {
			 throw new SkipException("Runmode of the test data is set to NO for class: "+TestBase.className);
		}else if(!data.get("runmode").equalsIgnoreCase("Y")) {
			throw new SkipException("Runmode of the test data is set to NO for class: "+TestBase.className+" --- Test Case: "+TestBase.TEST);
		}
     
     

	}
	

	
	
	
	
	public static Object[][] getData(Xls_Reader xls, String testName){
		
		sheetName="data";
	        String testCaseName=testName;
	        int testStartRowNum=1;
	      while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)){
	    	  
	    	  testStartRowNum++;
	      }
	  
	      int dataStartRowNum=testStartRowNum+2;
	      int dStartrNum=dataStartRowNum;
	      int cols=0;
	      while(!xls.getCellData(sheetName, cols, dataStartRowNum-1).equals("")){
	    	  cols++;
	      }
	      
	      int rows=0;
	      while(!xls.getCellData(sheetName, 0, dataStartRowNum+rows).equals("")){
	    	  rows++;
	      }
	     
	     
	     
	     
	     Object[][] data=new Object[rows][1];
	     int dataRow=0;
	     Hashtable<String,String> table=null;
	     for(int rNum=dStartrNum;rNum<dStartrNum+rows;rNum++){
	    	 table=new Hashtable<String,String>();
	    	 for(int cNum=0;cNum<cols;cNum++){
	    		 String key=xls.getCellData(sheetName, cNum, dStartrNum-1);
	    		 String value=xls.getCellData(sheetName, cNum, rNum);
	    		 table.put(key, value);
	    	 } 
	    	 data[dataRow][0]=table;
	    	 dataRow++;
	     }
	     return data;
	}
	
	
	
	/*************************APP SPECIFIC EXCEL FUNCTION******************************************/

}
