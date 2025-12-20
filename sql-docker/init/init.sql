USE quanlythisinh;

CREATE TABLE tinh (
                      ma_tinh INT PRIMARY KEY,
                      ten_tinh VARCHAR(100) NOT NULL,

                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                          ON UPDATE CURRENT_TIMESTAMP,

                      UNIQUE KEY uk_tinh_ten (ten_tinh)
);

CREATE TABLE thi_sinh (
                          ma_thi_sinh INT PRIMARY KEY,
                          ten_thi_sinh VARCHAR(150) NOT NULL,
                          ma_tinh INT NOT NULL,
                          ngay_sinh DATE NOT NULL,
                          gioi_tinh BOOLEAN NOT NULL,

                          diem_mon_1 FLOAT NOT NULL,
                          diem_mon_2 FLOAT NOT NULL,
                          diem_mon_3 FLOAT NOT NULL,

                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                              ON UPDATE CURRENT_TIMESTAMP,

                          CONSTRAINT fk_thi_sinh_tinh
                              FOREIGN KEY (ma_tinh)
                                  REFERENCES tinh(ma_tinh)
);
CREATE INDEX idx_ma_thi_sinh ON thi_sinh(ma_thi_sinh);
CREATE INDEX idx_thi_sinh_ten ON thi_sinh(ten_thi_sinh);
CREATE INDEX idx_thi_sinh_tinh ON thi_sinh(ma_tinh);
CREATE INDEX idx_thi_sinh_ngay_sinh ON thi_sinh(ngay_sinh);
CREATE INDEX idx_ten_tinh ON tinh(ten_tinh);

