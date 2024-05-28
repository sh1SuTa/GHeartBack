package top.putileaf.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import top.putileaf.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {

//    新增
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values (#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);


    //展示自己的文章
    List<Article> list(Integer userId, Integer categoryId, String state);

    //展示所有人的文章
    List<Article> listAll(Integer categoryId,String state);

    //修改
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id=#{id}")
    void update(Article article);
}
