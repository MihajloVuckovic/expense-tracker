/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.demo.expense_tracker.model.Post;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class PostService {

    private final WebClient webClient;

    public PostService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Page<Post> getAllPosts(Pageable pageable) {
        String url = "http://localhost:1337/api/posts";
            Mono<Response> responseMono = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(Response.class);

            Response response = responseMono.block(); 
            if (response != null && response.getData() != null) {
                List<Post> posts = Arrays.asList(response.getData());
                return new PageImpl<>(posts, pageable , posts.size());
            }

        return Page.empty();
    }

    @Getter
    @Setter
    private static class Response {
        private Post[] data;
    }
}
