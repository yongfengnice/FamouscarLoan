package com.famous.zhifu.loan.activity;

/**
 * 常量定义类
 * 
 */
public class Consts {
	public static final String MCD_FLAG = "mcd";
	public static final String CONFIG_CLIENT = "ANDROID";
	public static final String CONFIG_VERSION = "1.0";
	public static final String CONFIG_KEY = "123456";
	public static final String UNKNOWN_FAULT = "未知错误,操作失败";
	public static final String NONETWORK = "获取数据失败，请检查网络状态";
	public static final String SP_NAME = "sp";
	public static final String SP_USERINFO = "sp_userInfo";
	public static final String FAMOUSCAR = "famouscar";// 记录智富360页面最后刷新时间
	public final static String USERINFO = "userInfo";
	public final static String NOT_LOGIN = "NOT_LOGIN";

//	 public static final String SERVICE_URL = "http://121.41.81.177"; // 内网
	
//	 public static final String SERVICE_URL = "http://121.41.81.177"; // 内网新
	 
	 public static final String SERVICE_URL = "http://service.zhifu360.com"; // 外网
	 
//	 public static final String SERVICE_URL = "http://service.zhifu360.cn";
	

	/**
	 * 网络请求的接口字段名
	 * 
	 */
	public static class NetWork {
		/** 投标 */
		public static final String LOANTENDER = "loan_tender";
		/** 标详情 */
		public static final String LOANDETAIL = "loan_detail";
		/** 资金记录 */
		public static final String ZJLS = "home_moneydetail";
		/** 投资记录详情 */
		public static final String INVESTDETAIL = "home_lend_detail";
		/** 员工编号请求 */
		public static final String INVESTSTAFFNO = "home_lend_staffno";
		/** 验证接口 */
		public static final String VERIFY = "home_bind_fetch";
		/** 媒体公告 */
		public static final String MEDIA = "media";
		/** 网络公告 */
		public static final String NOTICE = "notice";
		/** 排行榜 */
		public static final String RANK = "toptenz";
		/** 申请借款 */
		public static final String BORROW = "borrow";
		/** 加盟我们 */
		public static final String JOIN = "join";
		/** 意见反馈 */
		public static final String FEEDBACK = "feedback_submit";
		/** 常见问题 */
		public static final String PROBLEM = "common_problem";
		/** 登录接口 */
		public static final String LOGIN = "member_login";
		/** 获取验证码接口 */
		public static final String SENDCODE = "member_reg";
		/** 找回密码接口 */
		public static final String RECOVERPWD = "recover_pwd";
		/** 投资信息接口 */
		public static final String INVESTINFO = "home_basic";
		/** 回款预告接口 */
		public static final String HKYG = "home_receivedpayment";
		/** 获取认证信息接口 */
		public static final String RZGL = "home_bind_fetch";
		/** 提交认证接口 */
		public static final String BINDSUBMIT = "home_bind_submit";
		/** 我要借款 */
		public static final String BORROWQIAN = "borrow_qian";
		/** 获取我的投资记录接口 */
		public static final String MYINVEST = "home_lend_list";
		/** 积分管理接口 */
		public static final String JFGL = "home_integral_detail";
		/** 首页接口 */
		public static final String HOMEINIT = "init";
		/** 身价计算接口 */
		public static final String SJJS = "tools_shenjia";
		/** 通用贷款接口 */
		public static final String TYDK = "tools_normal";
		/** 民间贷款接口 */
		public static final String MJDK = "tools_minjian";
		/** 存款利率接口 */
		public static final String CKLL = "tools_cunkuan";
		/** 充值接口 */
		public static final String RECHARGE = "member_cashin";
		/** 取现获取验证码接口 */
		public static final String WITHDRAW = "member_cashout";
		/** 兑换管理接口 */
		public static final String DHGL = "member_convert";
		/** 意见反馈接口 */
		public static final String YJFK = "contact_submit";
		/** 智富360列表接口 */
		public static final String DDBLB = "loan_index";
		/** 退出登录接口 */
		public static final String EXIT = "member_logout";
		/** 自动投标接口接口 */
		public static final String ZDTB = "member_autotender";

		/** 获取智富360标详情 */
		public static final String HQDDBXQ = "qian_detail";

		/** 获取排行榜 */
		public static final String PHB = "qian_toptenz";

		/** 公告 */
		public static final String GG = "qian_notice";

		/** 媒体公告 */
		public static final String MT = "qian_media";

		/** 媒体公告 */
		public static final String WDGL = "member_faq";

		/** 推广管理 */
		public static final String TGGL = "member_popularize";

		/** 问答列表 */
		public static final String WDLB = "qian_faq";

		/** 修改密码/头像 */
		public static final String UPDATE = "member_modify";
		
		/** 充值 */
		public static final String PAY = "member_cashin";
	}

	/**
	 * 首选项的字段名
	 * 
	 */
	public static class SharePref {
		/** 资金流水 */
		public static final String ZJLS = "zjls";
		/** 投资记录 */
		public static final String TZJL = "tzjl";
		/** 回款预告 */
		public static final String HKYG = "hkyg";
		/** token */
		public static final String TOKEN = "token";
		/** 用户名 */
		public static final String USERNAME = "userName";
		/** 是否记住密码 */
		public static final String REMENBER = "remenber";
		/** XListView */
		public static final String XLISTVIEW = "xlistview";
		/** 智富360投资列表最后刷新时间 */
		public static final String INVEST = "invest";
		/** 还款方案最后刷新时间 */
		public static final String REPAY = "repay";
		/** 投资记录最后刷新时间 */
		public static final String RECORD = "record";
		/** 排行榜刷新时间 */
		public static final String RANK = "rank";
		/** 排行榜刷新时间 */
		public static final String NOTICE = "notice";
		/** 排行榜刷新时间 */
		public static final String MEDIA = "media";
		/** 排行榜刷新时间 */
		public static final String HOME = "home";
	}
}
