import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {
    private static final String ip = "localhost";
    private boolean isReceived = false;
    private boolean alreadySent = false;
    private Socket sock;
    private String msg;
    private String data;

    /**송신 메세지 설정*/
    public Client setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**수신 메세지 Get*/
    public String getData() {
        return data;
    }

    /**수신 여부*/
    public boolean isReceived() {
        return isReceived;
    }

    /**송신 시작*/
    public void send() {
        if (alreadySent) {
            // 이미 보낸 요청
            return;
        }
        new Thread(this).start();

    }

    @Override
    public void run() {
        try {
            //소켓 설정
            InetSocketAddress sock_address = new InetSocketAddress(InetAddress.getLocalHost(), 8080); // 소켓 설정
            sock = new Socket();
            sock.connect(sock_address, 2000);
            //스트림 설정
            Thread.sleep(2000);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sock.getOutputStream())));

            //송신
            out.println(msg);
            out.flush();
//            out.close();

            //중복송신 방지
            alreadySent = true;

            //수신
            data = in.readLine();
            isReceived = true;
            in.close();

            //연결종료
            sock.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}