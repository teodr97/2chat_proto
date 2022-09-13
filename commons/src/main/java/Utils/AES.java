package Utils;

import javax.crypto.*;
import java.security.NoSuchAlgorithmException;


public class AES {

    private SecretKey Key;

    public String keyStr() {

        byte[] keyBytes = Key.getEncoded();

        String keyString = EncryptionUtils.bytesToString(keyBytes);

        return keyString;

    }

    public AES(SecretKey key) {

        this.Key = key;

    }

    public AES() throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        this.Key = keyGenerator.generateKey();

    }



    public String encrypt(String data) throws Exception {

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, Key);
        byte[] result = cipher.doFinal(data.getBytes());

        return EncryptionUtils.bytesToString(result);
    }

    public String decrypt(String data) throws Exception {

        byte[] dataBytes = EncryptionUtils.stringToBytes(data);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, Key);
        byte[] result = cipher.doFinal(dataBytes);

        return new String(result);
    }

    public void fencrypt(String filePath) throws Exception {

       byte[] dataBytes = EncryptionUtils.readFile(filePath);

       Cipher cipher = Cipher.getInstance("AES");
       cipher.init(Cipher.ENCRYPT_MODE, Key);
       byte[] result = cipher.doFinal(dataBytes);

       EncryptionUtils.writeFile(filePath, result);
    }

    public void fdecrypt(String filePath) throws Exception {

        byte[] dataBytes = EncryptionUtils.readFile(filePath);

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, Key);
        byte[] result = cipher.doFinal(dataBytes);

        EncryptionUtils.writeFile(filePath, result);
    }



}
