//package com.iwhalecloud.retail.oms.quartz.action;
//
//import com.ztesoft.form.util.StringUtil;
//import com.ztesoft.net.framework.database.Page;
//import org.apache.log4j.Logger;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class WWAction  {
//	protected final Logger logger = Logger.getLogger(getClass());
//	protected Page webpage;
//
//	protected Map session = null;
//
//	protected List msgs = new ArrayList();
//
//	protected Map urls = new HashMap();
//
//	protected static final String MESSAGE = "message";
//
//	protected static final String JSON_MESSAGE = "json_message";
//
//	protected static final String RESULT_MESSAGE = "result_message";
//
//	protected String excel;
//
//	protected String totalPage;	//标志导出为全部记录
//
//	protected String currentPage;	//标志导出为本页记录
//
//	protected String script = "";
//
//	protected String json;
//
//
//	protected int page;
//
//	protected int pageSize;
//
//	// 页面id,加载页面资源所用
//	protected String pageId;
//
//	public String list_ajax() {
//
//		return "list";
//	}
//
//	public String add_input() {
//
//		return "add";
//	}
//
//	public String edit_input() {
//
//		return "edit";
//	}
//
//	public int getPageSize() {
//
//		return 10;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public int getPage() {
//		page = page < 1 ? 1 : page;
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//
//
//	public Map getSession() {
//		return session;
//	}
//
//	public void setSession(Map session) {
//		this.session = session;
//	}
//
//
//
//	protected void showSuccessJson(String message){
//		if(StringUtil.isEmpty(message))
//			this.json="{result:1}";
//		else
//			this.json="{result:1,message:'"+message+"'}";
//	}
//
//
//	protected void showErrorJson(String message){
//		if(StringUtil.isEmpty(message))
//			this.json="{result:0}";
//		else
//			this.json="{result:0,message:'"+message+"'}";
//	}
//
//
//
//	public List getMsgs() {
//		return msgs;
//	}
//
//	public void setMsgs(List msgs) {
//		this.msgs = msgs;
//	}
//
//	public Map getUrls() {
//		return urls;
//	}
//
//	public void setUrls(Map urls) {
//		this.urls = urls;
//	}
//
//	public Page getWebpage() {
//		return webpage;
//	}
//
//	public void setWebpage(Page webpage) {
//		this.webpage = webpage;
//	}
//
//	public String getScript() {
//		return script;
//	}
//
//	public void setScript(String script) {
//		this.script = script;
//	}
//
//	public String getJson() {
//		return json;
//	}
//
//	public void setJson(String json) {
//		this.json = json;
//	}
//
//	public String getExcel() {
//		return excel;
//	}
//
//	public void setExcel(String excel) {
//		this.excel = excel;
//	}
//
//	public String getTotalPage() {
//		return totalPage;
//	}
//
//	public void setTotalPage(String totalPage) {
//		this.totalPage = totalPage;
//	}
//
//	public String getCurrentPage() {
//		return currentPage;
//	}
//
//	public void setCurrentPage(String currentPage) {
//		this.currentPage = currentPage;
//	}
//
//}
