/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mihajlo.vuckovic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attributes {
    private String title;
    private String author;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String publishedAt;
}
