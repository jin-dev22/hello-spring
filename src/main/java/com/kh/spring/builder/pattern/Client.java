package com.kh.spring.builder.pattern;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder//User패키지에 작성한 빌터패턴 내용 자동완성해줌
@NoArgsConstructor
@AllArgsConstructor
public class Client {
	private long code;// pk
	private String username;//유저 아이디
	private String password;
	private String name;
	private LocalDate birthDay;
	private String phone;
	private boolean married;
}
