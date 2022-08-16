package org.zerock.mapper;

import org.apache.ibatis.annotations.Insert;

public interface SampleOneMapper {
	@Insert("insert into tbl_sample1 (col1) values(#{data})")
	public int insertCol1(String data);
	// col1 is varchar2 500bytes
}
