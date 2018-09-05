package com.qa.testcases;

import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pages.HomePage;

public class LoginPageTest extends TestBase{

	
	@Test
	public void loginPageTitleTest() {
		initial_test_tasks();
		HomePage hp=new HomePage();
		hp.nav_to_peoplePage();
	}
	

}
