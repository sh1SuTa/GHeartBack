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

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
//        创建pageBean对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页查询
        Page<Article> objects = PageHelper.startPage(pageNum, pageSize);

        //获取用户id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer) map.get("id");

        //判断用户是否为成员
        Integer vip = (Integer) map.get("vip");
        if (vip ==1 ){
            List<Article> articles = articleMapper.listAll(categoryId, state);
            Page<Article> page = (Page<Article>) articles;
            //把数据填充到pageBean对象
            pb.setTotal(page.getTotal());
            pb.setItems(page.getResult());
            return pb;
        }else {
            //调用mapper
            List<Article> as = articleMapper.list(userId,categoryId,state);
            Page<Article> p = (Page<Article>) as;
            //把数据填充到pageBean对象
            pb.setTotal(p.getTotal());
            pb.setItems(p.getResult());
            return pb;
        }

//TODO
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }
}
