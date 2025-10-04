package org.myproject.bmanager5.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.myproject.bmanager5.dto.tmpdto.CategoryTmpDTO;
import org.myproject.bmanager5.service.AppDAO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryDAO {
    private final AppDAO appDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @SneakyThrows
    public List<CategoryTmpDTO> findAllWithPath(Long rootId, String pageable) {
        final String queryStr = appDAO.getSQL(
                "sql/category/select-children.sql",
                pageable
        );

        @SuppressWarnings({"unchecked", "deprecation"})
        NativeQuery<CategoryTmpDTO> query = entityManager.createNativeQuery(queryStr)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setParameter("rootId", rootId)
                .setResultTransformer(Transformers.aliasToBean(CategoryTmpDTO.class));

        return query.getResultList();
    }
}
