package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.TAppAuthorizeDao;
import io.renren.modules.generator.entity.TAppAuthorizeEntity;
import io.renren.modules.generator.service.TAppAuthorizeService;


@Service("tAppAuthorizeService")
public class TAppAuthorizeServiceImpl extends ServiceImpl<TAppAuthorizeDao, TAppAuthorizeEntity> implements TAppAuthorizeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TAppAuthorizeEntity> page = this.page(
                new Query<TAppAuthorizeEntity>().getPage(params),
                new QueryWrapper<TAppAuthorizeEntity>()
        );

        return new PageUtils(page);
    }

}