/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${STRAPI_API}")
    private String apiToken;
    @Value("${STRAPI_URL}")
    String url;
    private final WebClient webClient;

    public PostService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Page<Post> getAllPosts(Pageable pageable) {


            Mono<Response> responseMono = webClient.get()
                    .uri(url)
                    .headers(headers -> headers.setBearerAuth(apiToken))
                    .retrieve()
                    .bodyToMono(Response.class);

            Response response = responseMono.block(); 
            if (response != null && response.getData() != null) {
                List<Post> posts = response.getData();
                return new PageImpl<>(posts, pageable , posts.size());
            }

        return Page.empty();
    }

    public Post getOnePost(Long  id){
        Mono<SingleResponse> responseMono = webClient.get()
            .uri("http://localhost:1337/api/posts/{id}", id)
            .headers(headers -> headers.setBearerAuth(apiToken))
            .retrieve()
            .bodyToMono(SingleResponse.class);
            SingleResponse response =  responseMono.block();
            Post post = response.getData();
            return post;
    }

    @Getter
    @Setter
    private static class Response {
        private List<Post> data;
    }

    @Getter
    @Setter
    private static class SingleResponse{
        private Post data;
    }
}
