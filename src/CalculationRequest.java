
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class CalculationRequest implements Serializable{
   private int operand1,operand2;
   private char operator;

    public int getOperand1() {
        return operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
    }

    public void setOperator(char operator) throws Exception{
        if("+-/*%".contains(operator+""))
           this.operator = operator;
        else
            throw new Exception("Invalid Operator");
    }
   
   
    
}
