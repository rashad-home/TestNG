package com.zaizi.automation.alfresco.testScripts;

import java.io.IOException;
import java.lang.reflect.Method;

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
import com.zaizi.automation.alfresco.core.info.TestCaseProperties;
import com.zaizi.automation.alfresco.core.pages.AdminConsolePage;
import com.zaizi.automation.alfresco.core.pages.LoginPage;
import com.zaizi.automation.alfresco.core.pages.NavigateToPage;
import com.zaizi.automation.extentReports.ExtentManagerFF;
import com.zaizi.automation.listeners.FFRetryAnalyzer;
import com.zaizi.automation.testng.core.elements.Button;
import com.zaizi.automation.testng.core.elements.Element;
import com.zaizi.automation.testng.core.elements.TakeScreenShot;
import com.zaizi.automation.testng.core.elements.TextField;

public class UsergrouptestFf {

	
	/**
	 * 
	 * Defining log4j
	 */

	public static final Logger LOGGER = LogManager
			.getLogger(UsergrouptestFf.class.getName());
	
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

	public static String className = UsergrouptestFf.class
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
	public static void beforeClass() throws Exception

	{
		
		extent = ExtentManagerFF.getReporter(TestCaseProperties.REPORT_TEST_PATH_FF+"FFFullReport.html");
		parent=extent.startTest("<b>User Group Test in FF</b>","This is group Test,<b>Create the group,Edit the Group Name,Delete the group</b>");
		LOGGER.info("Testcases Started");		
		
		

	}
	
