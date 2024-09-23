

# VIT C Week 6 Lab 1: Structure & Nested Structure

This repository contains solutions for four programming problems related to **structures**, **nested structures**, and **sorting** in C. The questions focus on practical applications like identifying Pythagorean triples, calculating employee salaries, sorting students based on marks, and managing intern details using nested structures.

---

## **Question 1: Pythagorean Triples**

### Problem Statement:
Ariana is interested in finding **Pythagorean triples**—three integers \(a\), \(b\), and \(c\) that satisfy the equation \( a^2 + b^2 = c^2 \). Write a program that checks if the input values form a Pythagorean triple and also check if it's a special triple (3, 4, 5 or 4, 3, 5).

### Code:
```c
#include <stdio.h>

// Structure to represent Pythagorean triple
struct Pythagorean {
    int a, b, c;
};

// Function to check if numbers form a Pythagorean triple
int is_pythagorean(int a, int b, int c) {
    return (a * a + b * b == c * c);
}

int main() {
    struct Pythagorean triple;
    
    // Input the values of a, b, and c
    scanf("%d %d %d", &triple.a, &triple.b, &triple.c);
    
    // Check if the values form a Pythagorean triple
    if (is_pythagorean(triple.a, triple.b, triple.c) || is_pythagorean(triple.b, triple.a, triple.c)) {
        printf("Pythagorean triple.\n");
        // Check if the triple is the special 3, 4, 5 or 4, 3, 5
        if ((triple.a == 3 && triple.b == 4 && triple.c == 5) || (triple.a == 4 && triple.b == 3 && triple.c == 5)) {
            printf("Special triple.\n");
        } else {
            printf("Not a special triple.\n");
        }
    } else {
        printf("Not a Pythagorean triple.\n");
    }
    
    return 0;
}
```

### Explanation:

1. **Structure Definition (`struct Pythagorean`)**: This structure holds the three integers \(a\), \(b\), and \(c\) that represent the sides of a triangle.
2. **is_pythagorean Function**: This function checks if the given values satisfy the Pythagorean theorem. The condition \(a^2 + b^2 == c^2\) is checked.
3. **Input and Main Function**:
   - The `main()` function takes three integer inputs.
   - It checks if the values form a Pythagorean triple by calling `is_pythagorean()` and compares if the sides match.
4. **Special Triple Check**: If the numbers are either (3, 4, 5) or (4, 3, 5), it is classified as a "Special triple."
   
**Concepts Used**:
- **Structures** to store values.
- **Functions** to simplify the logic of checking conditions.
- **Conditional statements** to evaluate whether the input values form a Pythagorean triple and whether it's a special one.

---

## **Question 2: Employee Salary Details**

### Problem Statement:
Rajesh wants to calculate the total income, bonus, and taxable income of an employee based on their base salary and other allowances. Implement a program to calculate these values and display the results.

### Code:
```c
#include <stdio.h>

// Structure to store income details
struct incomeInLPA {
    int base_salary, HRA, TA, DA;
};

// Structure to store employee details
struct employee {
    char name[30];
    int id;
    struct incomeInLPA income;
};

int main() {
    struct employee emp;
    int total_income, bonus, taxable_income;
    
    // Input employee details
    scanf("%s %d", emp.name, &emp.id);
    scanf("%d %d %d %d", &emp.income.base_salary, &emp.income.HRA, &emp.income.TA, &emp.income.DA);
    
    // Calculate total income, bonus, and taxable income
    total_income = emp.income.base_salary + emp.income.HRA + emp.income.TA + emp.income.DA;
    bonus = 0.1 * total_income;  // 10% bonus
    taxable_income = total_income - bonus;  // Taxable income calculation
    
    // Output results
    printf("Employee Name: %s\n", emp.name);
    printf("Employee ID: %d\n", emp.id);
    printf("Total Income: %d LPA\n", total_income);
    printf("Bonus: %d LPA\n", bonus);
    printf("Taxable Income: %d LPA\n", taxable_income);
    
    return 0;
}
```

### Explanation:

