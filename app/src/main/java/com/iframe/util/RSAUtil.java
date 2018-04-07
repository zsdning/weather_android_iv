package com.iframe.util;

import com.iframe.net.model.FrameRSAKey;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

/**
 *
 */


public class RSAUtil {
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus, 16);
            BigInteger b2 = new BigInteger(exponent, 16);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String encrypt(String data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] e = cipher.doFinal(data.getBytes());
        return new String(new Hex().encode(e));
    }

    public static String generatePwd(FrameRSAKey key, String pwd) {
        String enPwd = "";
        RSAPublicKey pubKey = RSAUtil.getPublicKey(key.getRsaParameter1(), key.getRsaParameter2());
        try {
            enPwd = RSAUtil.encrypt(pwd, pubKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enPwd;
    }
}


