package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// AllArgs : 모든 field를 parameter로 받는 constructor
// NoArgs : default constructor
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleVO {
	// 전달된 data를 JSON, XML을 이용하여 객체로 생산하는 SampleVO class
	private Integer mno;
	private String firstName;
	private String lastName;
}
