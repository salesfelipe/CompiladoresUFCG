package compiler.analysis;

import compiler.core.*;
import compiler.exceptions.InvalidFunctionException;
import compiler.exceptions.InvalidNameException;
import compiler.exceptions.InvalidParameterException;
import compiler.exceptions.InvalidVariableException;

import java.util.*;

public class SemanticImpl {

    private static SemanticImpl singleton;
    private static List<String> tempIdList;
    private static List<Parameter> tempParameters;
    private static String selectedId;
    private static ScopedEntityRepository scopedRepository;

    private static void initCollections() {
        tempIdList = new ArrayList<String>();
        tempParameters = new ArrayList<Parameter>();
        scopedRepository = ScopedEntityRepository.getInstance();
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

    public void validateFunction(Expression e, String id) throws InvalidFunctionException {
        ((Function) scopedRepository.getFunctionOrProcedure(id)).validateReturnedType(e.getType());

        exitCurrentScope();
    }

    public void setReturnType(String id, Type type) {
        ((Function) scopedRepository.getFunctionOrProcedure(id)).setDeclaredReturnedType(type);
    }

    public Expression getTypeByID(String id) throws InvalidVariableException {
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

        boolean result = false;

        result = scopedRepository.existsVariable(id);

        if(!result){
            result = scopedRepository.existsFunctionOrProcedure(id);
        }

        if(!result){
            throw new InvalidVariableException("O id: " + id +" nunca foi declarada.");
        }
    }

    public void checkFunctionCall() throws InvalidParameterException, InvalidFunctionException {
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

}
