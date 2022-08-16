package com.kh.spring.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.session.RowBounds;

import com.kh.spring.board.model.dto.Attachment;
import com.kh.spring.board.model.dto.Board;

@Mapper
public interface BoardDao {
	@Select("select b.*, (select count(*) from attachment where board_no = b.no) attach_count from board b order by no desc")
	List<Board> selectBoardList(RowBounds rowBounds);

	@Select("select count(*) from board")
	int getTotalContent();

	@Insert("insert into board values(seq_board_no.nextval, #{title}, #{memberId}, #{content}, default, default, default)")
	@SelectKey(statement = "select seq_board_no.currval from dual", before = false, keyProperty = "no", resultType = int.class)//발급받은 pk번호를 board의 no에 setNo()해줌
	int insertBoard(Board board);

	@Insert("insert into attachment values(seq_attachment_no.nextval, #{boardNo}, #{originalFilename}, #{renamedFilename}, default, default)")
	int insertAttachment(Attachment attach);

}
