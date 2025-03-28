package __kylie_cho.community_be.service;

import __kylie_cho.community_be.dto.PostResponseDto;
import __kylie_cho.community_be.entity.Post;
import __kylie_cho.community_be.entity.User;
import __kylie_cho.community_be.repository.PostRepository;
import __kylie_cho.community_be.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 게시글 목록 조회 (페이지네이션 지원)
    public List<PostResponseDto> getPosts(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return postRepository.findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(post -> new PostResponseDto(post, post.getViewCount(), post.getCommentCount(), post.getHeartCount()))
                .toList();
    }

    // 게시글 상세 조회
    public PostResponseDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // 조회수 증가
        post.increaseViewCount();
        postRepository.save(post);

        return new PostResponseDto(post, post.getViewCount(), post.getCommentCount(), post.getHeartCount());
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long id, String newTitle, String newContent, MultipartFile newImageFile, Long userId) throws IOException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 게시글을 수정할 수 있어요.");
        }

        if (newTitle != null && !newTitle.isEmpty()) {
            post.setTitle(newTitle);
        }
        if (newContent != null && !newContent.isEmpty()) {
            post.setContent(newContent);
        }

        // 이미지 처리
        if (newImageFile != null && !newImageFile.isEmpty()) {
            String folderPath = "uploaded_images/";
            Path path = Paths.get(folderPath + newImageFile.getOriginalFilename());
            Files.createDirectories(path.getParent());
            newImageFile.transferTo(path);
            post.setImage(path.toString());
        }

        Post updatedPost = postRepository.save(post);
        return new PostResponseDto(updatedPost, updatedPost.getViewCount(), updatedPost.getCommentCount(), updatedPost.getHeartCount());
    }

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(String title, String content, Long userId, MultipartFile imageFile) throws IOException {
        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not Found"));

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);

        // 이미지 파일 처리
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            String folderPath = "uploaded_images/";
            Path path = Paths.get(folderPath + imageFile.getOriginalFilename());
            Files.createDirectories(path.getParent());
            imageFile.transferTo(path);
            imageUrl = path.toString();
        }

        post.setImage(imageUrl);

        post.setCommentCount(0);
        post.setHeartCount(0);
        post.setViewCount(0);

        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost, 0, 0, 0);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long id, Long userId) {
        // 게시글 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 게시글을 삭제할 수 있어요.");
        }

        // 이미지 파일 삭제
        String postImage = post.getImage();
        if (postImage != null && !postImage.isEmpty()) {
            Path imagePath = Paths.get(postImage);
            File imageFile = imagePath.toFile();

            // 저장소에 파일이 존재하면 삭제
            if (imageFile.exists()) {
                boolean isDeleted = imageFile.delete();
                if (!isDeleted) {
                    throw new RuntimeException("게시글 이미지 삭제에 실패했어요.");
                }
            }
        }

        postRepository.deleteById(id);
    }
}
