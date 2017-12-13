package com.llvision.security.plugin.service;

import com.llvision.security.domain.SystemConfig;
import com.llvision.security.repository.SystemConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * 系统配置的工具服务类
 * Created by llvision on 17/11/15.
 */
@Service
public class SystemConfigUtil {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    private HashMap<String, String> cache = new HashMap<>();

    public Optional<String> getValue(String key) {
        if (cache.containsKey(key)) {
            return Optional.ofNullable(cache.get(key));
        }
        SystemConfig config = systemConfigRepository.findOneByKey(key);
        return Optional.ofNullable(config == null ? null : config.getValue());
    }

    public void putValue(String key, String value) {
        SystemConfig config = systemConfigRepository.findOneByKey(key);
        if (config == null) {
            config = new SystemConfig();
            config.setKey(key);
        }
        config.setValue(value);
        systemConfigRepository.save(config);
        cache.put(key, value);
    }

    public Optional<List<Integer>> getIntArrayValue(String key) {
        // TODO(wangag): 获取逗号分割的整数列表
        return Optional.ofNullable(null);
    }

}
