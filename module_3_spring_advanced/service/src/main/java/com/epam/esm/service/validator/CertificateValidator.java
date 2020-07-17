package com.epam.esm.service.validator;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.constant.ErrorTextMessageConstants;
import com.epam.esm.pojo.CertificatePOJO;
import com.epam.esm.pojo.InvalidDataMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CertificateValidator { //fixme () перенести валидацию в Controller для не спецефичных моментов.

    private List<InvalidDataMessage> invalidDataMessageList;

    public CertificateValidator() {
    }

    private void nameLength(String name) {
        final String FIELD = "name";
        final int name_max_length = 60;
        final int name_min_length = 3;

        if (name.length() > name_max_length || name.length() < name_min_length) {
            invalidDataMessageList.add(new InvalidDataMessage(FIELD,
                    ErrorTextMessageConstants.NAME_FIELD));
        }
    }

    private void priceCheck(double price) {
        final String FIELD = "price";
        final int price_min_value = 0;

        if (price < price_min_value) {
            invalidDataMessageList.add(new InvalidDataMessage(FIELD,
                    ErrorTextMessageConstants.PRICE_FIELD));
        }
    }

    private void isCorrectModificationDate(Date certificateData) {
        if (certificateData != null) {
            final String FIELD = "modification date"; //fixme constantы выносить
            invalidDataMessageList.add(new InvalidDataMessage(FIELD,
                    ErrorTextMessageConstants.MODIFICATION_DATE_FIELD));
        }
    }

    private void isCorrectCreationDate(Date certificateData) {
        final String FIELD = "creation date";

        if (certificateData != null) {
            invalidDataMessageList.add(new InvalidDataMessage(FIELD,
                    ErrorTextMessageConstants.CREATION_DATE_FIELD));
        }
    }

    public void isCorrectCertificateUpdateData(CertificatePOJO certificate) {
        invalidDataMessageList = new ArrayList<>();
        nameLength(certificate.getName());
        priceCheck(certificate.getPrice());
        isCorrectModificationDate(certificate.getModification());
        if (!invalidDataMessageList.isEmpty()) {
            throw new ServiceException(invalidDataMessageList);
        }
    }

    public void isCorrectCertificateCreateData(CertificatePOJO certificate) {//fixme is - подразумевает true or false
        invalidDataMessageList = new ArrayList<>();//fixme многопоточка
        nameLength(certificate.getName());
        priceCheck(certificate.getPrice());
        isCorrectCreationDate(certificate.getCreationDate());
        isCorrectModificationDate(certificate.getModification());
        if (!invalidDataMessageList.isEmpty()) {
            throw new ServiceException(invalidDataMessageList);
        }
    }
}
