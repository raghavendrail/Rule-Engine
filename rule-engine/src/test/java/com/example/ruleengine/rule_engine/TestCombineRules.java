package com.example.ruleengine.rule_engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestCombineRules {

    private final RuleEngine ruleEngine = new RuleEngine();

    @Test
    public void testCombineTwoRules() {
        String rule1 = "((age > 30 AND department = 'Sales'))";
        String rule2 = "((salary > 50000))";
        ASTNode combinedAST = ruleEngine.combineRules(new String[]{rule1, rule2});

        assertNotNull(combinedAST);
        assertEquals("operator", combinedAST.getType());
        assertEquals("AND", combinedAST.getValue());
        assertEquals("operator", combinedAST.getLeft().getType());
        assertEquals("operand", combinedAST.getRight().getType());
    }

    @Test
    public void testCombineMultipleRules() {
        String rule1 = "((age < 25 AND department = 'Marketing'))";
        String rule2 = "(experience > 5)";
        String rule3 = "(salary > 20000)";
        ASTNode combinedAST = ruleEngine.combineRules(new String[]{rule1, rule2, rule3});

        assertNotNull(combinedAST);
        // Check if the combined AST structure is valid
        assertEquals("operator", combinedAST.getType());
        assertEquals("AND", combinedAST.getValue());
        assertTrue(combinedAST.getLeft() instanceof ASTNode);
        assertTrue(combinedAST.getRight() instanceof ASTNode);
    }
}

