/*
 * generated by Xtext 2.25.0
 */
package ca.uottawa.csmlab.symboleo.validation;

import java.util.HashSet;
import java.util.Set;


import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import ca.uottawa.csmlab.symboleo.symboleo.BaseType;
import ca.uottawa.csmlab.symboleo.symboleo.DomainType;
import ca.uottawa.csmlab.symboleo.symboleo.Event;
import ca.uottawa.csmlab.symboleo.symboleo.Model;
import ca.uottawa.csmlab.symboleo.symboleo.NumericExpressionParameter;
import ca.uottawa.csmlab.symboleo.symboleo.Obligation;
import ca.uottawa.csmlab.symboleo.symboleo.OntologyType;
import ca.uottawa.csmlab.symboleo.symboleo.Parameter;
import ca.uottawa.csmlab.symboleo.symboleo.Power;
import ca.uottawa.csmlab.symboleo.symboleo.Regular;
import ca.uottawa.csmlab.symboleo.symboleo.StringExpressionParameter;
import ca.uottawa.csmlab.symboleo.symboleo.SymboleoPackage;
import ca.uottawa.csmlab.symboleo.symboleo.Variable;
import ca.uottawa.csmlab.symboleo.symboleo.VariableEvent;

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class SymboleoValidator extends AbstractSymboleoValidator {
	
  
	@Check(CheckType.FAST)
	public void checkDomainTypesStartsWithCapital(DomainType type) {
		if (!Character.isUpperCase(type.getName().charAt(0))) {
			error("Domain types should start with a capital letter", type, SymboleoPackage.Literals.DOMAIN_TYPE__NAME);
		}
	}
  
  /*
   * Identifiers should be unique
   */
  @Check(CheckType.FAST)
  public void checkIdentifiersAreUnique(Model model) {
    Set<String> identifiers = new HashSet<>();
    
    for(DomainType x: model.getDomainTypes()) {
      if(identifiers.contains(x.getName())) {
        error("Duplicate identifier " + x.getName(), x, SymboleoPackage.Literals.DOMAIN_TYPE__NAME);
      }
      identifiers.add(x.getName());
    }
    
    for(Parameter x: model.getParameters()) {
      if(identifiers.contains(x.getName())) {
        error("Duplicate identifier " + x.getName(), x, SymboleoPackage.Literals.PARAMETER__NAME);
      }
      identifiers.add(x.getName());
    }
    
    for(Variable x: model.getVariables()) {
      if(identifiers.contains(x.getName())) {
        error("Duplicate identifier " + x.getName(), x, SymboleoPackage.Literals.VARIABLE__NAME);
      }
      identifiers.add(x.getName());
    }
    
    for(Obligation x: model.getObligations()) {
      if(identifiers.contains(x.getName())) {
        error("Duplicate identifier " + x.getName(), x, SymboleoPackage.Literals.OBLIGATION__NAME);
      }
      identifiers.add(x.getName());
    }
    
    for(Obligation x: model.getSurvivingObligations()) {
      if(identifiers.contains(x.getName())) {
        error("Duplicate identifier " + x.getName(), x, SymboleoPackage.Literals.OBLIGATION__NAME);
      }
      identifiers.add(x.getName());
    }
    
    for(Power x: model.getPowers()) {
      if(identifiers.contains(x.getName())) {
        error("Duplicate identifier " + x.getName(), x, SymboleoPackage.Literals.POWER__NAME);
      }
      identifiers.add(x.getName());
    }
  }
  
  @Check(CheckType.FAST)
  public void checkvariableEventsType(VariableEvent event) {    
    if (event.getVariable() == null) {
      error("only variable of type event is allowed", event, SymboleoPackage.Literals.VARIABLE_EVENT__VARIABLE);
      return;
    }
    
    Regular currentType = event.getVariable().getType();
    while(true) {
      if(currentType.getRegularType() == null) {
        break;
      }
      currentType = currentType.getRegularType();
    }
    
    if (!currentType.getOntologyType().getName().equalsIgnoreCase("event")) {
      error("only variable of type event is allowed", event, SymboleoPackage.Literals.VARIABLE_EVENT__VARIABLE);
    }
  }
  
  @Check(CheckType.FAST)
  public void checkNumericExpressionParameterType(NumericExpressionParameter np) {
    if (np.getValue().getType().getBaseType() == null || !np.getValue().getType().getBaseType().getName().equalsIgnoreCase("number")) {
      error("Should be of type number", np, SymboleoPackage.Literals.NUMERIC_EXPRESSION_PARAMETER__VALUE);
    }
  }

  @Check(CheckType.FAST)
  public void checkStringExpressionParameterType(StringExpressionParameter np) {
    if (np.getParameter().getType().getBaseType() == null || !np.getParameter().getType().getBaseType().getName().equalsIgnoreCase("string")) {
      error("Should be of type string", np, SymboleoPackage.Literals.STRING_EXPRESSION_PARAMETER__PARAMETER);
    }
  }
	
}
