package org.zerock.domain;

import lombok.Data;

@Data
public class AttachFileDTO {
	// Page 516 첨부 file 정보를 저장하는 DataTransferObject
	private String b_fileName;
	private String b_uploadPath;
	private String b_uuid;
}
