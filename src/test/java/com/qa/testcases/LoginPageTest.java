package com.qa.testcases;

import java.util.Hashtable;

import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.HomePage;

public class LoginPageTest extends TestBase{

	
	@Test(dataProvider="dataProvider")
	public void loginPageTitleTest(Hashtable<String,String> data) {
		initial_test_tasks(data);
		HomePage hp=new HomePage();
		hp.nav_to_peoplePage();
	}
	

}
