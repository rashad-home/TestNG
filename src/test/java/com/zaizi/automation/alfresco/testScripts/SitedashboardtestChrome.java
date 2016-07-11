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
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.automation.alfresco.core.info.TestCaseProperties;
import com.zaizi.automation.alfresco.core.pages.Dashboard;
import com.zaizi.automation.alfresco.core.pages.LoginPage;
import com.zaizi.automation.alfresco.core.pages.NavigateToPage;
import com.zaizi.automation.alfresco.core.pages.RemoveObjects;
import com.zaizi.automation.extentReports.ExtentManagerChrome;
import com.zaizi.automation.listeners.ChromeRetryAnalyzer;
import com.zaizi.automation.testng.core.elements.Button;
import com.zaizi.automation.testng.core.elements.Element;
import com.zaizi.automation.testng.core.elements.TakeScreenShot;
import com.zaizi.automation.testng.core.elements.TextField;


/**
*
* @author mrashad@zaizi.com
*/

public class SitedashboardtestChrome {

	
	/**
	 * 
	 * Defining log4j
	 */

	public static final Logger LOGGER = LogManager
			.getLogger(SitedashboardtestChrome.class.getName());


	

	
	/**
	 * 
	 * Defining Report
	 */
	static ExtentReports extent;
	static ExtentTest parent;
	static ExtentTest child1;
	static ExtentTest child2;
    
	/**
	 * 
	 * Define WebDriver
	 */

	private static WebDriver driver;	
	
	
	
	/**
	 * 
	 * Define className
	 */

	public static String className = SitedashboardtestChrome.class
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
		
		extent = ExtentManagerChrome.getReporter(TestCaseProperties.REPORT_TEST_PATH_CHROME+"ChromeFullReport.html");
		parent=extent.startTest("<b>Site Dashboard Test in Chrome</b>","This is site dashboard Test,<b>Create site,Add dashlet into site dashboard,Delete Site</b>");
		System.out.println("Testcase started");
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public static void beforemethod() throws MalformedURLException{
				//Set the DriverType(BrowserName,Platform)
				driver = TestCaseProperties.driverType("Chrome", "WINDOWS");
				
				driver.manage().window().setSize(new Dimension(1920, 1920));
				
				//Get LoginScreen_URl from TestcaseProperties Values
				driver.get(TestCaseProperties.LOGIN_SCREEN_URL);	
	}
	
	@Parameters({"siteChrome","siteIdChrome","dashletTitleChrome","screenShotNameChrome"})
	@Test(retryAnalyzer=ChromeRetryAnalyzer.class,testName = "Add Site Dashlet in Chrome",priority = 1)
	public void addSiteDashlet(String siteName, String siteURL, String dashletTitle, String screenShotName) throws InterruptedException, IOException

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
	        
	        
			 LOGGER.info("Create Public Site");

	        dashboard.createPublicSite(siteName);
	        
	        Thread.sleep(10000);
	        
	        LOGGER.info("Customize site dashlet");

	        dashboard.customizeSiteDashboard(dashletTitle);
	        
	        LOGGER.info("View site dashlet");

	        Thread.sleep(5000);
	        
	       /* Division rssFeedBlog = new Division(
	                driver,
	                By.xpath("//div[@id='page_x002e_component-1-2_x002e_site_x007e_"+siteURL+"_x007e_dashboard_x0023_default-title']"));
	        if (rssFeedBlog.getWebElement().isDisplayed())
	        {*/
	        if(Element.isElementPresent(driver, By.xpath("//div[text()='Alfresco Blog']"))){
	        	LOGGER.info( "Dashlet added");			
				child1.log(LogStatus.PASS, "<font color=green>"
						+ "Dashlet is added" + "<font>");
				
				
				TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShot(driver,className, screenShotName+"250");
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
	     	   	ts.takeScreenShot(driver,className, screenShotName+"251");
	     	   	child1.log(LogStatus.FAIL, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"251"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush(); 
	            
				
	        }
	        
	      Thread.sleep(5000);
	      extent.flush(); 
          extent.endTest(child1); 
	     
	    }
	
