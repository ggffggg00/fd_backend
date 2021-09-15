package fd.backend.blockchain.crypto.aes;

import lombok.SneakyThrows;
import org.jolokia.util.Base64Util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESCipher {

    private static final String ALG = "AES/CBC/PKCS5Padding";
    @SneakyThrows
    public static String encrypt(String input, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.ENCRYPT_MODE, key, generateIv());
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64Util.encode(cipherText);
    }

    public static String decrypt(String cipherText, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(ALG);
        cipher.init(Cipher.DECRYPT_MODE, key, generateIv());
        byte[] plainText = cipher.doFinal(Base64Util.decode(cipherText));
        return new String(plainText);
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

}
