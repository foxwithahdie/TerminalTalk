import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    private String alias;
    private final Inet4Address ipAddress;

    private SocketManager clientSocket;
    private int port;
    private Server cliServer;

    public Client() {
        Inet4Address ipAddress;
        this.alias = "Anon";
        this.port = -1;
        try {
            ipAddress = (Inet4Address) InetAddress.getLocalHost();
        } catch (UnknownHostException uhe) {
            System.out.println("Client constructor");
            uhe.printStackTrace();
            ipAddress = null;
        }
        this.ipAddress = ipAddress;
    }

    public Client(String alias) {
        Inet4Address ipAddress;
        this.alias = alias;
        this.port = -1;
        try {
            ipAddress = (Inet4Address) InetAddress.getLocalHost();
        } catch (UnknownHostException uhe) {
            System.out.println("Client constructor");
            uhe.printStackTrace();
            ipAddress = null;
        }
        this.ipAddress = ipAddress;
    }

    public void connect(int port) {
        this.port = port;
        try {

            this.cliServer = Server.findServer(port);

            if (cliServer.aliasDatabase.get(this.ipAddress) != null && !cliServer.aliasDatabase.get(this.ipAddress).contains(this.alias))
                cliServer.aliasDatabase.get(this.ipAddress).add(this.alias);
            else
                cliServer.aliasDatabase.put(this.ipAddress, new ArrayList<String>(Arrays.asList(this.alias)));

            this.clientSocket = new SocketManager(ipAddress, this.port);


            if (!this.clientSocket.isConnected())
                this.clientSocket.connect(this.cliServer.getServerIPAddress());


        } catch (Exception e) {
            System.out.println("Client.connect()");
            e.printStackTrace();

        }
    }

    public void disconnect() {
        try {
            this.clientSocket.close();
            this.cliServer = null;
            this.port = -1;

        } catch (IOException ioe) {
            System.out.println("Client.disconnect()");
            ioe.printStackTrace();
        }
    }

    public void send(String message) {

    }

    public String receive() {
        return "";
    }

    public void changeAlias(String alias) {
        this.alias = alias;
        if (this.cliServer != null)
            if (!this.cliServer.aliasDatabase.get(this.ipAddress).contains(alias))
                this.cliServer.aliasDatabase.get(this.ipAddress).add(alias);
    }

    public String getAlias() {
        return this.alias;
    }

    public String getPreviousAlias(int index) {
        try {
            return this.cliServer.aliasDatabase.get(this.ipAddress).get(index);
        } catch (ArrayIndexOutOfBoundsException indexException) {
            return null;
        }
    }

    public SocketManager getClientSocket() {
        return this.clientSocket;
    }

    public Server getClientServer() {
        return this.cliServer;
    }
}