	@Parameters({"siteChrome","siteIdChrome","screenShotNameChrome"})
	@Test(retryAnalyzer=ChromeRetryAnalyzer.class,testName = "DeleteSite in Chrome",priority = 2)
    public void deleteSite(String siteName,String siteURL,
			String screenShotName) throws InterruptedException, IOException
    {
	
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Delete site called \" "+siteName +" \" ");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		 child2 = extent.startTest("Delete Site","Delete site called \" "+siteName +" \" ");
     
		LOGGER.info("Test case Delete Site started executing");
		child2.log(LogStatus.INFO,
				"Test case Delete Site started executing");		

		LOGGER.info("Accessing the Login Page");
                child2.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as admin");
		child2.log(LogStatus.INFO, "Login as admin");
		 extent.flush(); 
		 
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		
		LOGGER.info("Check the site,if site exist Delete the site");
		child2.log(LogStatus.INFO, "Check the site,if site exist Delete the Site");
		
		 
                child2.log(LogStatus.INFO, "Search the site");
                child2.log(LogStatus.INFO, "Click \"Delete\" Button,next to site\""+siteName+"\"");
                child2.log(LogStatus.INFO, "Click \"Delete\" Button,for delete confirmation");
		        child2.log(LogStatus.INFO, "Login as Admin to delete Site in TrashCan");
                child2.log(LogStatus.INFO, "Click \"My Profile\"");
                child2.log(LogStatus.INFO, "Place siteUrl");
                child2.log(LogStatus.INFO, "Click \"Search\" Button");
                child2.log(LogStatus.INFO, "Check the patucular siteUrl");
                child2.log(LogStatus.INFO, "Click \"Selected Item\" Button");
                child2.log(LogStatus.INFO, "Click \"Delete\" Button");
                child2.log(LogStatus.INFO, "Click \"OK\" Button");
                child2.log(LogStatus.INFO, "Click \"OK confirmation\" Button");
                
                extent.flush();
                
                RemoveObjects deleteSite=new RemoveObjects(driver);
		       deleteSite.deleteSite(siteName,siteURL);		
                
                LOGGER.info("CHECK WHETHER SITE\" "+siteName+" \"IS DELETED OR NOT");
                child2.log(LogStatus.INFO,"CHECK WHETHER SITE\" "+siteName+" \"IS DELETED OR NOT");
                
                extent.flush();
                
                NavigateToPage navigateTo = new NavigateToPage(driver);		
		navigateTo.goToHome();
                
                LOGGER.error("Click \"My Profile\" Again");
		child2.log(LogStatus.INFO, "Click \"My Profile\" Again");
                
		 extent.flush();
		 
                child2.log(LogStatus.INFO, "Accessing trashcan Page");
		navigateTo.goToUserTrashCan();	
		
		LOGGER.info("Place siteUrl");
		child2.log(LogStatus.INFO, "Place siteUrl");
		
		 extent.flush();
		 
		TextField textField = new TextField(
				driver,
				By.id("template_x002e_user-trashcan_x002e_user-trashcan_x0023_default-search-text"));
		textField.clearText();
		textField.enterText(siteURL);
		
		LOGGER.info("Click \"Search\" Button");
		child2.log(LogStatus.INFO, "Click \"Search\" Button");
		Button searchButton = new Button(driver,By.xpath("//button[text()='Search']"));
		searchButton.click();
		
		Thread.sleep(2000);
		
