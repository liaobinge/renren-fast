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

import io.renren.modules.generator.entity.TAppUserEntity;
import io.renren.modules.generator.service.TAppUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-04 16:37:24
 */
@RestController
@RequestMapping("generator/tappuser")
public class TAppUserController {
    @Autowired
    private TAppUserService tAppUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:tappuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tAppUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("generator:tappuser:info")
    public R info(@PathVariable("userId") Long userId){
		TAppUserEntity tAppUser = tAppUserService.getById(userId);

        return R.ok().put("tAppUser", tAppUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:tappuser:save")
    public R save(@RequestBody TAppUserEntity tAppUser){
		tAppUserService.save(tAppUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:tappuser:update")
    public R update(@RequestBody TAppUserEntity tAppUser){
		tAppUserService.updateById(tAppUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:tappuser:delete")
    public R delete(@RequestBody Long[] userIds){
		tAppUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
