package com.struggle.excel.mapper;

import com.struggle.excel.model.ElTableField;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ElTableFieldMapper {
    List<ElTableField> listPage(ElTableField elTableField, RowBounds rowBounds);

    List<ElTableField> listLikePage(ElTableField elTableField, RowBounds rowBounds);

    List<ElTableField> list(ElTableField ElTableField);

    ElTableField getById(Long id);

    ElTableField getOne(ElTableField elTableField);

    Long count(ElTableField ElTableField);

    Long countLike(ElTableField ElTableField);

    void save(ElTableField ElTableField);

    void saveBatch(List<ElTableField> list);

    void update(ElTableField ElTableField);

    void updateBatch(List<ElTableField> list);

    void delete(Long id);
}