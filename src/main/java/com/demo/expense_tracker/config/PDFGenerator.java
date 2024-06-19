/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.demo.expense_tracker.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author mihajlo.vuckovic
 */
public class PDFGenerator {
    public static <T> void generatePdfToFile(Iterable<T> entities, Class<T> entityType, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);

                for (T entity : entities) {
                    Field[] fields = entityType.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        contentStream.showText(field.getName() + ": " + value);
                        contentStream.newLineAtOffset(0, -15);
                    }
                    contentStream.newLineAtOffset(0, -15);
                }

                contentStream.endText();
            }

            document.save(filePath);
            document.close();
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public static <T> ByteArrayOutputStream generatePdf(Iterable<T> entities, Class<T> entityType) {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);

                for (T entity : entities) {
                    Field[] fields = entityType.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        contentStream.showText(field.getName() + ": " + value);
                        contentStream.newLineAtOffset(0, -15);
                    }
                    contentStream.newLineAtOffset(0, -15);
                }

                contentStream.endText();
            }

            document.save(out);
            document.close();
            return out;
        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
