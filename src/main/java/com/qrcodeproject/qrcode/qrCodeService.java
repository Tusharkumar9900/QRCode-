package com.qrcodeproject.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class qrCodeService {
    /**
     * Generate QR code as byte array
     * 
     * @param text   The text/URL to encode
     * @param width  Width of the QR code
     * @param height Height of the QR code
     * @return QR code as PNG byte array
     * @throws WriterException If encoding fails
     * @throws IOException     If image writing fails
     */
    public byte[] generateQrCode(String text, int width, int height) 
            throws WriterException, IOException {
        
        // Validate input
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        
        if (width < 100 || width > 1000 || height < 100 || height > 1000) {
            throw new IllegalArgumentException("Size must be between 100 and 1000 pixels");
        }
        
        // Configure QR code hints
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        
        // Generate QR code matrix
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
            text, 
            BarcodeFormat.QR_CODE, 
            width, 
            height, 
            hints
        );
        
        // Convert to PNG image bytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        
        return outputStream.toByteArray();
    }
    
    /**
     * Generate QR code with default size (250x250)
     */
    public byte[] generateQrCode(String text) throws WriterException, IOException {
        return generateQrCode(text, 250, 250);
    }
}
