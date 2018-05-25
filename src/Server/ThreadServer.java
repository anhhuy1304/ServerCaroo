package Server;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import Enum.*;

public class ThreadServer extends Thread {
    private  int MaxClientConnect = 10;
    private  BufferedWriter os = null;
    private  BufferedReader is = null;
    private Socket SocketOfServer = null;
    private ThreadServer[] list = null;
    private boolean ClientCanPlay = false;
    private ThreadServer enemy =null;
    private int numberclient = 0;
    private static Queue<ThreadServer> QList;
    private  String Username="";


    public ThreadServer(Socket skofser, ThreadServer[] list, Queue<ThreadServer> Qlist) {
        this.list = list;
        this.QList = Qlist;
        this.SocketOfServer = skofser;
        System.out.println("new connection with client #" + this.numberclient + "at " + this.SocketOfServer);
    }

    public void run() {
        //mo luong vao ra tren socket tai server
        try {
            //luong vao
            is = new BufferedReader(new InputStreamReader(SocketOfServer.getInputStream()));
            //luong ra
            os = new BufferedWriter(new OutputStreamWriter(SocketOfServer.getOutputStream()));

            String str = null;
            while ((str = is.readLine()) != null) {
                Send s = Send.values()[str.charAt(0)];
                switch (s) {
                    case LOGIN:
                        this.Username = str.substring(1,str.length());
                        break;
                    case TICK :
                        enemy.os.write(str);
                        enemy.os.newLine();
                        enemy.os.flush();
                        break;
                    case FIND:
                        QList.add(this);
                        break;
                    case IS_LOSE:
                        enemy.os.write((char) Recv.IS_LOSE.ordinal() +"");
                        enemy.os.newLine();
                        enemy.os.flush();
                        break;
                    case QUIT:
                        for(int i=0; i<MaxClientConnect; i++) {
                            if(list[i] == this) {
                                list[i] = null;
                                os.close();
                                is.close();
                                SocketOfServer.close();
                            }
                        }
                        break;

                }
            }
        } catch (IOException e) {
        }
    }
//    private void sendLogout(BufferedWriter os){ //ham logout
//        try {
//            ClientCanPlay = false;
//            os.write((char)Recv.);
//            os.newLine();
//            os.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private void sendRegister (String str, BufferedWriter os){
//        // xu ly chuoi
//        String xuly = str.substring(1,str.length());
//        String ss[] = xuly.split(" ");
//        String sstr[]
//
//
//    }
    public void setenemy( ThreadServer enemy ){
        try {
            this.enemy = enemy;
            System.out.println("da set");
            os.write((char) Recv.ENEMY.ordinal() + enemy.Username);
            os.newLine();
            os.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void sendMessage(){

        try {

            os.write((char)Recv.CANPLAY.ordinal()+"");
            os.newLine();
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
