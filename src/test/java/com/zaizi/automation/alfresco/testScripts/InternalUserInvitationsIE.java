package com.zaizi.automation.alfresco.testScripts;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.zaizi.automation.alfresco.core.info.TestCaseProperties;
import com.zaizi.automation.alfresco.core.pages.AdminConsolePage;
import com.zaizi.automation.alfresco.core.pages.CreateObjects;
import com.zaizi.automation.alfresco.core.pages.LoginPage;
import com.zaizi.automation.alfresco.core.pages.NavigateToPage;
import com.zaizi.automation.alfresco.core.pages.RemoveObjects;
import com.zaizi.automation.alfresco.core.pages.SearchObjects;
import com.zaizi.automation.alfresco.core.pages.SiteDashboardPage;
import com.zaizi.automation.extentReports.ExtentManagerIE;
import com.zaizi.automation.listeners.IERetryAnalyzer;
import com.zaizi.automation.testng.core.elements.Button;
import com.zaizi.automation.testng.core.elements.Element;
import com.zaizi.automation.testng.core.elements.Span;
import com.zaizi.automation.testng.core.elements.TakeScreenShot;
import com.zaizi.automation.testng.core.elements.TextField;





/**
  *
  * @author mrashad@zaizi.com
  */

public class InternalUserInvitationsIE  {

	
	/**
	 * 
	 * Defining log4j
	 */

	public static final Logger LOGGER = LogManager
			.getLogger(InternalUserInvitationsIE.class.getName());

	
	/**
	 * 
	 * Defining Report
	 */
	static ExtentReports extent;
	static ExtentTest parent;
	static ExtentTest child1;
	static ExtentTest child2;
	static ExtentTest child3;
	static ExtentTest child4;
	static ExtentTest child5;
	
    
	/**
	 * 
	 * Define WebDriver
	 */

	private static WebDriver driver;	
	
	
	
	/**
	 * 
	 * Define className
	 */

	public static String className = InternalUserInvitationsIE.class
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
		parent=extent.startTest("<b>Send invitation to internalUser Test in IE</b>","This is internal User invitation Test,<b>Create the user,Create the site,Send invitation to internal user,Delete the user,Delete the site</b>");
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
	/**
	 * 
	 * @Test Create Internal User Invitations 	 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */

	

