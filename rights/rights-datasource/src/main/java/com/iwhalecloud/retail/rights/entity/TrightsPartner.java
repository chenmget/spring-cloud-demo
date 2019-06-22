package com.iwhalecloud.retail.rights.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * TrightsPartner
 * @author generator
 * @version 1.0
 * @since 1.0
 */
@Data
@TableName("T_RIGHTS_PARTNER")
@KeySequence(value="seq_t_rights_partner_partner_id",clazz = String.class)
@ApiModel(value = "对应模型T_RIGHTS_PARTNER, 对应实体TrightsPartner类")
public class TrightsPartner implements Serializable {
    /**表名常量*/
    public static final String TNAME = "T_RIGHTS_PARTNER";
  	private static final long serialVersionUID = 1L;
  
  	
  	//属性 begin
  	/**
  	 * partnerId
  	 */
	@TableId
	@ApiModelProperty(value = "partnerId")
  	private java.lang.Long partnerId;
  	
  	/**
  	 * partnerName
  	 */
	@ApiModelProperty(value = "partnerName")
  	private java.lang.String partnerName;
  	
  	/**
  	 * partnerCode
  	 */
	@ApiModelProperty(value = "partnerCode")
  	private java.lang.String partnerCode;
  	
  	/**
  	 * partnerType
  	 */
	@ApiModelProperty(value = "partnerType")
  	private java.lang.String partnerType;
  	
  	/**
  	 * partnerSale
  	 */
	@ApiModelProperty(value = "partnerSale")
  	private java.lang.String partnerSale;
  	
  	/**
  	 * partnerCompany
  	 */
	@ApiModelProperty(value = "partnerCompany")
  	private java.lang.String partnerCompany;
  	
  	/**
  	 * partnerCompanyType
  	 */
	@ApiModelProperty(value = "partnerCompanyType")
  	private java.lang.String partnerCompanyType;
  	
  	/**
  	 * businessLicense
  	 */
	@ApiModelProperty(value = "businessLicense")
  	private java.lang.String businessLicense;
  	
  	/**
  	 * taxNo
  	 */
	@ApiModelProperty(value = "taxNo")
  	private java.lang.String taxNo;
  	
  	/**
  	 * registeredFunds
  	 */
	@ApiModelProperty(value = "registeredFunds")
  	private java.lang.Long registeredFunds;
  	
  	/**
  	 * registeredDate
  	 */
	@ApiModelProperty(value = "registeredDate")
  	private java.util.Date registeredDate;
  	
  	/**
  	 * registeredAddress
  	 */
	@ApiModelProperty(value = "registeredAddress")
  	private java.lang.String registeredAddress;
  	
  	/**
  	 * businessNo
  	 */
	@ApiModelProperty(value = "businessNo")
  	private java.lang.String businessNo;
  	
  	/**
  	 * contractNo
  	 */
	@ApiModelProperty(value = "contractNo")
  	private java.lang.String contractNo;
  	
  	/**
  	 * juridicalPerson
  	 */
	@ApiModelProperty(value = "juridicalPerson")
  	private java.lang.String juridicalPerson;
  	
  	/**
  	 * certificateType
  	 */
	@ApiModelProperty(value = "certificateType")
  	private java.lang.String certificateType;
  	
  	/**
  	 * certificateNo
  	 */
	@ApiModelProperty(value = "certificateNo")
  	private java.lang.String certificateNo;
  	
  	/**
  	 * bankNo
  	 */
	@ApiModelProperty(value = "bankNo")
  	private java.lang.String bankNo;
  	
  	/**
  	 * bankName
  	 */
	@ApiModelProperty(value = "bankName")
  	private java.lang.String bankName;
  	
  	/**
  	 * accountNo
  	 */
	@ApiModelProperty(value = "accountNo")
  	private java.lang.String accountNo;
  	
  	/**
  	 * contactName
  	 */
	@ApiModelProperty(value = "contactName")
  	private java.lang.String contactName;
  	
  	/**
  	 * contactDuty
  	 */
	@ApiModelProperty(value = "contactDuty")
  	private java.lang.String contactDuty;
  	
  	/**
  	 * telephoneNo
  	 */
	@ApiModelProperty(value = "telephoneNo")
  	private java.lang.String telephoneNo;
  	
  	/**
  	 * mobileNo
  	 */
	@ApiModelProperty(value = "mobileNo")
  	private java.lang.String mobileNo;
  	
  	/**
  	 * email
  	 */
	@ApiModelProperty(value = "email")
  	private java.lang.String email;
  	
  	/**
  	 * contactAddress
  	 */
	@ApiModelProperty(value = "contactAddress")
  	private java.lang.String contactAddress;
  	
  	/**
  	 * postCode
  	 */
	@ApiModelProperty(value = "postCode")
  	private java.lang.String postCode;
  	
  	/**
  	 * emergencyContact
  	 */
	@ApiModelProperty(value = "emergencyContact")
  	private java.lang.String emergencyContact;
  	
  	/**
  	 * emergencyContactTelephone
  	 */
	@ApiModelProperty(value = "emergencyContactTelephone")
  	private java.lang.String emergencyContactTelephone;
  	
  	/**
  	 * custService
  	 */
	@ApiModelProperty(value = "custService")
  	private java.lang.String custService;
  	
