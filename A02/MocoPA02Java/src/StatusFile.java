import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StatusFile {

	Path parentPath = null;
	public StatusFile(){
		try {
			Path path = Paths.get(task1.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			this.parentPath = path.getParent();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void download(String input, int domainId)
	{   			
		//save JSON file from URL
		String filename = "status-0"+(domainId+1)+".json";
		
		try {
			//"http://jsonplaceholder.typicode.com/posts"
			URL url = new URL(input);
			ReadableByteChannel rbc =  Channels.newChannel(url.openStream());
			
			//Check if Directory exists
			File theDir = new File(parentPath.resolve("dataPa2").toString());
			File theDir2 = new File(parentPath.resolve("dataPa2\\jsonfiles").toString());
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				theDir.mkdir();
				theDir2.mkdir();
			}
			FileOutputStream fos = new FileOutputStream(
					parentPath.resolve("dataPa2\\jsonfiles\\"+filename).toString());

			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			//System.out.println("Successful created filename : "+filename);
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(SecurityException se){
			System.err.println("Unable to create directory");
	    } catch (IOException e) {
	    	System.err.println("Unable to access URL: " + input + "\n");
	    	e.printStackTrace();
		}	
	}

	public long jsonParser(String jsonFilePath, int numFile)
	{	
		long systemTime = 0;
		try {
			
			//Check if Directory exists
			File theDir = new File("dataPa2\\excelfiles");
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				theDir.mkdir();
			}
			
			jsonObject jsonObj = new jsonObject();		
			
			String excelFilePath = null;
			switch (numFile) {
			case 1:
				excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\status-01.xlsx").toString();
				break;
			case 2:
				excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\status-02.xlsx").toString();
				break;
			case 3:
				excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\status-03.xlsx").toString();
				break;
			case 4:
				excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\status-04.xlsx").toString();
				break;
			case 5:
				excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\status-05.xlsx").toString();
				break;
			case 6:
				excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\status-06.xlsx").toString();
				break;
			default:
				break;
			}
			
			//read file
			FileReader reader = new FileReader(jsonFilePath);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
			
			//get JsonArray
			systemTime 						= (long)jsonObject.get("systemTime");
			long timeSinceStartup 			= (long)jsonObject.get("timeSinceStartup");
			JSONArray neighbors				= (JSONArray)jsonObject.get("neighbors");
			JSONArray links 				= (JSONArray)jsonObject.get("links");
			JSONArray routes 				= (JSONArray)jsonObject.get("routes");
			JSONArray topology 				= (JSONArray)jsonObject.get("topology");
			
			//TEST
//			System.out.println("SysTime : "+systemTime);
						
			//get data
			//STATUS SHEET		
			jsonObj.setSystemTime(systemTime);
			jsonObj.setTimeSinceStartup(timeSinceStartup);			
			
			writeExcel(1,jsonObj, excelFilePath );
			
			//get data for sheets
			//take val element from array=============================
			//NEIGHBOHRS
			Iterator j = neighbors.iterator();
			while(j.hasNext()){
				JSONObject innerObj = (JSONObject)j.next();
				//System.out.println(innerObj.get("ipAddress"));
				jsonObj.setIpAdress((String)innerObj.get("ipAddress"));
				jsonObj.setSymmetric((Boolean)innerObj.get("symmetric"));
				jsonObj.setMultiPointRelay((Boolean)innerObj.get("multiPointRelay"));
				jsonObj.setMultiPointRelaySelector((Boolean)innerObj.get("multiPointRelaySelector"));
				jsonObj.setWillingness((long)innerObj.get("willingness"));
				jsonObj.setTwoHopNeighborCount((long)innerObj.get("twoHopNeighborCount"));
				//WRITE
				writeExcel(2, jsonObj, excelFilePath);	
			}	
			//========================================================
			//LINKS
			Iterator j1 = links.iterator();
			while(j1.hasNext()){
				JSONObject innerObj = (JSONObject)j1.next();
				//System.out.println(innerObj.get("ipAddress"));
				jsonObj.setLocalIP((String)innerObj.get("localIP"));
				jsonObj.setRemoteIP((String)innerObj.get("remoteIP"));
				jsonObj.setValidityTime((long)innerObj.get("validityTime"));
				jsonObj.setLinkQuality((double)innerObj.get("linkQuality"));
				jsonObj.setNeighborLinkQuality((double)innerObj.get("neighborLinkQuality"));
				jsonObj.setLinkCost((long)innerObj.get("linkCost"));
				//WRITE
				writeExcel(3, jsonObj, excelFilePath);				
			}
			//========================================================			
			//ROUTES
			/*
			 * private String destination;
				private long genmask;
				private String gateway;
				private long metric;
				private long rtpMetricCost;
				private String networkInterface;*/
			Iterator j2 = routes.iterator();
			while(j2.hasNext()){
				JSONObject innerObj = (JSONObject)j2.next();
				//System.out.println(innerObj.get("ipAddress"));
				jsonObj.setDestination((String)innerObj.get("destination"));
				jsonObj.setGenmask((long)innerObj.get("genmask"));
				jsonObj.setGateway((String)innerObj.get("gateway"));
				jsonObj.setMetric((long)innerObj.get("metric"));
				jsonObj.setRtpMetricCost((long)innerObj.get("rtpMetricCost"));
				jsonObj.setNetworkInterface((String)innerObj.get("networkInterface"));
				//WRITE
				writeExcel(4, jsonObj, excelFilePath);					
			}
			//========================================================	
			//TOPOLOGY
			Iterator j3 = topology.iterator();
			while(j3.hasNext()){
				JSONObject innerObj = (JSONObject)j3.next();
				jsonObj.setDestinationIP((String)innerObj.get("destinationIP"));
				jsonObj.setLastHopIP((String)innerObj.get("lastHopIP"));
				jsonObj.setLinkQuality((double)innerObj.get("linkQuality"));
				jsonObj.setNeighborLinkQuality((double)innerObj.get("neighborLinkQuality"));
				jsonObj.setTcEdgeCost((long)innerObj.get("tcEdgeCost"));
				jsonObj.setValidityTime((long)innerObj.get("validityTime"));
				//WRITE
				writeExcel(5, jsonObj, excelFilePath);			
			}
			//========================================================	
		} 
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParseException ex) {
			//ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}catch(SecurityException se){
			System.err.println("Unable to create directory");
	    }
		
		return systemTime;
	}
	

	@SuppressWarnings("resource")
	public void writeExcel(int sheetNo,jsonObject jsonObject, String excelFilePath) throws IOException{
		
		long SystemTime = jsonObject.getSystemTime();
		long TimeSinceStartup = jsonObject.getTimeSinceStartup();
		
		String ipAdress = jsonObject.getIpAdress();
		boolean symmetric = jsonObject.getSymmetric();
		boolean multiPointRelay = jsonObject.getMultiPointRelay();
		boolean multiPointRelaySelector = jsonObject.getMultiPointRelaySelector();
		long willingness = jsonObject.getWillingness();
		long twoHopNeighborCount = jsonObject.getTwoHopNeighborCount();
		
		String localIP = jsonObject.getLocalIP();
		String remoteIP = jsonObject.getRemoteIP();
		long validityTime = jsonObject.getValidityTime();
		double linkQuality = jsonObject.getLinkQuality();
		double neighborLinkQuality = jsonObject.getNeighborLinkQuality();
		long linkCost = jsonObject.getLinkCost();
		
		String destination = jsonObject.getDestination();
		long genmask = jsonObject.getGenmask();
		String gateway = jsonObject.getGateway();
		long metric = jsonObject.getMetric();
		long rtpMetricCost = jsonObject.getRtpMetricCost();
		String networkInterface = jsonObject.getNetworkInterface();
		
		String destinationIP = jsonObject.getDestinationIP();
		String lastHopIP = jsonObject.getLastHopIP();
		long tcEdgeCost = jsonObject.getTcEdgeCost();		
		
		
		//open stream 		
		try{
			  File excelFile = new File(excelFilePath);
			  if(!excelFile.exists()){
				  //create File
				  XSSFWorkbook wb = new XSSFWorkbook();
				  //new Sheets
			      XSSFSheet sheet1 = wb.createSheet("status");
			      XSSFSheet sheet2 = wb.createSheet("neighbors");
			      XSSFSheet sheet3 = wb.createSheet("links");
			      XSSFSheet sheet4 = wb.createSheet("routes");
			      XSSFSheet sheet5 = wb.createSheet("topology");
			      //create Title cells
			      Cell cell = null;
			      XSSFRow sheetrow;
			      //statusSheet
			      sheetrow = sheet1.createRow(0);	
			      cell = sheetrow.getCell(0);
			      if(cell == null){
			          cell = sheetrow.createCell(0);
			          cell.setCellValue("SystemTime");
			          cell = sheetrow.createCell(1);
			          cell.setCellValue("TimeSinceStartup");
			      }
			      //neighborSheet
			      sheetrow = sheet2.createRow(0);  
				      cell = sheetrow.getCell(0);
				      if(cell == null){
				          cell = sheetrow.createCell(0);
				          cell.setCellValue("SystemTime");
				          cell = sheetrow.createCell(1);
				          cell.setCellValue("ipAdress");
				          cell = sheetrow.createCell(2);
				          cell.setCellValue("symmetric");
				          cell = sheetrow.createCell(3);
				          cell.setCellValue("multiPointRelay");
				          cell = sheetrow.createCell(4);
				          cell.setCellValue("multiPointRelaySelector");
				          cell = sheetrow.createCell(5);
				          cell.setCellValue("willingness");
				          cell = sheetrow.createCell(6);
				          cell.setCellValue("twoHopNeighborCount");
				  }
				  //linkSheet
				  sheetrow = sheet3.createRow(0);	      
					cell = sheetrow.getCell(0);
					      if(cell == null){
					          cell = sheetrow.createCell(0);
					          cell.setCellValue("SystemTime");
					          cell = sheetrow.createCell(1);
					          cell.setCellValue("localIP");
					          cell = sheetrow.createCell(2);
					          cell.setCellValue("remoteIP");
					          cell = sheetrow.createCell(3);
					          cell.setCellValue("validityTime");
					          cell = sheetrow.createCell(4);
					          cell.setCellValue("linkQuality");
					          cell = sheetrow.createCell(5);
					          cell.setCellValue("neighborLinkQuality");
					          cell = sheetrow.createCell(6);
					          cell.setCellValue("linkCost");
					      }
				  //routeSheet
					      sheetrow = sheet4.createRow(0);	
						      cell = sheetrow.getCell(0);
						      if(cell == null){
						          cell = sheetrow.createCell(0);
						          cell.setCellValue("SystemTime");
						          cell = sheetrow.createCell(1);
						          cell.setCellValue("destination");
						          cell = sheetrow.createCell(2);
						          cell.setCellValue("genmask");
						          cell = sheetrow.createCell(3);
						          cell.setCellValue("gateway");
						          cell = sheetrow.createCell(4);
						          cell.setCellValue("metric");
						          cell = sheetrow.createCell(5);
						          cell.setCellValue("rtpMetricCost");
						          cell = sheetrow.createCell(6);
						          cell.setCellValue("networkInterface");
						      }
			      //topologySheet
				sheetrow = sheet5.createRow(0);	      
					cell = sheetrow.getCell(0);
							      if(cell == null){		
							          cell = sheetrow.createCell(0);
							          cell.setCellValue("SystemTime");		    	  
							          cell = sheetrow.createCell(1);
							          cell.setCellValue("destinationIP");
							          cell = sheetrow.createCell(2);
							          cell.setCellValue("lastHopIP");
							          cell = sheetrow.createCell(3);
							          cell.setCellValue("linkQuality");
							          cell = sheetrow.createCell(4);
							          cell.setCellValue("neighborLinkQuality");
							          cell = sheetrow.createCell(5);
							          cell.setCellValue("tcEdgeCost");
							          cell = sheetrow.createCell(6);
							          cell.setCellValue("validityTime");
							      }
			      FileOutputStream fileOut = new FileOutputStream(excelFile);
			      wb.write(fileOut);
			      fileOut.close();
			  }
			  FileInputStream infile = new FileInputStream(excelFile);
			  XSSFWorkbook workbook = new XSSFWorkbook(infile);
		      //getsheet  
		      XSSFSheet statusSheet 	= workbook.getSheetAt(0);
		      XSSFSheet neighborSheet 	= workbook.getSheetAt(1);
		      XSSFSheet linkSheet 		= workbook.getSheetAt(2);
		      XSSFSheet routeSheet 		= workbook.getSheetAt(3);
		      XSSFSheet topologySheet 	= workbook.getSheetAt(4);
		      //get number or row
		      int rowNumSheet1 = statusSheet.getLastRowNum()+1;
		      int rowNumSheet2 = neighborSheet.getLastRowNum()+1;
		      int rowNumSheet3 = linkSheet.getLastRowNum()+1;
		      int rowNumSheet4 = routeSheet.getLastRowNum()+1;
		      int rowNumSheet5 = topologySheet.getLastRowNum()+1;
		      
		      Cell cell = null;
		      
		      XSSFRow sheetrow;
		      switch (sheetNo) {
		      	case 1:
		      		  sheetrow = statusSheet.getRow(rowNumSheet1);
				      if(sheetrow == null){
				          sheetrow = statusSheet.createRow(rowNumSheet1);
				      }		      
				      //update the value of cell
				      cell = sheetrow.getCell(0);
				      if(cell == null){
				          cell = sheetrow.createCell(0);
				          cell.setCellValue(SystemTime);
				          cell = sheetrow.createCell(1);
				          cell.setCellValue(TimeSinceStartup);
				      }
		      		break;
		      	case 2:
		      		sheetrow = neighborSheet.getRow(rowNumSheet2);
		      		if(sheetrow == null){
				          sheetrow = neighborSheet.createRow(rowNumSheet2);
				      }		      
				      //update the value of cell
				      cell = sheetrow.getCell(0);
				      if(cell == null){
				          cell = sheetrow.createCell(0);
				          cell.setCellValue(SystemTime);
				          cell = sheetrow.createCell(1);
				          cell.setCellValue(ipAdress);
				          cell = sheetrow.createCell(2);
				          cell.setCellValue(symmetric);
				          cell = sheetrow.createCell(3);
				          cell.setCellValue(multiPointRelay);
				          cell = sheetrow.createCell(4);
				          cell.setCellValue(multiPointRelaySelector);
				          cell = sheetrow.createCell(5);
				          cell.setCellValue(willingness);
				          cell = sheetrow.createCell(6);
				          cell.setCellValue(twoHopNeighborCount);
				      }	      		
		      		break;
		      	case 3:
		      		sheetrow = linkSheet.getRow(rowNumSheet3);
		      		if(sheetrow == null){
				          sheetrow = linkSheet.createRow(rowNumSheet3);
				      }		      
				      //update the value of cell
				      cell = sheetrow.getCell(0);
				      if(cell == null){
				          cell = sheetrow.createCell(0);
				          cell.setCellValue(SystemTime);
				          cell = sheetrow.createCell(1);
				          cell.setCellValue(localIP);
				          cell = sheetrow.createCell(2);
				          cell.setCellValue(remoteIP);
				          cell = sheetrow.createCell(3);
				          cell.setCellValue(validityTime);
				          cell = sheetrow.createCell(4);
				          cell.setCellValue(linkQuality);
				          cell = sheetrow.createCell(5);
				          cell.setCellValue(neighborLinkQuality);
				          cell = sheetrow.createCell(6);
				          cell.setCellValue(linkCost);
				      }
		      		break;
		      	case 4:
		      		sheetrow = routeSheet.getRow(rowNumSheet4);
		      		if(sheetrow == null){
				          sheetrow = routeSheet.createRow(rowNumSheet4);
				      }		      
				      //update the value of cell
				      cell = sheetrow.getCell(0);
				      if(cell == null){
				          cell = sheetrow.createCell(0);
				          cell.setCellValue(SystemTime);
				          cell = sheetrow.createCell(1);
				          cell.setCellValue(destination);
				          cell = sheetrow.createCell(2);
				          cell.setCellValue(genmask);
				          cell = sheetrow.createCell(3);
				          cell.setCellValue(gateway);
				          cell = sheetrow.createCell(4);
				          cell.setCellValue(metric);
				          cell = sheetrow.createCell(5);
				          cell.setCellValue(rtpMetricCost);
				          cell = sheetrow.createCell(6);
				          cell.setCellValue(networkInterface);
				      }
		      		break;
		      	case 5:
		      		sheetrow = topologySheet.getRow(rowNumSheet5);
		      		if(sheetrow == null){
				          sheetrow = topologySheet.createRow(rowNumSheet5);
				      }		      
				      //update the value of cell
				      cell = sheetrow.getCell(0);
				      if(cell == null){		
				          cell = sheetrow.createCell(0);
				          cell.setCellValue(SystemTime);		    	  
				          cell = sheetrow.createCell(1);
				          cell.setCellValue(destinationIP);
				          cell = sheetrow.createCell(2);
				          cell.setCellValue(lastHopIP);
				          cell = sheetrow.createCell(3);
				          cell.setCellValue(linkQuality);
				          cell = sheetrow.createCell(4);
				          cell.setCellValue(neighborLinkQuality);
				          cell = sheetrow.createCell(5);
				          cell.setCellValue(tcEdgeCost);
				          cell = sheetrow.createCell(6);
				          cell.setCellValue(validityTime);
				      }
		      		break;
		      	default:
		      		break;
		      }
		           		      
		      
		      //=====================================================
		      //outputstream, close stream.
		      infile.close();
		      FileOutputStream outFile = new FileOutputStream(excelFile);
		      workbook.write(outFile);
	          outFile.close();	          
	          //System.out.println("Success updated..");
	          
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
	}
}
