package com.iwhalecloud.retail.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES加密解密工具类
 * @author wenlong.zhong
 * @date 2019/3/21
 */
@Slf4j
public class AESUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";
    // 算法
    private static final String ALGORITHM = "AES";
    // 偏移量字符串必须大于16位 当模式是CBC的时候必须设置偏移量 （这个偏移量是和前端 加密用的 偏移量一致的）
    private static String IV = "3468546098617269";
    // 算法/模式/偏移补码方式
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    // 偏移量
    private static final Integer IV_SIZE = 16;

    /**
     * 创建密钥
     * @param key
     * @return
     */
    private static SecretKeySpec createKey(String key){

        byte[] data = null;
        try {
            data = key.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.info("AESUtils.createKey(),UnsupportedEncodingException异常");
            e.printStackTrace();
        }
        log.info("密钥：{}", parseByte2HexStr(data));
        return new SecretKeySpec(data, ALGORITHM);
    }

    /**
     * 创建16位向量: 不够则用0填充
     * @return
     */
    private static IvParameterSpec createIV() {
        StringBuffer sb = new StringBuffer(IV_SIZE);
        sb.append(IV);
        if (sb.length() > IV_SIZE){
            sb.setLength(IV_SIZE);
        }
        if (sb.length() < IV_SIZE){
            while (sb.length() < IV_SIZE){
                sb.append("0");
            }
        }
        byte[] data = null;
        try {
            data = sb.toString().getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            log.info("AESUtils.IvParameterSpec(),UnsupportedEncodingException异常");
            e.printStackTrace();
        }
        log.info("偏移量：{}", parseByte2HexStr(data));
        return new IvParameterSpec(data);
    }

    /**
     * 加密：有向量16位，结果转base64
     * @param context 待加密的字符串
     * @param key 密钥
     * @return 加密后的 base64格式的字符串
     */
    public static String encrypt(String context, String key) {
        try {
            byte[] content = context.getBytes(DEFAULT_CHARSET);
            SecretKey secretKey = createKey(key);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, createIV());
            byte[] data = cipher.doFinal(content);
            String result = Base64.encodeBase64String(data);
            return result;
        } catch (Exception e) {
            log.info("AESUtils.encrypt(),加密异常");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param context 待加密的（Base64格式）内容
     * @param key 密钥
     * @return 解密后的字符串
     */
    public static String decrypt(String context, String key) {
        try {
            byte[] data= Base64.decodeBase64(context);
            SecretKey secretKey = createKey(key);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, createIV());
            byte[] content = cipher.doFinal(data);
            String result = new String(content, DEFAULT_CHARSET);
            return result;
        } catch (Exception e) {
            log.info("AESUtils.decrypt(),解密异常");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
          String hex = Integer.toHexString(buf[i] & 0xFF);
          if (hex.length() == 1) {
              hex = '0' + hex;
          }
          sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换成二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i =0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 +1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
        String key = "A4C25461C1E5CC67";
//        String content = "123";
//        System.out.println("加密前：" + content);
//        System.out.println("加密密钥和解密密钥：" + key);
//        String encrypt = encrypt(content, key);
//        System.out.println("加密后：" + encrypt);
//        String decrypt = decrypt(encrypt, key);
        String decrypt = decrypt("Zvhk1KrLFiCVCDIA+fhCfA==", key);

        System.out.println("解密后：" + decrypt);
    }

}
