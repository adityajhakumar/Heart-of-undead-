Here are exam-oriented programming questions for the specified topics in Object-Oriented Programming and Inheritance and Polymorphism, along with solutions that take user input.

### Module 4: Object-Oriented Programming

#### 1. Friend Functions
**Question:**  
Create two classes, `Person` and `Employee`. The `Employee` class should have a friend function that can access the private attributes of both classes. The `Person` class should store a name and age, while the `Employee` class should store an employee ID and salary. Implement a function to display the details of a person and their employee information.

**Solution:**
```cpp
#include <iostream>
#include <string>
using namespace std;

class Person;

class Employee {
private:
    int employeeID;
    float salary;

public:
    Employee(int id, float sal) : employeeID(id), salary(sal) {}

    friend void displayDetails(Person p, Employee e);
};

class Person {
private:
    string name;
    int age;

public:
    Person(string n, int a) : name(n), age(a) {}

    friend void displayDetails(Person p, Employee e);
};

void displayDetails(Person p, Employee e) {
    cout << "Name: " << p.name << ", Age: " << p.age << endl;
    cout << "Employee ID: " << e.employeeID << ", Salary: " << e.salary << endl;
}

int main() {
    string name;
    int age, employeeID;
    float salary;

    cout << "Enter Name: ";
    getline(cin, name);
    cout << "Enter Age: ";
    cin >> age;
    cout << "Enter Employee ID: ";
    cin >> employeeID;
    cout << "Enter Salary: ";
    cin >> salary;

    Person person(name, age);
    Employee employee(employeeID, salary);

    displayDetails(person, employee);

    return 0;
}
```

#### 2. Functions with Default Arguments
**Question:**  
Write a class `Rectangle` that calculates the area of a rectangle. It should have a member function that takes the length and breadth as parameters, with default values. If the user does not provide values, the rectangle should default to a square with sides of length 1.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class Rectangle {
public:
    float area(float length = 1.0, float breadth = 1.0) {
        return length * breadth;
    }
};

int main() {
    Rectangle rect;
    float length, breadth;

    cout << "Enter length (or press enter to use default 1): ";
    if (!(cin >> length)) {
        length = 1.0;
        cin.clear();  // clear the error flag
        cin.ignore(numeric_limits<streamsize>::max(), '\n');  // discard invalid input
    }

    cout << "Enter breadth (or press enter to use default 1): ";
    if (!(cin >> breadth)) {
        breadth = 1.0;
    }

    cout << "Area of Rectangle: " << rect.area(length, breadth) << endl;

    return 0;
}
```

#### 3. Functions with Objects as Arguments
**Question:**  
Design a class `Circle` that contains a radius and a member function to calculate the area. Write another class `Cylinder` that has a `Circle` object as a member. Implement a member function in `Cylinder` that calculates the volume using the radius from the `Circle` object and the height provided by the user.

**Solution:**
```cpp
#include <iostream>
#include <cmath>
using namespace std;

class Circle {
private:
    float radius;

public:
    Circle(float r) : radius(r) {}

    float getRadius() const {
        return radius;
    }
};

class Cylinder {
private:
    Circle circle;

public:
    Cylinder(float radius) : circle(radius) {}

    float calculateVolume(float height) {
        return M_PI * pow(circle.getRadius(), 2) * height;
    }
};

int main() {
    float radius, height;

    cout << "Enter radius of the Circle: ";
    cin >> radius;
    cout << "Enter height of the Cylinder: ";
    cin >> height;

    Cylinder cylinder(radius);
    cout << "Volume of Cylinder: " << cylinder.calculateVolume(height) << endl;

    return 0;
}
```

#### 4. Friend Functions and Friend Classes
**Question:**  
Create a class `BankAccount` with private attributes for account number and balance. Create a friend class `AccountManager` that can access these private attributes and implement a function to deposit money and check the account balance.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class BankAccount;

class AccountManager {
public:
    void deposit(BankAccount &account, float amount);
    void displayBalance(BankAccount &account);
};

class BankAccount {
private:
    string accountNumber;
    float balance;

public:
    BankAccount(string accNum, float initialBalance) : accountNumber(accNum), balance(initialBalance) {}

    friend class AccountManager;
};

void AccountManager::deposit(BankAccount &account, float amount) {
    account.balance += amount;
    cout << "Deposited: " << amount << " to account: " << account.accountNumber << endl;
}

void AccountManager::displayBalance(BankAccount &account) {
    cout << "Account Number: " << account.accountNumber << ", Balance: " << account.balance << endl;
}

int main() {
    string accountNumber;
    float initialBalance, depositAmount;

    cout << "Enter Account Number: ";
    cin >> accountNumber;
    cout << "Enter Initial Balance: ";
    cin >> initialBalance;

    BankAccount account(accountNumber, initialBalance);
    AccountManager manager;

    cout << "Enter Deposit Amount: ";
    cin >> depositAmount;
    manager.deposit(account, depositAmount);

    manager.displayBalance(account);

    return 0;
}
```

