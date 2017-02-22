package date.animu.image_tagger.utils;

import java.io.File;
import java.io.FileFilter;

public class AnimuPicFileFilter implements FileFilter {

	private String[] fileTypes = {".jpeg", ".jpg", ".png", ".gif"};
	
	@Override
	public boolean accept(File file) {
		if ( file.isDirectory() ){
			return true;
		}
		boolean matchingFileType = false;
		for ( String fileType : fileTypes){
			if ( file.getName().endsWith(fileType)){
				matchingFileType = true;
				break;
			}
		}
		
		return matchingFileType;
	}

}
