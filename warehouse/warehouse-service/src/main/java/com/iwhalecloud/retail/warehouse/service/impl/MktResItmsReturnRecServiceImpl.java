package com.iwhalecloud.retail.warehouse.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.iwhalecloud.retail.system.dto.PublicDictDTO;
import com.iwhalecloud.retail.system.service.PublicDictService;
import com.iwhalecloud.retail.warehouse.common.ResourceConst;
import com.iwhalecloud.retail.warehouse.dto.FtpDTO;
import com.iwhalecloud.retail.warehouse.entity.MktResItmsReturnRec;
import com.iwhalecloud.retail.warehouse.manager.MktResItmsReturnRecManager;
import com.iwhalecloud.retail.warehouse.service.MktResItmsReturnRecService;
import com.iwhalecloud.retail.warehouse.util.FtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class MktResItmsReturnRecServiceImpl implements MktResItmsReturnRecService {

    @Reference
    private PublicDictService publicDictService;

    @Autowired
    private MktResItmsReturnRecManager mktResItmsReturnRecManager;

    @Autowired
    private MktResItmsSyncRecServiceImpl mktResItmsSyncRecServiceImpl;
    /**
     * 从mkt_res_itms_sync_rec表查询未更新过的文件
     * 根据文件路径和文件名到服务器上读取相关文件
     * 将读取到的数据更新到mkt_res_itms_sync_rec表
     */
    @Override
    public void syncMktToITMSBack(){
        log.info("MktResItmsReturnRecServiceImpl.syncMktToITMSBack 开始读取ITMS回执文件");
        FtpDTO ftpInfo = mktResItmsSyncRecServiceImpl.getftpDTO();
        if(null != ftpInfo){
            try{
                this.syncMktToITMSBack(ftpInfo);
                log.info("MktResItmsReturnRecServiceImpl.syncMktToITMSBack 完成读取ITMS回执文件");
            }catch (Exception e){
                log.info("MktResItmsReturnRecServiceImpl.syncMktToITMSBack ", "读取回执文件异常");
            }
        }else{
            log.info("MktResItmsReturnRecServiceImpl.syncMktToITMSBack 获取ftp信息异常");
        }

    }
    private void syncMktToITMSBack(FtpDTO ftpInfo) throws Exception{
        FTPClient ftpClient = FtpUtils.connectFtp(ftpInfo);
        List<String> fileList = new ArrayList<>();
        //文件内容List
        ArrayList<String> lineList = new ArrayList<>();
        try {
            //查询ITMS回执文件路径信息
            List<PublicDictDTO> publicDictDTOS = publicDictService.queryPublicDictListByType(ResourceConst.DICT_TYPE_FTP_ITMS_RETURN_PATH);
            if(!CollectionUtils.isEmpty(publicDictDTOS)) {
                for (PublicDictDTO publicDictDTO : publicDictDTOS) {
                    //ITMS回传文件路径
                    String filePath = publicDictDTO.getCodec()+"/";
                    //ITMS备份文件路径
                    String backFilePath = publicDictDTO.getCoded()+"/";
                    //文件后缀
                    String ext = publicDictDTO.getCodeb();
                    //遍历目录下文件
                    fileList = FtpUtils.fileList(ftpClient, filePath, ext);
                    if(!CollectionUtils.isEmpty(fileList) ){
                        for (int j = 0; j < fileList.size(); j++) {
                            //进入指定目录
                            ftpClient = FtpUtils.changeFtpDir(ftpClient, filePath);
                            //读取文件
                            lineList = FtpUtils.readFile(ftpClient, fileList.get(j));
                            if (!CollectionUtils.isEmpty(lineList)) {
                                //插入数据
                                Boolean flag = insertDate(lineList, fileList.get(j));
                                if(flag){
                                    //移动备份回执文件
                                    FtpUtils.copyFile(ftpClient, backFilePath.concat(fileList.get(j)), fileList.get(j));
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            log.error("MktResItmsReturnRecServiceImpl.syncMktToITMSBack error{}"+e.getMessage());
        }finally {
            FtpUtils.closeServer(ftpClient);
        }
    }


    /**
     * 插数据到数据库
     * @param lineList
     * @param syncFileName
     */
    private Boolean insertDate(ArrayList<String> lineList,String syncFileName){
        Boolean flag = false;
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        String[] line = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfTem = new SimpleDateFormat("yyyyMMdd");
        Date startDate = new Date();
        String date = sdf.format(startDate);
        String dateTem = sdfTem.format(startDate);
        try{
            list = this.lineListSplit(lineList);
            for(int i = 0; i < list.size(); i++){
                List<MktResItmsReturnRec> dataList = Lists.newArrayList();
                if(!CollectionUtils.isEmpty(list.get(i))){
                    for(int j = 0; j < list.get(i).size(); j++){
                        line = list.get(i).get(j).split(",");
                        MktResItmsReturnRec mktData = new MktResItmsReturnRec();
                        mktData.setReturnFileName(syncFileName);
                        mktData.setMktResInstNbr(line[0].toString());
                        mktData.setStatusCd(line[1].toString());
                        mktData.setCreateStaff("admin");
                        mktData.setCreateDate(startDate);
                        dataList.add(mktData);
                    }
                }
                //批量插入
                if(!CollectionUtils.isEmpty(dataList)){
                    try{
                        mktResItmsReturnRecManager.batchAddMKTReturnInfo(dataList);
                        log.info("syncMktToITMSBack insertDate插入"+dataList.size()+"条数据成功");
                    }catch (Exception e){
                        log.error("syncMktToITMSBack insertDate插入"+e.getMessage());
                    }
                }
            }
            flag = true;
        }catch (Exception e){
            log.error("syncMktToITMSBack insertDate插入文本数据"+syncFileName+"失败! ",e.getMessage());
        }
        return flag;
    }

    /**
     * 将list以每num条拆分为多个list
     * @param lineList
     * @return
     */
    private ArrayList<ArrayList<String>> lineListSplit(ArrayList<String> lineList){
        ArrayList<ArrayList<String>> allLineLists = new ArrayList<>();
        int num = ResourceConst.EVERY_PART_NUM;
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
    
}