#### 5. Dynamic Memory Allocation
**Question:**  
Create a class `DynamicArray` that can store a dynamic array of integers. It should have methods to add elements, display the array, and delete the array when done. Use dynamic memory allocation to manage the array.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class DynamicArray {
private:
    int *arr;
    int size;

public:
    DynamicArray(int s) : size(s) {
        arr = new int[size]; // Dynamic allocation
    }

    void addElements() {
        cout << "Enter " << size << " elements:" << endl;
        for (int i = 0; i < size; i++) {
            cout << "Element " << (i + 1) << ": ";
            cin >> arr[i];
        }
    }

    void display() {
        cout << "Array elements: ";
        for (int i = 0; i < size; i++) {
            cout << arr[i] << " ";
        }
        cout << endl;
    }

    ~DynamicArray() {
        delete[] arr; // Free memory
    }
};

int main() {
    int size;

    cout << "Enter size of the dynamic array: ";
    cin >> size;

    DynamicArray dynamicArray(size);
    dynamicArray.addElements();
    dynamicArray.display();

    return 0;
}
```

### Module 5: Inheritance and Polymorphism

#### 1. Types of Inheritance: Single Inheritance
**Question:**  
Design a base class `Vehicle` with attributes for `make` and `model`. Create a derived class `Car` that adds an attribute for `fuelType`. Implement a method to display the details of both the vehicle and the car.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class Vehicle {
protected:
    string make;
    string model;

public:
    Vehicle(string mk, string mdl) : make(mk), model(mdl) {}
};

class Car : public Vehicle {
private:
    string fuelType;

public:
    Car(string mk, string mdl, string fuel) : Vehicle(mk, mdl), fuelType(fuel) {}

    void displayDetails() {
        cout << "Make: " << make << ", Model: " << model << ", Fuel Type: " << fuelType << endl;
    }
};

int main() {
    string make, model, fuelType;

    cout << "Enter Vehicle Make: ";
    cin >> make;
    cout << "Enter Vehicle Model: ";
    cin >> model;
    cout << "Enter Fuel Type: ";
    cin >> fuelType;

    Car car(make, model, fuelType);
    car.displayDetails();

    return 0;
}
```

#### 2. Multiple Inheritance
**Question:**  
Create two base classes, `Student` and `Sports`. The `Student` class should contain details like `name` and `roll number`, while the `Sports` class should have a method to display sports played. Derive a class `Athlete` from both classes and implement a method to display all the details.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class Student {
protected:
    string name;
    int rollNumber;

public:
    Student(string n, int r) : name(n), rollNumber(r) {}
};

class Sports {
public:
    void displaySports() {
        cout << "Sports Played: Basketball" << endl; // Example sports
    }
};

class Athlete : public Student, public Sports {
public:
    Athlete(string n, int r) : Student(n, r) {}

    void displayDetails() {
        cout << "Name: " << name << ", Roll Number: " << rollNumber << endl;
        displaySports();
    }
};

int main() {
    string name;
    int rollNumber;

    cout << "Enter Student

 Name: ";
    cin >> name;
    cout << "Enter Roll Number: ";
    cin >> rollNumber;

    Athlete athlete(name, rollNumber);
    athlete.displayDetails();

    return 0;
}
```

#### 3. Multi-level Inheritance
**Question:**  
Create a base class `Shape` with a method to calculate area. Derive a class `Rectangle` that overrides the method to calculate the area of a rectangle, and then derive another class `Square` from `Rectangle` that uses the overridden method to calculate the area of a square.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class Shape {
public:
    virtual float area() = 0; // Pure virtual function
};

class Rectangle : public Shape {
protected:
    float length, breadth;

public:
    Rectangle(float l, float b) : length(l), breadth(b) {}

    float area() override {
        return length * breadth;
    }
};

class Square : public Rectangle {
public:
    Square(float side) : Rectangle(side, side) {}

    float area() override {
        return Rectangle::area(); // Call Rectangle's area
    }
};

int main() {
    float length, breadth;

    cout << "Enter Length of Rectangle: ";
    cin >> length;
    cout << "Enter Breadth of Rectangle: ";
    cin >> breadth;

    Rectangle rectangle(length, breadth);
    cout << "Area of Rectangle: " << rectangle.area() << endl;

    float side;
    cout << "Enter Side of Square: ";
    cin >> side;

    Square square(side);
    cout << "Area of Square: " << square.area() << endl;

    return 0;
}
```

