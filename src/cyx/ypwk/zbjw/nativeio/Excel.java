package cyx.ypwk.zbjw.nativeio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cyx.ypwk.zbjw.base.ShareData;
import cyx.ypwk.zbjw.base.ShopMsg;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Excel {

	public static HSSFWorkbook Read(File file) throws IOException {
		FileInputStream inputStream = new FileInputStream(file);
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		return workbook;
	}

	public static void WriteExcel(String PageName, String[] data)
			throws IOException {
		if(PageName==null||PageName.equals(""))
		{
			PageName="xxx";
		}
		File file = new File(ShareData.ExcelFilePath);
		HSSFWorkbook workbook = Read(file);
		HSSFSheet sheet;
		FileOutputStream outputStream = new FileOutputStream(file);
		int PageCount = workbook.getNumberOfSheets();
		for (int i = 0; i < PageCount; i++) {
			if (workbook.getSheetName(i).equals(PageName)) {
				analy(data, workbook.getSheetAt(i), outputStream);
				return;
			}
		}
		sheet = workbook.createSheet(PageName);
		analy(data, workbook.getSheetAt(PageCount), outputStream);
	}

	public static File createExcel() throws FileNotFoundException, IOException {
		File file = new File(ShareData.ExcelFolderPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(ShareData.ExcelFilePath);
		if (!file.exists()) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("null");
			FileOutputStream _FOS = new FileOutputStream(file);
			_FOS.flush();
			workbook.write(_FOS);
			workbook.close();
			_FOS.close();
		}
		return file;
	}

	public static void analy(String[] data, HSSFSheet sheet,
			FileOutputStream outputStream) throws IOException {
		// 分页写入数据
		for (int i = 0; i < data.length; i++) {
			if (!FileReadAndWrite.isGoodJson(data[i])) {
				return;
			}

			HSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("店铺名称");
			row.createCell(1).setCellValue("联系电话1");
			row.createCell(2).setCellValue("联系电话2");
			row.createCell(3).setCellValue("QQ1");
			row.createCell(4).setCellValue("QQ2");

			Gson gson = new Gson();
			// 运用到反射，对性能消耗较大，考虑改变
			Type type = new TypeToken<List<ShopMsg>>() {
			}.getType();
			List<ShopMsg> pSML = null;
			pSML = gson.fromJson(data[i], type);

			for (int j = 0; j < pSML.size(); j++) {
				List<String> QQList = new ArrayList<String>();
				List<String> TELList = new ArrayList<String>();
				String ShopName = "";
				ShopMsg _msg = pSML.get(j);
				System.out.println(_msg.getShopName());
				ShopName = _msg.getShopName();

				if (_msg.getQQList() != null)
					for (int k = 0; k < _msg.getQQList().size(); k++) {
						QQList.add(_msg.getQQList().get(k));
					}
				if (_msg.getTELList() != null)
					for (int k = 0; k < _msg.getTELList().size(); k++) {
						TELList.add(_msg.getTELList().get(k));
					}

				int LastRowNum = sheet.getLastRowNum();
				row = sheet.createRow(LastRowNum + 1);
				row.createCell(0).setCellValue(ShopName);
				row.createCell(1).setCellValue(TELList.get(0) + "");
				row.createCell(2).setCellValue(TELList.get(1) + "");
				row.createCell(3).setCellValue(QQList.get(0) + "");
				row.createCell(4).setCellValue(QQList.get(1) + "");
			}
		}
		outputStream.flush();
		sheet.getWorkbook().write(outputStream);
		outputStream.close();
		sheet.getWorkbook().close();

	}
}
