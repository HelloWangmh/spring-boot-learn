package wang.mh.dao;

import org.apache.ibatis.annotations.*;
import wang.mh.pojo.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO USER(NAME, AGE) VALUES(#{name}, #{age})")
    int insert(@Param("name") String name, @Param("age") Integer age);

    @Results({@Result(property = "name", column = "name")})
    @Select("SELECT name FROM USER")
    List<User> selectOnlyName();
}
