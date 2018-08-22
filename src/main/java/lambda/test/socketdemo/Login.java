package lambda.test.socketdemo;

import lambda.test.doman.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Login {

  public static void main(String [] args){
      ClientLogin clientLogin =new ClientLogin();
      clientLogin.LoginStart("abc","Tom");
      clientLogin.LoginStart("123456","jack");
  }

}
