# Rule Engine with AST

A rule engine application that determines user eligibility based on attributes like age, department, income, and experience. The system utilizes an Abstract Syntax Tree (AST) to represent conditional rules, allowing for dynamic creation, combination, and modification of these rules.

## Table of Contents
- [Introduction](#introduction)
- [Objective](#objective)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Data Structure](#data-structure)
- [Data Storage](#data-storage)
- [API Design](#api-design)
- [Test Cases](#test-cases)
- [Bonus Features](#bonus-features)
- [Installation](#installation)
- [Usage](#usage)
- [Running the Application](#running-the-application)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)

## Introduction

This project implements a 3-tier architecture consisting of a simple user interface, an API for rule processing, and a backend for data management. The rule engine evaluates user eligibility based on defined rules using an AST structure.

## Objective

Develop a simple rule engine that dynamically manages eligibility rules based on various user attributes. The system supports creating and combining rules efficiently while allowing modifications to the rule set.

## Features
- Create and evaluate rules using an AST.
- Combine multiple rules into a single AST.
- Dynamic modification of existing rules.
- Comprehensive error handling for invalid inputs.
- Validation of user attributes against defined rules.

## Technologies Used
- **Java**
- **Spring Boot**
  **Postman**
- **Database**: MySQL (or another database, specify version)
- **Frontend**: HTML/CSS, JavaScript


## Data Structure

One data structure could be a Node with the following fields:
- `type`: String indicating the node type ("operator" for AND/OR, "operand" for conditions)
- `left`: Reference to another Node (left child)
- `right`: Reference to another Node (right child for operators)
- `value`: Optional value for operand nodes (e.g., number for comparisons)

## Data Storage
- Define the choice of database for storing the above rules and application metadata.
- Define the schema with samples.

### Sample Rules:
- **Rule 1**: `((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)`
- **Rule 2**: `((age > 30 AND department = 'Marketing')) AND (salary > 20000 OR experience > 5)`

## API Design
- `create_rule(rule_string)`: Takes a string representing a rule and returns a Node object representing the corresponding AST.
- `combine_rules(rules)`: Takes a list of rule strings and combines them into a single AST, minimizing redundant checks.
- `evaluate_rule(data)`: Evaluates the combined rule's AST against provided data attributes and returns a boolean indicating eligibility.

## Test Cases
- Create individual rules from examples using `create_rule` and verify their AST representation.
- Combine example rules using `combine_rules` and ensure the resulting AST reflects the combined logic.
- Implement sample JSON data and test `evaluate_rule` for various scenarios.
- Combine additional rules and test the functionality.

## Installation

### Prerequisites
- JDK 
- Maven 
- MySQL 

### Steps
1. Clone the repository:
    ```bash
    git clone <repository-url>
    ```
2. Navigate to the project directory:
    ```bash
    cd <project-directory>
    ```
3. Install dependencies:
    ```bash
    mvn install
    ```

## Usage
Instructions on how to use the application after installation, including example commands.

## Running the Application
To run the application locally, use the following command:
```bash
mvn spring-boot:run
