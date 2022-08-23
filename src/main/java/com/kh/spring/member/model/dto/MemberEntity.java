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
public class MemberEntity {
	@NonNull
	protected String memberId;
	@NonNull
	protected String password;
	@NonNull
	protected String name;
	protected Gender gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")//localDate는 사용자 입력값 담을때 포맷팅이 필요함.
	protected LocalDate birthday;
	protected String email;
	@NonNull
	protected String phone;
	protected String address;
	protected String[] hobby;
	protected LocalDateTime createdAt;
	protected LocalDateTime updatedAt;
	protected boolean enabled;
	
}
