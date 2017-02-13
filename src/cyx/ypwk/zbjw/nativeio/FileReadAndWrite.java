package cyx.ypwk.zbjw.nativeio;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cyx.ypwk.zbjw.base.ShareData;
import cyx.ypwk.zbjw.base.ShopMsg;
import org.jsoup.helper.StringUtil;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class FileReadAndWrite {

	private static String http = "http://";
	private static String com = ".com/";
	private static boolean isfount = false;

	public static String Read(String Path) throws IOException {
		FileInputStream inputStream = new FileInputStream(Path);
		byte[] by = new byte[1024];
		int len = -1;
		StringBuffer sBuffer = new StringBuffer("");
		while ((len = inputStream.read(by)) != -1) {
			sBuffer.append(new String(by, 0, len, "utf-8").trim());
		}
		inputStream.close();
		return sBuffer.toString();
	}

	public static void Write(String pCategory, String[] pData, boolean flag)
			throws IOException {
		if (pCategory.indexOf("txt") == pCategory.length() - 3
				|| pCategory.indexOf("jrd") == pCategory.length() - 3) {
			FileOutputStream _FOS = new FileOutputStream(pCategory, flag);
			pData[0] = pData[0] + "\n";
			_FOS.write(pData[0].getBytes("utf-8"));
			_FOS.close();
		} else {
			String src = pCategory;
			Excel.WriteExcel(src, pData);
		}
	}

	public static void WriteConfig(String data) throws IOException {
		System.out.println(data);
		isfount = false;
		String host = GetHost(data);
		String str = Read(ShareData.ConfigPath).trim();
		String[] array = str.split(":>");
		StringBuffer sBuffer = new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			if (array[i].indexOf(host) != -1) {
				array[i] = data.substring(0, data.length() - 2);
				isfount = true;
			}
		}

		if (!isfount) {
			sBuffer = new StringBuffer(data);
		} else {
			for (int i = 0; i < array.length; i++) {
				sBuffer.append(array[i] + ":>");
			}
		}
		Write(ShareData.ConfigPath, new String[] { sBuffer.toString().trim() },
				!isfount);
	}

	// 保存数据
	public static void SaveData(List<ShopMsg> pSML, String pCategory)
			throws IOException {
		Gson gson = new Gson();
		String data = gson.toJson(pSML) + "///";
		System.out.println(data);
		GetOrCreateFilePath(ShareData.RootDirectoryPath,
				ShareData.TempTextName(pCategory));

		Write(ShareData.TempTextPath(pCategory), new String[] { data }, true);

	}

	public static String[] GetDataArray(String ReadData) {
		String[] jsonarry = ReadData.split("///");
		return jsonarry;
	}

	@SuppressWarnings("resource")
	public static Boolean Then(String src, String num) throws IOException {
		String ReadData = Read(ShareData.TempTextPath(src));
		String[] jsonarry = GetDataArray(ReadData);
		for (int i = 0; i < jsonarry.length; i++) {
			if (!isGoodJson(jsonarry[i])) {
				continue;
			}
			JsonReader reader = new JsonReader(new StringReader(jsonarry[i]));
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				while (reader.hasNext()) {
					String Num = reader.nextName();
					if (Num.equals("Number")) {
						String mynum = reader.nextString();
						if (num.indexOf(mynum) != -1
								|| mynum.indexOf(num) != -1) {
							// System.out.println(num);
							return true;
						}
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
			}
			reader.endArray();
			reader.close();
		}
		return false;
	}

	public static boolean isGoodJson(String json) {
		if (StringUtil.isBlank(json)) {
			return false;
		}
		try {
			new JsonParser().parse(json);
			return true;
		} catch (JsonParseException e) {
			return false;
		}
	}

	public static String[] ReadConfig_PathArray() {
		String str = "";
		try {
			str = Read(ShareData.ConfigPath);
		} catch (IOException e) {

			e.printStackTrace();
		}
		String[] array = str.split(":>");
		return array;
	}

	public static String GetSrc(String Path) {
		String city = Path.substring(http.length(), Path.indexOf("."));
		int index = Path.indexOf(com) + com.length();
		String category = Path.substring(index, Path.indexOf("/", index + 1));
		String src = "(" + city + "-" + category + ")";
		return src;
	}

	public static String GetHost(String Path) {
		int index = Path.indexOf(com) + com.length();
		String src = Path.substring(0, Path.indexOf("/", index + 1));
		return src;
	}

	public static void CreateDataFile(String Path) {
		String src = GetSrc(Path);
		try {
			GetOrCreateFilePath(ShareData.RootDirectoryPath,
					ShareData.TempTextName(src));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void CreateConfigFile() {
		try {
			GetOrCreateFilePath(ShareData.RootDirectoryPath,
					ShareData.configName);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void RemoveFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (file.delete()) {
			System.out.println("成功");
		} else {
			JOptionPane.showMessageDialog(null, "数据清除失败", "错误信息!",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void RemoveFileByContext(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		String context;
		try {
			context = Read(ShareData.TempTextPath(path));
			if (context.equals("")) {
				if (file.delete()) {
					System.out.println("成功");
				} else {
					JOptionPane.showMessageDialog(null, "数据清除失败", "错误信息!",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "数ds据清除失败", "错误信息!",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static File GetOrCreateFilePath(String path, String filename)
			throws IOException {
		System.out.println(path + filename);
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(path + filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

}
