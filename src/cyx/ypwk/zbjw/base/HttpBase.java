package cyx.ypwk.zbjw.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpBase {
	public static String COOKIES="";
	public static String GetCookies(String Path) throws Exception{
		URL url = new URL(Path);
		HttpURLConnection _Conn = (HttpURLConnection) url.openConnection();
		
		//_Conn.setRequestProperty(RequestHeaders.USER_AGENT, pRH.getUserAgent());
		_Conn.setReadTimeout(5000);
		_Conn.setRequestMethod("GET");
		_Conn.connect();
		Map<String, List<String>> headers=_Conn.getHeaderFields();
		List<String> cookies=headers.get("Set-Cookie");
		StringBuffer _cookies=new StringBuffer("");
		for(String cookie:cookies){
			_cookies.append(cookie).append(";");
		}
		//String Cookies=_Conn.getHeaderField("Set-Cookie");
		_Conn.disconnect();
		//return Cookies;
		return _cookies.toString();
	}
	public static String GetHtml(String Path, RequestHeaders pRH)
			throws IOException {
		return Get(Path, pRH);
	}

	public static String PostCode(String Path, byte[] data) throws IOException {
		String result = Post(Path, data);
		return result;
	}

	public static byte[] CodeImage(String Path) throws IOException {
		URL url = new URL(Path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int code = 200;// connection.getResponseCode();
		InputStream inputStream = null;
		if (code == 200) {
			inputStream = connection.getInputStream();
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int n = -1;
		while ((n = inputStream.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
		byte[] by = output.toByteArray();
		connection.disconnect();
		return by;
	}

	private static String Post(String Path, byte[] data) throws IOException {
		URL url = new URL(Path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		HttpURLConnection.setFollowRedirects(true);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Encoding", "gzip");
		String result = "";
		OutputStream outputStream = connection.getOutputStream();
		outputStream.write(data);
		outputStream.flush();
		InputStream inputStream = connection.getInputStream();
		result = ReturnResult(inputStream, "UTF-8");
		outputStream.close();
		connection.disconnect();
		return result;
	}

	private static String Get(String Path, RequestHeaders pRH)
			throws IOException,SocketTimeoutException {
		URL url = new URL(Path);
		HttpURLConnection _Conn = (HttpURLConnection) url.openConnection();
		_Conn.setRequestProperty(RequestHeaders.USER_AGENT, pRH.getUserAgent());
		_Conn.setRequestProperty(RequestHeaders.COOKIE,COOKIES);
		_Conn.setReadTimeout(5000);
		_Conn.setRequestMethod("GET");
		_Conn.connect();
		int code = _Conn.getResponseCode();
		InputStream _inputStream = null;
		String result = "";
		if (code == 200) {
			
			_inputStream = _Conn.getInputStream();
			result = ReturnResult(_inputStream, "utf-8");
		} else {
			System.out.println("error code:" + code);
		}
		_Conn.disconnect();
		return result;
	}

	private static String ReturnResult(InputStream inputStream, String charcode)
			throws IOException {
		StringBuffer sBuffer = new StringBuffer("");
		if (inputStream != null) {
			byte[] by = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(by)) != -1) {
				sBuffer.append(new String(by, 0, len, charcode));
			}
			inputStream.close();
		}
		return sBuffer.toString();
	}

}
