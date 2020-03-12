package com.zlp.fileclient.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivateKeyUtils {
    public String decryptionByPrivateKey(String strPrivateKey,String strEncryptedInf){
        KeyFactory keyFactory5 = null;
        PrivateKey privateKey5 = null;
        Cipher cipher5 = null;
        String strDecryptionInf = "";
        byte[] result5 = new byte[0];
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(strPrivateKey));
            keyFactory5 = KeyFactory.getInstance("RSA");
            privateKey5 = keyFactory5.generatePrivate(pkcs8EncodedKeySpec5);
            cipher5 = Cipher.getInstance("RSA");
            cipher5.init(Cipher.DECRYPT_MODE, privateKey5);
            byte[] result2 =  Base64.decodeBase64(strEncryptedInf);
            result5 = cipher5.doFinal(result2);
            strDecryptionInf = new String(result5);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return strDecryptionInf;
        }
    }
}
