package com.ascendcorp.pushnotification;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<RequestObject, String> {

    public String handleRequest(RequestObject input, Context context) {
    	System.out.println("Input: " + input);
    	
    	Connection conn = null;
//    	String connectionString = System.getenv("connection_string");
    	String connectionString = "jdbc:mysql://dbone.cbvpxlkpqfey.ap-southeast-1.rds.amazonaws.com/test?"
        + "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&user=root&password=rootroot";

        try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(connectionString);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
    	String sqlString = "SELECT * FROM test.device_profile "
    			+ "WHERE phone = '" + input.getPhone() + "' ";
    	
    	//String device_id = exceuteSql(sqlSelect, conn);
    	Statement stmt = null;
    	ResultSet rs = null;
    	String device_id = null;
    	String os = null;

    	try {
    	    
    	    stmt = conn.createStatement();
    	    if(sqlString.startsWith("SELECT"))
    	    	rs = stmt.executeQuery(sqlString);
    	    
    	    while (rs != null && rs.next()) {
                device_id = rs.getString("device_id");
                os = rs.getString("os");
            }
    	    
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (rs != null) {
		        try { rs.close(); } 
		        catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try { stmt.close(); } 
		        catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		    if (conn != null) {
		        try { conn.close(); } 
		        catch (SQLException sqlEx) { } // ignore
		        conn = null;
		    }
		}
	    
//    	if(device_id == null || device_id.isEmpty()) return new ResponseObject(input, "Device ID not found!");
    	
		System.out.println("Device ID: " + device_id);
		
		sendPushNotification(device_id, input.getTextMessage());
    	
        return "SUCCESS";
    }
    
    public static void main(String[] args) {
		LambdaFunctionHandler test = new LambdaFunctionHandler();
		RequestObject input = new RequestObject();
		input.setPhone("308149");
		input.setTextMessage("Hellooooooo!!!");
		test.handleRequest(input, null);
	}
    
	public void sendPushNotification(String device_id, String message) {
		try {

			String url = "https://fcm.googleapis.com/fcm/send";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			//add reuqest header
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Authorization", "key=AIzaSyDLB2rnExdiSWLL5UqfsTZe-2ihJEYYZIg");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			JSONObject jPayload = new JSONObject();
			jPayload.put("to", device_id);
			JSONObject notification = new JSONObject();
			notification.put("title", "True Money Wallet");
			notification.put("text", message);
			notification.put("sound", "default");
			notification.put("badge", "1");
			notification.put("click_action", "OPEN_ACTIVITY_1");
			jPayload.put("notification", notification);
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jPayload.toString());
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + jPayload.toString());
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			System.out.println(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
