package com.struggle.excel.mapper;

import com.struggle.excel.model.ElField;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ElFieldMapper {
    List<ElField> listPage(ElField elField, RowBounds rowBounds);

    List<ElField> listLikePage(ElField elField, RowBounds rowBounds);

    List<ElField> list(ElField ElField);

    ElField getById(Long id);

    ElField getOne(ElField elField);

    Long count(ElField ElField);

    Long countLike(ElField ElField);

    void save(ElField ElField);

    void saveBatch(List<ElField> list);

    void update(ElField ElField);

    void updateBatch(List<ElField> list);

    void delete(Long id);
}