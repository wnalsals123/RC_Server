import javax.net.ssl.*;
import java.io.*;

public class SSLclient {
    public static void main(String[] arg){
        SSLclient c = new SSLclient();
        c.Connection();
    }

    public void Connection(){
        try {
            System.setProperty("javax.net.ssl.trustStore", "C:\\Key\\client\\client.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "12142");
            System.setProperty("javax.net.debug", "ssl");

            // 소켓에 서버의 IP와 포트 번호를 입력해준다.
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket("localhost", 1115);

            // 서버로 보낼 메시지 설정.
            BufferedReader in = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream())));

            out.println("Hi!");
            out.flush();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
