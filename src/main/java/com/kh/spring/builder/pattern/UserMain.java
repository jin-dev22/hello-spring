package com.kh.spring.builder.pattern;

import java.time.LocalDate;

public class UserMain {

	public static void main(String[] args) {
		User u1 = User.builder()
					.code(1L)
					.username("honggd")
					.password("1234")
					.name("홍길동")
					.birthDay(LocalDate.of(1999, 9, 9))
					.phone("01012341234")
					.married(false)
					.build();//이때 User객체가 반환됨
		System.out.println(u1);
		
		User u2 = User.builder().username("honggd").password("1234").build();
		System.out.println(u2);
		
		
		Client client = Client.builder()
						.code(1L)
						.username("honggd")
						.password("1234")
						.name("홍길동")
						.birthDay(LocalDate.of(1999, 9, 9))
						.phone("01012341234")
						.married(false)
						.build();
		
		System.out.println(client);
	}

}
