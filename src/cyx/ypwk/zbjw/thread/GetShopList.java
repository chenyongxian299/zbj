package cyx.ypwk.zbjw.thread;

import cyx.ypwk.zbjw.Main;
import cyx.ypwk.zbjw.base.HttpBase;
import cyx.ypwk.zbjw.base.RequestHeaders;
import cyx.ypwk.zbjw.tools.AnalyticData;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GetShopList extends Thread {
	private String mURL = "";
	private RequestHeaders mRH;
	private String mHtmlContent;
	public static boolean iswite = true;
	private boolean mIsHasNextP = true;

	public GetShopList(String pURL) {
		mURL = pURL;
		mRH = new RequestHeaders();
		mRH.setHost("Host: www.zhubajie.com");
		mRH.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
		mRH.setAccept("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		mRH.setAcceptLanguage("zh-CN,zh;q=0.8");
		mRH.setAcceptEncoding("gzip, deflate, sdch");
		mRH.setConneciton("Keep-alive");

	}

	public void run() {
		Main.textField_1.setVisible(true);
		//Main.button.setEnabled(false);
		
		Main.button_1.setEnabled(false);
		try {
			HttpBase.COOKIES=HttpBase.GetCookies(mURL);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (mIsHasNextP&&Main.ISSTART) {

			iswite = true;
			try {
				Main.textField_1.setText("");
				Main.textField_1.setText("正在获取店铺列表");
				Main.textField_1.setBackground(Color.RED);
				Main.textField.setText(mURL);
				mHtmlContent = HttpBase.GetHtml(mURL, mRH);

				List<String> _ShopUrl = AnalyticData
						.analyticShopList(mHtmlContent);
				new GetShopMsg(_ShopUrl);
				String _NextPageUrl = AnalyticData.getNextPageUrl();
				// 读取完全部列表后退出此线程
				if (_NextPageUrl == null) {
					System.out.println("No Next Page");
					mIsHasNextP = false;
				} else {
					mURL = "http://www.zhubajie.com" + _NextPageUrl;
					
				}

				// 启动另一条线程去获取店铺数据
				while (iswite) {
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Main.button.setLabel("start");
		Main.button.setEnabled(true);
		MoveData_Thread m = new MoveData_Thread();
		m.start();
	};
}
