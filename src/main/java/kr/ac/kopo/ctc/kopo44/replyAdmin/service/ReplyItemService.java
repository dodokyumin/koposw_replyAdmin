package kr.ac.kopo.ctc.kopo44.replyAdmin.service;

import java.util.List;

import kr.ac.kopo.ctc.kopo44.replyAdmin.domain.ReplyItem;

public interface ReplyItemService {

	//조회
	ReplyItem readOne(String id);
	
	List<ReplyItem> readAll(String strcPage);
	
	//페이지 불러오기
	Pagination getPagination(String strcPage);
	
	//입력
	boolean replyItemCreateOne(String title, String content);
	
	// 수정
	ReplyItem replyItemUpdateOne(String title, String content, String strId);
	
	// 삭제
	boolean replyItemDeleteOne(String strId);
	
	boolean replyItemDeleteAll();
	
	//총 갯수
	int getRowCount();
	
	//cPage null 체크
	String checkcPage(String strcPage);
	
	//새로운 날짜 받기
	String newDate();
	
	//페이지 null 체크
	public int checkCPage(String strcPage);
	
	//조회수 1추가
	int plusViewcnt(int inputId);
	
	//조회수 가져오기
	int getViewcnt(int inputId);
	
	//댓글 추가하기
	boolean createReply(String title, String content, String id, String relevel, String reRecnt);
	
	//댓글의 댓글...추가하기
	boolean createRereply(String title, String content, String id, String relevel);
}
