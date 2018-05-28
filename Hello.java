package com.mirror.bigdata;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

//import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;

//Plain old Java Object it does not extend as class or implements 
//an interface

//The class registers its methods for the HTTP GET request using the @GET annotation. 
//Using the @Produces annotation, it defines that it can deliver several MIME types,
//text, XML and HTML. 

//The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey 1";
	}

	// This method is called if XML is request
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {

		testmongo();

		return "<html> " + "<title>" + "Hello Jersey" + "</title>"
		+ "<body><h1>" + "Hello Jersey 2" + "</body></h1>" + "</html> ";
	}


	
	
	@POST
	@Path("/responsedata")
	@Consumes("application/json")
	public Response getData(String data) {
		


		JSONArray js = new JSONArray();

		try{
			System.out.println("Test 1");
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("local");	

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("iot");


			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("type", "temperature");
			//searchQuery.put("type", "pressure");
			//searchQuery.put("type", "vibration");

			DBCursor cursor = table.find(searchQuery);
			
			BasicDBObject obj = new BasicDBObject();
			
			while (cursor.hasNext()) {
			//	System.out.println(cursor.next());
				obj = (BasicDBObject) cursor.next();
				JSONObject out = new JSONObject();
				
				out.put("type", obj.getString("type"));
				out.put("value", obj.getInt("value"));
				out.put("ts", obj.getDate("ts"));
				
				js.put(out);
				
				System.out.println(js.toString());

				
			}
			
			cursor.close();
			
			
			searchQuery = new BasicDBObject();
			searchQuery.put("type", "pressure");

			cursor = table.find(searchQuery);
			
			 obj = new BasicDBObject();
			
			while (cursor.hasNext()) {
			//	System.out.println(cursor.next());
				obj = (BasicDBObject) cursor.next();
				JSONObject out = new JSONObject();
				
				out.put("type", obj.getString("type"));
				out.put("value", obj.getInt("value"));
				out.put("ts", obj.getDate("ts"));
				
				js.put(out);
				
				System.out.println(js.toString());

				
			}
			
			cursor.close();
			
			searchQuery = new BasicDBObject();
			searchQuery.put("type", "vibration");

			cursor = table.find(searchQuery);
			
			 obj = new BasicDBObject();
			
			while (cursor.hasNext()) {
			//	System.out.println(cursor.next());
				obj = (BasicDBObject) cursor.next();
				JSONObject out = new JSONObject();
				
				out.put("type", obj.getString("type"));
				out.put("value", obj.getInt("value"));
				out.put("ts", obj.getDate("ts"));
				
				js.put(out);
				
				System.out.println(js.toString());

				
			}
			
			cursor.close();


			
			System.out.println(js.toString());

		}catch(Exception e){
			e.printStackTrace();
		}



		JSONObject outputjs = new JSONObject();
		try {
			outputjs.put("output", js);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(201).entity(outputjs.toString()).build();
	
	}
	

	@POST
	@Path("/response")
	@Consumes("application/json")
	public Response getSpikeData(String data) {

		JSONArray js = new JSONArray();

		try{
			System.out.println("Test 1");
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("local");	

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("spike");


			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("type", "temperature");

			DBCursor cursor = table.find(searchQuery);
			
			BasicDBObject obj = new BasicDBObject();
			
			while (cursor.hasNext()) {
			//	System.out.println(cursor.next());
				obj = (BasicDBObject) cursor.next();
				JSONObject out = new JSONObject();
				
				out.put("type", obj.getString("type"));
				out.put("value", obj.getInt("value"));
				out.put("ts", obj.getDate("ts"));
				
				js.put(out);
				
				System.out.println(js.toString());

				
			}
			
			cursor.close();
			
			searchQuery.put("type", "pressure");

			cursor = table.find(searchQuery);
			
			 obj = new BasicDBObject();
			
			while (cursor.hasNext()) {
			//	System.out.println(cursor.next());
				obj = (BasicDBObject) cursor.next();
				JSONObject out = new JSONObject();
				
				out.put("type", obj.getString("type"));
				out.put("value", obj.getInt("value"));
				out.put("ts", obj.getDate("ts"));
				
				js.put(out);
				
				System.out.println(js.toString());

				
			}
			
			cursor.close();
			
			
			searchQuery.put("type", "vibration");

			cursor = table.find(searchQuery);
			
			 obj = new BasicDBObject();
			
			while (cursor.hasNext()) {
			//	System.out.println(cursor.next());
				obj = (BasicDBObject) cursor.next();
				JSONObject out = new JSONObject();
				
				out.put("type", obj.getString("type"));
				out.put("value", obj.getInt("value"));
				out.put("ts", obj.getDate("ts"));
				
				js.put(out);
				
				System.out.println(js.toString());

				
			}
			
			cursor.close();
			
			System.out.println(js.toString());

		}catch(Exception e){
			e.printStackTrace();
		}



		
		JSONObject outputjs = new JSONObject();
		try {
			outputjs.put("output", js);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(201).entity(outputjs.toString()).build();
	}


	@POST

	@Consumes("application/json")
	public Response startSimulation(String data) {

		System.out.println("Inside method");
		String result = data;
		System.out.println("data : " + data);

		try {
			JSONObject input = new JSONObject(data);
			boolean voltage = input.getBoolean("voltage");
			boolean pressure = input.getBoolean("pressure");
			boolean current = input.getBoolean("current");

			IoT.voltage = voltage;
			IoT.pressure = pressure;
			IoT.current = current;

			System.out.println("Voltage:" + voltage + "Pressure :" + pressure);
			System.out.println("Voltage:" + IoT.voltage + "Pressure :" + IoT.pressure);


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.status(201).entity("data1").build();

	}


	public void testmongo(){

		try{
			System.out.println("Test");
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("local");	

			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("iot");

			BasicDBObject doc = new BasicDBObject("title", "MongoDB").
					append("description", "database").
					append("likes", 100).
					append("url", "http://www.tutorialspoint.com/mongodb/").
					append("by", "tutorials point");

			table.insert(doc);
			System.out.println("Document inserted successfully");

		}catch(Exception e){
			e.printStackTrace();
		}


	}

} 