package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.bharatd.demo.pojos.Weather;

public class Utils {

	
	public static ArrayList<Weather> parsedTxtData(String regionCode,String weatherParam){
		  	URL url;
		    InputStream is = null;
		    BufferedReader br;
		    String line = null;
		    int count=0;
		    boolean showData=false;
		    ArrayList<Weather> weatherList=new ArrayList<>();
		    File file =null;
		    
	         FileWriter writer = null;
		    try{
		    	url = new URL(Constants.URL+weatherParam+"/date/"+regionCode+".txt");
  				
		    	weatherParam=changeWeaterParam(weatherParam);
		    	is = url.openStream();  // throws an IOException
		        br = new BufferedReader(new InputStreamReader(is));

		         file =new File(regionCode+".txt");
		        
		        
		          writer=new FileWriter(file);
		         
		        while ((line = br.readLine()) != null) {
		        	
		        	
			         writer.append(line);
			         writer.append("\n");
		        	
		            if(count>=8){
		            
		            	StringTokenizer st = new StringTokenizer(line);
		            	int loop=0;
		            	String year=st.nextToken();
            	 
		            	 try{
		            		 for(int i=1;i<Constants.coloumnIndex.length;i++)
		            			{
		            			 
		            			 	String value=line.substring(Constants.coloumnIndex[i]-4, Constants.coloumnIndex[i]).trim();
		            				
		            			 	if(value.equalsIgnoreCase("---") || value.equalsIgnoreCase(" ")){
		            			 		value="N/A";
		            			 	}
		            			 	
		            				Weather weather=new Weather(regionCode, weatherParam,year,Constants.SEASION_LIST[i],value);
					            	weatherList.add(weather);
		            				}
		            			}
		            			catch(StringIndexOutOfBoundsException e){
		            				//System.out.println("no data available for that month");
		            			}
		            	 
		            	 
		            }
		           
		            
		            count++;
		            
		        }
		        
		        
		    }
		    catch(Exception e){
		    	System.out.println("Error in Reading File");
		    }
		    finally {
				
	             try {
	 
	                 writer.flush();
	 
	                 writer.close();
	 
	             } catch (IOException e) {
	 
	                 System.out.println("Error while flushing/closing fileWriter !!!");
	 
	                 e.printStackTrace();
	
	             }


		    }  
  		
		return weatherList;
		
	}

	
	
	
	 private static String changeWeaterParam(String weatherParam) {
		// TODO Auto-generated method stub
		 
		 String result=weatherParam;
		if(weatherParam.equalsIgnoreCase("Tmax")){
			result="Max temp";
		}
		
		switch(weatherParam){
		case "Tmax":
			result="Max temp";
			break;
		case "Tmin":
			result="Min temp";
			break;
		case "Tmean":
			result="Mean temp";
			break;
		}
		
		
		return result;
	}




	public static String writeCsvFile(String fileName,ArrayList<Weather> list) {
		 
		 
					 	 final String COMMA_DELIMITER = ",";
					
					     final String NEW_LINE_SEPARATOR = "\n";
					     final String FILE_HEADER = "region_code,weather_param,year,key,value";
					     FileWriter writer=null;
					     File file = null;
					     String msg="";
						 
				 try {
					 
					 file=new File(fileName);
					 
						//System.out.println(file.getAbsolutePath());
			
						writer=new FileWriter(file);
					
					//Write the CSV file header
					writer.append(FILE_HEADER.toString());
					//Add a new line separator after the header
						writer.append(NEW_LINE_SEPARATOR);
						
						for(Weather weather:list){
							writer.append(weather.getWeather_param());
							writer.append(COMMA_DELIMITER);
							writer.append(weather.getWeather_param());
							writer.append(COMMA_DELIMITER);
							writer.append(weather.getYear());
							writer.append(COMMA_DELIMITER);
							writer.append(weather.getKey());
							writer.append(COMMA_DELIMITER);
							
							if(weather.getValue().equals("") || weather.getValue().equals("---")){
								writer.append("N/A");

							}
							else{
							writer.append(weather.getValue());
							}
							writer.append(COMMA_DELIMITER);

							writer.append(NEW_LINE_SEPARATOR);

						}
			
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Error in Creating csv file Kindly close file if open");
				}
				 finally {
					
					             try {
					 
					                 writer.flush();
					 
					                 writer.close();
					 
					             } catch (IOException e) {
					 
					                 System.out.println("Error while flushing/closing fileWriter !!!");
					 
					                 e.printStackTrace();
					
					             }
			
				 
				 }
				 
				 if(file==null)
				 msg="Something went wrong";
				 else
					 msg="File Created Available at path-->"+file.getAbsolutePath();
				 
				 
				 return msg;
					          
				 }




