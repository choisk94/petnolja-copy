package com.petnolja.notice.model.vo;

import java.util.Date;

public class Notice {

	private int noticeNo;
	private String noticeWriter;
	private String noticeCategory;
	private String noticeTitle;
	private String noticeContent;
	private Date createDate;
	private String noticeStatus;
	private int noticeCount;
	
	public Notice() {}

	public Notice(int noticeNo, String noticeWriter, String noticeCategory, String noticeTitle, String noticeContent,
			Date createDate, String noticeStatus, int noticeCount) {
		super();
		this.noticeNo = noticeNo;
		this.noticeWriter = noticeWriter;
		this.noticeCategory = noticeCategory;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.createDate = createDate;
		this.noticeStatus = noticeStatus;
		this.noticeCount = noticeCount;
	}
	
	

	/** 최서경
	 * 전체 공지사항 목록 조회용
	 */
	public Notice(int noticeNo, String noticeWriter, String noticeTitle, Date createDate, int noticeCount) {
		super();
		this.noticeNo = noticeNo;
		this.noticeWriter = noticeWriter;
		this.noticeTitle = noticeTitle;
		this.createDate = createDate;
		this.noticeCount = noticeCount;
	}
	
	

	/** 최서경
	 * 공지사항 상세조회, 공지사항 수정
	 */
	public Notice(int noticeNo, String noticeWriter, String noticeCategory, String noticeTitle, String noticeContent) {
		super();
		this.noticeNo = noticeNo;
		this.noticeWriter = noticeWriter;
		this.noticeCategory = noticeCategory;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
	}

	public int getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getNoticeWriter() {
		return noticeWriter;
	}

	public void setNoticeWriter(String noticeWriter) {
		this.noticeWriter = noticeWriter;
	}

	public String getNoticeCategory() {
		return noticeCategory;
	}

	public void setNoticeCategory(String noticeCategory) {
		this.noticeCategory = noticeCategory;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public int getNoticeCount() {
		return noticeCount;
	}

	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}

	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeWriter=" + noticeWriter + ", noticeCategory=" + noticeCategory
				+ ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent + ", createDate=" + createDate
				+ ", noticeStatus=" + noticeStatus + ", noticeCount=" + noticeCount + "]";
	};
	
	
}
