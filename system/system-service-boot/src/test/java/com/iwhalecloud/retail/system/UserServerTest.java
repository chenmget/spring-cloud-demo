package com.iwhalecloud.retail.system;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.system.dto.MenuDTO;
import com.iwhalecloud.retail.system.dto.RoleMenuDTO;
import com.iwhalecloud.retail.system.dto.UserDTO;
import com.iwhalecloud.retail.system.dto.UserRoleDTO;
import com.iwhalecloud.retail.system.dto.request.*;
import com.iwhalecloud.retail.system.dto.response.UserLoginResp;
import com.iwhalecloud.retail.system.service.*;
import com.iwhalecloud.retail.workflow.dto.req.WorkTaskAddReq;
import com.twmacinta.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemServiceApplication.class)
public class UserServerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private BssInfoSyncService bssInfoSyncService;

    @Autowired
    private SysUserMessageService sysUserMessageService;


    @org.junit.Test
    public void login(){
        MD5 md5 = new MD5();
        md5.Update("123");
//        log.info("new: {}", md5.asHex());
//        log.info("old: {}", md51("123"));

        UserLoginReq req = new UserLoginReq();
        req.setLoginName("test");
        UserLoginResp result = userService.login(req);
        System.out.print("结果：" + result.toString());
    }

    /**
     * MD5加密方法
     * @param str
     * @return String
     */
    public  String md51(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
        byte[] resultByte = messageDigest.digest(str.getBytes());

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < resultByte.length; ++i) {
            result.append(Integer.toHexString(0xFF & resultByte[i]));
            log.info("[{}]: {}", i, Integer.toHexString(0xFF & resultByte[i]));

        }
        return result.toString();
    }

    @org.junit.Test
    public  void getUserMenu(){
        List<MenuDTO> menuList = new ArrayList();
        //1、先找  用户-角色 关联
        List<UserRoleDTO> userRoleDTOList = userRoleService.listUserRoleByUserId("1").getResultData();
        List<String> menuIdList = new ArrayList<>();

        for(UserRoleDTO userRoleDTO : userRoleDTOList){
            //2、找 角色-菜单 关联
            String roleId = userRoleDTO.getRoleId();
            List<RoleMenuDTO> roleMenuDTOList = roleMenuService.listRoleMenuByRoleId(roleId).getResultData();
            // 存menuID
            for (RoleMenuDTO roleMenuDTO : roleMenuDTOList){
                menuIdList.add(roleMenuDTO.getMenuId());
            }
        }
        // menuID 去重
        HashSet h = new HashSet(menuIdList);
        menuIdList.clear();
        menuIdList.addAll(h);
        for (String menuId : menuIdList){
            MenuDTO menuDTO = menuService.getMenuByMenuId(menuId).getResultData();
            if(menuDTO != null){
                menuList.add(menuDTO);
            }
        }
        System.out.print("结果：" + menuList.toString());
    }

    @org.junit.Test
    public void pageUser(){
        UserPageReq req = new UserPageReq();
//        req.setLoginName("");
//        req.setStatus("1");
//        req.setUserFounder(null);
//        req.setIsRelCodeNull(false);
        req.setStatusList(Lists.newArrayList(1));
//        req.setNotInUserIdList(Lists.newArrayList("1"));
        Page<UserDTO> result = userService.pageUser(req);
        System.out.print("结果：" + JSON.toJSONString(result));
    }

    @org.junit.Test
    public void getEnableUserWithPartnerIds(){
        List list = new ArrayList();
        list.add("101");
        list.add("102");
        list.add("4301811025392");
        List result = userService.getEnableUserWithPartnerIds(list);
        System.out.print("结果：" + result.toString());
    }

    @org.junit.Test
    public void updateUserWithShopStaff(){
        UserUpdateWithShopStaffReq req = new UserUpdateWithShopStaffReq();
        req.setUserId("201502044037001020");
        req.setStaffId("4311031055094");
        int result = userService.updateUserWithShopStaff(req);
        System.out.print("结果：" + result);
    }


    @org.junit.Test
    public void addAdminUser(){
        UserAddReq req = new UserAddReq();
        req.setLoginName("merchant2"); // 账号
        req.setLoginPwd("123");
        req.setUserName("厂商2");
        req.setPhoneNo("159");
        req.setCreateStaff("1");
        req.setRelType("");
        req.setRemark("厂商1");
        req.setStatusCd(1);
        req.setUserFounder(9); // 3分销商  6 员工
        req.setRelCode(""); // partnerId
        req.setRelNo(""); // staffId

        ResultVO result = userService.addUser(req);
        System.out.print("结果：" + result.toString());
    }

    @org.junit.Test
    public void getAdminUser(){
        UserGetReq req = new UserGetReq();
        req.setLoginName("xiaoming"); // 账号
        req.setUserId("1"); //
        req.setRelNo("4311031055094"); // staffId

        UserDTO userDTO = userService.getUser(req);
        System.out.print("结果：" + userDTO);
    }

    @org.junit.Test
    public void updateUserPassword(){
        UserUpdatePasswordReq req = new UserUpdatePasswordReq();
        req.setNewPassword("123"); // 账号
        req.setOldPassword("1234"); //
        req.setUserId("201406101594000783"); // staffId

        ResultVO result = userService.updatePassword(req);
        System.out.print("结果：" + result);
    }

    @org.junit.Test
    public void update(){
        UserEditReq req = new UserEditReq();
        req.setUserName("user1"); // 账号
        req.setUserId("201406101594000783"); // staffId

        ResultVO result = userService.editUser(req);
        System.out.print("结果：" + result);
    }

    @org.junit.Test
    public void listUserByCondition(){
        ResultVO result = userService.listUserByCondition("1068415967000010753", "");
        System.out.print("结果：" + result);
    }

    @org.junit.Test
    public void getUserList(){
        UserListReq userListReq = new UserListReq();
//        userListReq.setRelCode("4301211021582");
//        userListReq.setRoleId("1068415967000010753");
//        userListReq.setLoginName("a");
//        userListReq.setUserName("a");
//        userListReq.setOrgName("a");
        userListReq.setUserFounderList(Lists.newArrayList(1, 2));
        List<UserDTO> userDTOList = userService.getUserList(userListReq);

        System.out.print("结果：" + userDTOList.toString());
    }

    @org.junit.Test
    public void getUserDetail(){
        ResultVO resultVO = userService.getUserDetailByUserId("1");
        System.out.print("结果：" + resultVO);
    }


    public static void main(String[] args) {
        //[1550525]【安全漏洞修复】明文密码、日志记录、硬编码、密钥明文存储、敏感信息泄露等
//        String reg = "^([A-Z]|[a-z]|[0-9]|[`\\-\\=\\[\\];,./~!@#$%^*()_+}{:?]){8,20}$";
//        String password = "ee4ddw>sW";
//        // 密码匹配  包含 大小写  数字  特殊字符  8到20位
//        boolean result = checkPassword(password);
//        System.out.print(result);
    }

   private static boolean checkPassword(String password) {
        // 方法1
       // 密码匹配  包含 大小写  数字  特殊字符  8到20位
//       String pw_test = "^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,20}$";
//       return password.matches(pw_test);

       //方法2
       // Z = 大写字母  X 小写字母  S = 数字 T = 特殊字符
       String regexZ = ".*[A-Z]+.*";
       String regexX = ".*[a-z]+.*";
       String regexS = ".*\\d+.*";
//       String regexT = ".*[~!@#$%^&*.]+.*";
       String regexT = ".*\\W+.*";

       if (password.length() < 8 || password.length() >20) {
           System.out.print("长度不对");
           return false;
       }
       if (!password.matches(regexZ)) {
           System.out.print("大写");
           return false;
       }
       if (!password.matches(regexX)) {
           System.out.print("小写");
           return false;
       }
       if (!password.matches(regexS)) {
           System.out.print("数字");
           return false;
       }
       if (!password.matches(regexT)) {
           System.out.print("特殊");
           return false;
       }
        return true;

   }

    @org.junit.Test
    public void userInfoSyncTest() {

        String stringJson = "{" +
                "\"systemUser\": {" +
                "\"sysUserDesc\": \"BSS3.0长电工号清理;陈前\", \n" +
                "       \"systemInfoId\": \"727001\", \n" +
                "       \"effDate\": \"2017-11-14 00:00:00\", \n" +
                "       \"expDate\": \"2040-12-31 00:00:00\", \n" +
                "       \"sysUserCode\": \"731CHNZQ01122\", \n" +
                "        \"statusDate\": \"2018-04-12 14:38:44\", \n" +
                "        \"password\": \"yohiegpSGKM4ikAarvCABA==\", \n" +
                "       \"pwdStatus\": \"1100\", \n" +
                "       \"userName\": \"陈前\", \n" +
                "       \"createDate\": \"2017-11-14 08:39:55\", \n" +
                "       \"regionId\": \"73101\", \n" +
                "       \"limitCount\": \"3\", \n" +
                "     \"updateStaff\": \"103907741\", \n" +
                "       \"staffId\": \"11075\", \n" +
                "     \"salesstaffCode\": \"Y43010137544\", \n" +
                "        \"pwdErrCnt\": \"0\", \n" +
                "       \"loginedNum\": \"287\", \n" +
                "        \"pwdNewtime\": \"2018-01-17 12:52:29\", \n" +
                "       \"updateDate\": \"2018-04-12 14:38:55\", \n" +
                "      \"sysUserId\": \"103907741\", \n" +
                "        \"userOrgId\": \"110002247702\", \n" +
                "        \"statusCd\": \"1000\", \n" +
                "        \"pwdSmsTel\": \"18975169056\", \n" +
                "       \"pwdEffectDays\": \"90\"\n" +
                "    }, \n" +
                "    \"actType\": \"A\"\n" +
                "}\n";

        String result = bssInfoSyncService.userInfoSync(stringJson);
        log.info("==================" + result + "==================");
    }

    @org.junit.Test
    public void insertByTaskWorkTaskTest() {
        WorkTaskAddReq taskAddReq = new WorkTaskAddReq();
        taskAddReq.setFormId("00101");
        List<WorkTaskAddReq.UserInfo> handlerUser = new ArrayList<>();
        WorkTaskAddReq.UserInfo userInfo1 = new WorkTaskAddReq.UserInfo();
        WorkTaskAddReq.UserInfo userInfo2 = new WorkTaskAddReq.UserInfo();
        userInfo1.setUserId("1");
        userInfo2.setUserId("1077839559879852033");
        handlerUser.add(userInfo1);
        handlerUser.add(userInfo2);
        taskAddReq.setHandlerUsers(handlerUser);
        taskAddReq.setTaskTitle("待发货");
        String taskId = "110";
        sysUserMessageService.insertByTaskWorkTask(taskAddReq, taskId);

    }


}
