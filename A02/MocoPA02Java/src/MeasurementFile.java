import java.io.BufferedReader;
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

public class MeasurementFile {

	Path parentPath = null;
	public MeasurementFile(){
		try {
			Path path = Paths.get(task1.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			this.parentPath = path.getParent();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void download(String fileId) throws IOException {
		// save text file from URL
		try {
			// "http://jsonplaceholder.typicode.com/posts"
			URL url = new URL("http://gizmo-01.informatik.uni-bonn.de/"+fileId+".txt");
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			// handle error
			FileOutputStream fos = new FileOutputStream(
					parentPath.resolve("sampledata\\jsonfiles\\" + fileId + ".txt").toString());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			System.out.println("Successful created filename : " + fileId);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void textParser(String textFilePath){
		try (BufferedReader br = new BufferedReader(new FileReader(textFilePath)))
		{
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				//System.out.println(sCurrentLine);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeExcel(){
		
	}
}
