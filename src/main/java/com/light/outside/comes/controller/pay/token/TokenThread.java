package com.light.outside.comes.controller.pay.token;

import com.light.outside.comes.controller.pay.TenWeChatGenerator;
import com.light.outside.comes.controller.pay.config.TenWeChatConfig;
import com.light.outside.comes.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class TokenThread implements Runnable{
	private Logger LOG = LoggerFactory.getLogger(TokenThread.class);
	// 第三方用户唯一凭证
	public static String appid = TenWeChatConfig.app_id;
	// 第三方用户唯一凭证密钥
	public static String appsecret = TenWeChatConfig.app_secret;
	public static AccessToken accessToken = null;
	@Override
	public void run() {
		while (true) {
			try {
				accessToken= TenWeChatGenerator.getAccessTokenModel();
				if (null != accessToken) {
					LOG.info("accesstoken:"+accessToken.getToken() +" date:"+ DateUtils.format(new Date(),DateUtils.datePatternAllHH));
					// 休眠7000秒
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(10 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
				}
			}
		}
	}

}
