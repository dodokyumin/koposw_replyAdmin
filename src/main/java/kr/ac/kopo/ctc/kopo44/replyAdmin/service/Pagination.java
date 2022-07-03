package kr.ac.kopo.ctc.kopo44.replyAdmin.service;

public class Pagination {
	private int ppPage; //previous previous
	private int pPage; //previous
	private int nPage; //next
	private int nnPage; //next
	private int cPage;
	private int countPerPage; // 한 페이지 당 글 갯수
	private int PageSize; // 한 줄에 나타낼 페이지 갯수
	private int totalCount;
	private int firstPage;
	private int lastPage;
	
	public int getPpPage() {
		return ppPage;
	}
	public void setPpPage(int ppPage) {
		this.ppPage = ppPage;
	}
	public int getpPage() {
		return pPage;
	}
	public void setpPage(int pPage) {
		this.pPage = pPage;
	}
	public int getnPage() {
		return nPage;
	}
	public void setnPage(int nPage) {
		this.nPage = nPage;
	}
	public int getNnPage() {
		return nnPage;
	}
	public void setNnPage(int nnPage) {
		this.nnPage = nnPage;
	}
	public int getcPage() {
		return cPage;
	}
	public void setcPage(int cPage) {
		this.cPage = cPage;
	}
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	public int getPageSize() {
		return PageSize;
	}
	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
}
