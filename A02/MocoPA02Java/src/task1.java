import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.text.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class task1 {	

	
	public static void main(String[] args) throws IOException 
	{
		StatusFile sFiles = new StatusFile();
		MeasurementFile mFiles = new MeasurementFile();
		
		//Current Path
		Path pPath = null;
		try {
			Path path = Paths.get(task1.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			pPath = path.getParent();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Path parentPath = pPath;
		//TESTLink
		//sFiles.download("http://vimeo.com/api/v2/video/38356.json", 0);
		
		
		//Files
		ArrayList<Path> jsonFilesList = new ArrayList<Path>();
		jsonFilesList.add(parentPath.resolve("dataPa2\\jsonfiles\\status-01.json"));
		jsonFilesList.add(parentPath.resolve("dataPa2\\jsonfiles\\status-02.json"));
		jsonFilesList.add(parentPath.resolve("dataPa2\\jsonfiles\\status-03.json"));
		jsonFilesList.add(parentPath.resolve("dataPa2\\jsonfiles\\status-04.json"));
		jsonFilesList.add(parentPath.resolve("dataPa2\\jsonfiles\\status-05.json"));
		jsonFilesList.add(parentPath.resolve("dataPa2\\jsonfiles\\status-06.json"));
		
    	//For 2 hours, every 60 seconds
		Timer timer = new Timer();

		timer.schedule( new TimerTask() {
			long initialTime = System.currentTimeMillis();
			long totalTime = 2*60*60*1000;
			//long totalTime = 2*60*1000;
			
			long intialStatus_01Timestamp = Long.MAX_VALUE;
			long finalStatus_01Timestamp = Long.MIN_VALUE;
		    public void run() {
		    	if (System.currentTimeMillis() - initialTime > totalTime) {
		    		System.out.println("============COMPLETED STATUS DATA=============");
		    		System.out.println("Downloading measurement data files ...");
		      		mFiles.download("http");
		      		mFiles.download("ping");
		      		System.out.println("Saving measurement data ...");
		    		mFiles.httpParser(parentPath.resolve("dataPa2\\jsonfiles\\http.txt").toString(),intialStatus_01Timestamp,finalStatus_01Timestamp);
		    		mFiles.pingParser(parentPath.resolve("dataPa2\\jsonfiles\\ping.txt").toString(),intialStatus_01Timestamp,finalStatus_01Timestamp);
		    		System.out.println("Success update measurement data files");
		    		System.out.println("============COMPLETED MEASUREMENT DATA=============");
	                cancel();
	              } else {
	            	  System.out.println("Downloading status files ...");
	          		//downloadFiles
	          		sFiles.download("http://gizmo-01.informatik.uni-bonn.de/status.json", 0);
	          		sFiles.download("http://gizmo-02.informatik.uni-bonn.de/status.json", 1);
	          		sFiles.download("http://gizmo-03.informatik.uni-bonn.de/status.json", 2);
	          		sFiles.download("http://gizmo-04.informatik.uni-bonn.de/status.json", 3);
	          		sFiles.download("http://gizmo-05.informatik.uni-bonn.de/status.json", 4);
	          		sFiles.download("http://gizmo-06.informatik.uni-bonn.de/status.json", 5);

	        		System.out.println("Saving status data ...");
	            	  for(int i=0; i<jsonFilesList.size(); i++){
	  					Path pathFile = jsonFilesList.get(i);
	  					int j=i+1;
	  					if(i==0){
	  						long currentStatus_01Timestamp = sFiles.jsonParser(pathFile.toString(),j);
	  						if(currentStatus_01Timestamp < intialStatus_01Timestamp)
	  							intialStatus_01Timestamp = currentStatus_01Timestamp;
	  						if(currentStatus_01Timestamp > finalStatus_01Timestamp)
	  							finalStatus_01Timestamp = currentStatus_01Timestamp;
	  					}else{
	  						sFiles.jsonParser(pathFile.toString(),j);
	  					}
	  				}
	  				
	  				System.out.println("Success update status files");
	              }				
		    }
		}, 0, 30000);
	}
}




