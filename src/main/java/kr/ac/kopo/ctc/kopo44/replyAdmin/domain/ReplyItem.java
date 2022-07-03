package kr.ac.kopo.ctc.kopo44.replyAdmin.domain;

import java.util.Objects;

public class ReplyItem {
	private int id;
	private String title;
	private String date;
	private String content;
	private int rootid;
	private int relevel;
	private int recnt;
	private int viewcnt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRootid() {
		return rootid;
	}
	public void setRootid(int rootid) {
		this.rootid = rootid;
	}
	public int getRelevel() {
		return relevel;
	}
	public void setRelevel(int relevel) {
		this.relevel = relevel;
	}
	public int getRecnt() {
		return recnt;
	}
	public void setRecnt(int recnt) {
		this.recnt = recnt;
	}
	public int getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(content, date, id, recnt, relevel, rootid, title, viewcnt);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReplyItem other = (ReplyItem) obj;
		return Objects.equals(content, other.content) && Objects.equals(date, other.date) && id == other.id
				&& recnt == other.recnt && relevel == other.relevel && rootid == other.rootid
				&& Objects.equals(title, other.title) && viewcnt == other.viewcnt;
	}
	
	@Override
	public String toString() {
		return "replyItem [id=" + id + ", title=" + title + ", date=" + date + ", content=" + content + ", rootid="
				+ rootid + ", relevel=" + relevel + ", recnt=" + recnt + ", viewcnt=" + viewcnt + "]";
	}
	
	
}
