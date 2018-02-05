package com.yicheng.net;

import lombok.SneakyThrows;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Created by yuer on 2017/4/10.
 */

public class InetAddressExample {
    @SneakyThrows
    public static void main(String[] args) {
        IntStream.range(0,100).forEach(System.out::println);
        System.out.println(BFFind("BBCABABCDABCDABDE","ABC"));
    }

    private static void InetAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()){
            NetworkInterface networkInterface = interfaces.nextElement();
            List<InterfaceAddress> addressList = networkInterface.getInterfaceAddresses();
            addressList.forEach(address ->{
                InetAddress address1 = address.getAddress();
                System.out.println(address1.getHostAddress());
            });
        }
    }

    private static int BFFind(String str,String str1){
        char[] chars = str.toCharArray();
        char[] chars1 = str1.toCharArray();
        int n = chars.length, m=chars1.length;
        for(int i = 0; i<n-m; i++){
            int j = 0;
            while (j<m && chars1[j] == chars[i+j]){
                j = j+1;
            }
            if (j == m){
                return  j;
            }
        }
        return  -1;
    }

}
