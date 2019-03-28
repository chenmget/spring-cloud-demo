package com.iwhalecloud.retail.web.controller.system;

import com.alibaba.dubbo.config.annotation.Reference;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.UserCollectionDTO;
import com.iwhalecloud.retail.system.dto.request.UserCollectionCancelReq;
import com.iwhalecloud.retail.web.annotation.UserLoginToken;
import com.iwhalecloud.retail.web.consts.WebConst;
import com.iwhalecloud.retail.web.controller.system.request.UserCollectionAddReq;
import com.iwhalecloud.retail.web.interceptor.UserContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import com.iwhalecloud.retail.system.service.UserCollectionService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/userCollection")
public class UserCollectionController {
	
    @Reference
    private UserCollectionService userCollectionService;


    /**
     * 添加收藏
     * @return
     */
    @ApiOperation(value = "添加收藏", notes = "")
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @UserLoginToken
    @RequestMapping(value = "/addUserCollection", method = RequestMethod.POST)
    public ResultVO<String> addUserCollection(@RequestBody @ApiParam(value = "添加收藏", required = true) UserCollectionAddReq userCollectionAddReq) {

        UserCollectionDTO userCollectionDTO = new UserCollectionDTO();
        userCollectionDTO.setUserId(UserContext.getUserId());
        userCollectionDTO.setObjId(userCollectionAddReq.getObjId());
        userCollectionDTO.setObjType(userCollectionAddReq.getObjType());
        return userCollectionService.addUserCollection(userCollectionDTO);
    }

    @ApiOperation(value = "根据收藏ID取消收藏")
    @UserLoginToken
    @RequestMapping(value = "/deleteUserCollection/{id}",method = RequestMethod.DELETE)
    public ResultVO<Boolean> deleteUserCollection(@PathVariable("id") String id){

        return userCollectionService.deleteUserCollection(id);
    }

    @ApiOperation(value = "根据收藏对象和收藏ID取消收藏")
    @UserLoginToken
    @RequestMapping(value = "/deleteUserCollection",method = RequestMethod.DELETE)
    public ResultVO<Boolean> deleteUserCollection(@RequestBody @ApiParam("userCollectionCancelReq") UserCollectionCancelReq userCollectionCancelReq) {

        userCollectionCancelReq.setUserId(UserContext.getUserId());
        return userCollectionService.deleteUserCollection(userCollectionCancelReq);
    }
    
    
}