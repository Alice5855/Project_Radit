package org.zerock.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean fileType;
	// Columns from spring_study(user book_ex) tbl_attach DB
	
	private Long bno; // FK from tbl_board
}
