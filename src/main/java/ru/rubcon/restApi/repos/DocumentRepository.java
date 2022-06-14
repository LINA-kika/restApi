package ru.rubcon.restApi.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.DocType;
import ru.rubcon.restApi.models.Document;


public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> findByConstruction(Construction construction, Pageable pageable);
    Page<Document> findByType(DocType type, Pageable pageable);
    Page<Document> findByTypeAndConstructionId(DocType type, Long construction_id, Pageable pageable);
    Page<Document> findByDocNameStartingWithIgnoreCaseAndConstructionId ( String docName,Long constId, Pageable pageable);
    Page<Document> findByDocNameStartingWithIgnoreCaseAndConstructionIdAndType ( String docName,Long constId, DocType type, Pageable pageable);
}
