import javax.net.ssl.*;
import java.io.*;

public class SSLserver {
    public static void main(String[] arg){
        SSLserver SSLs = new SSLserver();
        SSLs.SSL_Connection();
    }

    public void SSL_Connection(){
        try{
            // KeyStore 툴을 이용해서 만든 파일 설정.
            System.setProperty("javax.net.ssl.keyStore", "C:\\Key\\server\\server.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "12142");
            System.setProperty("javax.net.debug", "ssl");

            // 서버 소켓 팩토리 생성.
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

            // 서버 소켓 생성. 1115는 포트 번호.
            SSLServerSocket sslserversocket = (SSLServerSocket)sslserversocketfactory.createServerSocket(1115);

            // SSL RSA 통신을 통해 서버에 로그인.
            System.out.println("SSL Client Wait...");
            while(true){
                SSLSocket socket = (SSLSocket)sslserversocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String fromClient = in.readLine();
                if(fromClient.equals("OK")) { // 로그인 성공 시
                    System.out.println("SSL 로그인 성공");
                    sslserversocket.close();
                    break;
                }
            }

            // 클라이언트와 통신 시작.
            Server s = new Server();
            s.Connection();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
