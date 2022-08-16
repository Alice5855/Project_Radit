package org.zerock.mapper;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TimeMapperTest {
	
	@Setter(onMethod_ = @Autowired)
	private TimeMapper timeMapper;
	
	@Test
	public void testGetTime() {
		try {
			log.info(timeMapper.getClass().getName());
			log.info(timeMapper.getTime());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testGetTime2() {
		try {
			log.info("getTime2() method called");
			log.info(timeMapper.getTime2());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
