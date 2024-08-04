import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.google.gson.Gson;

public class Server implements AutoCloseable {
    private static final int maxConnected = 2;
    public static final ArrayList<Server> prevServers = new ArrayList<>(
            Arrays.stream(new File("src/main/resources/aliases").listFiles())
                    .mapToInt(file -> Integer.parseInt(file.getName()
                            .substring(file.getName().indexOf("port") + "port".length(), file.getName().indexOf('.'))))
                    .mapToObj(Server::new).toList());

    public HashMap<Inet4Address, ArrayList<String>> aliasDatabase;
    public HashMap<Inet4Address, String> messageDatabase;

    private final int port;
    private final ServerSocket serverSocket;
    private final InetSocketAddress serverIPAddress;

    public Server(int port) {

        System.out.println(prevServers);
        Gson gson = new Gson();
        this.port = port;
        
        // local variables for serverSocket and serverIPAddress
        ServerSocket localserverSocket = null;
        InetSocketAddress localserverIPAddress = null;
        
        try {
            localserverSocket = new ServerSocket(port);
            localserverIPAddress = new InetSocketAddress(InetAddress.getLocalHost(), port);
        } catch (IOException e) {
            System.out.println("Server constructor");
            e.printStackTrace();
        } finally {
            this.serverSocket = localserverSocket;
            this.serverIPAddress = localserverIPAddress;
        }

        // database info
        {
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
                    fileWriter.write("""
                            {
                            "127.0.0.1" : ["test", "data"]
                            }""");
                    this.aliasDatabase = new HashMap<>();
                } catch (IOException ioe) {
                    System.out.println("Server constructor: database");
                    ioe.printStackTrace();
                }
            } catch (IOException io) {
                System.out.println("Server constructor: database");
                io.printStackTrace();
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
                try (FileWriter fileWriter = new FileWriter(String.format("src/main/resources/messages/port%d.json", port))) {
                    fileWriter.write("{}");
                    this.aliasDatabase = new HashMap<>();
                } catch (IOException ioe) {
                    System.out.println("Server constructor: database");
                    ioe.printStackTrace();
                }
            } catch (IOException ioe) {
                System.out.println("Server constructor: database");
                ioe.printStackTrace();
            }
        }
    }

    public static Server findServer(int port) {
        for (Server prevServer : Server.prevServers)
            if (prevServer.getPort() == port) {
                return prevServer;
            }

        System.out.println("Server didn't exist; creating new server"); // include ...

        return new Server(port);
    }

    @Override
    public void close() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.out.println("Server.close()");
            e.printStackTrace();
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

    @Override
    public String toString() {
        return String.format("Server: port=%d, ip=%s, socket=%s", this.port, this.serverIPAddress, this.serverSocket);
    }

}
