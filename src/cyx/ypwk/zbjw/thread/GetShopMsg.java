package cyx.ypwk.zbjw.thread;

import cyx.ypwk.zbjw.Main;
import cyx.ypwk.zbjw.base.HttpBase;
import cyx.ypwk.zbjw.base.RequestHeaders;
import cyx.ypwk.zbjw.base.ShopMsg;
import cyx.ypwk.zbjw.nativeio.FileReadAndWrite;
import cyx.ypwk.zbjw.tools.AnalyticData;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GetShopMsg {

	private int _ShopCount;
	private int mCount = 0;
	private List<String> mShopUrl = null;
	private RequestHeaders mRH;
	private List<ShopMsg> mShopMsgList = null;
	private int kkk = 0;
	private Color[] colors = new Color[] { Color.BLUE, Color.GREEN,
			Color.DARK_GRAY, Color.PINK, Color.CYAN, Color.LIGHT_GRAY };

	public GetShopMsg(List<String> pShopUrl) {
		if (mShopMsgList != null) {
			mShopMsgList.clear();
		}
		mRH = new RequestHeaders();
		mRH.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		mShopUrl = pShopUrl;
		// 累积处理的数据条数
		mCount = 0;
		_ShopCount = mShopUrl.size();
		if (_ShopCount <= 10) {
			new GetMsg(true).start();
		} else {

			// 开几条线程获取本页全部店铺的数据
			int _ThreadC = 4;
			// 每条线程的最少数据处理量
			int _MinC = _ShopCount / _ThreadC;
			// 每条线程的最大数据处理量
			int _MaxC = _ShopCount % _ThreadC == 0 ? _MinC : _MinC + 1;
			int _YS = _ShopCount % _ThreadC;
			for (int i = 0; i < _ThreadC; i++) {
				GetMsg msg = null;
				if (_YS > i) {
					msg = new GetMsg(i * _MaxC, (i + 1) * _MaxC);
				} else {
					msg = new GetMsg(i * _MinC + _YS, (i + 1) * _MinC + _YS);
				}
				msg.setName("线程：" + i);
				msg.start();
			}
		}

	}

	private class GetMsg extends Thread {
		private int mBIndexOf = 0;
		private int mEIndexOf = 0;
		private boolean mIsMulti = false;

		public GetMsg(boolean pIsMulti) {
			mIsMulti = pIsMulti;
		}

		public GetMsg(int pBIndexOf, int pEIndexOf) {
			mBIndexOf = pBIndexOf;
			mEIndexOf = pEIndexOf;
			mIsMulti = true;
		}

		@Override
		public void run() {
			Main.textField_1.setText("");
			Main.textField_1.setText("正在获取店铺信息");
			Main.textField_1.setBackground(Color.GREEN);
			mShopMsgList = new ArrayList<ShopMsg>();
			// 单线程处理
			if (!mIsMulti) {
				while (mCount < _ShopCount) {
					mCount++;
				}
				GetShopList.iswite = false;
			}
			// 多线程处理
			else {

				while (mBIndexOf < mEIndexOf) {
					Random random = new Random();
					kkk = random.nextInt(5);
					System.out.println(kkk);
					Main.textField_1.setBackground(colors[kkk]);

					String _Path = mShopUrl.get(mBIndexOf);
					try {
						String _HtmlContent = HttpBase.GetHtml(_Path, mRH);
						ShopMsg _msg = AnalyticData
								.analyticShopMsg(_HtmlContent);
						if (_msg.getIsNormal()) {
							mShopMsgList.add(_msg);
						} else {
							// 无电话有QQ也会标记为异常信息，这里恢复
							if (_msg.getTELList() != null
									&& _msg.getQQList() != null)
								if (_msg.getTELList().size() == 0
										&& _msg.getQQList().size() > 0) {
									mShopMsgList.add(_msg);
								}
						}
						System.out.println(this.getName() + "==>" + mBIndexOf
								+ ":获取数据第" + AnalyticData.mActivePage + "页");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.err.println("TimeOut Error:HTTP connection to "
								+ _Path);
					}
					mBIndexOf++;
					mCount++;
				}
				if (mCount == _ShopCount) {
					System.out.println(mShopMsgList.size() + "_______________"
							+ mCount);
					try {
						if (mShopMsgList.size() > 0) {
							FileReadAndWrite.SaveData(mShopMsgList,
									AnalyticData.getCategory());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					GetShopList.iswite = false;
				}
			}

		}
	}
}
