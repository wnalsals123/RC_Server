import javax.net.ssl.*;
import java.io.*;

public class SSLclient {
    public static void main(String[] arg){
        SSLclient SSLc = new SSLclient();
        SSLc.Connection();
//        Client c = new Client();
//        c.start();
    }

    public void Connection(){
        try {
            System.setProperty("javax.net.ssl.trustStore", "C:\\Key\\client\\client.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "12142");
            System.setProperty("javax.net.debug", "ssl");

            // 클라이언트 소켓 팩토리 생성.
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            // 클라이언트 소켓 생성. 1115는 포트 번호.
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 1115);

            // SSL RSA 통신을 통해 서버에 로그인 시도.
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream())));
            out.println("OK");
            out.flush();
            System.out.println("SSL 로그인 성공");

            // 서버와 통신 시도.
            Client c = new Client();
            c.start();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
