package com.kh.spring.todo.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.todo.model.dto.Todo;

@Mapper
public interface TodoDao {

	@Select("select * from todo order by completed_at desc, no")
	List<Todo> selectTodoList();

	@Insert("insert into todo values (seq_todo_no.nextval, #{todo}, default, null)")
	int insertTodo(Todo todo);

	int updateTodo(Map<String, Object> param);

}
