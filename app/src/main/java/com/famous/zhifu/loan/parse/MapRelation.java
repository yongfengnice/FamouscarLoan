package com.famous.zhifu.loan.parse;

import java.util.HashMap;
import java.util.Map;

public class MapRelation {
	/** 担保方式映射关系  */
	@SuppressWarnings("serial")
	public static final Map<String, String> REPAYMENT_MAP  = new HashMap<String, String>(){
		{
			put("1", "等额本息");
			put("2", "付息还本");
			put("3", "本息到付");
		}
	};
}
