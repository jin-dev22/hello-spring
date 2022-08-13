package com.kh.spring.todo.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.todo.model.dto.Todo;
import com.kh.spring.todo.model.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/todo")
@Slf4j
public class TodoController {
	@Autowired
	private TodoService todoService;
	
	/**
	 * AOP에서 사용하는 Proxy객체
	 * : 2가지 경우로 나뉨
	 * 	- 인터페이스 구현객체를 의존주입한 경우: jdk 동적 proxy객체 사용 class com.sun.proxy.$Proxy180
	 * 	- 인터페이스 구현체가 아닌 객체를 의존주입한 경우: cglib라이브러리에서 생성한 프록시 객체 사용
	 */
	@GetMapping("/todoList.do")
	public ModelAndView todoList(ModelAndView mav) {
		try {
			//new todoServiceImpl();..?
			log.debug("todoService = {}", todoService.getClass());//todoService = class com.sun.proxy.$Proxy95
			
			List<Todo> todoList = todoService.selectTodoList();
			log.debug("todoList = {}", todoList);
			mav.addObject(todoList);
			
			if(true)
				throw new RuntimeException("메롱");
			
			return mav;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping("/insertTodo.do")
	public String insertTodo(Todo todo, RedirectAttributes redirectAttr) {
		try {
			int result = todoService.insertTodo(todo);
			redirectAttr.addFlashAttribute("msg", "할일이 추가됨");

			if(true)
				throw new RuntimeException("으아아아");
			
			return "redirect:/todo/todoList.do";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping("/updateTodo.do")
	public String updateTodo(@RequestParam int no, @RequestParam boolean isCompleted) {
		try {
			Map<String, Object> param = new HashMap<>();
			param.put("no", no);
			param.put("isCompleted", isCompleted);
			int result = todoService.updateTodo(param);
			return "redirect:/todo/todoList.do";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
}
