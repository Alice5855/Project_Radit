package org.zerock.mapper;

import org.apache.ibatis.annotations.Insert;

public interface SampleTwoMapper {
	@Insert("insert into tbl_sample2 (col2) values(#{data})")
	public int insertCol2(String data);
	// col2 is varchar2 50bytes
}
