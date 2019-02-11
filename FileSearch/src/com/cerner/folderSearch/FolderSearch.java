package com.cerner.folderSearch;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.cerner.folderSearch.dto.FolderSearchRequestDto;
import com.cerner.folderSearch.dto.FolderSearchResultDto;

public class FolderSearch {
	
	public List<FolderSearchResultDto> folderSearch(FolderSearchRequestDto request) {
		List<FolderSearchResultDto> list=new ArrayList<>();
		if(request.getEnvDirectoryPath()!=null && !request.getEnvName().equals("")){
			 Path filePath = Paths.get(request.getEnvDirectoryPath());
			if(Files.notExists(filePath)){
				System.err.print("Unable to access "+request.getEnvDirectoryPath());
			}else{
				walk(request.getEnvDirectoryPath(), list);
			}
		}
		return list;
	}
	
	
	 public void walk( String path ,List<FolderSearchResultDto> resultList) {

	        File root = new File( path );
	        File[] list = root.listFiles();

	        if (list == null) return;

	        for ( File f : list ) {
	            if ( f.isDirectory() ) {
	                walk( f.getAbsolutePath(),resultList );
	            }
	            else {
	                FolderSearchResultDto result=new FolderSearchResultDto(f.getAbsolutePath());
	                resultList.add(result);
	            }
	        }
	    }

}
