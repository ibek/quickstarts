package org.switchyard.quickstarts.bpel.xts.wsat.ws;

import java.io.Serializable;
import java.util.logging.Logger;

import com.arjuna.wst.Aborted;
import com.arjuna.wst.Durable2PCParticipant;
import com.arjuna.wst.Prepared;
import com.arjuna.wst.SystemException;
import com.arjuna.wst.Vote;
import com.arjuna.wst.WrongStateException;

public class OrderParticipant implements Durable2PCParticipant, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -6459800278322126331L;
  
  private static Logger log = Logger.getLogger(OrderParticipant.class.getName());

  protected String txID;
  protected String name;
  protected String fltid;
  
  public OrderParticipant(String txID, String name, String fltid){
    this.txID = txID;
    this.name = name;
    this.fltid = fltid;
  }
  
  public String getTxID(){
    return txID;
  }

  @Override
  public Vote prepare() throws WrongStateException, SystemException {
    boolean state = AirportManager.checkFLTID(fltid);
    if (state) {
      log.info("AirportOrderParticipant "+fltid+" prepare: prepared");
      return new Prepared();
    } else {
      log.info("AirportOrderParticipant "+fltid+" prepare: aborted");
      return new Aborted();
    }
  }

  @Override
  public void commit() throws WrongStateException, SystemException {
    log.info("AirportOrderParticipant "+fltid+" commit");
  }

  @Override
  public void rollback() throws WrongStateException, SystemException {
    log.info("AirportOrderParticipant "+fltid+" rollback");
  }

  @Override
  public void unknown() throws SystemException {
    
  }

  @Override
  public void error() throws SystemException {
    
  }

}
