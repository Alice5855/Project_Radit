package org.zerock.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // Dependency Injection
public class ReplyPageDTO {
	private int replyCnt;
	private List<ReplyVO> list;
	// ReplyService, ServiceImpl에 ReplyPageDTO를 반환하는 method 추가 요
}
