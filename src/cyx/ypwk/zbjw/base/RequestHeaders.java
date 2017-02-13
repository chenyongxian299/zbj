package cyx.ypwk.zbjw.base;

public class RequestHeaders {

	public final static String HOST = "Host";
	public final static String COOKIE = "Cookie";
	public final static String SET_COOKIE = "Set-Cookie";
	public final static String USER_AGENT = "User-Agent";
	public final static String AECCEPT = "Accept";
	public final static String ACCEPT_LANUAGE = "Accept-Language";
	public final static String ACCEPT_ENCODING = "Accept-Encoding";
	public final static String CONNECTION = "Connection";

	private String mCookies = "";
	private String mHost = "";
	private String mUserAgent = "";
	private String mAccept = "";
	private String mAcceptLanguage = "";
	private String mAcceptEncoding = "";
	private String mConnection = "";

	public String getCOOKIES() {
		return mCookies;
	}

	public void setCOOKIES(String pCookies) {
		mCookies = pCookies;
	}

	public String getHost() {
		return mHost;
	}

	public void setHost(String pHost) {
		mHost = pHost;
	}

	public String getUserAgent() {
		return mUserAgent;
	}

	public void setUserAgent(String pUserAgent) {
		mUserAgent = pUserAgent;
	}

	public String getAccept() {
		return mAccept;
	}

	public void setAccept(String pAccept) {
		mAccept = pAccept;
	}

	public String getAcceptLanguage() {
		return mAcceptLanguage;
	}

	public void setAcceptLanguage(String pAcceptLanguage) {
		mAcceptLanguage = pAcceptLanguage;
	}

	public String getAcceptEncoding() {
		return mAcceptEncoding;
	}

	public void setAcceptEncoding(String pAcceptEncoding) {
		mAcceptEncoding = pAcceptEncoding;
	}

	public String getConneciton() {
		return mConnection;
	}

	public void setConneciton(String pConneciton) {
		mConnection = pConneciton;
	}

}
