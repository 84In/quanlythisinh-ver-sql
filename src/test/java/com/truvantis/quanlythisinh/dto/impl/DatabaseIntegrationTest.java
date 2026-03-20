package com.truvantis.quanlythisinh.dto.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.model.ThiSinh;
import com.truvantis.quanlythisinh.service.impl.ThiSinhService;
import com.truvantis.quanlythisinh.service.impl.TinhService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseIntegrationTest {

    private static final String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MySQL";

    @BeforeAll
    void setupDatabase() throws Exception {
        System.setProperty("db.driver", "org.h2.Driver");
        System.setProperty("db.url", DB_URL);
        System.setProperty("db.user", "sa");
        System.setProperty("db.password", "");

        // Create schema matching the production database (with auto-increment for
        // tests)
        try (Connection conn = DriverManager.getConnection(DB_URL, "sa", "");
                Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE Tinh ("
                    + "maTinh INT PRIMARY KEY, "
                    + "tenTinh VARCHAR(100) NOT NULL, "
                    + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")");

            stmt.execute("CREATE TABLE ThiSinh ("
                    + "maThiSinh INT AUTO_INCREMENT PRIMARY KEY, "
                    + "tenThiSinh VARCHAR(150) NOT NULL, "
                    + "maTinh INT NOT NULL, "
                    + "ngaySinh DATE NOT NULL, "
                    + "gioiTinh BOOLEAN NOT NULL, "
                    + "diemMon1 FLOAT NOT NULL, "
                    + "diemMon2 FLOAT NOT NULL, "
                    + "diemMon3 FLOAT NOT NULL, "
                    + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                    + "CONSTRAINT fk_thi_sinh_tinh FOREIGN KEY (maTinh) REFERENCES Tinh(maTinh)"
                    + ")");

            // Seed reference data
            stmt.execute("INSERT INTO Tinh (maTinh, tenTinh) VALUES (1, 'Ha Noi')");
        }
    }

    @BeforeEach
    void resetDaoSingletons() throws Exception {
        // Ensure each test gets a fresh singleton that will use the test DB properties.
        resetSingleton(ThiSinhDao.class, "instance");
        resetSingleton(TinhDao.class, "instance");
    }

    @AfterAll
    void cleanup() {
        // Clear test properties so it doesn't affect other test suites
        System.clearProperty("db.driver");
        System.clearProperty("db.url");
        System.clearProperty("db.user");
        System.clearProperty("db.password");
    }

    @Test
    void thiSinhDao_SaveAndFindById_ShouldWork() {
        ThiSinhDao dao = ThiSinhDao.getInstance();
        ThiSinh input = new ThiSinh(0, "Nguyen Van B", new Tinh(1, "Ha Noi"), new Date(), true, 6.5f, 7.0f, 8.0f);

        long id = dao.saveThiSinh(input);
        assertNotNull(id);

        ThiSinh found = dao.findById((int) id);
        assertNotNull(found);
        assertEquals("Nguyen Van B", found.getTenThiSinh());
        assertEquals(1, found.getQueQuan().getMaTinh());
    }

    @Test
    void tinhDao_FindByName_ShouldReturnExistingTinh() {
        TinhDao dao = TinhDao.getInstance();
        Tinh found = dao.findTinhByName("Ha Noi");

        assertNotNull(found);
        assertEquals(1, found.getMaTinh());
        assertEquals("Ha Noi", found.getTenTinh());
    }

    @Test
    void tinhService_FindByName_ShouldReturnTinh() {
        TinhService service = new TinhService();
        Tinh found = service.findByName("Ha Noi");

        assertNotNull(found);
        assertEquals(1, found.getMaTinh());
    }

    @Test
    void thiSinhService_Insert_ShouldSaveAndReturnId() {
        ThiSinhService service = new ThiSinhService();
        ThiSinh input = new ThiSinh(0, "Le Thi C", new Tinh(1, "Ha Noi"), new Date(), false, 5.0f, 6.0f, 7.0f);

        long id = service.insert(input);
        assertNotNull(id);

        ThiSinh loaded = service.findById((int) id);
        assertNotNull(loaded);
        assertEquals("Le Thi C", loaded.getTenThiSinh());
    }

    private static void resetSingleton(Class<?> daoClass, String fieldName) throws Exception {
        Field f = daoClass.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, null);
    }
}
