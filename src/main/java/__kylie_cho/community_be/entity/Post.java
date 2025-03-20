package __kylie_cho.community_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("user-posts")
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference("post-comments")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference("post-hearts")
    private List<Heart> hearts;

    @Column(nullable = false, length = 30)
    private String title;
    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int heartCount;
    @Column(nullable = false)
    private int viewCount;
    @Column(nullable = false)
    private int commentCount;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(length = 100)
    private String image;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 조회수 업데이트 메소드
    public void increaseViewCount() {
        this.viewCount++;
    }

    // 좋아요 수 업데이트 메소드
    public void incrementHeartCount() {
        this.heartCount++;
    }

    public void decrementHeartCount() {
        this.heartCount--;
    }

    // 댓글 수 업데이트 메소드
    public void incrementCommentCount() {
        this.commentCount++;
    }

    public void decrementCommentCount() {
        this.commentCount--;
    }
}