#### 4. Inheritance and Constructors
**Question:**  
Implement a base class `Person` that has a name and age. Create a derived class `Student` that adds a student ID. Ensure the constructor of `Student` calls the constructor of `Person` to initialize the name and age.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class Person {
protected:
    string name;
    int age;

public:
    Person(string n, int a) : name(n), age(a) {}
};

class Student : public Person {
private:
    string studentID;

public:
    Student(string n, int a, string id) : Person(n, a), studentID(id) {}

    void displayDetails() {
        cout << "Name: " << name << ", Age: " << age << ", Student ID: " << studentID << endl;
    }
};

int main() {
    string name, studentID;
    int age;

    cout << "Enter Name: ";
    cin >> name;
    cout << "Enter Age: ";
    cin >> age;
    cout << "Enter Student ID: ";
    cin >> studentID;

    Student student(name, age, studentID);
    student.displayDetails();

    return 0;
}
```

These questions and solutions cover the specified topics and ensure the use of user input instead of hard-coded values, maintaining a good level of complexity.

Here are exam-oriented programming questions for Module 5: Inheritance and Polymorphism, including scenarios that require good levels of understanding, along with their solutions that take user input.

### Module 5: Inheritance and Polymorphism

#### 1. Single Inheritance
**Question:**  
Design a class `Account` that stores an account holder's name and balance. Create a derived class `SavingsAccount` that adds an interest rate and provides a method to calculate and add interest to the balance. Implement a method to display account details.

**Solution:**
```cpp
#include <iostream>
#include <string>
using namespace std;

class Account {
protected:
    string holderName;
    float balance;

public:
    Account(string name, float bal) : holderName(name), balance(bal) {}

    void display() {
        cout << "Account Holder: " << holderName << ", Balance: $" << balance << endl;
    }
};

class SavingsAccount : public Account {
private:
    float interestRate;

public:
    SavingsAccount(string name, float bal, float rate)
        : Account(name, bal), interestRate(rate) {}

    void addInterest() {
        float interest = balance * (interestRate / 100);
        balance += interest;
        cout << "Interest of $" << interest << " added to the balance." << endl;
    }
};

int main() {
    string name;
    float balance, interestRate;

    cout << "Enter Account Holder Name: ";
    getline(cin, name);
    cout << "Enter Initial Balance: ";
    cin >> balance;
    cout << "Enter Interest Rate (%): ";
    cin >> interestRate;

    SavingsAccount savings(name, balance, interestRate);
    savings.addInterest();
    savings.display();

    return 0;
}
```

#### 2. Multiple Inheritance
**Question:**  
Create two base classes `Employee` and `Department`. The `Employee` class should have attributes for `name` and `ID`, while the `Department` class should have an attribute for `deptName`. Create a derived class `Manager` that combines these attributes and includes a method to display all the details.

**Solution:**
```cpp
#include <iostream>
#include <string>
using namespace std;

class Employee {
protected:
    string name;
    int id;

public:
    Employee(string n, int i) : name(n), id(i) {}
};

class Department {
protected:
    string deptName;

public:
    Department(string d) : deptName(d) {}
};

class Manager : public Employee, public Department {
public:
    Manager(string n, int i, string d) : Employee(n, i), Department(d) {}

    void display() {
        cout << "Manager Name: " << name << ", ID: " << id << ", Department: " << deptName << endl;
    }
};

int main() {
    string name, deptName;
    int id;

    cout << "Enter Manager Name: ";
    getline(cin, name);
    cout << "Enter Manager ID: ";
    cin >> id;
    cout << "Enter Department Name: ";
    cin >> deptName;

    Manager manager(name, id, deptName);
    manager.display();

    return 0;
}
```

#### 3. Multi-level Inheritance
**Question:**  
Create a base class `Vehicle` with a method to display basic vehicle details. Derive a class `Car` that has attributes for `model` and `year`. Further derive a class `ElectricCar` that adds an attribute for `batteryCapacity` and overrides the display method to include all details.

**Solution:**
```cpp
#include <iostream>
#include <string>
using namespace std;

class Vehicle {
protected:
    string make;

public:
    Vehicle(string mk) : make(mk) {}

    virtual void display() {
        cout << "Vehicle Make: " << make << endl;
    }
};

