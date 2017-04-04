package package1;
/**
 * <p>
 * Title: StackEmptyException
 * </p>
 * 
 * <p>
 * Description: Used to deliver message if no data is in a stack
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2010
 * </p>
 * 
 * @author Brian Jordan
 * @version 0.9
 */
public class StackEmptyException extends Exception {
	/**
	 * Constructs a new StackEmptyException with a default error message string.
	 */
	public StackEmptyException(){
		super("Exception : Stack is empty");
	}
	/**
	 * Constructs a new StackEmptyException with the parameter as the error message string.
	 * @param msg The string passed as the error message string.
	 */
	public StackEmptyException(String msg){
		super(msg);
	}
}