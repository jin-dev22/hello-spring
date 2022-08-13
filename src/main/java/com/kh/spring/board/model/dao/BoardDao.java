package com.kh.spring.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.spring.board.model.dto.Board;

@Mapper
public interface BoardDao {
	@Select("select b.*, (select count(*) from attachment where board_no = b.no) attach_count from board b order by desc")
	List<Board> selectBoardList();

}
