package jasmin.methods;

import jasmin.InstSingleton;
import jasmin.translation.TranslateCall;
import jasmin.translation.TranslateElement;
import org.specs.comp.ollir.*;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildOperand extends JasminMethod {

    Element lhs;
    HashMap<String, Descriptor> table;

    public BuildOperand(ClassUnit ollir, HashMap<String, Descriptor> table, Element lhs) {
        super(ollir);
        this.table = table;
        this.lhs = lhs;

    }

    public String getOperand(Instruction inst) {

        switch (inst.getInstType()) {
            case BINARYOPER -> addBinaryOper((BinaryOpInstruction) inst);
            case NOPER -> addNoOper((SingleOpInstruction) inst);
            case CALL -> addCall((CallInstruction) inst);
        }

        return super.toString();
    }

    public void addBinaryOper(BinaryOpInstruction inst) {
        Element leftElem = inst.getLeftOperand();
        Element rightElem = inst.getRightOperand();
        // a = b[i];

        String leftInstruction = TranslateElement.getJasminInst(leftElem, table);
        String rightInstruction = TranslateElement.getJasminInst(rightElem, table);

        methodString.append(leftInstruction);
        methodString.append(rightInstruction);

        methodString.append(InstSingleton.getOp(inst.getUnaryOperation().getOpType()));
    }

    public void addNoOper(SingleOpInstruction inst) {
        Element element = inst.getSingleOperand();
        methodString.append(TranslateElement.getJasminInst(element, table));
    }

    public void addCall(CallInstruction callInstruction){
        System.out.println(OllirAccesser.getCallInvocation(callInstruction));
        System.out.println(callInstruction.getFirstArg().toString());
        if (callInstruction.getSecondArg() != null)
            System.out.println(callInstruction.getSecondArg());
        System.out.println(callInstruction.getNumOperands());
        methodString.append(TranslateCall.getJasminInst(callInstruction, table));
    }


}