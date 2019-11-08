import java.io.*;
import java.net.Socket;

public class Client { // 임시 테스트용
    public static void main(String[] arg) {
        Socket socket = null;            //Server와 통신하기 위한 Socket
        BufferedReader in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림
        BufferedReader in2 = null;        //키보드로부터 읽어들이기 위한 입력스트림
        PrintWriter out = null;            //서버로 내보내기 위한 출력 스트림

        try {
            socket = new Socket("127.0.0.1", 12142);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in2 = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            int i = 0;
            do {
                System.out.print("서버로 보낼 메시지 : ");
                String data = in2.readLine();            //키보드로부터 입력
                out.println(data);                        //서버로 데이터 전송
                out.flush();
            }while (i++ < 10);
            String str2 = in.readLine();            //서버로부터 되돌아오는 데이터 읽어들임
            System.out.print("서버로부터 되돌아온 메세지 : " + str2);
        }catch(Exception e) {}
    }
}
