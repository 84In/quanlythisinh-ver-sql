-- 1. Tạo Database với bảng mã chuẩn tiếng Việt
CREATE DATABASE IF NOT EXISTS `quanlythisinh` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `quanlythisinh`;
-- ------------------------------------------------------------------
-- 2. Bảng Tỉnh (Phải tạo trước vì ThiSinh tham chiếu đến nó)
-- ------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `Tinh` (
    `maTinh` INT AUTO_INCREMENT PRIMARY KEY,
    `tenTinh` VARCHAR(255) NOT NULL,
    `createdAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updatedAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE = InnoDB;
-- ------------------------------------------------------------------
-- 3. Bảng Thí Sinh
-- ------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `ThiSinh` (
    `maThiSinh` INT AUTO_INCREMENT PRIMARY KEY,
    `tenThiSinh` VARCHAR(255) NOT NULL,
    `maTinh` INT NOT NULL,
    `ngaySinh` DATE,
    `gioiTinh` TINYINT(1) NOT NULL DEFAULT 1,
    -- 1 = Nam, 0 = Nữ
    `diemMon1` FLOAT DEFAULT 0,
    `diemMon2` FLOAT DEFAULT 0,
    `diemMon3` FLOAT DEFAULT 0,
    `createdAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updatedAt` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    -- Khóa ngoại: Đảm bảo maTinh phải tồn tại trong bảng Tinh
    CONSTRAINT `fk_thisinh_tinh` FOREIGN KEY (`maTinh`) REFERENCES `Tinh` (`maTinh`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;
-- ------------------------------------------------------------------
-- 4. Tạo User và Cấp quyền (Dành cho kết nối ứng dụng)
-- ------------------------------------------------------------------
-- Lưu ý: Trong XAMPP, nếu chạy trên localhost thì dùng 'admin'@'localhost'
CREATE USER IF NOT EXISTS 'admin' @'localhost' IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON `quanlythisinh`.* TO 'admin' @'localhost';
-- Nếu bạn dùng Docker hoặc kết nối từ xa, dùng thêm dòng này:
CREATE USER IF NOT EXISTS 'admin' @'%' IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON `quanlythisinh`.* TO 'admin' @'%';
FLUSH PRIVILEGES;
-- ------------------------------------------------------------------
-- 5. Chèn dữ liệu mẫu vào bảng Tỉnh
INSERT INTO `Tinh` (`maTinh`, `tenTinh`)
VALUES (1, 'Hà Nội'),
    (2, 'Hồ Chí Minh'),
    (3, 'Đà Nẵng'),
    (4, 'Hải Phòng'),
    (5, 'Cần Thơ'),
    (6, 'An Giang'),
    (7, 'Bà Rịa - Vũng Tàu'),
    (8, 'Bắc Giang'),
    (9, 'Bắc Kạn'),
    (10, 'Bạc Liêu'),
    (11, 'Bắc Ninh'),
    (12, 'Bến Tre'),
    (13, 'Bình Dương'),
    (14, 'Bình Phước'),
    (15, 'Bình Thuận'),
    (16, 'Bình Định'),
    (17, 'Cà Mau'),
    (18, 'Cao Bằng'),
    (19, 'Đắk Lắk'),
    (20, 'Đắk Nông'),
    (21, 'Điện Biên'),
    (22, 'Đồng Nai'),
    (23, 'Đồng Tháp'),
    (24, 'Gia Lai'),
    (25, 'Hà Giang'),
    (26, 'Hà Nam'),
    (27, 'Hà Tĩnh'),
    (28, 'Hải Dương'),
    (29, 'Hậu Giang'),
    (30, 'Hòa Bình'),
    (31, 'Hưng Yên'),
    (32, 'Khánh Hòa'),
    (33, 'Kiên Giang'),
    (34, 'Kon Tum'),
    (35, 'Lai Châu'),
    (36, 'Lâm Đồng'),
    (37, 'Lạng Sơn'),
    (38, 'Lào Cai'),
    (39, 'Long An'),
    (40, 'Nam Định'),
    (41, 'Nghệ An'),
    (42, 'Ninh Bình'),
    (43, 'Ninh Thuận'),
    (44, 'Phú Thọ'),
    (45, 'Phú Yên'),
    (46, 'Quảng Bình'),
    (47, 'Quảng Nam'),
    (48, 'Quảng Ngãi'),
    (49, 'Quảng Ninh'),
    (50, 'Quảng Trị'),
    (51, 'Sóc Trăng'),
    (52, 'Sơn La'),
    (53, 'Tây Ninh'),
    (54, 'Thái Bình'),
    (55, 'Thái Nguyên'),
    (56, 'Thanh Hóa'),
    (57, 'Thừa Thiên Huế'),
    (58, 'Tiền Giang'),
    (59, 'Trà Vinh'),
    (60, 'Tuyên Quang'),
    (61, 'Vĩnh Long'),
    (62, 'Vĩnh Phúc'),
    (63, 'Yên Bái');
-- ------------------------------------------------------------------
-- 6. Chèn dữ liệu mẫu vào bảng Thí Sinh
DELIMITER $$ CREATE PROCEDURE GenerateThiSinhData() BEGIN
DECLARE i INT DEFAULT 1;
DECLARE ho_ten VARCHAR(100);
-- Vòng lặp tạo 1000 dòng
WHILE i <= 1000 DO
SET ho_ten = CONCAT('Thí sinh ', i);
INSERT INTO `ThiSinh` (
        `tenThiSinh`,
        `maTinh`,
        `ngaySinh`,
        `gioiTinh`,
        `diemMon1`,
        `diemMon2`,
        `diemMon3`
    )
VALUES (
        ho_ten,
        FLOOR(RAND() * 63) + 1,
        -- Mã tỉnh từ 1 đến 63
        DATE_ADD('2000-01-01', INTERVAL FLOOR(RAND() * 1000) DAY),
        -- Ngày sinh ngẫu nhiên
        FLOOR(RAND() * 2),
        -- Giới tính 0 hoặc 1
        ROUND(RAND() * 10, 1),
        -- Điểm 0.0 - 10.0
        ROUND(RAND() * 10, 1),
        ROUND(RAND() * 10, 1)
    );
SET i = i + 1;
END WHILE;
END $$ DELIMITER;
-- Gọi thủ tục để thực thi
CALL GenerateThiSinhData();
-- Xóa thủ tục sau khi dùng xong để dọn dẹp
DROP PROCEDURE IF EXISTS GenerateThiSinhData;