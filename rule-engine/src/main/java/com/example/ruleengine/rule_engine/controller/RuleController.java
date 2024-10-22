package com.example.ruleengine.rule_engine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ruleengine.rule_engine.model.Rule;
import com.example.ruleengine.rule_engine.service.RuleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rules")
public class RuleController {
    @Autowired
    private RuleService ruleService;

    @PostMapping("/create_rule")
    public ResponseEntity<Rule> createRule(@RequestBody Map<String, String> request) {
        String ruleString = request.get("rule_string");
        Rule rule = ruleService.createRule(ruleString);
        return ResponseEntity.ok(rule);
    }

    @PostMapping("/combine_rules")
    public ResponseEntity<Rule> combineRules(@RequestBody Map<String, List<Long>> request) {
        List<Long> ruleIds = request.get("rule_ids");
        Rule combinedRule = ruleService.combineRules(ruleIds);
        return ResponseEntity.ok(combinedRule);
    }

    @PostMapping("/evaluate_rule")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody Map<String, Object> request) {
        Long ruleId = Long.valueOf(request.get("rule_id").toString());
        Map<String, Object> data = (Map<String, Object>) request.get("data");
        boolean result = ruleService.evaluateRule(ruleId, data);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/modify_rule")
    public ResponseEntity<Rule> modifyRule(@RequestBody Map<String, Object> request) {
        Long ruleId = Long.valueOf(request.get("rule_id").toString());
        String newRuleString = request.get("new_rule_string").toString();
        Rule modifiedRule = ruleService.modifyRule(ruleId, newRuleString);
        return ResponseEntity.ok(modifiedRule);
    }


}
