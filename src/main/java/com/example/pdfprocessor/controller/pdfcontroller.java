package com.example.pdfprocessor.controller;

import com.example.pdfprocessor.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/api/converter")  // Update to match frontend API
public class pdfcontroller {  // Fixed class naming convention
    private final PdfService pdfService;

    public pdfcontroller(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/pdf-to-word")
    public ResponseEntity<byte[]> convertPdfToWord(@RequestParam("file") MultipartFile file) {
        ByteArrayOutputStream wordDoc = pdfService.convertPdfToWord(file);
        return generateResponse(wordDoc, "converted.docx");
    }

    @PostMapping("/word-to-pdf")
    public ResponseEntity<byte[]> convertWordToPdf(@RequestParam("file") MultipartFile file) {
        ByteArrayOutputStream pdfDoc = pdfService.convertWordToPdf(file);
        return generateResponse(pdfDoc, "converted.pdf");
    }

    @PostMapping("/pdf-to-image")
    public ResponseEntity<byte[]> convertPdfToImage(@RequestParam("file") MultipartFile file) {
        ByteArrayOutputStream image = pdfService.convertPdfToImage(file);
        return generateResponse(image, "converted.png");
    }

    @PostMapping("/image-to-pdf")
    public ResponseEntity<byte[]> convertImageToPdf(@RequestParam("file") MultipartFile file) {
        ByteArrayOutputStream pdfImage = pdfService.convertImageToPdf(file);
        return generateResponse(pdfImage, "converted.pdf");
    }

    @PostMapping("/image-to-word")
    public ResponseEntity<byte[]> convertImageToWord(@RequestParam("file") MultipartFile file) {
        ByteArrayOutputStream wordDoc = pdfService.convertImageToWord(file);
        return generateResponse(wordDoc, "converted.docx");
    }

    private ResponseEntity<byte[]> generateResponse(ByteArrayOutputStream byteArrayOutputStream, String fileName) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayOutputStream.toByteArray());
    }
}
