package org.switchyard.quickstarts.bpel.xts.wsat.ws;

import static javax.jws.soap.SOAPBinding.Style.DOCUMENT;
import static javax.jws.soap.SOAPBinding.Use.LITERAL;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import com.arjuna.mw.wst11.TransactionManagerFactory;
import com.arjuna.mw.wst11.UserTransaction;
import com.arjuna.mw.wst11.UserTransactionFactory;

@Stateless
@WebService(name = "AirportService", serviceName = "AirportService", targetNamespace = "http://www.jboss.org/bpel/examples/airport")
@SOAPBinding(style = DOCUMENT, use = LITERAL)
@HandlerChain(file = "/context-handlers.xml")
public class AirportService {

	private static Logger log = Logger.getLogger(AirportService.class.getName());

	/**
	 * @return format [from/to/month/day]
	 */
	@WebMethod
	@WebResult(name = "fltid")
	public String getFLTID(@WebParam(name = "from") String from,
			@WebParam(name = "to") String to, @WebParam(name = "date") Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return from + "/" + to + "/" + String.valueOf(c.get(Calendar.MONTH))
				+ "/" + String.valueOf(c.get(Calendar.DAY_OF_MONTH));
	}

	@WebMethod
	public void order(@WebParam(name = "name") String name,
			@WebParam(name = "fltid") String fltid) {
		log.info("AirportService:order");
		UserTransaction transactionId = UserTransactionFactory
				.userTransaction();
		if (transactionId != null) {
			log.info("Transaction ID = " + transactionId.toString());
			log.info("Name = " + name);
			OrderParticipant op = new OrderParticipant(
					transactionId.toString(), name, fltid);
			try {
				TransactionManagerFactory.transactionManager()
						.enlistForDurableTwoPhase(
								op,
								"org.jboss.wstdemo:AirportService:" + name
										+ ":" + fltid);
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				e.printStackTrace();
			}
		}
	}

}
