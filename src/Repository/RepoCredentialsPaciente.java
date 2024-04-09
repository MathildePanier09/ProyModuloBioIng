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
import users.Paciente;

public class RepoCredentialsPaciente {
	 private static String path = "repository/credentialsPaciente.json";
	  
		// ----------------------------SERIALIZAR Y DESERIALIZAR-------------------------------------
		    public static Vector<Paciente> deserializeJson() {
		        Vector<Paciente> loadedVector = new Vector<>();
		        try (Reader reader = new FileReader(path)) {
		            Gson gson = new Gson();
		            Type loadedList = new TypeToken<Vector<Paciente>>() {}.getType();
		            loadedVector = gson.fromJson(reader, loadedList);
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        return loadedVector;
		    }

		    public static void serializeJson(Vector<Paciente> vectorToBeSerialized) {
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
		    // ----------------------------AÑADIR LOS 1EROS pacientes A MANO---------------
		  /*  public static void firstPaciente() {
		        Vector<Paciente> listaCoach = deserializeJson();
		        Paciente paciente1 = new Paciente("Lola Lolita", "idLola","password");
		        listaCoach.add(paciente1);
		        serializeJson(listaCoach);
		    }
		    */
		/*   
		public static void main(String[] args) {
			// TODO Auto-generated method stub
	      createRegisterIfNotExists();
	      firstPaciente();
		}*/
		    
		// ----------------------------CREAR PACIENTE-------------------------------------
	    public static void createPaciente(Paciente nuevoPaciente) {
	        Vector<Paciente> listaPacientes = deserializeJson();

	        // Verificar si el paciente ya existe en el registro
	        boolean pacienteExistente = listaPacientes.stream().anyMatch(p -> p.getId().equals(nuevoPaciente.getId()));

	        if (!pacienteExistente) {
	            listaPacientes.add(nuevoPaciente);
	            serializeJson(listaPacientes);
	            System.out.println("Paciente creado con éxito: " + nuevoPaciente);
	        } else {
	            System.out.println("Error: El paciente con ID " + nuevoPaciente.getId() + " ya existe en el registro.");
	        }
	    }
	    /*public static void main(String[] args) {
	        createRegisterIfNotExists();
	        
	        // Ejemplo de creación de un nuevo paciente y su adición al registro
	        Paciente nuevoPaciente = new Paciente("NombrePaciente", "idPaciente", "passwordPaciente");
	        createPaciente(nuevoPaciente);
	    } */	
	 // ----------------------------ELIMINAR PACIENTE-------------------------------------
	    
	    public static void deletePaciente(String nombrePaciente) {
	        Vector<Paciente> listaPacientes = deserializeJson();

	        // Buscar el paciente en la lista por su nombre
	        Paciente pacienteAEliminar = null;
	        for (Paciente paciente : listaPacientes) {
	            if (paciente.getName().equalsIgnoreCase(nombrePaciente)) {
	                pacienteAEliminar = paciente;
	                break;
	            }
	        }

	        // Eliminar el paciente si se encontró
	        if (pacienteAEliminar != null) {
	            listaPacientes.remove(pacienteAEliminar);
	            serializeJson(listaPacientes);
	            System.out.println("Paciente eliminado con éxito: " + pacienteAEliminar);
	        } else {
	            System.out.println("Error: No se encontró ningún paciente con el nombre " + nombrePaciente);
	        }
	    }
	 // ----------------------------ACTUALIZAR PACIENTE POR NOMBRE-------------------------------------
	    public static void updatePaciente() {
	        Scanner scanner = new Scanner(System.in);
	        Vector<Paciente> listaPacientes = deserializeJson();

	        // Solicitar el nombre del paciente a actualizar
	        System.out.print("Ingrese el nombre del paciente a actualizar: ");
	        String nombrePaciente = scanner.nextLine();

	        // Buscar el paciente en la lista por su nombre
	        Paciente pacienteAActualizar = null;
	        for (Paciente paciente : listaPacientes) {
	            if (paciente.getName().equalsIgnoreCase(nombrePaciente)) {
	                pacienteAActualizar = paciente;
	                break;
	            }
	        }

	        // Actualizar el paciente si se encontró
	        if (pacienteAActualizar != null) {
	            // Solicitar el atributo a modificar
	            System.out.print("Ingrese el atributo a modificar (name/id/password): ");
	            String atributoAModificar = scanner.nextLine().toLowerCase();

	            // Realizar la modificación basada en el atributo seleccionado
	            switch (atributoAModificar) {
	                case "name":
	                    System.out.print("Ingrese el nuevo nombre: ");
	                    pacienteAActualizar.setName(scanner.nextLine());
	                    break;
	                case "id":
	                    System.out.print("Ingrese el nuevo ID: ");
	                    pacienteAActualizar.setId(scanner.nextLine());
	                    break;
	                case "password":
	                    System.out.print("Ingrese la nueva contraseña: ");
	                    pacienteAActualizar.setPassword(scanner.nextLine());
	                    break;
	                default:
	                    System.out.println("Error: Atributo no válido.");
	                    return;
	            }

	            // Guardar los cambios en el registro
	            serializeJson(listaPacientes);
	            System.out.println("Paciente actualizado con éxito: " + pacienteAActualizar);
	        } else {
	            System.out.println("Error: No se encontró ningún paciente con el nombre " + nombrePaciente);
	        }
	    }
	    public static Paciente findPacienteByName(String pacienteName) {
	        // Obtener la lista de coaches
	        Vector<Paciente> listaPaciente = RepoCredentialsPaciente.deserializeJson();

	        // Buscar el coach por nombre
	        for (Paciente paciente : listaPaciente) {
	            if (paciente.getName().equalsIgnoreCase(pacienteName)) {
	                return paciente;
	            }
	        }

	        // Retorna null si no se encontró el paciente
	        return null;
	    }
/*
	    public static void main(String[] args) {
	        createRegisterIfNotExists();
	        
	        // Ejemplo de actualización de un paciente por nombre
	        updatePaciente();
	    }*/
}
