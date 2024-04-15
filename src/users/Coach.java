package users;

import java.util.Vector;

public class Coach {
String name; 
String id; 
String password; 
Vector<Deportista> pacientes;
public Coach(String name, String id, String password, Vector<Deportista> pacientes) {
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
public Vector<Deportista> getPacientes() {
	return pacientes;
}
public void setPacientes(Vector<Deportista> pacientes) {
	this.pacientes = pacientes;
}
@Override
public String toString() {
	return "Coach [name=" + name + ", id=" + id + ", password=" + password + ", pacientes=" + pacientes + "]";
} 



}
