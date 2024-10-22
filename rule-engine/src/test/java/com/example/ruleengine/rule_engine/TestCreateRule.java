package com.example.ruleengine.rule_engine;

import org.junit.jupiter.api.Test;

import com.example.ruleengine.rule_engine.util.ASTNode;

import static org.junit.jupiter.api.Assertions.*;

public class TestCreateRule {

    private final RuleEngine ruleEngine = new RuleEngine();

    @Test
    public void testCreateIndividualRule1() {
        String rule = "((age > 30 AND department = 'Sales'))";
        ASTNode ast = ruleEngine.createRule(rule);
        assertNotNull(ast);
        assertEquals("operator", ast.getType());
        assertEquals("AND", ast.getValue());
        assertEquals("operand", ast.getLeft().getType());
        assertEquals("operand", ast.getRight().getType());
    }

    @Test
    public void testCreateIndividualRule2() {
        String rule = "((age < 25 AND department = 'Marketing'))";
        ASTNode ast = ruleEngine.createRule(rule);
        assertNotNull(ast);
        assertEquals("operator", ast.getType());
        assertEquals("AND", ast.getValue());
        assertEquals("operand", ast.getLeft().getType());
        assertEquals("operand", ast.getRight().getType());
    }

    @Test
    public void testCreateIndividualRule3() {
        String rule = "(salary > 50000 OR experience > 5)";
        ASTNode ast = ruleEngine.createRule(rule);
        assertNotNull(ast);
        assertEquals("operator", ast.getType());
        assertEquals("OR", ast.getValue());
        assertEquals("operand", ast.getLeft().getType());
        assertEquals("operand", ast.getRight().getType());
    }
}
