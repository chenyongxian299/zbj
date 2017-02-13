package cyx.ypwk.zbjw.tools;

public class Parsing {

	public static String Getuuid(String html) {
		String uuid = "\"uuid\" : '";
		return GetNewString(html, uuid, "'");
	}

	private static String GetNewString(String html, String startstring,
			String endstring) {
		int startindex = html.indexOf(startstring) + startstring.length();
		int endindex = html.indexOf(endstring, startindex + 1);
		String resutl = html.substring(startindex, endindex);
		return resutl;
	}

	public static String GetUrlCode(String html) {
		String var = "var url = '/firewall/code/";
		String code = GetNewString(html, var, "/");
		return code;
	}

	public static String Getrnd() {
		String rnd = "0.3142875649500638";
		return rnd;
	}

	public static String GetCode(String html) {
		String uuid = Getuuid(html);
		String rnd = Getrnd();
		String Path = "http://support.58.com/firewall/code/"+GetUrlCode(html)+"/" + uuid
				+ ".do?rnd=" + rnd;
		return Path;
	}

}
