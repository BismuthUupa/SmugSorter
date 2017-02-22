package date.animu.image_tagger.controler;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import date.animu.image_tagger.model.Image;
import date.animu.image_tagger.service.ImageService;
import date.animu.image_tagger.service.TagService;

@RestController
public class Controller {

	@Autowired 
	ImageService imageService;
	
	@Autowired
	TagService tagService;
	
	
	
	@RequestMapping(method=RequestMethod.GET, value="/image")
	public Image getImageObject(){
		Image image;
		image = (Math.random() >= 0.333 )? this.imageService.getNotTagged() : this.imageService.getTagged();
		return image;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/image/{id}", produces=MediaType.IMAGE_PNG_VALUE)
	public byte[] getImage(@PathVariable("id") long id) throws IOException{
		Image img = this.imageService.findOne(id);
		File file = new File(img.getPath());
		byte[] imgDataBa = new byte[(int)file.length()];
		DataInputStream dataIs = new DataInputStream(new FileInputStream(file));
		dataIs.readFully(imgDataBa);
		return imgDataBa;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/tags")
	public List<String> getTags(){
		return this.tagService.getAsString();
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/image")
	public void updateImage(@RequestBody Image image){
		this.imageService.updateImage(image);
	}
	@RequestMapping(method=RequestMethod.GET, value="/completion")
	public double getCompletion(){
		return this.imageService.getCompletion();
	}
}
