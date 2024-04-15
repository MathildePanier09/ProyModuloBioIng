package Repository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import users.Coach;
import users.Deportista;
import Repository.RepoCredentialsPaciente;

public class RepoCredentialsCoach {
	Scanner scanner = new Scanner(System.in);
	private static String path = "repository/credentialsCoach.json";

	// ----------------------------SERIALIZAR Y DESERIALIZAR-------------------------------------
	public static Vector<Coach> deserializeJson() {
		Vector<Coach> loadedVector = new Vector<>();
		try (Reader reader = new FileReader(path)) {
			Gson gson = new Gson();
			Type loadedList = new TypeToken<Vector<Coach>>() {}.getType();
			loadedVector = gson.fromJson(reader, loadedList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadedVector;
	}

	public static void serializeJson(Vector<Coach> vectorToBeSerialized) {
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

		try (FileWriter writer = new FileWriter(path)) {
			prettyGson.toJson(vectorToBeSerialized, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ----------------------------CREAR REGISTRO SI NO EXISTE-------

	public static void createRegisterIfNotExists() {
		try {
			Path registerPath = Paths.get(path);
			if (!Files.exists(registerPath)) {
				Files.createDirectories(registerPath.getParent());
				Files.createFile(registerPath);
				serializeJson(new Vector<>()); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ----------------------------AÑADIR LOS 1EROS Coach A MANO---------------
	public static void firstCoach() {
		Vector<Coach> listaCoach = deserializeJson();
		Coach coach1 = new Coach("Pepe Pepito", "idPepito","password",null);
		listaCoach.add(coach1);
		serializeJson(listaCoach);
	}


	/*public static void main(String[] args) {
			// TODO Auto-generated method stub
	      createRegisterIfNotExists();
	      firstCoach();
		}*/

	// ----------------------------CREAR Coach-------------------------------------
	public static void createCoach(Coach nuevoCoach) {
		Vector<Coach> listaCoach = deserializeJson();

		// Verificar si el paciente ya existe en el registro
		boolean coachExistente = listaCoach.stream().anyMatch(p -> p.getId().equals(nuevoCoach.getId()));

		if (!coachExistente) {
			listaCoach.add(nuevoCoach);
			serializeJson(listaCoach);
			System.out.println("Coach creado con éxito: " + nuevoCoach);
		} else {
			System.out.println("Error: El coach con ID " + nuevoCoach.getId() + " ya existe en el registro.");
		}
	}
	/*public static void main(String[] args) {
	        createRegisterIfNotExists();

	        // Ejemplo de creación de un nuevo paciente y su adición al registro
	        Paciente nuevoPaciente = new Paciente("NombrePaciente", "idPaciente", "passwordPaciente");
	        createPaciente(nuevoPaciente);
	    } */	
	// ----------------------------ELIMINAR PACIENTE-------------------------------------

	public static void deleteCoach(String nombreCoach) {
		Vector<Coach> listaCoach = deserializeJson();

		// Buscar el paciente en la lista por su nombre
		Coach CoachAEliminar = null;
		for (Coach coach : listaCoach) {
			if (coach.getName().equalsIgnoreCase(nombreCoach)) {
				CoachAEliminar = coach;
				break;
			}
		}

		// Eliminar el paciente si se encontró
		if (CoachAEliminar != null) {
			listaCoach.remove(CoachAEliminar);
			serializeJson(listaCoach);
			System.out.println("Coach eliminado con éxito: " + CoachAEliminar);
		} else {
			System.out.println("Error: No se encontró ningún Coach con el nombre " + nombreCoach);
		}
	}
	// ----------------------------ACTUALIZAR PACIENTE POR NOMBRE-------------------------------------
	public static void updateCoach() {
		Scanner scanner = new Scanner(System.in);
		Vector<Coach> listaCoach = (Vector<Coach>) deserializeJson();

		// Solicitar el nombre del Coach a actualizar
		System.out.print("Ingrese el nombre del Coach a actualizar: ");
		String nombreCoach = scanner.nextLine();

		// Buscar el paciente en la lista por su nombre
		Coach CoachAActualizar = null;
		for (Coach coach : listaCoach) {
			if (coach.getName().equalsIgnoreCase(nombreCoach)) {
				CoachAActualizar = coach;
				break;
			}
		}

		// Actualizar el paciente si se encontró
		if (CoachAActualizar != null) {
			// Solicitar el atributo a modificar
			System.out.print("Ingrese el atributo a modificar (name/id/password): ");
			String atributoAModificar = scanner.nextLine().toLowerCase();

			// Realizar la modificación basada en el atributo seleccionado
			switch (atributoAModificar) {
			case "name":
				System.out.print("Ingrese el nuevo nombre: ");
				CoachAActualizar.setName(scanner.nextLine());
				break;
			case "id":
				System.out.print("Ingrese el nuevo ID: ");
				CoachAActualizar.setId(scanner.nextLine());
				break;
			case "password":
				System.out.print("Ingrese la nueva contraseña: ");
				CoachAActualizar.setPassword(scanner.nextLine());
				break;
			default:
				System.out.println("Error: Atributo no válido.");
				return;
			}

			// Guardar los cambios en el registro
			serializeJson(listaCoach);
			System.out.println("Coach actualizado con éxito: " + CoachAActualizar);
		} else {
			System.out.println("Error: No se encontró ningún Coach con el nombre " + nombreCoach);
		}
	}
	/*
	    public static void main(String[] args) {
	        createRegisterIfNotExists();

	        // Ejemplo de actualización de un coach por nombre
	        updateCoach();
	    }*/
	public static Coach returnCoach() {
		Scanner scanner = new Scanner(System.in);

		// Solicitar el nombre del coach
		System.out.print("Ingrese el nombre del coach al que desea añadir un paciente: ");
		String nombreCoach = scanner.nextLine();

		// Buscar el coach por nombre
		Coach coach = findCoachByName(nombreCoach);
		return coach;
	}

	public static void addPacienteToCoach() {
		Coach coach = returnCoach();
		Scanner scanner = new Scanner(System.in);
		 Vector<Deportista> listaPacientes = RepoCredentialsPaciente.deserializeJson();
		 Vector<Coach> listaCoach = deserializeJson();

		// Verificar si se encontró el coach
		if (coach != null) {
			// Solicitar información del nuevo paciente
			System.out.println("Anadir un paciente al coach:");
			System.out.print("Ingrese el nombre del paciente: ");
			String nombrePaciente = scanner.nextLine();
			
			if (RepoCredentialsPaciente.findPacienteByName(nombrePaciente) != null) {
				Deportista paciente = RepoCredentialsPaciente.findPacienteByName(nombrePaciente);
				updateCoachConPacientes(coach,paciente);
			
			}
			
		}
	}
	private static Coach findCoachByName(String coachName) {
		// Obtener la lista de coaches
		Vector<Coach> listaCoach = RepoCredentialsCoach.deserializeJson();

		// Buscar el coach por nombre
		for (Coach coach : listaCoach) {
			if (coach.getName().equalsIgnoreCase(coachName)) {
				return coach;
			}
		}

		// Retorna null si no se encontró el coach
		return null;
	}
	
	public static void updateCoachConPacientes(Coach coach, Deportista paciente) {
	    // Deserializar la lista actual de coaches
	    Vector<Coach> listaCoach = deserializeJson();

	    // Buscar el coach en la lista por su ID
	    Coach coachEnLista = null;
	    for (Coach c : listaCoach) {
	        if (c.getId().equals(coach.getId())) {
	            coachEnLista = c;
	            break;
	        }
	    }

	    // Verificar si el coach se encontró en la lista
	    if (coachEnLista != null) {
	        // Obtener el vector de pacientes del coach en la lista
	        Vector<Deportista> pacientes = coachEnLista.getPacientes();

	        // Verificar si el vector de pacientes es nulo
	        if (pacientes == null) {
	            pacientes = new Vector<>();
	        }

	        // Verificar si el paciente ya está asociado al coach
	        if (!pacientes.contains(paciente)) {
	            // Agregar el paciente al vector de pacientes del coach
	            pacientes.add(paciente);

	            // Actualizar el vector de pacientes en el objeto Coach
	            coachEnLista.setPacientes(pacientes);

	            // Serializar la lista actualizada de coaches
	            serializeJson(listaCoach);

	            System.out.println("Paciente añadido con éxito al coach " + coach.getName());
	        } else {
	            System.out.println("Error: El paciente ya está asociado al coach " + coach.getName());
	        }
	    } else {
	        System.out.println("Error: No se encontró el coach en la lista.");
	    }
	}

}
