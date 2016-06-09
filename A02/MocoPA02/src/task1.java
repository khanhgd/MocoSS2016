import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.text.*;

import org.json.*;
import org.json.simple.JSONObject;

public class task1 {	

	public void download(String input, int domainId) throws IOException
	{   			
		//save JSON file from URL
		DateFormat df = new SimpleDateFormat("yyyyMMddHHss");
		String date = df.format(new Date());
		
		String domain;
		switch (domainId) {
		case 0:
			domain = "gizmo-01";
			break;
		case 1:
			domain = "gizmo-02";
			break;
		case 2:
			domain = "gizmo-03";
			break;
		case 3:
			domain = "gizmo-04";
			break;
		case 4:
			domain = "gizmo-05";
			break;
		case 5:
			domain = "gizmo-06";
			break;
		default:
			domain = "@_@";
			break;
		}
		
		String filename = domain+"-"+date;
		
		try {
			//"http://jsonplaceholder.typicode.com/posts"
			URL url = new URL(input);
			ReadableByteChannel rbc =  Channels.newChannel(url.openStream());		
			FileOutputStream fos = new FileOutputStream("E:\\Eclipse_workspace\\MocoPA02\\sampledata\\"+filename+".json");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			System.out.println("Successful created filename : "+filename);
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}	
	}		
	
	public void jsonParser()
	{
		
	}
	
	public static void main(String[] args) throws IOException 
	{
		ArrayList<String> urlList = new ArrayList<String>();
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");	
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
		urlList.add("http://vimeo.com/api/v2/video/38356.json");
			
		task1 taskObj = new task1();
		
		for(int i=0; i<urlList.size();i++)
		{
			String url = urlList.get(i);			
			taskObj.download(url,i);
		}	
		System.out.println("=====COMPLETED======");
		System.exit(0);
	}
}




