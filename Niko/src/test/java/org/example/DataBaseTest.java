package org.example;

import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {

    private Connection connectToMemoryDatabase() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
            DataBase.connection = connection; // Asignamos la conexión a la clase `DataBase`
            DataBase.iniciarBaseDeDatos();
            return connection;
        } catch (Exception e) {
            throw new RuntimeException("Error al conectar a la base de datos en memoria", e);
        }
    }

    @Test
    void testConexionBaseDeDatos() throws SQLException {
        Connection connection = connectToMemoryDatabase();
        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    void testCrearTabla() {
        Connection connection = connectToMemoryDatabase();  //Ayuda de chatGPT
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='usuarios';")) {
            assertTrue(rs.next(), "La tabla 'usuarios' no se creó correctamente.");
        } catch (Exception e) {
            fail("Error al verificar la creación de la tabla: " + e.getMessage());
        }
    }

    @Test
    void testAgregarUsuario() {
        Connection connection = connectToMemoryDatabase();
        Usuario usuario = new Usuario("12345678", 100.0);
        DataBase.addUsuario(usuario);

        String query = "SELECT * FROM usuarios WHERE dni = '12345678'";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            assertTrue(rs.next(), "El usuario no se agregó correctamente.");
            assertEquals("12345678", rs.getString("dni"));
            assertEquals(100.0, rs.getDouble("saldo"));
        } catch (Exception e) {
            fail("Error al verificar la inserción del usuario: " + e.getMessage());
        }
    }

    @Test
    void testObtenerUsuario() {
        Connection connection = connectToMemoryDatabase();
        Usuario usuario = new Usuario("12345678", 100.0);
        DataBase.addUsuario(usuario);

        Usuario obtenido = DataBase.getUsuario("12345678");
        assertNotNull(obtenido, "No se pudo obtener el usuario.");
        assertEquals("12345678", obtenido.getDni());
        assertEquals(100.0, obtenido.getSaldo());
    }

    @Test
    void testActualizarSaldo() {
        Connection connection = connectToMemoryDatabase();
        Usuario usuario = new Usuario("12345678", 100.0);
        DataBase.addUsuario(usuario);

        usuario.setSaldo(200.0);
        DataBase.actualizarSaldo(usuario);

        Usuario actualizado = DataBase.getUsuario("12345678");
        assertNotNull(actualizado, "No se pudo obtener el usuario actualizado.");
        assertEquals(200.0, actualizado.getSaldo());
    }

    @Test
    void testAgregarVariosUsuarios() {
        Connection connection = connectToMemoryDatabase();
        DataBase.addVariosUsuarios();

        String query = "SELECT COUNT(*) AS total FROM usuarios";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            assertTrue(rs.next(), "No se pudo obtener el total de usuarios.");
            assertEquals(4, rs.getInt("total"), "El número de usuarios agregados no es correcto.");
        } catch (Exception e) {
            fail("Error al verificar los usuarios predeterminados: " + e.getMessage());
        }
    }
}
