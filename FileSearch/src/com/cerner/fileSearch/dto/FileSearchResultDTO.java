package com.cerner.fileSearch.dto;

public class FileSearchResultDTO {
	private String fileName;
	private long lineNoStartTime = -1;
	private long lineNoStopTime = -1;
	private long lineNoKeyWord = -1;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getLineNoStartTime() {
		return lineNoStartTime;
	}

	public void setLineNoStartTime(long lineNoStartTime) {
		this.lineNoStartTime = lineNoStartTime;
	}

	public long getLineNoStopTime() {
		return lineNoStopTime;
	}

	public void setLineNoStopTime(long lineNoStopTime) {
		this.lineNoStopTime = lineNoStopTime;
	}

	public long getLineNoKeyWord() {
		return lineNoKeyWord;
	}

	public void setLineNoKeyWord(long lineNoKeyWord) {
		this.lineNoKeyWord = lineNoKeyWord;
	}

}
