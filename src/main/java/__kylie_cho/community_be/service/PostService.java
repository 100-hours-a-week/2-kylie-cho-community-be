package __kylie_cho.community_be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getPosts() {
        // 간단한 SQL 쿼리 실행
        String sql = "SELECT post_title FROM Post";
        List<String> posts = jdbcTemplate.queryForList(sql, String.class);

        return posts.isEmpty() ? List.of("게시글 없어요") : posts;
    }
}
