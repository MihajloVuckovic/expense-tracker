/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.expense_tracker.model.Post;
import com.demo.expense_tracker.services.PostService;



/**
 *
 * @author mihajlo.vuckovic
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService=postService;
    }
    
    @GetMapping("")
    @Secured("ROLE_PREMIUM")
    public Page<Post> getPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue="id") String sortBy,
    @RequestParam(defaultValue="asc") String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{id}")
    @Secured("ROLE_PREMIUM")
    public Post getOnePost(@PathVariable Long id){
        return postService.getOnePost(id);
    }
    
}
