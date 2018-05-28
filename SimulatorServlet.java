package com.mirror.bigdata;

import java.io.*;
import java.sql.Timestamp;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SimulatorServlet extends HttpServlet implements Runnable {

	boolean voltage, current, pressure;   
	Thread simulator;

	public static int i = 1;

	public void init() throws ServletException {
		simulator = new Thread(this);
		simulator.setPriority(Thread.MIN_PRIORITY);
		simulator.start();


	}

	public void run() {

		i = 1;
		int jj = 1;

		while (true) {     

			 i++; //commented to get data from website
			
		//	boolean datafound = false;
			boolean datafound = true;
			 
		
			try {
				
		/*		//changes sarting to get data from website starts
				
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet(
					"http://mirrortech.in/EarthLine/view_data.php?pass=arun&a=" + jj );
				HttpResponse httpresponse = httpClient.execute(getRequest);
				if (httpresponse.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ httpresponse.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(
		                        new InputStreamReader((httpresponse.getEntity().getContent())));

				String output;
				System.out.println("Output from Server .... \n");
				String id = "";
				String a ="";
				String b = "";
				String c = "";
				
				while ((output = br.readLine()) != null) {
					System.out.println(output);
			
					if(output.contains("Records")){
						
					}else{
					
						JSONObject j = new JSONObject(output);
						id = j.getString("id");
						a = j.getString("a");
						b = j.getString("b");
						c = j.getString("c");
						
						System.out.println("id : " + id + " a : " + a + " b: " + b + " c : " + c);
						
						
						jj++;
						datafound = true;
						
					}
					
						
				}
				
				//changes starting to get data from website ends */

				
				voltage = IoT.voltage;
				current = IoT.current;
				pressure = IoT.pressure;

				//changes made to get data from website starts
				
				if(datafound){
				voltage = true;
				current = true;
				pressure = true;
				}
				else{
					voltage = false;
					current = false;
					pressure = false;
					
				}
				//changes made to get data from website ends
				
				
				if (voltage) {
					System.out.println("voltage");

					int v = ThreadLocalRandom.current().nextInt(220, 230 + 1);

					//changes to get data from website starts
					//v = Integer.parseInt(a);

					//changes to get data from website ends					
			//		insertdata("voltage",v);
					
					insertdata("temperature",v);

					if( (i>30) && (i<40)){

						v = ThreadLocalRandom.current().nextInt(170, 180 + 1);
						insertSpikedata("temperature",v);


					}

					//if( (i>100) && (i<110)){
					
				/*	if( (v > 30)){

						//v = ThreadLocalRandom.current().nextInt(170, 180 + 1);
						//insertSpikedata("voltage",v);
						insertSpikedata("temperature",v);


					} */

				}
				if (current) {
					System.out.println("current");

					int c1 = ThreadLocalRandom.current().nextInt(9, 10 + 1);

					//changes to get data from website starts
				//	c1 = Integer.parseInt(b);
					//changes to get data from website ends
					
					//insertdata("current",c1);
					insertdata("pressure",c1);
					
					
					if( (i>30) && (i<40)){

						c1 = ThreadLocalRandom.current().nextInt(5, 6 + 1);
						insertSpikedata("pressure",c1);


					}

					if( (i>100) && (i<110)){

						c1 = ThreadLocalRandom.current().nextInt(5, 6 + 1);
						insertSpikedata("pressure",c1);


					}

					
					
				}
				if (pressure) {
					System.out.println("pressure");

					int p =  ThreadLocalRandom.current().nextInt(38, 40 + 1);

					//changes to get data from website starts
					//p = Integer.parseInt(c);
					//changes to get data from website ends

					
					//insertdata("pressure",p);
					
					System.out.println("vibration: " + i);
					
					insertdata("vibration",p);
					
					
					if( (i>30) && (i<40)){

						p = ThreadLocalRandom.current().nextInt(20, 22 + 1);
						insertSpikedata("vibration",p);


					}

					if( (i>100) && (i<110)){

						p = ThreadLocalRandom.current().nextInt(20, 22 + 1);
						insertSpikedata("vibration",p);


					}

					
				}
				simulator.sleep(5000);
			}
			catch (Exception ex) { ex.printStackTrace();}

		}
	}



	public void destroy() {
		simulator.stop();
	}

	
	public void insertSpikedata(String type, int value){

		try{
			System.out.println("TYPE : " + type);
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("local");	

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("spike");

			if(type.equals("temperature")){
				BasicDBObject doc = new BasicDBObject("value", value).
						append("type","temperature").
						append("ts", new Timestamp(new Date().getTime()));
				table.insert(doc);
				System.out.println("Document inserted successfully");


			}
			if(type.equals("vibration")){
				BasicDBObject doc = new BasicDBObject("value", value).
						append("type","vibration").
						append("ts",  new Timestamp(new Date().getTime()));

				table.insert(doc);
				System.out.println("Document inserted successfully");


			}
			if(type.equals("pressure")){
				BasicDBObject doc = new BasicDBObject("value", value).
						append("type","pressure").
						append("ts",  new Timestamp(new Date().getTime()));

				table.insert(doc);
				System.out.println("Document inserted successfully");


			}
			
			


		}catch(Exception e){
			e.printStackTrace();
		}

	}
	

	public void insertdata(String type, int value){

		try{
			System.out.println("Test");
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("local");	

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("iot");

			if(type.equals("temperature")){
				BasicDBObject doc = new BasicDBObject("value", value).
						append("type","temperature").
						append("ts", new Timestamp(new Date().getTime()));
				table.insert(doc);
				System.out.println("Document inserted successfully");


			}
			if(type.equals("vibration")){
				BasicDBObject doc = new BasicDBObject("value", value).
						append("type","vibration").
						append("ts",  new Timestamp(new Date().getTime()));

				table.insert(doc);
				System.out.println("Document inserted successfully");


			}
			if(type.equals("pressure")){
				BasicDBObject doc = new BasicDBObject("value", value).
						append("type","pressure").
						append("ts",  new Timestamp(new Date().getTime()));

				table.insert(doc);
				System.out.println("Document inserted successfully");


			}


		}catch(Exception e){
			e.printStackTrace();
		}

	}
}