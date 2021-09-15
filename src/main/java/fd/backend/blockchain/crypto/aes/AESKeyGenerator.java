package fd.backend.blockchain.crypto.aes;

import lombok.SneakyThrows;
import org.jolokia.util.Base64Util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESKeyGenerator {

    public static final String ALG = "AES";
    public static final int BLOCK_SIZE = 256;
    public static final byte[] SALT = "sameSalt".getBytes();

    private final KeyGenerator keyGenerator;

    public static AESKeyGenerator getInstance(){
        return new AESKeyGenerator();
    }

    @SneakyThrows
    private AESKeyGenerator() {
        keyGenerator = KeyGenerator.getInstance(ALG);
        keyGenerator.init(BLOCK_SIZE);
    }

    public SecretKey generateKey(){
        return keyGenerator.generateKey();
    }

    @SneakyThrows
    public static SecretKey fromString(String key){
        var decodedKey = Base64Util.decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALG);
    }

    public static String keyToString(SecretKey key){
        return Base64Util.encode(key.getEncoded());
    }

    public String generateKeyString(){
        return keyToString(generateKey());
    }






}
