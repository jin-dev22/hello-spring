package com.kh.spring.todo.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
	private int no;
	@NonNull
	private String todo;
	private LocalDateTime createdAt;
	private LocalDateTime completedAt;
}
