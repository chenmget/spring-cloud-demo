package com.iwhalecloud.retail.web.controller.fdfs;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.oms.OmsCommonConsts;
import com.iwhalecloud.retail.oms.common.ResultCodeEnum;
import com.iwhalecloud.retail.oms.dto.response.CommonResultResp;
import com.iwhalecloud.retail.oms.dto.resquest.FileManagerDTO;
import com.iwhalecloud.retail.oms.service.FileManagerService;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.controller.BaseController;
import com.iwhalecloud.retail.web.controller.fdfs.req.FileBase64UploadReq;
import com.iwhalecloud.retail.web.utils.ResponseComUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/fileManager")
@Slf4j
public class FileManagerController extends BaseController {

    @Resource
    private FileManagerService fileManagerService;

    @Value("${fdfs.suffix.allowUpload}")
    private String allowUploadSuffix;

    @UserLoginToken
    @RequestMapping(value = "/uploadImageString", method = RequestMethod.POST)
    public ResultVO uploadImageString(@RequestBody List<FileBase64UploadReq> files) {
        ResultVO resultVO = new ResultVO();

        List<FileManagerDTO> fileDtos = null;
        try {
            fileDtos = new ArrayList<FileManagerDTO>();
            for (FileBase64UploadReq req : files) {
                //校验附件后缀
                String suffix = StringUtils.getFilenameExtension(req.getFileName());

                //如果不在允许范围内的附件后缀直接抛出错误
                if (StringUtils.isEmpty(suffix) || !allowUploadSuffix.contains(suffix.toLowerCase())) {
                    log.error("FileManagerController.allowUploadSuffix error-->allowUploadSuffix={},current suffix={}"
                            ,allowUploadSuffix,suffix);
                    return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
                }

                FileManagerDTO dto = new FileManagerDTO();
                dto.setFileName(req.getFileName());
                InputStream is = new ByteArrayInputStream(Base64.decodeBase64(req.getImage()));
                dto.setImage(is);
                dto.setFileSize(req.getFileSize());
                fileDtos.add(dto);
            }
            CommonResultResp resp = fileManagerService.uploadImage(fileDtos);
            ResponseComUtil.RespToResultVO(resp, resultVO);
        } catch (Exception e) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
            log.error("图片上传失败",e);
        } finally {
            if (fileDtos != null) {
                for (FileManagerDTO dto : fileDtos) {
                    IOUtils.closeQuietly(dto.getImage());
                }
            }
        }
        return resultVO;
    }

    @UserLoginToken
    @RequestMapping(value = "/uploadImageByFile",headers = "content-type=multipart/form-data" ,method = RequestMethod.POST)
    public ResultVO uploadImageByFile(@RequestParam("file") MultipartFile file) {

        String suffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
        //如果不在允许范围内的附件后缀直接抛出错误
        if (StringUtils.isEmpty(suffix) || !allowUploadSuffix.contains(suffix.toLowerCase())) {
            log.error("FileManagerController.allowUploadSuffix error-->allowUploadSuffix={},current suffix={}"
                    ,allowUploadSuffix,suffix);
            return ResultVO.errorEnum(ResultCodeEnum.FORBID_UPLOAD_ERROR);
        }


        ResultVO resultVO = new ResultVO();
        InputStream is = null;
        try {
            is = file.getInputStream();
            FileManagerDTO fileManagerDTO = new FileManagerDTO();
            fileManagerDTO.setImage(is);
            fileManagerDTO.setFileSize(file.getSize());
            fileManagerDTO.setFileName(file.getOriginalFilename());

            CommonResultResp resp = fileManagerService.uploadImage(fileManagerDTO);
            ResponseComUtil.RespToResultVO(resp, resultVO);
        } catch (Exception e) {
            resultVO.setResultCode(OmsCommonConsts.RESULE_CODE_FAIL);
            resultVO.setResultMsg(e.getMessage());
            log.error("图片流上传失败",e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return resultVO;
    }

    @RequestMapping(value = "/deleteImg", method = RequestMethod.POST)
    public ResultVO deleteImg(@RequestBody FileManagerDTO fileManagerDTO) {
        ResultVO resultVO = new ResultVO();
        CommonResultResp resp = fileManagerService.deleteImg(fileManagerDTO);
        ResponseComUtil.RespToResultVO(resp, resultVO);
        return resultVO;
    }
}
