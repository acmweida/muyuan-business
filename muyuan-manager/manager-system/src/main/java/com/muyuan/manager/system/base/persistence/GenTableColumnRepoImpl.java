package com.muyuan.manager.system.base.persistence;


import com.muyuan.manager.system.base.persistence.mapper.GenTableColumnMapper;
import com.muyuan.manager.system.model.GenTableColumn;
import com.muyuan.manager.system.repo.GenTableColumnRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName GenTableColumnRepoImpl
 * Description GenTableColumnRepoImpl Repo
 * @Author 2456910384
 * @Date 2022/6/1 16:32
 * @Version 1.0
 */
@Component
@AllArgsConstructor
public class GenTableColumnRepoImpl implements GenTableColumnRepo {

    private GenTableColumnMapper genTableColumnMapper;


    @Override
    public void insert(GenTableColumn genTableColumn) {
        genTableColumnMapper.insert(genTableColumn);
    }

    @Override
    public List<GenTableColumn> selectDbTableColumnsByName(String tableName) {
        return genTableColumnMapper.selectDbTableColumnsByName(tableName);
    }

    @Override
    public int updateGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.updateById(genTableColumn);
    }

    @Override
    public int deleteGenTableColumnByIds(Long[] ids) {
        return genTableColumnMapper.deleteBatchIds(
                Arrays.asList(ids)
        );
    }

    @Override
    public int insertGenTableColumn(GenTableColumn genTableColumn) {
        return genTableColumnMapper.insertGenTableColumn(genTableColumn);
    }

    @Override
    public int deleteGenTableColumns(List<GenTableColumn> genTableColumns) {
        return genTableColumnMapper.deleteGenTableColumns(genTableColumns);
    }
}
