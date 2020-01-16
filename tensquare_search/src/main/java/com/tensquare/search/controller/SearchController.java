package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.SearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;


@RestController
@RequestMapping("/article")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/{key}/{page}/{size}")
    public Result findArticleList(@PathVariable("key") String key, @PathVariable("page") int page,
                                  @PathVariable("size") int size){

        Page<Article> articles = searchService.findByKey(key, page, size);
        return new Result(true, StatusCode.OK,"查询成功",
                                new PageResult<Article>(articles.getTotalElements(),articles.getContent()));

    }
    @PostMapping
    public Result save(@RequestBody Article article){
        searchService.save(article);
        return new Result(true,StatusCode.OK,"添加成功");
    }
}
