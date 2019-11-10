import java.io.*;
import java.net.*;

public class client {
    Socket sock = null;
    BufferedReader in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림
    BufferedReader in2 = null;        //키보드로부터 읽어들이기 위한 입력스트림
    PrintWriter out = null;            //서버로 내보내기 위한 출력 스트림
    InputThread i = new InputThread();
    OutputThread o = new OutputThread();

    public static void main(String arg[]){
        client c = new client();
    }

    public client(){
        try {
            sock = new Socket("127.0.0.1", 8888);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            in2 = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));
            ThreadStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ThreadStart(){
        i.start();
        o.start();
    }

    public class InputThread extends Thread{
        public void run(){
            while (true){
                try {
                    String data = in.readLine();
                    System.out.print("서버로 온 출력 : " + data + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public class OutputThread extends Thread{
        public void run(){
            while (true){
                try {
                    Thread.sleep(300);
                    System.out.print("입력 : ");
                    String data = in2.readLine();            //키보드로부터 입력
                    out.println(data);                        //서버로 데이터 전송
                    out.flush();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}