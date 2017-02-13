package cyx.ypwk.zbjw.base;

import java.util.ArrayList;
import java.util.List;

public class ShareData {
	public static String HOST = "http://www.zhubajie.com";
	public static String CATEGORY = "";

	// 程序配置文件完整路径
	public static String ConfigPath = "D:\\猪八戒网\\猪八戒网config.jrd";
	// 程序配置文件完整名字
	public static String configName = "猪八戒网config.jrd";
	// 程序Excel文件存放完整路径
	public static String ExcelFilePath = "D:\\猪八戒网\\xls\\猪八戒网.xls";
	// 程序Excel文件存放的目录路径
	public static String ExcelFolderPath = "D:\\猪八戒网\\xls\\";
	// 程序的"根"文件目录路径
	public static String RootDirectoryPath = "D:\\猪八戒网\\";

	public static List<String> NUMLIST = new ArrayList<String>();

	public static String TempTextPath(String name) {
		return RootDirectoryPath + TempTextName(name);
	}

	public static String TempTextName(String name) {
		return "猪八戒网(" + name + ").txt";
	}

}
