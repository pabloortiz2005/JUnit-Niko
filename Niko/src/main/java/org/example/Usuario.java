package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Usuario {

    private String dni;
    private  double saldo;

    static Scanner sc = new Scanner(System.in);

    public Usuario(String dni, double saldo) {
        this.dni = dni;
        this.saldo = saldo;
    }

    public String getDni() {
        return dni;
    }

    public  double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public static boolean validarDni(String dni) {
        return dni.matches("\\d{8}");
    }
    public static void crearUsuario() {
        String dni;
        double saldo;

        while (true) {
            System.out.println("Ingrese el DNI (8 dígitos numéricos): ");
            dni = sc.nextLine();
            if (validarDni(dni)) {
                break;
            }
            System.out.println("DNI no válido. Debe contener exactamente 8 dígitos numéricos.");
        }

        while (true) {
            System.out.println("Ingrese el saldo (debe ser un número positivo): ");
            if (sc.hasNextDouble()) {
                saldo = sc.nextDouble();
                sc.nextLine();
                if (saldo >= 0) {
                    break;
                }
            } else {
                sc.nextLine();
            }
            System.out.println("Saldo no válido. Debe ser un número positivo.");
        }


        Usuario nuevoUsuario = new Usuario(dni, saldo);
        DataBase.addUsuario(nuevoUsuario);
        System.out.println("Usuario creado correctamente.");
    }

    public static Usuario iniciarSesion() {
        String dni;

        while (true) {
            System.out.println("Ingrese el DNI (8 dígitos numéricos): ");
            dni = sc.nextLine();
            if (validarDni(dni)) {
                break;
            }
            System.out.println("DNI no válido. Debe contener exactamente 8 dígitos numéricos.");
        }


        Usuario usuario = DataBase.getUsuario(dni);

        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso.");
            return usuario;

        } else {
            System.out.println("El usuario no existe.");
        }

        return null;
    }



    public static void verUsuarios() {
        String query = "SELECT * FROM usuarios";
        try (Statement statement = DataBase.connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            System.out.println("Usuarios en la base de datos:");
            while (rs.next()) {
                String dni = rs.getString("dni");
                double saldo = rs.getDouble("saldo");
                System.out.println(new Usuario(dni, saldo));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los usuarios", e);
        }
    }

    public static void SumarSaldo(Usuario Usuario) {
        System.out.println("El saldo actual es de: " + Usuario.getSaldo());
        System.out.println("Ingrese el monto a sumar: ");
        double monto = sc.nextDouble();
        if (monto < 0) {
            System.out.println("No se puede sumar un monto negativo.");
            return;
        }
        double nuevoSaldo = Usuario.getSaldo() + monto;
        Usuario.setSaldo(nuevoSaldo);

        DataBase.actualizarSaldo(Usuario);

        System.out.println("El nuevo saldo es de: " + nuevoSaldo);
    }

    public static void RestarSaldo(Usuario Usuario) {
        System.out.println("El saldo actual es de: " + Usuario.getSaldo());
        System.out.println("Ingrese el monto a restar: ");
        double monto = sc.nextDouble();

        if (monto > Usuario.getSaldo()) {
            System.out.println("No se puede restar un monto mayor al saldo actual.");
            return;
        }
        if (monto < 0) {
            System.out.println("No se puede restar un monto negativo.");
            return;
        }
        double nuevoSaldo = Usuario.getSaldo() - monto;
        Usuario.setSaldo(nuevoSaldo);

        DataBase.actualizarSaldo(Usuario);

        System.out.println("El nuevo saldo es de: " + nuevoSaldo);
    }


}
