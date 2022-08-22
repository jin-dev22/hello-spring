package com.kh.spring.builder.pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Builder Pattern
 * - GoF의 디자인패턴 중 생성패턴의 하나로 소개되어 있음
 * - 필드가 여러개일 경우, 필드값을 개별적으로 등록 후 객체를 생성하는 방법.
 *
 */
public class User {
	private long code;// pk
	private String username;//유저 아이디
	private String password;
	private String name;
	private LocalDate birthDay;
	private String phone;
	private boolean married;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(long code, String username, String password, String name, LocalDate birthDay, String phone,
			boolean married) {
		super();
		this.code = code;
		this.username = username;
		this.password = password;
		this.name = name;
		this.birthDay = birthDay;
		this.phone = phone;
		this.married = married;
	}
	
	/**
	 * Builder패턴은 롬복으로 처리가능. 어떤 원리인지 이해를 위해 직접 작성
	 *
	 */
	public static class Builder{
		private long code;// pk
		private String username;//유저 아이디
		private String password;
		private String name;
		private LocalDate birthDay;
		private String phone;
		private boolean married;
		
		public Builder code(long code) {
			this.code = code;
			return this;//Builder객체 자신을 반환
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;//Builder객체 자신을 반환
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;//Builder객체 자신을 반환
		}
		
		public Builder name(String name) {
			this.name = name;
			return this;//Builder객체 자신을 반환
		}
		
		public Builder birthDay(LocalDate birthDay) {
			this.birthDay = birthDay;
			return this;//Builder객체 자신을 반환
		}
		
		public Builder phone(String phone) {
			this.phone = phone;
			return this;//Builder객체 자신을 반환
		}
		
		public Builder married(boolean married) {
			this.married = married;
			return this;//Builder객체 자신을 반환
		}
		
		public User build() {
			return new User(code, username, password, name, birthDay, phone, married);
		}
	}
	
	public static Builder builder() {
		return new Builder();
	}

	@Override
	public String toString() {
		return "User [code=" + code + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", birthDay=" + birthDay + ", phone=" + phone + ", married=" + married + "]";
	}
	
	
}
