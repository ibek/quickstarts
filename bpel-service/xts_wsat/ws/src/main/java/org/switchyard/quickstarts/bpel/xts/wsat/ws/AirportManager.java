package org.switchyard.quickstarts.bpel.xts.wsat.ws;

public class AirportManager {

  public static final String cities[] = {"Brno","Praha","London"};
  
  public static boolean checkFLTID(String fltid){
    boolean state = false;
    String parts[] = fltid.split("/");
    if(parts.length != 4)
      return state;
    for(int i=0; i<2; i++){
      state = false;
      for(String city : cities){
         if(parts[i].compareTo(city) == 0){
           state = true;
           break;
         }
      } 
      if(!state)
        return state;
    }
    return state;
  }
  
}
