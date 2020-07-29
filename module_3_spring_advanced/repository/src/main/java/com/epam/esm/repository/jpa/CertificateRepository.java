package com.epam.esm.repository.jpa;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;

public interface CertificateRepository {
    void delete(long id);

    Certificate findById(long id);

    int getCertificateCount();

    @Deprecated
    List<Certificate> findAll();

    List<Certificate> findAll(int offset, int limit);

    List<Certificate> findAllComplex(String query, Map<String, Object> params, int offset, int limit);

    int findCountComplex(String query, Map<String, Object> params);

    Certificate create(Certificate certificate);

    List<Certificate> findAllByDate(int page, int size);

    @Deprecated
    List<Certificate> findAllByIdThreshold(long id);

    @Deprecated
    List<Certificate> findAllByNamePart(String namePart);

    List<Certificate> findAllByTags(String Query, int offset, int limit);

    void update(Certificate certificate, long id);

    void updatePrice(long id, double price);

    List<Certificate> findByTagName(String name, int page, int size);

    void addTag(long idCertificate, long idTag);

    void deleteTag(long idCertificate, Tag tag);
}
