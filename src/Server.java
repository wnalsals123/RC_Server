import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    int id_count = 0;
    Socket[] sock = new Socket[3];
    ServerSocket s_sock = null;
    ServerThread[] st = new ServerThread[3];

    public static void main(String[] arg){
        Server s = new Server();
    }

    public Server() {
        try {
            s_sock = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println("연결 대기중...");
            st[id_count] = new ServerThread(id_count, sock[id_count]);
            System.out.println("연결 성공 ID: " + id_count);
            st[id_count].start();
            id_count++;
        }
    }

    public class ServerThread extends Thread {
        int id;
        Socket sock = null;
        BufferedReader in = null;            //Client로부터 데이터를 읽어들이기 위한 입력스트림
        PrintWriter out = null;                //Client로 데이터를 내보내기 위한 출력 스트림

        public ServerThread(int id, Socket sock){
            this.id = id;
            this.sock = sock;
            try {
                sock = s_sock.accept();
                in = new BufferedReader(new InputStreamReader(sock.getInputStream())); //입력스트림 생성
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()))); //출력스트림 생성
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void InputMsg(){
            String str = null;
            try {
                str = in.readLine();                //Client로부터 데이터를 읽어옴
                System.out.println("Client로 부터 온 메세지 : " + str);
                Broadcast(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void PushMsg(int i, String msg){
            st[i].out.println(msg);                        //서버로 데이터 전송
            st[i].out.flush();
        }

        public void Broadcast(String msg){
            for(int i = 0; i < id_count; i++){
                PushMsg(i, msg);
            }
        }

        public void run(){
            while (true){
                InputMsg();
            }
        }
    }
}