	public static void showAverage(ArrayList<Weather> list, String wea_param) {
		// TODO Auto-generated method stub
	float avgUk = 0,avgEngland=0,avgWales=0,avgScotland=0;
	int cntuk = 0,cntwales=0,cntScotland=0,cntEng=0;
	System.out.println(wea_param);
	for(Weather weather:list){
		
		switch(weather.getRegion_code()){
		case "UK":
			
			if(!weather.getValue().equalsIgnoreCase("N/A")&& !weather.getValue().isEmpty() && weather.getWeather_param().equalsIgnoreCase(wea_param))
			{
				cntuk++;
				System.out.println(weather.getValue());

				avgUk=avgUk+Float.parseFloat(weather.getValue());
			}
		break;
		
		case "England":
			
			if(!weather.getValue().equalsIgnoreCase("N/A")&& !weather.getValue().isEmpty() && weather.getWeather_param().equalsIgnoreCase(wea_param))
			{
				cntEng++;
				avgEngland=avgEngland+Float.parseFloat(weather.getValue());
			}
		break;
	
		case "Scotland":
			
			if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty() && weather.getWeather_param().equalsIgnoreCase(wea_param))
			{
				cntScotland++;
				avgScotland=avgScotland+Float.parseFloat(weather.getValue());
			}
		break;
		case "Wales":
			
			if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty() &&  weather.getWeather_param().equalsIgnoreCase(wea_param))
			{
				cntwales++;
				avgWales=avgWales+Float.parseFloat(weather.getValue());
			}
		break;

		
		
		}
		
		
		
	}
	
	System.out.println("Average of "+wea_param+" from 1910 to 2017 is for UK is"+(avgUk/cntuk) );
	System.out.println("Average of "+wea_param+" from 1910 to 2017 is for England is"+(avgEngland/cntEng) );
	System.out.println("Average of "+wea_param+" from 1910 to 2017 is for Scotland is"+(avgScotland/cntScotland) );
	System.out.println("Average of "+wea_param+" from 1910 to 2017 is for Wales is"+(avgWales/cntwales) );
	
	}




	public static void maxWeatherParameterValue(ArrayList<Weather> list) {
		// TODO Auto-generated method stub
		
		Float maxRainFall=(float) 0.0,maxSunshine=(float) 0.0,maxMeanTemp=(float) 0.0,maxMinTemp=(float) 0.0,maxMaxTemp=(float) 0.0;
		Weather objRanFall=null,objSunShine=null,objMinTemp=null,objMaxTemp=null,objMeanTemp=null;
		for(Weather weather:list){
			
				
			switch (weather.getWeather_param()) {
			
			

			
			case "Max temp":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxMaxTemp<temp){
						maxMaxTemp=temp;
					objMaxTemp=weather;
					}
				}
					
				break;

			
			case "Min temp":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxMinTemp<temp){
						maxMinTemp=temp;
					objMinTemp=weather;
					}
				}
					
				break;
			
			
			
			
			case "Mean temp":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxMeanTemp<temp){
						maxRainFall=temp;
					objMeanTemp=weather;
					}
				}
					
				break;
			
			
			
			
			case "Rainfall":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxRainFall<temp){
						maxRainFall=temp;
					objRanFall=weather;
					}
				}
					
				break;

			case "Sunshine":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxSunshine<temp){
						maxSunshine=temp;
					objSunShine=weather;
					}
				}
					
				break;

			
			default:
				break;
			}	
		}
	
		System.out.println("Max Maximum Temp found in "+objMaxTemp.getRegion_code()+" in year "+objMaxTemp.getYear()+" With value "+objMaxTemp.getValue());
		System.out.println("Max Min temp found in "+objMinTemp.getRegion_code()+" in year "+objMinTemp.getYear()+" With value "+objMinTemp.getValue());
		
		System.out.println("Max mean Temp found in "+objMeanTemp.getRegion_code()+" in year "+objMeanTemp.getYear()+" With value "+objMeanTemp.getValue());
		System.out.println("Max SunShine found in "+objSunShine.getRegion_code()+" in year "+objSunShine.getYear()+" With value "+objSunShine.getValue());
		
		System.out.println("Max Rainfall found in "+objRanFall.getRegion_code()+" in year "+objRanFall.getYear()+" With value "+objRanFall.getValue());
		
	}




	public static void minWeatherParameterValue(ArrayList<Weather> list) {
		// TODO Auto-generated method stub
		float initialValue=Float.parseFloat(list.get(0).getValue());
		Float maxRainFall=initialValue,maxSunshine=initialValue,maxMeanTemp=initialValue,maxMinTemp=initialValue,maxMaxTemp=initialValue;
		Weather objRanFall=null,objSunShine=null,objMinTemp=null,objMaxTemp=null,objMeanTemp=null;
		for(Weather weather:list){
			
				
			switch (weather.getWeather_param()) {
			
			

			
			case "Max temp":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxMaxTemp>temp){
						maxMaxTemp=temp;
					objMaxTemp=weather;
					}
				}
					
				break;

			
			case "Min temp":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxMinTemp>temp){
						maxMinTemp=temp;
					objMinTemp=weather;
					}
				}
					
				break;
			
			
			
			
			case "Mean temp":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxMeanTemp>temp){
						maxRainFall=temp;
					objMeanTemp=weather;
					}
				}
					
				break;
			
			
			
			
			case "Rainfall":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxRainFall>temp){
						maxRainFall=temp;
					objRanFall=weather;
					}
				}
					
				break;

			case "Sunshine":
				if(!weather.getValue().equalsIgnoreCase("N/A") && !weather.getValue().isEmpty()){
					Float temp=Float.parseFloat(weather.getValue());
					if(maxSunshine>temp){
						maxSunshine=temp;
					objSunShine=weather;
					}
				}
					
				break;

			
			default:
				break;
			}	
		}
	
		System.out.println("Min Maximum Temp found in "+objMaxTemp.getRegion_code()+" in year "+objMaxTemp.getYear());
		System.out.println("Min Min temp found in "+objMinTemp.getRegion_code()+" in year "+objMinTemp.getYear());
		
		System.out.println("Min mean Temp found in "+objMeanTemp.getRegion_code()+" in year "+objMeanTemp.getYear());
		System.out.println("Min SunShine found in "+objSunShine.getRegion_code()+" in year "+objSunShine.getYear());
		
		System.out.println("Min Rainfall found in "+objRanFall.getRegion_code()+" in year "+objRanFall.getYear());
				
	}
		
}
