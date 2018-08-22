package lambda.test.socketdemo;

import java.net.ServerSocket;
import java.net.Socket;

public class ServiceSocket {


    public static void main(String[] args) {
        LoginThread loginThread = null;
        ServerSocket serverSocket=null;
        try {
            //创建一个ServiceSocket对象 指定端口号
            serverSocket = new ServerSocket(8888);
            while (true) {
                //监听客户端请求
                Socket socket = serverSocket.accept();
                loginThread = new LoginThread(socket);
                loginThread.start();
            }

        } catch (Exception e) {

        }


    }
}
