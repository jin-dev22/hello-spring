package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class MemberSecurityController {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//@RequestMapping(path = "/memberEnroll.do", method = RequestMethod.GET)
	@GetMapping("/memberEnroll.do")
	public String memberEnroll() {
		return "member/memberEnroll";
	}
	
	/**
	 * $2a$10$Vo40.zwppnqreJe0nny7quMHyB7gmd8ETaZ0KKf3w8RTSdIvJhbTm
	 * - $2a$ 알고리즘 타입
	 * - 10$ 옵션: 비용이 높을수록 속도가 느리고 메모리 사용량이 많음
	 * - Vo40.zwppnqreJe0nny7qu 22자리: 랜덤한 salt값
	 * - MHyB7gmd8ETaZ0KKf3w8RTSdIvJhbTm 31자리: hashing + encoding처리된 값
	 */
	@PostMapping("/memberEnroll.do")
	public String memberEnroll(Member member, RedirectAttributes redirectAttr) {
		try {
			log.debug("member = {}", member);
			//비밀번호 암호화 
			String rawPassword = member.getPassword();
			String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
			member.setPassword(encodedPassword);
			log.debug("encodedPw = {}",encodedPassword);
			int result = memberService.insertMember(member);
			redirectAttr.addFlashAttribute("msg", "회원가입 정상처리 완료");
			return "redirect:/";
		} catch (Exception e) {
			log.error("회원가입 오류 : "+e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * viewName이 null인 경우 요청 url을 기준으로 jsp위치를 자동으로 추론해줌.
	 * 		요청url: /member/memberLogin.do
	 * 		추론: member/memberLogin
	 */
	@GetMapping("/memberLogin.do")
	public void memberLogin() {
		
	}
	
	@PostMapping("/memberLoginSuccess.do")
	public String memberLoginSuccess(HttpSession session) {
		log.debug("memberLoginSuccess.do 호출");
		//로그인 후처리
		String location = "/";

		// security가 관리하는 다음 리다이렉트 url
		SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		if(savedRequest != null) {
			location = savedRequest.getRedirectUrl();
		}
		log.debug("location = {}",location);
		return "redirect:" + location;
	}
}
