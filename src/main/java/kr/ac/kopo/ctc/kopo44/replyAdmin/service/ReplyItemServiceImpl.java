package kr.ac.kopo.ctc.kopo44.replyAdmin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.ac.kopo.ctc.kopo44.replyAdmin.dao.ReplyItemDao;
import kr.ac.kopo.ctc.kopo44.replyAdmin.dao.ReplyItemDaoImpl;
import kr.ac.kopo.ctc.kopo44.replyAdmin.domain.ReplyItem;

public class ReplyItemServiceImpl implements ReplyItemService {
	ReplyItemDao replyItemDao = new ReplyItemDaoImpl();

	private int countPerPage = 10;
	final int COUNT_PER_PAGE = 10;
	final int PAGE_SIZE = 10;

	@Override
	public List<ReplyItem> readAll(String strcPage) {

		Pagination pagination = new Pagination();
		int cPage = checkCPage(strcPage);
		pagination.setcPage(cPage);
		pagination.setCountPerPage(countPerPage);

		// 전체 목록 조회 
		int startIndex = ((pagination.getcPage() - 1) * pagination.getCountPerPage() + 1);
		
		List<ReplyItem> replyItem = replyItemDao.readAll(startIndex, countPerPage);
	
		return replyItem;
	}

	@Override
	public ReplyItem readOne(String strId) {
		int id = Integer.parseInt(strId);
		ReplyItem scoreItem = replyItemDao.readOne(id);

		return scoreItem;
	}

	@Override
	// countPerPage 한페이지당 몇개의 리스트
	// pageSize 밑에 보여줄 페이지 갯수
	// totalCount 총 게시물 수
	public Pagination getPagination(String strcPage) {
		
		int currPage = Integer.parseInt(strcPage);
		
		Pagination p = new Pagination();
		
		// 총 레코드 수 조회
		int totalCount = getRowCount();
		
		// >>
		int totalPage;
		if ((totalCount % countPerPage) > 0) {
			totalPage = totalCount / countPerPage + 1;
		} else {
			totalPage = totalCount / countPerPage;
		}

		// currPage
		if (currPage > totalPage) {
			currPage = totalPage;
		} else if (currPage < 1) {
			currPage = 1;
		}
		p.setcPage(currPage);
		
		//pageSize
		p.setPageSize(PAGE_SIZE);

		// <<
		p.setPpPage(1);
		// >>
		p.setNnPage(totalPage);

		// >
		if ((totalPage - currPage) < PAGE_SIZE) {
			p.setnPage(totalPage);
		} else {
			p.setnPage((currPage / PAGE_SIZE + 1) * PAGE_SIZE + 1);
		}
		// < 
		if ((currPage / PAGE_SIZE) == 0) {
			p.setpPage(1);
		} else {
			p.setpPage((currPage-PAGE_SIZE / PAGE_SIZE)); //이 부분 문데
		}

		// 첫 페이지 번호
		int startPage = (currPage / PAGE_SIZE) * PAGE_SIZE + 1;	
		if ((currPage % PAGE_SIZE) == 0) {		
			startPage -= PAGE_SIZE;
		}
		p.setFirstPage(startPage);
		
		// 마지막 페이지 번호
		int lastPage = (startPage + PAGE_SIZE - 1) >= totalPage ? totalPage : (startPage + PAGE_SIZE - 1);
		p.setLastPage(lastPage);
		
		if(lastPage >= totalPage) {
			p.setLastPage(totalPage);
		}
		
		return p;
	}

	@Override
	public boolean replyItemCreateOne(String strTitle, String strContent) {
		
		int deter = 0;
		boolean result = false;
		
		ReplyItemDao replyItemDao = new ReplyItemDaoImpl();
		ReplyItem replyItem = new ReplyItem();
		
		String date = newDate();

		String title = strTitle;
		String content = strContent;

		replyItem.setTitle(title);
		replyItem.setDate(date);
		replyItem.setContent(content);
		
		//replyItem.setRootid(rootid);
		replyItem.setRelevel(0);
		replyItem.setRecnt(0);
		
		deter = replyItemDao.createOne(replyItem);
		if(deter == 1) {
			result = true;
		}
		
		return result;
	}

	@Override
	public ReplyItem replyItemUpdateOne(String strTitle, String strContent, String strId) {
		
		String title = strTitle;
		String content = strContent;
		int id = Integer.parseInt(strId);
		
		ReplyItemDao replyItemDao = new ReplyItemDaoImpl();
		ReplyItem replyItem = new ReplyItem();
		
		String date = newDate();

		replyItem.setId(id);
		replyItem.setTitle(title);
		replyItem.setDate(date);
		replyItem.setContent(content);
		
		
		replyItemDao.updateOne(replyItem);
		
		return replyItem;
	}

	@Override
	public boolean replyItemDeleteOne(String strId) {
		int deter = 0;
		boolean result = false;
		int id = Integer.parseInt(strId);
		ReplyItemDao replyItemDao = new ReplyItemDaoImpl();
		deter = replyItemDao.deleteOne(id);
		
		if(deter == 1) {
			result = true;
		}
		
		return result;
	}

	@Override
	public int getRowCount() {
		int rowcount = replyItemDao.RowCount();
		return rowcount;
	}

	@Override
	public String checkcPage(String strcPage) {
		if(strcPage == null) {
			strcPage = "1";
		}
		return strcPage;
	}

	@Override
	public String newDate() {
		Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd"); 
		//원하는 데이터 포맷 지정
		String date = simpleDateFormat.format(nowDate);
		return date;
	}

	@Override
	public boolean replyItemDeleteAll() {
		int deter = 0;
		boolean result = false;
		ReplyItemDao replyItemDao = new ReplyItemDaoImpl();
		deter = replyItemDao.deleteAll();
		
		if(deter >= 0) {
			result = true;
		}
		
		return result;
	}
	
	@Override
	public int checkCPage(String strcPage) {
		// 현재 페이지 번호 null 체크
		int cPage = 0;
		if (strcPage == null) {
			cPage = 1;
		} else {
			cPage = Integer.parseInt(strcPage);
		}
		return cPage;
	}

	@Override
	public int plusViewcnt(int inputId) {
		replyItemDao.plusViewcnt(inputId);
		return 0;
	}

	@Override
	public int getViewcnt(int inputId) {
		int vc = replyItemDao.getViewcnt(inputId);
		return vc;
	}

	@Override
	public boolean createReply(String title, String content, String strRootid, String strRelevel, String strRecnt) {
		//ㅔ
		int deter = 0;
		boolean result = false;
		
		ReplyItemDao replyItemDao = new ReplyItemDaoImpl();
		ReplyItem replyItem = new ReplyItem();
		
		String date = newDate();
		
		replyItem.setTitle(title);
		replyItem.setDate(date);
		replyItem.setContent(content);

		int rootid = Integer.parseInt(strRootid);
		int relevel = Integer.parseInt(strRelevel);
		int recnt = Integer.parseInt(strRecnt);
		
		replyItem.setRootid(rootid);
		replyItem.setRelevel(relevel);
		replyItem.setRecnt(recnt);
		
		deter = replyItemDao.createReplyOne(replyItem);
		if(deter == 1) {
			result = true;
		}
		
		return result;
	}

	@Override
	public boolean createRereply(String title, String content, String id, String relevel) {
		// TODO Auto-generated method stub
		return false; 
	}
	
	

}
