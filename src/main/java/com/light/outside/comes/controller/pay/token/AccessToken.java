package com.light.outside.comes.controller.pay.token;

import java.util.Date;

public class AccessToken {
		// 获取到的凭证
		private String token;
		// 凭证有效时间，单位：秒
		private int expiresIn;
		//获取凭证时间
		private long tokenTime;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public int getExpiresIn() {
			return expiresIn;
		}

		public void setExpiresIn(int expiresIn) {
			this.expiresIn = expiresIn;
		}

	public long getTokenTime() {
		return tokenTime;
	}

	public void setTokenTime(long tokenTime) {
		this.tokenTime = tokenTime;
	}
}
