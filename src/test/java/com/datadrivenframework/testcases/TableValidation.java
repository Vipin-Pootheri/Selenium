package com.datadrivenframework.testcases;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.datadrivenframework.testcases.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class TableValidation extends basetest{
	SoftAssert soa =null;
	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+ "\\TestData.xlsx");
	@Test(dataProvider="getData")
	public void tablevalidation(Hashtable<String, String> data)  {
		String dataBaseName ="localhost:15211:xe";
		String SchemaName="testing";
		
		test=rep.startTest("Validate  " + data.get("TestCaseName") + " table");
		try {
			test.log(LogStatus.INFO, "Connecting to datbase " + "testing" );
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","testing","1234");
			Statement st=con.createStatement(); 
			int mincount;
			int scount =executesourcequery(st,data.get("SOURCE QUERY"));
			if (scount!=0) {
				int tcount = executetargetquery(st, data.get("TARGET QUERY"));
				if (tcount!=0) {
					test.log(LogStatus.INFO, "Comparing source and target record counts");
					if (scount==tcount) {
						test.log(LogStatus.INFO, "Source and target records are matching");	
						
						mincount=comparedata(st,data.get("SOURCE QUERY"),data.get("TARGET QUERY"));
							if (mincount==0) {
								test.log(LogStatus.PASS, "Source and target data are same");
							}
							else {
								test.log(LogStatus.FAIL, "Source and target data are not same");
							}
						
					}else if (scount>tcount){
						test.log(LogStatus.FAIL, "Source has more records than target");
					}
					else if (tcount>scount){
						test.log(LogStatus.FAIL, "Target has more records than source");
					}
						
				}
			}
		con.close(); 	
			}catch (Throwable t) {
				System.out.println("Unable to connect to databse : '"+ dataBaseName + "'  Error : " +t.getMessage());
				test.log(LogStatus.FAIL, "Unable to connect to databse :'"+ dataBaseName +"' :  Error : " +t.getMessage());
				
			}
		 	
	

	}
	@AfterMethod
	public void quit(){
		
		rep.endTest(test);
		rep.flush();
	}
	@BeforeMethod
	public void BeforeTest() {
		
	}
	@DataProvider
	public Object[][] getData() throws IOException{
				
		
		String sheetname="TestData";
		
		int k=1;
		int rows=0;
		while (!(xls.getCellData(sheetname, 0, k)=="")){
			k=k+1;
			rows=rows+1;
		}
		rows =rows-1;
		//System.out.println("No.Of datarows " + rows);

		int cols=0;
		while (!(xls.getCellData(sheetname, cols, 1)=="")){
			cols =cols+1;
		}
		//System.out.println("No.Of datacols " + cols);
		int row=0;
		//read data
		Object[][] data = new Object[rows][1];
		Hashtable<String, String> table =null;
		String key;
		String value;
		for (int i=2;i<=rows+1;i++) {
			table =new Hashtable<String,String>();
			for (int j=0;j<cols;j++) {
				key=xls.getCellData(sheetname, j, 1);
				value=xls.getCellData(sheetname, j, i);	
				table.put(key, value);
			}
			data[row][0]=table;
			row =row+1;
		}
		return data;
	
	}


	}

