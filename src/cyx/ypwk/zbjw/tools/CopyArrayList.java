package cyx.ypwk.zbjw.tools;

import java.util.ArrayList;
import java.util.List;

public class CopyArrayList {

	public static List<String> CopyArrayListString(List<String> list)
	{
		List<String> newlist=new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			newlist.add(list.get(i));
		}
		return newlist;
	}
}
