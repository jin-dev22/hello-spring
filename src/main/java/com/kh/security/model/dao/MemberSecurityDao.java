package com.kh.security.model.dao;

import com.kh.spring.member.model.dto.Member;

public interface MemberSecurityDao {

	Member loadUserByUsername(String username);

}
