package fd.backend.blockchain.crypto;

import lombok.SneakyThrows;
import org.jolokia.util.Base64Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Hashing {

    @SneakyThrows
    public static String SHA256(String data){
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(
                data.getBytes(StandardCharsets.UTF_8));
        return Base64Util.encode(encodedHash);
    }

}
