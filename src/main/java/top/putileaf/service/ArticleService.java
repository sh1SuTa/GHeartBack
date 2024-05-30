package top.putileaf.service;

import top.putileaf.pojo.Article;
import top.putileaf.pojo.PageBean;

public interface ArticleService {

    //添加
    void add(Article article);

    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    //修改
    void update(Article article);

    PageBean<Article> listRead(Integer pageNum, Integer pageSize, Integer categoryId, String searchKeyword);
}
