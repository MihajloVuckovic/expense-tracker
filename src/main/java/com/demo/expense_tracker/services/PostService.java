/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.demo.expense_tracker.model.Post;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@Service
public class PostService {

    @Autowired
    private final RestTemplate restTemplate;

    public PostService(RestTemplate restTemplate){
        this.restTemplate=restTemplate;
    }

    public List<Post> getAllPosts() {
        String url = "http://localhost:1337/api/posts";
        try {
            Response response = restTemplate.getForObject(url, Response.class);
            if (response != null && response.getData() != null) {
                return Arrays.asList(response.getData());
            }
            return List.of();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            
            System.err.println("Error fetching posts: " + e.getMessage());
            return List.of();
        }
    }

    @Getter
    @Setter
    private static class Response {
        private Post[] data;
    }
}
