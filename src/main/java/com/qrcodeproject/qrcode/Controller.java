package com.qrcodeproject.qrcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/qrcode")
@CrossOrigin(origins = "*")
public class Controller {

    @Autowired
    private qrCodeService qrCodeService;

    /**
     * Generate QR code as PNG image
     * GET /api/qrcode?text=HelloWorld&size=300
     */
    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQrCode(
            @RequestParam String text,
            @RequestParam(defaultValue = "250") int size) {
        
        try {
            byte[] qrCode = qrCodeService.generateQrCode(text, size, size);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "qrcode.png");
            
            return new ResponseEntity<>(qrCode, headers, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Generate QR code as Base64 encoded string
     * GET /api/qrcode/base64?text=HelloWorld&size=300
     */
    @GetMapping(value = "/base64", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateQrCodeBase64(
            @RequestParam String text,
            @RequestParam(defaultValue = "250") int size) {
        
        try {
            byte[] qrCode = qrCodeService.generateQrCode(text, size, size);
            String base64Image = Base64.getEncoder().encodeToString(qrCode);
            
            Map<String, String> response = new HashMap<>();
            response.put("text", text);
            response.put("size", String.valueOf(size));
            response.put("qrcode", "data:image/png;base64," + base64Image);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to generate QR code: " + e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Health check endpoint
     * GET /api/qrcode/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "QR Code Generator API");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}
    

