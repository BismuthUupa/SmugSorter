package date.animu.image_tagger.dao;

import org.springframework.data.repository.CrudRepository;

import date.animu.image_tagger.model.ImageTag;

public interface TagRepository extends CrudRepository<ImageTag, String> {

}
