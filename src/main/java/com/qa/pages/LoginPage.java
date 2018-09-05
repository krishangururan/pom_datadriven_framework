package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class LoginPage extends TestBase {
	
	//Page Factory - OR:
	
	@FindBy(xpath="//*[@id='loginForm']/div/input[1]")
	WebElement username;
	
	@FindBy(xpath="//*[@id='loginForm']/div/input[2]")
	WebElement password;
	
	@FindBy(xpath="//*[@id='loginForm']/div/div/input")
	WebElement loginBtn;
	
	@FindBy(xpath="//button[contains(text(),'Sign Up')]")
	WebElement signUpBtn;
	
	//Initializing Page Objects:
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	public String validatePageTitle() {
		return driver.getTitle();
	}
	
	public boolean validateLoginBtn() {
		return loginBtn.isDisplayed();
	}
	
	public HomePage login(String uname,String pwd) {
		username.sendKeys(uname);
		password.sendKeys(pwd);
		loginBtn.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new HomePage();
	}
	
	

}
