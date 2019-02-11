package com.cerner.fileSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.cerner.fileSearch.dto.FileSearchRequestDTO;
import com.cerner.fileSearch.dto.FileSearchResultDTO;

public class FileSearch {
public FileSearchResultDTO fileSearch(FileSearchRequestDTO request){
	FileSearchResultDTO fileSearchResultDTO=null;
	if(request.getFileName()!=null && !request.getFileName().equals("")){
		Path path=Paths.get(request.getFileName());
		try {
			FileTime lastModifiedTime=Files.getLastModifiedTime(path);
			if(request.getStartDateTime()!=null){
				Date newDate = new Date( lastModifiedTime.toMillis() );
				if(request.getStartDateTime().after(newDate)){
					return null;
				}
			}
			
			fileSearchResultDTO=parseFile(request);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return fileSearchResultDTO;
}


public FileSearchResultDTO parseFile(FileSearchRequestDTO request) throws FileNotFoundException{
	FileSearchResultDTO returnDto=new FileSearchResultDTO();
	returnDto.setFileName(request.getFileName());
    Scanner scan = new Scanner(new File(request.getFileName()));
    long seekPositionStartTime=-1;
    long seekPositionStopTime=-1;
    long seekPositionKeyWord=-1;
    int lineNo=1;
    
    if(request.getStartDateTime()==null){
    	seekPositionStartTime=0;
    	returnDto.setLineNoStartTime(seekPositionStartTime);
    }
    if(request.getStopDatetime()==null){
    	returnDto.setLineNoStartTime(seekPositionStopTime);
    	seekPositionStopTime=0;
    }
    if(request.getKeyword()==null||!request.getKeyword().equals("")){
    	returnDto.setLineNoKeyWord(seekPositionKeyWord);
    	seekPositionKeyWord=0;
    }
    while(scan.hasNext()){
        String line = scan.nextLine().toLowerCase().toString();
        if((seekPositionStartTime==-1||seekPositionStopTime==-1) && (request.getStartDateTime()!=null||request.getStopDatetime()!=null)){
        	String arr[]=line.split(",");
        	if(arr!=null && arr[0]!=null && arr[0].length()=="2019-02-06 20:24:56".length()){
        		Date parsedDate=getParsedDate(arr[0]);
        		if(!parsedDate.before(request.getStartDateTime())){
        			seekPositionStartTime=lineNo;
        			returnDto.setLineNoStartTime(seekPositionStartTime);
        		}  
        		else if(!parsedDate.before(request.getStopDatetime())){
        			seekPositionStopTime=lineNo;	
        			returnDto.setLineNoStartTime(seekPositionStopTime);
        			break;
        		}
        	}
        }
        if(seekPositionKeyWord==-1 && seekPositionStartTime!=-1 && line.indexOf(request.getKeyword().toLowerCase())!=-1){
        	seekPositionKeyWord=lineNo;
        	returnDto.setLineNoKeyWord(seekPositionKeyWord);
        	
        	break;
        }
       
        lineNo++;
    }
    scan.close();
    
  return returnDto;
    
}


Date getParsedDate(String str){
	String pattern = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	try {
		Date parsedDate= simpleDateFormat.parse(str);
		 Calendar cal = Calendar.getInstance();
	        cal.setTime(parsedDate);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND,0);
	        return cal.getTime();
	} catch (ParseException e) {
		return null;
	}
}
}
