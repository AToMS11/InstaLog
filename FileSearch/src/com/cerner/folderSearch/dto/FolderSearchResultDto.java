package com.cerner.folderSearch.dto;

public class FolderSearchResultDto {
String fileName;

public String getFileName() {
	return fileName;
}

public void setFileName(String fileName) {
	this.fileName = fileName;
}

public FolderSearchResultDto(String fileName) {
	super();
	this.fileName = fileName;
}

}
