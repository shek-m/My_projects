package Chat.client;

import Chat.Connection;
import Chat.ConsoleHelper;
import Chat.Message;
import Chat.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    protected String getServerAddress(){
        ConsoleHelper.writeMessage("Please, type the server IP-address..");
        return ConsoleHelper.readString();
    }

    protected int getServerPort(){
        ConsoleHelper.writeMessage("Please, type the server TCP-port..");
        return ConsoleHelper.readInt();
    }

    protected String getUserName(){
        ConsoleHelper.writeMessage("Please, type your name,,");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread(){
        return new SocketThread();
    }

    protected void sendTextMessage(String text){
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            e.printStackTrace();
            clientConnected = false;
        }
    }

    public void run() {
        SocketThread newThread = getSocketThread();
        newThread.setDaemon(true);
        newThread.start();

        synchronized (this){
            try {
                this.wait();
            } catch (InterruptedException e) {
                ConsoleHelper.writeMessage("Exception has occurred.");
            }
        }

        if (clientConnected){
            ConsoleHelper.writeMessage("Соединение установлено.");
            ConsoleHelper.writeMessage("Для выхода наберите команду 'exit'.");}
        else
            ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");

        while (clientConnected){
            String message = ConsoleHelper.readString();

            if (message.equals("exit"))
                break;

            if (shouldSendTextFromConsole())
                sendTextMessage(message);
        }


    }

    public class SocketThread extends Thread{

        protected void processIncomingMessage(String message){
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName){
            ConsoleHelper.writeMessage("Пользователь под именем " + userName + " присоединился к чату.");
        }

        protected void informAboutDeletingNewUser(String userName){
            ConsoleHelper.writeMessage("Пользователь под именем " + userName + " покинул чат.");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException{
            while (true){
                Message mes = connection.receive();
                if (mes.getType() == MessageType.NAME_REQUEST){
                    connection.send(new Message(MessageType.USER_NAME, getUserName()));
                } else if(mes.getType() == MessageType.NAME_ACCEPTED){
                    notifyConnectionStatusChanged(true);
                    break;
                } else throw new IOException("Unexpected MessageType");

            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException{
            while (true){
                Message mes = connection.receive();

                if (mes.getType() == null)
                    throw new IOException("Unexpected MessageType");

                switch (mes.getType()){
                    case TEXT: processIncomingMessage(mes.getData());
                        break;
                    case USER_ADDED: informAboutAddingNewUser(mes.getData());
                        break;
                    case USER_REMOVED: informAboutDeletingNewUser(mes.getData());
                        break;
                    case USER_NAME:
                    case NAME_REQUEST:
                    case NAME_ACCEPTED:
                        throw new IOException("Unexpected MessageType");
                }
            }
        }

        @Override
        public void run() {
            try {
                connection = new Connection(new Socket(getServerAddress(), getServerPort()));
                clientHandshake();

                clientMainLoop();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                notifyConnectionStatusChanged(false);
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
