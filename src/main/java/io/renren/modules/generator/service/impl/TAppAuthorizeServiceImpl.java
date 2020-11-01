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
    public TAppAuthorizeEntity queryByUserId(Long userId) {
        return baseMapper.selectOne(new QueryWrapper<TAppAuthorizeEntity>().eq("user_id", userId));
    }
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TAppAuthorizeEntity> page = this.page(
                new Query<TAppAuthorizeEntity>().getPage(params),
                new QueryWrapper<TAppAuthorizeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public TAppAuthorizeEntity queryByAlipayAu(String alipayAu) {
        return baseMapper.selectOne(new QueryWrapper<TAppAuthorizeEntity>().eq("alipay_au", alipayAu));
    }

    @Override
    public TAppAuthorizeEntity queryByQQAu(String qqAu) {
        return baseMapper.selectOne(new QueryWrapper<TAppAuthorizeEntity>().eq("qq_au", qqAu));
    }

    @Override
    public TAppAuthorizeEntity queryByWechatAu(String wechatAu) {
        return baseMapper.selectOne(new QueryWrapper<TAppAuthorizeEntity>().eq("wechat_au", wechatAu));
    }

    @Override
    public TAppAuthorizeEntity queryByWeiboAu(String weiboAu) {
        return baseMapper.selectOne(new QueryWrapper<TAppAuthorizeEntity>().eq("weibo_au", weiboAu));
    }

    @Override
    public TAppAuthorizeEntity queryByOneKeyAu(String oneKeyAu) {
        return baseMapper.selectOne(new QueryWrapper<TAppAuthorizeEntity>().eq("banma_au", oneKeyAu));
    }

}