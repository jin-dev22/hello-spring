package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.board.common.HelloSpringUtils;
import com.kh.spring.board.model.dto.Attachment;
import com.kh.spring.board.model.dto.Board;
import com.kh.spring.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	//생명주기가 가장 긴 scope객체 ServletContext : 스프링 빈 관리하는 servlet-context와 무관.
	@Autowired
	ServletContext application;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@GetMapping("/boardList.do")
	public void boardList(@RequestParam(defaultValue = "1") int cPage, Model model, HttpServletRequest request) {
		//1. content영역
		Map<String, Integer> param = new HashMap<>();
		int limit = 10;
		param.put("cPage", cPage);
		param.put("limit", limit);
		List<Board> list = boardService.selectBoardList(param);
		log.debug("list = {}", list);
		model.addAttribute("list", list);
		
		//2. pagebar영역
		int totalContent = boardService.getTotalContent();
		log.debug("totalContent = {}", totalContent);
		String url = request.getRequestURI();// /spring/board/boardList.do
		String pagebar = HelloSpringUtils.getPagebar(cPage, limit, totalContent, url);
		model.addAttribute("pagebar", pagebar);
	}
	
	@GetMapping("/boardForm.do")
	public void boardForm() {
		
	}
	
	@PostMapping("/boardEnroll.do")
	public String boardEnroll(Board board, @RequestParam(name = "upFile") List<MultipartFile> upFileList, 
			RedirectAttributes redirectAttr) throws IllegalStateException, IOException{//upFile은 절대 null값이 넘어오지 않는다
		
		for(MultipartFile upFile : upFileList) {
//			log.debug("upFile = {}", upFile); 
//			log.debug("upFile#name = {}", upFile.getName()); 
//			log.debug("upFile#filename = {}", upFile.getOriginalFilename()); 
//			log.debug("upFile#size = {}", upFile.getSize()); 
			
			if(!upFile.isEmpty()) {//파일 안올려도 무조건 upFile이 넘어옴, 검사 안하면 빈 파일이 경로에 저장됨
				//a. 서버 컴퓨터에 저장
				String saveDirectory = application.getRealPath("/resources/upload/board");
				String renamedFilename = HelloSpringUtils.getRenamedFilename(upFile.getOriginalFilename());// 20220816_193012345_123.txt
				File destFile = new File(saveDirectory, renamedFilename);
				upFile.transferTo(destFile);//해당 경로에 파일을 저장
				
				//b. DB저장을 위해 Attachment객체 생성
				Attachment attach = new Attachment(upFile.getOriginalFilename(), renamedFilename);
				board.add(attach);
			}
		}
		log.debug("board = {}", board); 
		
		//업무로직: DB저장
		int result = boardService.insertBoard(board);//insert board,attach는 트랜잭션으로 묶으려면 서비스단에서 insert처리해야 됨
		
		redirectAttr.addFlashAttribute("msg", "게시글 저장 성공");
		
		return "redirect:/board/boardList.do";
	}
	
	@GetMapping("/boardDetail.do")
	public void baordDetail(@RequestParam int no, Model model) {
		//Board (+Attachment) 조회. 방법 2가지 있음
		//1. 쿼리 2번 날리는 버전
		Board board = boardService.selectOneBoard(no);
		log.debug("board = {}", board);
		model.addAttribute("board", board);
	}
	
	/**
	 * Resource
	 * 다음 구현체들의 추상화 레이어를 제공.(PSA)
	 * - 웹상 자원: 구현클래스=UrlResource
	 * - classpath 자원: ClassPathResource
	 * - 서버컴퓨터 자원: FileSystemResource
	 * - ServletContext(web root)자원: ServletContextResource
	 * - 입출력 자원: InputStreamResource
	 * - 이진데이터 자원: ByteArrayResource
	 * @throws IOException 
	 * 
	 * @ResponseBody: 핸들러의 반환된 자바객체를 응답메세지의 body에 직접 출력해줌
	 */
	@GetMapping(path="/fileDownload.do", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public Resource fileDownload(@RequestParam int no, HttpServletResponse response) throws IOException {
		Attachment attach = boardService.selectOneAttachment(no);
		log.debug("attach = {}", attach);
		
		String saveDirectory = application.getRealPath("/resources/upload/board");
		File downFile = new File(saveDirectory, attach.getRenamedFilename());
		String location = "file:" + downFile;//file: 리소스가 어떤 자원을 반환할지 인식하는 프로토콜 File#toString은 파일의 절대경로를 반환
		Resource resource = resourceLoader.getResource(location);
		log.debug("resource = {}", resource);
		log.debug("resource#file = {}", resource.getFile());
		
		//응답헤더 작성
		response.setContentType("application/octet-stream; charset=utf-8");
		String filename = new String(attach.getOriginalFilename().getBytes("utf-8"), "iso-8859-1");//중간에 톰캣에 의해 전송되면서 깨지는거 방지
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
		
		return resource;//객체를 리턴하면 ResponseBody가 이 반환값을 body에 써줌. 이전jsp,서블릿만사용했을때 코드랑 비교해보기 
	}
	
	@GetMapping("/boardUpdate.do")
	public void boardUpdate(@RequestParam int no, Model model) {
		Board board = boardService.selectOneBoard(no);
		model.addAttribute("board", board);
	}
	
	/**
	 * @실습문제
	 * - 게시글 수정
	 * - 첨부파일 삭제(실제파일삭제, DB attachment 행 제거)
	 * - 첨부파일 추가
	 */
	@PostMapping("/boardUpdate.do")
	public String boardUpdate() {
		
		
		return "redirect:/board/boardDetail.do";
	}
}
