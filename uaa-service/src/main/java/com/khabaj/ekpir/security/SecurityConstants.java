package com.khabaj.ekpir.security;

public class SecurityConstants {
	public static final String SECRET = "SecretKeyToGenJWTs";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String AUTH_URL = "/auth/token";
	public static final String REGISTRATION_URL = "/accounts";
}
