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
		ArrayList<String> urlList = new ArrayList<String>();
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");	
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		//Files
		ArrayList<Path> jsonFilesList = new ArrayList<Path>();
		jsonFilesList.add(parentPath.resolve("sampledata\\jsonfiles\\status-01.json"));
		jsonFilesList.add(parentPath.resolve("sampledata\\jsonfiles\\status-02.json"));
		jsonFilesList.add(parentPath.resolve("sampledata\\jsonfiles\\status-03.json"));
		jsonFilesList.add(parentPath.resolve("sampledata\\jsonfiles\\status-04.json"));
		jsonFilesList.add(parentPath.resolve("sampledata\\jsonfiles\\status-05.json"));
		jsonFilesList.add(parentPath.resolve("sampledata\\jsonfiles\\status-06.json"));
		
		System.out.println("Initiating Process ...");
    	//For 2 hours, every 60 seconds
		Timer timer = new Timer();

		timer.schedule( new TimerTask() {
			long initialTime = System.currentTimeMillis();
			//long totalTime = 2*60*60*1000;
			long totalTime = 3*60*1000;
		    public void run() {
		    	if (System.currentTimeMillis() - initialTime > totalTime) {
		    		System.out.println("============COMPLETED=============");
	                cancel();
	              } else {
	            	  for(int i=0; i<jsonFilesList.size(); i++){
	  					Path pathFile = jsonFilesList.get(i);
	  					int j=i+1;
	  					sFiles.jsonParser(pathFile.toString(),j);
	  				}
	  				mFiles.httpParser(parentPath.resolve("sampledata\\jsonfiles\\http.txt").toString());
	  				mFiles.pingParser(parentPath.resolve("sampledata\\jsonfiles\\ping.txt").toString());
	  				
	  				System.out.println("Success update files");
	              }				
		    }
		}, 0, 60000);
		//}, 60*1000, 2*60*60*1000);
		
		
	}
}




