package cyx.ypwk.zbjw.base;

import java.util.List;

public class ShopMsg {

	private List<String> QQList = null;
	private List<String> TELList = null;
	private String ShopName = "";
	// 标记信息是否健全，如不全将会舍弃本记录
	private boolean IsNormal = true;

	public List<String> getQQList() {
		return QQList;
	}

	public void setQQList(List<String> pQQList) {
		QQList = pQQList;
	}

	public List<String> getTELList() {
		return TELList;
	}

	public void setTELList(List<String> pTELList) {
		TELList = pTELList;
	}

	public String getShopName() {
		return ShopName;
	}

	public void setShopName(String pShopName) {
		ShopName = pShopName;
	}

	public boolean getIsNormal() {
		return IsNormal;
	}

	public void setIsNarmal(boolean pIsNormal) {
		IsNormal = pIsNormal;
	}
}
