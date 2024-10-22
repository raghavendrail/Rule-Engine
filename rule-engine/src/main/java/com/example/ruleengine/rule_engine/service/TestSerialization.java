//package com.example.ruleengine.rule_engine.service;
//
//import com.example.ruleengine.rule_engine.util.ASTNode;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//public class TestSerialization {
//    public static void main(String[] args) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        // Create the ASTNode structure
//        ASTNode leftComparison = new ASTNode("operator", ">",
//            new ASTNode("operand", "salary"), new ASTNode("operand", "50000"));
//
//        ASTNode rightComparison = new ASTNode("operator", ">",
//            new ASTNode("operand", "experience"), new ASTNode("operand", "5"));
//
//        ASTNode root = new ASTNode("operator", "OR", leftComparison, rightComparison);
//
//        try {
//            String json = objectMapper.writeValueAsString(root);
//            System.out.println(json); // This should print the JSON representation
//        } catch (JsonProcessingException e) {
//            e.printStackTrace(); // Debugging information
//        }
//    }
//}
