package top.putileaf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.putileaf.mapper.CategoryMapper;
import top.putileaf.pojo.Category;
import top.putileaf.service.CategoryService;
import top.putileaf.utils.ThreadLocalUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void add(Category category) {
        //设置创建时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        //获取用户id作为创建者
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        //传给mapper
        categoryMapper.add(category);
    }

    //查询所有分类
    @Override
    public List<Category> list() {
        //获取当前登录的id
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        //获取是否成员身份
        Integer vip = (Integer) map.get("vip");
        if (vip == 1){
            List<Category> list = categoryMapper.listAll();
        //是会员显示所有节点
            return list;
        }else {
        //传给mapper
        List<Category> list = categoryMapper.list(userId);
        return list;}
    }

    @Override
    public Category findById(Integer id) {
        Category c = categoryMapper.findById(id);
        return c;
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }







}
