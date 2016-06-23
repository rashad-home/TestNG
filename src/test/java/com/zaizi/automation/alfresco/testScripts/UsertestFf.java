package com.zaizi.automation.alfresco.testScripts;


import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
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
import com.zaizi.automation.alfresco.core.info.TestCaseProperties;
import com.zaizi.automation.alfresco.core.pages.AdminConsolePage;
import com.zaizi.automation.alfresco.core.pages.LoginPage;
import com.zaizi.automation.alfresco.core.pages.UserDashboardPage;
import com.zaizi.automation.extentReports.ExtentManagerFF;
import com.zaizi.automation.listeners.FFRetryAnalyzer;

public class UsertestFf {

	/**
	 * 
	 * Defining log4j
	 */

	public static final Logger LOGGER = LogManager
			.getLogger(UsertestFf.class.getName());
	
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

	public static String className = UsertestFf.class
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
		parent=extent.startTest("<b>User Test in FF</b>","This is user Test,<b>Create the user,Edit the first Name,Delete the user</b>");
		LOGGER.info("Testcases Started");
		

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
	 * @Test createUser	 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */

	@Parameters({"firstNameFF", "lastNameFF","emailFF","userNameFF","PasswordFF","fullNameFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "Create user in FF",priority = 1)
	public void createUser(String firstName,String lastName,String email,String userName,String password,String fullName,String screenShotName) throws InterruptedException, IOException

	{

		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Create User in FF "+userName);

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
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"1");
     	   	child1.log(LogStatus.INFO, "User is alredy created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"1"+".png"));
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
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"2");
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
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"3");
     	   	child1.log(LogStatus.PASS, "User is created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"3"+".png"));
     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
            extent.flush();    	

 		} else {
 			LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"User is NOT Sucessfully Created");
 			child1.log(LogStatus.FAIL, "<font color=RED>User is NOT Sucessfully Created<font>");
 			
 			TakeScreenShot ts=new TakeScreenShot();
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"3");
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
     	   	ts.takeScreenShotFF(driver,className, screenShotName+"4");
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
	 * @Test editUser	 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */

