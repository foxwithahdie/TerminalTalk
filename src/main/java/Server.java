import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import com.google.gson.Gson;

public class Server {
    private ServerSocket socket;
    private int port;
    private InetSocketAddress ip;

    public static final ArrayList<Server> prevServers = new ArrayList<>(
        Arrays.stream(Objects.requireNonNull(new File("src/main/resources/aliases").listFiles()))
                .mapToInt(file -> Integer.parseInt(file.getName()
                        .substring(file.getName().indexOf("port") + "port".length(), file.getName().indexOf('.'))))
                .mapToObj(Server::new).toList());

    public HashMap<Inet4Address, ArrayList<String>> aliasDatabase;
    public HashMap<Inet4Address, String> messageDatabase;

    public Server(int port) {
        Gson gson = new Gson();
        this.port = port;

        try {
            this.socket = new ServerSocket(port);
            this.ip = new InetSocketAddress(InetAddress.getLocalHost(), port);
        } catch (IOException e) {
            System.out.println("ServerSocket did not instantiate, or IP Address did not instantiate");
            throw new RuntimeException(e);
        }

        // database info
        try (FileReader fileReader = new FileReader(String.format("src/main/resources/aliases/port%d.json", port))) {
            var json = gson.fromJson(fileReader, HashMap.class);
            this.aliasDatabase = new HashMap<>();
            for (Object key : json.keySet()) {
                this.aliasDatabase.put(
                        ((Inet4Address) InetAddress.getByName((String) key)),
                        (ArrayList<String>) json.get(key)
                );
            }
        } catch (FileNotFoundException _s) {
            try (FileWriter fileWriter = new FileWriter(String.format("src/main/resources/aliases/port%d.json", port))) {
                fileWriter.write("""
                        {
                        "127.0.0.1" : ["test", "data"]
                        }""");
                this.aliasDatabase = new HashMap<>();
            } catch (IOException ioe) {
                System.out.println("Error with FileWriter");
                throw new RuntimeException(ioe);
            }
        } catch (IOException io) {
            System.out.println("Error with FileReader and is not that the file is not found");
            throw new RuntimeException(io);
        }

        try (FileReader fileReader = new FileReader(String.format("src/main/resources/messages/port%d.json", port))) {
            var json = gson.fromJson(fileReader, HashMap.class);
            for (Object key : json.keySet()) {
                this.messageDatabase.put(
                        ((Inet4Address) InetAddress.getByName((String) key)),
                        (String) json.get(key)
                );
            }
        } catch (FileNotFoundException _) {
            try (FileWriter fileWriter = new FileWriter(String.format("src/main/resources/messages/port%d.json", port))) {
                fileWriter.write("{}");
                this.aliasDatabase = new HashMap<>();
            } catch (IOException ioe) {
                System.out.println("Error with FileWriter");
                throw new RuntimeException(ioe);
            }
        } catch (IOException ioe) {
            System.out.println("Error with FileReader that is not that the file is not found");
            throw new RuntimeException(ioe);
        }
    }

    public static Server findServer(int port) {
        for (Server prevServer : Server.prevServers)
            if (prevServer.getPort() == port) {
                return prevServer;
            }

        System.out.println("Server didn't exist; creating new server...");

        return new Server(port);
    }

    public void start() {

    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            System.out.println("Server socket could not close");
            throw new RuntimeException(e);
        }
    }

    public void serverSend() {

    }

    public void serverReceive() {

    }

    public void receiveConnection() {

    }

    public void log() {

    }

    public ServerSocket getSocket() {
        return this.socket;
    }

    public int getPort() {
        return this.port;
    }

    public InetSocketAddress getIp() {
        return this.ip;
    }

    public void kick(Client client) {

    }

    public static class MessageSender implements Runnable {
        public void run() {

        }
    }

    public static class MessageReceiver implements Runnable {
        public void run() {

        }
    }

    public static class ConnectionHandler implements Runnable {
        public void run() {

        }
    }

    @Override
    public String toString() {
        return String.format("Server(port=%d, ip=%s, socket=%s)", this.port, this.ip, this.socket);
    }
}
