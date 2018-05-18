package Server;

import java.io.*;
import java.util.ArrayList;

public class User {
    private String username;
    private String name;
    private String email;
    private String password;
    private int coin;
    private int exp;

    User(String username, String password, String name, String email){
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.coin = 10000;
        this.exp = 0;
    }
    public boolean inputUserToFile() throws IOException { //ham nay kiem tra xem username da ton tai chua, neu chua thi bo vao file va tra ve true, nguoc lai false
        //check xem username co ton tai chua
        FileReader fr = new FileReader("D:\\Data.csv");
        BufferedReader br = new BufferedReader(fr);
        String line="";
        while((line = br.readLine())!= null){
            String ss[] = line.split(",");
            if (ss[0].equals(this.username)){
                //neu username da ton tai thi sai
                fr.close();
                br.close();
                return false;
            }
        }
        //neu chua ton tai thi add vao
        fr.close();
        br.close();
        FileOutputStream fos = new FileOutputStream("D:\\data.csv",true);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(this.username + ',' + this.password + "," + this.name + "," + this.email +","+ this.coin + "," + this.exp);
        pw.close();
        fos.flush();
        fos.close();
        return true;
    }
    public boolean checklogin() throws IOException { //ham nay kiem tra user va pass cua ng dung nhap co dung khong, dung de login
        String user = this.username, pass = this.password;
        FileReader fr = new FileReader("D:\\Data.csv");
        BufferedReader br = new BufferedReader(fr);
        String line="";
        while((line = br.readLine())!= null){
            String ss[] = line.split(",");
            if (ss[0].equals(user) && ss[1].equals(pass)) {
                fr.close();
                br.close();
                return true;
            }
        }
        fr.close();
        br.close();
        return false;
    }
}
