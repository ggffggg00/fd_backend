package fd.backend.blockchain.crypto.rsa;

import lombok.Getter;
import lombok.SneakyThrows;
import org.jolokia.util.Base64Util;

import javax.swing.text.html.Option;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

@Getter
public class RSAKeyPairGenerator {

    public static String ALG = "RSA";
    public static int KEY_SIZE = 2048;

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public static RSAKeyPairGenerator generate() {
        return new RSAKeyPairGenerator(KEY_SIZE);
    }

    public static RSAKeyPairGenerator generate(int keySize) {
        return new RSAKeyPairGenerator(keySize);
    }

    @SneakyThrows
    private RSAKeyPairGenerator(int keySize) {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALG);
        keyGen.initialize(keySize);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public String getPrivateKeyEncoded() {
        return Base64Util.encode(privateKey.getEncoded());
    }

    public String getPublicKeyEncoded() {
        return Base64Util.encode(publicKey.getEncoded());
    }

    public static Optional<PrivateKey> decodePrivateKey(String base64PrivateKey) {
        var privateKeyBytes = Base64Util.decode(base64PrivateKey);
        var keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALG);
            return Optional.ofNullable(keyFactory.generatePrivate(keySpec));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<PublicKey> decodePublicKey(String base64PublicKey) {
        var privateKeyBytes = Base64Util.decode(base64PublicKey);
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return Optional.ofNullable(keyFactory.generatePublic(keySpec));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
