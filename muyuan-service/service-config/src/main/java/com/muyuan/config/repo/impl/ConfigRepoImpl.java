package com.muyuan.config.repo.impl;

import com.muyuan.common.bean.Page;
import com.muyuan.common.mybatis.jdbc.crud.SqlBuilder;
import com.muyuan.config.entity.Config;
import com.muyuan.config.face.dto.ConfigQueryCommand;
import com.muyuan.config.repo.ConfigRepo;
import com.muyuan.config.repo.converter.ConfigConverter;
import com.muyuan.config.repo.dataobject.ConfigDO;
import com.muyuan.config.repo.mapper.ConfigMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.muyuan.config.repo.mapper.ConfigMapper.*;

@Component
@AllArgsConstructor
public class ConfigRepoImpl implements ConfigRepo {

    private ConfigMapper configMapper;

    private ConfigConverter converter;

    @Override
    public Page<Config> select(ConfigQueryCommand command) {
        SqlBuilder sqlBuilder = new SqlBuilder(ConfigDO.class)
                .eq(ID,command.getId())
                .eq(CONFIG_NAME,command.getConfigName())
                .eq(CONFIG_KEY,command.getConfigKey())
                .eq(CONFIG_VALUE,command.getConfigValue())
                .eq(CONFIG_TYPE,command.getConfigType())
                .eq(CREATE_BY,command.getCreateBy())
                .eq(CREATE_TIME,command.getCreateTime())
                .eq(UPDATE_BY,command.getUpdateBy())
                .eq(UPDATE_TIME,command.getUpdateTime())
                .eq(REMARK,command.getRemark())
;

        Page<Config> page = Page.<Config>builder().build();
        if (command.enablePage()) {
            page.setPageSize(command.getPageSize());
            page.setPageNum(command.getPageNum());
            sqlBuilder.page(page);
        }

        List<ConfigDO> list = configMapper.selectList(sqlBuilder.build());

        page.setRows(converter.to(list));

        return page;
    }

    @Override
    public Config selectConfig(Long id) {
        ConfigDO configDO = configMapper.selectOne(new SqlBuilder(ConfigDO.class)
                .eq(ID, id)
                .build());
        return converter.to(configDO);
    }

    @Override
    public Config selectConfig(Config.Identify identify) {
        ConfigDO configDO = configMapper.selectOne(new SqlBuilder(ConfigDO.class).select(CONFIG_ID)
                .eq(CONFIG_ID, identify.getId())
                .build());

        return converter.to(configDO);
    }

    @Override
    public boolean addConfig(Config config) {
        ConfigDO to = converter.to(config);
        Integer count = configMapper.insertAuto(to);
        return count > 0;
    }

    @Override
    public Config updateConfig(Config config) {
        SqlBuilder sqlBuilder = new SqlBuilder(ConfigDO.class)
                .eq(ID, config.getId());

        ConfigDO configDO = configMapper.selectOne(sqlBuilder.build());
        if (ObjectUtils.isNotEmpty(configDO)) {
            configMapper.updateBy(converter.to(config), ID);
        }

        return converter.to(configDO);
    }

    @Override
    public List<Config> deleteBy(Integer... ids) {
        List<ConfigDO> configs = configMapper.selectList(new SqlBuilder(ConfigDO.class)
                .in(ID, ids)
                .build());

        configMapper.deleteBy(new SqlBuilder().in(ID, ids).build());

        return converter.to(configs);
    }

}
