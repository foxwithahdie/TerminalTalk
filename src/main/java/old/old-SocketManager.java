//import java.io.*;
//import java.net.*;
//import java.util.*;
//
//public class SocketManager extends Socket {
//    private Socket socket = null;
//    private PrintWriter out;
//    private BufferedReader in;
//
//    public SocketManager(InetAddress ipAddress, int port) throws IOException {
//        super(ipAddress, port);
//        this.out = new PrintWriter(this.getOutputStream());
//        this.in = new BufferedReader(new InputStreamReader(this.getInputStream()));
//    }
//
//    public SocketManager(Socket socket) throws IOException {
//        this.socket = socket;
//        this.out = new PrintWriter(this.socket.getOutputStream());
//        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
//        System.out.println("runs");
//    }
//
//    public void println(String message) {
//        out.println(message);
//        out.flush();
//    }
//
//    public String readLine() {
//        try {
//            return in.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public void close() throws IOException {
//       this.out.close();
//       this.in.close();
//       if (this.socket == null)
//           super.close();
//       else
//           this.socket.close();
//    }
//}
