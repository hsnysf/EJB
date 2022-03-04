package ejb.learn.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AddressInvalidException extends Exception {

	private static final long serialVersionUID = 1L;
}
