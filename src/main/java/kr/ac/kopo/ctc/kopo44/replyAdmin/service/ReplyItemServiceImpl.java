package kr.ac.kopo.ctc.kopo44.replyAdmin.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
		ReplyItem replyItem = replyItemDao.readOne(id);

		return replyItem;
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

		replyItem.setTitle(strTitle);
		replyItem.setDate(date);
		replyItem.setContent(strContent);
		
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
	public boolean replyItemDeleteOne(String strId, int rootid, int relevel, int recnt) {
		int deter = 0;
		//파트 삭제 결과 판단용2
		int deter2 = 0;
		boolean result = false;
		int id = Integer.parseInt(strId);
		ReplyItemDao replyItemDao = new ReplyItemDaoImpl();

		int deleteLimit = findRecnt(rootid, relevel, recnt);
		
		deter2 = replyItemDao.deleteLowerLevels(rootid, recnt, deleteLimit);
		
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
		
		//현재 게시글의 recnt찾기
		int myRecnt = findRecnt(rootid, relevel, recnt);
		int myLevel = relevel + 1;
		
		replyItem.setRootid(rootid);
		replyItem.setRelevel(myLevel);
		replyItem.setRecnt(myRecnt);
		
		//번호 뒤로 밀기
		replyItemDao.pushBackRecnt(rootid, myLevel, myRecnt);
		
		
		//반환 값 정해주기
		deter = replyItemDao.createReplyOne(replyItem);
		if(deter == 1) {
			result = true;
		}
		
		return result;
	}

	
	//만드는 게시글의 recnt 값 구하기
	private int findRecnt(int rootid, int MomRelevel, int MomRecnt) {
		int myrecnt = 0;
		
		ReplyItemDao replyItemDao = new ReplyItemDaoImpl();
		
		ArrayList<Integer[]> rootidPack = replyItemDao.findRecnt(rootid, MomRecnt);

		//만약 엄마 row 말고 더 없을 경우. 그냥 더하기 해주기
		if(rootidPack.size() == 1) {
			Integer[] oneRow = rootidPack.get(0);
			myrecnt = oneRow[1]+1;
			return myrecnt;
		}
		
		for(int i = 1; i < rootidPack.size(); i++) {
			
			//같은 rootid를 가진 row들 중, relevel과 recnt를 추출하여 만든 배열
			Integer[] oneRow = rootidPack.get(i);
			
			
			if(oneRow[0] <= MomRelevel) {
				myrecnt = oneRow[1];
				break;
			}
			
			//배열에서 엄마 레벨과 같은 row가 없는 경우 그냥 반복문 끝나는거 방지.
			if(i == rootidPack.size()-1) {
				myrecnt = oneRow[1]+1;
				return myrecnt;
			}

		}
		
		
		return myrecnt;
	}

	

}
