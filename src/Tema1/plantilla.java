package Tema1;

import java.io.*;

public class plantilla {

    public static final String TEXT_FILE_NAME = "file.txt";
    public static final String BINARY_FILE_NAME = "bin.dat";
    private static final String SERIALIZED_FILE_NAME = "serialized.ser";

    public static void main(String[] args) {

    }

    public static void readTextFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(TEXT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTextFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TEXT_FILE_NAME))) {
            pw.println("Hello World");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void  readSerializedFile() {
        Object object = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SERIALIZED_FILE_NAME))) {
            while (true) {
                object = (Object) ois.readObject();
                System.out.println(object.toString());
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void  writeSerializedFile() {
        Object object = null;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME))) {
            oos.writeObject(object);
        } catch (EOFException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCsvFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(TEXT_FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                for (String datum : data) {
                    System.out.println(datum);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAndWritebinaryFile() {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(BINARY_FILE_NAME));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(BINARY_FILE_NAME))) {
            int numBytesRead = 0;
            int off = 0; //offset at which to start storing bytes.
            int len = 1024 * 16; //maximum number of bytes to read.
            byte[] buffer = new byte[len]; //destination buffer.

            while ((numBytesRead = bis.read(buffer, off, len)) != -1) {
                bos.write(buffer, off, numBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile() {
        try {
            File file = new File("hola.txt");
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createDirectory() {
        File file = new File("dir");
        file.mkdir();
    }
}
