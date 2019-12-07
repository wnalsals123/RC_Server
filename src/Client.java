import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private Socket sock = null;
    private PrintWriter out = null;
    private String msg = null;

    @Override
    public void run() {
        try {
            //소켓 설정
            InetSocketAddress sock_address = new InetSocketAddress(InetAddress.getLocalHost(), 1115); // 소켓 설정
            sock = new Socket();
            sock.connect(sock_address, 2000);
            System.out.println("서버 연결 성공");

            //스트림 설정
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));

            // 서버와 통신
            while (true){
                Scanner scan = new Scanner(System.in);
                System.out.print("서버에 보낼 메시지: ");
                msg = scan.nextLine();
                out.println(msg);
                out.flush();
                if(msg.equals("exit"))
                    break;
            }

            //연결종료
            sock.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}