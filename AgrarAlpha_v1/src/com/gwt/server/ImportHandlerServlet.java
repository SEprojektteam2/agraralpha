package com.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.opencsv.CSVReader;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ImportHandlerServlet extends HttpServlet {
		
		public static final Logger log = Logger.getLogger(ImportHandlerServlet.class.getName());
		private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();  	
		private int noOfThreads = 0;
		Connection conn;
		
		@Override
		public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
			
		
			
			//setting up outputstream
			PrintWriter out = resp.getWriter();
			resp.setContentType("text/html");
			
			//get the file from blobstore
			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
			List<BlobKey> bkList = blobs.get("importCSV");
			BlobKey blobKey = bkList.get(0);
			BlobstoreInputStream blobStream = new BlobstoreInputStream(blobKey);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(blobStream, "utf-8"));
			CSVReader csvReader = null;
			//read lines from file
			while(bufferedReader.read() != -1){
				csvReader = new CSVReader(bufferedReader);
				List<String[]> rows = csvReader.readAll();
				Thread thread = ThreadManager.createBackgroundThread(new ImportHandlerThread(rows.toArray(new String[rows.size()][])));
				
				//Send redirect to client
				//out.println("success");
				//out.flush();
				//resp.sendRedirect("default.agraralphav1.appspot.com");
				
				//add new thread to insert lines into database. There are only 10 Threads allowed.
				while(noOfThreads > 9){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						log.warning(e.toString());
					}
				}
					
				thread.start();
				noOfThreads++;
					
				try {
					thread.join();
				} catch (InterruptedException e) {
					log.warning(e.toString());
				}
				
				if(!thread.isAlive())
					noOfThreads--;
			}
			
			blobstoreService.delete(blobKey);
			connectToDatabase();
			updateCountries();
			updateItems();
			updateElements();
			out.flush();
			csvReader.close();
		}
		
		//Create connection to mysqldatabase
		private void connectToDatabase(){ 
			MySQLConnection database = new MySQLConnection();
			if(database.connect()){
				System.out.println("<html><head></head><body>Connection Started</body></html>");
			}
		    conn = database.returnConnection();
		}
		
		private void updateCountries(){
			Statement deletion;
			try {
				deletion = conn.createStatement();
				deletion.executeUpdate("TRUNCATE countries");
				log.warning("Table countries truncated");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			//	e1.printStackTrace();
				log.warning(e1.getMessage());
			}
			
			Statement insertion;
			try {
				insertion = conn.createStatement();
				log.warning("Now countries inserting");
				insertion.executeUpdate("INSERT INTO countries (AreaCode, AreaName) SELECT distinct AreaCode, AreaName from records");
				log.warning("country insertion finished");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				log.warning(e1.getErrorCode() + e1.getMessage());
			}
		}
		
		private void updateElements(){
			Statement deletion;
			try {
				deletion = conn.createStatement();
				deletion.executeUpdate("TRUNCATE elements");
				log.warning("Table elements truncated");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			//	e1.printStackTrace();
				log.warning(e1.getMessage());
			}
			
			Statement insertion;
			try {
				insertion = conn.createStatement();
				log.warning("Now elements inserting");
				insertion.executeUpdate("INSERT INTO elements (ElementCode, ElementName) SELECT distinct ElementCode, ElementName from records");
				log.warning("element insertion finished");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				log.warning(e1.getErrorCode() + e1.getMessage());
			}
		}
		
		private void updateItems(){
			Statement deletion;
			try {
				deletion = conn.createStatement();
				deletion.executeUpdate("TRUNCATE items");
				log.warning("Table items truncated");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
			//	e1.printStackTrace();
				log.warning(e1.getMessage());
			}
			
			Statement insertion;
			try {
				insertion = conn.createStatement();
				log.warning("Now items inserting");
				insertion.executeUpdate("INSERT INTO items (ItemCode, ItemName) SELECT distinct ItemCode, ItemName from records");
				log.warning("item insertion finished");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				log.warning(e1.getErrorCode() + e1.getMessage());
			}
		}
	}
