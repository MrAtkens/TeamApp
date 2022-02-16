package com.example.teamapp.controllers;


import com.example.teamapp.dto.PostCommentDto;
import com.example.teamapp.dto.PostDto;
import com.example.teamapp.models.Auth.UserEntity;
import com.example.teamapp.models.PostEntity;
import com.example.teamapp.models.VisibilityEnum;
import com.example.teamapp.services.PostCommentService;
import com.example.teamapp.services.PostService;
import com.example.teamapp.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@Log4j2
public class AccountPageController {

    private final UserService userService;
    private final PostService postService;
    private final PostCommentService commentService;

    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public AccountPageController(UserService userService, PostService postService, PostCommentService commentService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }


    @GetMapping("/")
    public String index(Map<String, Object> model, Principal principal) {
        VisibilityEnum visibilityEnum = null;
        if (principal == null) {
            visibilityEnum = VisibilityEnum.VISIBLE_TO_ALL;
        } else {
            visibilityEnum = VisibilityEnum.VISIBLE_TO_USERS;
        }
        model.put("posts", postService.findPosts(visibilityEnum));
        return "index";
    }

@GetMapping("/add-post")
    public String addPost(){
        return "add-post";
    }

    @PreAuthorize("hasRole('USERS')")
    @GetMapping("/users")
    public String users(Map<String, Object> model, Principal principal){
        UserEntity user = userService.findUserByUsername(principal.getName());
        model.put("friends", user.getFriends());
        return "users";
    }

    @GetMapping("/profile")
    public String profile(Principal principal) {
        return "redirect:/user/" + principal.getName();
    }

//    @PostAuthorize()
    @GetMapping("/user/{username}")
    public String user(HttpServletRequest request, @PathVariable String username, Map<String, Object> model, Principal principal) {
        UserEntity user = userService.findUserByUsername(username);
        if (user == null) {
            return "error/404";
        }

        boolean isPageOwner = userService.isPageOwner(username, principal);
        model.put("user", user);
        model.put("__SELF", request.getRequestURI()); // to redirect user to current page after creating a comment
        if (isPageOwner) {
            Collection<PostEntity> posts = postService.findAllPostsOf(username);
            model.put("posts", posts);
            return "blog_my";
        }
        else {
            VisibilityEnum accessLevel = userService.determineAccessLevel(username, principal);
            if (user.getPageVisibility().ordinal() >= accessLevel.ordinal()) {
                Collection<PostEntity> posts = postService.findPosts(username, principal);
                model.put("posts", posts);
                model.put("canSeePosts", true);
            } else {
                model.put("canSeePosts", false);
                System.out.println("access level: " + accessLevel + " pageVisibility: " + user.getPageVisibility());
            }
            return "blog";
        }
    }

    @PreAuthorize("hasRole('USERS')")
    @PostMapping("/findUsers")
    public String findUsers(@RequestParam String username, Map<String, Object> model){
        List<UserEntity> users = userService.findUsers(username);
        model.put("users", users);
        return "index";
    }


    @PreAuthorize("hasRole('USERS')")
    @PostMapping("/create-post")
    public String createPost(PostDto postDto, Principal principal) {
        System.out.println("" + principal.getName());
        System.out.println(postDto.getPostTitle() + "\n" + postDto.getPostText() + "\nVisibility: " +postDto.getPostVisibility() + "\nComments: " + postDto.isCommentsEnabled());
        try {
            postService.savePost(postDto, principal.getName());
        } catch (Exception e) {
            return "error/403";
        }
        return "redirect:/user/" + principal.getName();
    }

    @PreAuthorize("hasRole('USERS')")
    @GetMapping("/post-set-comments")
    public String setCommentsEnabled(@RequestParam UUID postId, @RequestParam(value = "commentsEnabled") boolean commentsEnabled, Principal principal) {
        System.out.println("setCommentsEnabled: " + postId + " " + commentsEnabled);
        try {
            postService.setCommentsEnabled(postId, commentsEnabled, principal);
        } catch (Exception e) {
            return "error/403";
        }
        return "redirect:/user/" + principal.getName();
    }

    @PreAuthorize("#username == authentication.principal.username")
    @PostMapping("/set-page-visibility")
    public String setPageVisibility(String username, VisibilityEnum pageVisibility) {
        userService.setPageVisibility(username, pageVisibility);
        return "redirect:/user/" + username;
    }


    @PreAuthorize("hasRole('USERS')")
    @PostMapping("/create-comment")
    public String createComment(PostCommentDto postCommentDto, String continueTo, Principal principal) {
        commentService.saveComment(postCommentDto, userService.findUserByUsername(principal.getName()));
        System.out.println("AccountPageController.createComment: Redirecting to " + continueTo);
        return "redirect:" + continueTo;
    }

    public static void infoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Date: " + dtf.format(LocalDateTime.now())
                + "\n Request "
                + "\n Headers: "  + request.getHeaderNames()
                + "\n Parameters names: "  + request.getParameterNames()
                + "\n Parameters: "  + request.getParameterMap()
                + "\n Attributes: " + request.getAttributeNames()
                + "\n ContentType: " + request.getContentType()
                + "\n ContentLength: " + request.getContentLength()
                + " Response "
                + "\n Headers: " + response.getHeaderNames()
                + "\n ContentType: " + response.getContentType()
                + "\n Content: " + response.getWriter().toString());
    }
}