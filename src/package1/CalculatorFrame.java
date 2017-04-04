package package1;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import java.util.StringTokenizer;

/**
*)<b>Title:</b> Lab 0:<br>
*)<b>Filename:</b> CalculatorFrame.java<br>
*)<b>Date Written:</b> October 16th,2015<br>
*)<b>Due Date:</b> October 23rd, 2015<br>
*)<p>
*)<b>Description:</b><br>
*)This class creates and displays a calculator allowing the user to input an equation containing
*number operands and operators. It then coverts the infix equation into postfix. Once this is done
*it evaluates the postfix expression
*)</p>
*@author Brian Jordan()
*/
@SuppressWarnings("serial")
class CalculatorFrame extends JFrame implements ActionListener {
	JTextField jtfInfix = new JTextField(21); // for infix 
	JTextField jtfPostfix = new JTextField();  // for postix
	JTextField result = new JTextField("0");   // for result
	
	JButton[][] calcButton = new JButton[4][5];
	
	JPanel calcPanel = new JPanel();	
	JPanel topPanel = new JPanel();    

	
	public CalculatorFrame(){
		String[][] buttonText = 
				new String[][]{{"7","8","9","/","C"},{"4","5","6","*","B"},
				{"1","2","3","-","R"},{"0","(",")","+","="}};
				
		this.setTitle("Postfix Calculator");
		this.setLayout(new BorderLayout(2,1));

		jtfInfix.setHorizontalAlignment(JTextField.RIGHT);
		jtfPostfix.setHorizontalAlignment(JTextField.RIGHT);
		result.setHorizontalAlignment(JTextField.RIGHT);
		jtfPostfix.setEnabled(false);
		result.setEnabled(false);
		//jtfInfix.setEditable(false); // hide text caret
		
		// set the font size to 34 for the text fields
		Font textFieldFont=new Font(jtfPostfix.getFont().getName(),jtfPostfix.getFont().getStyle(),24);
		jtfInfix.setFont(textFieldFont);
		jtfPostfix.setFont(textFieldFont);
		result.setFont(textFieldFont);
		
		topPanel.setLayout(new GridLayout(3,1));				
		topPanel.add(jtfInfix);		
		topPanel.add(jtfPostfix);
		topPanel.add(result);
		
		calcPanel.setLayout(new GridLayout(4,5,3,3));
		
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {
				calcButton[i][j]= new JButton("" + buttonText[i][j]);
				calcButton[i][j].setForeground(Color.blue);
				calcButton[i][j].setFont(new Font("sansserif",Font.BOLD,42));
				calcButton[i][j].addActionListener(this);
				calcButton[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				calcPanel.add(calcButton[i][j]);
			}
		}
		this.add(topPanel,BorderLayout.NORTH);
		this.add(calcPanel,BorderLayout.CENTER);
	}
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 5; j++) {				
				if(e.getSource() == calcButton[i][j]){
					// clear
					if(i==0 && j == 4){
						jtfInfix.setText(null);
						jtfPostfix.setText(null);
						result.setText("0");
					}
					// backspace
					else if(i==1 && j == 4){
						if(jtfInfix.getDocument().getLength()>0)
							try {
								jtfInfix.setText(jtfInfix.getText(0, jtfInfix.getDocument().getLength()-1));
							} catch (BadLocationException e1) {
								e1.printStackTrace();
							}
						
					}
					// number or operator
					else if(j<4){
						jtfInfix.setText(jtfInfix.getText()
							+ calcButton[i][j].getText());
						}
					// = button pressed
					else if(i==3&&j==4){
						// erase contents of the postfix textfield
						jtfPostfix.setText(null);  
						// update the postfix textfield with the String returned  
						jtfPostfix.setText(infixToPostfix());
						// update the result textfield with the result of the computation
						result.setText("" + calculate());
					}
				}
			}
		}
    }

	/**
	 * infixToPostfix() 
	 * This method takes an infix expression and converts it into to a postfix
	 * 	expression. The expression is converted into tokens using StringTokenizer. StringTokenizer is
	 * 	set to true so operands and operators are considered tokens. Each token is then looked at
	 * 	separately in specific order to give precedence. The precedence order is number, left parenthesis,
	 * 	right parenthesis, multiply or division, and addition or subtraction.
	 * @return postFix is String which contains operators and operands. Strings are appendage to postFix 
	 * throughout the method because of the precedence.
	 */
	public String infixToPostfix(){		
		String postFix = "";
		String expression = jtfInfix.getText();
		String delims = "+-*/() ";
		StringTokenizer strToken = new StringTokenizer(expression, delims, true);
		String token = null;
		Stack<String> signs = new Stack<String>(expression.length());
		while(strToken.hasMoreTokens()){
			token = strToken.nextToken();
			//handles number
			if ((token.contains("0")) || (token.contains("1"))||(token.contains("2"))||(token.contains("3"))||
					(token.contains("4"))||(token.contains("5"))||(token.contains("6"))||(token.contains("7"))
						||(token.contains("8"))||(token.contains("9"))){
				postFix += token +" ";
			 //handles left parenthesis
			}else if (token.equals("(")){
				try{
					signs.push(token);
				}catch (StackFullException see){
					System.out.println("Stack is full");
				}
			//handles right parenthesis
			}else if (token.equals(")")){ 
				try{
					while(!signs.peek().equals("(")){
						postFix += signs.pop() + " ";
					}
					signs.pop();
				}catch (StackEmptyException see){
					System.out.println("Stack is empty");
				}
			//handles * or /
			}else if (token.equals("*") || (token.equals("/"))){ // multiplication & division
				try {
					signs.push(token);
				}catch (StackFullException see)	{
					System.out.println("Stack is full");
				}
			// handles + or -
			}
			else if(token.equals("+") || token.equals("-")){
				try{
					while (signs.peek().equals("*") || signs.peek().equals("/")){
						postFix += signs.pop() + " ";
					}
				}catch (StackEmptyException see){
					System.out.println("Stack is empty");
				}
				signs.push(token);
				}
			}
		while(!signs.isEmpty()){
			try {
				postFix += signs.pop();
			}catch (StackEmptyException see) {
				System.out.println("Stack is empty");
			}
		}
		return postFix;
	}
	
	/**
	 * calculate() 
	 * @return
	 */
	public String calculate() {
		String ans = "";
		String postfixStr = infixToPostfix();
		String delims = "+-*/() ";
		String tempToken;
		double operand1 = 0.0;
		double operand2 = 0.0;
		double total = 0.0;
		System.out.println(postfixStr);
		Stack<String> nums = new Stack<String>(postfixStr.length());
		StringTokenizer token = new StringTokenizer(postfixStr, delims, true);
		try{
		while(token.hasMoreTokens()){
			tempToken = token.nextToken();
			System.out.println(tempToken);
			if ((tempToken.contains("0")) || (tempToken.contains("1"))||(tempToken.contains("2"))||(tempToken.contains("3"))||
					(tempToken.contains("4"))||(tempToken.contains("5"))||(tempToken.contains("6"))||(tempToken.contains("7"))
					||(tempToken.contains("8"))||(tempToken.contains("9"))){
				nums.push(tempToken);
			} else if (tempToken.equals("/")){
				operand1 = Double.parseDouble(nums.pop());
				operand2 = Double.parseDouble(nums.pop());
				total = (operand2/operand1);
				nums.push(total+"");
			} else if (tempToken.equals("*")){
				operand1 = Double.parseDouble(nums.pop());
				operand2 = Double.parseDouble(nums.pop());
				total = (operand2*operand1);
				nums.push(total+"");
			} else if (tempToken.equals("+")){
				operand1 = Double.parseDouble(nums.pop());
				operand2 = Double.parseDouble(nums.pop());
				total = (operand2+operand1);
				nums.push(total+"");
			} else if (tempToken.equals("-")){
				operand1 = Double.parseDouble(nums.pop());
				operand2 = Double.parseDouble(nums.pop());
				total = (operand2-operand1);
				nums.push(total+"");
			}
		}
		System.out.println(nums.peek());
		ans = nums.pop();
		} catch (StackEmptyException see){
			System.out.println("StackEmpty");
		}
		return ans;
	}
	
	static final int MAX_WIDTH = 398, MAX_HEIGHT = 440;
	
	public static void main(String arg[]){
		JFrame frame = new CalculatorFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(MAX_WIDTH,MAX_HEIGHT);	
		frame.setBackground(Color.white);		
		frame.setResizable(false);				
		frame.setVisible(true);
	}
}
