package compiler.analysis;

import compiler.core.*;
import compiler.exceptions.*;

import java.util.*;

public class SemanticImpl {

    private static SemanticImpl singleton;
    private static List<String> tempIdList;
    private static List<Parameter> tempParameters;
    private static String selectedId;
    private static ScopedEntityRepository scopedRepository;
    private static Expression selectedExp;
    public  static boolean isFunctionCall;

    private static void initCollections() {
        tempIdList = new ArrayList<String>();
        tempParameters = new ArrayList<Parameter>();
        scopedRepository = new ScopedEntityRepository();
    }

    public void addIdToTempList(String id) {
        tempIdList.add(id);
    }

    public void clearIdTempList() {
        tempIdList.clear();
    }

    public void addToTempParameter(Parameter param) {
        tempParameters.add(param);
    }

    public void clearParameterList() {
        tempParameters.clear();
    }

    public void createParameters(Type type) {
        for (int i = 0; i < tempIdList.size(); i++) {
            tempParameters.add(new DeclarationParameter(type, tempIdList.get(i)));
        }
    }

    public ArrayList<Parameter> getParameters() {
        return (ArrayList<Parameter>) tempParameters;
    }

    public static SemanticImpl getInstance() {
        if (singleton == null) {
            singleton = new SemanticImpl();
            initCollections();
        }

        return singleton;
    }

    public void addVariablesFromTempList(Type type) throws Exception {
        for (String identifier : tempIdList) {
            scopedRepository.addVariable(new Variable(type, identifier, false));
        }
        tempIdList.clear();
    }

    public void addVariable(Variable variable) throws InvalidNameException {
        scopedRepository.addVariable(variable);
    }

    public void addType(Type t) throws InvalidNameException {
        scopedRepository.addType(t);
    }

    public void validateFunction(String id) throws InvalidFunctionException {
        ((Function) scopedRepository.getFunctionOrProcedure(id)).validateReturnedType();

        exitCurrentScope();
    }

    public void setReturnType(String id, Type type) {
        Function f = ((Function) scopedRepository.getFunctionOrProcedure(id));
        f.setDeclaredReturnedType(type);

        if(!f.isProcedure()) {
            Variable v = new Variable(f.getDeclaredReturnType(), f.getName(), false);

            try {
                f.addVariable(v);
            } catch (InvalidNameException e) {
                e.printStackTrace();
            }
        }
    }

    public Expression getExpressionById(String id) throws InvalidVariableException {
        Type result;

        if (scopedRepository.existsVariable(id)) {
            result = scopedRepository.getVariable(id).getType();
        }

        else if (scopedRepository.existsFunctionOrProcedure(id)) {
            if (!scopedRepository.getFunctionOrProcedure(id).isProcedure())
                result = ((Function) scopedRepository.getFunctionOrProcedure(id)).getDeclaredReturnType();
            else
                result = new Type("void");
        }

        else {
            throw new InvalidVariableException("A variável " + id +" nunca foi declarada.");
        }

        return new Expression(result, id);
    }

    public void addFunctionProcedure(ScopedEntity f) throws InvalidNameException {
        List<Parameter> parametros = new ArrayList<Parameter>(f.getParams());
        f.setParams(parametros);

        scopedRepository.addFunctionOrProcedure(f);

    }

    public void exitCurrentScope() {
        if (!scopedRepository.isEmpty()) {
            scopedRepository.exitCurrentScope();
            tempIdList.clear();
            tempParameters.clear();
        }
    }

    public void setSelectedId(String id) throws InvalidVariableException {
        selectedId = id;

        checkVariableExistence(id);
    }


    public void setSelectedExp(Expression exp) {
        selectedExp = exp;
    }

    public Expression getSelectedExp() {
        return selectedExp;
    }

    public Expression checkOperation(String op, Expression exp) throws InvalidOperationException, InvalidTypeException {

        if(!(selectedExp.getType().isCompatible(exp.getType()) || exp.getType().isCompatible(selectedExp.getType()))){
            throw new InvalidOperationException("O operador '" +  op + "' não é compatível com os tipos: (" + selectedExp.getType().getName() + "," + exp.getType().getName() +")" );
        }

        if(isRelationalOp(op)){
            return new Expression(singleton.getTypeById("boolean"));
        } else {
            return new Expression(singleton.getTypeById(Type.resultantType(selectedExp.getType(),exp.getType())));
        }

    }

    public boolean isRelationalOp(String op) {
        List<String> operadores = new ArrayList<String>(){
            {
                add("<");
                add(">");
                add("=");
                add("<=");
                add(">=");
                add("<>");
            }
        };

        return operadores.contains(op);
    }

	public void checkVariableExistence(String id) throws InvalidVariableException {
		boolean result;
		result = scopedRepository.existsVariable(id);

        if(!result){
            result = scopedRepository.existsFunctionOrProcedure(id);
        }

        if(!result){
            throw new InvalidVariableException("O id: " + id +" nunca foi declarada.");
        }
	}

    public void checkFunctionCall() throws InvalidParameterException, InvalidFunctionException {
        System.out.println("!!! NO ID");
        selectedId = selectedId.toLowerCase();

        if (!scopedRepository.existsFunctionOrProcedure(selectedId))
            throw new InvalidFunctionException("A função '" + selectedId + "' não existe");

        List<Parameter> parametrosFuncao = scopedRepository.getFunctionOrProcedure(selectedId).getParams();
        List<Parameter> parametrosChamada = tempParameters;
        if (parametrosChamada.size() != parametrosFuncao.size())
            throw new InvalidParameterException("A quantidade de parâmetros da função "+selectedId+" está incorreta.");
        for (int i = 0 ; i < parametrosChamada.size(); i++) {
            if (!parametrosFuncao.get(i).equals(parametrosChamada.get(i))) {
                throw new InvalidParameterException("O parâmetro: '"+parametrosChamada.get(i)+" deveria ser do tipo "+parametrosFuncao.get(i).getType());
            }
        }

    };

    public boolean isFunctionOrProcedure(String id) {
        return scopedRepository.existsFunctionOrProcedure(id);
    }

    public Type getTypeById(String id) throws InvalidTypeException {
        return scopedRepository.getTypeById(id);
    }

	public boolean checkTypeOfAssignment(Expression exp)
			throws InvalidAssignmentException {

        if(scopedRepository.existsFunctionOrProcedure(selectedId)) {
            ScopedEntity s = scopedRepository.getFunctionOrProcedure(selectedId);
            if(!s.isProcedure()){
                ((Function) s).setHasReturn(true);
            }
        }

		Variable variable = scopedRepository.getVariable(selectedId);

		if (!variable.getType().isCompatible((exp.getType()))) {
			throw new InvalidAssignmentException("Atribuição inválida: (" + variable.getType().getName()  + ", " + exp.getType().getName() + ")");
		}
		return true;
	}

	public void checkRepeat(Expression exp) throws InvalidRepeatStatement {
        if (! exp.getType().getName().equalsIgnoreCase("boolean")){
            throw new InvalidRepeatStatement("A expression do repeat tem que retornar um boolean");
        }

    }

    public Variable getVariable() {
        return scopedRepository.getVariable(selectedId);
    }

    public Variable getVariable(String id) {
        return scopedRepository.getVariable(id);
    }
}