		TakeScreenShot ts=new TakeScreenShot();
 	   	ts.takeScreenShot(driver,className, screenShotName+"17");
 	   	child2.log(LogStatus.INFO, "Search Site : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"17"+".png"));
 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
        extent.flush(); 
		//Element.takescreenshot(driver, className, screenShotName+"deleteConfirmation");
		
		//CheckBox checkbox=new CheckBox(driver, By.xpath("//div[@class='name'][text()='"+item+"']/ancestor::td[1]/preceding-sibling::td[2]//input"));
		//checkbox.click();

		LOGGER.info("Check the patucular siteUrl");
		child2.log(LogStatus.INFO, "Check the patucular siteUrl");
		
		if(Element.isElementPresent(driver, By.xpath("//tbody[@class='yui-dt-message']//tr//td//div[text()='No items exist']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,siteName+" SITE IS DELETED SUCCESSFULLY");
			child2.log(LogStatus.PASS, siteName+" SITE IS DELETED SUCCESSFULLY");
			
			
     	   	ts.takeScreenShot(driver,className, screenShotName+"18");
     	   	child2.log(LogStatus.PASS, "Site is Deleted : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"18"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		else if(Element.isElementPresent(driver,By.xpath("//div[text()='"+siteURL+"']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_FAIL,siteName+" SITE IS NOT DELETED SUCCESSFULLY");
			child2.log(LogStatus.FAIL, siteName+" SITE IS NOT DELETED SUCCESSFULLY");
			
			
     	   	ts.takeScreenShot(driver,className, screenShotName+"A18");
     	   	child2.log(LogStatus.FAIL, "Site is NOT Deleted : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"A18"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}		
		else{
			Element.waitUntilElementPresent(driver, By.xpath("//div//div//table//tbody//tr[contains(.,'" + siteURL
					+ "')]//td//div"));
	
	java.util.List<WebElement> myList = driver.findElements(By
			.xpath("//div//div//table//tbody//tr[contains(.,'" + siteURL
					+ "')]//td//div"));

	for (int i = 0; i < myList.size(); i++) {

		if (myList.get(i).getText().equals(siteURL)) 
		{	
			Thread.sleep(2000);
			LOGGER.error(TestCaseProperties.TEXT_TEST_PASS,siteName+ "SITE IS NOT DELETED");
			child2.log(LogStatus.FAIL, siteName+" SITE IS NOT DELETED");
			
			
     	   	ts.takeScreenShot(driver,className, screenShotName+"19");
     	   	child2.log(LogStatus.FAIL, "Site is not deleted : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"19"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		else
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,siteName+" SITE IS DELETED SUCCESSFULLY");
			child2.log(LogStatus.PASS, siteName+"SITE IS DELETED SUCCESSFULLY");
			
			ts.takeScreenShot(driver,className, screenShotName+"20");
     	   	child2.log(LogStatus.PASS, "Site is not deleted : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"20"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		}
		
		}
		Thread.sleep(3000);
		LoginPage loginpage1=new LoginPage(driver);
		loginpage1.logout();
		
		LOGGER.info("Test case Delete Site executed");
		child2.log(LogStatus.INFO, "Test case Delete Site executed");
		 extent.flush();
         extent.endTest(child2);      
               
		
    }
	
	
			
		   
			@AfterMethod
			   public void aftermethod(Method method,ITestResult result) throws Exception{
				
				if(method.getName().equals("addSiteDashlet")) {			
					
					if (result.getStatus() == ITestResult.FAILURE) {
				        child1.log(LogStatus.FAIL,"addSiteDashlet Test failed because "+ result.getThrowable());
				        extent.flush();
				    } else if (result.getStatus() == ITestResult.SKIP) {
				    	child1.log(LogStatus.SKIP, "addSiteDashlet Test skipped because " + result.getThrowable());
				        extent.flush();
				    } else {
				    	child1.log(LogStatus.PASS, "addSiteDashlet Test got executed successfully");
				        extent.flush();
				    }
					}
				else if(method.getName().equals("deleteSite")) {			
					
					if (result.getStatus() == ITestResult.FAILURE) {
				        child2.log(LogStatus.FAIL,"deleteSite Test failed because "+ result.getThrowable());
				        extent.flush();
				    } else if (result.getStatus() == ITestResult.SKIP) {
				    	child2.log(LogStatus.SKIP, "deleteSite Test skipped because " + result.getThrowable());
				        extent.flush();
				    } else {
				    	child2.log(LogStatus.PASS, "deleteSite Test got executed successfully");
				        extent.flush();
				    }
					}
					
				
				driver.quit(); 
				
			   }

			@AfterTest(alwaysRun=true)
			public void extent() {
				
				LOGGER.info("Test case closed");
				parent
					.appendChild(child1)
				  .appendChild(child2);
				extent.endTest(parent);
				extent.close();	
				driver.quit();
				

			} 
			
			
			
}
