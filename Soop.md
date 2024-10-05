

# **VIT_C_Week 7 Lab 2: Classes and Objects**

## **Problem 1: Character Occurrence Counter**

### **Problem Summary:**
You need to help Sam, who is working on a text analysis tool. Sam wants a class `Occurrence` that will take a string and a specific character as input and count how many times that character appears in the string.

### **Concepts Involved:**
- **Classes and Objects:** A class is a blueprint for creating objects. It can hold data (attributes) and functions (methods).
- **String Manipulation:** We need to iterate over the string and count specific character occurrences.
- **Input/Output Handling:** The user provides a string and a character, and the program should output the count of the character in the string.

### **Code Explanation:**
1. **Class Definition:** A class `Occurrence` is defined with attributes `str` (for the string) and `ch` (for the character).
2. **Initialization:** A method `initialize` is provided to set the values of the string and character.
3. **Counting Method:** The method `countOccurrences` iterates through the string and counts the occurrences of the character `ch`.
4. **Main Function:** The `main` function accepts input, initializes the class, and prints the result of the `countOccurrences` method.

```c
#include <stdio.h>
#include <string.h>

class Occurrence {
    public:
    char str[100];
    char ch;

    void initialize(char string[], char character) {
        strcpy(str, string);
        ch = character;
    }

    int countOccurrences() {
        int count = 0;
        for(int i = 0; i < strlen(str); i++) {
            if(str[i] == ch) {
                count++;
            }
        }
        return count;
    }
};

int main() {
    Occurrence occ;
    char string[100], character;

    scanf("%s", string);
    scanf(" %c", &character);

    occ.initialize(string, character);

    printf("%d\n", occ.countOccurrences());

    return 0;
}
```

### **Concept Summary:**
- **Classes** are used to group related data and functions.
- **String manipulation** through loops allows character comparison.
- **Modular programming** allows each function to have a specific task (initialization, counting, etc.).

---

## **Problem 2: Basic Arithmetic Operations**

### **Problem Summary:**
Venu needs help in writing a program that takes two integers and performs basic arithmetic operations (addition, subtraction, multiplication, division, modulus). The program should use a class `Operation` with the two integers as attributes.

### **Concepts Involved:**
- **Basic Arithmetic Operations:** Addition, subtraction, multiplication, division, and modulus.
- **Conditional Statements:** Handling the division by zero scenario.
- **Classes and Methods:** Attributes and methods inside a class to perform operations on the input numbers.

### **Code Explanation:**
1. **Class Definition:** A class `Operation` is defined with two public attributes `a` and `b`.
2. **Method `initialize`:** This method is used to set the values of `a` and `b`.
3. **Method `performOperations`:** This method performs all the operations (addition, subtraction, multiplication, etc.) and checks if `b` is zero before performing division and modulus.
4. **Main Function:** The `main` function accepts input, initializes the class, and calls `performOperations` to display the results.

```c
#include <stdio.h>

class Operation {
    public:
    int a, b;

    void initialize(int x, int y) {
        a = x;
        b = y;
    }

    void performOperations() {
        printf("Addition: %d\n", a + b);
        printf("Difference: %d\n", a - b);
        printf("Product: %d\n", a * b);
        
        if(b == 0) {
            printf("Division by zero not possible\n");
        } else {
            printf("Division: %d\n", a / b);
            printf("Modulus: %d\n", a % b);
        }
    }
};

int main() {
    Operation op;
    int a, b;

    scanf("%d %d", &a, &b);

    op.initialize(a, b);
    op.performOperations();

    return 0;
}
```

### **Concept Summary:**
- **Arithmetic operators** (+, -, *, /, %) are used to perform basic mathematical operations.
- **Classes** help encapsulate the two numbers (`a` and `b`) and the operations performed on them.
- **Conditional logic** is used to handle division by zero scenarios.
  
---

## **Problem 3: Item Type Class**

### **Problem Summary:**
In this problem, you are tasked with creating a class `ItemType` that stores information about an item including its name, deposit, and cost per day. The program should include getters, setters, and a method to display the entered values in a specific format.

### **Concepts Involved:**
- **Encapsulation:** Using private attributes in a class and providing getter and setter methods to access and modify them.
- **Class Methods:** Using methods to set values (`setName`, `setDeposit`, `setCostPerDay`) and to display them (`display`).
- **Data Formatting:** Ensuring the correct output format.

### **Code Explanation:**
1. **Class Definition:** The class `ItemType` has three private attributes: `name` (string), `deposit` (double), and `costPerDay` (double).
2. **Getters and Setters:** Methods to set and get the values of the attributes.
3. **Display Method:** The `display` method prints the values of the attributes in the required format.
4. **Main Function:** The `main` function gets the input values from the user, sets the attributes using setters, and displays them using the `display` method.

```c
#include <stdio.h>
#include <string.h>

class ItemType {
    private:
    char name[100];
    double deposit;
    double costPerDay;

    public:
    void setName(char itemName[]) {
        strcpy(name, itemName);
    }

    void setDeposit(double itemDeposit) {
        deposit = itemDeposit;
    }

    void setCostPerDay(double itemCostPerDay) {
        costPerDay = itemCostPerDay;
    }

    char* getName() {
        return name;
    }

    double getDeposit() {
        return deposit;
    }

    double getCostPerDay() {
        return costPerDay;
    }

    void display() {
        printf("Name : %s\n", name);
        printf("Deposit Amount : %.0lf\n", deposit);
        printf("Cost per day : %.0lf\n", costPerDay);
    }
};

int main() {
    ItemType item;
    char name[100];
    double deposit, costPerDay;

    scanf("%s", name);
    scanf("%lf", &deposit);
    scanf("%lf", &costPerDay);

    item.setName(name);
    item.setDeposit(deposit);
    item.setCostPerDay(costPerDay);

    item.display();

    return 0;
}
```

### **Concept Summary:**
- **Encapsulation** ensures that object attributes are hidden and can only be accessed or modified via methods (getters/setters).
- **Data formatting** ensures that output is correctly formatted and meets the problem’s requirements.
  
---

