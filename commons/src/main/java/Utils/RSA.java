package Utils;

import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;



public class RSA {

    PublicKey pbk;
    PrivateKey prk;

    public RSA() {

    }

    //Key pair strings class - used to store and use keys efficiently
    public class KeyPairStrings {

        private String pubKey;
        private String privKey;

        public String getPubKey() {

            return pubKey;
        }

        public String getPrivKey() {

            return privKey;
        }

        @Override
        public String toString() {

            return "KeyPairStrings\n pubKey = " + pubKey +
                    "\n privKey = " + privKey;
        }

    }

    //Key generator
    public KeyPairStrings genKeys() throws NoSuchAlgorithmException {

        //Instantiate keyPairStrings object and keyPairGenerator and actually generate the keys

        KeyPairStrings keyPairStrings = new RSA.KeyPairStrings();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.genKeyPair();

        //Separate the keys

        pbk = kp.getPublic();
        prk = kp.getPrivate();

        //Get the byte arrays

        byte[] publicKeyBytes = pbk.getEncoded();
        byte[] privateKeyBytes = prk.getEncoded();

        //Convert them to strings

        keyPairStrings.pubKey = EncryptionUtils.bytesToString(publicKeyBytes);
        keyPairStrings.privKey = EncryptionUtils.bytesToString(privateKeyBytes);

        return keyPairStrings;
    }

    public String encrypt
            (String text) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {

        //Initialization of the cipher and encryption operation

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pbk);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());

        return EncryptionUtils.bytesToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws
            NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {

        String decrypted;

        //Actual decryption

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prk);
        byte[] decryptedBytes = cipher.doFinal(EncryptionUtils.stringToBytes(encryptedText));

        //Turning result to string

        decrypted = new String(decryptedBytes);

        return decrypted;
    }

    public void fencrypt(String filePath) throws Exception {

        byte[] dataBytes = EncryptionUtils.readFile(filePath);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pbk);
        byte[] result = cipher.doFinal(dataBytes);

        EncryptionUtils.writeFile(filePath, result);
    }

    public void fdecrypt(String filePath) throws Exception {

        byte[] dataBytes = EncryptionUtils.readFile(filePath);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prk);
        byte[] result = cipher.doFinal(dataBytes);

        EncryptionUtils.writeFile(filePath, result);
    }


}