	@BeforeMethod(alwaysRun=true)
	public static void beforemethod() throws Exception{
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

	@Parameters({"groupNameFF", "groupIdFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "Create Group in FF",priority = 1)
	public void createGroup(String groupName,String groupId,String screenShotName) throws Exception

	{
		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Create Group called\" "+groupName+" \"");

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child1 = extent.startTest("Create Group","Create Group called\" "+groupName+" \"");                
	
				LOGGER.info("Test case create Group started executing");
				child1.log(LogStatus.INFO,
						"Test case create Group started executing");		

				LOGGER.info("Accessing the Login Page");
		                child1.log(LogStatus.INFO, "Accessing the Login Page");
		        
				LOGGER.info("Login as ADMIN");
				child1.log(LogStatus.INFO, "Login as ADMIN ");
				extent.flush(); 
				
				
				LoginPage loginPage = new LoginPage(driver);
				loginPage.loginAsAdmin();
				Thread.sleep(5000);
				
				LOGGER.info("Create Group");
				child1.log(LogStatus.INFO, "Create Group ");
				child1.log(LogStatus.INFO, "Navigate to \"Groups\" in Admin tools");
				child1.log(LogStatus.INFO, "Click \"Browse\" Button");
				child1.log(LogStatus.INFO, "Click \"+\" ,to create Group");
				child1.log(LogStatus.INFO, "Enter the groupID as "+groupId);
				child1.log(LogStatus.INFO, "Enter the group name as "+groupName);
				child1.log(LogStatus.INFO, "Click \"Create\" Button");
				extent.flush(); 
				 
				AdminConsolePage createGroup=new AdminConsolePage(driver);
				createGroup.createGroup(groupName,groupId);
			
				TakeScreenShot ts=new TakeScreenShot();
	     	   	ts.takeScreenShotFF(driver,className, screenShotName+"1");
	     	   	child1.log(LogStatus.INFO, "Create Group : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"1"+".png"));     		
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();   
				
	            if(Element.isElementPresent(driver, By.xpath("//div[@id='prompt']//div[@class='bd']")))
	            {
	            	LOGGER.info("Group "+groupName +" is already exist");
					child1.log(LogStatus.INFO, "Group "+groupName +" is already exist");
					
					
		     	   	ts.takeScreenShotFF(driver,className, screenShotName+"1T");
		     	   	child1.log(LogStatus.INFO, "Create Group : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"1T"+".png"));     		
		     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
		            extent.flush();
		            
		            Button cancelBtn=new Button(driver, By.xpath("//div[@id='prompt']//div[3]//button[text()='Cancel']"));
		            cancelBtn.click();
		            
	            }
	            
	            else 
	            {
	            	LOGGER.info("Group "+groupName +" is NOT already exist");
					child1.log(LogStatus.INFO, "Group "+groupName +" is NOT already exist");
					 extent.flush();
	            }
	            
	            LOGGER.info("CHECK WHETHER GROUP IS SUCESSFULLY CREATED OR NOT");
				child1.log(LogStatus.INFO, "CHECK WHETHER GROUP IS SUCESSFULLY CREATED OR NOT");
				
				child1.log(LogStatus.INFO, "Navigate to \"Groups\" in Admin tools");
				LOGGER.info("Navigate to \"Groups\" in Admin tools");
				extent.flush(); 
				 
				NavigateToPage navigateToPage = new NavigateToPage(driver);
				navigateToPage.goToGroups();
				Element.waitForLoad(driver);
				
				
				LOGGER.info("Enter GroupName to search");
				child1.log(LogStatus.INFO, "Enter GroupName to search");
				extent.flush(); 
				 
				TextField groupSearchBar = new TextField(
						driver,
						By.id("page_x002e_ctool_x002e_admin-console_x0023_default-search-text"));
				groupSearchBar.clearText();
				groupSearchBar.enterText(groupName);
				 
				ts.takeScreenShotFF(driver,className, screenShotName+"2");
	     	   	child1.log(LogStatus.INFO, "Group search : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"2"+".png"));
	     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
	            extent.flush();  
				
				LOGGER.info("Click \"Search\" Button");
				child1.log(LogStatus.INFO, "Click \"Search\" Button");
				extent.flush();
				 
				Button searchButton = new Button(
						driver,
						By.id("page_x002e_ctool_x002e_admin-console_x0023_default-search-button-button"));
				searchButton.click();
				Thread.sleep(5000);
				
				 
				
				if (Element.isTextPresentInListForGroup(
						driver.findElements(By
								.xpath("//tbody//tr//td//div[contains(., '" + groupName
										+ "')]")), groupName)) {
					
					LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Group "+groupName+" IS SUCCESSFULLY CREATED");
					child1.log(LogStatus.PASS, "Group "+groupName+" IS SUCCESSFULLY CREATED");
					
					ts.takeScreenShotFF(driver,className, screenShotName+"3");
		     	   	child1.log(LogStatus.PASS, "Group is created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"3"+".png"));
		     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
		            extent.flush();  
					
				}

				else {
					
					LOGGER.info(TestCaseProperties.TEXT_TEST_FAIL,"Group "+groupName+" IS NOT SUCCESSFULLY CREATED");
					child1.log(LogStatus.FAIL, "Group "+groupName+" IS NOT SUCCESSFULLY CREATED");
					
					ts.takeScreenShotFF(driver,className, screenShotName+"3");
		     	   	child1.log(LogStatus.FAIL, "Group is NOT created : " +child1.addScreenCapture("./"+className+"/"+screenShotName+"3"+".png"));
		     	   	LOGGER.info("Screenshot Taken Successfully!!!!");  
		            extent.flush();  

				}
				Thread.sleep(3000);
				loginPage.logout();
				LOGGER.info("Test case Create Group executed");
				child1.log(LogStatus.INFO, "Test case Create Group executed");
				extent.flush();
		        extent.endTest(child1);        
		               

	}
	
	/**
	 * 
	 * @Test editGroup 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */
	
	@Parameters({"groupNameFF", "newGroupNameFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "Edit Group in FF",priority = 2)
	public void editGroup(String groupName,String newGroupName,String screenShotName) throws InterruptedException, IOException

	{

		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Edit Group from\" "+groupName+" \" to group "+newGroupName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child2 = extent.startTest("Edit Group","Edit Group from\" "+groupName+" \" to group "+newGroupName);           
		
		LOGGER.info("Test case Edit Group started executing");
		child2.log(LogStatus.INFO,
				"Test case Edit Group started executing");		

		LOGGER.info("Accessing the Login Page");
        child2.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as ADMIN");
		child2.log(LogStatus.INFO, "Login as ADMIN ");
		 extent.flush();
		 
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		Thread.sleep(5000);
		
		LOGGER.info("EDIT THE GROUP NAME");
		child2.log(LogStatus.INFO, "EDIT THE GROUP NAME");
		extent.flush();
		 
		LOGGER.info("Accessing AdminToolPage");
        child2.log(LogStatus.INFO, "Accessing AdmintoolPage");
		child2.log(LogStatus.INFO, "Navigate to \"Groups\" in admintools");
		child2.log(LogStatus.INFO, "Check whether particular group is Exist or not");
		child2.log(LogStatus.INFO, "Edit  GroupName from \""+groupName+"\" to \""+newGroupName+"\"");
		child2.log(LogStatus.INFO, "Click \"Save\" button");
		extent.flush();
		 
		AdminConsolePage editGroup=new AdminConsolePage(driver);
		editGroup.editGroup(groupName,newGroupName);
		
		TakeScreenShot ts=new TakeScreenShot();
		ts.takeScreenShotFF(driver,className, screenShotName+"4");
		child2.log(LogStatus.INFO, "Login Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"4"+".png"));
	    extent.flush(); 
        
        LOGGER.info("CHECK WHETHER GROUPNAME IS SUCCESSFULLY EDITED FROM \""+groupName+"\" to \""+newGroupName+"\"");
		child2.log(LogStatus.INFO, "CHECK WHETHER GROUPNAME IS SUCCESSFULLY EDITED FROM \""+groupName+"\" to \""+newGroupName+"\"");
		
		child2.log(LogStatus.INFO, "Navigate to \"Groups\" in admintools AGAIN");
		extent.flush();
		 
		NavigateToPage navigateToPage = new NavigateToPage(driver);
		navigateToPage.goToGroups();
		Element.waitForLoad(driver);
		
		LOGGER.info("Enter GroupName to search");
		child2.log(LogStatus.INFO, "Enter GroupName to search");
		extent.flush();
		 
		TextField groupSearchBar = new TextField(
				driver,
				By.id("page_x002e_ctool_x002e_admin-console_x0023_default-search-text"));
		groupSearchBar.clearText();
		groupSearchBar.enterText(newGroupName);		 
		
		   ts.takeScreenShotFF(driver,className, screenShotName+"5");
		   child2.log(LogStatus.INFO, "Login Snapshot below: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"5"+".png"));
	       extent.flush(); 
		
		LOGGER.info("Click \"Search\" Button");
		child2.log(LogStatus.INFO, "Click \"Search\" Button");
		extent.flush(); 
		
		Button searchButton = new Button(
				driver,
				By.id("page_x002e_ctool_x002e_admin-console_x0023_default-search-button-button"));
		searchButton.click();
		Thread.sleep(5000);
		 
		
		if (Element.isTextPresentInListForGroup(
				driver.findElements(By
						.xpath("//tbody//tr//td//div[contains(., '" + newGroupName
								+ "')]")), newGroupName)) {		
			
			
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Group "+groupName+" IS SUCCESSFULLY EDITED as Group "+newGroupName);
			child2.log(LogStatus.PASS, "Group "+groupName+"  IS SUCCESSFULLY EDITED as Group "+newGroupName);	
			
			ts.takeScreenShotFF(driver,className, screenShotName+"6");
			   child2.log(LogStatus.PASS, "Group successfully edited: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"6"+".png"));
		       extent.flush();
		}

		else {
			LOGGER.info(TestCaseProperties.TEXT_TEST_FAIL,"Group "+groupName+" IS NOT SUCCESSFULLY EDITED as Group "+newGroupName);
			child2.log(LogStatus.FAIL, "Group "+groupName+" IS NOT SUCCESSFULLY EDITED as Group "+newGroupName);
			
				ts.takeScreenShotFF(driver,className, screenShotName+"7");
			   child2.log(LogStatus.FAIL, "Group is not successfully edited: " +child2.addScreenCapture("./"+className+"/"+screenShotName+"7"+".png"));
		       extent.flush();
		}
		Thread.sleep(3000);
		loginPage.logout();
		
		LOGGER.info("Test case Edit Group executed");
		child2.log(LogStatus.INFO, "Test case Edit Group executed");                       
        extent.flush();
        extent.endTest(child2); 

	}
	
	/**
	 * 
	 * @Test removeGroup 
	 * @return
	 * 
	 * @throws Exception InterruptedException, IOException
	 */
	
	@Parameters({"newGroupNameFF","screenShotNameFF" })
	@Test(retryAnalyzer=FFRetryAnalyzer.class,testName = "Remove Group in FF",priority = 3)
	public void removeGroup(String newGroupName,String screenShotName) throws InterruptedException, IOException

	{

		LOGGER.info(TestCaseProperties.TEXT_TEST_EXECUTING, "Remove Group"+newGroupName);

		//Extent Report Start Configuration(testCaseName,Definition of testCase)
		child3 = extent.startTest("Remove Group","Remove Group"+newGroupName);
		
		
		LOGGER.info("Test case Remove Group started executing");
		child3.log(LogStatus.INFO,
				"Test case Remove Group started executing");		

		LOGGER.info("Accessing the Login Page");
                child3.log(LogStatus.INFO, "Accessing the Login Page");
        
		LOGGER.info("Login as ADMIN");
		child3.log(LogStatus.INFO, "Login as ADMIN ");
		extent.flush(); 
		
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginAsAdmin();
		Thread.sleep(5000);
		
		LOGGER.info("REMOVE THE GROUP");
		child3.log(LogStatus.INFO, "REMOVE THE GROUP");
		extent.flush(); 
		
		child3.log(LogStatus.INFO, "Accessing AdmintoolPage");
		child3.log(LogStatus.INFO, "Navigate to \"Groups\" in admintoolpage");
		child3.log(LogStatus.INFO, "Search the groupname");
		child3.log(LogStatus.INFO, "Check whether particular group is Exist or not");
		child3.log(LogStatus.INFO, "Remove  Group\""+newGroupName+"\"");
		child3.log(LogStatus.INFO, "Click \"Delete\" Button");
		extent.flush(); 
		
		AdminConsolePage removeGroup=new AdminConsolePage(driver);
		removeGroup.removeGroup(newGroupName);
		
		   TakeScreenShot ts=new TakeScreenShot();
		   ts.takeScreenShotFF(driver,className, screenShotName+"8");
		   child3.log(LogStatus.INFO, "Remove Group Snapshot below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"8"+".png"));
	       extent.flush(); 
                
        LOGGER.info("CHECK WHTHER GROUP "+newGroupName+"IS SUCESSFULLY DELETED OR NOT");
		child3.log(LogStatus.INFO, "CHECK WHTHER GROUP "+newGroupName+" IS SUCESSFULLY DELETED OR NOT");
		
		child3.log(LogStatus.INFO, "Accessing AdmintoolPage");
		child3.log(LogStatus.INFO, "Navigate to \"Groups\" in admintoolpage");
		extent.flush(); 
		
		NavigateToPage navigateToPage = new NavigateToPage(driver);
		navigateToPage.goToGroups();
		Element.waitForLoad(driver);
		
		LOGGER.info("Enter GroupName to search");
		child3.log(LogStatus.INFO, "Enter GroupName to search");
		extent.flush(); 
		
		TextField groupSearchBar = new TextField(
				driver,
				By.id("page_x002e_ctool_x002e_admin-console_x0023_default-search-text"));
		groupSearchBar.clearText();
		groupSearchBar.enterText(newGroupName);
		 
		
		   ts.takeScreenShotFF(driver,className, screenShotName+"9");
		   child3.log(LogStatus.INFO, "Group Search below: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"9"+".png"));
	       extent.flush(); 
		
		LOGGER.info("Click \"Search\" Button");
		child3.log(LogStatus.INFO, "Click \"Search\" Button");
		 extent.flush();
		 
		Button searchButton = new Button(
				driver,
				By.id("page_x002e_ctool_x002e_admin-console_x0023_default-search-button-button"));
		searchButton.click();
		Thread.sleep(5000);
		 
		
		if (Element.isTextPresentInListForGroup(
				driver.findElements(By
						.xpath("//tbody//tr//td//div[contains(., '" + newGroupName
								+ "')]")), newGroupName)) {
			
			LOGGER.error(TestCaseProperties.TEXT_TEST_FAIL,"Group "+newGroupName+" IS NOT SUCCESSFULLY Removed");
			child3.log(LogStatus.FAIL, "Group "+newGroupName+" IS NOT SUCCESSFULLY Removed");	
			
			
			   ts.takeScreenShotFF(driver,className, screenShotName+"10");
			   child3.log(LogStatus.FAIL, "Group is not successfully DELETE: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"10"+".png"));
		       extent.flush(); 
		       
			
		}

		else {
			
			LOGGER.info(TestCaseProperties.TEXT_TEST_PASS,"Group "+newGroupName+" IS SUCCESSFULLY Removed");
			child3.log(LogStatus.PASS, "Group "+newGroupName+" IS SUCCESSFULLY Removed");
			
			
			   ts.takeScreenShotFF(driver,className, screenShotName+"11");
			   child3.log(LogStatus.PASS, "Group is successfully removed: " +child3.addScreenCapture("./"+className+"/"+screenShotName+"11"+".png"));
		       extent.flush(); 
			
		}
				Thread.sleep(3000);
                loginPage.logout();
		
		
		LOGGER.info("Test case Remove Group executed");
		child3.log(LogStatus.INFO, "Test case Remove Group executed");                    
                extent.flush();
                extent.endTest(child3);    
	}
	
	
	
	@AfterMethod
	   public void aftermethod(Method method,ITestResult result) throws Exception{
		
		if(method.getName().equals("createGroup")) {			
			
			if (result.getStatus() == ITestResult.FAILURE) {
		        child1.log(LogStatus.FAIL,"CreateGroup Test failed because "+ result.getThrowable());
		        extent.flush();
		    } else if (result.getStatus() == ITestResult.SKIP) {
		    	child1.log(LogStatus.SKIP, "CreateGroup Test skipped because " + result.getThrowable());
		        extent.flush();
		    } else {
		    	child1.log(LogStatus.PASS, "CreateGroup Test got executed successfully");
		        extent.flush();
		    }
			}
			else if(method.getName().equals("editGroup")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child2.log(LogStatus.FAIL,"EditGroup Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child2.log(LogStatus.SKIP, "EditGroup Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child2.log(LogStatus.PASS, "EditGroup Test got executed successfully");
			        extent.flush();
			    }
				}
			else if(method.getName().equals("removeGroup")) {			
				
				if (result.getStatus() == ITestResult.FAILURE) {
			        child3.log(LogStatus.FAIL,"removeGroup Test failed because "+ result.getThrowable());
			        extent.flush();
			    } else if (result.getStatus() == ITestResult.SKIP) {
			    	child3.log(LogStatus.SKIP, "removeGroup Test skipped because " + result.getThrowable());
			        extent.flush();
			    } else {
			    	child3.log(LogStatus.PASS, "removeGroup Test got executed successfully");
			        extent.flush();
			    }
				}
		
		driver.quit(); 
		
	   }
		

	@AfterTest(alwaysRun=true)
	public void extent() throws Exception {
		
		
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
