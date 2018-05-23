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
import java.util.LinkedList;
import java.util.Queue;

import Enum.*;


public class SeverCaro extends Thread {
    private  static int MaxClient = 10;
    private static  ThreadServer list [] = new ThreadServer[MaxClient];
    private static Queue<ThreadServer> Qlist   = new LinkedList<>();
    public static void main(String[] args) throws IOException {
        ServerSocket listen ;
        int clientNumber = 0;
        System.out.println("Wait...");
        listen = new ServerSocket(13047);
        new SeverCaro().start();
            while(true){
                try {
                    Socket sock = listen.accept();

                   // System.out.println("qua accept");
                    if(sock !=null){
                        for (int i = 0; i < MaxClient; i++) {
                            if (list[i] == null) {
                                list[i] = new ThreadServer(sock, list,Qlist);
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
    @Override
    public void run(){
        while (true) {
            int size = Qlist.size();
            size +=0;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(size >1 ){
                ThreadServer user1 = Qlist.remove();
                ThreadServer user2 = Qlist.remove();
                user1.setenemy(user2);
                user2.setenemy(user1);
                user1.sendMessage();
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

