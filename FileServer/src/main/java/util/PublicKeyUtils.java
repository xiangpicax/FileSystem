package util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class PublicKeyUtils {
    public String encryptByPublicKey(String strPublicKey,String strEncMessage){
        KeyFactory keyFactory2 = null;
        PublicKey publicKey2 = null;
        Cipher cipher2 = null;
        String strEncryptedInf = "";
        byte[] result2 = new byte[0];
        try {
            X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(strPublicKey));
            keyFactory2 = KeyFactory.getInstance("RSA");
            publicKey2 = keyFactory2.generatePublic(x509EncodedKeySpec2);
            cipher2 = Cipher.getInstance("RSA");
            cipher2.init(Cipher.ENCRYPT_MODE, publicKey2);
            result2 = cipher2.doFinal(strEncMessage.getBytes());
            strEncryptedInf = Base64.encodeBase64String(result2);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return strEncryptedInf;
        }

    }

}
