package com.iwhalecloud.retail.pay.controller;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.pay.handler.BestpayHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/6.
 */
@Controller
@RequestMapping(path = "/cah")
@Slf4j
public class BestpayController {
    @Value("${spring.profiles.active}")
    private String activeProfile;

    public String getActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    @Autowired
    private BestpayHandler bestpayHandler;

    public BestpayHandler getBestpayHandler() {
        return bestpayHandler;
    }

    public void setBestpayHandler(BestpayHandler bestpayHandler) {
        this.bestpayHandler = bestpayHandler;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Value("${asynNotify.URL}")
    private String asynNotifyUrl;

    @RequestMapping("/synnotify")
    public void synNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("{}:接收到同步回调", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS").format(LocalDateTime.now()));
        Enumeration<String> parameterNames= request.getParameterNames();
        Map<String,String> params=new HashMap<>();
        while (parameterNames.hasMoreElements()){
            String parameterName=parameterNames.nextElement();
            String parameterValue=request.getParameter(parameterName);
            params.put(parameterName,parameterValue);
        }
        log.info("接收到的同步回调参数为：{}", net.sf.json.JSONObject.fromObject(params).toString());
        boolean ret=verify(params);
        if(ret){
            log.info("翼支付同步回调验签通过");

        }else{
            log.info("翼支付同步回调验签不通过");
        }
        Map<String,String> model=new HashMap<String,String>();

        model.put("ORDERID",params.get("ORDERID"));
        model.put("ORDERAMOUNT",params.get("ORDERAMOUNT"));
        model.put("ORDERSTATUS",params.get("ORDERSTATUS"));
        model.put("VERIFYRESULT",String.valueOf(ret));
        rediretByPost("/cah/notifyResult",response, model);
    }

    @RequestMapping("/asynnotify")
    public @ResponseBody  String  asynNotify(HttpServletRequest request) throws Exception {
        log.info("{}:接收到异步回调", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS").format(LocalDateTime.now()));
        Enumeration<String> parameterNames= request.getParameterNames();
        Map<String,String> params=new HashMap<>();
        while (parameterNames.hasMoreElements()){
            String parameterName=parameterNames.nextElement();
            String parameterValue=request.getParameter(parameterName);
            params.put(parameterName,parameterValue);
        }
        log.info("接收到的异步回调参数为：{}", net.sf.json.JSONObject.fromObject(params).toString());
        boolean ret=verify(params);
        log.info("异步回调验签结果:{}", ret);
        if(ret){
            log.info("翼支付异步响应验签通过");
            try {
                MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
                postParameters.add("ORGLOGINCODE", params.get("ORGLOGINCODE"));
                postParameters.add("PLATCODE", params.get("PLATCODE"));
                postParameters.add("CUSTCODE", params.get("CUSTCODE"));
                postParameters.add("ORDERID", params.get("ORDERID"));
                postParameters.add("TRSSEQ", params.get("TRSSEQ"));
                postParameters.add("ORDERAMOUNT", params.get("ORDERAMOUNT"));
                postParameters.add("FEE", params.get("FEE"));
                postParameters.add("TRANSDATE", params.get("TRANSDATE"));
                postParameters.add("ORDERSTATUS", params.get("ORDERSTATUS"));
                postParameters.add("BANKCODE", params.get("BANKCODE"));
                postParameters.add("PERENTFLAG", params.get("PERENTFLAG"));
                postParameters.add("COMMENT1", params.get("COMMENT1"));
                postParameters.add("COMMENT2", params.get("COMMENT2"));
                postParameters.add("SIGNSTR", params.get("SIGNSTR"));
                postParameters.add("IOUSAMOUNT", params.get("IOUSAMOUNT"));
                postParameters.add("ORDERTYPE", params.get("ORDERTYPE"));
                ResultVO resultVO = restTemplate.postForObject(asynNotifyUrl, postParameters, ResultVO.class);
            } catch (Exception e) {
                log.error("notify web fail", e);
            }
           return  "BUSINESS_CONFIRM";
        }else{
            log.info("翼支付异步响应验签不通过！");
            return "VERIFY_FAILED";
        }
    }
    @RequestMapping("/notifyResult")
    public ModelAndView notifyResult(HttpServletRequest request){
        String ORDERID=request.getParameter("ORDERID");
        String ORDERAMOUNT=request.getParameter("ORDERAMOUNT");
        String ORDERSTATUS=request.getParameter("ORDERSTATUS");
        String VERIFYRESULT=request.getParameter("VERIFYRESULT");
        System.out.println(request.getAttribute("ORDERID"));
        request.setAttribute("ORDERID",ORDERID);
        request.setAttribute("ORDERAMOUNT",ORDERAMOUNT);
        request.setAttribute("ORDERSTATUS",ORDERSTATUS);
        request.setAttribute("VERIFYRESULT",VERIFYRESULT);
        ModelAndView modelAndView=new ModelAndView("notifyResult.html");
        return modelAndView;
    }

    private boolean verify(Map<String,String> params){
        boolean ret=false;
        StringBuffer sb=new StringBuffer();
        sb.append("ORGLOGINCODE=").append(params.get("ORGLOGINCODE")).append("&");
        sb.append("PLATCODE=").append(params.get("PLATCODE")).append("&");
        sb.append("CUSTCODE=").append(params.get("CUSTCODE")).append("&");
        sb.append("ORDERID=").append(params.get("ORDERID")).append("&");
        sb.append("TRSSEQ=").append(params.get("TRSSEQ")).append("&");
        sb.append("ORDERAMOUNT=").append(params.get("ORDERAMOUNT")).append("&");
        sb.append("FEE=").append(params.get("FEE")).append("&");
        sb.append("TRANSDATE=").append(params.get("TRANSDATE")).append("&");
        sb.append("ORDERSTATUS=").append(params.get("ORDERSTATUS")).append("&");
        sb.append("BANKCODE=").append(params.get("BANKCODE")).append("&");
        sb.append("PERENTFLAG=").append(params.get("PERENTFLAG")).append("&");
        sb.append("COMMENT1=").append(params.get("COMMENT1")).append("&");
        sb.append("COMMENT2=").append(params.get("COMMENT2"));
        log.info("验签原文：{}", sb.toString());
        ret=bestpayHandler.verify(sb.toString(),params.get("SIGNSTR"),BestpayHandler.SIGNATURE_ALGORITHM_SHA1);
        return ret;
    }

    private void rediretByPost(String URL,HttpServletResponse response,Map<String,String>params) throws Exception{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println(" <HEAD><TITLE>sender</TITLE></HEAD>");
        out.println(" <BODY>");
        out.println("<form name=\"submitForm\" action=\""+URL+"\" method=\"post\">");
        Iterator<String> it=params.keySet().iterator();
        while(it.hasNext())
        {
            String key=it.next();
            out.println("<input type=\"hidden\" name=\""+key+"\" value=\""+params.get(key)+"\"/>");
        }
        out.println("</from>");
        out.println("<script>window.document.submitForm.submit();</script> ");
        out.println(" </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

}
