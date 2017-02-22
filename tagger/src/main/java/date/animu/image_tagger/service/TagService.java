package date.animu.image_tagger.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import date.animu.image_tagger.dao.TagRepository;
import date.animu.image_tagger.model.ImageTag;

@Service
public class TagService {
	
	@Autowired
	TagRepository tagDao;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${generate_from_scratch}")
	private String generateFromScratch;
	

	public Iterable<ImageTag> getAll(){
		return this.tagDao.findAll();
	}
	
	public List<String> getAsString(){
		List<String> tagString = new ArrayList<>();
		Iterable<ImageTag> tags = this.tagDao.findAll();
		for ( ImageTag tag: tags){
			tagString.add(tag.getName());
		}
		return tagString;
	}
	
	@PostConstruct
	public void init(){
		if ( generateFromScratch.toLowerCase().equals("true")){
			InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream("/tags.json"));
			List<ImageTag> tags = new ArrayList<>();
			try {
				List list = objectMapper.readValue(inputStreamReader, List.class);
				List<String> stringList = ((List<String>) list);
				for ( String str : stringList){
					tags.add(new ImageTag(str));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("TAGS PERSISTED : "+tags.size());
			this.tagDao.save(tags);
		}
	}
}
