package org.project.mw.serial;

import jssc.SerialPort;
import jssc.SerialPortException;

public class WaterFlowSensorReader implements Runnable {

	private static String comPort = null;
	
	private static String FindFreeComPort(){
		String port = null;
		for(int i=0; i<10; i++){
			SerialPort serialPort= new SerialPort("COM"+i);
			try{
				serialPort.openPort();
				port = "COM"+i;
				serialPort.closePort();
			} catch(Exception e){
				// no message in here, there will always be a few exceptions
				//System.out.println(e);
			}
		}
		return port;
	}
	private static String readSensor() {
		SerialPort serialPort= new SerialPort(comPort);
		String portOut = new String();
	    try{
	    	serialPort.openPort(); // open serial port
	    	serialPort.setParams(38400, 8, 1, 0); // set params like baudrate
	    	byte[] buffer= serialPort.readBytes(10); // read 10byts from serial port
	    	
	    	portOut = new String(buffer);
	    	portOut= portOut.trim();
	    	
	    	serialPort.closePort(); //closing
	    } catch(SerialPortException e){
	    	System.out.println(e);
	    }
	    
	    return portOut;
	}
	
	private double lastFlowSpeed = 0;
	private int lastSensorId = 0;
	
	
	private boolean running = false;

	private Thread thread;

	public double getLastFlowSpeed() {
		return lastFlowSpeed;
	}
	
	public int getLastSensorId() {
		return lastSensorId;
	}
	
	@Override
	public void run() {
		comPort = FindFreeComPort();
	    
	    while(running){
	    	parseSensorValue(readSensor());
	    }
	}

	
	/**
	 * Initializes the seperate thread and starts the Serial Port Reader. 
	 */
	public void start() {
		running = true;
		thread = new Thread(this, "SerialPortReader");
		thread.start();
	}
	
	/**
	 * Stop the thread
	 */
	public void stop() {
		running = false;
	}
	
	private void parseSensorValue(String value) {
		String splitString[] = value.split(":");
		lastSensorId = Integer.parseInt(splitString[0]);
		//readValue can reach 450 pulses at max
	    //readValue/450 -> 1 liter throughput
	    //450/30 = 15;
	    //30 liters per minute can be calculated, hence 450/30
		lastFlowSpeed = Integer.parseInt(splitString[1])/15.0;
	}

}
