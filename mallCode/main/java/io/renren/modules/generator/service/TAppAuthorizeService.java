package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.TAppAuthorizeEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-04 16:37:25
 */
public interface TAppAuthorizeService extends IService<TAppAuthorizeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

