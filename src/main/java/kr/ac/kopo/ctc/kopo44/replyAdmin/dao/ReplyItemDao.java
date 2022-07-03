package kr.ac.kopo.ctc.kopo44.replyAdmin.dao;

import java.util.List;

import kr.ac.kopo.ctc.kopo44.replyAdmin.domain.ReplyItem;

public interface ReplyItemDao {
	final static String COLUMN_ID = "id";
	final static String COLUMN_ROOTID = "rootid";
	final static String COLUMN_RECNT = "recnt";
	final static String COLUMN_RELEVEL = "relevel";
	final static String TABLE_NAME = "replyBoard";
	
	//create
	int createOne(ReplyItem ReplyItem);
	
	//read
	ReplyItem readOne(int id);
	List<ReplyItem> readAll(int startIndex, int countPerPage);
	
	//update
	ReplyItem updateOne(ReplyItem ReplyItem);
	
	//delete
	int deleteOne(int id);

	//count rows
	int RowCount();	
	
	//delete all
	int deleteAll();

	//조회수 더하기
	void plusViewcnt(int inputId);

	//조회수 가져오기
	int getViewcnt(int inputId);
	
	//마지막 id값 가져오기
	int getLastId();
	
	//댓글 만들기
	int createReplyOne(ReplyItem replyItem);

	//대댓댓글 생성시 뒤 recnt 한칸씩 밀기
	void pushBackRecnt(int rootid, int relevel, int recnt);
}
