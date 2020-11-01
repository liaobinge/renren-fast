package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.TAppSmsEntity;
import io.renren.modules.generator.service.TAppSmsService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-04 16:37:25
 */
@RestController
@RequestMapping("generator/tappsms")
public class TAppSmsController {
    @Autowired
    private TAppSmsService tAppSmsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:tappsms:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tAppSmsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{smsId}")
    @RequiresPermissions("generator:tappsms:info")
    public R info(@PathVariable("smsId") Long smsId){
		TAppSmsEntity tAppSms = tAppSmsService.getById(smsId);

        return R.ok().put("tAppSms", tAppSms);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:tappsms:save")
    public R save(@RequestBody TAppSmsEntity tAppSms){
		tAppSmsService.save(tAppSms);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:tappsms:update")
    public R update(@RequestBody TAppSmsEntity tAppSms){
		tAppSmsService.updateById(tAppSms);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:tappsms:delete")
    public R delete(@RequestBody Long[] smsIds){
		tAppSmsService.removeByIds(Arrays.asList(smsIds));

        return R.ok();
    }

}
