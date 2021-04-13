package Chat.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BotClient extends Client{

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        int x = (int)(Math.random()*100);
        return "date_bot_" + x;
    }

    public class BotSocketThread extends SocketThread{
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            super.processIncomingMessage(message);


            String userName = null;
            String mes = null;

            if (message.contains(": ")) {
                userName = message.split(": ")[0];
                mes = message.split(": ")[1];
            }

            if(userName != null && mes !=null && !userName.isEmpty() && !mes.isEmpty()) {

                SimpleDateFormat formatter = new SimpleDateFormat();

                if (mes.equals("дата")) {
                    formatter.applyPattern("d.MM.YYYY");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
                else if (mes.equals("день")){
                    formatter.applyPattern("d");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
                else if (mes.equals("месяц")){
                    formatter.applyPattern("MMMM");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
                else if (mes.equals("год")){
                    formatter.applyPattern("YYYY");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
                else if (mes.equals("время")){
                    formatter.applyPattern("H:mm:ss");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
                else if (mes.equals("час")){
                    formatter.applyPattern("H");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
                else if (mes.equals("минуты")){
                    formatter.applyPattern("m");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
                else if (mes.equals("секунды")){
                    formatter.applyPattern("s");
                    String finalMes = String.format("Информация для %s: %s", userName, formatter.format(new GregorianCalendar().getTime()));
                    sendTextMessage(finalMes);
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new BotClient();
        client.run();
    }
}