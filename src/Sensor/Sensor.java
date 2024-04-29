package Sensor;

public class Sensor {
    private int value;
    private String time;
    private String ownerID;
    private String sensorFeatures;

    public Sensor(int value, String time, String ownerID,  String sensorFeatures ) {
        this.value = value;
        this.time = time;
        this.ownerID = ownerID;
        this.sensorFeatures = sensorFeatures;
    }

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getSensorFeatures() {
		return sensorFeatures;
	}

	public void setSensorFeatures(String sensorFeatures) {
		this.sensorFeatures = sensorFeatures;
	}	
}