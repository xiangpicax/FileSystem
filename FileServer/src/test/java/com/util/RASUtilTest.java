package com.util;

import org.junit.Assert;
import org.junit.Test;
import util.Constants;
import util.PrivateKeyUtils;
import util.PublicKeyUtils;

import java.security.PrivateKey;
import java.util.UUID;

public class RASUtilTest {
    @Test
    public void RASUtilTest(){
        String uuid = UUID.randomUUID().toString();
        PublicKeyUtils publicKeyUtils = new PublicKeyUtils();
        String encrypt = publicKeyUtils.encryptByPublicKey(Constants.strPublicKey, uuid);
        PrivateKeyUtils privateKeyUtils  = new PrivateKeyUtils();
        String decryption = privateKeyUtils.decryptionByPrivateKey(Constants.strPrivateKey, encrypt);
        Assert.assertEquals(uuid,decryption);

    }
}
