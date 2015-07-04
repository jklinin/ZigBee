package simpleread;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerPort {
  
  private static String comPort=null;
  private static String portOut=null;
  private static Integer portValue;
  
  // check for a open port and use it
  public static String setPort(){
    for(int i=0; i<10; i++){
      SerialPort serialPort= new SerialPort("COM"+i);
      try{
        serialPort.openPort();
        comPort= "COM"+i;
        serialPort.closePort();
      } catch(Exception e){
        // no message in here, there will always be a few exceptions
        //System.out.println(e);
      }
    }
    return comPort;
  }
  
  // enter specific port if known
  public static String setPort(String portNr){
      SerialPort serialPort= new SerialPort(portNr);
      try{
        serialPort.openPort();
        comPort= portNr;
        serialPort.closePort();
      } catch(Exception e){
        System.out.println(e);
      }
    return comPort;
  }
  
  public static String readPort(){
    return readPort(comPort);
  }
  public static String readPort(String port) {
	  SerialPort serialPort= new SerialPort(port);
	  	String portOut = new String();
	    try{
	      serialPort.openPort(); // open serial port
	      serialPort.setParams(38400, 8, 1, 0); // set params like baudrate
	      byte[] buffer= serialPort.readBytes(10); // read 10byts from serial port
	      
	      portOut = new String(buffer);
	      //portOut= portOut.trim();
	      
	      serialPort.closePort(); //closing
	    } catch(SerialPortException e){
	      System.out.println(e);
	    }
	    
	    return portOut;
  }
  
  public static void writePort(String arg){
    SerialPort serialPort= new SerialPort(comPort);
    
    try{
      serialPort.openPort();
      serialPort.setParams(SerialPort.BAUDRATE_38400, 
                           SerialPort.DATABITS_8, 
                           SerialPort.STOPBITS_1, 
                           SerialPort.PARITY_NONE);
      serialPort.writeBytes(arg.getBytes());
      serialPort.closePort();
    } catch(SerialPortException e){
      System.out.println(e);
    }
  }
  
  public static int getValue(String toConvert){
    portValue=0;
    
      try{
        portValue= Integer.parseInt(toConvert.trim());
        //System.out.println(portValue.toString());
      }catch(NumberFormatException e){
        //System.out.println(".. output is not a number..");
      }
      
    return portValue;
  }
  
  public static double getFlowSpeed(int readValue){
    // readValue can reach 450 pulses at max
    // readValue/450 -> 1 liter throughput
    // 450/30= 15;
    // 30 liters per minute can be calculated, hence 450/30
    double calculated= readValue/15.0;
    
    return calculated;
  }
  
  static boolean bool=true;

}
