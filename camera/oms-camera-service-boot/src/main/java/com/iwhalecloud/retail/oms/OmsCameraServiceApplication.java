package com.iwhalecloud.retail.oms;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication

@ImportResource({"classpath:dubbo-provider.xml"})
@Import(FdfsClientConfig.class)
public class OmsCameraServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmsCameraServiceApplication.class, args);
	}
}
