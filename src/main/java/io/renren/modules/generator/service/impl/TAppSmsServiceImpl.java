package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.TAppSmsDao;
import io.renren.modules.generator.entity.TAppSmsEntity;
import io.renren.modules.generator.service.TAppSmsService;


@Service("tAppSmsService")
public class TAppSmsServiceImpl extends ServiceImpl<TAppSmsDao, TAppSmsEntity> implements TAppSmsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TAppSmsEntity> page = this.page(
                new Query<TAppSmsEntity>().getPage(params),
                new QueryWrapper<TAppSmsEntity>()
        );

        return new PageUtils(page);
    }
    @Override
    public TAppSmsEntity queryByMobile(String mobile) {
      // return  baseMapper.selectOne(new QueryWrapper<TAppSmsEntity>().eq("mobile", mobile).orderByDesc("send_time"));
        List<TAppSmsEntity> smsList= baseMapper.selectList(new QueryWrapper<TAppSmsEntity>().eq("mobile", mobile).orderByDesc("send_time"));
        if(smsList!=null&&smsList.size()>0){
            return smsList.get(0);
        }else{
            return  null;
        }

    }

}