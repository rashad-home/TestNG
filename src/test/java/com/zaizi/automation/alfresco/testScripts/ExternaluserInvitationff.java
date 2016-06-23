package com.zaizi.automation.alfresco.testScripts;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

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

import com.zaizi.automation.alfresco.core.elements.Button;
import com.zaizi.automation.alfresco.core.elements.Element;
import com.zaizi.automation.alfresco.core.elements.Span;
import com.zaizi.automation.alfresco.core.elements.TakeScreenShot;
import com.zaizi.automation.alfresco.core.elements.TextField;
import com.zaizi.automation.alfresco.core.info.TestCaseProperties;
import com.zaizi.automation.alfresco.core.pages.CreateObjects;
import com.zaizi.automation.alfresco.core.pages.LoginPage;
import com.zaizi.automation.alfresco.core.pages.NavigateToPage;
import com.zaizi.automation.alfresco.core.pages.RemoveObjects;
import com.zaizi.automation.alfresco.core.pages.SearchObjects;
import com.zaizi.automation.alfresco.core.pages.SiteDashboardPage;
import com.zaizi.automation.extentReports.ExtentManagerFF;
import com.zaizi.automation.listeners.FFRetryAnalyzer;


/**
*
* @author mrashad@zaizi.com
*/



public class ExternaluserInvitationff {

	
	/**
	 * 
	 * Defining log4j
	 */

	public static final Logger LOGGER = LogManager
			.getLogger(ExternaluserInvitationff.class.getName());



	
	/**
	 * 
	 * Defining Report
	 */
	static ExtentReports extent;
	static ExtentTest parent;
	static ExtentTest child1;
	static ExtentTest child2;
	static ExtentTest child3;
    
	/**
	 * 
	 * Define WebDriver
	 */

	private static WebDriver driver;	
	
	
	
	/**
	 * 
	 * Define className
	 */

	public static String className = ExternaluserInvitationff.class
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
		
