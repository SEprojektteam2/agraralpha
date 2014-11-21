package com.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
				out.println("success");
				out.flush();
				resp.sendRedirect("default.agraralphav1.appspot.com");
				
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
			out.flush();
			csvReader.close();
		}
	}
