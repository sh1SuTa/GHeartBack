package top.putileaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.putileaf.pojo.Article;
import top.putileaf.pojo.PageBean;
import top.putileaf.pojo.Result;
import top.putileaf.service.ArticleService;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.successT("添加成功");
    }



    @GetMapping
    public Result<PageBean<Article>> list(
            //@RequestParam 注解用于显式指定请求参数的名称以及是否为必需参数
    Integer pageNum,Integer pageSize,@RequestParam(required = false) Integer categoryId, @RequestParam(required = false)String state
    ){
        PageBean<Article> pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    //展示所有已发布文章
    @GetMapping("/read")
    public Result<PageBean<Article>> listRead(
            Integer pageNum,Integer pageSize,@RequestParam(required = false) Integer categoryId, @RequestParam(required = false)String searchKeyword
    ){
        PageBean<Article> pb = articleService.listRead(pageNum,pageSize,categoryId,searchKeyword);
        return Result.success(pb);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Article article){
        articleService.update(article);
        return Result.successT("修改成功");
    }



}
