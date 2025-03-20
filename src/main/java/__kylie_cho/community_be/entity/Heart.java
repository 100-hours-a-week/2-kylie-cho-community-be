package __kylie_cho.community_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference("user-hearts")
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JsonBackReference("post-hearts")
    @JoinColumn(nullable = false)
    private Post post;
}
