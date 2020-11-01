package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.TAppUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-04 16:37:24
 */
public interface TAppUserService extends IService<TAppUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

