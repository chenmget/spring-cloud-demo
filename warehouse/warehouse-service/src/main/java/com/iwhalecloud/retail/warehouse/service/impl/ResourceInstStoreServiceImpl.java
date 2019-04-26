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
import com.iwhalecloud.retail.warehouse.manager.ResourceInstStoreManager;
import com.iwhalecloud.retail.warehouse.mapper.ResourceInstMapper;
import com.iwhalecloud.retail.warehouse.service.ResouceStoreService;
import com.iwhalecloud.retail.warehouse.service.ResourceInstStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static String[] brands = {"10350143", "10350148"};
    private static String[] types = {"1001", "1002", "1003"};

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
     * 入库：1001，调拨：1002，退库：1003
     * 统计时间和序列号都存放在SYS_CONFIG_INFO表中，
     * 其中统计时间的CF_ID是CONF_STR，一字符串的方式存储；生成文件的序列号的CF_ID是SEQ_CONF_STR，以json格式的字符串存储
     *
     * Created by jiyou on 2019/4/24.
     */
    @Override
    public void syncMktToITMS() {

        File dir = new File(basePath);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 开始时间
        String startStr = resourceInstMapper.findCfValueByCfId(CONF_STR);
        Date startDate = null;
        try {
            if (startStr != null) {
                startDate = sdf.parse(startStr);
            }
        } catch (ParseException e) {
            log.error("统计时间，字符串转换时间失败！");
        }

        // 结束时间
        Date endDate = new Date();

        // 初始化序列号
        String jsonSeqStr = resourceInstMapper.findCfValueByCfId(SEQ_CONF_STR);
        JSONObject jsonObject = null;
        if (jsonSeqStr == null) {
            jsonObject = initConfJsonStr();
        } else {
            jsonObject = JSON.parseObject(jsonSeqStr);
        }

        for (int i = 0; i < brands.length; i++) {
            for (int j = 0; j < types.length; j++) {
                this.syncMktToITMS(brands[i], types[j], startDate, endDate, jsonObject);
            }
        }

        if (startStr == null) {
            //初始化一条数据
            resourceInstMapper.initConfig(sdf.format(endDate));
        } else {
            resourceInstMapper.updateCfValueByCfId(CONF_STR, sdf.format(endDate));
        }

        //删除临时文件
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }

    }

    public void syncMktToITMS(String brand, String ops, Date startDate, Date endDate, JSONObject jsonObject) {

        List<String> files = new ArrayList<String>();

        // 查询所有地市
        List<String> latIdList = resourceInstMapper.findAllLanID();

        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
        String time = sdf.format(new Date());
        String opsTime = (String) jsonObject.get("ops_time");
        if (!opsTime.equals(time)) {
            jsonObject = initConfJsonStr();
        }

        List<String> mktList = null;
        PrintWriter pw = null;
        for (int i = 0; i < latIdList.size(); i++) {

            mktList = resourceInstMapper.findMKTInfoByLadId(latIdList.get(i), brand, ops, startDate, endDate);
            if (mktList.size() <= 0) continue;

            int seqNb = Integer.valueOf(jsonObject.get("ITMS_" + brand + "_" + ops).toString());
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
                pw.write(mktList.get(j));
            }
        }
        pw.close();

        resourceInstMapper.updateCfValueByCfId(SEQ_CONF_STR, jsonObject.toString());

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
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
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
    private void sendFileToFtp(FTPClient ftpClient, List<String> files, String brand, String ops) {
        String sendDir = null;
        // 判断传送目录
        if (brands[0].equals(brand)) {
            // 光猫
            if (types[0].equals(ops)) {
                sendDir = gmdirAdd;
            }
            if (types[1].equals(ops)) {
                sendDir = gmdirModify;
            }
            if (types[2].equals(ops)) {
                sendDir = gmdirDelete;
            }
        } else {
            // iptv
            if (types[0].equals(ops)) {
                sendDir = iptvdirAdd;
            }
            if (types[1].equals(ops)) {
                sendDir = iptvdirModify;
            }
            if (types[2].equals(ops)) {
                sendDir = iptvdirDelete;
            }
        }
        InputStream is = null;
        try {
            ftpClient.changeWorkingDirectory(sendDir);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //传输模式
            ftpClient.enterLocalPassiveMode();
            for (int i = 0; i < files.size(); i++) {
                is = new FileInputStream(basePath + File.separator + files.get(i));
                //字节数组
                ftpClient.storeFile(new String(), is);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
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

}
