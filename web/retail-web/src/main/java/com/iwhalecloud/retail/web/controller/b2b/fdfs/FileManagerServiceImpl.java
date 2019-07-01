package com.iwhalecloud.retail.web.controller.b2b.fdfs;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.web.controller.b2b.fdfs.consts.FileSubfixConstants;
import com.iwhalecloud.retail.web.controller.b2b.fdfs.dto.FileManagerDTO;
import com.iwhalecloud.retail.web.controller.b2b.fdfs.dto.FileManagerRespDTO;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileManagerServiceImpl implements FileManagerService {

    @Value("${fdfs.showUrl}")
    private String dfsShowIp;

    @Autowired
    public FastFileStorageClient fastFileStorageClient;

    @Override
    public ResultVO uploadImage(FileManagerDTO file) {
        ResultVO resultVO = new ResultVO();

        try {
            InputStream is = file.getImage();
            String fileName = file.getFileName();
            String subfix = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!StringUtils.hasText(subfix)) {
                resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
                resultVO.setResultMsg("The picture format is incorrect");
                return resultVO;
            }
            List<FileManagerRespDTO> respList = new ArrayList<>();
            // 若为视频文件，抓取视频第一帧作为视频简介
            if (FileSubfixConstants.checkContainSubfix(subfix)){
                // 复制输入流
                ByteArrayOutputStream baos = cloneInputStream(is);
                InputStream rowStream = new ByteArrayInputStream(baos.toByteArray());
                InputStream genStream = new ByteArrayInputStream(baos.toByteArray());
                file.setImage(rowStream);
                FileManagerDTO thumbNailDto = fetchVedioFrame(genStream);
                String thumbNailFileName = thumbNailDto.getFileName();
                String thumbNailSubfix = thumbNailFileName.substring(thumbNailFileName.lastIndexOf(".") + 1);
                respList.add(uploadFile(thumbNailDto, thumbNailSubfix));
            }
            //保存上传文件，并加入返回列表
            respList.add(uploadFile(file,subfix));
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultData(respList);
            resultVO.setResultMsg("Upload success");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
        }

        return resultVO;
    }

    // 上传文件私有方法
    private FileManagerRespDTO uploadFile(FileManagerDTO file,String subfix){
        long c1 = System.currentTimeMillis();
        StorePath storePath = fastFileStorageClient.uploadFile(file.getImage(), file.getFileSize(), subfix, null);
        FileManagerRespDTO resp = new FileManagerRespDTO();
        BeanUtils.copyProperties(storePath, resp);
        resp.setFileUrl(dfsShowIp + storePath.getFullPath());
        return resp;
    }

    // 截取视频图片作为简图
    private FileManagerDTO fetchVedioFrame(InputStream fileIs) throws Exception{
        long start = System.currentTimeMillis();
        FFmpegFrameGrabber videoGrabber = new FFmpegFrameGrabber(fileIs);
        videoGrabber.start();
        // 视频总帧数
        int frameNumTotal = videoGrabber.getLengthInFrames();
        org.bytedeco.javacv.Frame frameOfImg = null;
        int frameCount = 0;
        while (frameCount < frameNumTotal){
            frameOfImg = videoGrabber.grabFrame();
            if (null != frameOfImg && null != frameOfImg.image){
                break;
            }
            frameCount++;
        }

        int owidth = frameOfImg.imageWidth;
        int oheight = frameOfImg.imageHeight;
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        Java2DFrameConverter converter =new Java2DFrameConverter();
        BufferedImage fecthedImage =converter.getBufferedImage(frameOfImg);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(fecthedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
        String fileName = "videoCover" + System.currentTimeMillis() + ".png";
        String tmpDir = System.getProperty("java.io.tmpdir");
        File imgFile = new File(tmpDir + File.separator + fileName);
//        File imgFile = new File(tmpDir + fileName);
        ImageIO.write(bi,"png", imgFile);
        videoGrabber.stop();
        // 装箱返回
        FileManagerDTO dto = new FileManagerDTO();
        dto.setImage(new FileInputStream(imgFile));
        dto.setFileSize(imgFile.length());
        dto.setFileName(fileName);
        long end = System.currentTimeMillis();
        return dto;
    }

    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ResultVO uploadImage(List<FileManagerDTO> files) {

        List<FileManagerRespDTO> resps = new ArrayList<FileManagerRespDTO>();
        for (FileManagerDTO dto : files) {
            String suffix = dto.getFileName().substring(dto.getFileName().lastIndexOf(".") + 1);
            FileManagerRespDTO uploadResp = this.uploadFile(dto,suffix);
            resps.add(uploadResp);
        }
        ResultVO resultVO = new ResultVO();
        resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        resultVO.setResultData(resps);
        return resultVO;
    }

    @Override
    public ResultVO deleteImg(FileManagerDTO fileName) {
        ResultVO resultVO = new ResultVO();
        if (fileName == null || StringUtils.isEmpty(fileName.getDeleteImgName())) {
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg("Picture name cannot be empty");
            return resultVO;
        }
        try {
            fastFileStorageClient.deleteFile(fileName.getDeleteImgName());
            resultVO.setResultCode(ResultCodeEnum.SUCCESS.getCode());
            resultVO.setResultMsg("Delete successful");
        } catch (Exception e) {
            e.printStackTrace();
            resultVO.setResultCode(ResultCodeEnum.ERROR.getCode());
            resultVO.setResultMsg(e.getMessage());
        }
        return resultVO;
    }
}