  	/**
  	 * custServiceTelephone
  	 */
	@ApiModelProperty(value = "custServiceTelephone")
  	private java.lang.String custServiceTelephone;
  	
  	/**
  	 * itContact
  	 */
	@ApiModelProperty(value = "itContact")
  	private java.lang.String itContact;
  	
  	/**
  	 * itContactTelephone
  	 */
	@ApiModelProperty(value = "itContactTelephone")
  	private java.lang.String itContactTelephone;
  	
  	/**
  	 * gmtCreate
  	 */
	@ApiModelProperty(value = "gmtCreate")
  	private java.util.Date gmtCreate;
  	
  	/**
  	 * createStaff
  	 */
	@ApiModelProperty(value = "createStaff")
  	private java.lang.String createStaff;
  	
  	/**
  	 * modifiedStaff
  	 */
	@ApiModelProperty(value = "modifiedStaff")
  	private java.util.Date modifiedStaff;
  	
  	/**
  	 * gmtModify
  	 */
	@ApiModelProperty(value = "gmtModify")
  	private java.lang.String gmtModify;
  	
  	/**
  	 * 00A生效，00X注销
  	 */
	@ApiModelProperty(value = "00A生效，00X注销")
  	private java.lang.String partnerState;
  	
  	
  	//属性 end
	
    /** 字段名称枚举. */
    public enum FieldNames {
		/** partnerId. */
		partnerId("partnerId","PARTNER_ID"),
		
		/** partnerName. */
		partnerName("partnerName","PARTNER_NAME"),
		
		/** partnerCode. */
		partnerCode("partnerCode","PARTNER_CODE"),
		
		/** partnerType. */
		partnerType("partnerType","PARTNER_TYPE"),
		
		/** partnerSale. */
		partnerSale("partnerSale","PARTNER_SALE"),
		
		/** partnerCompany. */
		partnerCompany("partnerCompany","PARTNER_COMPANY"),
		
		/** partnerCompanyType. */
		partnerCompanyType("partnerCompanyType","PARTNER_COMPANY_TYPE"),
		
		/** businessLicense. */
		businessLicense("businessLicense","BUSINESS_LICENSE"),
		
		/** taxNo. */
		taxNo("taxNo","TAX_NO"),
		
		/** registeredFunds. */
		registeredFunds("registeredFunds","REGISTERED_FUNDS"),
		
		/** registeredDate. */
		registeredDate("registeredDate","REGISTERED_DATE"),
		
		/** registeredAddress. */
		registeredAddress("registeredAddress","REGISTERED_ADDRESS"),
		
		/** businessNo. */
		businessNo("businessNo","BUSINESS_NO"),
		
		/** contractNo. */
		contractNo("contractNo","CONTRACT_NO"),
		
		/** juridicalPerson. */
		juridicalPerson("juridicalPerson","JURIDICAL_PERSON"),
		
		/** certificateType. */
		certificateType("certificateType","CERTIFICATE_TYPE"),
		
		/** certificateNo. */
		certificateNo("certificateNo","CERTIFICATE_NO"),
		
		/** bankNo. */
		bankNo("bankNo","BANK_NO"),
		
		/** bankName. */
		bankName("bankName","BANK_NAME"),
		
		/** accountNo. */
		accountNo("accountNo","ACCOUNT_NO"),
		
		/** contactName. */
		contactName("contactName","CONTACT_NAME"),
		
		/** contactDuty. */
		contactDuty("contactDuty","CONTACT_DUTY"),
		
		/** telephoneNo. */
		telephoneNo("telephoneNo","TELEPHONE_NO"),
		
		/** mobileNo. */
		mobileNo("mobileNo","MOBILE_NO"),
		
		/** email. */
		email("email","E_MAIL"),
		
		/** contactAddress. */
		contactAddress("contactAddress","CONTACT_ADDRESS"),
		
		/** postCode. */
		postCode("postCode","POST_CODE"),
		
		/** emergencyContact. */
		emergencyContact("emergencyContact","EMERGENCY_CONTACT"),
		
		/** emergencyContactTelephone. */
		emergencyContactTelephone("emergencyContactTelephone","EMERGENCY_CONTACT_TELEPHONE"),
		
		/** custService. */
		custService("custService","CUST_SERVICE"),
		
		/** custServiceTelephone. */
		custServiceTelephone("custServiceTelephone","CUST_SERVICE_TELEPHONE"),
		
		/** itContact. */
		itContact("itContact","IT_CONTACT"),
		
		/** itContactTelephone. */
		itContactTelephone("itContactTelephone","IT_CONTACT_TELEPHONE"),
		
		/** gmtCreate. */
		gmtCreate("gmtCreate","GMT_CREATE"),
		
		/** createStaff. */
		createStaff("createStaff","CREATE_STAFF"),
		
		/** modifiedStaff. */
		modifiedStaff("modifiedStaff","MODIFIED_STAFF"),
		
		/** gmtModify. */
		gmtModify("gmtModify","GMT_MODIFY"),
		
		/** 00A生效，00X注销. */
		partnerState("partnerState","PARTNER_STATE");

		private String fieldName;
		private String tableFieldName;
		FieldNames(String fieldName, String tableFieldName){
			this.fieldName = fieldName;
			this.tableFieldName = tableFieldName;
		}

		public String getFieldName() {
			return fieldName;
		}

		public String getTableFieldName() {
			return tableFieldName;
		}
	}

}
