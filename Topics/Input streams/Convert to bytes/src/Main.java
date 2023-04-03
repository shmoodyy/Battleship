import java.io.InputStream;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;
        int charAsByte = inputStream.read();
        while (charAsByte != -1) {
            byte inputByte = (byte) charAsByte;
            System.out.print(inputByte);
            charAsByte = inputStream.read();
        }
        inputStream.close();
    }
}