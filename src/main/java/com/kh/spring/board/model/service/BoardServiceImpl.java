package com.kh.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.dto.Attachment;
import com.kh.spring.board.model.dto.Board;

import lombok.extern.slf4j.Slf4j;

@Transactional(rollbackFor = Exception.class)//롤백기준이 될 예외종류 - 클래스 레벨에 작성하기. AOP로 처리됨
@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDao boardDao;
	
	@Override
	public List<Board> selectBoardList(Map<String, Integer> param) {
		//mybatis에서 제공하는 페이징 처리객체 RowBounds: 혹시 마이바티스를 다른걸로 대체할 경우 영향을 최소화하기 위해 서비스에 작성함
		//offset, limit
		int limit = param.get("limit");
		int offset = (param.get("cPage") - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return boardDao.selectBoardList(rowBounds);
	}
	
	@Override
	public int getTotalContent() {
		return boardDao.getTotalContent();
	}
	
//	@Transactional(rollbackFor = Exception.class)//롤백기준이 될 예외종류
	@Override
	public int insertBoard(Board board) {
		//insert board
		int result = boardDao.insertBoard(board);
		log.debug("board#no = {}", board.getNo());
		
		// 현재발급된 pk. board_no 가져오기 -> mybatis에서 지원하는 기능있음. Dao에서 작성함
				
		//insert attachment * n
		List<Attachment> attachments = board.getAttachments();
		if(!attachments.isEmpty()) {
			for(Attachment attach : attachments) {
				attach.setBoardNo(board.getNo());
				result = boardDao.insertAttachment(attach);
			}
		}
		return result;
	}
	
	@Override
	public Board selectOneBoard(int no) {
		//Board 조회
		Board board = boardDao.selectOneBoard(no);
		log.debug("board b4 attach ={}",board);
		//List<Attachment> 조회
		List<Attachment> attachments = boardDao.selectAttachmentListByBoardNo(no);
		board.setAttachments(attachments);
		log.debug("board aft attach = {}", board);
		return board;
	}
}
