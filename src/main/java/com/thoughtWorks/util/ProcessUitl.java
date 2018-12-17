package com.thoughtWorks.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
/*该类用以解决CMD命令运行，wait超时问题*/
public class ProcessUitl implements Runnable{
    public String getErrStr() {
        return errStr;
    }
 
 
    public void setErrStr(String errStr) {
        this.errStr = errStr;
    }
 
 
    //设置读取的字符编码
    private String character = "utf-8";
    private InputStream inputStream;
    private String errStr = null;
    
    public ProcessUitl(InputStream inputStream) {
        this.inputStream = inputStream;
    }
 
    @Override
    public void run() {
        BufferedReader br = null;
        try { 
            br = new BufferedReader(new InputStreamReader(inputStream, character));//以utf-8的编码格式去读取文件
            String line = null;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            errStr = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public void start(){
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }
/*    public static void main(String[] args) {
        String command = "ping www.baid.com";
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(command);
            ProcessUitl pu = new ProcessUitl(ps.getErrorStream());
            ps.getOutputStream().close();
            ps.waitFor();
            String errorStr = pu.getErrStr();
            if(errorStr != null){
                System.out.println("Error!!!!!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            if(ps != null){
                ps.destroy();
            }
        }
        
 
    }*/
 
}
