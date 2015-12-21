package resumeupdater;

/**
 * 
 * @author SteshenkoMA
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//Данный класс посылает POST запрос на обновление резюме


public  class ResumeUpdater implements Runnable {
    
   private final String resumeID;
   
   ResumeUpdater (String resumeID) {
   this.resumeID = resumeID;
   }
          
   @Override
   public void run() {              
   
   try{
	String url = "http://spb.hh.ru/applicant/resumes/touch";
	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	//Добавляем заголовок
        
	con.setRequestMethod("POST");
                                
        //Считываем данные из PostRequest.properties
        
        Properties  RequestConfig = new Properties();
        try {
        RequestConfig.load(new FileInputStream("PostRequest.properties"));
        } catch (IOException e) {
        }
        
        //Записываем их в HashMap hm
        
        Map<String, String> hm = new HashMap<String, String>();
           for(String key : RequestConfig.stringPropertyNames()) {
           String value = RequestConfig.getProperty(key);
           hm.put(key,value);
        }
        
        //Задаем параметры запроса 
           
        for (Map.Entry<String,String> entry : hm.entrySet()) {
           con.setRequestProperty(entry.getKey(), entry.getValue());
        }
        
	String urlParameters = "resume="+resumeID+"&undirectable=true";
		
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(urlParameters);
	wr.flush();
	wr.close();
          
	//Выводим результаты запроса 
        
	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + urlParameters);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = new BufferedReader(
	new InputStreamReader(con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
	    response.append(inputLine);
	}
	in.close();
		
	System.out.println(response.toString());
        System.out.println("Response for ID = "+ resumeID +" Done at "+System.currentTimeMillis());

}
catch (Exception e) {

}

	}
    
}
