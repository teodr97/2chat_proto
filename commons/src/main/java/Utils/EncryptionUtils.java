package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class EncryptionUtils {

    //Conversion functions
    public static String bytesToString(byte[] b) {

        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);

    }

    public static byte[] stringToBytes(String s) {

        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);

    }

    public static byte[] readFile(String path) throws IOException {

        File file = new File(path);

        byte[] fileData = new byte[(int) file.length()];

        try(FileInputStream FIS = new FileInputStream(file)) {

            FIS.read(fileData);

        }

        return fileData;
    }

    public static void writeFile(String path, byte[] data) throws IOException {

        try(FileOutputStream FOS = new FileOutputStream(path)) {
            FOS.write(data);
        }
    }

}
