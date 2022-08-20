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
	private String b_video;
	
	private List<BoardAttachVO> attachList;
	// data를 한번에 처리하기 위한 List 객체
}
