package com.kh.spring.demo.model.dao;

import java.util.List;

import com.kh.spring.demo.model.dto.Dev;

public interface DemoDao {

	int insertDev(Dev dev);

	List<Dev> selectDevList();

	Dev selectDevByNo(int no);

	int updateDev(Dev dev);

	int deleteDev(int no);

	int updatePartialDev(Dev dev);
	
}
