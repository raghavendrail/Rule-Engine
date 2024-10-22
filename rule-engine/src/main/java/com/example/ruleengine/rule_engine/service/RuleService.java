package com.example.ruleengine.rule_engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ruleengine.rule_engine.model.Rule;
import com.example.ruleengine.rule_engine.repository.RuleRepository;
import com.example.ruleengine.rule_engine.util.ASTNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Rule createRule(String ruleString) {
        ASTNode ast = parseRuleString(ruleString);
        Rule rule = new Rule();
        rule.setRuleString(ruleString);
        rule.setAst(astToJson(ast)); // Convert AST to JSON
        return ruleRepository.save(rule);
    }

    public static ASTNode parseRuleString(String rule) {
        return parseRule(rule.trim().replaceAll("^\\(|\\)$", ""));
    }

    public static ASTNode parseRule(String rule) {
        String[] orParts = splitByOperator(rule, "OR");
        if (orParts.length == 1) {
            return parseExpression(orParts[0].trim());
        }

        ASTNode left = parseExpression(orParts[0].trim());
        ASTNode right = parseRule(orParts[1].trim());
        return new ASTNode("operator", "OR", left, right);
    }

    private static ASTNode parseExpression(String expr) {
        if (expr.startsWith("(") && expr.endsWith(")")) {
            return parseRule(expr);
        }

        String[] andParts = splitByOperator(expr, "AND");
        if (andParts.length == 1) {
            return parseRelational(andParts[0].trim());
        }

        ASTNode left = parseRelational(andParts[0].trim());
        ASTNode right = parseExpression(andParts[1].trim());
        return new ASTNode("operator", "AND", left, right);
    }

    private static ASTNode parseRelational(String expr) {
        String[] parts;

        if ((parts = expr.split("=", 2)).length == 2) {
            return new ASTNode("operator", "=", new ASTNode("operand", parts[0].trim(), null, null), new ASTNode("operand", parts[1].trim(), null, null));
        }

        if ((parts = expr.split(">", 2)).length == 2) {
            return new ASTNode("operator", ">", new ASTNode("operand", parts[0].trim(), null, null), new ASTNode("operand", parts[1].trim(), null, null));
        }

        if ((parts = expr.split("<", 2)).length == 2) {
            return new ASTNode("operator", "<", new ASTNode("operand", parts[0].trim(), null, null), new ASTNode("operand", parts[1].trim(), null, null));
        }

        throw new IllegalArgumentException("Invalid expression: " + expr);
    }

    private static String[] splitByOperator(String rule, String operator) {
        List<String> parts = new ArrayList<>();
        int depth = 0;
        StringBuilder currentPart = new StringBuilder();

        for (int i = 0; i < rule.length(); i++) {
            char c = rule.charAt(i);
            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
            } else if (depth == 0 && rule.startsWith(operator, i)) {
                parts.add(currentPart.toString().trim());
                currentPart.setLength(0); // Clear the current part
                i += operator.length() - 1; // Move past the operator
                continue;
            }
            currentPart.append(c);
        }

        if (currentPart.length() > 0) {
            parts.add(currentPart.toString().trim());
        }

        return parts.toArray(new String[0]);
    }

    public String astToJson(ASTNode ast) {
        try {
            return objectMapper.writeValueAsString(ast);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting AST to JSON", e);
        }
    }

    public Rule combineRules(List<Long> ruleIds) {
        List<Rule> rules = ruleRepository.findAllById(ruleIds);
        if (rules.isEmpty()) {
            throw new IllegalArgumentException("No rules found with the given IDs.");
        }

        String combinedRuleString = String.join(" AND ", rules.stream().map(Rule::getRuleString).toList());
        return createRule(combinedRuleString); // Reuse createRule to save the combined rule
    }

    public boolean evaluateRule(Long ruleId, Map<String, Object> data) {
        // Fetch the rule from the database using ruleId
        Rule rule = ruleRepository.findById(ruleId)
                .orElseThrow(() -> new RuleNotFoundException("Rule not found"));

        // Convert AST JSON to ASTNode object
        ASTNode ast = jsonToAst(rule.getAst());

        if (ast == null) {
            throw new NullPointerException("AST is null for ruleId: " + ruleId);
        }

        // Proceed with the evaluation logic
        return evaluate(ast, data);
    }


    public ASTNode jsonToAst(String jsonAst) {
        try {
            return objectMapper.readValue(jsonAst, ASTNode.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean evaluate(ASTNode node, Map<String, Object> data) {
        if (node.getValue() == null) {
            // It's an operand (leaf node)
            return data.containsKey(node.getRight()) && data.get(node.getRight()) instanceof Number;
        }

        boolean leftValue = (node.getLeft() instanceof ASTNode)
                ? evaluate((ASTNode) node.getLeft(), data)
                : data.get(((ASTNode) node.getLeft()).getRight()) instanceof Number;

        boolean rightValue = (node.getRight() instanceof ASTNode)
                ? evaluate((ASTNode) node.getRight(), data)
                : data.get(((ASTNode) node.getRight()).getRight()) instanceof Number;

        switch (node.getValue()) {
            case "AND":
                return leftValue && rightValue;
            case "OR":
                return leftValue || rightValue;
            case ">":
                return compareNumbers(data.get(((ASTNode) node.getLeft()).getRight()), data.get(((ASTNode) node.getRight()).getRight()), ">");
            case "<":
                return compareNumbers(data.get(((ASTNode) node.getLeft()).getRight()), data.get(((ASTNode) node.getRight()).getRight()), "<");
            case "=":
                return data.get(((ASTNode) node.getLeft()).getRight()).equals(data.get(((ASTNode) node.getRight()).getRight()));
            default:
                throw new IllegalArgumentException("Unknown operator: " + node.getValue());
        }
    }


    private static boolean compareNumbers(Object left, Object right, String operator) {
        if (left instanceof Number && right instanceof Number) {
            double leftValue = ((Number) left).doubleValue();
            double rightValue = ((Number) right).doubleValue();
            return switch (operator) {
                case ">" -> leftValue > rightValue;
                case "<" -> leftValue < rightValue;
                default -> false;
            };
        }
        return false;
    }

   


    private ASTNode convertToASTNode(Object node) {
        if (node instanceof Map) {
            // Use ObjectMapper to convert Map to ASTNode
            return objectMapper.convertValue(node, ASTNode.class);
        }
        throw new IllegalArgumentException("Expected a Map for ASTNode but got: " + node.getClass());
    }


}
