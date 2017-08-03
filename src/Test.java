
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.bharatd.demo.pojos.*;

import dao.Constants;
import dao.Utils;
public class Test {

	
	static ArrayList<Weather> list;
	
	static ArrayList<Weather> tempList;
	static boolean isFileRead=false;
	
	public static void main(String[] args) {
		boolean breakLoop=false;
		list=new ArrayList<>();
		//take user input to read or download
		Scanner sc=new Scanner(System.in);
		
		System.out.println("*****************Program Started ****************");

		while(true){
		System.out.println("Please Enter \n  1.Download Files \n2.create csv\n 3.Amezing Fact 1\n 4.Fact 2\n 5.Fact3\n 6.quit");
		switch(sc.next()){
		case "1":
			readFiles();
			isFileRead=true;
			
		break;
		case "2"://Create CSV File
			if(!isFileRead){
			readFiles();
			isFileRead=true;
			}
			String fileName="weather.csv";
			System.out.println(Utils.writeCsvFile(fileName,list));
			break;
		
		case "3":		
			if(!isFileRead){
				readFiles();
				isFileRead=true;
			}
			Utils.showAverage(list,"Max temp");
			
			break;
		
			
		case "4":		
			if(!isFileRead){
				readFiles();
				isFileRead=true;
			}
			Utils.maxWeatherParameterValue(list);
			
			break;
		case "5":		
			if(!isFileRead){
				readFiles();
				isFileRead=true;
			}
			Utils.minWeatherParameterValue(list);

			break;
				
		case "6":		
			breakLoop=true;
			
			break;
			
		}
		
		if(breakLoop)
		break;
		}
		
		System.out.println("*****************Program Ended ****************");
		
	}


	private static void readFiles()  {
		//Reading Remote Files File From Remote 
		
		System.out.println("Downloading...");
		for(int i=0;i<Constants.REGION_LIST.length;i++){
			for(int j=0;j<Constants.WEATHER_PARAMETER.length;j++){
				tempList=Utils.parsedTxtData(Constants.REGION_LIST[i], Constants.WEATHER_PARAMETER[j]);
				list.addAll(tempList);
				System.out.println("...");
				}
			
			System.out.println("Downloading Complete.");

			
		}

		
	}
	
	
	
	
	
	
}
