package org.zerock.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
	/* Ajax 댓글 처리 기능 구현
	 * REST API를 가장 많이 사용하는 형태는 web browser나 모바일 app 등에서 data를 
	 * ajax로 호출 하는 것. Ajax의 호출을 가정하고 댓글 기능을 구현. DB상에서 댓글은 전형적인
	 * 1:n 관계로 구성되기 때문에 하나의 게시물에 여러개의 댓글을 추가하는 형태로 구성하고
	 * redirect 없이 글 조회 화면에서 처리하기 때문에 Ajax를 통한 호출을 수행
	 */
	private Long rno;
	private Long bno;
	
	private String reply;
	private String replyer; // reply author
	private Date replyDate;
	private Date updateDate;
}
