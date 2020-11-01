package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.TAppUserDao;
import io.renren.modules.generator.entity.TAppUserEntity;
import io.renren.modules.generator.service.TAppUserService;


@Service("tAppUserService")
public class TAppUserServiceImpl extends ServiceImpl<TAppUserDao, TAppUserEntity> implements TAppUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TAppUserEntity> page = this.page(
                new Query<TAppUserEntity>().getPage(params),
                new QueryWrapper<TAppUserEntity>()
        );

        return new PageUtils(page);
    }

}