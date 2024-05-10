package src.main.java;

import java.net.*;
import java.util.*;

public class Client {
    private String alias;
    private final Inet4Address ipAddress;
    private int port;
    private Server cliServer;

    public Client() throws UnknownHostException {
        this.alias = "Anon";
        this.port = -1;
        this.ipAddress = (Inet4Address) Inet4Address.getLocalHost();
        throw new UnsupportedOperationException("Not implemented!");
    }

    public Client(String alias) throws UnknownHostException {
        this.alias = alias;
        this.port = -1;
        this.ipAddress = (Inet4Address) Inet4Address.getLocalHost();
        throw new UnsupportedOperationException("Not implemented!");
    }

    public void changeAlias(String alias) {
        this.alias = alias;
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
            cliServer = Server.findServer(port);
            if (!cliServer.aliasDatabase.get(ipAddress).contains(this.alias)) {
                cliServer.aliasDatabase.get(ipAddress).add(this.alias);
            }
            return true;
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public void disconnect() {
        cliServer = null;
        this.port = -1;
    }
    
}
