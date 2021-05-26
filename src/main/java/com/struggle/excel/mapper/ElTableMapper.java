package com.struggle.excel.mapper;

import com.struggle.excel.model.ElTable;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ElTableMapper {
    List<ElTable> listPage(ElTable elTable, RowBounds rowBounds);

    List<ElTable> listLikePage(ElTable elTable, RowBounds rowBounds);

    List<ElTable> list(ElTable ElTable);

    ElTable getById(Long id);

    ElTable getOne(ElTable elTable);

    Long count(ElTable ElTable);

    Long countLike(ElTable ElTable);

    void save(ElTable ElTable);

    void saveBatch(List<ElTable> list);

    void update(ElTable ElTable);

    void updateBatch(List<ElTable> list);

    void delete(Long id);
}