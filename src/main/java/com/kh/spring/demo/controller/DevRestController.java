package com.kh.spring.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.demo.model.dto.Dev;
import com.kh.spring.demo.model.service.DemoService;

import lombok.extern.slf4j.Slf4j;

/**
 * 요청url예시(전송방식이 다르므로 url을 절약할 수 있다)
 * 
 * GET /dev
 * GET /dev/1
 * GET /dev/email/honggd@naver.com
 * GET /dev/lang/C
 * GET /dev/lang/Java
 * 
 * POST /dev
 * 
 * PUT /dev
 * 
 * DELETE /dev/1
 *
 * @RestController: @Controller + 모든 메소드에 @ResponseBody처리가능
 */
//@Controller
@RestController
@Slf4j
@RequestMapping("/dev")
public class DevRestController {
	
	@Autowired
	DemoService demoService;
	
	@GetMapping
	//@ResponseBody
	public List<Dev> dev(){
		return demoService.selectDevList();
	}
	
	@GetMapping("/{no}")
	public ResponseEntity<?> dev(@PathVariable int no) {
		log.debug("no = {}", no);
		Dev dev = demoService.selectDevByNo(no);
		if(dev != null) {
			return ResponseEntity.ok(dev);
		}
		else {
			return ResponseEntity.notFound().build();//builder패턴
		}
	}
	
	/**
	 * 전체 조회 후 핸들러에서 필터링 처리할 것.
	 * - 해당 언어에 해당하는 결과가 없는 경우 404처리
	 */
	@GetMapping("/lang/{lang}")
	public ResponseEntity<?> dev(@PathVariable String lang){
		log.debug("lang = {}", lang);
		
		List<Dev> devList = demoService.selectDevList();
		List<Dev> resultList = new ArrayList<>();
		for(Dev dev : devList) {
			List<String> langList = Arrays.asList(dev.getLang());
			if(containsIgnoreCase(langList, lang)) {
				resultList.add(dev);
			}
		}
		if(resultList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(resultList);
	}

	private boolean containsIgnoreCase(List<String> strList, String str) {
		for(String s : strList) {
			if(s != null && s.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}
}