	@Parameters({"firstNameIE", "lastNameIE","emailIE","userNameIE","PasswordIE","fullNameIE","screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "Create user in IE",priority = 1)
	public void createUser(String firstName,String lastName,String email,String userName,String password,String fullName,String screenShotName) throws InterruptedException, IOException

	{

		
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Create User in IE "+userName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child1 = extent.startTest("Create new User","Create new user : \" "+userName+" \"");
		
		LOGGER.info("Test case createUser started executing");
		child1.log(LogStatus.INFO,
				"Test case createUser started executing");		

		LOGGER.info("Accessing the Login Page");
        child1.log(LogStatus.INFO, "Accessing the Login Page");        
               	 		         
        LOGGER.info("Login as \"Admin\"");
        child1.log(LogStatus.INFO, "Login as \"Admin\""); 
        extent.flush();
        
   	    LoginPage loginPage=new LoginPage(driver);   	            
   	    loginPage.loginAsAdmin();
   	         
   	    LOGGER.info("Create new user : \" "+userName+" \"");
        child1.log(LogStatus.INFO, "Create new user : \" "+userName+" \""); 
        
   	    LOGGER.info("Accessing the AdminTool Page");
        child1.log(LogStatus.INFO, "Accessing the AdminTool Page");         
        child1.log(LogStatus.INFO, "Navigate \"Users\" in AdminTools");
        child1.log(LogStatus.INFO, "Click \"New User\"");
        child1.log(LogStatus.INFO, "Fill the userfields");
        child1.log(LogStatus.INFO, "Click \"Create User\"");
        extent.flush();
        
        Element.waitForLoad(driver);
		AdminConsolePage createUser=new AdminConsolePage(driver);		
		createUser.createUser(firstName, lastName, email,userName,password,password);			
		Element.waitForLoad(driver);
		Thread.sleep(5000);
		 //If Any Message prompt as "Failure",User is alredy exist
        if(Element.isElementPresent(driver, By.xpath("//div[@id='prompt_h'][text()='Failure']"))) 
        {   
        	Thread.sleep(5000);
            Span notification = new Span(driver, By.xpath("//div[@id='prompt']/descendant::div[@class='bd']"));
            notification.getText();
            
            LOGGER.info("Message display as : "+notification.getText());
            child1.log(LogStatus.INFO, "<font color=blue>Message display as : "+notification.getText()+"<font>");
            
            TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"1");
     	   	child1.log(LogStatus.INFO, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"1"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();   
            
            Thread.sleep(3000);
            Button okaybtn=new Button(driver,By.xpath("//Button[text()='OK']"));
            okaybtn.click();
             
            
           
        }
       //ELSE USER CREATED SUCESSFULLY
        else 
        {   
        	
        	LOGGER.info("User is Successfully Created");
        	child1.log(LogStatus.INFO, "<font color=green>User is Successfully Created<font>");
        	
        	TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"2");
     	   	child1.log(LogStatus.INFO, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"2"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();          
        	
        } 
        
        Thread.sleep(3000);
        LoginPage loginPage1=new LoginPage(driver);  
		loginPage1.logout();	
		
		Element.waitForLoad(driver);    
		Thread.sleep(3000);
        LOGGER.info("CHECK WHETHER USER IS CREATED OR NOT");
		child1.log(LogStatus.INFO, "CHECK WHETHER USER IS CREATED OR NOT");  
		
       
		LOGGER.info("Accessing the Login Page");
        child1.log(LogStatus.INFO, "Accessing the Login Page");

        LOGGER.info("Login as created user"+userName);
        child1.log(LogStatus.INFO, "Login as created user"+userName);
        
        LoginPage loginPage2 = new LoginPage(driver);
        loginPage2.loginAsUser(userName, password);
           
        Thread.sleep(5000);
        LOGGER.info("Verify the HEADER_USER_MENU_NAME");
 		child1.log(LogStatus.INFO, "Verify the HEADER_USER_MENU_NAME");
        
 		if(!(Element.isElementPresent(driver,By.xpath("//div[@class='error']"))))
        {
        	Thread.sleep(5000);
        	
 		if (Element.isElementPresent(driver,By.xpath("//Span[@id='HEADER_USER_MENU_POPUP_text'][text()='"+fullName+"']"))) {
 			
 			Thread.sleep(2000);
 			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"User is Sucessfully Created");
 			child1.log(LogStatus.PASS, "<font color=green>User is Sucessfully Created<font>");
 			
 			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"3");
     	   	child1.log(LogStatus.PASS, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"3"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();    	

 		} else {
 			LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"User is NOT Sucessfully Created");
 			child1.log(LogStatus.FAIL, "<font color=RED>User is NOT Sucessfully Created<font>");
 			
 			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"3");
     	   	child1.log(LogStatus.FAIL, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"3"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();   
           
 		}
		
      }
 		else
 		{
        
        	
        	LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"User is NOT Sucessfully Created");
  			child1.log(LogStatus.FAIL, "<font color=RED>User is NOT Sucessfully Created<font>");
  			
  			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"4");
     	   	child1.log(LogStatus.FAIL, "User is NOT created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"4"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();        
 		}
 		
 		Thread.sleep(3000);
        LoginPage loginPage3 = new LoginPage(driver);
        loginPage3.logout();
        Element.waitForLoad(driver);
        
        LOGGER.info("Test case createUser executed");
		child1.log(LogStatus.INFO, "Test case createUser executed");
		extent.flush();
        extent.endTest(child1);       
		
		

	}
	
	/**
	 * 
	 * @Test createSite
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */

	@Parameters({"siteIE", "siteIdIE", "siteCreatorNameIE","expectedResultIE","isPrivateIE", "screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "createSite in IE",priority = 2)
    public void createSite(String siteName,String siteId,String siteCreatorName,String expectedResult,
			Boolean isPrivate,String screenShotName) throws InterruptedException, IOException
    {
    {
		 
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Create Site "+siteName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child2 = extent.startTest("createSite","Create site called \" "+siteName +" \",Is it private Site "+isPrivate);

		
		LOGGER.info("Test case createSite started executing");
		child2.log(LogStatus.INFO,
				"Test case createSite started executing");		

		LOGGER.info("Accessing the Login Page");
        child2.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as admin");
		child2.log(LogStatus.INFO, "Login as admin");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		
		CreateObjects createObjects = new CreateObjects(driver);
		
		if (isPrivate)
        {	
            LOGGER.info("CREATE PRIVATE SITE");
             child2.log(LogStatus.INFO, "CREATE PRIVATE SITE");
                
             child2.log(LogStatus.INFO,"Click \"Create Site\" ");
             child2.log(LogStatus.INFO,"Enter "+siteName+" in \"site Name Field\" ");
	     child2.log(LogStatus.INFO,"Enter "+siteId+" in \"site URL Field\" ");	
             child2.log(LogStatus.INFO,"Check \"private\" Option");
             child2.log(LogStatus.INFO,"Click \"OK\" Button "); 
             createObjects.createPrivateSite(siteName, siteId,expectedResult);
			
                        WebDriver augmentedDriver1 = new Augmenter().augment(driver);
		        File screenshot1 = ((TakesScreenshot)augmentedDriver1).getScreenshotAs(OutputType.FILE);
		    	FileUtils.copyFile(screenshot1, new File(TestCaseProperties.REPORT_TEST_PATH_IE+className+"/"+screenShotName+"171"+".jpg"));	
		    	child2.log(LogStatus.INFO, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"171"+".jpg"));  		
		        System.out.println("Screenshot Taken Successfully!!!!"); 
                        
                        //If Site Created
	    if(Element.isElementPresent(driver, By.xpath("//h1[@id='HEADER_TITLE']/a[text()='"+siteName+"']")))
		{	
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Site "+siteName +" CREATED ");
			child2.log(LogStatus.INFO, "<font color=blue>Site "+siteName +" CREATED <font> ");	
		    	
		}		
		//If Site IS NOT CREATED
		//Expected Result1=[COULD NOT CREATE SITE SINCE THE URL IS ALREADY USED]
		//ExpectedResult1==CurrentResult
		else 
		{
			SiteDashboardPage siteDash1=new SiteDashboardPage(driver);
			String siteErrorNotification=siteDash1.errorNotification(siteName);
	    	
    	    if(siteErrorNotification.toUpperCase().equals(expectedResult))
    		{    	    			    
	    	        LOGGER.info("Expected Results : " + expectedResult);
	        	child2.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child2.log(LogStatus.PASS, "Current Test Results : " +"<font color=green>" +siteErrorNotification+"<font>");	        	

                        Button okaybtn=new Button(driver,By.xpath("//button[text()='OK']"));
		        okaybtn.click(); 
    		}
    	    //ExpectedResult1!=CurrentResult[PROBLEM]
    	    else 
    	    {
    	    	LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Private Site "+siteName +"IS NOT CREATED ");
                child2.log(LogStatus.FAIL, "Private Site "+siteName +"IS NOT CREATED ");
			
	    	    LOGGER.info("Expected Results : " + expectedResult);
	            child2.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child2.log(LogStatus.FAIL, "Current Test Results : " +"<font color=red>" +siteErrorNotification+"<font>");        	
	        		
    	    }
		}
             
             Element.waitForLoad(driver);
             LOGGER.info("CHECK WHETHER PRIVATE SITE CREATED OR NOT");
             child2.log(LogStatus.INFO, "CHECK WHETHER PRIVATE SITE CREATED OR NOT");
             
             LOGGER.info("Check whether \" " +siteName+ "\" SITE IS VISIBLE TO "+siteCreatorName);
             child2.log(LogStatus.INFO, "Check whether \" " +siteName+ "\" SITE IS VISIBLE TO "+siteCreatorName);
	     Thread.sleep(5000);

             LOGGER.info("Search the sitename in Site finder");
             child2.log(LogStatus.INFO, "Search the sitename in Site finder");
             
			SearchObjects searchSite = new SearchObjects(driver);			
			searchSite.searchSite(siteName);	
			
			if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
			{
				LOGGER.info("Message display as \"No sites found\"");
				child2.log(LogStatus.INFO, "Message display as \"No sites found\"");
				
				LOGGER.info(siteName+ " SITE IS NOT VISIBLE TO "+siteCreatorName);
				child2.log(LogStatus.FAIL,"<font color=blue>"+siteName+ " SITE IS NOT VISIBLE TO "+siteCreatorName+"<font>");
                                
				TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"172");
	     	   	child2.log(LogStatus.FAIL, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"172"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
				
			}
			else if (Element.isTextPresentInListForSite(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName))
			{	
				LOGGER.info(siteName+ " SITE IS VISIBLE TO "+siteCreatorName);
				child2.log(LogStatus.INFO,"<font color=blue>"+siteName+ " SITE IS VISIBLE TO "+siteCreatorName+"<font>");
                                
				TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"172");
	     	   	child2.log(LogStatus.PASS, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"173"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
							
			}				
			
		   
		   
                Thread.sleep(5000);                        
                Element.waitForLoad(driver);
                
                LoginPage loginPage1 = new LoginPage(driver);
                loginPage1.logout();        
                Element.waitForLoad(driver);
                
                LOGGER.info("Login as  \"privateuser\" ");
                child2.log(LogStatus.INFO,"Login as \"privateuser\" ");
                
                loginPage1.loginAsUser1("privateuser", "1qaz@WSX");
                Element.waitForLoad(driver);
                Thread.sleep(5000);
                LOGGER.info("Check whether \"" +siteName+ "\" SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                child2.log(LogStatus.INFO, "Check whether \" " +siteName+ "\" SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                
                SearchObjects searchSite2 = new SearchObjects(driver);
                searchSite2.searchSite(siteName);
                Element.waitForLoad(driver);       
                
                
                if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
                {
                    Thread.sleep(5000);
                    LOGGER.info("Message display as \"No sites found\"");
                    child2.log(LogStatus.INFO, "Message display as \"No sites found\"");
                    
                    LOGGER.info(siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER");
                    child2.log(LogStatus.PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER \"privateuser\"");
                    
                    TakeScreenShot ts=new TakeScreenShot();
             	   	ts.takeScreenShotIE(driver,className, screenShotName+"174");
             	   	child2.log(LogStatus.PASS, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"174"+".png"));
             	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                    extent.flush();   
                    
                }
                else 
                {
                    Thread.sleep(5000);
                    if (Element.isTextPresentInList(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName)) {
                        
                        LOGGER.info(siteName+ " SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                        child2.log(LogStatus.FAIL,siteName+ " SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                        
                        TakeScreenShot ts=new TakeScreenShot();
                 	   	ts.takeScreenShotIE(driver,className, screenShotName+"175");
                 	   	child2.log(LogStatus.FAIL, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"175"+".png"));
                 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                        extent.flush();  
                        
                        
                    } else {
                        LOGGER.info(siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER");
                        child2.log(LogStatus.PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER \"privateuser\"");
                        TakeScreenShot ts=new TakeScreenShot();
                 	   	ts.takeScreenShotIE(driver,className, screenShotName+"176");
                 	   	child2.log(LogStatus.PASS, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"176"+".png"));
                 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                        extent.flush();  
                        
                        
                    }
                }            
            
        }
		else
		{	LOGGER.info("CREATE PUBLIC SITE");
        child2.log(LogStatus.INFO, "CREATE PUBLIC SITE");
             
                        child2.log(LogStatus.INFO,"Click \"Create Site\" ");
             child2.log(LogStatus.INFO,"Enter "+siteName+" in \"site Name Field\" ");
	     child2.log(LogStatus.INFO,"Enter "+siteId+" in \"site URL Field\" ");	
             child2.log(LogStatus.INFO,"Check \"private\" Option");
             child2.log(LogStatus.INFO,"Click \"OK\" Button ");
             
			createObjects.createPublicSite(siteName, siteId, expectedResult);
                        Thread.sleep(5000);
                                                
                        //If Site Created
	    if(Element.isElementPresent(driver, By.xpath("//h1[@id='HEADER_TITLE']/a[text()='"+siteName+"']")))
		{	
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Site "+siteName +" CREATED ");
			child2.log(LogStatus.INFO, "<font color=blue>Site "+siteName +" CREATED <font> ");
                        
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"177");
     	   	child2.log(LogStatus.PASS, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"177"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
		    	
		}		
		//If Site IS NOT CREATED
		//Expected Result1=[COULD NOT CREATE SITE SINCE THE URL IS ALREADY USED]
		//ExpectedResult1==CurrentResult
		else 
		{
			SiteDashboardPage siteDash1=new SiteDashboardPage(driver);
			String siteErrorNotification=siteDash1.errorNotification(siteName);
	    	
    	    if(siteErrorNotification.toUpperCase().equals(expectedResult))
    		{    	    			    
	    	        LOGGER.info("Expected Results : " + expectedResult);
	        	child2.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child2.log(LogStatus.PASS, "Current Test Results : " +"<font color=green>" +siteErrorNotification+"<font>");	        	

	        	TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"178");
	     	   	child2.log(LogStatus.PASS, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"178"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
                        
                        Button okaybtn=new Button(driver,By.xpath("//button[text()='OK']"));
		        okaybtn.click(); 
    		}
    	    //ExpectedResult1!=CurrentResult[PROBLEM]
    	    else 
    	    {
    	    	LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Private Site "+siteName +"IS NOT CREATED ");
                child2.log(LogStatus.FAIL, "Private Site "+siteName +"IS NOT CREATED ");
			
	    	    LOGGER.info("Expected Results : " + expectedResult);
	            child2.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child2.log(LogStatus.FAIL, "Current Test Results : " +"<font color=red>" +siteErrorNotification+"<font>");        	
	        	
	        	TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"179");
	     	   	child2.log(LogStatus.FAIL, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"179"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
    	    }
		}
                LOGGER.info("CHECK WHETHER "+siteName+" IS CREATED OR NOT");
                child2.log(LogStatus.INFO,"CHECK WHETHER "+siteName+" IS CREATED OR NOT");
                
		LOGGER.info("CHECK WHETHER "+siteName+" IS DISPLAY IN SITES LIST");
                child2.log(LogStatus.INFO,"CHECK WHETHER "+siteName+" IS DISPLAY IN SITES LIST");
                
                LOGGER.info("Click \"Sites\"");
		child2.log(LogStatus.INFO, "Click \"Sites\"");
                
                LOGGER.info("Click \"Site Finder\"");
		child2.log(LogStatus.INFO, "Click \"Site Finder\"");
                
                LOGGER.info("Search sitename");
		child2.log(LogStatus.INFO, "Search sitename");
                
		SearchObjects searchSite = new SearchObjects(driver);
		searchSite.searchSite(siteName);
                
		if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
		{
			LOGGER.info("Message display as \"No sites found\"");
			child2.log(LogStatus.INFO, "Message display as \"No sites found\"");
                        
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"180");
     	   	child2.log(LogStatus.PASS, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"180"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
			
		}
		else 
		{
			if (Element.isTextPresentInListForSite(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName)) {
				
                            LOGGER.info("Site is Display in \"Site Search\"");
                            child2.log(LogStatus.PASS, "<font color=green>Site is Display in \"Site Search\"<font>");
                            
                        	TakeScreenShot ts=new TakeScreenShot();
                     	   	ts.takeScreenShotIE(driver,className, screenShotName+"181");
                     	   	child2.log(LogStatus.PASS, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"181"+".png"));
                     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                            extent.flush();  
                            
                        
			} else {
				
                            LOGGER.info("Site IS NOT Display in \"Site Search\"");
                            child2.log(LogStatus.FAIL, "<font color=red>Site is NOT Display in \"Site Search\"<font>");
                            
                        	TakeScreenShot ts=new TakeScreenShot();
                     	   	ts.takeScreenShotIE(driver,className, screenShotName+"182");
                     	   	child2.log(LogStatus.FAIL, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"182"+".png"));
                     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                            extent.flush();  
				
				
			}
		}
		}	
		loginPage.logout();
		LOGGER.info("Test case createSite executed");
		child2.log(LogStatus.INFO, "Test case createSite executed");
                
                 extent.endTest(child2);        
                extent.flush();
                
    }
		
    }
	
	/**
	 * 
	 * @Test sendSite Invitation to internalUser	 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */
	
	
	@Parameters({"firstNameIE","userNameIE","siteIE", "siteIdIE","roleNameIE","expectedInvitationresultIE","screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "sendSiteInvitationtointernalUser in IE",priority = 3)
    public void sendSiteinvitationtoInternaluser(String firstName,String userName, String siteName,String siteId,
			String roleName,String expectedInvitationresult,String screenShotName) throws InterruptedException, IOException
    {
    	
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING,
				"Check whether Site Invitation sent BY " + firstName);
		
		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child3= extent.startTest("Send SiteInvitation",
				"Check whether Site Invitation sent BY " + firstName);

	
		LoginPage loginPage = new LoginPage(driver);

		//Login as "userName" 
		LOGGER.info("Accessing the LoginPage");
		child3.log(LogStatus.INFO,"Accessing the LoginPage");
		
		LOGGER.info("Login As Admin");
		child3.log(LogStatus.INFO,"Login As Admin");
		loginPage.loginAsAdmin();
		Element.waitForLoad(driver);

		LOGGER.info("Test case sendSiteInvitation started executing");
		child3.log(LogStatus.INFO,
				"Test case sendSiteInvitation started executing");

		//Check whether Invite Option is Available For InternalUsers
		
		//InviteButton IS AVAILABLE FOR internalUsers
		SiteDashboardPage siteDashboardPage = new SiteDashboardPage(driver);
		if (siteDashboardPage.checkInternalUserInvite(siteName, className,
				screenShotName + "psiteName1")) {
			LOGGER.info("\"Search for People \" OPTION IS AVAILABLE for INTERNAL USERS ");
			child3.log(LogStatus.INFO,
					"\"Search for People \" OPTION IS AVAILABLE for INTERNAL USERS ");

			LOGGER.info("SEND THE SITE INVITATION");
			child3.log(LogStatus.INFO,"SEND THE SITE INVITATION");
			
			 child3.log(LogStatus.INFO,"Enter "+userName+" in \"search For people Field\" ");
			 child3.log(LogStatus.INFO,"Click \"Search\" Button ");
			 child3.log(LogStatus.INFO,"Click \"Add>>\" Button "+userName);
			 child3.log(LogStatus.INFO,"Select \""+roleName+"\" next to "+userName);
			 child3.log(LogStatus.INFO,"Click \"Invite\" Button ");
			 
			 Thread.sleep(3000);
			 
			siteDashboardPage.searchPopleForInvite(userName, roleName,
					className, screenShotName + "pnew1");
			
			

			String str1 = siteDashboardPage.successNotification(className,
					screenShotName + "pe1");
			
			//expectedInvitationresult=[1 INVITES SENT OUT, 0 FAILURES]
			//expectedInvitationresult==CurrentResult[PASS]
			if (str1.toUpperCase().equals(expectedInvitationresult)) {
				LOGGER.info("Expected Results : " + expectedInvitationresult);
				child3.log(LogStatus.INFO, "Expected Results : "
						+ expectedInvitationresult);

				LOGGER.info("Current Test Results : " + str1);
				child3.log(LogStatus.PASS, "Current Test Results : "
						+ "<font color=green>" + str1 + "<font>");
				
				TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"183");
	     	   	child3.log(LogStatus.PASS, "Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"183"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
			//	Element.takescreenshot(driver, className, screenShotName
				//		+ "spvalid1");
			} 
			//expectedInvitationresult!=CurrentResult[FAIL]
			else {
				LOGGER.info("Expected Results : " + expectedInvitationresult);
				child3.log(LogStatus.INFO, "Expected Results : "
						+ expectedInvitationresult);

				LOGGER.info("Current Test Results : " + str1);
				child3.log(LogStatus.PASS, "Current Test Results : "
						+ "<font color=red>" + str1 + "<font>");
				
				TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"184");
	     	   	child3.log(LogStatus.PASS, "Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"184"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
				//Element.takescreenshot(driver, className, screenShotName
					//	+ "spvalid2");
			}
		} 
		//InviteButton IS NOT AVAILABLE FOR internalUsers
		else {
			LOGGER.info("\"Search for People \" OPTION IS NOT AVAILABLE for INTERNAL USERS ");
			child3.log(LogStatus.INFO,
					"\"Search for People \" OPTION IS NOT AVAILABLE for INTERNAL USERS ");
			
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"185");
     	   	child3.log(LogStatus.PASS, "Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"185"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
		}

		loginPage.logout();
		LOGGER.info("Test case sendSiteInvitation executed");
		child3.log(LogStatus.INFO,"Test case sendSiteInvitation executed");

                 extent.endTest(child3);        
                extent.flush();
		
		
    }

	
	/**
	 * 
	 * @Test DeleteSite
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */	 
	
	@Parameters({"siteIE", "siteIdIE","screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "DeleteSite in IE",priority = 4)
    public void deleteSite(String siteName,String siteId,
			String screenShotName) throws InterruptedException, IOException
    {
	
		
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Delete site called \" "+siteName +" \" ");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child4 = extent.startTest("deleteSite","Delete site called \" "+siteName +" \" ");

		

		LOGGER.info("Test case deleteSite started executing");
		child4.log(LogStatus.INFO,
				"Test case deleteSite started executing");		

		LOGGER.info("Accessing the Login Page");
                child4.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as admin");
		child4.log(LogStatus.INFO, "Login as admin");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		
                LOGGER.info("Check the site,if site exist Delet the user");
		child4.log(LogStatus.INFO, "Check the site,if site exist Delete the user");
                child4.log(LogStatus.INFO, "Search the site");
                child4.log(LogStatus.INFO, "Click \"Delete\" Button,next to site\""+siteName+"\"");
                child4.log(LogStatus.INFO, "Click \"Delete\" Button,for delete confirmation");
		child4.log(LogStatus.INFO, "Login as Admin to delete Site in TrashCan");
                child4.log(LogStatus.INFO, "Click \"My Profile\"");
                child4.log(LogStatus.INFO, "Place siteUrl");
                child4.log(LogStatus.INFO, "Click \"Search\" Button");
                child4.log(LogStatus.INFO, "Check the patucular siteUrl");
                child4.log(LogStatus.INFO, "Click \"Selected Item\" Button");
                child4.log(LogStatus.INFO, "Click \"Delete\" Button");
                child4.log(LogStatus.INFO, "Click \"OK\" Button");
                child4.log(LogStatus.INFO, "Click \"OK confirmation\" Button");
                RemoveObjects deleteSite=new RemoveObjects(driver);
		deleteSite.deleteSite(siteName, siteId);		
                
                LOGGER.info("CHECK WHETHER SITE\" "+siteName+" \"IS DELETED OR NOT");
		child4.log(LogStatus.INFO,"CHECK WHETHER SITE\" "+siteName+" \"IS DELETED OR NOT");
                
                NavigateToPage navigateTo = new NavigateToPage(driver);		
		navigateTo.goToHome();
                
                LOGGER.error("Click \"My Profile\" Again");
		child4.log(LogStatus.INFO, "Click \"My Profile\" Again");
                
                child4.log(LogStatus.INFO, "Accessing trashcan Page");
		navigateTo.goToUserTrashCan();	
		
		LOGGER.info("Place siteUrl");
		child4.log(LogStatus.INFO, "Place siteUrl");
		TextField textField = new TextField(
				driver,
				By.id("template_x002e_user-trashcan_x002e_user-trashcan_x0023_default-search-text"));
		textField.clearText();
		textField.enterText(siteId);
		
		LOGGER.info("Click \"Search\" Button");
		child4.log(LogStatus.INFO, "Click \"Search\" Button");
		Button searchButton = new Button(driver,By.xpath("//button[text()='Search']"));
		searchButton.click();
		Thread.sleep(2000);
                
		TakeScreenShot ts=new TakeScreenShot();
 	   	ts.takeScreenShotIE(driver,className, screenShotName+"186");
 	   	child4.log(LogStatus.PASS, "Snapshot below: " +child4.addScreenCapture("./"+className+"/"+screenShotName+"186"+".png"));
 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
        extent.flush();  
		//Element.takescreenshot(driver, className, screenShotName+"deleteConfirmation");
		
		//CheckBox checkbox=new CheckBox(driver, By.xpath("//div[@class='name'][text()='"+item+"']/ancestor::td[1]/preceding-sibling::td[2]//input"));
		//checkbox.click();

		LOGGER.info("Check the patucular siteUrl");
		child4.log(LogStatus.INFO, "Check the patucular siteUrl");
		
		if(Element.isElementPresent(driver, By.xpath("//tbody[@class='yui-dt-message']//tr//td//div[text()='No items exist']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,siteName+" SITE IS DELETED SUCCESSFULLY");
			child4.log(LogStatus.PASS, siteName+" SITE IS DELETED SUCCESSFULLY");
			
			
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"187");
     	   	child4.log(LogStatus.PASS, "Site is Deleted : " +child4.addScreenCapture("./"+className+"/"+screenShotName+"187"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		else if(Element.isElementPresent(driver,By.xpath("//div[text()='"+siteId+"']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_FAIL,siteName+" SITE IS NOT DELETED SUCCESSFULLY");
			child4.log(LogStatus.FAIL, siteName+" SITE IS NOT DELETED SUCCESSFULLY");
			
			
	 	   	ts.takeScreenShotIE(driver,className, screenShotName+"A18");
	 	   	child4.log(LogStatus.FAIL, "Site is NOT Deleted : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"A18"+".png"));
	 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	        extent.flush(); 
		}
		else{
			Element.waitUntilElementPresent(driver, By.xpath("//div//div//table//tbody//tr[contains(.,'" + siteId
					+ "')]//td//div"));
	
	java.util.List<WebElement> myList = driver.findElements(By
			.xpath("//div//div//table//tbody//tr[contains(.,'" + siteId
					+ "')]//td//div"));

	for (int i = 0; i < myList.size(); i++) {

		if (myList.get(i).getText().equals(siteId)) 
		{	
			Thread.sleep(2000);
			LOGGER.info(siteName+ "SITE IS NOT DELETED");
			child4.log(LogStatus.FAIL, siteName+" SITE IS NOT DELETED");
			TakeScreenShot ts2=new TakeScreenShot();
     	   	ts2.takeScreenShotIE(driver,className, screenShotName+"188");
     	   	child4.log(LogStatus.FAIL, "Snapshot below: " +child4.addScreenCapture("./"+className+"/"+screenShotName+"188"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
		}
		else
		{
			LOGGER.info(siteName+" SITE IS DELETED SUCCESSFULLY");
			child4.log(LogStatus.PASS, siteName+"SITE IS DELETED SUCCESSFULLY");
			TakeScreenShot ts3=new TakeScreenShot();
     	   	ts3.takeScreenShotIE(driver,className, screenShotName+"189");
     	   	child4.log(LogStatus.PASS, "Snapshot below: " +child4.addScreenCapture("./"+className+"/"+screenShotName+"189"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
		}
		}
		
		}
		
		
		loginPage.logout();
		
		LOGGER.info("Test case deleteSite executed");
		child4.log(LogStatus.INFO, "Test case deleteSite executed");
                
                extent.endTest(child4);        
                extent.flush();
		
    }
   
	
	
	/**
     * deleteUser test case
     * 
     * @throws InterruptedException
	 * @throws IOException 
     */
	
	
	@Parameters({"firstNameIE", "userNameIE", "lastNameIE","emailIE","fullNameIE","screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "Create user in IE",priority = 5)
	public void deleteUser(String firstName,String userName, String lastName,String email,String fullName,String screenShotName) throws InterruptedException, IOException


	{
		
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Delete User called \" "+userName+ " \"once loginTest Over");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child5 = extent.startTest("deleteUser","Delete User called \" "+userName+" \" once loginTest Over");

		

		LOGGER.info("Test case deleteUser started executing");
		child5.log(LogStatus.INFO,
				"Test case deleteUser started executing");		

		LOGGER.info("Accessing the Login Page Again");
        child5.log(LogStatus.INFO, "Accessing the Login Page Again");
        
		LOGGER.info("Login as admin");
		child5.log(LogStatus.INFO, "Login as admin");

		Thread.sleep(3000);
		LoginPage loginPage1 = new LoginPage(driver);
		loginPage1.loginAsAdmin();
		
		LOGGER.info("CHECK WHETHER USER EXIST TO DELETE");
		child5.log(LogStatus.INFO, "CHECK WHETHER USER EXIST TO DELETE");
		
		child5.log(LogStatus.INFO, "Accessing HomePage");
		child5.log(LogStatus.INFO, "Accessing Admintool page \"Users\"");
		child5.log(LogStatus.INFO, "Accessing UserProfilePage");
		child5.log(LogStatus.INFO, "Search User");
		child5.log(LogStatus.INFO, "Enter the username "+userName);	
		child5.log(LogStatus.INFO, "Click \"Search\" Button");
		
                Element.waitForLoad(driver);
		AdminConsolePage adminConsolePage1 = new AdminConsolePage(driver);		
		if (adminConsolePage1.checkUserPresence(userName)) {
			
			child5.log(LogStatus.INFO, "<font color=green>User "+firstName+" is exist <font>");
			WebDriver augmentedDriver1 = new Augmenter().augment(driver);
	        File screenshot1 = ((TakesScreenshot)augmentedDriver1).getScreenshotAs(OutputType.FILE);
	    	FileUtils.copyFile(screenshot1, new File(TestCaseProperties.REPORT_TEST_PATH_IE+className+"/"+screenShotName+"192"+".jpg"));	
	    	child5.log(LogStatus.INFO, "Snapshot below: " +child5.addScreenCapture("./"+className+"/"+screenShotName+"192"+".jpg"));  		
	        System.out.println("Screenshot Taken Successfully!!!!");  
	            
			child5.log(LogStatus.INFO, "Accessing UserProfilePage Again");
			child5.log(LogStatus.INFO, "Enter the username "+userName+" to search");
			child5.log(LogStatus.INFO, "Click \"search\" button");
			child5.log(LogStatus.INFO, "Click user to go inside the userprofile");		
			child5.log(LogStatus.INFO, "Click \"Delete User\" Button");
			child5.log(LogStatus.INFO, "Click \"Delete Confirmation\" Button");
			
                        Element.waitForLoad(driver);
			AdminConsolePage deleteUser=new AdminConsolePage(driver);                
			deleteUser.deleteUser(firstName,lastName,userName,className, screenShotName);
			
		}
		else
		{
			child5.log(LogStatus.INFO, "User : " + firstName
					+ " Not Available in the System to Delete");
		}
		
		
        LOGGER.info("CHECK WHETHER USER IS DELETED OR NOT");
		child5.log(LogStatus.INFO, "CHECK WHETHER USER IS DELETED OR NOT");
           
		child5.log(LogStatus.INFO, "Accessing HomePage");
		child5.log(LogStatus.INFO, "Accessing Admintool page \"Users\"");
		child5.log(LogStatus.INFO, "Accessing UserProfilePage");
		child5.log(LogStatus.INFO, "Search User");
		child5.log(LogStatus.INFO, "Enter the username "+userName);	
		child5.log(LogStatus.INFO, "Click \"Search\" Button");
		
                Element.waitForLoad(driver);
		AdminConsolePage adminConsolePage2 = new AdminConsolePage(driver);
		if(adminConsolePage2.checkUserPresence(userName))
			{
				LOGGER.info("User "+firstName+" IS NOT DELETED");
		    	child5.log(LogStatus.FAIL, "<font color=red>User "+firstName+" IS NOT DELETED<font>");
		    	
		    	TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"190");
	     	   	child5.log(LogStatus.FAIL, "Snapshot below: " +child5.addScreenCapture("./"+className+"/"+screenShotName+"190"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
		    	
			}
			
			else
			{
				LOGGER.info("User "+firstName+" IS DELETED SUCCESSFULLY DELETED");
		        child5.log(LogStatus.PASS, "<font color=green>User "+firstName+" IS SUCCESSFULLY DELETED<font>");
		        
		        TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"191");
	     	   	child5.log(LogStatus.PASS, "Snapshot below: " +child5.addScreenCapture("./"+className+"/"+screenShotName+"191"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
		       
			}
		loginPage1.logout();
		Element.waitForLoad(driver);
		LOGGER.info("Test case deleteUser executed");
		child5.log(LogStatus.INFO, "Test case deleteUser executed");
                extent.endTest(child5);        
                extent.flush();
		
		
   
		
		
    }
	
   
	@AfterMethod
	   public void aftermethod(Method method,ITestResult result) throws Exception{
		
		if(method.getName().equals("createUser")) {			
			
			if (result.getStatus() == ITestResult.FAILURE) {
		        child1.log(LogStatus.FAIL,"createUser Test failed because "+ result.getThrowable());
		        extent.flush();
		    } else if (result.getStatus() == ITestResult.SKIP) {
		    	child1.log(LogStatus.SKIP, "createUser Test skipped because " + result.getThrowable());
		        extent.flush();
		    } else {
		    	child1.log(LogStatus.PASS, "createUser Test got executed successfully");
		        extent.flush();
		    }
			}
			else if(method.getName().equals("createSite")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child2.log(LogStatus.FAIL,"createSite Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child2.log(LogStatus.SKIP, "createSite Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child2.log(LogStatus.PASS, "createSite Test got executed successfully");
			        extent.flush();
			    }
				}
			else if(method.getName().equals("sendSiteinvitationtoInternaluser")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child3.log(LogStatus.FAIL,"sendSiteinvitationtoInternaluser Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child3.log(LogStatus.SKIP, "sendSiteinvitationtoInternaluser Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child3.log(LogStatus.PASS, "sendSiteinvitationtoInternaluser Test got executed successfully");
			        extent.flush();
			    }
				}
			else if(method.getName().equals("deleteSite")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child4.log(LogStatus.FAIL,"deleteSite Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child4.log(LogStatus.SKIP, "deleteSite Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child4.log(LogStatus.PASS, "deleteSite Test got executed successfully");
			        extent.flush();
			    }
				}
			else if(method.getName().equals("deleteUser")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child5.log(LogStatus.FAIL,"deleteUser Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child5.log(LogStatus.SKIP, "deleteUser Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child5.log(LogStatus.PASS, "deleteUser Test got executed successfully");
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
		  .appendChild(child2)
		  .appendChild(child3)
		  .appendChild(child4)		 
		  .appendChild(child5);
		extent.endTest(parent);
		extent.close();	
		driver.quit();
		

	} 
}
	


