package Login;
import java.util.Scanner;
import java.util.Vector;

import Repository.RepoCredentialsCoach;
import Repository.RepoCredentialsPaciente;
import users.Coach;
import users.Paciente;

public class MainTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear registro si no existe
        RepoCredentialsPaciente.createRegisterIfNotExists();
        RepoCredentialsCoach.createRegisterIfNotExists();

        // Menú principal
        while (true) {
            System.out.println("Menú Principal:");
            System.out.println("1. Crear Paciente");
            System.out.println("2. Actualizar Paciente");
            System.out.println("3. Eliminar Paciente");
            System.out.println("4. Visualizar Pacientes");
            System.out.println("5. Crear Coach");
            System.out.println("6. Actualizar Coach");
            System.out.println("7. Eliminar Coach");
            System.out.println("8. Visualizar Coaches");
            System.out.println("9. Añadir Paciente a Coach");
            System.out.println("10. Salir");
            System.out.print("Seleccione una opción (1-10): ");

            int opcion = scanner.nextInt();
            scanner.nextLine();  
            switch (opcion) {
                case 1:
                    // Crear Paciente
                    System.out.println("Creación de Paciente:");
                    System.out.print("Ingrese el nombre del nuevo paciente: ");
                    String nombrePaciente = scanner.nextLine();
                    System.out.print("Ingrese el ID del nuevo paciente: ");
                    String idPaciente = scanner.nextLine();
                    System.out.print("Ingrese la contraseña del nuevo paciente: ");
                    String passwordPaciente = scanner.nextLine();

                    Paciente nuevoPaciente = new Paciente(nombrePaciente, idPaciente, passwordPaciente);
                    RepoCredentialsPaciente.createPaciente(nuevoPaciente);
                    break;

                case 2:
                    // Actualizar Paciente
                    System.out.println("Actualización de Paciente:");
                    RepoCredentialsPaciente.updatePaciente();
                    break;

                case 3:
                    // Eliminar Paciente
                    System.out.println("Eliminación de Paciente:");
                    System.out.print("Ingrese el nombre del paciente a eliminar: ");
                    String nombreEliminar = scanner.nextLine();
                    RepoCredentialsPaciente.deletePaciente(nombreEliminar);
                    break;

                case 4:
                    // Visualizar Registro de Pacientes
                    System.out.println("Registro de Pacientes:");
                    Vector<Paciente> pacientes = RepoCredentialsPaciente.deserializeJson();
                    for (Paciente paciente : pacientes) {
                        System.out.println(paciente);
                    }
                    break;

                case 5:
                    // Crear Coach
                    System.out.println("Creación de Coach:");
                    System.out.print("Ingrese el nombre del nuevo coach: ");
                    String nombreCoach = scanner.nextLine();
                    System.out.print("Ingrese el ID del nuevo coach: ");
                    String idCoach = scanner.nextLine();
                    System.out.print("Ingrese la contraseña del nuevo coach: ");
                    String passwordCoach = scanner.nextLine();

                    Coach nuevoCoach = new Coach(nombreCoach, idCoach, passwordCoach, new Vector<>());
                    RepoCredentialsCoach.createCoach(nuevoCoach);
                    break;

                case 6:
                    // Actualizar Coach
                    System.out.println("Actualización de Coach:");
                    RepoCredentialsCoach.updateCoach();
                    break;

                case 7:
                    // Eliminar Coach
                    System.out.println("Eliminación de Coach:");
                    System.out.print("Ingrese el nombre del coach a eliminar: ");
                    String nombreEliminarCoach = scanner.nextLine();
                    RepoCredentialsCoach.deleteCoach(nombreEliminarCoach);
                    break;

                case 8:
                    // Visualizar Coaches
                    System.out.println("Registro de Coaches:");
                    Vector<Coach> coaches = RepoCredentialsCoach.deserializeJson();
                    for (Coach coach : coaches) {
                        System.out.println(coach);
                    }
                    break;

                case 9:
                    // Añadir Paciente a Coach
                    System.out.println("Añadir Paciente a Coach:");
                    RepoCredentialsCoach.addPacienteToCoach();
                    break;

                case 10:
                    // Salir del programa
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }
}
