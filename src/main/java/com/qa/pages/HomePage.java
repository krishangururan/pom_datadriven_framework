package com.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;
import com.qa.util.AppConstants;

public class HomePage extends TestBase{
	
	@FindBy(xpath=AppConstants.PEOPLE_LINK)
	WebElement people_link;
	
	public HomePage() {
		super();
		PageFactory.initElements(driver, this);
	}
	
	public people nav_to_peoplePage() {
		people_link.click();
		return PageFactory.initElements(driver, people.class);
	}

}
