package com.cerner.fileSearch.dto;

import java.util.Date;

public class FileSearchRequestDTO {
String fileName;
String keyword;
Date startDateTime;
Date stopDatetime;
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getKeyword() {
	return keyword;
}
public void setKeyword(String keyword) {
	this.keyword = keyword;
}
public Date getStartDateTime() {
	return startDateTime;
}
public void setStartDateTime(Date startDateTime) {
	this.startDateTime = startDateTime;
}
public Date getStopDatetime() {
	return stopDatetime;
}
public void setStopDatetime(Date stopDatetime) {
	this.stopDatetime = stopDatetime;
}
public FileSearchRequestDTO(String fileName, String keyword, Date startDateTime, Date stopDatetime) {
	this.fileName = fileName;
	this.keyword = keyword;
	this.startDateTime = startDateTime;
	this.stopDatetime = stopDatetime;
}


}
