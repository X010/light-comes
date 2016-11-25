package com.light.outside.comes.controller.pay.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class DesUtil {
	
	public final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	public final String key = "12345678";
	
	
	private DesUtil(){
		
	}
	/**
	 * 实例化当前加密类
	 * @return
	 */
	public static DesUtil Instance(){ 
		return new DesUtil(); 
	}  
	
	/**
	   * DES算法，加密
	   *
	   * @param data 待加密字符串
	   * @param key 加密私钥，长度不能够小于8位
	   * @return 加密后的字节数组，一般结合Base64编码使用
	   * @throws CryptException 异常
	   */
	
	public String encodeVal(String data)throws Exception{
		return this.encode(key,data.getBytes());
	}
	/**
	 * DES算法，解密
	 * @param key
	 * @param data
	 * @return
	 */
	public String decodeVal(String data){
		byte[] datas;
		String value = null;
		try {
			datas = this.decode(key, Base64.decodeBase64(data.getBytes()));
			value = new String(datas);
		} catch (Exception e) {
			// TODO: handle exception
			value = "";
		}
		return value;
	}
	/**
	   * DES算法，加密
	   *
	   * @param data 待加密字符串
	   * @param key 加密私钥，长度必须8 bytes long
	   * @return 加密后的字节数组，结合Base64编码
	   * @throws Exception 异常
	   */
	  private String encode(String key,byte[] data)throws Exception{
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey,paramSpec);
			byte[] bytes = cipher.doFinal(data);
			return new String(Base64.encodeBase64(bytes));
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}
	}
		
	/**
	   * DES算法，解密
	   *
	   * @param data 待解密字符串
	   * @param key 解密私钥，长度必须是8
	   * @return 解密后的字节数组
	   * @throws Exception 异常
	   */
	private byte[] decode(String key,byte[] data) throws Exception{
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			//key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(key.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey,paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(DesUtil.Instance().encodeVal("1"));
		System.out.println(DesUtil.Instance().decodeVal("hAY77bhzXBE="));
		
	}
}
