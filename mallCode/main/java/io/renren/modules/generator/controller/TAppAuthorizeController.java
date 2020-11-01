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

import io.renren.modules.generator.entity.TAppAuthorizeEntity;
import io.renren.modules.generator.service.TAppAuthorizeService;
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
@RequestMapping("generator/tappauthorize")
public class TAppAuthorizeController {
    @Autowired
    private TAppAuthorizeService tAppAuthorizeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:tappauthorize:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tAppAuthorizeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{auId}")
    @RequiresPermissions("generator:tappauthorize:info")
    public R info(@PathVariable("auId") Long auId){
		TAppAuthorizeEntity tAppAuthorize = tAppAuthorizeService.getById(auId);

        return R.ok().put("tAppAuthorize", tAppAuthorize);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:tappauthorize:save")
    public R save(@RequestBody TAppAuthorizeEntity tAppAuthorize){
		tAppAuthorizeService.save(tAppAuthorize);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:tappauthorize:update")
    public R update(@RequestBody TAppAuthorizeEntity tAppAuthorize){
		tAppAuthorizeService.updateById(tAppAuthorize);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:tappauthorize:delete")
    public R delete(@RequestBody Long[] auIds){
		tAppAuthorizeService.removeByIds(Arrays.asList(auIds));

        return R.ok();
    }

}
