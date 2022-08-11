package com.kh.spring.todo.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.todo.model.dao.TodoDao;
import com.kh.spring.todo.model.dto.Todo;

@Service
public class TodoServiceImpl implements TodoService {
	@Autowired
	private TodoDao todoDao;
	
	@Override
	public List<Todo> selectTodoList() {
		return todoDao.selectTodoList();
	}
	
	@Override
	public int insertTodo(Todo todo) {
		return todoDao.insertTodo(todo);
	}
	
	@Override
	public int updateTodo(Map<String, Object> param) {
		return todoDao.updateTodo(param);
	}
}
