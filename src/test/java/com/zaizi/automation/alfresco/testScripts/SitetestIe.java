package com.zaizi.automation.alfresco.testScripts;


import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import com.zaizi.automation.alfresco.core.elements.Button;
import com.zaizi.automation.alfresco.core.elements.Element;
import com.zaizi.automation.alfresco.core.elements.Span;
import com.zaizi.automation.alfresco.core.elements.TakeScreenShot;
import com.zaizi.automation.alfresco.core.elements.TextField;
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

public class SitetestIe {

	/**
	 * 
	 * Defining log4j
	 */

	public static final Logger LOGGER = LogManager
			.getLogger(SitetestIe.class.getName());
	
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
	/**
	 * 
	 * Define WebDriver
	 */

	private static WebDriver driver;	
	
	
	
	/**
	 * 
	 * Define className
	 */

	public static String className = SitetestIe.class
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
		parent=extent.startTest("<b>Site Test in IE</b>","This is Site Test,<b>Create the Site,Edit the Site Name,Delete the Site</b> & prerequsite is create \"privateUser\" user inorder to check private site is created or not");
		LOGGER.info("Testcases Started");
		

	}
	
	@BeforeMethod(alwaysRun=true)
	public static void beforemethod() throws MalformedURLException{
				//Set the DriverType(BrowserName,Platform)
				driver = TestCaseProperties.driverType("IE", "WINDOWS");
				
				driver.manage().window().setSize(new Dimension(1920, 1920));
				
				//Get LoginScreen_URl from TestcaseProperties Values
				driver.get(TestCaseProperties.LOGIN_SCREEN_URL);	
	}
	
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
        	
            Span notification = new Span(driver, By.xpath("//div[@id='prompt']/descendant::div[@class='bd']"));
            notification.getText();
            
            LOGGER.info("Message display as : "+notification.getText());
            child1.log(LogStatus.INFO, "<font color=blue>Message display as : "+notification.getText()+"<font>");
            
            TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"1");
     	   	child1.log(LogStatus.INFO, "User is alredy created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"1"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();             
            
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
     	   	child1.log(LogStatus.INFO, "User is created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"2"+".png"));
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
     	   	child1.log(LogStatus.PASS, "User is created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"3"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();    	

 		} else {
 			LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"User is NOT Sucessfully Created");
 			child1.log(LogStatus.FAIL, "<font color=RED>User is NOT Sucessfully Created<font>");
 			
 			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"3");
     	   	child1.log(LogStatus.FAIL, "User is created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"3"+".png"));
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
	 * @Test createUser	 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */

	@Parameters({"siteIE", "isPrivateIE","siteIdIE","expectedResultIE","siteCreatorNameIE","screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "Create site in IE",priority = 2)
	public void createSite(String siteName,Boolean isPrivate,String siteId,String expectedResult,String siteCreatorName,String screenShotName) throws InterruptedException, IOException

	{
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Create Site "+siteName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		 child2 = extent.startTest("Create Site","Create site called \" "+siteName +" \",Is it private Site "+isPrivate);        

		LOGGER.info("Test case Create Site started executing");
		child2.log(LogStatus.INFO,
				"Test case Create Site started executing");		

		LOGGER.info("Accessing the Login Page");
        child2.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as admin");
		child2.log(LogStatus.INFO, "Login as admin");
		extent.flush(); 
		 
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
             extent.flush();        
             createObjects.createPrivateSite(siteName, siteId,expectedResult);
			
            TakeScreenShot ts=new TakeScreenShot();
       	   	ts.takeScreenShotIE(driver,className, screenShotName+"5");
       	   	child2.log(LogStatus.INFO, "Site created : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"5"+".png"));
       	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
            Thread.sleep(5000);
            
        //If Site Created
		if(Element.isElementPresent(driver, By.xpath("//h1[@id='HEADER_TITLE']/a[text()='"+siteName+"']")))
		{	
			LOGGER.info("Site "+siteName +" CREATED ");
			child2.log(LogStatus.INFO, "<font color=blue>Site "+siteName +" CREATED <font> ");	
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
    	    	LOGGER.info("Private Site "+siteName +"IS already CREATED ");
                child2.log(LogStatus.INFO, "Private Site "+siteName +"IS already CREATED ");
                
	    	    LOGGER.info("Expected Results : " + expectedResult);
	        	child2.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child2.log(LogStatus.INFO, "Current Test Results : " +"<font color=blue>" +siteErrorNotification+"<font>");	        	

                Button okaybtn=new Button(driver,By.xpath("//button[text()='OK']"));
		        okaybtn.click(); 
		        
		        Thread.sleep(2000);
		        
		        Button okayButton=new Button(driver,By.xpath("//Button[text()='Cancel']"));
		        okayButton.click(); 	
		        extent.flush(); 
    		}
    	    //ExpectedResult1!=CurrentResult[PROBLEM]
    	    else 
    	    {
    	    	LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Private Site "+siteName +"IS NOT CREATED ");
                child2.log(LogStatus.FAIL, "Private Site "+siteName +"IS NOT CREATED ");
			
	    	    LOGGER.info("Expected Results : " + expectedResult);
	            child2.log(LogStatus.FAIL, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child2.log(LogStatus.FAIL, "Current Test Results : " +"<font color=red>" +siteErrorNotification+"<font>");        	
	        		
	        	 extent.flush(); 
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
             
             extent.flush(); 
             
			SearchObjects searchSite = new SearchObjects(driver);			
			searchSite.searchSite(siteName);	
			Thread.sleep(5000);
			
			if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
			{
				LOGGER.info("Message display as \"No sites found\"");
				child2.log(LogStatus.INFO, "Message display as \"No sites found\"");
				
				LOGGER.info(siteName+ " SITE IS NOT VISIBLE TO "+siteCreatorName);
				child2.log(LogStatus.INFO,"<font color=blue>"+siteName+ " SITE IS NOT VISIBLE TO "+siteCreatorName+"<font>");
                                
				   
		     	   	ts.takeScreenShotIE(driver,className, screenShotName+"6");
		     	   	child2.log(LogStatus.INFO, "Site is not visible to site creator : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"6"+".png"));
		     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
		            extent.flush();   
				
			}
			else if (Element.isTextPresentInListForSite(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName))
			{	
				LOGGER.info(siteName+ " SITE IS VISIBLE TO "+siteCreatorName);
				child2.log(LogStatus.INFO,"<font color=blue>"+siteName+ " SITE IS VISIBLE TO "+siteCreatorName+"<font>");
                                
				ts.takeScreenShotIE(driver,className, screenShotName+"7");
	     	   	child2.log(LogStatus.INFO, "Site is visible to site creator : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"7"+".png"));
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
                extent.flush(); 
                
                loginPage1.loginAsUser("privateuser", "1qaz@WSX");            
                Element.waitForLoad(driver);
                Thread.sleep(5000);
                
                LOGGER.info("Check whether \"" +siteName+ "\" SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                child2.log(LogStatus.INFO, "Check whether \" " +siteName+ "\" SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                extent.flush(); 
                
                SearchObjects searchSite2 = new SearchObjects(driver);
                searchSite2.searchSite(siteName);
                Element.waitForLoad(driver);       
                
                
                if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
                {
                    Thread.sleep(5000);
                    LOGGER.info("Message display as \"No sites found\"");
                    child2.log(LogStatus.INFO, "Message display as \"No sites found\"");
                    
                    LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER");
                    child2.log(LogStatus.PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER \"privateuser\"");
                    
                    ts.takeScreenShotIE(driver,className, screenShotName+"8");
    	     	   	child2.log(LogStatus.PASS, "User is alredy created : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"8"+".png"));
    	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
    	            extent.flush();  
                    
                }
                else 
                {
                    Thread.sleep(5000);
                    if (Element.isTextPresentInList(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName)) {
                        
                        LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,siteName+ " SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                        child2.log(LogStatus.FAIL,siteName+ " SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                        
                        ts.takeScreenShotIE(driver,className, screenShotName+"9");
        	     	   	child2.log(LogStatus.FAIL, "Site is not visible to site creator : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"9"+".png"));
        	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
        	            extent.flush(); 
                        
                        
                    } else {
                    	
                        LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER");
                        child2.log(LogStatus.PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER \"privateuser\"");
                        
                        ts.takeScreenShotIE(driver,className, screenShotName+"9");
        	     	   	child2.log(LogStatus.PASS, "Site is visible to site creator : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"9"+".png"));
        	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
        	            extent.flush();
                        
                        
                    }
                }            
            
        }
		else
		{	
			LOGGER.info("CREATE PUBLIC SITE");
            child2.log(LogStatus.INFO, "CREATE PUBLIC SITE");
            
			child2.log(LogStatus.INFO, "Click \"Create Site\" ");
			child2.log(LogStatus.INFO, "Enter " + siteName
					+ " in \"site Name Field\" ");
			child2.log(LogStatus.INFO, "Enter " + siteId
					+ " in \"site URL Field\" ");
			child2.log(LogStatus.INFO, "Check \"private\" Option");
			child2.log(LogStatus.INFO, "Click \"OK\" Button ");
			extent.flush();
			
			createObjects.createPublicSite(siteName, siteId, expectedResult);
			Thread.sleep(5000);         
                                                
                        //If Site Created
		if(Element.isElementPresent(driver, By.xpath("//h1[@id='HEADER_TITLE']/a[text()='"+siteName+"']")))
		{	
			LOGGER.info("Site "+siteName +" CREATED ");
			child2.log(LogStatus.INFO, "<font color=blue>Site "+siteName +" CREATED <font> ");
                        
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"10");
     	   	child2.log(LogStatus.INFO, "Public site created : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"10"+".png"));
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
	        	child2.log(LogStatus.INFO, "Current Test Results : " +"<font color=green>" +siteErrorNotification+"<font>");	        	

	        	TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"11");
	     	   	child2.log(LogStatus.INFO, "Public Site already created : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"11"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush(); 
                        
                Button okaybtn=new Button(driver,By.xpath("//button[text()='OK']"));
		        okaybtn.click(); 
		        
		        Thread.sleep(2000);
		        
		        Button okayButton=new Button(driver,By.xpath("//button[text()='Cancel']"));
		        okayButton.click(); 
    		}
    	    //ExpectedResult1!=CurrentResult[PROBLEM]
    	    else 
    	    {
    	    	LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Private Site "+siteName +"IS NOT CREATED ");
                child2.log(LogStatus.FAIL, "Private Site "+siteName +"IS NOT CREATED ");
			
	    	    LOGGER.info("Expected Results : " + expectedResult);
	            child2.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child2.log(LogStatus.INFO, "Current Test Results : " +"<font color=red>" +siteErrorNotification+"<font>");        	
	        	
	        	TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotIE(driver,className, screenShotName+"12");
	     	   	child2.log(LogStatus.INFO, "Public Site not created : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"12"+".png"));
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
                
                extent.flush();
                
                SearchObjects searchSite = new SearchObjects(driver);
                searchSite.searchSite(siteName);
                Thread.sleep(5000);
                
		if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
		{
			LOGGER.info("Message display as \"No sites found\"");
			child2.log(LogStatus.INFO, "Message display as \"No sites found\"");
                        
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"13");
     	   	child2.log(LogStatus.INFO, "Site is not visible in search : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"13"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
			
		}
		else 
		{
			if (Element.isTextPresentInListForSite(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName)) {
				
               LOGGER.info("Site is Display in \"Site Search\"");
               child2.log(LogStatus.PASS, "<font color=green>Site is Display in \"Site Search\"<font>");
                            
               TakeScreenShot ts=new TakeScreenShot();
        	   	ts.takeScreenShotIE(driver,className, screenShotName+"14");
        	   	child2.log(LogStatus.PASS, "Site is visible in search : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"14"+".png"));
        	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
               extent.flush(); 
                            
                        
			} else {
				
                            LOGGER.info("Site IS NOT Display in \"Site Search\"");
                            child2.log(LogStatus.FAIL, "<font color=red>Site is NOT Display in \"Site Search\"<font>");
                            
                            TakeScreenShot ts=new TakeScreenShot();
                    	   	ts.takeScreenShotIE(driver,className, screenShotName+"14");
                    	   	child2.log(LogStatus.FAIL, "Site is not visible in search : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"14"+".png"));
                    	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                            extent.flush(); 
				
				
			}
		}
		}	
		Thread.sleep(3000);
		LoginPage loginpage1=new LoginPage(driver);
		loginpage1.logout();
		LOGGER.info("Test case create Site executed");
		child2.log(LogStatus.INFO, "Test case create Site executed");                      
        extent.flush();
        extent.endTest(child2);   

	
	}
	
	@Parameters({"siteIE", "newSiteNameIE","screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "Edit site in IE",priority = 3)
	public void editSite(String siteName,String newSiteName,String screenShotName) throws InterruptedException, IOException

	{
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Edit siteName from \" "+siteName +" \" to "+newSiteName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		 child3 = extent.startTest("Edit Site","Edit siteName from \" "+siteName +" \" to "+newSiteName);      

				LOGGER.info("Test case Edit Site started executing");
				child3.log(LogStatus.INFO,
						"Test case Edit Site started executing");		

				LOGGER.info("Accessing the Login Page Again");
		                child3.log(LogStatus.INFO, "Accessing the Login Page Again");
		        
				LOGGER.info("Login as admin");
				child3.log(LogStatus.INFO, "Login as admin");
				extent.flush();
				
				LoginPage loginPage = new LoginPage(driver);
				loginPage.loginAsAdmin();
				
		        LOGGER.info("Click \"Sites\"");
				child3.log(LogStatus.INFO, "Click \"Sites\"");
		                
		         LOGGER.info("Click \"Site Finder\"");
				child3.log(LogStatus.INFO, "Click \"Site Finder\"");
		                
		        LOGGER.info("Search sitename");
				child3.log(LogStatus.INFO, "Search sitename");
		                
		        child3.log(LogStatus.INFO, "Click \"Configuration button\" to edit Site");		                
		        child3.log(LogStatus.INFO, "Clicked \"Edit Site Details\"");
		        child3.log(LogStatus.INFO, "Edit the siteName form \""+siteName +"\" to \""+newSiteName+" \"");
		        child3.log(LogStatus.INFO, "click \"OK\" button");
		                
		        extent.flush();
				SiteDashboardPage editSiteName=new SiteDashboardPage(driver);
				editSiteName.editSiteName(siteName, newSiteName);
				
		        LOGGER.info("CHECK WHETHER SiteName IS EDITED OR NOT");
				child3.log(LogStatus.INFO, "CHECK WHETHER SiteName IS EDITED OR NOT");
		                
		         Element.waitForLoad(driver);
				 Thread.sleep(2000);
		                 
		        LOGGER.info("Click \"Sites\"");
				child3.log(LogStatus.INFO, "Click \"Sites\"");
		                
		        LOGGER.info("Click \"Site Finder\"");
				child3.log(LogStatus.INFO, "Click \"Site Finder\"");
		                
		        LOGGER.info("Search sitename");
				child3.log(LogStatus.INFO, "Search sitename");
				extent.flush();
				
		        SearchObjects searchObjects = new SearchObjects(driver);
				searchObjects.searchSite(siteName);
				
				Element.waitForLoad(driver);
				Thread.sleep(3000);

				if(Element.isElementPresent(driver, By.xpath("//tbody//tr//td//div//h3//a[text()='" + siteName+ "']")))
				{
					LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Site " + siteName + " was found,Site name was NOT Changed Successfully");
					child3.log(LogStatus.FAIL, "Site " + siteName + " was found,Site name was NOT Changed Successfully");
					
					TakeScreenShot ts=new TakeScreenShot();
		     	   	ts.takeScreenShotIE(driver,className, screenShotName+"15");
		     	   	child3.log(LogStatus.FAIL, "Site rename is unsucessfull : " +child3.addScreenCapture("./"+className+"/"+screenShotName+"15"+".png"));
		     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
		            extent.flush(); 
				}
				 else 
				 {
					LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Site " + siteName + " is NOT FOUND,Site name was Changed Successfully FROM \""+siteName+"\"TO \""+newSiteName+"\"");
					child3.log(LogStatus.PASS, "Site " + siteName + " is NOT FOUND,Site name was Changed Successfully FROM \""+siteName+"\"TO \""+newSiteName+"\"");
					
					TakeScreenShot ts=new TakeScreenShot();
		     	   	ts.takeScreenShotIE(driver,className, screenShotName+"16");
		     	   	child3.log(LogStatus.PASS, "Site was edited successfull : " +child3.addScreenCapture("./"+className+"/"+screenShotName+"16"+".png"));
		     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
		            extent.flush();
				
				}
			
				Element.waitForLoad(driver);			
				loginPage.logout();
				
				LOGGER.info("Test case Edit Site executed");
				child3.log(LogStatus.INFO, "Test case Edit Site executed");		                       
		        extent.flush();
		        extent.endTest(child3);  

	}
	
	@Parameters({"newSiteNameIE","siteIdIE","screenShotNameIE" })
	@Test(retryAnalyzer=IERetryAnalyzer.class,testName = "Delete site in IE",priority = 4)
	public void deleteSite(String newSiteName,String siteId,String screenShotName) throws InterruptedException, IOException

	{
		 LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Delete site called \" "+newSiteName +" \" ");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		 child4 = extent.startTest("Delete Site","Delete site called \" "+newSiteName +" \" ");
     
		LOGGER.info("Test case Delete Site started executing");
		child4.log(LogStatus.INFO,
				"Test case Delete Site started executing");		

		LOGGER.info("Accessing the Login Page");
                child4.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as admin");
		child4.log(LogStatus.INFO, "Login as admin");
		 extent.flush(); 
		 
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		
		LOGGER.info("Check the site,if site exist Delete the site");
		child4.log(LogStatus.INFO, "Check the site,if site exist Delete the Site");
		
		 
                child4.log(LogStatus.INFO, "Search the site");
                child4.log(LogStatus.INFO, "Click \"Delete\" Button,next to site\""+newSiteName+"\"");
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
                
                extent.flush();
                
                RemoveObjects deleteSite=new RemoveObjects(driver);
		       deleteSite.deleteSite(newSiteName,siteId);		
                
                LOGGER.info("CHECK WHETHER SITE\" "+newSiteName+" \"IS DELETED OR NOT");
                child4.log(LogStatus.INFO,"CHECK WHETHER SITE\" "+newSiteName+" \"IS DELETED OR NOT");
                
                extent.flush();
                
                NavigateToPage navigateTo = new NavigateToPage(driver);		
		navigateTo.goToHome();
                
                LOGGER.error("Click \"My Profile\" Again");
		child4.log(LogStatus.INFO, "Click \"My Profile\" Again");
                
		 extent.flush();
		 
                child4.log(LogStatus.INFO, "Accessing trashcan Page");
		navigateTo.goToUserTrashCan();	
		
		LOGGER.info("Place siteUrl");
		child4.log(LogStatus.INFO, "Place siteUrl");
		
		 extent.flush();
		 
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
 	   	ts.takeScreenShotIE(driver,className, screenShotName+"17");
 	   	child4.log(LogStatus.INFO, "Search Site : " +child4.addScreenCapture("./"+className+"/"+screenShotName+"17"+".png"));
 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
        extent.flush(); 
		//Element.takescreenshot(driver, className, screenShotName+"deleteConfirmation");
		
		//CheckBox checkbox=new CheckBox(driver, By.xpath("//div[@class='name'][text()='"+item+"']/ancestor::td[1]/preceding-sibling::td[2]//input"));
		//checkbox.click();

		LOGGER.info("Check the patucular siteUrl");
		child4.log(LogStatus.INFO, "Check the patucular siteUrl");
		
		if(Element.isElementPresent(driver, By.xpath("//tbody[@class='yui-dt-message']//tr//td//div[text()='No items exist']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,newSiteName+" SITE IS DELETED SUCCESSFULLY");
			child4.log(LogStatus.PASS, newSiteName+" SITE IS DELETED SUCCESSFULLY");
			
			
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"18");
     	   	child4.log(LogStatus.PASS, "Site is Deleted : " +child4.addScreenCapture("./"+className+"/"+screenShotName+"18"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		else if(Element.isElementPresent(driver,By.xpath("//div[text()='"+siteId+"']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_FAIL,newSiteName+" SITE IS NOT DELETED SUCCESSFULLY");
			child4.log(LogStatus.FAIL, newSiteName+" SITE IS NOT DELETED SUCCESSFULLY");
			
			
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
			LOGGER.error(TestCaseProperties.TEXT_TEST_PASS,newSiteName+ "SITE IS NOT DELETED");
			child4.log(LogStatus.FAIL, newSiteName+" SITE IS NOT DELETED");
			
			
     	   	ts.takeScreenShotIE(driver,className, screenShotName+"19");
     	   	child4.log(LogStatus.FAIL, "Site is not deleted : " +child4.addScreenCapture("./"+className+"/"+screenShotName+"19"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		else
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,newSiteName+" SITE IS DELETED SUCCESSFULLY");
			child4.log(LogStatus.PASS, newSiteName+"SITE IS DELETED SUCCESSFULLY");
			
			ts.takeScreenShotIE(driver,className, screenShotName+"20");
     	   	child4.log(LogStatus.PASS, "Site is not deleted : " +child4.addScreenCapture("./"+className+"/"+screenShotName+"20"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		}
		
		}
		Thread.sleep(3000);
		LoginPage loginpage1=new LoginPage(driver);
		loginpage1.logout();
		
		LOGGER.info("Test case Delete Site executed");
		child4.log(LogStatus.INFO, "Test case Delete Site executed");
		 extent.flush();
         extent.endTest(child4);        
               

                		
		
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
			else if(method.getName().equals("editSite")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child3.log(LogStatus.FAIL,"editSite Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child3.log(LogStatus.SKIP, "editSite Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child3.log(LogStatus.PASS, "editSite Test got executed successfully");
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
		
		driver.quit(); 
		
	   }

	@AfterTest(alwaysRun=true)
	public void extent() {
		
		LOGGER.info("Test case closed");
		parent
		  .appendChild(child1)
		  .appendChild(child2)
		  .appendChild(child3)
		  .appendChild(child4);
		extent.endTest(parent);
		extent.close();	
		driver.quit();
		

	} 
}
