package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kh.spring.member.model.dto.Member;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인여부확인
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		if(loginMember == null) {
			// 메소드시그니처를 변경할 수 없으므로 RedirectAttribute대신 FlashMap사용함
			FlashMap flashMap = new FlashMap();
			flashMap.put("msg", "로그인 후 이용가능");
			FlashMapManager manager = RequestContextUtils.getFlashMapManager(request);
			manager.saveOutputFlashMap(flashMap, request, response);
			
			response.sendRedirect(request.getContextPath()+"/");
			return false;
		}
		
		
		return super.preHandle(request, response, handler);
	}
}
