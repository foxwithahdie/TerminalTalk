package src.main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.AutoCloseable;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

public class Server implements AutoCloseable {
    private static final int maxConnected = 2;
    private static final ArrayList<Integer> prevServers = new ArrayList<>();

    public HashMap<Inet4Address, ArrayList<String>> aliasDatabase;
    public HashMap<Inet4Address, String> messageDatabase;
    private final int port;
    private final ServerSocket serverSocket;
    private final InetSocketAddress serverIPAddress;


    public Server(int port) {
        Gson gson = new Gson();
        this.port = port;

        try {
            this.serverSocket = new ServerSocket(port, maxConnected);
            this.serverIPAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
            this.serverSocket.bind(this.serverIPAddress, maxConnected);
            prevServers.add(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (FileReader fileReader = new FileReader(String.format("src/main/resources/aliases/port%d.json", port))) {
            var json = gson.fromJson(fileReader, HashMap.class);
            this.aliasDatabase = new HashMap<>();
            for (Object key : json.keySet()) {
                this.aliasDatabase.put(
                        ((Inet4Address) InetAddress.getByName((String) key)),
                        (ArrayList<String>) json.get(key)
                );
            }
        } catch (FileNotFoundException notFound) {
            try (FileWriter fileWriter = new FileWriter(String.format("src/main/resources/aliases/port%d.json", port))) {
                fileWriter.write("{}");
                this.aliasDatabase = new HashMap<>();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

        try (FileReader fileReader = new FileReader(String.format("src/main/resources/messages/port%d.json", port))) {
            var json = gson.fromJson(fileReader, HashMap.class);
            for (Object key : json.keySet()) {
                this.messageDatabase.put(
                        ((Inet4Address) InetAddress.getByName((String) key)),
                        (String) json.get(key)
                );
            }
        } catch (FileNotFoundException notFound) {
            try (FileWriter fileWriter = new FileWriter(String.format("src/main/resources/message/port%d.json", port))) {
                fileWriter.write("{}");
                this.aliasDatabase = new HashMap<>();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        throw new UnsupportedOperationException("Not implemented!");
    }

    public static Server findServer(int port) {
        for (Integer prevServer : prevServers)
            if (port == prevServer)
                return new Server(prevServer);

        System.out.println("Server didn't exist; creating new server"); // include ...

        return new Server(port);
    }

    public boolean close() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public int getPort() {
        return this.port;
    }

    public ServerSocket server() {
        return this.serverSocket;
    }

    public InetSocketAddress getServerIPAddress() {
        return this.serverIPAddress;
    }


}
