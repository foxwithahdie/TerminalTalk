package src.main.java;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private String alias;
    private final Inet4Address ipAddress;
    private Socket clientSocket;
    private int port;
    private Server cliServer;

    public Client() throws UnknownHostException, UnsupportedOperationException {
        this.alias = "Anon";
        this.port = -1;
        this.ipAddress = (Inet4Address) Inet4Address.getLocalHost();
    }

    public Client(String alias) throws UnknownHostException, UnsupportedOperationException {
        this.alias = alias;
        this.port = -1;
        this.ipAddress = (Inet4Address) Inet4Address.getLocalHost();
    }

    public void changeAlias(String alias) {
        this.alias = alias;
        if (this.cliServer != null)
            if (!this.cliServer.aliasDatabase.get(ipAddress).contains(alias))
                this.cliServer.aliasDatabase.get(ipAddress).add(alias);
    }

    public String getAlias() {
        return this.alias;
    }

    public String getPreviousAlias(int index) {
        try {
            return this.cliServer.aliasDatabase.get(ipAddress).get(index);
        } catch (ArrayIndexOutOfBoundsException indexException) {
            return "";
        }
    }

    public boolean connect(int port) {
        this.port = port;
        try {

            this.cliServer = Server.findServer(port);

            if (!cliServer.aliasDatabase.get(ipAddress).contains(this.alias))
                cliServer.aliasDatabase.get(ipAddress).add(this.alias);

            this.clientSocket = this.cliServer.server().accept();

            if (!this.clientSocket.isConnected())
                this.clientSocket.connect(this.cliServer.getServerIPAddress());

            return this.clientSocket.isConnected();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        }
    }

    public boolean disconnect() {
        try {
            this.clientSocket.close();
            this.cliServer = null;
            this.port = -1;
            return this.clientSocket.isClosed();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            return this.clientSocket.isClosed();

        }
    }

    public boolean send(String message) {
        try (OutputStreamWriter writer = new OutputStreamWriter(this.clientSocket.getOutputStream());
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public String receive() {
        try (InputStreamReader reader = new InputStreamReader(this.clientSocket.getInputStream());
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
