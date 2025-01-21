package org.example;

import java.sql.*;

public class DataBase {
    private static final String DB_NAME = "usuarios.db";
    private static final String TABLE_NAME = "usuarios";

    static Connection connection;

    public static void iniciarBaseDeDatos() {
        conectar();
        crearTabla();
        addVariosUsuarios();
    }

    private static void conectar() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
                System.out.println("Conexión establecida con la base de datos.");
            } catch (SQLException e) {
                throw new RuntimeException("Error al conectar con la base de datos", e);
            }
        }
    }

    private static void crearTabla() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "dni TEXT PRIMARY KEY, "
                + "saldo REAL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
            System.out.println("Tabla 'usuarios' creada o ya existe.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la tabla", e);
        }
    }

    static void addUsuario(Usuario usuario) {
        if (existeUsuario(usuario.getDni())) {
            System.out.println("El usuario con DNI " + usuario.getDni() + " ya existe.");
        } else {
            String query = "INSERT INTO " + TABLE_NAME + " (dni, saldo) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, usuario.getDni());
                statement.setDouble(2, usuario.getSaldo());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Error al agregar el usuario", e);
            }
        }
    }

    private static boolean existeUsuario(String dni) {
        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE dni = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, dni);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar la existencia del usuario", e);
        }
    }

    public static Usuario getUsuario(String dni) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE dni = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, dni);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(rs.getString("dni"), rs.getDouble("saldo"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el usuario con DNI: " + dni, e);
        }
        return null;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexión con la base de datos", e);
        }
    }

    public static void addVariosUsuarios() {
        String[][] usuarios = {
                {"12345678", "500.0"},
                {"87654321", "1000.0"},
                {"11223344", "250.0"},
                {"44332211", "750.0"}
        };

        for (String[] usuario : usuarios) {
            String dni = usuario[0];
            double saldo = Double.parseDouble(usuario[1]);

            // Verificamos si el usuario ya existe en la base de datos
            if (!existeUsuario(dni)) {
                String query = "INSERT INTO " + TABLE_NAME + " (dni, saldo) VALUES (?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, dni);
                    statement.setDouble(2, saldo);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException("Error al agregar el usuario con DNI " + dni, e);
                }
            }
        }
        System.out.println("Usuarios predeterminados agregados si no existían.");
    }

    public static void actualizarSaldo(Usuario usuario) {
        String query = "UPDATE " + TABLE_NAME + " SET saldo = ? WHERE dni = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, usuario.getSaldo());
            statement.setString(2, usuario.getDni());
            statement.executeUpdate();
            System.out.println("Saldo actualizado para el usuario con DNI: " + usuario.getDni());
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el saldo del usuario con DNI: " + usuario.getDni(), e);
        }
    }


}
