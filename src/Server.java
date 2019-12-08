import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private ServerSocket s_sock = null;
    private Socket sock = null;
    private BufferedReader in = null;            //Client로부터 데이터를 읽어들이기 위한 입력스트림

    public void Connection() {
        System.out.println("Client Wait...");
        try {
            s_sock = new ServerSocket(1115);
            sock = s_sock.accept();
            System.out.println("클라이언트 연결 성공");
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            start();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        String line = null;
        while(true){
            try {
                line = in.readLine();
                System.out.println("클라이언트로 온 메시지: " + line);
                if(line == null || line.equals("exit"))
                    break;
            } catch (IOException e) {
                System.out.println(e);
                break;
            }
        }
    }
}
