/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.somnus.cloud.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.somnus.cloud.common.util.exception.HttpAesException;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @ClassName: HttpAesUtil
 * @Description: The class Http aes util.
 * @author Somnus
 * @date 2018年10月9日
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpAesUtil {

	private static final String CHAR_SET = "UTF-8";

	/**
	 * 加密
	 *
	 * @param contentParam 需要加密的内容
	 * @param keyParam     加密密码
	 * @param md5Key       是否对key进行md5加密
	 * @param ivParam      加密向量
	 *
	 * @return 加密后的字节数据 string
	 */
	public static String encrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
		try {
			byte[] content = contentParam.getBytes(CHAR_SET);
			byte[] key = keyParam.getBytes(CHAR_SET);
			byte[] iv = ivParam.getBytes(CHAR_SET);

			if (md5Key) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				key = md.digest(key);
			}
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			//"算法/模式/补码方式"
			Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
			//使用CBC模式, 需要一个向量iv, 可增加加密算法的强度
			IvParameterSpec ivps = new IvParameterSpec(iv);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
			byte[] bytes = cipher.doFinal(content);
			return Base64.getEncoder().encodeToString(bytes);
		} catch (Exception ex) {
			log.error("加密密码失败", ex);
			throw new HttpAesException("加密失败");
		}
	}

	/**
	 * 解密
	 *
	 * @param contentParam 需要加密的内容
	 * @param keyParam     加密密码
	 * @param md5Key       是否对key进行md5加密
	 * @param ivParam      加密向量
	 *
	 * @return string
	 */
	public static String decrypt(String contentParam, String keyParam, boolean md5Key, String ivParam) {
		try {
			if (PubUtils.isNull(contentParam, keyParam, md5Key, ivParam)) {
				return "";
			}
			byte[] content = Base64.getDecoder().decode(contentParam);
			byte[] key = keyParam.getBytes(CHAR_SET);
			byte[] iv = ivParam.getBytes(CHAR_SET);

			if (md5Key) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				key = md.digest(key);
			}
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			//"算法/模式/补码方式"
			Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding");
			//使用CBC模式, 需要一个向量iv, 可增加加密算法的强度
			IvParameterSpec ivps = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
			byte[] bytes = cipher.doFinal(content);
			return new String(bytes, CHAR_SET);
		} catch (Exception ex) {
			log.error("解密密码失败", ex);
			throw new HttpAesException("解密失败");
		}
	}
}