1. **Structure Definition (`struct incomeInLPA`)**: This structure holds income details like base salary, HRA (House Rent Allowance), TA (Travel Allowance), and DA (Dearness Allowance).
2. **Nested Structure (`struct employee`)**: This structure stores the employee's name, ID, and an instance of the `incomeInLPA` structure to group salary details.
3. **Input and Main Function**:
   - The user inputs the employee’s name, ID, base salary, and allowances.
   - Total income is calculated by adding all components (base salary, HRA, TA, and DA).
4. **Bonus and Taxable Income**:
   - The bonus is 10% of the total income.
   - The taxable income is calculated as the total income minus the bonus.
   
**Concepts Used**:
- **Nested structures** to represent an employee's salary and income components.
- **Basic arithmetic operations** for calculating the total income and taxable income.

---

## **Question 3: Sorting Students by Marks**

### Problem Statement:
Sort a list of students based on their marks in **descending order** and display the sorted list.

### Code:
```c
#include <stdio.h>

// Structure to store student information
struct student {
    char name[30];
    int marks;
};

int main() {
    int n;
    
    // Input number of students
    scanf("%d", &n);
    
    struct student students[n];
    
    // Input student details
    for (int i = 0; i < n; i++) {
        scanf("%s %d", students[i].name, &students[i].marks);
    }
    
    // Sort students by marks in descending order (using Bubble Sort)
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (students[j].marks < students[j + 1].marks) {
                // Swap the two students
                struct student temp = students[j];
                students[j] = students[j + 1];
                students[j + 1] = temp;
            }
        }
    }
    
    // Print sorted student list
    for (int i = 0; i < n; i++) {
        printf("%s %d\n", students[i].name, students[i].marks);
    }
    
    return 0;
}
```

### Explanation:

1. **Structure Definition (`struct student`)**: This structure holds two fields: the student's name and their marks.
2. **Input and Main Function**:
   - The program first takes the number of students as input and creates an array of `student` structures.
   - For each student, their name and marks are inputted.
3. **Sorting Logic (Bubble Sort)**:
   - The Bubble Sort algorithm is used to sort the array of students based on their marks in descending order.
   - If a student has fewer marks than the next student, their positions are swapped.
4. **Output**: The sorted list is displayed, showing each student’s name and their marks.
   
**Concepts Used**:
- **Structures** to store student information.
- **Sorting (Bubble Sort)** for arranging students in descending order.

---

## **Question 4: Intern Details Using Nested Structures**

### Problem Statement:
Karthik is managing intern records and needs to store details about each intern, such as their name, ID, job role, college CGPA, and school percentage. Implement a program that calculates the average of the intern’s CGPA and percentage and displays the details.

### Code:
```c
#include <stdio.h>

// Structure to store college details
struct College {
    float cgpa;
};

// Structure to store school details
struct School {
    float percentage;
};

// Structure to store intern details
struct Intern {
    char name[50];
    int intern_id;
    char job_role[30];
    struct College clg;
    struct School sch;
};

int main() {
    struct Intern intern;
    float average;
    
    // Input intern details
    scanf("%s %d %s", intern.name, &intern.intern_id, intern.job_role);
    scanf("%f %f", &intern.clg.cgpa, &intern.sch.percentage);
    
    // Calculate average of CGPA and percentage
    average = (intern.clg.cgpa + intern.sch.percentage) / 2.0;
    
    // Output intern

 details and average
    printf("Name: %s\n", intern.name);
    printf("Intern-id: %d\n", intern.intern_id);
    printf("Job-role: %s\n", intern.job_role);
    printf("Average: %.1f\n", average);
    
    return 0;
}
```

### Explanation:

1. **Nested Structures**:
   - **`struct College`**: Stores the intern’s CGPA.
   - **`struct School`**: Stores the intern’s school percentage.
   - **`struct Intern`**: Stores the intern’s name, ID, job role, and includes instances of `struct College` and `struct School`.
2. **Input and Output**:
   - The program takes the intern’s name, ID, job role, college CGPA, and school percentage as inputs.
   - It calculates the average of the CGPA and percentage.
3. **Display**: Finally, the intern’s details and the calculated average are displayed in a formatted output.
   
**Concepts Used**:
- **Nested structures** to represent the hierarchy of information (intern details, college, and school data).
- **Basic arithmetic operations** for calculating the average of CGPA and percentage.

