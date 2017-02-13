package cyx.ypwk.zbjw.base;

import cyx.ypwk.zbjw.tools.Parsing;

import java.io.IOException;

public class OCR {
	public static Boolean PostOCR(String html) throws IOException,
			InterruptedException {
		String ImageSrc = "";
		String uuid = Parsing.Getuuid(html);
		ImageSrc = Parsing.GetCode(html);
		// Main_Component.OCRimage.setIcon(new ImageIcon(Internet
		// .CodeImage(ImageSrc)));
		// Main_Component.frame.setBounds(100, 100, 720, 317);
		/*while (!HTTP_Thread.CodeIsOk) {
			Thread.sleep(10);
		}
		HTTP_Thread.CodeIsOk = false;
		String data = "uuid=" + uuid + "&namespace=infodetailweb&inputcode="
				+ Main_Component.OCRText.getText();
		String result = Internet.PostCode(
				"http://support.58.com/firewall/valid/"
						+ Parsing.GetUrlCode(html) + ".do",
				data.getBytes("UTF-8"));
		System.out.println("result:" + result);
		if (result.equals("0")) {
			Main_Component.OCRText.setText("验证码错误，请重新输入");
		} else {
			Main_Component.frame.setBounds(100, 100, 550, 317);
			Main_Component.OCRimage.setIcon(null);
			Main_Component.OCRText.setText("");
		}
		*/
		return false;
	}

}
