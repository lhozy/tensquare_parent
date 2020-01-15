package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;

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
}
