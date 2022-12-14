package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class PageDTO {
	// 화면에 paging을 하기 위한 정보를 다루는 class
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
		this.startPage = this.endPage - 9;
		
		int realEnd = (int) (Math.ceil((total*1.0)/cri.getAmount()));
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		// Check Math_ceil_Page304.java out
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
		// prev, next의 boolean값에 따라 pagination을 만들 예정
	}
	
}
