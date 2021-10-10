package io.tianger;

import io.tianger.inbound.HttpinboundHander;

import java.lang.reflect.Array;
import java.util.Arrays;

public class nettyHttyServer
{public final static String  GATEWAY_NAME="NIOGateway";
    public final static String  GATEWAY_version="3.0.0";

    public static void main(String[] args) {
       String proxyPort= System.getProperty("proxyPort","8888");

       String proxyServers=System.getProperty
               ("proxyServers","http://localhost:8801,http://localhost:8801");
       int port =Integer.parseInt(proxyPort);

       HttpinboundHander server=new HttpinboundHander(port, Arrays.asList(proxyServers.split(",")));

       try{
           server.run();
       }catch (Exception ex){
           ex.printStackTrace();
       }
    }
}
