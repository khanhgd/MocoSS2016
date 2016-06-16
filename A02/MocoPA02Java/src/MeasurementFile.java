import java.io.BufferedReader;
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
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
	
	public void download(String fileId) {
		// save text file from URL
		try {
			
			//Check if Directory exists
			File theDir = new File(parentPath.resolve("dataPa2").toString());
			File theDir2 = new File(parentPath.resolve("dataPa2\\jsonfiles").toString());
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				theDir.mkdir();
				theDir2.mkdir();
			}
			
			URL url = new URL("http://gizmo-01.informatik.uni-bonn.de/"+fileId+".txt");
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			
			FileOutputStream fos = new FileOutputStream(
					parentPath.resolve("dataPa2\\jsonfiles\\" + fileId + ".txt").toString());
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(SecurityException se){
			System.err.println("Unable to create directory");
	    } catch (IOException e) {
	    	System.err.println("Unable to access URL: " + "http://gizmo-01.informatik.uni-bonn.de/"+fileId+".txt");
		}
	}
	
	public void httpParser(String textFilePath){
		String excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\http.xlsx").toString();
		try (BufferedReader br = new BufferedReader(new FileReader(textFilePath)))
		{
			//Check if Directory exists
			File theDir = new File("dataPa2\\excelfiles");
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				theDir.mkdir();
			}
			
			ArrayList<HttpObject> httpArray= new ArrayList<HttpObject>();
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				HttpObject httpObj = new HttpObject();
				httpObj.setTimestamp(Long.parseLong(sCurrentLine));
				if((sCurrentLine = br.readLine()) != null){
					httpObj.setBytes(Long.parseLong(sCurrentLine.split(" ")[1]));
				}
				if((sCurrentLine = br.readLine()) != null){
					httpObj.setTime(Double.parseDouble(sCurrentLine.split(" ")[1]));
				}
				httpArray.add(httpObj);
			}
			writeHttpExcel(httpArray,excelFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}catch(SecurityException se){
			System.err.println("Unable to create directory");
	    }
	}
	
	public void pingParser(String textFilePath){
		String excelFilePath = parentPath.resolve("dataPa2\\excelfiles\\ping.xlsx").toString();
		try (BufferedReader br = new BufferedReader(new FileReader(textFilePath)))
		{
			//Check if Directory exists
			File theDir = new File("dataPa2\\excelfiles");
			// if the directory does not exist, create it
			if (!theDir.exists()) {
				theDir.mkdir();
			}
			ArrayList<PingObject> pingArray= new ArrayList<PingObject>();
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				PingObject pingObj = new PingObject();
				double packetloss;
				pingObj.setTimestamp(Long.parseLong(sCurrentLine));
				sCurrentLine = br.readLine();
				sCurrentLine = br.readLine();
				sCurrentLine = br.readLine();
				if((sCurrentLine = br.readLine()) != null){
					packetloss = Double.parseDouble(sCurrentLine.split(" ")[6].split("%")[0])/100;
					pingObj.setPacketloss(packetloss);
					if(packetloss < 1.0){
						if((sCurrentLine = br.readLine()) != null){
							String[] rtt = sCurrentLine.split(" ")[3].split("/");
							pingObj.setRTTmin(Double.parseDouble(rtt[0]));
							pingObj.setRTTavg(Double.parseDouble(rtt[1]));
							pingObj.setRTTmax(Double.parseDouble(rtt[2]));
						}
					}
				}
				pingArray.add(pingObj);
			}
			writePingExcel(pingArray,excelFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}catch(SecurityException se){
			System.err.println("Unable to create directory");
	    }
	}
	
	public void writeHttpExcel(ArrayList<HttpObject> httpArray, String excelFilePath){
		// open stream
		try {
			//deleting old File to avoid duplicates
			File oldExcelFile = new File(excelFilePath);
			if (oldExcelFile.exists()) {
				oldExcelFile.delete();
			}
			File excelFile = new File(excelFilePath);
			// create File
			XSSFWorkbook wb = new XSSFWorkbook();
			// new Sheets
			XSSFSheet sheet1 = wb.createSheet("Sheet1");
			// create Title cells
			Cell cell = null;
			XSSFRow sheetrow;
			sheetrow = sheet1.createRow(0);
			cell = sheetrow.getCell(0);
			if (cell == null) {
				cell = sheetrow.createCell(0);
				cell.setCellValue("Timestamp");
				cell = sheetrow.createCell(1);
				cell.setCellValue("Bytes");
				cell = sheetrow.createCell(2);
				cell.setCellValue("Time");
			}
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			wb.write(fileOut);
			fileOut.close();
			FileInputStream infile = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(infile);
			// getsheet
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			for(HttpObject httpObj: httpArray){
				// get number or row
				int rowNumSheet = sheet.getLastRowNum() + 1;
				Cell newCell = null;
				XSSFRow newRow = sheet.getRow(rowNumSheet);
				if (newRow == null) {
					newRow = sheet.createRow(rowNumSheet);
				}
				// update the value of cell
				newCell = newRow.getCell(0);
				if (newCell == null) {
					newCell = newRow.createCell(0);
					newCell.setCellValue(httpObj.getTimestamp());
					newCell = newRow.createCell(1);
					newCell.setCellValue(httpObj.getBytes());
					newCell = newRow.createCell(2);
					newCell.setCellValue(httpObj.getTime());
				}
			}
			// =====================================================
			// outputstream, close stream.
			infile.close();
			FileOutputStream outFile = new FileOutputStream(excelFile);
			workbook.write(outFile);
			outFile.close();
			// System.out.println("Success updated..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writePingExcel(ArrayList<PingObject> pingArray, String excelFilePath){
		// open stream
		try {
			// deleting old File to avoid duplicates
			File oldExcelFile = new File(excelFilePath);
			if (oldExcelFile.exists()) {
				oldExcelFile.delete();
			}
			File excelFile = new File(excelFilePath);
			// create File
			XSSFWorkbook wb = new XSSFWorkbook();
			// new Sheets
			XSSFSheet sheet1 = wb.createSheet("Sheet1");
			// create Title cells
			Cell cell = null;
			XSSFRow sheetrow;
			sheetrow = sheet1.createRow(0);
			cell = sheetrow.getCell(0);
			if (cell == null) {
				cell = sheetrow.createCell(0);
				cell.setCellValue("Timestamp");
				cell = sheetrow.createCell(1);
				cell.setCellValue("Packetloss");
				cell = sheetrow.createCell(2);
				cell.setCellValue("RTTmin");
				cell = sheetrow.createCell(3);
				cell.setCellValue("RTTavg");
				cell = sheetrow.createCell(4);
				cell.setCellValue("RTTmax");
			}
			FileOutputStream fileOut = new FileOutputStream(excelFile);
			wb.write(fileOut);
			fileOut.close();
			FileInputStream infile = new FileInputStream(excelFile);
			XSSFWorkbook workbook = new XSSFWorkbook(infile);
			// getsheet
			XSSFSheet sheet = workbook.getSheetAt(0);

			for (PingObject pingObj : pingArray) {
				// get number or row
				int rowNumSheet = sheet.getLastRowNum() + 1;
				Cell newCell = null;
				XSSFRow Row = sheet.getRow(rowNumSheet);
				if (Row == null) {
					Row = sheet.createRow(rowNumSheet);
				}
				// update the value of cell
				newCell = Row.getCell(0);
				if (newCell == null) {
					newCell = Row.createCell(0);
					newCell.setCellValue(pingObj.getTimestamp());
					newCell = Row.createCell(1);
					newCell.setCellValue(pingObj.getPacketloss());
					newCell = Row.createCell(2);
					newCell.setCellValue(pingObj.getRTTmin());
					newCell = Row.createCell(3);
					newCell.setCellValue(pingObj.getRTTavg());
					newCell = Row.createCell(4);
					newCell.setCellValue(pingObj.getRTTmax());
				}
			}
			// =====================================================
			// outputstream, close stream.
			infile.close();
			FileOutputStream outFile = new FileOutputStream(excelFile);
			workbook.write(outFile);
			outFile.close();
			// System.out.println("Success updated..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
