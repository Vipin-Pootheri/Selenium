package com.datadrivenframework.testcases;
import java.util.Hashtable;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReadingData {

	@Test(dataProvider="getData")
	public void TestA(Hashtable<String, String>data) {
		System.out.println(data.get("Col1"));
	}
	
	
	@DataProvider
	public Object[][] getData(){
		String testcase="TestA";
		Xls_Reader xls = new Xls_Reader("C:\\Users\\me_si\\Desktop\\New folder\\Data.xlsx");
		String sheetName="TestData";
		int rowNum=1;
		int teststartrownum;
		int colstartrownum;
		int datastartrownum;
		while (!(xls.getCellData(sheetName, 0, rowNum).equals(testcase))) {
				rowNum++;
		}
		teststartrownum=rowNum;
		colstartrownum=rowNum+1;
		datastartrownum=rowNum+2;
		
		System.out.println("TestCase starts from " + teststartrownum);
		System.out.println("TestCase column starts from "+colstartrownum);
		System.out.println("Test Data starts from "+ datastartrownum);
		
		int k=datastartrownum;
		int rows=0;
		while (!(xls.getCellData(sheetName, 0, k)=="")){
			k=k+1;
			rows=rows+1;
		}
		System.out.println("No.Of datarows " + rows);

		int cols=0;
		while (!(xls.getCellData(sheetName, cols, colstartrownum)=="")){
			cols =cols+1;
		}
		System.out.println("No.Of datacols " + cols);
		int row=0;
		//read data
		Object[][] data = new Object[rows][1];
		Hashtable<String, String> table =null;
		String key;
		String value;
		for (int i=datastartrownum;i<(datastartrownum+rows);i++) {
			table =new Hashtable<String,String>();
			for (int j=0;j<cols;j++) {
				key=xls.getCellData(sheetName, j, colstartrownum);
				value=xls.getCellData(sheetName, j, i);	
				table.put(key, value);
			}
			data[row][0]=table;
			row =row+1;
		}
		return data;
	
	}
	
}