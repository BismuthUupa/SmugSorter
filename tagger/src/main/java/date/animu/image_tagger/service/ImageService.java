package date.animu.image_tagger.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import date.animu.image_tagger.dao.ImageRepository;
import date.animu.image_tagger.model.Image;
import date.animu.image_tagger.utils.AnimuPicFileFilter;

@Service
public class ImageService {

	@Autowired
	private ImageRepository imageDao;
	
	@Value("${images.path}")
	private String imageStashPath;
	
	@Value("${generate_from_scratch}")
	private String generateFromScratch;
	
	public void createImage( String filePath){
		Image image = new Image(filePath);
		this.imageDao.save(image);
	}
	
	public Image findOne (long id){
		return this.imageDao.findOne(id);
	}
	
	public Image getTagged(){
		ArrayList<Image> taggedImages = this.imageDao.findByTagged(true);
		if ( taggedImages.isEmpty() ){
			return this.getNotTagged();
		}
		return taggedImages.get((int) (taggedImages.size() * Math.random()));
	}
	
	public Image getNotTagged(){
		ArrayList<Image> untaggedImages = this.imageDao.findByTagged(false);
		return untaggedImages.get((int) (untaggedImages.size() * Math.random()));
	}
	
	public double getCompletion(){
		long allImages = this.imageDao.count();
		long tagged = this.imageDao.findByTagged(true).size();
		return (double) (tagged * 100/ allImages);
	}
	
	public void updateImage ( Image image ){
		if ( ! validateTagging(image) ){
			System.out.println("discarded change");
			return;
		}
		System.out.println("saving image");
		Image originalImage = this.imageDao.findOne(image.getId());
		originalImage.setTagged(true);
		originalImage.setTags(image.getTags());
		this.imageDao.save(originalImage);
	}
	
	private boolean validateTagging(Image image){
		return (image.getTags() != null && ! image.getTags().isEmpty());
	}
	
	@PostConstruct
	public void init(){
		if ( generateFromScratch.toLowerCase().equals("true")){
			if ( this.imageStashPath == null ){
				throw new RuntimeException("You need to specify a path to the pic stash");
			}
			File imageRootFolder = new File( this.imageStashPath);
			if( ! imageRootFolder.isDirectory()){
				throw new RuntimeException ("Specified path to the pic stash is wrong somehow");
			}
			Collection<Image> images = fileWalker(imageRootFolder);
			System.out.println("IMAGES PERSISTED : "+images.size());
			this.imageDao.save(images);
		}
	}
	
	private Collection<Image> fileWalker(File file){
		Collection<Image> images = new ArrayList<>();
		if( file.isDirectory() ){
			for ( File f : file.listFiles(new AnimuPicFileFilter())){
				images.addAll( fileWalker(f) );
			}
		}
		else{
			images.add(new Image(file.getAbsolutePath()));
		}
		return images;
	}
	
}
