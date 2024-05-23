import java.util.*;
import java.io.*;
import java.net.*;

import com.google.gson.Gson;

public class ClientThread extends Thread {

    private SocketManager socket;
    private Client client;
    private String previousMessage;
    private String currentMessage;

    public ClientThread(Client client) {
        this.socket = client.getClientSocket();
        this.client = client;
        this.previousMessage = "";
    }

    @Override
    public void run() {

    }

}
