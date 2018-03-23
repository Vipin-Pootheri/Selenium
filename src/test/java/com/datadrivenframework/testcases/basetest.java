package com.datadrivenframework.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class basetest {
	WebDriver driver = null;
	public 	Properties prop;
	public ExtentReports rep=ExtentManager.getInstance();
	public ExtentTest test;
	
	
	/****Report Functions*****/
	public void reportPass(String msg) {
		
		test.log(LogStatus.PASS, msg);
	}
	public void reportFail(String msg) {
		test.log(LogStatus.FAIL, msg);
		attachfile();
		Assert.fail(msg);
		
	}
	public void attachfile() {
		
		//put screenshot file in reports
		//test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
	}
	
	public int executesourcequery(Statement st, String query) {
		test.log(LogStatus.INFO, "Executing the source query : Query : " + query);
		 int scount=0;
		try {
		ResultSet rs=st.executeQuery(query);
		test.log(LogStatus.INFO, "Source query executed sucessfully");
		test.log(LogStatus.INFO, "Getting the record count from source query");
		
		while (rs.next()) {
			scount=scount+1;
			}
		test.log(LogStatus.INFO, "Source record count is :" + scount);
		if(scount ==0) {
			test.log(LogStatus.FAIL, "Source record count is zero.Please recheck source query");
			
		}
	
		}

		catch (Throwable e ) {
			System.out.println("Error in Executing the source  query : Query :'"+query + "'  Error Message : "  + e.getMessage());
			test.log(LogStatus.FAIL,"Error in Executing the source  query : Query :'"+ query + "'  Error Message : "  + e.getMessage());
			
		
		}
		return scount;
}
	
		public int executetargetquery(Statement st, String query) {
			test.log(LogStatus.INFO, "Executing the target query : Query : " +  query);
			int tcount=0;
			try {
			ResultSet rs=st.executeQuery(query);
			test.log(LogStatus.INFO, "Target query executed sucessfully");
			
			while (rs.next()) {
				tcount=tcount+1;
				}
			test.log(LogStatus.INFO, "Target record count is :" + tcount);
			if(tcount ==0) {
				test.log(LogStatus.FAIL, "Target record count is zero.Please recheck target query");
				}
			}catch (Throwable e ) {
				System.out.println("Error in Executing the target  query : Query :'"+query + "'  Error Message : "  + e.getMessage());
				test.log(LogStatus.FAIL,"Error in Executing the target  query : Query :'"+query + "'  Error Message : "  + e.getMessage());
		}
			return tcount;
	}
		public int comparedata(Statement st, String squery, String tquery) {
			test.log(LogStatus.INFO, "Comparing source and target data");
			int fincount=0;
			String finquery=tquery + " minus " + squery;
			try {
				ResultSet rs=st.executeQuery(finquery);
				while (rs.next()) {
					 fincount = fincount+1;
					}
			} catch (Throwable e) {
				System.out.println("Error in Executing the minus query : Query :" + e.getMessage());
				test.log(LogStatus.FAIL,"Error in Executing the minus query : Query :" + e.getMessage());
		
				
			}
			return fincount;
		}
		
}
