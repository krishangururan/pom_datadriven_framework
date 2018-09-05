import org.testng.TestNG;
import org.testng.xml.XmlTest;

import com.qa.testcases.MyFirstTest;

public class projectRunner {

	public static void main(String[] args) {
		XmlSuilte xmlSuite=new XmlSuite();
		xmlSuite.setName("Automation Test Cases");
		xmlSuite.setParallel("false");
		xmlSuite.setverbose(1);
		xmlSuite.addListener("com.qa.ExtentReportListener.ExtentReporterNG" );
		
		XmlTest xmlTest=new XmlTest(xmlSuite);
		xmlTest.setName("First Test");
		xmlTest.setPreserveOrder(true);
		
		XmlClass xmlClass=new XmlClass(MyFirstTest.class);
		List<XmlClass> list=new ArrayList<XmlClass>();
		list.add(xmlClass);
		xmlTest.setXmlClasses(list);
		TestNG testng=new TestNG();
		
		List<XmlSuite> suites=new ArrayList<XmlSuite>();
		suites.add(xmlSuite);
		testng.setXmlPathInJar(suites);
		testng.run();

	}

}
