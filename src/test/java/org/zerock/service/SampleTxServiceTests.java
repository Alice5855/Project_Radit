package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SampleTxServiceTests {
	
	@Setter(onMethod_ = @Autowired)
	private SampleTxService service;

	@Test
	public void testLong() {
		String str = "Starry\r\n" + "Starry night\r\n" + "Paint your palette bright blue and dull grey\r\n" + "Look out on a summer day the sun might burn you";
		
		log.info(str.getBytes().length);
		
		service.addData(str);
	}
	/* 
	 * it is not transaction *YET* so if value that bigger than 50bytes
	 * is inserted col2(tbl_sample2 table) would have problem
	 * 
	 * if it is transaction, in the case that one of process got problem
	 * then all transaction would stop and *CANCEL ALL PROCESS*
	 * 
	 * ORA-12899: "BOOK_EX"."TBL_SAMPLE2"."COL2" 열에 대한 값이 너무 큼
	 * (실제: 115, 최대값: 50)
	 * 
	 * INFO : jdbc.audit - 1. Connection.rollback() returned
	 * transaction이 close되면서 rollback을 자동으로 수행해줌
	 */

}
