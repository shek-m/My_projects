package customFileEncryption;

import java.io.*;

public class customEncryptor {

        public static void main(String[] args) throws IOException {

            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(args[1]));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(args[2]));
            byte[] buff = new byte[inputStream.available()];

            if(buff.length ==1) {
                if (args[0].equals("-e")) {
                    int i =  inputStream.read();
                    outputStream.write(i+1);
                }
                if (args[0].equals("-d")) {
                    int i = inputStream.read();
                    outputStream.write(i-1);
                }
            }
            else if (buff.length>0){
                if (args[0].equals("-e")){
                    int x =inputStream.read(buff);

                    for (int i = 0; i < buff.length; i++) {
                        if (i%2 !=0)
                            outputStream.write(buff[i]);
                    }
                    for (int i = 0; i < buff.length; i++) {
                        if (i%2 ==0)
                            outputStream.write(buff[i]);
                    }

                }
                else if (args[0].equals("-d")){
                    byte[] decoded = new byte[inputStream.available()];
                    int numb = inputStream.read(buff);
                    int initStart0 = 0;
                    int dop = 0;

                    if ((numb-1)%2 ==0)
                        initStart0 = (numb - 1) / 2;
                    else
                        initStart0 = numb/2;

                    for (int i = 1; i < decoded.length; i += 2) {
                        if (dop < initStart0) {
                            decoded[i] = buff[dop];
                            dop++;
                        }
                    }
                    for (int i = 0; i < decoded.length; i += 2) {
                        if (initStart0<decoded.length) {
                            decoded[i] = buff[initStart0];
                            initStart0++;
                        }
                    }
                    outputStream.write(decoded);
                }
            }
            inputStream.close();
            outputStream.close();

        }
    }
