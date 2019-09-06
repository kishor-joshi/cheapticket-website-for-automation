package com.cheapticket.testscript;
import com.cheapticket.base.*;
import org.apache.log4j.Logger;
import com.cheapticket.helper.HelperClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
/**
 * 
 * @author kishor.joshi
 *
 */

public class OneWayFlightBooking extends BaseClass{

	Properties prop = new Properties();
	Logger log=Logger.getLogger(OneWayFlightBooking.class);
	/**
	 * It will load properties file
	 * @throws IOException
	 */
	
	@Test(priority=1)
	public void openURL() throws IOException
	{
		FileInputStream input = new FileInputStream(".\\src\\test\\resources\\locators\\locators.properties");

		//loads locaters from properties file
		prop.load(input);
		log.info("properties file is loaded");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	/**
	 * This method will Book flight ticket by taking user data  from dataprovider method.
	 * @param String[]
	 * @throws InterruptedException
	 */
	
	
	@Test(priority=2,dataProvider="SearchProvider",dataProviderClass=HelperClass.class)
	public void bookTicket(String[] arr) throws InterruptedException
	{
		//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		 // Launch cheapticket  Website 
		driver.get(prop.getProperty("url"));
		log.info("cheapticket website is  launchedfor one way flight booking");
		
		
		driver.findElement(By.xpath(prop.getProperty("domestic"))).click();
		
		//clicks on oneway radio button
		driver.findElement(By.xpath(prop.getProperty("oneway"))).click();
		
		// bording point 
		WebElement from = waitForElement(30, driver,prop.getProperty("from"));
		from.sendKeys(arr[0]);
		
		//departure point
		Thread.sleep(1000);
		WebElement to = waitForElement(30, driver,prop.getProperty("to"));
		to.sendKeys(arr[1]);
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("depart"))).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("departdate"))).click();
		Thread.sleep(2000);
		
		//
		driver.findElement(By.xpath(prop.getProperty("class"))).click();
		
		driver.findElement(By.xpath(prop.getProperty("submit"))).click();
		Thread.sleep(3000);
		WebElement book=waitForElement(30,driver,prop.getProperty("book"));
        book.click();
        Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("email"))).sendKeys(arr[2]);
		
		//converts decimal number into interger type value
		String phNum=arr[3].replace(".", "").replace("E9", "");
		driver.findElement(By.xpath(prop.getProperty("mobile"))).sendKeys(phNum);
		
		driver.findElement(By.xpath(prop.getProperty("continue"))).click();
	    driver.findElement(By.xpath(prop.getProperty("dropdown1"))).click();
	    
	    driver.findElement(By.xpath(prop.getProperty("Mr"))).click();
	    
	    //traveller first name
		driver.findElement(By.xpath(prop.getProperty("firstname1"))).sendKeys(arr[4]);
		
		//traveller last name
		driver.findElement(By.xpath(prop.getProperty("lastname1"))).sendKeys(arr[5]);
		driver.findElement(By.xpath(prop.getProperty("submitlast"))).click();
		
		
		
		log.info("from "+arr[0]+" to "+arr[1]+" flight ticket booked ");
		  
		  
	}
	
	/**
	 * it will quit the browser
	 */
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
	
	/**
	 * this method is for fluent wait
	 * @param seconds
	 * @param driver
	 * @param xpath
	 * @return
	 */
	public WebElement waitForElement(long seconds,WebDriver driver,final String xpath) {
		FluentWait<WebDriver> fluentWait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(seconds))
				.pollingEvery(Duration.ofSeconds(2))
				.ignoring(NoSuchElementException.class);
		
		WebElement element = fluentWait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver dvr) {
				return dvr.findElement(By.xpath(xpath));
			}
		});
		return element;
	}
}

