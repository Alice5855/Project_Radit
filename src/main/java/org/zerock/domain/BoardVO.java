package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private long b_number;
	private String b_title;
	private String b_text;
	private String u_email;
	private Date b_regDate;
	private Date b_updateDate;
	private String b_img;
	
	// Page 481. 게시물이 가진 댓글의 숫자를 count하는 replyCnt column을 DB에 추가함
	private int replyCnt;
	
	private List<BoardAttachVO> attachList;
	// data를 한번에 처리하기 위한 List 객체
}
