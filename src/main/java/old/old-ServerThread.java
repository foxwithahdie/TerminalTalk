//import java.io.IOException;
//
//public class ServerThread extends Thread {
//    private Server server;
//    private int port;
//    private boolean handlingRequests;
//
//    public ServerThread(int port) {
//        this.port = port;
//        this.handlingRequests = false;
//    }
//
//    @Override
//    public void run() {
//        this.handlingRequests = true;
//        while (this.handlingRequests) {
//            try {
//                System.out.println("Indicator  -  connection");
//                SocketManager socket = new SocketManager(this.server.server().accept());
//                RequestHandler rqHandler = new RequestHandler(socket);
//                rqHandler.start();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void startServer() {
//        try {
//            this.server = Server.findServer(this.port);
//            this.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void close() {
//        this.server.close();
//        this.handlingRequests = false;
//        this.interrupt();
//    }
//
//    public static void main(String[] args) {
//        ServerThread server = new ServerThread(4567);
//        server.startServer();
//        try {
//            Thread.sleep(60000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("happened - before close");
//        server.close();
//    }
//}
//
//class RequestHandler extends Thread {
//
//    private final SocketManager socket;
//
//    public RequestHandler(SocketManager socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Socket " + this.socket + " connected");
//
//        socket.println("> Server: Welcome!");
//
//        String line = this.socket.readLine();
//        while (line != null && !line.isEmpty()) {
//            this.socket.println("> Server: " + line);
//            line = socket.readLine();
//        }
//
//        try {
//            socket.close();
//            System.out.println("closed");
//        } catch (IOException e) {
//           e.printStackTrace();
//        }
//    }
//
//}