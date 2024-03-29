package com.muyuan.config.service.impl;

import com.muyuan.common.bean.Page;
import com.muyuan.common.core.constant.GlobalConst;
import com.muyuan.config.entity.Config;
import com.muyuan.config.face.dto.ConfigCommand;
import com.muyuan.config.face.dto.ConfigQueryCommand;
import com.muyuan.config.repo.ConfigRepo;
import com.muyuan.config.service.ConfigService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author wd
 * @ClassName ConfigDomainServiceImpl
 * Description 权限
 * @date 2022-11-30T10:41:23.089+08:00
 * @Version 1.0
 */
@Service
@AllArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    private ConfigRepo configRepo;

    @Override
    public Page<Config> list(ConfigQueryCommand command) {
        return configRepo.select(command);
    }

    @Override
    public boolean exists(Config.Identify identify) {
        Long id = null == identify.getId() ? null : identify.getId();
        Config config = configRepo.selectConfig(identify);
        if (null != config && !id.equals(config.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addConfig(ConfigCommand command) {
        Config config = new Config();

        config.setName(command.getName());
        config.setConfigKey(command.getConfigKey());
        config.setConfigValue(command.getConfigValue());
        config.setType(command.getType());

        config.setRemark(command.getRemark());
        config.setCreator(command.getOpt().getId());
        config.setCreateBy(command.getOpt().getName());
        config.setCreateTime(DateTime.now().toDate());

        return configRepo.addConfig(config);
    }

    @Override
    public Optional<Config> getConfig(Long id) {
        return Optional.of(id)
                .map(id_ -> {
                    return configRepo.selectConfig(id_);
                });
    }

    @Override
    public boolean updateConfig(ConfigCommand command) {
        Config config = new Config();

        config.setId(command.getId());
        config.setName(command.getName());
        config.setConfigKey(command.getConfigKey());
        config.setConfigValue(command.getConfigValue());
        config.setType(command.getType());

        config.setRemark(command.getRemark());
        config.setUpdater(command.getOpt().getId());
        config.setUpdateBy(command.getOpt().getName());
        config.setUpdateTime(DateTime.now().toDate());

        Config old = configRepo.updateConfig(config);
        if (ObjectUtils.isNotEmpty(old)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteConfigById(Long... ids) {
        if (ObjectUtils.isEmpty(ids)) {
            return false;
        }
        List<Long> removeIds = new ArrayList(Arrays.asList(ids));

        List<Config> olds = configRepo.deleteBy(removeIds.toArray(new Long[0]));

        return !olds.isEmpty();
    }


}
