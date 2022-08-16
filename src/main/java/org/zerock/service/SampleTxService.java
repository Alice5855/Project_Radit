package org.zerock.service;

public interface SampleTxService {
	/*
	 * Transaction은 Business Layer에서 이루어 지기 때문에 service layer에서
	 * SampleOneMapper, SampleTwoMapper를 사용하는 Interface를 설계
	 */
	// Page 427 : Transaction이 설정되어있지 않은 상태에서의 test
	public void addData(String value);
}
