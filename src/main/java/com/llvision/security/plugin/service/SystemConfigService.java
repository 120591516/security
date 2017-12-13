package com.llvision.security.plugin.service;

/**
 * 系统配置接口
 * Created by llvision on 17/11/15.
 */
public interface SystemConfigService {
    /**
     * 初始化系统配置
     * 初次使用的时候创建相关的配置选项
     */
    void initConfig();

    /**
     * 系统配置修改后产生的回调
     * 用于监听一些关键配置信息，修改后重新处理已经保存的信息
     *
     * @param key 修改的键值
     */
    void onValueChanged(String key);
}
