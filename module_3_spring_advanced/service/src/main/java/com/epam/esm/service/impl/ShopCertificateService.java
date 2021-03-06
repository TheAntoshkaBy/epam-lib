package com.epam.esm.service.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceBadRequestException;
import com.epam.esm.exception.constant.ErrorTextMessageConstants;
import com.epam.esm.pojo.CertificatePOJO;
import com.epam.esm.pojo.InvalidDataMessage;
import com.epam.esm.pojo.TagPOJO;
import com.epam.esm.repository.jpa.CertificateRepository;
import com.epam.esm.repository.jpa.TagRepository;
import com.epam.esm.service.CertificateInternalService;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.impl.handler.CertificateServiceRequestParameterHandler;
import com.epam.esm.service.support.PojoConverter;
import com.epam.esm.service.support.impl.CertificatePojoConverter;
import com.epam.esm.service.validator.CertificateValidator;
import com.epam.esm.service.validator.TagValidator;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ShopCertificateService implements CertificateInternalService, CertificateService {

    private CertificateServiceRequestParameterHandler certificateServiceRequestParameterHandler;
    private final TagValidator tagValidator;
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final PojoConverter<CertificatePOJO, Certificate> converter;
    private final PojoConverter<TagPOJO, Tag> tagConverter;
    private final CertificateValidator certificateValidator;

    @Autowired
    public ShopCertificateService(TagValidator tagValidator,
        CertificateRepository certificateRepository,
        TagRepository tagRepository, CertificatePojoConverter converter,
        PojoConverter<TagPOJO, Tag> tagConverter,
        CertificateValidator certificateValidator) {
        this.tagValidator = tagValidator;
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.converter = converter;
        this.tagConverter = tagConverter;
        this.certificateValidator = certificateValidator;
    }

    @Autowired
    public void setCertificateServiceRequestParameterHandler(
        CertificateServiceRequestParameterHandler certificateServiceRequestParameterHandler) {
        this.certificateServiceRequestParameterHandler = certificateServiceRequestParameterHandler;
    }

    /**
     * @param params Request params for choice selection of certificate display type
     * @return Certificate list
     */
    @Override
    public List<CertificatePOJO> findAll(Map<String, String> params, List<TagPOJO> tags,
                                         int page, int size
    ) {
        return certificateServiceRequestParameterHandler.find(params, tags, page, size);
    }

    public List<CertificatePOJO> findAllComplex(Map<String, String> request, List<TagPOJO> tags,
                                                int page, int size) {
        page = PojoConverter.convertPaginationPageToDbOffsetParameter(page, size);
        Map<String, Object> parametrizedRequest = certificateServiceRequestParameterHandler
            .filterAndSetParams(request);
        String query = certificateServiceRequestParameterHandler.filterAnd(request, tags);

        return converter.convert(certificateRepository
            .findAllComplex(query, parametrizedRequest, --page, size));
    }

    @Override
    public int getCertificatesCount(Map<String, String> request, List<TagPOJO> tags) {
        return certificateServiceRequestParameterHandler.findAllCount(request, tags);
    }

    public int getCountComplex(Map<String, String> request, List<TagPOJO> tags) {
        Map<String, Object> parametrizedRequest = certificateServiceRequestParameterHandler
            .filterAndSetParams(request);
        String query = certificateServiceRequestParameterHandler.filterAndGetCount(request, tags);

        return certificateRepository.findCountComplex(query, parametrizedRequest);
    }

    @Override
    public int findByAllCertificatesByIdThresholdCount(long id) {
        return certificateRepository.findCountAllByIdThreshold(id);
    }

    @Override
    public List<CertificatePOJO> findAll(int page, int size) {
        page = PojoConverter.convertPaginationPageToDbOffsetParameter(page, size);

        return converter.convert(certificateRepository.findAll(--page, size));
    }

    @Override
    public CertificatePOJO find(long id) {
        certificateValidator.checkId(id);
        return new CertificatePOJO(certificateRepository.findById(id));
    }

    @Override
    public List<CertificatePOJO> findAllCertificatesByDate(int page, int size) {
        return converter.convert(certificateRepository.findAllByDate(page, size));
    }

    @Override
    public int getAllCertificateCount() {
        return certificateRepository.getCertificateCount();
    }

    @Deprecated
    @Override
    public List<CertificatePOJO> findAllCertificatesByIdThreshold(long id, int page, int size) {
        return converter.convert(certificateRepository.findAllByIdThreshold(id, --page, size));
    }

    @Deprecated
    @Override
    public List<CertificatePOJO> findAllCertificatesByTag(TagPOJO tag, int page, int size) {
        tagValidator.isCorrectTag(tag);

        return converter.convert(certificateRepository.findByTagName(tag.getName(), page, size));
    }

    @Override
    public void delete(long id) {
        certificateRepository.delete(id);
    }

    @Override
    public void update(long id, CertificatePOJO certificate) {
        certificateValidator.checkId(id);

        certificate.setModification(new Date());
        Certificate updatedCertificate = certificateRepository.findById(id);
        Certificate updateDataCertificate = converter.convert(certificate);
        certificateRepository
            .update(updatedCertificate, updateDataCertificate);
    }

    @Override
    public void updatePath(long id, CertificatePOJO newCertificateData) {
        Certificate certificateUpdateData = converter.convert(newCertificateData);
        Certificate updatedCertificate = certificateRepository.findById(id);
        if(certificateUpdateData.getPrice() != null){
            updatedCertificate.setPrice(certificateUpdateData.getPrice());
        }
        if(certificateUpdateData.getName() != null){
            updatedCertificate.setName(certificateUpdateData.getName());
        }
        if(certificateUpdateData.getDurationDays() != null){
            updatedCertificate.setDurationDays(certificateUpdateData.getDurationDays());
        }
    }

    @Override
    public CertificatePOJO create(CertificatePOJO certificate) {
        certificate.setCreationDate(new Date());

        return new CertificatePOJO(certificateRepository
            .create(converter.convert(certificate)));
    }

    @Override
    public void addTag(long id, TagPOJO tag) {
        tagValidator.isCorrectTag(tag);
        certificateRepository
            .addTag(id, tagRepository.create(tagConverter.convert(tag)).getId());
    }

    @Override
    public void addTag(long idCertificate, long idTag) {
        Tag tag = tagRepository.findById(idTag);
        Certificate certificate = certificateRepository.findById(idCertificate);

        certificateRepository.addTag(certificate,tag);
    }

    @Override
    public void deleteTag(long idCertificate, long idTag) {
        Certificate buffCertificate = certificateRepository.findById(idCertificate);

        if (buffCertificate == null) {
            throw new ServiceBadRequestException(
                new InvalidDataMessage(ErrorTextMessageConstants.NOT_FOUND_CERTIFICATE));
        }

        Optional<Tag> buffTag = buffCertificate
            .getTags()
            .stream()
            .filter(tag -> tag.getId() == idTag).findFirst();
        if (buffTag.isPresent()) {
            certificateRepository.deleteTag(idCertificate, buffTag.get());
        } else {
            throw new ServiceBadRequestException(
                new InvalidDataMessage(ErrorTextMessageConstants.NOT_FOUND_TAG));
        }
    }

    @Override
    public List<CertificatePOJO> findByAllCertificatesByNamePart(String text) {
        String foundedText = "%";
        foundedText += text;
        foundedText += "%";

        return converter.convert(certificateRepository
            .findAllByNamePart(foundedText)
        );
    }
}
