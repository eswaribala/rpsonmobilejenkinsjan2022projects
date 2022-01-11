package com.amex.cucumberdemo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
@Slf4j
public class StepDefinitions {
	private WebDriver webDriver;
    @Given("^Open the Chrome and Launch the application$")
	public void openChrome_LaunchtheApplication() {
		
    	String username = System.getenv("BROWSERSTACK_USERNAME");
    	String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    	String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
    	String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
    	String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");

    	DesiredCapabilities capabilities = new DesiredCapabilities();
    	capabilities.setCapability("os", "Windows");
    	capabilities.setCapability("os_version", "10");
    	capabilities.setCapability("browser", "chrome");
    	capabilities.setCapability("browser_version", "latest");
    	capabilities.setCapability("name", "BStack-[Java] Sample Test"); // test buildName
    	capabilities.setCapability("build", buildName); // CI/CD job name using BROWSERSTACK_BUILD_NAME env variable
    	capabilities.setCapability("browserstack.local", browserstackLocal);
    	capabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
    	try {
			webDriver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), capabilities);
			webDriver.manage().window().maximize();
			webDriver.get("http://demo.guru99.com/v4");	
	       log.info("This Step open the Chrome and launch the application.");	
    	
    	} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// System.setProperty("webdriver.chrome.driver","c:/chromedriver.exe");
			//webDriver=new ChromeDriver();			
						
	    
	}
    
    @When("^Enter Username \"(.*)\" and Password \"(.*)\"$")
    public void enterUserNameAndPassword(String userName,String password) {
    	 webDriver.findElement(By.name("uid")).sendKeys(userName);					
		 webDriver.findElement(By.name("password")).sendKeys(password);
    }
    
    @Then("^Reset the credentials$")
    public void resetButton() {
    	webDriver.findElement(By.name("btnReset")).click();	
    	webDriver.close();
    }
	
}
