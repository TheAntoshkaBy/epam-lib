package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.pojo.TagPOJO;
import com.epam.esm.repository.jpa.impl.TagRepositoryJPA;
import com.epam.esm.service.TagService;
import com.epam.esm.service.support.PojoConverter;
import com.epam.esm.service.validator.TagValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ShopTagService implements TagService {

    private final TagRepositoryJPA tagRepository;
    private final TagValidator tagValidator;
    private final PojoConverter<TagPOJO, Tag> converter;

    @Autowired
    public ShopTagService(TagRepositoryJPA tagRepository, TagValidator tagValidator,
                          PojoConverter<TagPOJO, Tag> converter) {
        this.tagRepository = tagRepository;
        this.tagValidator = tagValidator;
        this.converter = converter;
    }

    @Override
    public List<TagPOJO> findAll(int page, int size) {
        page = PojoConverter.convertPaginationPageToDbOffsetParameter(page, size);

        return converter.convert(tagRepository.findAll(--page, size));
    }

    @Override
    public TagPOJO find(long id) {
        tagValidator.checkId(id);
        return new TagPOJO(tagRepository.findById(id));
    }

    @Override
    public TagPOJO findMostWidelyUsedTag() {
        return new TagPOJO(tagRepository.findMostWidelyUsedTag());
    }

    @Override
    public void delete(long id) {
        tagValidator.checkId(id);
        tagRepository.delete(id);
    }

    @Override
    public TagPOJO create(TagPOJO tag) {
        tagValidator.isCorrectTag(tag);

        return new TagPOJO(tagRepository.create(converter.convert(tag)));
    }

    @Override
    public int getTagCount() {
        return tagRepository.getTagCount();
    }
}
