package users;

import java.util.Vector;

public class Coach {
String name; 
String id; 
String password; 
Vector<Paciente> pacientes;
public Coach(String name, String id, String password, Vector<Paciente> pacientes) {
	super();
	this.name = name;
	this.id = id;
	this.password = password;
	this.pacientes = pacientes;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Vector<Paciente> getPacientes() {
	return pacientes;
}
public void setPacientes(Vector<Paciente> pacientes) {
	this.pacientes = pacientes;
} 



}
