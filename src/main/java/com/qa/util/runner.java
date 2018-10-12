package com.qa.util;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;


public class runner {

	public XmlTest test;
	public List<XmlClass> classes;
	public List<XmlSuite> suites;
	public TestNG tng;
	public XmlSuite suite;
	public Xls_Reader xls = new Xls_Reader(AppConstants.DATASHEET_PATH);
	public String Sheetname;
	
	
	@Test
	public void test_runner(){

		Sheetname="runner";

		int rows = xls.getRowCount(Sheetname);
		int runmoderow=1;
		suite = new XmlSuite();
		suite.setName("Portal Automation Testing");
		suite.addListener("com.qa.ExtentReportListener.ExtentReporterNG" );
		

		for(int i=0;i<=rows;i++){
			if(xls.getCellData(Sheetname, 1, runmoderow).equalsIgnoreCase("Y") | xls.getCellData(Sheetname, 1, runmoderow).equalsIgnoreCase("N")){

				System.out.println(xls.getCellData(Sheetname, 0, runmoderow));


				test = new XmlTest(suite);
				test.setName(xls.getCellData(Sheetname, 0, runmoderow));
				classes = new ArrayList<XmlClass>();
				classes.add(new XmlClass("com.qa.testcases."+xls.getCellData(Sheetname, 0, runmoderow)));
				test.setXmlClasses(classes);


			} 
			
			runmoderow++;

		}suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.run();


	}}


