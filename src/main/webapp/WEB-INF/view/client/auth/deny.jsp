<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>403. Không Được Phép Truy Cập</title>
        <!-- Bao gồm Bootstrap CSS từ CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }

            .error-container {
                margin-top: 100px;
                text-align: center;
            }

            .error-code {
                font-size: 100px;
                font-weight: bold;
                color: #dc3545;
            }

            .error-message {
                font-size: 24px;
                margin-bottom: 30px;
            }

            .btn-custom {
                margin: 5px;
            }
        </style>
    </head>

    <body>
        <div class="container error-container">
            <div class="error-code">404. Page not found.</div>
            <p>URL yêu cầu không được phép trên máy chủ. Đây là tất cả những gì chúng tôi biết. Bạn có thể thử các tùy
                chọn sau:</p>
            <div>
                <a href="/" class="btn btn-primary btn-custom">Trở về Trang Chủ</a>
                <a href="/login" class="btn btn-secondary btn-custom">Đăng Nhập</a>
                <a href="/help" class="btn btn-info btn-custom">Trợ Giúp</a>    
            </div>
        </div>

        <!-- Bao gồm Bootstrap JS và các phụ thuộc từ CDN -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>