package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import Enum.*;


public class SeverCaro  {
    private  static int MaxClient = 10;
    private static  ThreadServer list [] = new ThreadServer[MaxClient];
    public static void main(String[] args) throws IOException {
        ServerSocket listen ;
        int clientNumber = 0;
        System.out.println("Wait...");
        listen = new ServerSocket(13047);
            while(true){
                try {
                    Socket sock = listen.accept();
                   // System.out.println("qua accept");
                    if(sock !=null){
                        for (int i = 0; i < MaxClient; i++) {
                            if (list[i] == null) {
                                list[i] = new ThreadServer(sock, list,i);
                                list[i].start();
                                break;
                            }
                        }
                    }

                }  catch (IOException e){
                    System.out.print("loi");
                }
            }


    }
}






//    boolean SendLogin(BufferedWriter os, boolean checklogin){
//        try {
//            if (checklogin) {
//                os.write(1);
//                os.flush();
//            } else {
//
//            }
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    }

