import java.io.*;
import java.net.Socket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {
    private String alias;
    private int port;
    private Socket socket;
    private Inet4Address ip;
    private Server connection;

    public Client() {
        try {
            this.ip = (Inet4Address) InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Client IP not functional.");
            throw new RuntimeException(e);
        }

        this.port = -1;
    }

    public void connect() {

    }

    public void disconnect() {

    }

    public Inet4Address getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public Socket socket() {
        return this.socket;
    }

    public Server getConnectedServer() {
        return null;
    }

    public String getAlias() {
        return this.alias;
    }

    public void changeAlias(String newAlias) {
        this.alias = newAlias;
    }

    public static class MessageReceiver implements Runnable {
        public void run() {

        }
    }

    public static class MessageSender implements Runnable {
        public void run() {

        }
    }

}
