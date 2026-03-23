# 🎓 Quản lý Thí sinh (Student Management System)

[![Java](https://img.shields.io/badge/Java-17-orange.svg)]() 
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)]() 
[![UI](https://img.shields.io/badge/UI-FlatLaf-green.svg)]()

> *Ứng dụng desktop hiện đại, tinh gọn giúp quản lý dữ liệu thí sinh hiệu quả, tuân thủ nguyên tắc thiết kế MVC.*

---

## 🖼 Xem trước giao diện
<img width="983" height="699" alt="{F7A2CF7A-32E5-41F8-9C85-4DC4EB61B4DA}" src="https://github.com/user-attachments/assets/7b0db47f-072e-4885-acb7-20645f885b2d" />
" />


## ✨ Điểm nổi bật
* **Thiết kế hiện đại:** Sử dụng **FlatLaf** mang lại trải nghiệm người dùng mượt mà.
* **Mô hình MVC chuẩn:** Tách biệt Logic (Controller), Dữ liệu (Model) và Giao diện (View).
* **Trải nghiệm người dùng (UX):**
    * Căn chỉnh bảng dữ liệu thông minh, dễ đọc.
    * Hộp thoại xác nhận trước khi xóa dữ liệu.
    * Tính năng "Làm mới" dữ liệu tức thời.
* **Dễ mở rộng:** Sử dụng `Interface` cho View giúp việc bảo trì dễ dàng.

## 🛠 Công nghệ sử dụng
| Thành phần | Công nghệ |
| :--- | :--- |
| **Ngôn ngữ** | Java (Swing) |
| **Cơ sở dữ liệu** | MySQL 8.0+ |
| **Giao diện (Look & Feel)** | [FlatLaf](https://www.formdev.com/flatlaf/) |

## 🚀 Hướng dẫn bắt đầu
### 1. Thiết lập Database
Chạy script SQL để khởi tạo cấu trúc:
```sql
CREATE DATABASE quanlythisinh;
-- Sử dụng file script SQL đính kèm trong dự án
