package com.iwhalecloud.retail.pay;

import com.iwhalecloud.retail.pay.entity.TradeCertificate;
import com.iwhalecloud.retail.pay.handler.BestpayHandler;
import com.iwhalecloud.retail.pay.util.CertificateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BestpayCahNotifyApplication {
	@Value("${spring.profiles.active}")
	private String activeProfile;

	public String getActiveProfile() {
		return activeProfile;
	}

	public void setActiveProfile(String activeProfile) {
		this.activeProfile = activeProfile;
	}
	@Bean
	public BestpayHandler bestpayHandler(){
		String certConfigPath="properties/"+activeProfile+"/cert.properties";
		TradeCertificate tradeCertificate= CertificateUtil.getTradeCertificate(certConfigPath, CertificateUtil.KEYSTORETYPE_JKS,true);
		return  new BestpayHandler(tradeCertificate);
	}

	public static void main(String[] args) {
		SpringApplication.run(BestpayCahNotifyApplication.class, args);
	}
}
