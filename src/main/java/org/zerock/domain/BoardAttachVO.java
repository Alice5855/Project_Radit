package org.zerock.domain;

import lombok.Data;

@Data
public class BoardAttachVO {
	private String b_uuid;
	private String b_uploadPath;
	private String b_fileName;
	
	private Long b_number; // FK from tbl_board
}
