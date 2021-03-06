package com.epam.esm.service.validator;

import com.epam.esm.exception.ServiceValidationException;
import com.epam.esm.exception.constant.ErrorTextMessageConstants;
import com.epam.esm.pojo.InvalidDataMessage;
import com.epam.esm.pojo.TagPOJO;
import com.epam.esm.repository.jpa.TagRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagValidator {

    private final TagRepository tagRepository;
    private List<InvalidDataMessage> invalidDataMessageList;

    @Autowired
    public TagValidator(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void isCorrectTag(TagPOJO tag) {
        invalidDataMessageList = new ArrayList<>();
        checkNameUnique(tag.getName());
        if (!invalidDataMessageList.isEmpty()) {
            throw new ServiceValidationException(invalidDataMessageList);
        }
    }

    private void checkNameUnique(String name) {
      if (tagRepository.findByName(name) != null) {
            invalidDataMessageList.add(
                new InvalidDataMessage(ErrorTextMessageConstants.TAG_NAME_FIELD_IS_EXIST));
        }
    }

    public void checkId(Long id) {
        if (id <= 0) {
            throw new ServiceValidationException(
                new InvalidDataMessage(ErrorTextMessageConstants.TAG_ID));
        }
    }
}
