package __kylie_cho.community_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false, length = 20)
    private String nickname;

//    private String profileImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fm.blog.naver.com%2Fgambasg%2F222132751279&psig=AOvVaw0chHekwVUVVVtatmZ20ZEX&ust=1742380007472000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLiF4Mm1k4wDFQAAAAAdAAAAABAE";
    private String profileImage;
}
