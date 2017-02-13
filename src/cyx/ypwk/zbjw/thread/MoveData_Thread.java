package cyx.ypwk.zbjw.thread;

import cyx.ypwk.zbjw.Main;
import cyx.ypwk.zbjw.base.ShareData;
import cyx.ypwk.zbjw.nativeio.Excel;
import cyx.ypwk.zbjw.nativeio.FileReadAndWrite;
import cyx.ypwk.zbjw.tools.AnalyticData;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MoveData_Thread extends Thread {

	public MoveData_Thread() {

	}

	@Override
	public void run() {
		try {
			Excel.createExcel();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = new File(ShareData.RootDirectoryPath);
		if (file.exists()) {
			File[] files = file.listFiles();
			try {
				for (int i = 0; i < files.length; i++) {
					String FileName = files[i].getName();
					if (FileName.equals(ShareData.configName)
							|| FileName.equals("xls")) {
						continue;
					}
					String ReadData;
					ReadData = FileReadAndWrite.Read(files[i].getPath()
							.toString());
					// 分页取数据
					String data[] = FileReadAndWrite.GetDataArray(ReadData);

					for (int j = 0; j < data.length; j++) {
						if (!FileReadAndWrite.isGoodJson(data[j])) {
							continue;
						}
						String Msg = "";
						int timer = 5 - j % 5;
						for (int k = 0; k < timer + 1; k++) {
							Msg = Msg + ".";
						}
						/*	Main_Component.lblNewLabel2
									.setText("数据获取完毕,正在生成Excel文件" + Msg);
							Main_Component.lblNewLabel
									.setText(Main_Component.lblNewLabel.getText()
											.toString() + data[j]);
							Main_Component.lblNewLabel
									.setCaretPosition(Main_Component.lblNewLabel
											.getText().length());*/
					}
					// 储存整理好的数据（每页是一个元素）
					FileReadAndWrite.Write(AnalyticData.mCategoryName, data,
							false);
					/*	FileReadAndWrite.RemoveFile(files[i].getPath().toString());
						ShareData.NUMLIST.clear();*/
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "错误信息!",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			System.out
					.println("-------------------------------导出成功-------------------------");
			// Main_Component.lblNewLabel2.setText("数据导出成功(D:\\58TC.xls)");

		}
		Main.textField_1.setVisible(false);
		Main.button_1.setEnabled(true);
	}
}
