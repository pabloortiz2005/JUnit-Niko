package org.example;

import java.util.Scanner;

public class ControlGastosIngresos {

    static Scanner sc = new Scanner(System.in);

    public static void elegirOpcionGasto(Usuario usuario) {
        int opcion = 0;
        boolean opcionValida = false;
        do {
            System.out.println("Elige una opcion: ");
            System.out.println("1. Vacaciones");
            System.out.println("2. Alquiler");
            System.out.println("3. IRPF de su nomina (15%)");
            System.out.println("4. Vicios Variados");
            System.out.println("5. Salir");
            System.out.print("Elige una opcion: ");
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                if (opcion >= 1 && opcion <= 5) {
                    opcionValida = true;
                } else {
                    System.out.println("Opción no válida, por favor elige una opción entre 1 y 5.");
                }
            } else {
                System.out.println("Por favor ingresa un número válido.");
                sc.next();
            }
        } while (!opcionValida);

        switch (opcion) {
            case 1:
                Usuario.RestarSaldo(usuario);
                System.out.println("Gasto por vacaciones agregado correctamente.");
                break;
            case 2:
                Usuario.RestarSaldo(usuario);
                System.out.println("Gasto por alquiler agregado correctamente.");
                break;
            case 3:
                Usuario.RestarSaldo(usuario);
                System.out.println("Gasto por IRPF agregado correctamente.");
                break;
            case 4:
                Usuario.RestarSaldo(usuario);
                System.out.println("Gasto por Vicios variados agregado correctamente.");
                break;
            case 5:
                System.out.println("Saliendo...");
                break;
        }
    }

    public static void elegirOpcionIngreso(Usuario usuario) {
        int opcion = 0;
        boolean opcionValida = false;

        do {
            System.out.println("Elige una opción de ingreso: ");
            System.out.println("1. Nómina");
            System.out.println("2. Venta de objetos");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine(); // Consumir la nueva línea pendiente
                if (opcion >= 1 && opcion <= 3) {
                    opcionValida = true;
                } else {
                    System.out.println("Opción no válida, por favor elige una opción entre 1 y 3.");
                }
            } else {
                System.out.println("Por favor ingresa un número válido.");
                sc.next();
            }
        } while (!opcionValida);

        switch (opcion) {
            case 1:
                Usuario.SumarSaldo(usuario);
                System.out.println("Ingreso por nómina agregado correctamente.");
                break;
            case 2:
                Usuario.SumarSaldo(usuario);
                System.out.println("Ingreso por venta de objetos agregado correctamente.");
                break;
            case 3:
                System.out.println("Saliendo del menú de ingresos...");
                break;
        }
    }


    public static Usuario Creariniciarusuario() {
        Usuario usuario = null;
        int opcion = 0;
        boolean opcionValida = false;
        do {
            System.out.println("Elige una opcion: ");
            System.out.println("1. Iniciar sesion con DNI ");
            System.out.println("2. Crear usuario con DNI");
            System.out.println("3. Ver Usuarios de la base de datos");
            System.out.println("4. Salir");
            System.out.print("Elige una opcion: ");
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion >= 1 && opcion <= 4) {
                    opcionValida = true;
                } else {
                    System.out.println("Opción no válida, por favor elige una opción entre 1 y 4.");
                }
            } else {
                System.out.println("Por favor ingresa un número válido.");
                sc.next();
            }
        } while (!opcionValida);

        switch (opcion) {
            case 1:
                usuario = Usuario.iniciarSesion();
                break;
            case 2:
                Usuario.crearUsuario();
                usuario = Usuario.iniciarSesion();
                break;
            case 3:
                Usuario.verUsuarios();
                usuario = Usuario.iniciarSesion();
                break;
            case 4:
                System.out.println("Saliendo...");
                DataBase.close();
                System.exit(0);
                break;
        }
        return usuario;
    }


    public static void inicioPrograma(Usuario usuario) {
        int opcion = 0;
        boolean opcionValida = false;
        do {
            System.out.println("Su saldo es de: " + usuario.getSaldo());
            System.out.println("Elige una opción: ");
            System.out.println("1. Gastos");
            System.out.println("2. Ingresos");
            System.out.println("3. Gestionar usuario");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                if (opcion >= 1 && opcion <= 4) {
                    opcionValida = true;
                } else {
                    System.out.println("Opción no válida, por favor elige una opción entre 1 y 4.");
                }
            } else {
                System.out.println("Por favor ingresa un número válido.");
                sc.next();
            }
        } while (!opcionValida);

        switch (opcion) {
            case 1:
                elegirOpcionGasto(usuario);
                break;
            case 2:
                elegirOpcionIngreso(usuario);
                break;
            case 3:
                Usuario.verUsuarios();
                break;
            case 4:
                System.out.println("Saliendo...");
                DataBase.close();
                break;
        }

        if (opcion != 4) {
            inicioPrograma(usuario);
        }
    }
}
