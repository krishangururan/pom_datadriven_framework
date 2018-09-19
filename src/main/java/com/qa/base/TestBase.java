package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.qa.util.AppConstants;
import com.qa.util.TestUtil;
import com.qa.util.WebEventListener;
import com.qa.util.Xls_Reader;
import com.qa.util.readAndWriteData;

public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver edriver;
	public static WebEventListener eventlistener;
	public Xls_Reader excelReader;
	public readAndWriteData readNwrite=new readAndWriteData();
	public String TEST;
	public ITestResult result;
	public static Logger logger;
	static String className;
	public TestBase() {

		try {

			prop=new Properties();
			FileInputStream ip=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/qa/config/config.properties");
			prop.load(ip);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		


	}

	//********************************Browser invoke function*****************************************//

	public static void initialization() {
		if(driver==null) {
			String browserName=prop.getProperty("browser");
			if(browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/chromedriver.exe");
				driver=new ChromeDriver();
			}else if(browserName.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/geckodriver.exe");
				driver=new FirefoxDriver();
			}

			edriver=new EventFiringWebDriver(driver);
			eventlistener=new WebEventListener();
			edriver.register(eventlistener);
			driver=edriver;
		}
		logger=Logger.getLogger(className);
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\config\\log4j.properties");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));

	}


	//********************************Before-After Methods and Initial test Methods ************************//

	public void initial_test_tasks(Hashtable<String,String> data) {
		readNwrite.runmodeCheck(TEST,data);

	}

	public void initial_test_tasks() {
		readNwrite.runmodeCheck(TEST);		
	}

	@BeforeMethod
	public void setUp(Method m) {
		TEST=m.getName().toString();
		className = this.getClass().getName();
		logger=Logger.getLogger(className+"----"+TEST);
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\config\\log4j.properties");

	}

	@AfterClass
	public void tearDown() {
		if(driver!=null)
			driver.quit();
		driver=null;
	}

	@BeforeClass
	public void startUp() {
		className = this.getClass().getName();
		initialization();
	}

	//********************************Data Provider*****************************************//
	@DataProvider
	public Object[][] dataProvider(Method m){
		excelReader=new Xls_Reader(AppConstants.DATASHEET_PATH);
		TEST=m.getName().toString();
		return readAndWriteData.getData(excelReader,m.getName().toString());

	}


}
