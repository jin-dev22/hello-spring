package com.kh.spring.board.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
//@AllArgsConstructor //롬복의 한계. 부모 클래스 필드는 못 가져옴
@ToString(callSuper = true)
public class Board extends BoardEntity {
	private int attachCount;

	public Board(int no, String title, String memberId, String content, int readCount, LocalDateTime createdAt,
			LocalDateTime updatedAt, int attachCount) {
		super(no, title, memberId, content, readCount, createdAt, updatedAt);
		this.attachCount = attachCount;
	}
	
	
}
