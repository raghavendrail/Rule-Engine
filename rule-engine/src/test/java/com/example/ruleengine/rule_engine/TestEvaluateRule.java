package com.example.ruleengine.rule_engine;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TestEvaluateRule {

    private final RuleEngine ruleEngine = new RuleEngine();

    @Test
    public void testEvaluateTrueCondition() {
        String rule = "((age > 30 AND department = 'Sales') OR (salary > 50000))";
        ASTNode ast = ruleEngine.createRule(rule);

        Map<String, Object> data = new HashMap<>();
        data.put("age", 35);
        data.put("department", "Sales");
        data.put("salary", 60000);
        data.put("experience", 3);

        boolean result = ruleEngine.evaluateRule(ast, data);
        assertTrue(result); // Expecting true
    }

    @Test
    public void testEvaluateFalseCondition() {
        String rule = "((age < 25 AND department = 'Marketing') OR (salary > 50000))";
        ASTNode ast = ruleEngine.createRule(rule);

        Map<String, Object> data = new HashMap<>();
        data.put("age", 30);
        data.put("department", "Sales");
        data.put("salary", 40000);
        data.put("experience", 3);

        boolean result = ruleEngine.evaluateRule(ast, data);
        assertFalse(result); // Expecting false
    }

    @Test
    public void testEvaluateCombinedRules() {
        String rule = "((age > 30 AND department = 'Sales') OR (experience > 5)) AND (salary > 20000)";
        ASTNode ast = ruleEngine.createRule(rule);

        Map<String, Object> data = new HashMap<>();
        data.put("age", 35);
        data.put("department", "Sales");
        data.put("salary", 25000);
        data.put("experience", 6);

        boolean result = ruleEngine.evaluateRule(ast, data);
        assertTrue(result); // Expecting true
    }
}

