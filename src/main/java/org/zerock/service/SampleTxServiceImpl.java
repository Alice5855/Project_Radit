package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.SampleOneMapper;
import org.zerock.mapper.SampleTwoMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService {
	
	@Setter(onMethod_ = @Autowired)
	private SampleOneMapper mapper1;
	
	@Setter(onMethod_ = @Autowired)
	private SampleTwoMapper mapper2;

	/* Transaction : 쉽게 말해서 여러개의 프로세스를 묶어서 하나의 프로세스처럼 취급하여
	 * 여러개의 프로세스가 한꺼번에 작동하는 것 처럼 만드는 것. @Transactional 적용 후
	 * SampleTxServiceTest에서 test 실행 후 tbl_sample1은 유효성 검사에 문제가 없음
	 * 에도 불구하고 tbl_sample2에 insert가 실패하여 transaction이 실행되지 않아
	 * tbl_sample1에도 data가 insert 되지 않았음을 확인
	 */
	@Transactional
	@Override
	public void addData(String value) {
		log.info("Smpl 1 mapper processing");
		mapper1.insertCol1(value);
		log.info("Smpl 2 mapper processing");
		mapper2.insertCol2(value);
		
		log.info("Process done");
	}
	// col2 is smaller one
	/* 
	 * it is not transaction *YET* so if value that bigger than 50bytes
	 * is inserted col2(tbl_sample2 table) would have problem
	 * 
	 * if it is transaction, in the case that one of process got problem
	 * then all transaction would stop and *CANCEL ALL PROCESS*
	 */
	/* @Transactional annotation은 method, class, interface에 적용할 수 있는데,
	 * 적용 우선순위가 method, class, interface순으로 적용된다. (method가 최상위)
	 * 
	 */
}
