package com.epam.esm.service.Impl.Impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.Impl.CertificateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

@Component
public class CertificateServiceImpl implements CertificateService {

    private final CertificateDAO certificateDAO;

    public CertificateServiceImpl(@Qualifier("certificateDAOJDBCTemplate") CertificateDAO certificateDAO) {
        this.certificateDAO = certificateDAO;
    }

    @Override
    public List<Certificate> findAll(HttpServletRequest params) {
        if(params.getParameter("filter") == null){
            return findAll();
        }

        switch (params.getParameter("filter")){
            case "date": {
               return findAllCertificatesByDate();
            }
            case "By name part": {
                return findByAllCertificatesByNamePart(params.getParameterValues("name")[0]);
            }
            case "more id":{
                return findAllCertificatesWhereIdMoreThenTransmittedId(
                        Integer.parseInt(params.getParameterValues("id")[0])
                );
            }
            default: {
               return findAll();
            }
        }
    }

    @Override
    public List<Certificate> findAll() {
        return certificateDAO.findAll();
    }

    @Override
    public Certificate find(int id) {
        return certificateDAO.findCertificateById(id);
    }

    @Override
    public void delete(int id) {
        certificateDAO.deleteCertificateById(id);
    }

    @Override
    public void update(int id, Certificate certificate) {
        certificateDAO.updateCertificate(id, certificate);
    }

    @Override
    public void create(Certificate certificate) {
        certificateDAO.addCertificate(certificate);
    }

    @Override
    public void addTag(int id, Tag tag) {
        certificateDAO.addTag(id, tag);
    }

    @Override
    public void addTag(int idCertificate, int idTag) {
        certificateDAO.addTag(idCertificate, idTag);
    }

    @Override
    public void deleteTag(int idCertificate, int idTag) {
        certificateDAO.deleteTag(idCertificate, idTag);
    }

    @Override
    public List<Certificate> findByAllCertificatesByNamePart(String text) {
        text+='%';
        return certificateDAO.findCertificateByNamePart(text);
    }

    @Override
    public List<Certificate> findAllCertificatesByDate() {
        return certificateDAO.findAllByDate();
    }

    @Override
    public List<Certificate> findAllCertificatesWhereIdMoreThenTransmittedId(int id) {
        return certificateDAO.findCertificateWhereIdMoreThanParameter(id);
    }

    @Override
    public List<Certificate> findAllCertificatesByTag(Tag tag) {
        return certificateDAO.findCertificateWhereTagNameIs(tag);
    }

}