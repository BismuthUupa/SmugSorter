package date.animu.image_tagger.dao;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import date.animu.image_tagger.model.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {

	public ArrayList<Image> findByTagged(boolean tagged);
}
