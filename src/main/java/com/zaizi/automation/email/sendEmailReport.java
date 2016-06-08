package com.zaizi.automation.email;

import java.io.File;

import org.testng.annotations.AfterSuite;

import com.zaizi.automation.alfresco.core.info.TestCaseProperties;

public class sendEmailReport {
	
	@AfterSuite
	public void aftersuite()
	{
		AppZip appZip = new AppZip();
		appZip.generateFileList(new File(TestCaseProperties.SOURCE_FOLDER));
		appZip.zipIt(TestCaseProperties.OUTPUT_ZIP_FILE);
	
		emailReport a=new emailReport();
		a.sendEmail2();
	}

}
