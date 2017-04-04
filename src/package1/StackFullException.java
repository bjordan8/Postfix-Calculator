package package1;
/**
 * <p>
 * Title: StackFullException
 * </p>
 * 
 * <p>
 * Description: Used to deliver message for putting data into a full stack
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author Brian Jordan & 
 * @version 0.9
 */
public class StackFullException  extends RuntimeException {
	/**
	 * Constructs a new StackFullException with a default error message string.
	 */
	public StackFullException(){
		super("Exception : Stack is full");
	}
	/**
	 * Constructs a new StackFullException with the parameter as the error message string.
	 * @param msg The string passed as the error message string.
	 */
	public StackFullException(String msg){
		super(msg);
	}
}

