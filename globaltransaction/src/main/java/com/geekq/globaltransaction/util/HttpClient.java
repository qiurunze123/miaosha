package com.geekq.globaltransaction.util;

import com.geekq.globaltransaction.transactional.GlobalTransactionManager;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClient {

   public static String get(String url){

       String result="";
       CloseableHttpClient client= HttpClients.createDefault();
       HttpGet httpGet=new HttpGet(url);
       httpGet.addHeader("content-type","application/json");
       httpGet.addHeader("groupId", GlobalTransactionManager.getCurrentGroupId());
       try {
          CloseableHttpResponse response= client.execute(httpGet);
          if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
             result= EntityUtils.toString(response.getEntity(),"utf-8");
          }
          response.close();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return  result;
   }

}
