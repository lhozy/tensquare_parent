package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据上级ID查询吐槽分页数据
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result listPageByParentid(@PathVariable String parentid , @PathVariable int page, @PathVariable int size){
        Page<Spit> pageResponse = spitService.findSpitListPageByParentid(parentid, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Spit>(pageResponse.getTotalElements(), pageResponse.getContent()) );
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }
    @GetMapping
    public Result getList(){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findAll());
    }
    @PostMapping
    public Result add(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"添加成功");
    }
    @PutMapping("/{id}")
    public Result edit(@RequestBody Spit spit,@PathVariable("id") String id){
        spit.setId(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    @PutMapping("/thumbup/{id}")
    public Result thumbup(@PathVariable("id") String id){
        String userid = "111";
        if (redisTemplate.opsForValue().get("thumbup" + userid) != null) {
            return new Result(false,StatusCode.REPERROR,"不能重复点赞");
        }
        spitService.thumbup(id);
        redisTemplate.opsForValue().set("thumbup"+userid,1);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
