package com.kh.spring.board.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloSpringUtils {

	/**
	 * 필요한 변수
	 * totalPage : 전체 페이지 수
	 * pagebarSize : 페이지바에 몇개씩 보여줄지 
	 * pageNo : 증감변수
	 * pagebarStart : 증감변수 시작값
	 * pagebarEnd : 증감변수 끝
	 */
	public static String getPagebar(int cPage, int limit, int totalContent, String url) {
		StringBuffer pagebar = new StringBuffer();//버퍼가 멀티스레딩 환경에서 더 적합함
		url += "?cPage=";// spring/board/boardList.do?cPage=
		
		final int pagebarSize = 5;//바뀌지 않는 값은 상수로 작성. 코드가 길어질 경우 변수명 헷갈릴 수 있음
		final int totalPage = (int) Math.ceil((double) totalContent / limit);
		final int pagebarStart = ((cPage - 1) / pagebarSize) * pagebarSize + 1;
		final int pagebarEnd = pagebarStart + pagebarSize - 1;
		int pageNo = pagebarStart;
		
		pagebar.append("<ul class=\"pagination justify-content-center\">\r\n");
		
		//1. prev
		if(pageNo == 1) {
			pagebar.append("<li class=\"page-item disabled\">\r\n"
					+ "	      <a class=\"page-link\" href=\"#\" aria-label=\"Previous\">\r\n"
					+ "	        <span aria-hidden=\"true\">&laquo;</span>\r\n"
					+ "	        <span class=\"sr-only\">Previous</span>\r\n"
					+ "	      </a>\r\n"
					+ "	    </li>\r\n");
		}else {
			pagebar.append("<li class=\"page-item\">\r\n"
					+ "	      <a class=\"page-link\" href=\""+url + (pageNo - 1)+"\" aria-label=\"Previous\">\r\n"
					+ "	        <span aria-hidden=\"true\">&laquo;</span>\r\n"
					+ "	        <span class=\"sr-only\">Previous</span>\r\n"
					+ "	      </a>\r\n"
					+ "	    </li>\r\n");
		}
		
		//2. pageNo
		while(pageNo <= pagebarEnd && pageNo <= totalPage) {
			if(pageNo == cPage) {
				pagebar.append("<li class=\"page-item active\"><a class=\"page-link\" href=\"#\">" + pageNo + "</a></li>\r\n");
			}else {
				pagebar.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + pageNo + "\">" + pageNo + "</a></li>\r\n");
			}
			pageNo++;
		}
		
		//3. next
		if(pageNo > totalPage) {
			pagebar.append("<li class=\"page-item disabled\">\r\n"
					+ "	      <a class=\"page-link\" href=\"#\" aria-label=\"Next\">\r\n"
					+ "	        <span aria-hidden=\"true\">&raquo;</span>\r\n"
					+ "	        <span class=\"sr-only\">Next</span>\r\n"
					+ "	      </a>\r\n"
					+ "	    </li>\r\n");
		}else {
			pagebar.append("<li class=\"page-item\">\r\n"
					+ "	      <a class=\"page-link\" href=\""+url + pageNo+"\" aria-label=\"Next\">\r\n"
					+ "	        <span aria-hidden=\"true\">&raquo;</span>\r\n"
					+ "	        <span class=\"sr-only\">Next</span>\r\n"
					+ "	      </a>\r\n"
					+ "	    </li>\r\n");
		}
		pagebar.append("</ul>");
		
		return pagebar.toString();
	}

	public static String getRenamedFilename(String originalFilename) {
		//확장자추출
		int beginIdex = originalFilename.lastIndexOf(".");
		String ext = "";
		if(beginIdex > -1) {
			ext = originalFilename.substring(beginIdex);// .txt
		}
		
		//새이름 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
		DecimalFormat df = new DecimalFormat("000");	
		return sdf.format(new Date()) + df.format(Math.random() * 1000) + ext;
	}

	/*
	   <ul class="pagination">
	    <li class="page-item">
	      <a class="page-link" href="#" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	        <span class="sr-only">Previous</span>
	      </a>
	    </li>
	    <li class="page-item"><a class="page-link" href="#">1</a></li>
	    <li class="page-item"><a class="page-link" href="#">2</a></li>
	    <li class="page-item"><a class="page-link" href="#">3</a></li>
	    <li class="page-item">
	      <a class="page-link" href="#" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	        <span class="sr-only">Next</span>
	      </a>
	    </li>
	  </ul>
	  
	 */
}
