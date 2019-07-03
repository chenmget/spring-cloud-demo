package com.iwhalecloud.retail.warehouse.errorcode.manager;

import com.google.common.collect.Lists;
import com.iwhalecloud.retail.dto.ErrorCodeDTO;
import com.iwhalecloud.retail.dto.ResultCodeEnum;
import com.iwhalecloud.retail.exception.RetailTipException;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Description: ErrorCodeManager
 * @author: Z
 * @date: 2019/7/2 20:16
 */
@Service
@Slf4j
public class ErrorCodeManager {

    public static final String ERROR_CODE_QUERY = "select error_code,error_msg,system_code,error_desc,create_time,region_language,error_type from sys_error_code where system_code = ? ";

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 根据系统编码查询异常编码信息
     * @param systemCode
     * @return
     */
    public List<ErrorCodeDTO> queryErrorCodes(String systemCode) {
        Connection connection = sqlSessionTemplate.getSqlSessionFactory().openSession().getConnection();
        ResultSet rs = null;
        List<ErrorCodeDTO> errorCodes = Lists.newArrayList();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ERROR_CODE_QUERY);
            preparedStatement.setString(1, systemCode);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ErrorCodeDTO errorCodeDTO = new ErrorCodeDTO();
                errorCodeDTO.setErrorCode(rs.getString("error_code"));
                errorCodeDTO.setErrorMsg(rs.getString("error_msg"));
                errorCodeDTO.setSystemCode(rs.getString("system_code"));
                errorCodeDTO.setErrorDesc(rs.getString("error_desc"));
                errorCodeDTO.setCreateTime(rs.getDate("create_time"));
                errorCodeDTO.setRegionLanguage(rs.getString("region_language"));
                errorCodeDTO.setErrorType(rs.getString("error_type"));

                errorCodes.add(errorCodeDTO);
            }
        } catch (SQLException e) {
            log.error("ErrorCodeManager.queryErrorCodes", e);
            throw new RetailTipException(ResultCodeEnum.QUERY_DB_EXCEPTION, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("ErrorCodeManager.queryErrorCodes", e);
                }
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error("ErrorCodeManager.queryErrorCodes", e);
            }
        }

        return errorCodes;
    }

}
