package com.kh.spring.todo.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/todoList.do")
	public void todoList(Model model) {
		try {
			List<Todo> todoList = todoService.selectTodoList();
			log.debug("todoList = {}", todoList);
			model.addAttribute(todoList);			
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
