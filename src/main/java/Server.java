package src.main.java;

import java.net.*;
import java.util.*;
import java.io.*;

import com.google.gson.*;

public class Server {
    private final int port;
    public HashMap<Inet4Address, ArrayList<String>> aliasDatabase;

    public Server(int port) {
        Gson gson = new Gson();
        this.port = port;

        try (FileReader fileReader = new FileReader(String.format("src/main/resources/port%d.json", port))) {
            var json = gson.fromJson(fileReader, HashMap.class);
            this.aliasDatabase = new HashMap<>();
            for (Object key : json.keySet()) {
                this.aliasDatabase.put(
                        ((Inet4Address) InetAddress.getByName((String) key)),
                        (ArrayList<String>) json.get(key));
            }
        } catch (FileNotFoundException notFound) {
            try (FileWriter fileWriter = new FileWriter(String.format("src/main/resources/port%d.json", port))) {
                fileWriter.write("{}");
                this.aliasDatabase = new HashMap<>();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

        throw new UnsupportedOperationException("Not implemented!");
    }

    public static Server findServer(int port) {
        throw new UnsupportedOperationException("Not implemented!");
    }

    public boolean close() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented!");
    }

    public int getPort() {
        return this.port;
    }

}

