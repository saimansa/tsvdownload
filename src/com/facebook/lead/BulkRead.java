package com.facebook.lead;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import sun.net.www.protocol.https.HttpsURLConnectionImpl;

public class BulkRead {

	public static final String access_token = "EAAFNsBlN4joBABxNuw8kvaIWwEJrDYbC8ckASEypxPfCVdK5RmPEPotIXedp4z6J1J1YLEKPDuWhZCrIlZCKZCPNJm85TmAYYFIZCCz2ohsxhbvqDKo4RGk5icZCpsc43wZCOvyWlJigIalx3k7cOmuS6ZAok3wTTWxbr1kkZChvHOZBixeZA67KZBHGGtPBXlZA5uM1Mw6gOdHLXkKmHfbsdqtEnoOCQ6ZBwzIZAIBag5XQHAfgZDZD";
	private static final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			URL url = new URL("https://graph.facebook.com/v3.2/277385353205976/leads?access_token="+access_token);

			HttpsURLConnectionImpl con = (HttpsURLConnectionImpl) url.openConnection();
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			con.disconnect();
			System.out.println(content);
			ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
			writer.writeValue(new File("D:/facebookwebhook/leadInfo.json"), mapper.readValue(content.toString(), new TypeReference<Map<String, Object>>() { }));

		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
