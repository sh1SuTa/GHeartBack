package top.putileaf.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.putileaf.mapper.ArticleMapper;
import top.putileaf.pojo.Article;
import top.putileaf.pojo.PageBean;
import top.putileaf.service.ArticleService;
import top.putileaf.utils.ThreadLocalUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    //文章管理查询（只查询自己的文章）
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
//        创建pageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询
        Page<Article> objects = PageHelper.startPage(pageNum, pageSize);

        //获取用户id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer) map.get("id");


            //调用mapper
            List<Article> as = articleMapper.list(userId,categoryId,state);
            Page<Article> p = (Page<Article>) as;
            //把数据填充到pageBean对象
            pb.setTotal(p.getTotal());
            pb.setItems(p.getResult());
            return pb;

    }

    //TODO
    //文章阅读查询

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    @Override
    public PageBean<Article> listRead(Integer pageNum, Integer pageSize, Integer categoryId, String searchKeyword) {
        //        创建pageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询
        Page<Article> objects = PageHelper.startPage(pageNum, pageSize);

        //调用mapper
        List<Article> as = articleMapper.listAll(categoryId,searchKeyword);
        Page<Article> p = (Page<Article>) as;
        //把数据填充到pageBean对象
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
