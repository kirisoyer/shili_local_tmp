package com.example.ordermanager.dao;

import com.example.ordermanager.entity.BbsPost;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BbsPostMapper {
    // 按ID查帖子
    @Select("SELECT * FROM bbs_post WHERE id = #{id}")
    BbsPost findPostById(Long id);

    // 分页+条件查询帖子（按板块、标题模糊）
    List<BbsPost> find(@Param("offset") long offset,
                       @Param("rows") int rows,
                       @Param("boardId") Long boardId,
                       @Param("title") String title);

    // 统计帖子总数（配合分页）
    int count(@Param("boardId") Long boardId, @Param("title") String title);

    // 新增帖子
    @Insert("INSERT INTO bbs_post(title, content, user_id, board_id, view_count) " +
            "VALUES(#{title}, #{content}, #{userId}, #{boardId}, #{viewCount})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertPost(BbsPost post);

    // 更新帖子
    @Update("UPDATE bbs_post SET title=#{title}, content=#{content}, board_id=#{boardId}, update_time=NOW() " +
            "WHERE id=#{id}")
    int updatePost(BbsPost post);

    // 删除帖子
    @Delete("DELETE FROM bbs_post WHERE id=#{id}")
    int deletePost(Long id);

    // 增加浏览量
    @Update("UPDATE bbs_post SET view_count = view_count + 1 WHERE id=#{id}")
    int incrViewCount(Long id);
}