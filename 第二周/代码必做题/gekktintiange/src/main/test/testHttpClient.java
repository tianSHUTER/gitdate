import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class testHttpClient {

    @Test

    public void doGetTestOne(){
//        游客户端执行get请求
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //创建get请求
        HttpGet httpGet = new HttpGet("http://localhost:8080/do");

        //相应模型
        CloseableHttpResponse response = null;
        try{
            //将服务端的get请求得来的内容转化为response类
            response = httpClient.execute(httpGet);
            //响应的内容
            HttpEntity responseEntity = response.getEntity();

            //由response类得到get请求中的信息内容
            System.out.println("响应状态为："+response.getStatusLine());
            if (responseEntity!=null){
                System.out.println("响应内容长度为"+responseEntity.getContentLength());
                System.out.println("相应内容为："+ EntityUtils.toString(responseEntity));
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if (httpClient!= null){
                    httpClient.close();
                }
                if (response !=null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }
