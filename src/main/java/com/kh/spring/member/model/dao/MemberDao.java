package com.kh.spring.member.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.spring.member.model.dto.Member;
/**
 * mybatis에서 알아서daoImpl을 만들도록 할 수 있음. 
 * mapper파일에서 <mapper namespace="com.kh.spring.member.model.dao.MemberDao">이렇게 작성
 */
@Mapper
public interface MemberDao {

	int insertMember(Member member);

}
