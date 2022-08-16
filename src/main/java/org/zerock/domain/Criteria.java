package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1, 10);
		// default page 1, 10 entries
	}

	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
		// get page number, entry amount via parameter of constructor
	}
	
	public String[] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
		// type이 null일 경우 배열 String[] 객체를 생성하고 null이 아닌경우
		// 정규식 ""를 기준으로 배열 String[]을 나눔
	}
	// T(itle), C(ontent), W(riter)로 구성된 검색 조건을 배열로 처리하기 위함
	// (MyBatis 동적 tag 활용, BoardMapper.xml 참고)
	
	// Page580 게시글 삭제 후 pageNum, 검색 조건 등을 유지하면서 이동하기 위해
	// parameter들을 UriComponentsBuilder를 활용하여 query string을 쉽게 처리
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.getPageNum())
				.queryParam("pageNum", this.getAmount())
				.queryParam("pageNum", this.getType())
				.queryParam("pageNum", this.getKeyword());
		return builder.toUriString();
				
	}
}
