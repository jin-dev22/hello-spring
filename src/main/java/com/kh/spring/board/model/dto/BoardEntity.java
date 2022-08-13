package com.kh.spring.board.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity {
	private int no;
	private String title;
	private String memberId;
	private String content;
	private int readCount;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
