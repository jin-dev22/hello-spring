package com.kh.spring.member.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import com.kh.spring.demo.model.dto.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//기본 생성자는 nonNull인 인자 생성자만 만들어줌. 따로 아래처럼 작성해주면 이 생성자는 사라짐
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@NonNull
	private String memberId;
	@NonNull
	private String password;
	@NonNull
	private String name;
	private Gender gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")//localDate는 사용자 입력값 담을때 포맷팅이 필요함.
	private LocalDate birthday;
	private String email;
	@NonNull
	private String phone;
	private String address;
	private String[] hobby;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean enabled;
	
}
