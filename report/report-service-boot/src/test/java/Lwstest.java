import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iwhalecloud.retail.dto.ResultVO;
import com.iwhalecloud.retail.report.ReportServiceApplication;
import com.iwhalecloud.retail.report.dto.request.ReportOrderDaoReq;
import com.iwhalecloud.retail.report.dto.response.ReportOrderResp;
import com.iwhalecloud.retail.report.service.ReportOrderService;
import com.iwhalecloud.retail.system.service.CommonOrgService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReportServiceApplication.class)
public class Lwstest {
	
	@Autowired
    private ReportOrderService reportOrderService;
	
	@Test
	public void test() {
		ReportOrderDaoReq req = new ReportOrderDaoReq();
		List<String> orgName = new ArrayList<String>(); 
		orgName.add("1000000020.843000000000000.843073100000000.843073105030000");
		orgName.add("1000000020.843000000000000.843073400000000.843073405020000");
		req.setOrgName(orgName);
		ResultVO<Page<ReportOrderResp>>  list = reportOrderService.getReportOrderList1(req);
		System.out.println("11111111111");
	}

}
