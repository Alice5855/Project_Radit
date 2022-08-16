package org.zerock.domain;

import lombok.Data;

@Data
public class AttachFileDTO {
	// Page 516 첨부 file 정보를 저장하는 DataTransferObject
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean image;
	// isImage() boolean method 생성됨
}
