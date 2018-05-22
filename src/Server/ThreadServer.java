package Server;

import java.io.*;
import java.net.Socket;
import Enum.*;

public class ThreadServer extends Thread {
    private  int MaxClientConnect = 10;
    private  BufferedWriter os = null;
    private  BufferedReader is = null;
    private Socket SocketOfServer = null;
    private ThreadServer[] list = null;
    private boolean ClientCanFind = false;
    private boolean ClientCanPlay = false;
    private ThreadServer enemy =null;
    private int numberclient = 0;
    private int positionInArray;


    public ThreadServer(Socket skofser, ThreadServer[] list, int vitri) {
        this.list = list;
        this.positionInArray = vitri;
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
                        sendLoginCheck(str,os);
                        break;
                    case LOGOUT:
                       // sendLogout(os);
                        break;
                    case TICK :
                        for(int i = 0 ;i < 10 ;i++){
                            if(list[i] != null && list[i] != this) {
                                enemy.os.write(str);
                                enemy.os.newLine();
                                enemy.os.flush();
                            }

                        }
                        break;
                    case FIND:
                        this.ClientCanPlay = true;
                        boolean flat = true;
                        while(flat){
                            for(int i = 0 ; i< MaxClientConnect ; i++){
                                if (list[i] != null && list[i]!= this && list[i].ClientCanPlay==true){
                                    enemy = list[i];
                                    if(this.positionInArray < i ){
                                        os.write((char) Recv.CANPLAY.ordinal() + "");
                                        os.newLine();
                                        os.flush();
                                    }
                                  //  this.ClientCanPlay=false;
                                    flat = false;
                                    break;
                                }
                            }
                        }

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
    private  void sendLoginCheck(String str, BufferedWriter os) {
        try {

            //xu ly string nhan duoc thanh mang userlogin
            int numofusername = (int) str.charAt(str.length() - 1);
            String UserLogin[] = new String[2];
            UserLogin[0] = str.substring(1, numofusername + 1);
            UserLogin[1] = str.substring(numofusername + 1, str.length() - 1);


            //tao user
            User phuc = new User(UserLogin[0], UserLogin[1], "", "");

            //kiem tra login va gui ve cho client

            if (phuc.checklogin()) {
                //gui lai ve client
                os.write((char) Recv.LOGIN_SUCCESS.ordinal() + "");
                os.newLine();
                os.flush();
            } else {
                os.write((char) Recv.LOGIN_FAIL.ordinal() + "");
                os.newLine();
                os.flush();
            }

            //xet true cho client can play
            ClientCanFind = true;
        }
        catch (IOException e){
        }
    } //ham login
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





}