		extent = ExtentManagerFF.getReporter(TestCaseProperties.REPORT_TEST_PATH_FF+"FFFullReport.html");
		parent=extent.startTest("<b>Send invitation to externalUser Test in Firefox</b>","This is external User invitation Test,<b>Create the site,Send invitation to external user,Delete the site</b>");
		System.out.println("Testcase started");
		

	}
	
	
	@BeforeMethod(alwaysRun=true)
	public static void beforemethod() throws MalformedURLException{
				//Set the DriverType(BrowserName,Platform)
				driver = TestCaseProperties.driverType("Firefox", "WINDOWS");
				
				driver.manage().window().setSize(new Dimension(1920, 1920));
				
				//Get LoginScreen_URl from TestcaseProperties Values
				driver.get(TestCaseProperties.LOGIN_SCREEN_URL);	
	}
	
	
	
	
	/**
	 * 
	 * @Test createSite
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */
	
	@Parameters({"siteFF","siteIdFF","siteCreatorNameFF","expectedResultFF","isPrivateFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "Create user in FF",priority = 1)
	public void createSite(String siteName,String siteId,String siteCreatorName,String expectedResult,
			Boolean isPrivate,String screenShotName) throws InterruptedException, IOException
	{

		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Create Site "+siteName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child1 = extent.startTest("a_createSite","Create site called \" "+siteName +" \",Is it private Site "+isPrivate);		

		LOGGER.info("Test case a_createSite started executing");
		child1.log(LogStatus.INFO,
				"Test case a_createSite started executing");		

		LOGGER.info("Accessing the Login Page");
        child1.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as admin");
		child1.log(LogStatus.INFO, "Login as admin");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		
		CreateObjects createObjects = new CreateObjects(driver);
		
		if (isPrivate)
        {	
            LOGGER.info("CREATE PRIVATE SITE");
             child1.log(LogStatus.INFO, "CREATE PRIVATE SITE");
                
             child1.log(LogStatus.INFO,"Click \"Create Site\" ");
             child1.log(LogStatus.INFO,"Enter "+siteName+" in \"site Name Field\" ");
	     child1.log(LogStatus.INFO,"Enter "+siteId+" in \"site URL Field\" ");	
             child1.log(LogStatus.INFO,"Check \"private\" Option");
             child1.log(LogStatus.INFO,"Click \"OK\" Button "); 
             createObjects.createPrivateSite(siteName, siteId,expectedResult+ "private2");
			
             TakeScreenShot ts=new TakeScreenShot();
      	   	ts.takeScreenShotFF(driver,className, screenShotName+"195");
      	   	child1.log(LogStatus.PASS, "Snapshot below:" +child1.addScreenCapture("./"+className+"/"+screenShotName+"195"+".png"));
      	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
             extent.flush();    
                        
                        //If Site Created
		if(Element.isElementPresent(driver, By.xpath("//div[@class='bd']/span[@class='wait']")))
		{	
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Site "+siteName +" CREATED ");
			child1.log(LogStatus.INFO, "<font color=blue>Site "+siteName +" CREATED <font> ");	
		    	
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
	        	child1.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child1.log(LogStatus.PASS, "Current Test Results : " +"<font color=green>" +siteErrorNotification+"<font>");	        	

                        Button okaybtn=new Button(driver,By.xpath("//button[text()='OK']"));
		        okaybtn.click(); 
    		}
    	    //ExpectedResult1!=CurrentResult[PROBLEM]
    	    else 
    	    {
    	    	LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Private Site "+siteName +"IS NOT CREATED ");
                child1.log(LogStatus.FAIL, "Private Site "+siteName +"IS NOT CREATED ");
			
	    	    LOGGER.info("Expected Results : " + expectedResult);
	            child1.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child1.log(LogStatus.FAIL, "Current Test Results : " +"<font color=red>" +siteErrorNotification+"<font>");        	
	        		
    	    }
		}
             
             Element.waitForLoad(driver);
             LOGGER.info("CHECK WHETHER PRIVATE SITE CREATED OR NOT");
             child1.log(LogStatus.INFO, "CHECK WHETHER PRIVATE SITE CREATED OR NOT");
             
             LOGGER.info("Check whether \" " +siteName+ "\" SITE IS VISIBLE TO "+siteCreatorName);
             child1.log(LogStatus.INFO, "Check whether \" " +siteName+ "\" SITE IS VISIBLE TO "+siteCreatorName);
	     Thread.sleep(5000);

             LOGGER.info("Search the sitename in Site finder");
             child1.log(LogStatus.INFO, "Search the sitename in Site finder");
             
			SearchObjects searchSite = new SearchObjects(driver);			
			searchSite.searchSite(siteName);	
			
			if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
			{
				LOGGER.info("Message display as \"No sites found\"");
				child1.log(LogStatus.INFO, "Message display as \"No sites found\"");
				
				LOGGER.info(siteName+ " SITE IS NOT VISIBLE TO "+siteCreatorName);
				child1.log(LogStatus.FAIL,"<font color=blue>"+siteName+ " SITE IS NOT VISIBLE TO "+siteCreatorName+"<font>");
                                
				TakeScreenShot ts1=new TakeScreenShot();
	     	   	ts1.takeScreenShotFF(driver,className, screenShotName+"196");
	     	   	child1.log(LogStatus.FAIL, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"196"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();    
				
			}
			else if (Element.isTextPresentInListForSite(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName))
			{	
				LOGGER.info(siteName+ " SITE IS VISIBLE TO "+siteCreatorName);
				child1.log(LogStatus.INFO,"<font color=blue>"+siteName+ " SITE IS VISIBLE TO "+siteCreatorName+"<font>");
                                
				TakeScreenShot ts3=new TakeScreenShot();
	     	   	ts3.takeScreenShotFF(driver,className, screenShotName+"197");
	     	   	child1.log(LogStatus.INFO, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"197"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();    
							
			}				
			
		   
		   
                Thread.sleep(5000);                        
                Element.waitForLoad(driver);
                
                LoginPage loginPage1 = new LoginPage(driver);
                loginPage1.logout();        
                Element.waitForLoad(driver);
                
                LOGGER.info("Login as  \"privateuser\" ");
                child1.log(LogStatus.INFO,"Login as \"privateuser\" ");
                
                loginPage1.loginAsUser1("privateuser", "1qaz@WSX");
                Element.waitForLoad(driver);
                Thread.sleep(5000);
                LOGGER.info("Check whether \"" +siteName+ "\" SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                child1.log(LogStatus.INFO, "Check whether \" " +siteName+ "\" SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                
                SearchObjects searchSite2 = new SearchObjects(driver);
                searchSite2.searchSite(siteName);
                Element.waitForLoad(driver);       
                
                
                if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
                {
                    Thread.sleep(5000);
                    LOGGER.info("Message display as \"No sites found\"");
                    child1.log(LogStatus.INFO, "Message display as \"No sites found\"");
                    
                    LOGGER.info(siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER");
                    child1.log(LogStatus.PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER \"privateuser\"");
                    
                    TakeScreenShot ts4=new TakeScreenShot();
             	   	ts4.takeScreenShotFF(driver,className, screenShotName+"198");
             	   	child1.log(LogStatus.INFO, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"198"+".png"));
             	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                    extent.flush();    
                    
                }
                else 
                {
                    Thread.sleep(5000);
                    if (Element.isTextPresentInList(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName)) {
                        
                        LOGGER.info(siteName+ " SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                        child1.log(LogStatus.FAIL,siteName+ " SITE IS VISIBLE TO UNJOIN USER \"privateuser\"");
                        
                        TakeScreenShot ts5=new TakeScreenShot();
                 	   	ts5.takeScreenShotFF(driver,className, screenShotName+"199");
                 	   	child1.log(LogStatus.INFO, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"199"+".png"));
                 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                        extent.flush();    
                        
                        
                    } else {
                        LOGGER.info(siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER");
                        child1.log(LogStatus.PASS,siteName+ " SITE IS NOT VISIBLE TO UNJOIN USER \"privateuser\"");
                        
                        
                        TakeScreenShot ts6=new TakeScreenShot();
                 	   	ts6.takeScreenShotFF(driver,className, screenShotName+"200");
                 	   	child1.log(LogStatus.PASS, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"200"+".png"));
                 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                        extent.flush();    	
                        
                        
                    }
                }            
            
        }
		else
		{	LOGGER.info("CREATE PUBLIC SITE");
        child1.log(LogStatus.INFO, "CREATE PUBLIC SITE");
             
                        child1.log(LogStatus.INFO,"Click \"Create Site\" ");
             child1.log(LogStatus.INFO,"Enter "+siteName+" in \"site Name Field\" ");
	     child1.log(LogStatus.INFO,"Enter "+siteId+" in \"site URL Field\" ");	
             child1.log(LogStatus.INFO,"Check \"private\" Option");
             child1.log(LogStatus.INFO,"Click \"OK\" Button ");
             
			createObjects.createPublicSite(siteName, siteId, expectedResult);
                        
                                                
                        //If Site Created
		if(Element.isElementPresent(driver, By.xpath("//div[@class='bd']/span[@class='wait']")))
		{	
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Site "+siteName +" CREATED ");
			child1.log(LogStatus.INFO, "<font color=blue>Site "+siteName +" CREATED <font> ");
                        
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"201");
     	   	child1.log(LogStatus.INFO, "User is created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"201"+".png"));
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
	        	child1.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child1.log(LogStatus.PASS, "Current Test Results : " +"<font color=green>" +siteErrorNotification+"<font>");	        	

	        	TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotFF(driver,className, screenShotName+"202");
	     	   	child1.log(LogStatus.PASS, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"202"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();    	
                        
                        Button okaybtn=new Button(driver,By.xpath("//button[text()='OK']"));
		        okaybtn.click(); 
    		}
    	    //ExpectedResult1!=CurrentResult[PROBLEM]
    	    else 
    	    {
    	    	LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Private Site "+siteName +"IS NOT CREATED ");
                child1.log(LogStatus.FAIL, "Private Site "+siteName +"IS NOT CREATED ");
			
	    	    LOGGER.info("Expected Results : " + expectedResult);
	            child1.log(LogStatus.INFO, "Expected Results : " + expectedResult);
	        	
	        	LOGGER.info("Current Test Results : "+siteErrorNotification);
	        	child1.log(LogStatus.FAIL, "Current Test Results : " +"<font color=red>" +siteErrorNotification+"<font>");        	
	        	
	        	TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotFF(driver,className, screenShotName+"203");
	     	   	child1.log(LogStatus.FAIL, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"203"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush(); 
    	    }
		}
                LOGGER.info("CHECK WHETHER "+siteName+" IS CREATED OR NOT");
                child1.log(LogStatus.INFO,"CHECK WHETHER "+siteName+" IS CREATED OR NOT");
                
		LOGGER.info("CHECK WHETHER "+siteName+" IS DISPLAY IN SITES LIST");
                child1.log(LogStatus.INFO,"CHECK WHETHER "+siteName+" IS DISPLAY IN SITES LIST");
                
                LOGGER.info("Click \"Sites\"");
		child1.log(LogStatus.INFO, "Click \"Sites\"");
                
                LOGGER.info("Click \"Site Finder\"");
		child1.log(LogStatus.INFO, "Click \"Site Finder\"");
                
                LOGGER.info("Search sitename");
		child1.log(LogStatus.INFO, "Search sitename");
                
		SearchObjects searchSite = new SearchObjects(driver);
		searchSite.searchSite(siteName);
                
		if(Element.isElementPresent(driver,By.xpath("//Span[text()='No sites found']"))) 
		{
			LOGGER.info("Message display as \"No sites found\"");
			child1.log(LogStatus.INFO, "Message display as \"No sites found\"");
                        
			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"204");
     	   	child1.log(LogStatus.INFO, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"204"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
			
		}
		else 
		{
			if (Element.isTextPresentInListForSite(driver.findElements(By.xpath("//tbody//tr//td//div//h3//a[contains(., '" + siteName+ "')]")), siteName)) {
				
                            LOGGER.info("Site is Display in \"Site Search\"");
                            child1.log(LogStatus.PASS, "<font color=green>Site is Display in \"Site Search\"<font>");
                            
                            TakeScreenShot ts=new TakeScreenShot();
                     	   	ts.takeScreenShotFF(driver,className, screenShotName+"205");
                     	   	child1.log(LogStatus.PASS, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"205"+".png"));
                     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                            extent.flush(); 
                            
                        
			} else {
				
                            LOGGER.info("Site IS NOT Display in \"Site Search\"");
                            child1.log(LogStatus.FAIL, "<font color=red>Site is NOT Display in \"Site Search\"<font>");
                            
                            TakeScreenShot ts=new TakeScreenShot();
                     	   	ts.takeScreenShotFF(driver,className, screenShotName+"206");
                     	   	child1.log(LogStatus.FAIL, "Snapshot below: " +child1.addScreenCapture("./"+className+"/"+screenShotName+"206"+".png"));
                     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
                            extent.flush(); 
				
				
			}
		}
		}	
		loginPage.logout();
		LOGGER.info("Test case a_createSite executed");
		child1.log(LogStatus.INFO, "Test case a_createSite executed");
                
                 extent.endTest(child1);        
                extent.flush();

	}
	
	
	
	
	
	/**
	 * 
	 * @Test send SiteInvitation to ExternalUser	 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */
	
	@Parameters({"firstNameFF","lastNameFF","userNameFF","PasswordFF","siteFF","siteIdFF","roleNameFF","emailFF","expectedResultFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "sendSiteInvitationToExternallUser in FF",priority = 2)
    public void sendSiteinvitationToexternallUser(String firstName, String lastName, String userName, String password, String siteName,String siteId,
			String roleName,String email, String expectedResult,String screenShotName) throws InterruptedException, IOException
    {
		
		
		
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING,
				"Check whether Site Invitation sent BY " + firstName);
		
		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child2 = extent.startTest("Send SiteInvitation",
				"Check whether Site Invitation sent BY " + firstName);	
		
		LoginPage loginPage = new LoginPage(driver);

		//Login as "userName" 
		LOGGER.info("Accessing the LoginPage");
		child2.log(LogStatus.INFO,"Accessing the LoginPage");
		
		LOGGER.info("Login As Admin");
		child2.log(LogStatus.INFO,"Login As Admin");
		loginPage.loginAsAdmin();
		Element.waitForLoad(driver);

		LOGGER.info("Test case b_sendExternalUserInvitation started executing");
		child2.log(LogStatus.INFO,
				"Test case b_sendExternalUserInvitation started executing");
		

		LOGGER.info("SEND EXTERNAL SITE INVITATION");
		child2.log(LogStatus.INFO,
				"SEND EXTERNAL SITE INVITATION");
		
		child2.log(LogStatus.INFO, "Click \"Search Finder\"");
		child2.log(LogStatus.INFO, "Search Site \""+siteName+"\"");
		child2.log(LogStatus.INFO, "Navigate to the " +siteName);
		child2.log(LogStatus.INFO, "Click \"Invite Button\"");
		child2.log(LogStatus.INFO, "Enter the firstName");
		child2.log(LogStatus.INFO, "Enter the lastName");
		child2.log(LogStatus.INFO, "Enter the email");
		child2.log(LogStatus.INFO, "Click \"Add\" next to user");
		child2.log(LogStatus.INFO, "Select the role");
		child2.log(LogStatus.INFO, "Click \"Invite\" Button");
		
		SiteDashboardPage inviteExternalUser=new SiteDashboardPage(driver);
		inviteExternalUser.inviteExternalUser(siteName, firstName, lastName, email,roleName);
		loginPage.logout();
		
		LOGGER.info("CHECK WHETHER INVITATION IS SEND OR NOT");
		child2.log(LogStatus.INFO,
				"CHECK WHETHER INVITATION IS SEND OR NOT");
		
		LOGGER.info("Go to Gmail");
		child2.log(LogStatus.INFO, "Go to Gmail");
		
        driver.get("https://accounts.google.com/ServiceLogin?service=mail&passive=true&rm=false&continue=https://mail.google.com/mail/?ui%3D2&ss=1&scc=1&ltmpl=default&ltmplcache=2&emr=1");
        Thread.sleep(4000);
        
        LOGGER.info("Enter email Address");
		child2.log(LogStatus.INFO, "Enter email Address");
        TextField emailField = new TextField(driver, By.xpath("//input[@id='Email']"));
        emailField.clearText();
        emailField.enterText(email);
        
        
        LOGGER.info("Enter email Address");
		child2.log(LogStatus.INFO, "Enter email Address");
        TextField next = new TextField(driver, By.xpath("//input[@id='next']"));
        next.click();
        Thread.sleep(2000);
        
        LOGGER.info("Enter password");
		child2.log(LogStatus.INFO, "Enter password");
        TextField passwordField = new TextField(driver, By.xpath("//input[@id='Passwd']"));
        passwordField.clearText();
        passwordField.enterText(password);
        
        LOGGER.info("Click \"Sign In\"");
		child2.log(LogStatus.INFO, "Click \"Sign In\"");
        TextField signIn = new TextField(driver, By.xpath("//input[@id='signIn']"));
        signIn.click();
        
        
        Thread.sleep(10000);
        Span link = new Span(
                driver,
                By.xpath("//span/b[contains(., 'Alfresco Share: You have been invited to join the Test Site site by admin')]"));
        if (link.getWebElement().isDisplayed())
        {
            LOGGER.info("The invitation has been received!");
            child2.log(LogStatus.PASS, "The invitation has been received!");
            TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"207");
     	   	child2.log(LogStatus.INFO, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"207"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
        }
        else
        {
            LOGGER.info("The invitation was not found!");
            child2.log(LogStatus.FAIL, "The invitation was not found!");
            TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"208");
     	   	child2.log(LogStatus.INFO, "Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"208"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
	        
        }
		
		LOGGER.info("Test case b_sendExternalUserInvitation executed");
		child2.log(LogStatus.INFO,"Test case b_sendExternalUserInvitation executed");
                 extent.endTest(child2);        
                extent.flush();
		
		
		
		
		
    }
	
	
	
	
	/**
	 * 
	 * @Test DeleteSite
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */	 
	
	@Parameters({"siteFF", "siteIdFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "DeleteSite in FF",priority = 4)
    public void deleteSite(String siteName,String siteId,
			String screenShotName) throws InterruptedException, IOException
    {
	
		
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Delete site called \" "+siteName +" \" ");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child3 = extent.startTest("e_deleteSite","Delete site called \" "+siteName +" \" ");
		

		LOGGER.info("Test case e_deleteSite started executing");
		child3.log(LogStatus.INFO,
				"Test case e_deleteSite started executing");		

		LOGGER.info("Accessing the Login Page");
                child3.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as admin");
		child3.log(LogStatus.INFO, "Login as admin");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		
                LOGGER.info("Check the site,if site exist Delet the user");
		child3.log(LogStatus.INFO, "Check the site,if site exist Delete the user");
                child3.log(LogStatus.INFO, "Search the site");
                child3.log(LogStatus.INFO, "Click \"Delete\" Button,next to site\""+siteName+"\"");
                child3.log(LogStatus.INFO, "Click \"Delete\" Button,for delete confirmation");
		child3.log(LogStatus.INFO, "Login as Admin to delete Site in TrashCan");
                child3.log(LogStatus.INFO, "Click \"My Profile\"");
                child3.log(LogStatus.INFO, "Place siteUrl");
                child3.log(LogStatus.INFO, "Click \"Search\" Button");
                child3.log(LogStatus.INFO, "Check the patucular siteUrl");
                child3.log(LogStatus.INFO, "Click \"Selected Item\" Button");
                child3.log(LogStatus.INFO, "Click \"Delete\" Button");
                child3.log(LogStatus.INFO, "Click \"OK\" Button");
                child3.log(LogStatus.INFO, "Click \"OK confirmation\" Button");
                RemoveObjects deleteSite=new RemoveObjects(driver);
		deleteSite.deleteSite(siteName, siteId);		
                
                LOGGER.info("CHECK WHETHER SITE\" "+siteName+" \"IS DELETED OR NOT");
		child3.log(LogStatus.INFO,"CHECK WHETHER SITE\" "+siteName+" \"IS DELETED OR NOT");
                
                NavigateToPage navigateTo = new NavigateToPage(driver);		
		navigateTo.goToHome();
                
                LOGGER.error("Click \"My Profile\" Again");
		child3.log(LogStatus.INFO, "Click \"My Profile\" Again");
                
                child3.log(LogStatus.INFO, "Accessing trashcan Page");
		navigateTo.goToUserTrashCan();	
		
		LOGGER.info("Place siteUrl");
		child3.log(LogStatus.INFO, "Place siteUrl");
		TextField textField = new TextField(
				driver,
				By.id("template_x002e_user-trashcan_x002e_user-trashcan_x0023_default-search-text"));
		textField.clearText();
		textField.enterText(siteId);
		
		LOGGER.info("Click \"Search\" Button");
		child3.log(LogStatus.INFO, "Click \"Search\" Button");
		Button searchButton = new Button(driver,By.xpath("//button[text()='Search']"));
		searchButton.click();
		Thread.sleep(2000);
                
		TakeScreenShot ts=new TakeScreenShot();
 	   	ts.takeScreenShotFF(driver,className, screenShotName+"210");
 	   	child3.log(LogStatus.PASS, "Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"210"+".png"));
 	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
        extent.flush();  
		//Element.takescreenshot(driver, className, screenShotName+"deleteConfirmation");
		
		//CheckBox checkbox=new CheckBox(driver, By.xpath("//div[@class='name'][text()='"+item+"']/ancestor::td[1]/preceding-sibling::td[2]//input"));
		//checkbox.click();

		LOGGER.info("Check the patucular siteUrl");
		child3.log(LogStatus.INFO, "Check the patucular siteUrl");
		
		if(Element.isElementPresent(driver, By.xpath("//tbody[@class='yui-dt-message']//tr//td//div[text()='No items exist']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,siteName+" SITE IS DELETED SUCCESSFULLY");
			child3.log(LogStatus.PASS, siteName+" SITE IS DELETED SUCCESSFULLY");
			
			
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"211");
     	   	child3.log(LogStatus.PASS, "Site is Deleted : " +child3.addScreenCapture("./"+className+"/"+screenShotName+"211"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush(); 
		}
		else if(Element.isElementPresent(driver,By.xpath("//div[text()='"+siteId+"']")))
		{
			LOGGER.info(TestCaseProperties.TEXT_TEST_FAIL,siteName+" SITE IS NOT DELETED SUCCESSFULLY");
			child3.log(LogStatus.FAIL, siteName+" SITE IS NOT DELETED SUCCESSFULLY");
			
			
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"A18");
     	   	child3.log(LogStatus.FAIL, "Site is NOT Deleted : " +child2.addScreenCapture("./"+className+"/"+screenShotName+"A18"+".png"));
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
			child3.log(LogStatus.FAIL, siteName+" SITE IS NOT DELETED");
			TakeScreenShot ts2=new TakeScreenShot();
     	   	ts2.takeScreenShotFF(driver,className, screenShotName+"212");
     	   	child3.log(LogStatus.FAIL, "Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"212"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
		}
		else
		{
			LOGGER.info(siteName+" SITE IS DELETED SUCCESSFULLY");
			child3.log(LogStatus.PASS, siteName+"SITE IS DELETED SUCCESSFULLY");
			TakeScreenShot ts3=new TakeScreenShot();
     	   	ts3.takeScreenShotFF(driver,className, screenShotName+"213");
     	   	child3.log(LogStatus.PASS, "Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"213"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();  
		}
		}
		
		}
		
		
		loginPage.logout();
		
		LOGGER.info("Test case e_deleteSite executed");
		child3.log(LogStatus.INFO, "Test case e_deleteSite executed");
                
                extent.endTest(child3);        
                extent.flush();
		
    }
	
   
	@AfterMethod
	   public void aftermethod(Method method,ITestResult result) throws Exception{
		
		if(method.getName().equals("createSite")) {			
			
			if (result.getStatus() == ITestResult.FAILURE) {
		        child1.log(LogStatus.FAIL,"createSite Test failed because "+ result.getThrowable());
		        extent.flush();
		    } else if (result.getStatus() == ITestResult.SKIP) {
		    	child1.log(LogStatus.SKIP, "createSite Test skipped because " + result.getThrowable());
		        extent.flush();
		    } else {
		    	child1.log(LogStatus.PASS, "createSite Test got executed successfully");
		        extent.flush();
		    }
			}
			else if(method.getName().equals("sendSiteinvitationToexternallUser")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child2.log(LogStatus.FAIL,"sendSiteinvitationToexternallUser Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child2.log(LogStatus.SKIP, "sendSiteinvitationToexternallUser Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child2.log(LogStatus.PASS, "sendSiteinvitationToexternallUser Test got executed successfully");
			        extent.flush();
			    }
				}
			else if(method.getName().equals("deleteSite")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child3.log(LogStatus.FAIL,"deleteSite Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child3.log(LogStatus.SKIP, "deleteSite Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child3.log(LogStatus.PASS, "deleteSite Test got executed successfully");
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
		  .appendChild(child3);
		extent.endTest(parent);
		extent.close();	
		driver.quit();
		//driver.close();

	} 
	
	
}
