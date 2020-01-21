package com.tensquare.base.controll;

import com.tensquare.base.po.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author bruce
 * @date 2020/1/12 0012 - 下午 8:06
 */
@RestController
@RequestMapping("/label")
@CrossOrigin //允许跨域
@RefreshScope
public class LabelController {
    @Autowired
    private LabelService labelService;
    @Value("${customkey}")
    private String customkey;


    @PostMapping
    public Result add(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");

    }

    @PutMapping("/{id}")
    public Result edit(@RequestBody Label label,@PathVariable String id){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        labelService.deleteLabelById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
//    @GetMapping("/{id}")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        //int i = 1/0;
        return new Result(true,StatusCode.OK,"查询成功",labelService.findById(id));
    }
    @GetMapping
    public Result findAll(){
//        String authorization = request.getHeader("Authorization");
//        System.out.println(authorization);
        System.out.println("配置文件中自定义的字段customkey："+customkey);
        List<Label> labels = labelService.findAll();
        return new Result(true,StatusCode.OK,"查询所有",labels);
    }
    @PostMapping("/search")
    public Result findByCondition(@RequestBody Label label){
        List<Label> labels = labelService.findByCondition(label);
        return new Result(true,StatusCode.OK,"条件查询成功",labels);

    }
    @PostMapping("/search/{page}/{size}")
    public Result findByCondition(@RequestBody Label label,@PathVariable Integer page,@PathVariable Integer size){
        Page<Label> pages = labelService.findByConditionPage(label,page,size);
        return new Result(true,StatusCode.OK,"分页条件查询成功",new PageResult<Label>(pages.getTotalElements(),pages.getContent()));

    }

}
