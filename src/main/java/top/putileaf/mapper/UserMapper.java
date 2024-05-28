package top.putileaf.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.putileaf.pojo.User;

@Mapper
public interface UserMapper {

    //查询用户是否会员


    //根据username查找用户
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    //根据id查询用户
    @Select("select * from user where id = #{id}")
    User findById(Integer id);

    //注册用户
    @Insert("insert into user(username,password,create_time,update_time) " +
            " values (#{username},#{password},now(),now())")
    void add(String username, String password);

    //修改用户
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime}where id = #{id}")
    void update(User user);

    //更新头像
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id} ")
    void updateAvatar(String avatarUrl, Integer id);

    //重置密码
    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);


}