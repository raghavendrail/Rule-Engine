package com.example.ruleengine.rule_engine.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ASTNode {
	  @JsonProperty("type")
	String type;
	  @JsonProperty("value")
    String value;
	  @JsonProperty("left")
    Object left;
	  @JsonProperty("right")
    Object right;
	  
	  public ASTNode() {
	    }

    public ASTNode(String type, String value, Object left, Object right) {
        this.type = type;
        this.value= value;
        this.left = left;
        this.right = right;
    }
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Object getLeft() {
		return left;
	}
	public void setLeft(Object left) {
		this.left = left;
	}
	public Object getRight() {
		return right;
	}
	public void setRight(Object right) {
		this.right = right;
	}
	public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null; // No left child
        this.right = null;
    }
    @Override
    public String toString() {
        if (left == null && right == null) {
            return "ASTNode(\"" + type + "\", \"" + value + "\")";
        }
        return "ASTNode(\"" + type + "\", \"" + value + "\", " + left + ", " + right + ")";
    }
}
