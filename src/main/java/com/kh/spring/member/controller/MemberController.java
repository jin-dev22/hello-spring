package com.kh.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@SessionAttributes({"loginMember"})
@Slf4j
public class MemberController {
	
	//static final Logger log = LoggerFactory.getLogger(MemberController.class)//-->slf4j
	
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

	@PostMapping("/memberLogin.do")
	public String memberLogin(@RequestParam String memberId, @RequestParam String password,
								Model model,
								RedirectAttributes redirectAttr) {
		//1. memberId로 회원조회
		Member member = memberService.selectOneMember(memberId);
		log.debug("member = {}", member);
				
		String location = "/";
		//2. 조회된 member가 null이 아니면서 비번이 일치하면 로그인 성공
		if(member != null && bcryptPasswordEncoder.matches(password, member.getPassword())) {
			//model을 통해 session scope에 속성저장하기: 클래스레벨에 @SessionAttributes로 등록된 속성이름으로 저장하면 세션에 저장. 등록 안되어있으면 리퀘스트임
			model.addAttribute("loginMember", member);
		}
		//else 로그인실패
		else {
			redirectAttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
			location = "/member/memberLogin.do";
		}
		return "redirect:"+location;
	}
	
	/**
	 * @SessionAttributes로 세션관리를 한다면,
	 * SessionStatus#setComplete로 만료처리해야 함.
	 */
	@GetMapping("/memberLogout.do")
	public String memberLogout(SessionStatus sessionStatus) {
		
		if(sessionStatus.isComplete()) {
			sessionStatus.setComplete();//세션 아이디 그대로 살아있음(세션 재사용 가능)
		}
		return "redirect:/";
	}
	
	@GetMapping("/memberDetail.do")
	public ModelAndView memberDetail(ModelAndView mav, @SessionAttribute Member loginMember) {
		log.debug("loginMember = {}", loginMember);
		
		mav.setViewName("member/memberDetail");
		return mav;
	}
	
	@PostMapping("/memberUpdate.do")
	public String memberUpdate(Member member, RedirectAttributes redirectAttr, Model model) {
		try {
//			log.debug("udate = {}", member);
			int result = memberService.updateMember(member);
			model.addAttribute("loginMember", memberService.selectOneMember(member.getMemberId()));
			redirectAttr.addFlashAttribute("msg", "회원정보 변경 성공");
			return "redirect:/member/memberDetail.do";
		} catch (Exception e) {
			log.error("회원정보수정 오류 : "+e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * jsonView 빈을 통한 ajax응답하기
	 * - model에 담긴 속성을 json문자열로 변환해서 응답메세지 바디에 출력함
	 * - BeanNameViewResolver를 통해서 viewName에 해당하는 빈을 찾는다.
	 */
	//@GetMapping("/checkIdDuplicate.do")
	public String checkIdDuplicate1(@RequestParam String memberId, Model model) {
		Member member = memberService.selectOneMember(memberId);
		boolean available = member == null;
		
		//사용자에게 전달할 데이터 model에 속성으로 저장
		model.addAttribute("memberId",memberId);
		model.addAttribute("available",available);
		
		return "jsonView";
	}
	
	/**
	 * - MessageConverter에서 리턴객체를 json으로 변환 후
	 * @ReponseBody가 핸들러의 리턴객체를 응답메세지 body에 작성한다.
	 */
	//@GetMapping("/checkIdDuplicate.do")
	@ResponseBody 
	public Map<String, Object> checkIdDuplicate2(@RequestParam String memberId) {
		Member member = memberService.selectOneMember(memberId);
		boolean available = member == null;
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("available", available);
		
		return map;
	}
	
	/**
	 * ResponseEntity
	 * - @ResponseBody 기능
	 * - 핸들러에서 응답코드, 응답헤더, 메세지바디를 자유롭게 제어할 수 있도록 도와주는 객체
	 * - 메세지바디에 작성할 자바객체는 messageConvertoer빈에 의해 json으로 처리됨
	 */
	@GetMapping("/checkIdDuplicate.do")
//	public ResponseEntity<Map<String, Object>> checkIdDuplicate3(@RequestParam String memberId) {
	public ResponseEntity<?> checkIdDuplicate3(@RequestParam String memberId) {
		Member member = memberService.selectOneMember(memberId);
		boolean available = member == null;
		
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("available", available);
		
		return ResponseEntity.status(HttpStatus.OK).body(map);//OK: 200, header는 생략
	}
}