class Car : public Vehicle {
protected:
    string model;
    int year;

public:
    Car(string mk, string mdl, int yr) : Vehicle(mk), model(mdl), year(yr) {}

    void display() override {
        Vehicle::display();
        cout << "Model: " << model << ", Year: " << year << endl;
    }
};

class ElectricCar : public Car {
private:
    float batteryCapacity;

public:
    ElectricCar(string mk, string mdl, int yr, float capacity)
        : Car(mk, mdl, yr), batteryCapacity(capacity) {}

    void display() override {
        Car::display();
        cout << "Battery Capacity: " << batteryCapacity << " kWh" << endl;
    }
};

int main() {
    string make, model;
    int year;
    float batteryCapacity;

    cout << "Enter Vehicle Make: ";
    getline(cin, make);
    cout << "Enter Car Model: ";
    getline(cin, model);
    cout << "Enter Year of Manufacture: ";
    cin >> year;
    cout << "Enter Battery Capacity (kWh): ";
    cin >> batteryCapacity;

    ElectricCar electricCar(make, model, year, batteryCapacity);
    electricCar.display();

    return 0;
}
```

#### 4. Inheritance and Constructors
**Question:**  
Implement a base class `Person` that has attributes for `name` and `age`. Create a derived class `Teacher` that adds attributes for `subject` and `salary`. Ensure that the constructor of `Teacher` calls the constructor of `Person` to initialize `name` and `age`.

**Solution:**
```cpp
#include <iostream>
#include <string>
using namespace std;

class Person {
protected:
    string name;
    int age;

public:
    Person(string n, int a) : name(n), age(a) {}
};

class Teacher : public Person {
private:
    string subject;
    float salary;

public:
    Teacher(string n, int a, string sub, float sal)
        : Person(n, a), subject(sub), salary(sal) {}

    void displayDetails() {
        cout << "Name: " << name << ", Age: " << age << ", Subject: " << subject << ", Salary: $" << salary << endl;
    }
};

int main() {
    string name, subject;
    int age;
    float salary;

    cout << "Enter Teacher Name: ";
    getline(cin, name);
    cout << "Enter Age: ";
    cin >> age;
    cout << "Enter Subject: ";
    cin >> subject;
    cout << "Enter Salary: ";
    cin >> salary;

    Teacher teacher(name, age, subject, salary);
    teacher.displayDetails();

    return 0;
}
```

#### 5. Polymorphism: Method Overriding
**Question:**  
Create a base class `Animal` with a virtual function `makeSound()`. Derive two classes, `Dog` and `Cat`, that override the `makeSound()` method. Implement a function that takes an `Animal` pointer and calls the `makeSound()` method.

**Solution:**
```cpp
#include <iostream>
using namespace std;

class Animal {
public:
    virtual void makeSound() {
        cout << "Some generic animal sound." << endl;
    }
};

class Dog : public Animal {
public:
    void makeSound() override {
        cout << "Bark!" << endl;
    }
};

class Cat : public Animal {
public:
    void makeSound() override {
        cout << "Meow!" << endl;
    }
};

void animalSound(Animal *animal) {
    animal->makeSound();
}

int main() {
    Dog dog;
    Cat cat;

    cout << "Dog sound: ";
    animalSound(&dog);
    cout << "Cat sound: ";
    animalSound(&cat);

    return 0;
}
```

#### 6. Polymorphism: Method Overloading
**Question:**  
Create a class `Print` that has multiple overloaded methods named `display()`, one for displaying an integer, one for displaying a float, and one for displaying a string. Implement a function that takes user input and displays the data using the appropriate `display()` method.

**Solution:**
```cpp
#include <iostream>
#include <string>
using namespace std;

class Print {
public:
    void display(int value) {
        cout << "Integer: " << value << endl;
    }

    void display(float value) {
        cout << "Float: " << value << endl;
    }

    void display(string value) {
        cout << "String: " << value << endl;
    }
};

int main() {
    Print printer;
    int intValue;
    float floatValue;
    string strValue;

    cout << "Enter an integer: ";
    cin >> intValue;
    printer.display(intValue);

    cout << "Enter a float: ";
    cin >> floatValue;
    printer.display(floatValue);

    cout << "Enter a string: ";
    cin.ignore(); // Clear newline from the input buffer
    getline(cin, strValue);
    printer.display(strValue);

    return 0;
}
```

These questions and solutions cover various aspects of inheritance and polymorphism, ensuring that the scenarios are realistic and require thoughtful implementation while still taking user input instead of relying on hard-coded values.
