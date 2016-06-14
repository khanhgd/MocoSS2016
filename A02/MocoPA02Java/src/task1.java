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
		Path parentPath = null;
		try {
			Path path = Paths.get(task1.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			parentPath = path.getParent();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		//For 2 hours, every 30 seconds, updates every 10 minutes? 
		for(int i=0; i<jsonFilesList.size(); i++){
			Path pathFile = jsonFilesList.get(i);
			int j=i+1;
			sFiles.jsonParser(pathFile.toString(),j);
			System.out.println("Success update file status-0"+j+".xls");
		}
		mFiles.textParser(parentPath.resolve("sampledata\\jsonfiles\\http.txt").toString());
		System.out.println("Success update file http.txt");
		mFiles.textParser(parentPath.resolve("sampledata\\jsonfiles\\ping.txt").toString());
		System.out.println("Success update file ping.txt");
		
		System.out.println("============COMPLETED=============");
        
	}
}




