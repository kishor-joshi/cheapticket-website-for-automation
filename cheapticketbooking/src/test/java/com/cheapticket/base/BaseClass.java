package com.cheapticket.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	protected WebDriver driver;
/**
 * select driver class type
 * 
 * @param selecteddriver
 * @throws IOException
 */
	
	@Parameters("selecteddriver")
	@BeforeClass
	public void setBrowser(String selecteddriver) throws IOException {
		
		
		switch (selecteddriver) {
		
		case "chrome":
			
			// System Property for Chrome Driver   
			System.setProperty("webdriver.chrome.driver", "./libs/chromedriver.exe");
			
			// Instantiate a ChromeDriver class.     
			driver = new ChromeDriver();
			break;
			
		case "firefox":
			
			// System Property for firefox Driver   
			System.setProperty("webdriver.gecko.driver", "./libs/geckodriver.exe");
			
			// Instantiate a firefoxDriver class.     
			driver = new FirefoxDriver();
			break;
			
		case "ie":
			
			// System Property for IE Driver   
			System.setProperty("webdriver.ie.driver", "./libs/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;
		}
	}

	



}
