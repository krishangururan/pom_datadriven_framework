package com.qa.util;

public class AppConstants {

	public static final String APPLICATION_URL="https://www.ashurst.com/";
	public static final String SCREENSHOT_PATH = System.getProperty("user.dir")+"/Screenshots";
	public static final String DATASHEET_PATH=System.getProperty("user.dir")+"/Test_Suite_Data.xlsx";
	public static final String CHROMEDRIVER_EXE=System.getProperty("user.dir")+"/chromedriver.exe";
	public static final String FIREFOXDRIVER_EXE=System.getProperty("user.dir")+"/geckodriver.exe";
	public static final String LOCATIONS_SWAGGER = System.getProperty("user.dir")+"/swagger.json";
	
	
	/***************************APPLICATION XPATHS**********************************************************/
	
	// APPLICATION CTA XPATHS:
	
	public static final String RATES_XPATH="//a[contains(@href, 'api/rates')]";
	
	// APPLICATION LINKS XPATHS:
	public static final String PEOPLE_LINK="html/body/header/section[3]/div/nav[1]/ul/li[2]/a";
	public static final String LOCATION_LINK="html/body/header/section[3]/div/nav[1]/ul/li[2]/a";
	
	//CONTAINER XPATHS:
	
	
	//TWILLO OTP ACCESS:

	
	public static final String ACCOUNT_SID="AC48e1de40819270239301b5f749da3b8b";
	public static final String AUTH_TOKEN="e4b3a50dea055b350ef13308e2f55435";
	

}
