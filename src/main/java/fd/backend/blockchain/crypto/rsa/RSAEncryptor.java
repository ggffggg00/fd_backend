package fd.backend.blockchain.crypto.rsa;

import lombok.SneakyThrows;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAEncryptor {

    public static String TRANSFORMATION_ALG = "RSA/ECB/PKCS1Padding";
    private final Cipher cipher;

    public static RSAEncryptor getInstance() {
        return new RSAEncryptor();
    }

    @SneakyThrows
    private RSAEncryptor() {
        this.cipher = Cipher.getInstance(TRANSFORMATION_ALG);
    }

    public byte[] encrypt(String data, PublicKey publicKey) throws
            BadPaddingException, IllegalBlockSizeException, InvalidKeyException,
            NoSuchPaddingException, NoSuchAlgorithmException {
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    public String decrypt(byte[] data, PrivateKey privateKey) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

}
