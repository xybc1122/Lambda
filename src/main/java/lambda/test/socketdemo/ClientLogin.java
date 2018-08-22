package lambda.test.socketdemo;

import lambda.test.doman.User;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientLogin {


    public void LoginStart(String pwd, String userName) {
        Socket socket = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        User user = new User();
        user.setPwd(pwd);
        user.setUserName(userName);
        try {
            //创建一个socket对象(ip,端口)
            socket = new Socket("127.0.0.1", 8888);
            //创建一个字符输出流
            os = socket.getOutputStream();
            //创建一个对象输出流
            oos = new ObjectOutputStream(os);
            //发送到服务端
            oos.writeObject(user);

        } catch (Exception e) {

        } finally {
            try {
                //关闭输出流
                socket.shutdownOutput();
                //####################
                os.close();
                oos.close();
                socket.close();
            } catch (Exception e) {

            }
        }
    }
}



