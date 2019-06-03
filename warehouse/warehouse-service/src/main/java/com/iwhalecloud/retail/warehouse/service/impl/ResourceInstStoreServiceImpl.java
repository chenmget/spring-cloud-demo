package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.ConfigInfoDTO;
import com.iwhalecloud.retail.system.service.ConfigInfoService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.constant.Constant;
import com.iwhalecloud.retail.warehouse.dto.ResourceInstStoreDTO;
import com.iwhalecloud.retail.warehouse.dto.request.*;
import com.iwhalecloud.retail.warehouse.dto.response.GetProductQuantityByMerchantResp;
import com.iwhalecloud.retail.warehouse.dto.response.InventoryWarningResp;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsSyncRec;
import com.iwhalecloud.retail.warehouse.manager.ResourceInstStoreManager;
import com.iwhalecloud.retail.warehouse.mapper.MktResItmsSyncRecMapper;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ResourceInstStoreServiceImpl implements ResourceInstStoreService {

    @Value("${ftp.basePath}")
    private String basePath;

    @Value("${ftp.ftpAddress}")
    private String ftpAddress;
    @Value("${ftp.ftpPort}")
    private String ftpPort;
    @Value("${ftp.ftpUserName}")
    private String ftpUserName;
    @Value("${ftp.ftpPassword}")
    private String ftpPassword;

    @Value("${ftp.gmAdd}")
    private String gmAdd;
    @Value("${ftp.iptvAdd}")
    private String iptvAdd;

    @Value("${ftp.gmModify}")
    private String gmModify;
    @Value("${ftp.iptvModify}")
    private String iptvModify;

    @Value("${ftp.gmDelete}")
    private String gmDelete;
    @Value("${ftp.iptvDelete}")
    private String iptvDelete;

    @Value("${ftp.gmdirAdd}")
    private String gmdirAdd;
    @Value("${ftp.iptvdirAdd}")
    private String iptvdirAdd;

    @Value("${ftp.gmdirModify}")
    private String gmdirModify;
    @Value("${ftp.iptvdirModify}")
    private String iptvdirModify;

    @Value("${ftp.gmdirDelete}")
    private String gmdirDelete;
    @Value("${ftp.iptvdirDelete}")
    private String iptvdirDelete;
    @Value("${ftp.extName}")
    private String extName;

    private static String[] brands = {"10350143", "10350148"};
    private static String[][] types = {{"1001", "1003"}, {"1002"}, {"1007", "1009"}};

    private static final String SEQ_CONF_STR = "ITMS_PUSH_SEQ";
    private static final String CONF_STR = "ITMS_PUSH_MKT_NB";

    @Autowired
    private ResourceInstStoreManager resourceInstStoreManager;

    @Autowired
    private ResourceInstMapper resourceInstMapper;

    @Reference
    private ConfigInfoService configInfoService;

    @Reference
    private ResouceStoreService resouceStoreService;

    @Autowired
    private Constant constant;

    @Autowired
    private MktResItmsSyncRecMapper mktResItmsSyncRecMapper;


    @Override
    public ResultVO<Integer> getQuantityByMerchantId(String merchantId) {
        List<String> statusList = new ArrayList<>();
        statusList.add(ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode());
        Integer amount = resourceInstStoreManager.getQuantityByMerchantId(merchantId, statusList);
        log.info("ResourceInstStoreServiceImpl.getQuantityByMerchantId esourceInstStoreManager.getQuantityByMerchantId req.merchantId={}, req.statusList={}, resp={}", merchantId, JSON.toJSONString(statusList), amount);
        return ResultVO.success(amount);
    }

    @Override
    public ResultVO<GetProductQuantityByMerchantResp> getProductQuantityByMerchant(GetProductQuantityByMerchantReq req) {
        List<ProductQuantityItem> itemList = req.getItemList();
        StringBuffer stringBuffer = new StringBuffer("");
        GetProductQuantityByMerchantResp resp = new GetProductQuantityByMerchantResp();
        List<ProductQuantityItem> itemListResp = new ArrayList<>();
        boolean flag = true;
        for (ProductQuantityItem item : itemList) {
            StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
            storeGetStoreIdReq.setMerchantId(req.getMerchantId());
            storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
            String storeId = resouceStoreService.getStoreId(storeGetStoreIdReq);
            if (StringUtils.isBlank(storeId)) {
                return ResultVO.error(constant.getCannotGetStoreMsg());
            }
            String statusCd = ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode();
            ResourceInstStoreDTO dto = resourceInstStoreManager.getStore(req.getMerchantId(), item.getProductId(), ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode(), storeId);
            log.info("ResourceInstStoreServiceImpl.getProductQuantityByMerchant resourceInstStoreManager.getStore req.merchantId={}, req.productId={}, req.statusList={}, resp={}", req.getMerchantId(), item.getProductId(), JSON.toJSONString(statusCd), JSON.toJSONString(dto));
            ProductQuantityItem itemResp = new ProductQuantityItem();
            if (null == dto) {
                stringBuffer.append(item.getProductId() + ":库存不够;");
                flag = false;
                itemResp.setProductId(item.getProductId());
                itemResp.setNum(0L);
                itemResp.setIsEnough(false);
            } else {
                Long quantity = dto.getQuantity();
                Long onwayQuantity = dto.getOnwayQuantity();
                //库存数量- 在途数量 - 下单数量 小于0 则表示无库存
                if (Long.compare(quantity - onwayQuantity, item.getNum()) < 0) {
                    stringBuffer.append(item.getProductId() + ":库存不够;");
                    flag = false;
                    itemResp.setIsEnough(false);
                } else {
                    itemResp.setIsEnough(true);
                }
                itemResp.setProductId(item.getProductId());
                itemResp.setNum(quantity - onwayQuantity - item.getNum());
            }
            itemListResp.add(itemResp);
        }
        resp.setItemList(itemListResp);
        resp.setInStock(flag);
        return ResultVO.success(stringBuffer.toString(), resp);
    }

    @Override
    public ResultVO updateStock(UpdateStockReq req) {
        int i = this.resourceInstStoreManager.updateStock(req);
        log.info("ResourceInstStoreServiceImpl.updateStock resourceInstStoreManager.updateStock req={}, resp={}", JSON.toJSONString(req), i);
        if (i > 0) {
            return ResultVO.success();
        } else {
            return ResultVO.error("没有更新成功");
        }
    }

    @Override
    public ResultVO updateResourceInstStore(ResourceInstStoreDTO req) {
        return ResultVO.success(resourceInstStoreManager.updateResourceInstStore(req));
    }

    @Override
    public ResultVO<List<InventoryWarningResp>> queryInventoryWarning(List<InventoryWaringReq> req) {
        log.info("ResourceInstStoreServiceImpl.queryInventoryWarning req={}", JSON.toJSONString(req));
        List<InventoryWarningResp> resp = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(req)) {
            req.forEach(item -> {
                StoreGetStoreIdReq storeGetStoreIdReq = new StoreGetStoreIdReq();
                storeGetStoreIdReq.setMerchantId(item.getMerchantId());
                storeGetStoreIdReq.setStoreSubType(ResourceConst.STORE_SUB_TYPE.STORE_TYPE_TERMINAL.getCode());
                String storeId = resouceStoreService.getStoreId(storeGetStoreIdReq);
                Long quantity = 0L;
                Long onWayQuantity = 0L;
                if (!StringUtils.isBlank(storeId)) {
                    ResourceInstStoreDTO dto = resourceInstStoreManager.getStore(item.getMerchantId(), item.getProductId(), ResourceConst.StatusCdEnum.STATUS_CD_VALD.getCode(), storeId);
                    quantity = null == dto ? 0L : dto.getQuantity();
                    onWayQuantity = null == dto ? 0L : dto.getOnwayQuantity();
                }

                InventoryWarningResp inventoryWarningResp = new InventoryWarningResp();
                inventoryWarningResp.setProductId(item.getProductId());
                inventoryWarningResp.setMerchantId(item.getMerchantId());

                ConfigInfoDTO configInfo = configInfoService.getConfigInfoById(ResourceConst.INVENTORY_CONFIG_ID);
                Long inventoryNum = Long.valueOf(configInfo.getCfValue());
                //库存总数量 - 在途数量 - 预警数量 小于0 则预警提示
                if (Long.compare(quantity - onWayQuantity, inventoryNum) < 0) {
                    inventoryWarningResp.setInventoryNum(quantity - onWayQuantity);
                    inventoryWarningResp.setIsInventory(true);
                } else {
                    inventoryWarningResp.setIsInventory(false);
                }
                resp.add(inventoryWarningResp);
            });
        }
        log.info("ResourceInstStoreServiceImpl.queryInventoryWarning resp={}", JSON.toJSONString(resp));
        return ResultVO.success(resp);
    }

    /**
     * 根据地市生成相关的文件
     * 光猫：10350143，IPTV：10350148
     * 新增：1001,1003, 修改：1002, 删除：1007,1009
     * 统计时间和序列号都存放在SYS_CONFIG_INFO表中，
     * 其中统计时间的CF_ID是CONF_STR，一字符串的方式存储；
     *
     * Created by jiyou on 2019/4/24.
     * updated by xieqi on 2019/5/15
     */
    @Override
    public void syncMktToITMS() {

        File dir = new File(basePath);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        // 开始时间
        String startStr = resourceInstMapper.findCfValueByCfId(CONF_STR);

        int num = insertToMRISR(startStr);

        //没有待推送数据
        if(num == 0){
        	log.info(startStr+"ITMS没有待推送数据。");
        	return;
        }
    	log.info(startStr+"成功插入到 MktResItmsSyncRec表 "+num+"条数据。");

        // 结束时间
        Date endDate = new Date();

        for (int i = 0; i < brands.length; i++) {
            for (int j = 0; j < types.length; j++) {
                this.syncMktToITMS(brands[i], types[j], startStr);
            }
        }

        //更新时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = new Date();
        if (null == startStr) {
            resourceInstMapper.initConfig(sdf.format(startDate));
        } else {
            resourceInstMapper.updateCfValueByCfId(CONF_STR, sdf.format(startDate));
        }

        //删除临时文件
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }

    }

    public void syncMktToITMS(String brand, String[] ops, String startDate) {

        List<String> files = new ArrayList<String>();

        // 查询所有地市
        List<String> latIdList = resourceInstMapper.findAllLanID();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String time = sdf.format(new Date());

        List<Map<String, String>> mktList = null;
        PrintWriter pw = null;
        for (int i = 0; i < latIdList.size(); i++) {

        	String sendDir = getSendDir(brand, ops);
        	mktList = mktResItmsSyncRecMapper.findMKTInfoByLadId(latIdList.get(i), brand, ops, startDate);
        	if (mktList.size() <= 0) continue;

        	String seqStr = mktResItmsSyncRecMapper.getSeqBysendDir(sendDir+"/"+latIdList.get(i));
        	String resStr = "0";
        	if(StringUtils.isNotBlank(seqStr)){
        		resStr = seqStr.substring(seqStr.length()-3,seqStr.length());
        	}
        	int seqNb = Integer.parseInt(resStr);
        	seqNb++;
        	String seq = getSeqStr(seqNb);
            String destFileName = latIdList.get(i) + "ITMS" + time + seq + ".txt";
            File destFile = new File(basePath + File.separator + destFileName);
            files.add(destFileName);
            pw = getPrintWriter(destFile);

            for (int j = 0; j < mktList.size(); j++) {
                if (j > 0 && j % 10000 == 0) {
                    seqNb++;
                    seq = getSeqStr(seqNb);
                    destFileName = latIdList.get(i) + "ITMS" + time + seq + ".txt";
                    destFile = new File(basePath + File.separator + destFileName);
                    files.add(destFileName);
                    pw = getPrintWriter(destFile);
                }
                pw.write(mktList.get(j).get("itmsStr"));
                pw.println();

                String syncFileName = sendDir+"/"+destFileName;
                mktResItmsSyncRecMapper.updateFileNameById(mktList.get(j).get("id"), syncFileName, time + seq);

            }
        }
        if(pw != null){
        	pw.close();
        }

        FTPClient ftpClient = connectedToftpServer();
        sendFileToFtp(ftpClient, files, brand, ops);
        files.clear();

    }

    /**
     * 将序列号转换成字符串
     *
     * @param seqNb
     * @return
     */
    private String getSeqStr(int seqNb) {
        String seq = "";
        if (seqNb < 10) {
            seq = "00" + seqNb;
        } else if (seqNb < 100) {
            seq = "0" + seqNb;
        } else {
            seq = "" + seqNb;
        }
        return seq;
    }

    /**
     * 初始化配置
     *
     * @return
     */
    private JSONObject initConfJsonStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ops_time", sdf.format(new Date()));

        for (int i = 0; i < brands.length; i++) {
            for (int j = 0; j < types.length; j++) {
                jsonObject.put("ITMS_" + brands[i] + "_" + types[j], 1);
            }
        }

        return jsonObject;
    }

    /**
     * 获取输出对象
     *
     * @param file
     * @return
     */
    private PrintWriter getPrintWriter(File file) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"), true);
        } catch (UnsupportedEncodingException e) {
            log.error("串码入库，与ITMS集成" + e.getMessage(), e);
        } catch (FileNotFoundException e) {
            log.error("串码入库，与ITMS集成" + e.getMessage(), e);
        }
        return pw;
    }

    /**
     * 传送文件
     *
     * @param ftpClient
     * @param files
     */
    private void sendFileToFtp(FTPClient ftpClient, List<String> files, String brand, String[] ops) {
        String sendDir = null;
        // 判断传送目录
        sendDir = getSendDir(brand, ops);
        InputStream is = null;
        try {
            Boolean flag = ftpClient.changeWorkingDirectory(sendDir);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //传输模式
            ftpClient.enterLocalPassiveMode();
            for (int i = 0; i < files.size(); i++) {
                is = new FileInputStream(basePath + File.separator + files.get(i));
                //字节数组
                ftpClient.storeFile(files.get(i), is);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 回传文件
     *
     * @param ftpClient
     * @param files
     */
    private List<String> getFileFromFtp(FTPClient ftpClient, List<String> files, String getDir) {
//        String sendDir = null;
        // 判断传送目录
//        sendDir = getSendDir(brand, ops);
        InputStream is = null;
        List<String> lineList = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        try {
            Boolean flag = ftpClient.changeWorkingDirectory(getDir);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //传输模式
            ftpClient.enterLocalPassiveMode();
            for (int i = 0; i < files.size(); i++) {
                lines = readFile(ftpClient, files.get(i));
                if(!CollectionUtils.isEmpty(lines)){
                    lineList.addAll(lines);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return lineList;
    }
    /**
     * 回传文件
     *
     * @param ftpClient
     * @param fileName
     * @param getDir
     */
    private ArrayList<String> changeFtpDir(FTPClient ftpClient,String fileName, String getDir) {
//        String sendDir = null;
        // 判断传送目录
//        sendDir = getSendDir(brand, ops);
        InputStream is = null;
//        List<String> lineList = new ArrayList<>();
        ArrayList<String> lineList = new ArrayList<>();
        try {
            Boolean flag = ftpClient.changeWorkingDirectory(getDir);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //传输模式
            ftpClient.enterLocalPassiveMode();
//            for (int i = 0; i < files.size(); i++) {
            lineList = readFile(ftpClient, fileName);
//                if(!CollectionUtils.isEmpty(lines)){
//                    lineList.addAll(lines);
//                }
//            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return lineList;
    }

    public ArrayList<String> readFile(FTPClient ftpClient, String fileName) {
        InputStream ins = null;
//        StringBuilder builder = new StringBuilder();
        ArrayList<String> lineLists = new ArrayList<>();
        try {
            // 从服务器上读取指定的文件
//            System.out.println("文件名："+fileName);
            ins = ftpClient.retrieveFileStream(fileName);
            if(ins != null){
                BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
                String line;
//            builder = new StringBuilder(150);
                while ((line = reader.readLine()) != null) {
                    lineLists.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != ins) {
                try {
                    ins.close();
                    ftpClient.completePendingCommand();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return lineLists;
    }
    /**
     * 创建FTPClient客户端
     *
     * @return
     * @throws SocketException
     * @throws IOException
     */
    private FTPClient connectedToftpServer() {

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpAddress, Integer.valueOf(ftpPort));
            ftpClient.login(ftpUserName, ftpPassword);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                ftpClient.getReplyCode();
            }
        } catch (Exception e) {
            log.error("ftp创建连接失败！");
        }
        return ftpClient;
    }

    /**
     * 数据插入到MKT_RES_ITMS_SYNC_REC表
     *
     * @return
     * @throws
     */
    private int insertToMRISR(String startDate) {
        List<MktResItmsSyncRec> mktResItmsSyncRecList = mktResItmsSyncRecMapper.findMKTInfoByDate(startDate);
        int num = 0;
        if(mktResItmsSyncRecList.size()>0){
            for(MktResItmsSyncRec mktResItmsSyncRec : mktResItmsSyncRecList){
                mktResItmsSyncRecMapper.insert(mktResItmsSyncRec);
                num++;
            }
        }
        return num;
    }

    //获取返回的文件路径
    public String getReturnDir(String brand, String[] ops){
        String baseDir = "/home/itsm_y/itmsfile";
        String sendDir = "";
        if (brands[0].equals(brand)) {
            // 光猫
            if (Arrays.equals(types[0],ops)) {
                sendDir = gmdirAdd;
            }
            if (Arrays.equals(types[1],ops)) {
                sendDir = gmdirModify;
            }
            if (Arrays.equals(types[2],ops)) {
                sendDir = gmdirDelete;
            }
        } else {
            // iptv
            if (Arrays.equals(types[0],ops)) {
                sendDir = iptvdirAdd;
            }
            if (Arrays.equals(types[1],ops)) {
                sendDir = iptvdirModify;
            }
            if (Arrays.equals(types[2],ops)) {
                sendDir = iptvdirDelete;
            }
        }
        return sendDir;
    }

    public String getSendDir(String brand, String[] ops){
        String baseDir = "/home/itsm_y/itmsfile";
    	String sendDir = "";
    	if (brands[0].equals(brand)) {
            // 光猫
            if (Arrays.equals(types[0],ops)) {
                sendDir = gmAdd;
            }
            if (Arrays.equals(types[1],ops)) {
                sendDir = gmModify;
            }
            if (Arrays.equals(types[2],ops)) {
                sendDir = gmDelete;
            }
        } else {
            // iptv
            if (Arrays.equals(types[0],ops)) {
                sendDir = iptvAdd;
            }
            if (Arrays.equals(types[1],ops)) {
                sendDir = iptvModify;
            }
            if (Arrays.equals(types[2],ops)) {
                sendDir = iptvDelete;
            }
        }
        return baseDir+sendDir;
    }

    /**
     * 从mkt_res_itms_sync_rec表查询未更新过的文件
     * 根据文件路径和文件名到服务器上读取相关文件
     * 将读取到的数据更新到mkt_res_itms_sync_rec表
     */
    @Override
    public void syncMktToITMSBack(){
        log.info("ResourceInstStoreServiceImpl.syncMktToITMSBack ", "开始读取ITMS文件回执");
        FTPClient ftpClient = connectedToftpServer();
        try {
//            String pathName = null;
            List<String> pathNameList = new ArrayList<>();
            List<String[]> filePathList = new ArrayList<>();
            //文件内容List
            ArrayList<String> lineList = new ArrayList<>();
            pathNameList.add(gmdirAdd);
            pathNameList.add(iptvdirAdd);
            pathNameList.add(gmdirModify);
            pathNameList.add(iptvdirModify);
            pathNameList.add(gmdirDelete);
            pathNameList.add(iptvdirDelete);
            for (int i = 0; i < pathNameList.size(); i++) {
                //获取文件路径
                filePathList = this.filePathList(pathNameList.get(i), ftpClient);
                if(CollectionUtils.isEmpty(filePathList) || StringUtils.isEmpty(pathNameList.get(i))){
                    continue;
                }
                for (int j = 0; j < filePathList.size(); j++) {
                    lineList = changeFtpDir(ftpClient, filePathList.get(j)[0], pathNameList.get(i));
                    if (!CollectionUtils.isEmpty(lineList)) {
                        //插入数据
                        insertDate(lineList, filePathList.get(j)[1]);
                        //移动备份文件
                        this.deleteFile(ftpClient, pathNameList.get(i), filePathList.get(j)[0]);

                    }
                }
            }
            ftpClient.logout();
            log.info("ResourceInstStoreServiceImpl.syncMktToITMSBack  读取ITMS文件回执结束");
        }catch (Exception e){
            log.error("ResourceInstStoreServiceImpl.syncMktToITMSBack error{}"+e.getMessage());
        }finally {
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取文件目录
     * @param syncFileName
     * @return
     */
    private String getDir(String syncFileName){
        String sendDir = syncFileName.replace("/back/","/back/Failure/");
        sendDir = sendDir.substring(0,sendDir.length()-23);
        return sendDir;
    }

    /**
     * 获取成功和失败的文件名
     * @param syncFileName
     * @return
     */
    private List<String> getFileName(String syncFileName){

        List<String> fileName = new ArrayList<>();
        try {
            if(!syncFileName.isEmpty()){
                String[] sort = syncFileName.split("/");
                fileName.add(sort[8]);
                fileName.add(sort[8].replace("ITMS","ITMSSuccess"));
            }
        }catch (Exception e){
        }
        return fileName;
    }
    /**
     * 插数据到数据库
     * @param lineList
     * @param syncFileName
     */
    private void insertDate(ArrayList<String> lineList,String syncFileName){
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        String[] line = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfTem = new SimpleDateFormat("yyyyMMdd");
        Date startDate = new Date();
        String date = sdf.format(startDate);
        String dateTem = sdfTem.format(startDate);
        try{
            list = this.lineListSplit(lineList, 1000);
            for(int i = 0; i < list.size(); i++){
                List<MktResItmsSyncRec> dataList = Lists.newArrayList();
                if(list.get(i).size() == 0){
                    continue;
                }
                for(int j = 0; j < list.get(i).size(); j++){
                    line = list.get(i).get(j).split(",");
                    MktResItmsSyncRec mktData = new MktResItmsSyncRec();
                    mktData.setSyncDate(date);
                    mktData.setSyncFileName(dateTem);
                    mktData.setMktResInstNbr(line[0].toString());
                    mktData.setStatusCd(line[1].toString());
                    mktData.setSyncDate(date);
                    mktData.setCreateDate(date);
                    mktData.setStatusDate(date);
                    mktData.setSyncBatchId(dateTem);
                    mktData.setMktResEventId(dateTem);
                    mktData.setMktResChngEvtDetailId(dateTem);
                    mktData.setBrandId(dateTem);
//                    mktResItmsSyncRecMapper.insert(mktData);
                    dataList.add(mktData);

                }
                //批量插入
                if(dataList.size()>0){
                    resourceInstStoreManager.batchAddMKTInfo(dataList);
                    log.info("syncMktToITMSBack insertDate插入"+dataList.size()+"条数据成功");
                }
            }
        }catch (Exception e){
            log.error("syncMktToITMSBack insertDate插入数据失败!");
        }

//        mktResItmsSyncRecMapper.batchUpdateMRIyFileName(dataList);

    }

    /**
     * 递归遍历目录下面指定的文件名
     *
     * @param pathName 需要遍历的目录，必须以"/"开始和结束
     * @param ftp      ftp客户端
     * @throws IOException
     */
    public List<String[]> filePathList(String pathName, FTPClient ftp) {
        String[] filePathList = null;
        List<String[]> list = new ArrayList<>();
        try {
            if (pathName.startsWith("/") && pathName.endsWith("/")) {
                //更换目录到当前目录
                ftp.changeWorkingDirectory(pathName);
                FTPFile[] files = ftp.listFiles();
                for (FTPFile file : files) {
                    if (file.isFile()) {
                        if (file.getName().endsWith(extName)) {
                            filePathList = new String[2];
                            filePathList[0] = file.getName();
                            filePathList[1] = pathName + file.getName();
                        }
                    } else if (file.isDirectory()) {
                        //路径下级文件
                    /*if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                        filePathList(pathName + file.getName() + "/", ext);
                    }*/
                    }
                    if(filePathList != null||filePathList.length != 0){
                        list.add(filePathList);
                    }

                }
            }
        }catch (IOException e){

        }
        return list;
    }

    /**
     * 将list以每num条拆分为多个list
     * @param lineList
     * @param num
     * @return
     */
    private ArrayList<ArrayList<String>> lineListSplit(ArrayList<String> lineList, int num){
        ArrayList<ArrayList<String>> allLineLists = new ArrayList<>();
        int number;
        if(lineList.size() > num){
            //判断要分割的个数
            if(lineList.size() % num > 0){
                number = lineList.size() / num + 1;
            }else{
                number = lineList.size() / num;
            }
            // 循环把分割的数组存入 userIdLists 中
            for (int i = 0; i < number; i++) {
                ArrayList<String> list = new ArrayList<>();
                // 最后一个数组长度不定
                if (i != number - 1) {
                    for (int j = 0; j < num; j++) {
                        list.add(lineList.get(i * num + j));
                    }
                } else {
                    for (int j = 0; j < lineList.size() - (i * num); j++) {
                        list.add(lineList.get(i * num + j));
                    }
                }
                allLineLists.add(list);
            }
        }else {
            allLineLists.add(lineList);
        }
        return allLineLists;
    }

    /** * 移动文件 *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要移动的文件名称 *
     * @return */
    public boolean deleteFile(FTPClient ftpClient,String pathname, String filename){
        boolean flag = false;
        try {
            System.out.println("开始移动文件");
            String newFilePathName = pathname.replace("data/back/Failure","bakfile")+filename;
            ftpClient.rename(filename,newFilePathName);
            flag = true;
//            System.out.println("移动文件成功pathname:"+pathname+"     newFilePathName:"+newFilePathName);
        } catch (Exception e) {
//            System.out.println("移动文件失败");
            e.printStackTrace();
        }
        return flag;
    }


}
