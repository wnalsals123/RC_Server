import javax.net.ssl.*;
import java.io.*;

public class SSLserver {
    public static void main(String[] arg){
        SSLserver s = new SSLserver();
        s.Connection();
    }

    public void Connection(){
        try{
            System.setProperty("javax.net.ssl.keyStore", "C:\\Key\\server\\server.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "12142");
            System.setProperty("javax.net.debug", "ssl");

            // 서버 소켓 팩토리 생성.
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

            // 서버 소켓 생성. 1115는 포트 번호.
            SSLServerSocket sslserversocket = (SSLServerSocket)sslserversocketfactory.createServerSocket(1115);
            System.out.println("Wating Connection");

            // 클라이언트가 언제 접속할지 모르니 항상 대기.
            while(true){
                SSLSocket socket = (SSLSocket)sslserversocket.accept();
                // 데이터 읽는 부분은 쓰레드로 처리.
                ThreadServer thread = new ThreadServer(socket);
                thread.start();
            }

        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public class ThreadServer extends Thread {

        private SSLSocket socket;
        private BufferedReader in = null;            //Client로부터 데이터를 읽어들이기 위한 입력스트림
        private PrintWriter out = null;                //Client로 데이터를 내보내기 위한 출력 스트림

        public ThreadServer(SSLSocket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                String fromClient = null;
                in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //입력스트림 생성
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))); //출력스트림 생성

                fromClient = in.readLine();
                System.out.println(fromClient);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
