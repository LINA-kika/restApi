package ru.rubcon.restApi.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.DocType;
import ru.rubcon.restApi.models.Document;
import ru.rubcon.restApi.repos.ConstructionRepository;
import ru.rubcon.restApi.repos.DocumentRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ConstructionRepository constructionRepository;

    public DocumentService(DocumentRepository documentRepository, ConstructionRepository constructionRepository) {
        this.documentRepository = documentRepository;
        this.constructionRepository = constructionRepository;
    }

    public Page<Document> findByTypeOrderByUploadDate(DocType type, Pageable pageable) {
        Page<Document> result = documentRepository.findByType(type, pageable);
        if (result == null) {
            log.warn("IN findByTypeOrderByUploadDate - no documents of type {} found", type);
            return null;
        }

        log.info("IN findByTypeOrderByUploadDate - documents: {} found by type {}", result, type);
        return result;
    }

    public Page<Document> findByDocName(String docName, Long constId, Pageable pageable) {
        Page<Document> result = documentRepository.findByDocNameStartingWithIgnoreCaseAndConstructionId( docName, constId, pageable);
        if (result == null) {
            log.warn("IN findByDocName - no documents of type {} found", docName);
            return null;
        }

        log.info("IN findByDocName - documents: {} found by type {}", result, docName);
        return result;
    }

    public Page<Document> findByDocNameAndType(DocType type, String docName, Long constId, Pageable pageable) {
        Page<Document> result = documentRepository.findByDocNameStartingWithIgnoreCaseAndConstructionIdAndType(docName, constId,type, pageable);
        if (result == null) {
            log.warn("IN findByDocNameAndType - no documents of type {} and name {} found", docName, type);
            return null;
        }

        log.info("IN findByDocNameAndType - documents: {} found by name {} and type {}", result, docName, type);
        return result;
    }

    public Page<Document> findByTypeOrderByUploadDate(DocType type, Long constId, Pageable pageable) {
        Page<Document> result = documentRepository.findByTypeAndConstructionId(type, constId, pageable);
        if (result == null) {
            log.warn("IN findByTypeOrderByUploadDate - no documents of type {} found", type);
            return null;
        }

        log.info("IN findByTypeOrderByUploadDate - documents: {} found by type {}", result, type);
        return result;
    }

    public Document addToConstructionGetById(Long constId, Document doc) {
        Optional<Construction> construction = constructionRepository.findById(constId);
        doc.setConstruction(construction.get());

        Document result = documentRepository.save(doc);
        System.out.println(result.getConstruction().getId());
        log.info("IN addToConstructionGetById - {} document added to construction {}", result, constId);
        return result;
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
        log.info("IN delete - document with id: {} successfully deleted");
    }

    public List<Document> getAll() {
        List<Document> result = documentRepository.findAll();
        log.info("IN getAll - {} documents found", result.size());
        return result;
    }

    public Document getById(Long id) {
        Document result = documentRepository.findById(id).get();
        if (result == null) {
            log.warn("IN getById - no document found by id: {}", id);
            return null;
        }

        log.info("IN getById - document: {} found by id: {}", result);
        return result;
    }

    public Page<Document> getDocsByConstId(Long constId, Pageable pageable) {
        var construction = constructionRepository.getById(constId);
        Page<Document> result = documentRepository.findByConstruction(construction, pageable);
        if (result == null) {
            log.warn("IN getDocsByConstId - no documents found for construction with id: {}", constId);
            return null;
        }

        log.info("IN getDocsByConstId - document: {} found by construction with id: {}", result);
        return result;
    }


    public void update(Document document) {
        Document result = documentRepository.save(document);
        log.info("IN update - document: {} successfully updated", result);
    }
}
