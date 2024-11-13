package database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    // vraca MD5 hash passworda
    public static String getHash(String pass) {
        try {
            MessageDigest msgDst = MessageDigest.getInstance("MD5");
            byte[] msgArr = msgDst.digest(pass.getBytes());
            BigInteger bi = new BigInteger(1, msgArr); 
            String hshtxt = bi.toString(16);
            while (hshtxt.length() < 32) {
                hshtxt = "0" + hshtxt;
            }
            return hshtxt;
        }   
        catch (NoSuchAlgorithmException abc) {
            throw new RuntimeException(abc);
        }
    }
}
