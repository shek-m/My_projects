package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    private static class Handler extends Thread{
        private Socket socket;

        public Handler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run() {
            String newClientName = "";
            ConsoleHelper.writeMessage(socket.getRemoteSocketAddress().toString());

            try(Connection connection = new Connection(socket)) {

                newClientName = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, newClientName));       // отправили всем имя клиента
                notifyUsers(connection, newClientName);                                         // получили имена всех учасников чата
                serverMainLoop(connection, newClientName);
                connectionMap.remove(newClientName);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, newClientName));
                ConsoleHelper.writeMessage("The connection with remote server has been closed.");



            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Problem has occurred during data exchange with server.");
            }
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException{
            String name;


            while(true) {
                connection.send(new Message(MessageType.NAME_REQUEST));
                Message newMes = connection.receive();

                if (newMes.getType() != MessageType.USER_NAME){}
                else {
                    name = newMes.getData();
                    break;
                }
            }

            if (name != null && !connectionMap.containsKey(name) && !name.isEmpty()) {
                connectionMap.put(name, connection);
                connection.send(new Message(MessageType.NAME_ACCEPTED));
                return name;
            }
            else
                return serverHandshake(connection);
        }

        private void notifyUsers(Connection connection, String userName) throws IOException{
            for (Map.Entry<String, Connection> client: connectionMap.entrySet()) {
                if (!client.getKey().equals(userName))
                    connection.send(new Message(MessageType.USER_ADDED, client.getKey()));
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException{
            while (true) {
                Message receivedMessage = connection.receive();
                String mes;
                if (receivedMessage.getType() == (MessageType.TEXT)){
                    mes = userName + ": " + receivedMessage.getData();
                    sendBroadcastMessage(new Message(MessageType.TEXT, mes));
                }
                else
                    ConsoleHelper.writeMessage("Error. Message doesn't have any text.");
            }
        }
    }

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(ConsoleHelper.readInt())) {
            System.out.println("Server has started");

            while(true){
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendBroadcastMessage(Message message){
        try {
            for (Map.Entry<String, Connection> pair: connectionMap.entrySet()){
                pair.getValue().send(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}