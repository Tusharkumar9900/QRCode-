# 🧩 QR Code Generator API

A lightweight and containerized **Spring Boot REST API** that generates **QR codes** for any given text or URL.  
This project demonstrates backend development with **Spring Boot**, containerization using **Docker**, and deployment readiness for **Kubernetes**.

---

## 📘 Features

- Generate QR code for any text or URL  
- Returns image in PNG format or Base64 string  
- Customizable QR code size via query parameter  
- Stateless REST service — perfect for scaling with Docker & Kubernetes  
- Clean, production-ready setup

---

## ⚙️ Tech Stack

- **Backend:** Spring Boot (Java 17)  
- **QR Library:** ZXing  
- **Containerization:** Docker   
- **Build Tool:** Maven

---

## 🧱 Architecture Overview

Client (Postman / Web)
│
▼
Spring Boot REST API (QR Generator)
│
▼
ZXing Library → QR Code (PNG / Base64)

## 🚀 API Endpoints

| Method | Endpoint | Description | Example |
|---------|-----------|--------------|----------|
| `GET` | `/api/qrcode?text=HelloWorld` | Generate a default QR code | Returns PNG |
| `GET` | `/api/qrcode?text=Hello&size=400` | Generate custom-sized QR code | Returns PNG |
| `GET` | `/api/qrcode/base64?text=Hello` | Return Base64 encoded QR | Returns JSON |

**Example Response (Base64):**
```json
{
  "base64": "iVBO........."
}

