package com.example.ruleengine.rule_engine.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ruleengine.rule_engine.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    // You can define custom query methods here if needed
}