	@Parameters({"userNameFF","PasswordFF","newFirstNameFF","lastNameFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "Edit User in FF",priority = 2)
	public void editUser(String userName,String password,String newFirstName,String lastName,String screenShotName) throws InterruptedException, IOException

	{

		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Edit User Details "+userName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		 child2 = extent.startTest("Edit User","Edit User Details "+userName);    

		LOGGER.info("Test case Edit User started executing");
		child2.log(LogStatus.INFO,"Test case Edit User started executing");		

		LOGGER.info("Accessing the Login Page");
        child2.log(LogStatus.INFO, "Accessing the Login Page");	
        extent.flush();
        
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsUser(userName, password);
		
        child2.log(LogStatus.INFO, "Click \"My Profile\"");
        child2.log(LogStatus.INFO, "Click \"Edit Profile\"");
        child2.log(LogStatus.INFO, "Change the User FirstName");
		child2.log(LogStatus.INFO, "Click \"Save\" to save the changes");
		extent.flush();
		
        UserDashboardPage editUser=new UserDashboardPage(driver);
		editUser.editUserFirstName(newFirstName);
		
		   TakeScreenShot ts=new TakeScreenShot();
		   ts.takeScreenShotFF(driver,className, screenShotName+"5");
		   child2.log(LogStatus.INFO, "Edited profile below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"5"+".png"));
	       extent.flush();
                
	     Thread.sleep(3000);      
        loginPage.logout();
		
		LOGGER.info("Accessing the Login Page Again");
        child2.log(LogStatus.INFO, "Accessing the Login Page Again");
        extent.flush();
        
		loginPage.loginAsUser(userName, password);
                
                LOGGER.info("Check whether User FirstName/LastName is Edited or Not");
                child2.log(LogStatus.INFO, "Check whether User FirstName/LastName is Edited or Not");
                
			Element element = new Element(driver,By.id("HEADER_USER_MENU_POPUP_text"));	
			extent.flush();
			
		String name;
		
		if(lastName.isEmpty())
		{
			name = newFirstName;
		}
		else
		{
		name = newFirstName + " " + lastName;
		}
		
		if (Element.isTextPresentInElement(element, name)) {
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"User firstName is successfully edited");
			child2.log(LogStatus.PASS, "User firstName is successfully edited");
			
			   ts.takeScreenShotFF(driver,className, screenShotName+"6");
			   child2.log(LogStatus.INFO, "Login Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"6"+".png"));
		       extent.flush();
			

		} else {
			LOGGER.info(TestCaseProperties.TEXT_TEST_FAIL,"User firstName IS NOT successfully edited");
			child2.log(LogStatus.FAIL, "User firstName IS NOT successfully edited");
			
			 	ts.takeScreenShotFF(driver,className, screenShotName+"7");
			   child2.log(LogStatus.INFO, "Login Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"7"+".png"));
		       extent.flush();

		}
		Thread.sleep(3000);
		loginPage.logout();
		
		LOGGER.info("Test case Edit User executed");
		child2.log(LogStatus.INFO, "Test case Edit User executed");                   
        extent.flush();
        extent.endTest(child2);      

	}
	
	/**
     * deleteUser test case
     * 
     * @throws InterruptedException
	 * @throws IOException 
     */
	@Parameters({"userNameFF","newFirstNameFF", "lastNameFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "Delete User in FF",priority = 3)
    public void deleteUser(String userName,String newFirstName,String lastName,String screenShotName) throws InterruptedException, IOException
    {
    	LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Delete User called \" "+userName+ " \"");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		 child3 = extent.startTest("Delete User","Delete User called \" "+userName+" \"");
		
		LOGGER.info("Test case Delete User started executing");
		child3.log(LogStatus.INFO,
				"Test case Delete User started executing");		

		LOGGER.info("Accessing the Login Page Again");
        child3.log(LogStatus.INFO, "Accessing the Login Page Again");
        
		LOGGER.info("Login as admin");
		child3.log(LogStatus.INFO, "Login as admin");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		
		LOGGER.info("CHECK WHETHER USER EXIST TO DELETE");
		child3.log(LogStatus.INFO, "CHECK WHETHER USER EXIST TO DELETE");
		
		child3.log(LogStatus.INFO, "Accessing HomePage");
		child3.log(LogStatus.INFO, "Accessing Admintool page \"Users\"");
		child3.log(LogStatus.INFO, "Accessing UserProfilePage");
		child3.log(LogStatus.INFO, "Search User");
		child3.log(LogStatus.INFO, "Enter the username "+userName);	
		child3.log(LogStatus.INFO, "Click \"Search\" Button");
		
	    extent.flush();
		AdminConsolePage adminConsolePage4 = new AdminConsolePage(driver);		
		if (adminConsolePage4.checkUserPresence(userName)) {
			
			LOGGER.info("User "+userName+" is exist");
			child3.log(LogStatus.INFO, "<font color=blue>User "+userName+" is exist <font>");
			
			TakeScreenShot ts=new TakeScreenShot();
			   ts.takeScreenShotFF(driver,className, screenShotName+"8");
			   child3.log(LogStatus.INFO, "Login Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"8"+".png"));
		       extent.flush(); 
	            
			child3.log(LogStatus.INFO, "Accessing UserProfilePage Again");
			child3.log(LogStatus.INFO, "Enter the username "+userName+" to search");
			child3.log(LogStatus.INFO, "Click \"search\" button");
			child3.log(LogStatus.INFO, "Click user to go inside the userprofile");		
			child3.log(LogStatus.INFO, "Click \"Delete User\" Button");
			child3.log(LogStatus.INFO, "Click \"Delete Confirmation\" Button");
			
			AdminConsolePage deleteUser=new AdminConsolePage(driver);                
			deleteUser.deleteTestUser(newFirstName,lastName,userName);
			extent.flush(); 
			
		}
		else
		{
			child3.log(LogStatus.INFO, "User : " + newFirstName
					+ " Not Available in the System to Delete");
			TakeScreenShot ts=new TakeScreenShot();
			   ts.takeScreenShotFF(driver,className, screenShotName+"9");
			   child3.log(LogStatus.INFO, "Login Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"9"+".png"));
		       extent.flush(); 
		}
		
		
        LOGGER.info("CHECK WHETHER USER IS DELETED OR NOT");
		child3.log(LogStatus.INFO, "CHECK WHETHER USER IS DELETED OR NOT");
           
		child3.log(LogStatus.INFO, "Accessing HomePage");
		child3.log(LogStatus.INFO, "Accessing Admintool page \"Users\"");
		child3.log(LogStatus.INFO, "Accessing UserProfilePage");
		child3.log(LogStatus.INFO, "Search User");
		child3.log(LogStatus.INFO, "Enter the username "+userName);	
		child3.log(LogStatus.INFO, "Click \"Search\" Button");
		extent.flush();
		
		AdminConsolePage adminConsolePage3 = new AdminConsolePage(driver);
		if(adminConsolePage3.checkUserPresence(userName))
			{
				LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"User "+newFirstName+" IS NOT DELETED");
		    	child3.log(LogStatus.FAIL, "<font color=red>User "+newFirstName+" IS NOT DELETED<font>");
		    	
		    	TakeScreenShot ts=new TakeScreenShot();
				   ts.takeScreenShotFF(driver,className, screenShotName+"10");
				   child3.log(LogStatus.INFO, "Login Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"10"+".png"));
			       extent.flush();   
		    	
			}
			
			else
			{
				LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"User "+newFirstName+" IS DELETED SUCCESSFULLY DELETED");
		        child3.log(LogStatus.PASS, "<font color=green>User "+newFirstName+" IS SUCCESSFULLY DELETED<font>");
		        
		        TakeScreenShot ts=new TakeScreenShot();
				   ts.takeScreenShotFF(driver,className, screenShotName+"11");
				   child3.log(LogStatus.INFO, "Login Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"11"+".png"));
			       extent.flush(); 
		       
			}
		Thread.sleep(3000);
		loginPage.logout();
		
		LOGGER.info("Test case deleteUser executed");
		child3.log(LogStatus.INFO, "Test case deleteUser executed");                       
                extent.flush();
                extent.endTest(child3);
		
		
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
			else if(method.getName().equals("editUser")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child2.log(LogStatus.FAIL,"editUser Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child2.log(LogStatus.SKIP, "editUser Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child2.log(LogStatus.PASS, "editUser Test got executed successfully");
			        extent.flush();
			    }
				}
			else if(method.getName().equals("deleteUser")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child3.log(LogStatus.FAIL,"deleteUser Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child3.log(LogStatus.SKIP, "deleteUser Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child3.log(LogStatus.PASS, "deleteUser Test got executed successfully");
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
		

	} 
}
