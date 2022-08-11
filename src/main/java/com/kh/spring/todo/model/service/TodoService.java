package com.kh.spring.todo.model.service;

import java.util.List;
import java.util.Map;

import com.kh.spring.todo.model.dto.Todo;

public interface TodoService {

	List<Todo> selectTodoList();

	int insertTodo(Todo todo);

	int updateTodo(Map<String, Object> param);

}
