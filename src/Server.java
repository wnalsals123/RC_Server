import java.io.*;
import java.net.*;

public class Server {
    private int id_count = 0;
    private ServerSocket s_sock;
    private Socket[] sock = new Socket[3];
    private ServerThread[] RC_Net = new ServerThread[3];

    public static void main(String[] arg){
        Server s = new Server();
        s.connect();
    }

    public void connect(){
        try {
            s_sock = new ServerSocket(12142);
        }catch (IOException e){
            System.out.println("해당 포트가 사용중입니다.");
        }
        while(true){
            try{
                System.out.println("연결 대기...");
                sock[id_count] = s_sock.accept();
                RC_Net[id_count] = new ServerThread(id_count);
                RC_Net[id_count++].start();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public class ServerThread extends Thread {
        private int id;
        private boolean ing = true;
        private BufferedReader in = null;   //Client로부터 데이터를 읽어들이기 위한 입력스트림
        private PrintWriter out = null;     //Client로 데이터를 내보내기 위한 출력 스트림

        public ServerThread(int id){
            this.id = id;
            System.out.println("연걸완료 id: " + id);
        }

        @Override
        public void run() {
            try {
                String line = null;
                in = new BufferedReader(new InputStreamReader(sock[id].getInputStream())); //입력스트림 생성
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock[id].getOutputStream()))); //출력스트림 생성
                while(ing) {
                    line = in.readLine();
                    if(line.equals("quit"))
                        break;
                    System.out.println(line);
                    BroadCast(line);
                }
            }catch(Exception e) {
                System.out.println(e);
                System.out.println("connection lost");
                try {
                    sock[id].close();
                } catch (IOException g) {
                    g.printStackTrace();
                } finally {
                    BroadCast("lost");
                }
            }
        }

        public void PushMsg(int id, String msg){
            RC_Net[id].out.println(msg);
            RC_Net[id].out.flush();
        }

        public void BroadCast(String msg){
            for(int i = 0; i < id_count; i++)
                PushMsg(i, msg);
        }
    }
}
