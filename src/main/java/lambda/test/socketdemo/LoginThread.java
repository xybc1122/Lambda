package lambda.test.socketdemo;

import lambda.test.doman.User;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class LoginThread extends Thread {
    Socket socket = null;

    public LoginThread(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        InputStream is = null;
        ObjectInputStream ois = null;
        try {
            //打开输入流，处理用户请求
            is = socket.getInputStream();
            //字节转字符 buff包装字符   ois = new ObjectInputStream(is);
            ois = new ObjectInputStream(is);
            User user = (User) ois.readObject();
            System.out.println("info:" + user.getUserName() + "===" + user.getPwd());

            //关闭资源


        } catch (Exception e) {

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (ois != null) {
                    ois.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {

            }

        }
    }


}
