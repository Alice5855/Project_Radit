package org.zerock.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	@Select("SELECT SYSDATE FROM DUAL")
	public String getTime();
	// mybatis의 annotation으로 SQL query문을 Java에서 실행할 수 있음
	
	public String getTime2();
	// src/main/resources/org.zerock.mapper/TimeMapper.xml 참고
}
