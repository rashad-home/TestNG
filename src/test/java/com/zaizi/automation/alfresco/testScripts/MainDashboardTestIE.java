package com.zaizi.automation.alfresco.testScripts;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.automation.alfresco.core.info.TestCaseProperties;
import com.zaizi.automation.alfresco.core.pages.Dashboard;
import com.zaizi.automation.alfresco.core.pages.LoginPage;
import com.zaizi.automation.extentReports.ExtentManagerIE;
import com.zaizi.automation.listeners.IERetryAnalyzer;
import com.zaizi.automation.testng.core.elements.Division;
import com.zaizi.automation.testng.core.elements.TakeScreenShot;



/**
  *
  * @author mrashad@zaizi.com
  */


public class MainDashboardTestIE {

	
	/**
	 * 
	 * Defining log4j
	 */

	public static final Logger LOGGER = LogManager
			.getLogger(MainDashboardTestIE.class.getName());

	
	/**
	 * 
	 * Defining Report
	 */
	static ExtentReports extent;
	static ExtentTest parent;
	static ExtentTest child1;
    
	/**
	 * 
	 * Define WebDriver
	 */

	private static WebDriver driver;	
	
	
	
	/**
	 * 
	 * Define className
	 */

	public static String className = MainDashboardTestIE.class
			.getSimpleName();

	
	
	/**
	 * 
	 * Declares ExtentReports Configurations here 
	 * SeleniumWebdriver Specification (Set capabilities of Platform,BrowserName,Version)
	 * RemoteWebdriver Specification(new RemoteWebDriver(new URL(NODEURL),capability)) 
	 * Grid Specification
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */

	@BeforeTest(alwaysRun=true)
	public static void beforeClass() throws IOException

	{
		
		extent = ExtentManagerIE.getReporter(TestCaseProperties.REPORT_TEST_PATH_IE+"IEFullReport.html");
		parent=extent.startTest("<b>Main Dashboard Test in IE</b>","This is main dashboard Test,<b>Add dashlet into main dashboard</b>");
		System.out.println("Testcase started");
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public static void beforemethod() throws MalformedURLException{
				//Set the DriverType(BrowserName,Platform)
				driver = TestCaseProperties.driverType("IE", "WINDOWS");
				
				driver.manage().window().setSize(new Dimension(1920, 1920));
				
				//Get LoginScreen_URl from TestcaseProperties Values
				driver.get(TestCaseProperties.LOGIN_SCREEN_URL);	
	}
	
	@Parameters({"addDashletIE","screenShotNameIE"})
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "Add Dashlet in IE",priority = 1)
	public void addDashlet(String dashletTitle, String screenShotName) throws InterruptedException, IOException

	{
	
		
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "a_addDashlet");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
    	child1 = extent.startTest("AddDashlet","Admin for loginTest");	

	     LoginPage loginPage = new LoginPage(driver);
	        loginPage.loginAsAdmin();
	        Thread.sleep(2000);
		
		LOGGER.info("Test case AddDashlet started executing");
		child1.log(LogStatus.INFO,
				"Test case AddDashlet started executing");	
    	
    	
    	Dashboard dashboard = new Dashboard(driver);
        dashboard.customizeDashboard(dashletTitle);
        
        Division rssFeedBlog = new Division(
                driver,
                By.xpath("//div[@id='page_x002e_component-1-3_x002e_user_x007e_admin_x007e_dashboard_x0023_default-title']"));
        if (rssFeedBlog.getWebElement().isDisplayed())
        {
        	LOGGER.info( "Dashlet added");			
			child1.log(LogStatus.PASS, "<font color=green>"
					+ "Dashlet is added" + "<font>");
			
			
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"250");
     	   	child1.log(LogStatus.PASS, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"250"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
			
        }
        else
        {
        	LOGGER.info(" Dashlet is not added");
			child1.log(LogStatus.FAIL, "<font color=green>"
					+ "Dashlet is not added" + "<font>");
			
			
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"251");
     	   	child1.log(LogStatus.FAIL, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"251"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();
           
        }
        
        extent.flush();
        extent.endTest(child1); 
        
    }
		

		
		@AfterMethod
		   public void aftermethod(Method method,ITestResult result) throws Exception{
			
			if(method.getName().equals("addDashlet")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child1.log(LogStatus.FAIL,"addDashlet Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child1.log(LogStatus.SKIP, "addDashlet Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child1.log(LogStatus.PASS, "addDashlet Test got executed successfully");
			        extent.flush();
			    }
				}
				
			
			driver.quit(); 
			
		   }

		@AfterTest(alwaysRun=true)
		public void extent() {
			
			LOGGER.info("Test case closed");
			parent
			  .appendChild(child1);
			extent.endTest(parent);
			extent.close();	
			driver.quit();
			

		} 
	
}
