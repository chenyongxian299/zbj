package cyx.ypwk.zbjw.tools;

import cyx.ypwk.zbjw.base.ShopMsg;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AnalyticData {
	private static Document mDoc;
	private static String mNextPageUrl = "";
	public static String mActivePageUrl = "";
	public static String mActivePage = "";
	private static String mCategory = "";

	public static void setCategory(String pPath) {
		//pPath = "http://www.zhubajie.com/ppsj/pp1.html";
		int _beginIndex = pPath.indexOf(".com/") + 5;
		mCategory = pPath.substring(_beginIndex,
				pPath.indexOf("/", _beginIndex + 1));
	}

	public static String getCategory() {
		return mCategory;
	}

	private static String getQQnum(String pS) {
		String QQ = "";
		try {
			int start = pS.indexOf("&uin=") + 5;
			QQ = pS.substring(start, pS.indexOf("&", start));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("QQ num no be found");
		}
		return QQ;
	}

	public static ShopMsg analyticShopMsg(String pHtmlContent) {
		ShopMsg _msg = new ShopMsg();
		List<String> _List = new ArrayList<String>();

		mDoc = Jsoup.parse(pHtmlContent);
		try {
			Element _Shop = mDoc.select("div.shop-fixed-im-hover").get(0);
			String _ShopName = _Shop.select("div.fix-im-cate").get(0).text();
			_msg.setShopName(_ShopName);
			_List.clear();
			Elements _QQ = _Shop.select("div.shop-fix-im-qq").get(0)
					.select("div.qq-item");
			for (int i = 0; i < 2; i++) {
				if (_QQ.size() - i > 0) {
					// 强制让QQList的size为2
					_List.add(getQQnum(_QQ.get(i).select("a").attr("href") + ""));
				} else {
					_List.add(" ");

				}
			}
			_msg.setQQList(CopyArrayList.CopyArrayListString(_List));
			_List.clear();
			Elements _PhoneItme = _Shop.select("div.shop-fix-im-phone");
			Elements _TEL = null;
			if (_PhoneItme.size() == 0) {
				_PhoneItme = _Shop.select("div.shop-fix-im-time");
				_TEL = _PhoneItme.get(_PhoneItme.size() - 1).select(
						"div.time-item");
			} else {
				_TEL = _PhoneItme.get(0).select("div.phone-item");
			}

			for (int i = 0; i < 2; i++) {
				if (_TEL.size() - i > 0) {
					// 强制让TELList的size为2
					_List.add(_TEL.get(i).text() + "");
				} else {
					_List.add(" ");
				}
			}
			_msg.setTELList(CopyArrayList.CopyArrayListString(_List));
		} catch (ArrayIndexOutOfBoundsException e) {
			_msg.setIsNarmal(false);
			System.err.println("unkown error");
		} catch (IndexOutOfBoundsException e) {
			_msg.setIsNarmal(false);
			System.err.println("no information");
		}
		return _msg;
	}

	public static String getNextPageUrl() {
		return mNextPageUrl;
	}

	private static String nextPageUrl() throws ArrayIndexOutOfBoundsException {
		String _Nexturl = "";
		Element _Page = mDoc.select("div.pagination").get(0);
		Element _Ul = _Page.select("ul").get(0);
		Elements _Li = _Ul.select("li");
		Element _LiActive = _Li.select("li.active").get(0);
		mActivePageUrl = _LiActive.select("a").attr("href");
		mActivePage = _LiActive.select("a").text();
		int _Count = _Li.size();
		_Nexturl = _Li.get(_Count - 1).select("a").get(0).attr("href");
		if (_Nexturl.indexOf("java") == 0) {
			return null;
		}
		return _Nexturl;
	}

	public static String mCategoryName = "";

	public static List<String> analyticShopList(String pHtml) {
		List<String> _list = new ArrayList<String>();
		mDoc = Jsoup.parse(pHtml);
		try {
			mCategoryName = mDoc.select("span.item-txt").get(0).text();
			System.out.println(mCategoryName);
			Element _ListCon = mDoc.select("div.list-container").get(0);
			Element _ListBody = _ListCon.select("div.list-body").get(0);
			Element _ListWik = _ListBody.select("div.witkey-list").get(0);
			Elements _itmes = _ListWik.select("div.fws-item");
			// 当前页面店铺个数
			int _Conut = _itmes.size();
			for (int i = 0; i < _Conut; i++) {
				Element _ShopUrl = _itmes.get(i).select("a.witkey-item-name")
						.get(0);
				_list.add(_ShopUrl.attr("href"));
			}
			mNextPageUrl = nextPageUrl();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("超标");
		}

		return _list;
	}

	private String MsgtoGs(ShopMsg pShopMsg) {
		System.out.println("Shop Name:" + pShopMsg.getShopName());
		if (pShopMsg.getQQList() != null)
			for (int i = 0; i < pShopMsg.getQQList().size(); i++) {
				System.out
						.println("QQ" + i + ":" + pShopMsg.getQQList().get(i));
			}
		if (pShopMsg.getTELList() != null)
			for (int i = 0; i < pShopMsg.getTELList().size(); i++) {
				System.out.println("TEL" + i + ":"
						+ pShopMsg.getTELList().get(i));
			}
		return null;
	}
}
