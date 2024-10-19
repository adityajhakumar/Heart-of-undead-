

### **Module 4: Object-Oriented Programming Question 1**

**Question**:  
Create a class called `BankAccount` that represents a bank account. The class should have the following features:
- Private attributes for account number, account holder's name, and balance.
- A constructor to initialize the account with an account number and account holder's name.
- A method to deposit money.
- A method to withdraw money, ensuring that the balance does not go negative.
- A method to display account details.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

class BankAccount {
private:
    string accountNumber;
    string accountHolder;
    double balance;

public:
    // Constructor
    BankAccount(string accNum, string accHolder) {
        accountNumber = accNum;
        accountHolder = accHolder;
        balance = 0.0; // Initial balance is 0
    }

    // Method to deposit money
    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            cout << "Deposited: " << amount << endl;
        } else {
            cout << "Deposit amount must be positive!" << endl;
        }
    }

    // Method to withdraw money
    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            cout << "Withdrew: " << amount << endl;
        } else {
            cout << "Insufficient funds or invalid withdrawal amount!" << endl;
        }
    }

    // Method to display account details
    void displayAccount() {
        cout << "Account Number: " << accountNumber << endl;
        cout << "Account Holder: " << accountHolder << endl;
        cout << "Balance: " << balance << endl;
    }
};

int main() {
    BankAccount acc("123456", "John Doe");
    acc.displayAccount();
    acc.deposit(500.0);
    acc.withdraw(200.0);
    acc.displayAccount();
    acc.withdraw(400.0); // Should show insufficient funds

    return 0;
}
```

---


### **Module 4: Object-Oriented Programming Question 2**

**Question**:  
Design a class called `Rectangle` that represents a rectangle. The class should include:
- Private attributes for length and width.
- A constructor to initialize the rectangle's dimensions.
- A method to calculate the area of the rectangle.
- A method to calculate the perimeter of the rectangle.
- A method to display the dimensions, area, and perimeter.

**Program**:
```cpp
#include <iostream>
using namespace std;

class Rectangle {
private:
    double length;
    double width;

public:
    // Constructor
    Rectangle(double l, double w) {
        length = l;
        width = w;
    }

    // Method to calculate area
    double area() {
        return length * width;
    }

    // Method to calculate perimeter
    double perimeter() {
        return 2 * (length + width);
    }

    // Method to display rectangle details
    void display() {
        cout << "Length: " << length << endl;
        cout << "Width: " << width << endl;
        cout << "Area: " << area() << endl;
        cout << "Perimeter: " << perimeter() << endl;
    }
};

int main() {
    Rectangle rect(5.0, 3.0);
    rect.display();

    return 0;
}
```

---


Got it! Let's move on to a slightly more complex question for **Module 4**.

### **Module 4: Object-Oriented Programming Question 3**

**Question**:  
Create a class called `Employee` that represents an employee in a company. The class should include:
- Private attributes for employee ID, name, and salary.
- A constructor to initialize the employee's details.
- A method to give a raise (increase the salary by a specified percentage).
- A method to display the employee's details.
- Additionally, create a derived class called `Manager` that inherits from `Employee` and has an additional attribute for department name. It should override the display method to include the department name.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

class Employee {
private:
    int employeeID;
    string name;
    double salary;

public:
    // Constructor
    Employee(int id, string n, double sal) : employeeID(id), name(n), salary(sal) {}

    // Method to give a raise
    void giveRaise(double percentage) {
        if (percentage > 0) {
            salary += salary * (percentage / 100);
            cout << name << "'s salary increased by " << percentage << "%." << endl;
        } else {
            cout << "Invalid percentage!" << endl;
        }
    }

    // Method to display employee details
    virtual void display() {
        cout << "Employee ID: " << employeeID << endl;
        cout << "Name: " << name << endl;
        cout << "Salary: $" << salary << endl;
    }
};

class Manager : public Employee {
private:
    string department;

public:
    // Constructor
    Manager(int id, string n, double sal, string dept) : Employee(id, n, sal), department(dept) {}

    // Overriding the display method
    void display() override {
        Employee::display(); // Call base class display
        cout << "Department: " << department << endl;
    }
};

int main() {
    Employee emp(1, "Alice", 50000);
    emp.display();
    emp.giveRaise(10); // Increase salary by 10%
    emp.display();

    cout << endl;

    Manager mgr(2, "Bob", 70000, "Sales");
    mgr.display();
    mgr.giveRaise(15); // Increase salary by 15%
    mgr.display();

    return 0;
}
```

---




### **Module 4: Object-Oriented Programming Question 4**

**Question**:  
Design a class hierarchy for a `Shape` class and its derived classes `Circle` and `Triangle`. The `Shape` class should be an abstract base class with a pure virtual method for calculating the area. The derived classes should implement this method. Include a method to display the shape details in each derived class.

**Program**:
```cpp
#include <iostream>
#include <cmath> // For M_PI
using namespace std;

// Abstract base class
class Shape {
public:
    virtual double area() = 0; // Pure virtual function
    virtual void display() = 0; // Pure virtual function
};

class Circle : public Shape {
private:
    double radius;

public:
    // Constructor
    Circle(double r) : radius(r) {}

    // Implementing area calculation
    double area() override {
        return M_PI * radius * radius; // Area of circle
    }

    // Implementing display method
    void display() override {
        cout << "Circle with radius: " << radius << endl;
        cout << "Area: " << area() << endl;
    }
};

class Triangle : public Shape {
private:
    double base;
    double height;

public:
    // Constructor
    Triangle(double b, double h) : base(b), height(h) {}

    // Implementing area calculation
    double area() override {
        return 0.5 * base * height; // Area of triangle
    }

    // Implementing display method
    void display() override {
        cout << "Triangle with base: " << base << " and height: " << height << endl;
        cout << "Area: " << area() << endl;
    }
};

int main() {
    Shape* shapes[2]; // Array of Shape pointers

    // Creating objects of Circle and Triangle
    shapes[0] = new Circle(5.0);
    shapes[1] = new Triangle(4.0, 3.0);

    // Displaying details of each shape
    for (int i = 0; i < 2; i++) {
        shapes[i]->display();
        cout << endl;
    }

    // Clean up memory
    delete shapes[0];
    delete shapes[1];

    return 0;
}
```

### **Explanation of the Code**:
1. **Abstract Base Class `Shape`**:
   - This class has two pure virtual methods: `area()` and `display()`. This makes `Shape` an abstract class that cannot be instantiated directly.
  
2. **Derived Class `Circle`**:
   - Inherits from `Shape` and implements the `area()` method to calculate the area of the circle using the formula \( \pi r^2 \).
   - The `display()` method prints the radius and area of the circle.

3. **Derived Class `Triangle`**:
   - Also inherits from `Shape` and implements the `area()` method using the formula \( \frac{1}{2} \times \text{base} \times \text{height} \).
   - The `display()` method prints the base, height, and area of the triangle.

4. **`main()` Function**:
   - An array of `Shape` pointers is created to store different shapes.
   - Objects of `Circle` and `Triangle` are created and assigned to the array.
   - A loop iterates through the array, calling the `display()` method for each shape, which demonstrates polymorphism.
   - Finally, dynamically allocated memory is freed using `delete`.

---


### **Module 4: Object-Oriented Programming Question 5**

**Question**:  
Implement a class called `Library` that manages a collection of books. Each book should be represented by a class `Book` with attributes for title, author, and publication year. The `Library` class should include methods to add a book, remove a book, and display all books in the library. Use dynamic memory allocation to manage the collection of books.

**Program**:
```cpp
#include <iostream>
#include <string>
#include <vector> // For dynamic array (vector)
using namespace std;

class Book {
private:
    string title;
    string author;
    int publicationYear;

public:
    // Constructor
    Book(string t, string a, int year) : title(t), author(a), publicationYear(year) {}

    // Method to display book details
    void display() {
        cout << "Title: " << title << ", Author: " << author << ", Year: " << publicationYear << endl;
    }
};

class Library {
private:
    vector<Book*> books; // Dynamic array of Book pointers

public:
    // Method to add a book
    void addBook(string title, string author, int year) {
        books.push_back(new Book(title, author, year)); // Create and add a new Book
    }

    // Method to remove a book by title
    void removeBook(string title) {
        for (auto it = books.begin(); it != books.end(); ++it) {
            if ((*it)->display() == title) { // Find book by title
                delete *it; // Free the memory
                books.erase(it); // Remove from vector
                cout << "Book '" << title << "' removed from the library." << endl;
                return;
            }
        }
        cout << "Book not found!" << endl;
    }

    // Method to display all books
    void displayBooks() {
        cout << "Books in the library:" << endl;
        for (Book* book : books) {
            book->display(); // Display each book's details
        }
    }

    // Destructor to clean up memory
    ~Library() {
        for (Book* book : books) {
            delete book; // Free each Book's memory
        }
    }
};

int main() {
    Library myLibrary;
    myLibrary.addBook("1984", "George Orwell", 1949);
    myLibrary.addBook("To Kill a Mockingbird", "Harper Lee", 1960);
    myLibrary.displayBooks();

    myLibrary.removeBook("1984");
    myLibrary.displayBooks();

    return 0;
}
```

### **Explanation of the Code**:
1. **Class `Book`**:
   - Represents a book with attributes for title, author, and publication year.
   - The constructor initializes these attributes, and the `display()` method prints the book details.

2. **Class `Library`**:
   - Manages a collection of `Book` objects using a dynamic array (vector) of `Book` pointers.
   - **Method `addBook()`**: Creates a new `Book` object and adds it to the collection.
   - **Method `removeBook()`**: Searches for a book by title. If found, it deletes the book from memory and removes it from the vector. If not found, it displays a message.
   - **Method `displayBooks()`**: Iterates through the vector and calls the `display()` method for each `Book` to show the library's collection.
   - **Destructor**: Cleans up memory by deleting all dynamically allocated `Book` objects when the `Library` object is destroyed.

3. **`main()` Function**:
   - Creates an instance of the `Library` class and adds a couple of books.
   - Calls `displayBooks()` to show the current collection.
   - Removes a book by title and displays the updated collection.

---


### **Module 4: Object-Oriented Programming Question 6**

**Question**:  
Create a class called `Person` with private attributes for name and age. Implement a derived class called `Student` that adds a student ID and includes methods to set and get these values. The `Student` class should also include a method to display the student’s information. 

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

class Person {
protected:
    string name;
    int age;

public:
    // Constructor
    Person(string n, int a) : name(n), age(a) {}

    // Method to display person's name and age
    void display() {
        cout << "Name: " << name << ", Age: " << age << endl;
    }
};

class Student : public Person {
private:
    string studentID;

public:
    // Constructor
    Student(string n, int a, string id) : Person(n, a), studentID(id) {}

    // Method to display student's information
    void display() {
        Person::display(); // Call base class display method
        cout << "Student ID: " << studentID << endl;
    }
};

int main() {
    Student student("Alice", 20, "S123456");
    student.display();

    return 0;
}
```

### **Explanation of the Code**:
1. **Class `Person`**:
   - This is a base class with protected attributes for name and age. 
   - The constructor initializes these attributes, and the `display()` method prints the name and age of the person.

2. **Class `Student`**:
   - Inherits from `Person` and adds a private attribute for `studentID`.
   - The constructor initializes the inherited attributes using the base class constructor.
   - The `display()` method overrides the base class's `display()` method to include the student's ID in the output.

3. **`main()` Function**:
   - Creates an instance of the `Student` class, initializing it with a name, age, and student ID.
   - Calls the `display()` method to show the student's details, which includes name, age, and student ID.

---

### **Module 4: Object-Oriented Programming Question 7**

**Question**:  
Develop a class called `Vehicle` that represents a generic vehicle. Create derived classes `Car` and `Bike`. The `Vehicle` class should have attributes for brand and model, and methods to display these details. The `Car` class should include an additional attribute for the number of doors, while the `Bike` class should include an attribute for the type of bike (e.g., mountain, road). Implement polymorphism to display the details of each vehicle.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

// Base class
class Vehicle {
protected:
    string brand;
    string model;

public:
    // Constructor
    Vehicle(string b, string m) : brand(b), model(m) {}

    // Virtual method to display vehicle details
    virtual void display() {
        cout << "Brand: " << brand << ", Model: " << model << endl;
    }

    // Virtual destructor
    virtual ~Vehicle() {}
};

// Derived class Car
class Car : public Vehicle {
private:
    int doors;

public:
    // Constructor
    Car(string b, string m, int d) : Vehicle(b, m), doors(d) {}

    // Overriding display method
    void display() override {
        Vehicle::display(); // Call base class display
        cout << "Number of doors: " << doors << endl;
    }
};

// Derived class Bike
class Bike : public Vehicle {
private:
    string type;

public:
    // Constructor
    Bike(string b, string m, string t) : Vehicle(b, m), type(t) {}

    // Overriding display method
    void display() override {
        Vehicle::display(); // Call base class display
        cout << "Type: " << type << endl;
    }
};

int main() {
    Vehicle* vehicles[2]; // Array of Vehicle pointers

    // Creating Car and Bike objects
    vehicles[0] = new Car("Toyota", "Camry", 4);
    vehicles[1] = new Bike("Yamaha", "MT-07", "Sport");

    // Displaying details of each vehicle
    for (int i = 0; i < 2; i++) {
        vehicles[i]->display();
        cout << endl;
    }

    // Clean up memory
    delete vehicles[0];
    delete vehicles[1];

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Vehicle`**:
   - Contains protected attributes for `brand` and `model`.
   - The constructor initializes these attributes, and the `display()` method outputs the vehicle's brand and model.
   - The class has a virtual destructor to ensure proper cleanup of derived class objects.

2. **Derived Class `Car`**:
   - Inherits from `Vehicle` and adds a private attribute for the number of doors.
   - The constructor initializes the base class attributes and the `doors`.
   - The `display()` method overrides the base class's method to include the number of doors in the output.

3. **Derived Class `Bike`**:
   - Inherits from `Vehicle` and adds a private attribute for the type of bike.
   - Similar to the `Car` class, it overrides the `display()` method to include the bike's type.

4. **`main()` Function**:
   - An array of `Vehicle` pointers is created to hold both `Car` and `Bike` objects.
   - Objects are created for `Car` and `Bike` and assigned to the array.
   - A loop iterates through the array, calling the `display()` method for each vehicle, demonstrating polymorphism.
   - Finally, dynamically allocated memory is cleaned up using `delete`.

---

### **Module 4: Object-Oriented Programming Question 8**

**Question**:  
Implement a class called `Account` to manage bank accounts with basic operations like deposit and withdrawal. Create a derived class called `SavingsAccount` that adds an interest rate feature. The `SavingsAccount` should have a method to apply interest to the balance at the end of each month. Include appropriate constructors and a method to display the account details.

**Program**:
```cpp
#include <iostream>
#include <iomanip> // For std::fixed and std::setprecision
using namespace std;

// Base class
class Account {
protected:
    double balance;

public:
    // Constructor
    Account(double initialBalance) : balance(initialBalance) {}

    // Method to deposit money
    void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            cout << "Deposited: $" << amount << endl;
        } else {
            cout << "Invalid deposit amount!" << endl;
        }
    }

    // Method to withdraw money
    void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            cout << "Withdrew: $" << amount << endl;
        } else {
            cout << "Insufficient funds or invalid withdrawal amount!" << endl;
        }
    }

    // Virtual method to display account details
    virtual void display() {
        cout << "Balance: $" << fixed << setprecision(2) << balance << endl;
    }
};

// Derived class SavingsAccount
class SavingsAccount : public Account {
private:
    double interestRate; // Annual interest rate in percentage

public:
    // Constructor
    SavingsAccount(double initialBalance, double rate) : Account(initialBalance), interestRate(rate) {}

    // Method to apply interest
    void applyInterest() {
        double interest = (balance * interestRate) / 100;
        balance += interest;
        cout << "Interest applied: $" << interest << endl;
    }

    // Overriding display method
    void display() override {
        Account::display(); // Call base class display
        cout << "Interest Rate: " << interestRate << "%" << endl;
    }
};

int main() {
    SavingsAccount mySavings(1000.0, 5.0); // $1000 initial balance, 5% interest rate
    mySavings.display();

    mySavings.deposit(500.0);
    mySavings.withdraw(200.0);
    mySavings.applyInterest(); // Apply interest at the end of the month
    mySavings.display();

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Account`**:
   - Contains a protected attribute for `balance`.
   - The constructor initializes the balance.
   - The `deposit()` method allows adding money to the account.
   - The `withdraw()` method allows withdrawing money, ensuring the balance doesn’t go negative.
   - The `display()` method shows the current balance, using `fixed` and `setprecision` to format the output to two decimal places.

2. **Derived Class `SavingsAccount`**:
   - Inherits from `Account` and adds a private attribute for `interestRate`.
   - The constructor initializes both the balance and interest rate.
   - The `applyInterest()` method calculates and adds the interest to the balance based on the interest rate.
   - The `display()` method overrides the base class’s method to include the interest rate in the output.

3. **`main()` Function**:
   - Creates an instance of `SavingsAccount` with an initial balance and interest rate.
   - Calls the `display()` method to show the account details.
   - Performs deposit and withdrawal operations, then applies interest and displays updated account details.

---

### **Module 4: Object-Oriented Programming Question 9**

**Question**:  
Create a class called `Movie` to represent a movie with attributes for title, director, release year, and rating. Implement a derived class called `ActionMovie` that adds an attribute for the main actor. Include methods for displaying movie details, as well as a method to compare ratings between two movies. Use dynamic memory allocation for managing an array of movies.

**Program**:
```cpp
#include <iostream>
#include <string>
#include <iomanip> // For std::fixed and std::setprecision
using namespace std;

// Base class
class Movie {
protected:
    string title;
    string director;
    int releaseYear;
    double rating;

public:
    // Constructor
    Movie(string t, string d, int year, double r) 
        : title(t), director(d), releaseYear(year), rating(r) {}

    // Method to display movie details
    virtual void display() {
        cout << "Title: " << title << endl;
        cout << "Director: " << director << endl;
        cout << "Release Year: " << releaseYear << endl;
        cout << "Rating: " << fixed << setprecision(1) << rating << "/10" << endl;
    }

    // Method to compare ratings
    bool hasHigherRatingThan(Movie* other) {
        return rating > other->rating;
    }

    // Virtual destructor
    virtual ~Movie() {}
};

// Derived class ActionMovie
class ActionMovie : public Movie {
private:
    string mainActor;

public:
    // Constructor
    ActionMovie(string t, string d, int year, double r, string actor) 
        : Movie(t, d, year, r), mainActor(actor) {}

    // Overriding display method
    void display() override {
        Movie::display(); // Call base class display
        cout << "Main Actor: " << mainActor << endl;
    }
};

int main() {
    int n;
    cout << "Enter number of movies: ";
    cin >> n;
    cin.ignore(); // To ignore newline character after integer input

    Movie** movies = new Movie*[n]; // Dynamic array of Movie pointers

    // Input movie details
    for (int i = 0; i < n; i++) {
        string title, director, actor;
        int year;
        double rating;
        char type;

        cout << "Enter details for Movie " << (i + 1) << ":" << endl;
        cout << "Title: ";
        getline(cin, title);
        cout << "Director: ";
        getline(cin, director);
        cout << "Release Year: ";
        cin >> year;
        cout << "Rating: ";
        cin >> rating;
        cout << "Is it an Action Movie? (y/n): ";
        cin >> type;
        cin.ignore(); // To ignore newline character

        if (type == 'y' || type == 'Y') {
            cout << "Main Actor: ";
            getline(cin, actor);
            movies[i] = new ActionMovie(title, director, year, rating, actor);
        } else {
            movies[i] = new Movie(title, director, year, rating);
        }
    }

    // Display movies and compare ratings
    for (int i = 0; i < n; i++) {
        cout << endl;
        movies[i]->display();
    }

    // Comparing ratings of the first two movies
    if (n > 1) {
        if (movies[0]->hasHigherRatingThan(movies[1])) {
            cout << movies[0]->title << " has a higher rating than " << movies[1]->title << endl;
        } else {
            cout << movies[1]->title << " has a higher rating than " << movies[0]->title << endl;
        }
    }

    // Clean up memory
    for (int i = 0; i < n; i++) {
        delete movies[i];
    }
    delete[] movies;

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Movie`**:
   - Contains protected attributes for `title`, `director`, `releaseYear`, and `rating`.
   - The constructor initializes these attributes.
   - The `display()` method outputs movie details, formatting the rating to one decimal place.
   - The `hasHigherRatingThan()` method compares the rating of the current movie with another movie.
   - A virtual destructor ensures proper cleanup of derived class objects.

2. **Derived Class `ActionMovie`**:
   - Inherits from `Movie` and adds a private attribute for the `mainActor`.
   - The constructor initializes both the inherited attributes and the `mainActor`.
   - The `display()` method overrides the base class’s method to include the main actor in the output.

3. **`main()` Function**:
   - Prompts the user for the number of movies to enter and creates a dynamic array of `Movie` pointers.
   - For each movie, it asks for the details, including whether it's an action movie. If it is, it creates an `ActionMovie` object; otherwise, it creates a `Movie` object.
   - It displays the details of each movie and compares the ratings of the first two movies, printing which one has a higher rating.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---

### **Module 4: Object-Oriented Programming Question 10**

**Question**:  
Design a class hierarchy for a `Shape` with derived classes `Square` and `Circle`. Each class should have a method to calculate its area and a method to display its details. Include a mechanism to store an array of different shapes and a function to calculate the total area of all shapes combined. 

**Program**:
```cpp
#include <iostream>
#include <cmath> // For M_PI
#include <iomanip> // For std::fixed and std::setprecision
using namespace std;

// Base class
class Shape {
public:
    virtual double area() = 0; // Pure virtual function for area
    virtual void display() = 0; // Pure virtual function for display
    virtual ~Shape() {} // Virtual destructor
};

// Derived class Square
class Square : public Shape {
private:
    double side;

public:
    // Constructor
    Square(double s) : side(s) {}

    // Implementing area calculation
    double area() override {
        return side * side; // Area of square
    }

    // Implementing display method
    void display() override {
        cout << "Square with side: " << side << endl;
        cout << "Area: " << fixed << setprecision(2) << area() << endl;
    }
};

// Derived class Circle
class Circle : public Shape {
private:
    double radius;

public:
    // Constructor
    Circle(double r) : radius(r) {}

    // Implementing area calculation
    double area() override {
        return M_PI * radius * radius; // Area of circle
    }

    // Implementing display method
    void display() override {
        cout << "Circle with radius: " << radius << endl;
        cout << "Area: " << fixed << setprecision(2) << area() << endl;
    }
};

// Function to calculate total area of shapes
double totalArea(Shape** shapes, int count) {
    double total = 0;
    for (int i = 0; i < count; i++) {
        total += shapes[i]->area(); // Accumulate area of each shape
    }
    return total;
}

int main() {
    int n;
    cout << "Enter the number of shapes: ";
    cin >> n;

    Shape** shapes = new Shape*[n]; // Dynamic array of Shape pointers

    // Input shape details
    for (int i = 0; i < n; i++) {
        char type;
        cout << "Enter shape type (s for square, c for circle): ";
        cin >> type;

        if (type == 's' || type == 'S') {
            double side;
            cout << "Enter side length of the square: ";
            cin >> side;
            shapes[i] = new Square(side);
        } else if (type == 'c' || type == 'C') {
            double radius;
            cout << "Enter radius of the circle: ";
            cin >> radius;
            shapes[i] = new Circle(radius);
        } else {
            cout << "Invalid shape type. Please enter 's' or 'c'." << endl;
            i--; // Decrement to repeat the input for this index
        }
    }

    // Display shapes and calculate total area
    cout << endl << "Shape details:" << endl;
    for (int i = 0; i < n; i++) {
        shapes[i]->display();
        cout << endl;
    }

    double total = totalArea(shapes, n);
    cout << "Total Area of all shapes: " << fixed << setprecision(2) << total << endl;

    // Clean up memory
    for (int i = 0; i < n; i++) {
        delete shapes[i];
    }
    delete[] shapes;

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Shape`**:
   - This is an abstract class with pure virtual functions `area()` and `display()`. It has a virtual destructor to ensure derived class objects are properly destroyed.

2. **Derived Class `Square`**:
   - Contains a private attribute for `side` and implements the `area()` method to calculate the area of the square.
   - The `display()` method shows the side length and the area.

3. **Derived Class `Circle`**:
   - Contains a private attribute for `radius` and implements the `area()` method to calculate the area of the circle.
   - The `display()` method shows the radius and the area.

4. **Function `totalArea()`**:
   - Takes an array of `Shape` pointers and counts the total area of all shapes by summing their individual areas.

5. **`main()` Function**:
   - Prompts the user for the number of shapes and creates a dynamic array of `Shape` pointers.
   - Based on user input, it either creates a `Square` or a `Circle`, storing it in the array.
   - After collecting all shapes, it displays their details and computes the total area using the `totalArea()` function.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---



### **Module 5: Inheritance and Polymorphism Question 1**

**Question**:  
Create a base class called `Animal` with a method `makeSound()`. Implement derived classes `Dog` and `Cat` that override this method to provide their specific sounds. Demonstrate polymorphism by creating an array of `Animal` pointers and calling `makeSound()` on each of them.

**Program**:
```cpp
#include <iostream>
using namespace std;

// Base class
class Animal {
public:
    // Virtual function to make sound
    virtual void makeSound() {
        cout << "Some generic animal sound." << endl;
    }

    // Virtual destructor
    virtual ~Animal() {}
};

// Derived class Dog
class Dog : public Animal {
public:
    // Overriding makeSound method
    void makeSound() override {
        cout << "Woof!" << endl;
    }
};

// Derived class Cat
class Cat : public Animal {
public:
    // Overriding makeSound method
    void makeSound() override {
        cout << "Meow!" << endl;
    }
};

int main() {
    const int size = 3;
    Animal* animals[size]; // Array of Animal pointers

    // Creating Dog and Cat objects
    animals[0] = new Dog();
    animals[1] = new Cat();
    animals[2] = new Dog();

    // Calling makeSound on each animal
    for (int i = 0; i < size; i++) {
        animals[i]->makeSound();
    }

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete animals[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Animal`**:
   - Contains a virtual method `makeSound()` that provides a generic animal sound.
   - The class has a virtual destructor to ensure proper cleanup of derived class objects.

2. **Derived Class `Dog`**:
   - Overrides the `makeSound()` method to output "Woof!" when called.

3. **Derived Class `Cat`**:
   - Overrides the `makeSound()` method to output "Meow!" when called.

4. **`main()` Function**:
   - Creates an array of `Animal` pointers to hold `Dog` and `Cat` objects.
   - Initializes the array with a mix of `Dog` and `Cat` objects.
   - A loop iterates through the array and calls the `makeSound()` method on each animal, demonstrating polymorphism.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---

### **Module 5: Inheritance and Polymorphism Question 2**

**Question**:  
Design a class hierarchy for `Employee` with derived classes `FullTimeEmployee` and `PartTimeEmployee`. Each employee should have a method to calculate their monthly salary. The `FullTimeEmployee` class should have a fixed salary, while the `PartTimeEmployee` class should calculate salary based on hours worked. Demonstrate polymorphism by creating an array of `Employee` pointers and calculating the total salary.

**Program**:
```cpp
#include <iostream>
#include <vector>
using namespace std;

// Base class
class Employee {
public:
    // Virtual function to calculate salary
    virtual double calculateSalary() = 0; // Pure virtual function
    virtual void display() = 0; // Pure virtual function
    virtual ~Employee() {}
};

// Derived class FullTimeEmployee
class FullTimeEmployee : public Employee {
private:
    double fixedSalary;

public:
    // Constructor
    FullTimeEmployee(double salary) : fixedSalary(salary) {}

    // Implementing calculateSalary method
    double calculateSalary() override {
        return fixedSalary;
    }

    // Implementing display method
    void display() override {
        cout << "Full-Time Employee, Salary: $" << fixedSalary << endl;
    }
};

// Derived class PartTimeEmployee
class PartTimeEmployee : public Employee {
private:
    double hourlyRate;
    int hoursWorked;

public:
    // Constructor
    PartTimeEmployee(double rate, int hours) : hourlyRate(rate), hoursWorked(hours) {}

    // Implementing calculateSalary method
    double calculateSalary() override {
        return hourlyRate * hoursWorked;
    }

    // Implementing display method
    void display() override {
        cout << "Part-Time Employee, Hourly Rate: $" << hourlyRate << ", Hours Worked: " << hoursWorked << endl;
        cout << "Calculated Salary: $" << calculateSalary() << endl;
    }
};

int main() {
    vector<Employee*> employees; // Vector to hold Employee pointers

    // Creating employees
    employees.push_back(new FullTimeEmployee(5000.0)); // Fixed salary
    employees.push_back(new PartTimeEmployee(20.0, 80)); // $20/hour for 80 hours

    double totalSalary = 0.0;

    // Displaying employee details and calculating total salary
    for (Employee* emp : employees) {
        emp->display();
        totalSalary += emp->calculateSalary();
    }

    cout << "Total Salary of all employees: $" << totalSalary << endl;

    // Clean up memory
    for (Employee* emp : employees) {
        delete emp;
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Employee`**:
   - An abstract class with pure virtual functions `calculateSalary()` and `display()`, enforcing that derived classes must implement these methods.

2. **Derived Class `FullTimeEmployee`**:
   - Contains a private attribute for `fixedSalary` and implements the `calculateSalary()` method to return the fixed salary.
   - The `display()` method outputs the employee type and salary.

3. **Derived Class `PartTimeEmployee`**:
   - Contains private attributes for `hourlyRate` and `hoursWorked`. It calculates the salary by multiplying the hourly rate by the hours worked.
   - The `display()` method shows the employee type, hourly rate, hours worked, and calculated salary.

4. **`main()` Function**:
   - Uses a `vector` to hold pointers to `Employee` objects.
   - Creates instances of `FullTimeEmployee` and `PartTimeEmployee`, adding them to the vector.
   - Iterates through the vector, displaying employee details and calculating the total salary by calling `calculateSalary()` on each object.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---

### **Module 5: Inheritance and Polymorphism Question 3**

**Question**:  
Create a base class `Shape` with a method `area()`. Implement derived classes `Rectangle` and `Triangle` that override this method to calculate their respective areas. Include a method to display the shape type and area. Demonstrate polymorphism by creating an array of `Shape` pointers and calculating the total area of all shapes.

**Program**:
```cpp
#include <iostream>
using namespace std;

// Base class
class Shape {
public:
    // Virtual function to calculate area
    virtual double area() = 0; // Pure virtual function
    virtual void display() = 0; // Pure virtual function
    virtual ~Shape() {}
};

// Derived class Rectangle
class Rectangle : public Shape {
private:
    double width;
    double height;

public:
    // Constructor
    Rectangle(double w, double h) : width(w), height(h) {}

    // Implementing area calculation
    double area() override {
        return width * height; // Area of rectangle
    }

    // Implementing display method
    void display() override {
        cout << "Rectangle: Width = " << width << ", Height = " << height 
             << ", Area = " << area() << endl;
    }
};

// Derived class Triangle
class Triangle : public Shape {
private:
    double base;
    double height;

public:
    // Constructor
    Triangle(double b, double h) : base(b), height(h) {}

    // Implementing area calculation
    double area() override {
        return 0.5 * base * height; // Area of triangle
    }

    // Implementing display method
    void display() override {
        cout << "Triangle: Base = " << base << ", Height = " << height 
             << ", Area = " << area() << endl;
    }
};

int main() {
    const int size = 3;
    Shape* shapes[size]; // Array of Shape pointers

    // Creating Rectangle and Triangle objects
    shapes[0] = new Rectangle(4.0, 5.0); // Rectangle with width 4 and height 5
    shapes[1] = new Triangle(3.0, 6.0); // Triangle with base 3 and height 6
    shapes[2] = new Rectangle(2.0, 3.5); // Another Rectangle

    double totalArea = 0.0;

    // Displaying shapes and calculating total area
    for (int i = 0; i < size; i++) {
        shapes[i]->display();
        totalArea += shapes[i]->area(); // Accumulate area of each shape
    }

    cout << "Total Area of all shapes: " << totalArea << endl;

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete shapes[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Shape`**:
   - This is an abstract class with pure virtual functions `area()` and `display()`, which forces derived classes to implement these methods.

2. **Derived Class `Rectangle`**:
   - Contains private attributes `width` and `height`.
   - The `area()` method calculates the area of the rectangle (width × height).
   - The `display()` method outputs the dimensions and the calculated area.

3. **Derived Class `Triangle`**:
   - Contains private attributes `base` and `height`.
   - The `area()` method calculates the area of the triangle (0.5 × base × height).
   - The `display()` method outputs the dimensions and the calculated area.

4. **`main()` Function**:
   - Creates an array of `Shape` pointers and initializes it with instances of `Rectangle` and `Triangle`.
   - A loop iterates through the array, displaying each shape's details and calculating the total area by calling the `area()` method on each object.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---


### **Module 5: Inheritance and Polymorphism Question 4**

**Question**:  
Design a base class `Vehicle` with derived classes `Car` and `Bike`. Each class should have attributes for speed and fuel efficiency, and methods to display their details. Additionally, implement a method to compare the fuel efficiency of two vehicles. Demonstrate polymorphism by creating an array of `Vehicle` pointers and displaying their details.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

// Base class
class Vehicle {
protected:
    double speed; // in km/h
    double fuelEfficiency; // in km/l

public:
    Vehicle(double s, double fe) : speed(s), fuelEfficiency(fe) {}
    
    // Virtual method to display details
    virtual void display() = 0; // Pure virtual function

    // Method to compare fuel efficiency
    bool hasBetterFuelEfficiencyThan(Vehicle* other) {
        return fuelEfficiency > other->fuelEfficiency;
    }

    virtual ~Vehicle() {}
};

// Derived class Car
class Car : public Vehicle {
private:
    string model;

public:
    Car(string m, double s, double fe) : Vehicle(s, fe), model(m) {}

    // Implementing display method
    void display() override {
        cout << "Car Model: " << model << ", Speed: " << speed 
             << " km/h, Fuel Efficiency: " << fuelEfficiency << " km/l" << endl;
    }
};

// Derived class Bike
class Bike : public Vehicle {
private:
    string type;

public:
    Bike(string t, double s, double fe) : Vehicle(s, fe), type(t) {}

    // Implementing display method
    void display() override {
        cout << "Bike Type: " << type << ", Speed: " << speed 
             << " km/h, Fuel Efficiency: " << fuelEfficiency << " km/l" << endl;
    }
};

int main() {
    const int size = 3;
    Vehicle* vehicles[size]; // Array of Vehicle pointers

    // Creating Car and Bike objects
    vehicles[0] = new Car("Sedan", 120.0, 15.0); // Car with speed 120 km/h and fuel efficiency 15 km/l
    vehicles[1] = new Bike("Mountain", 30.0, 40.0); // Bike with speed 30 km/h and fuel efficiency 40 km/l
    vehicles[2] = new Car("Hatchback", 110.0, 18.0); // Another Car

    // Displaying vehicles and comparing fuel efficiencies
    for (int i = 0; i < size; i++) {
        vehicles[i]->display();
    }

    // Comparing fuel efficiency between the first two vehicles
    if (vehicles[0]->hasBetterFuelEfficiencyThan(vehicles[1])) {
        cout << "The " << static_cast<Car*>(vehicles[0])->model << " has better fuel efficiency than the " 
             << static_cast<Bike*>(vehicles[1])->type << "." << endl;
    } else {
        cout << "The " << static_cast<Bike*>(vehicles[1])->type << " has better fuel efficiency than the " 
             << static_cast<Car*>(vehicles[0])->model << "." << endl;
    }

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete vehicles[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Vehicle`**:
   - Contains protected attributes `speed` and `fuelEfficiency`.
   - The constructor initializes these attributes.
   - A pure virtual method `display()` enforces that derived classes must implement this method.
   - The method `hasBetterFuelEfficiencyThan()` compares the fuel efficiency of the current vehicle with another vehicle.

2. **Derived Class `Car`**:
   - Contains a private attribute for `model`.
   - The constructor initializes the model along with speed and fuel efficiency.
   - The `display()` method outputs the car's model, speed, and fuel efficiency.

3. **Derived Class `Bike`**:
   - Contains a private attribute for `type`.
   - The constructor initializes the type along with speed and fuel efficiency.
   - The `display()` method outputs the bike's type, speed, and fuel efficiency.

4. **`main()` Function**:
   - Creates an array of `Vehicle` pointers and initializes it with instances of `Car` and `Bike`.
   - A loop iterates through the array, displaying the details of each vehicle.
   - The program compares the fuel efficiency of the first two vehicles and outputs which one is better.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---

### **Module 5: Inheritance and Polymorphism Question 5**

**Question**:  
Implement a class hierarchy for a `Library` system with a base class `Media` and derived classes `Book` and `DVD`. Each class should have methods to display information about the media, including title, author/director, and duration. Demonstrate polymorphism by creating an array of `Media` pointers and displaying their details.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

// Base class
class Media {
protected:
    string title;

public:
    Media(string t) : title(t) {}
    
    // Virtual function to display media details
    virtual void display() = 0; // Pure virtual function
    virtual ~Media() {}
};

// Derived class Book
class Book : public Media {
private:
    string author;
    int pages;

public:
    Book(string t, string a, int p) : Media(t), author(a), pages(p) {}

    // Implementing display method
    void display() override {
        cout << "Book Title: " << title << ", Author: " << author 
             << ", Pages: " << pages << endl;
    }
};

// Derived class DVD
class DVD : public Media {
private:
    string director;
    double duration; // in minutes

public:
    DVD(string t, string d, double dur) : Media(t), director(d), duration(dur) {}

    // Implementing display method
    void display() override {
        cout << "DVD Title: " << title << ", Director: " << director 
             << ", Duration: " << duration << " minutes" << endl;
    }
};

int main() {
    const int size = 3;
    Media* mediaCollection[size]; // Array of Media pointers

    // Creating Book and DVD objects
    mediaCollection[0] = new Book("1984", "George Orwell", 328); // Book
    mediaCollection[1] = new DVD("Inception", "Christopher Nolan", 148); // DVD
    mediaCollection[2] = new Book("The Great Gatsby", "F. Scott Fitzgerald", 180); // Another Book

    // Displaying media details
    for (int i = 0; i < size; i++) {
        mediaCollection[i]->display();
    }

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete mediaCollection[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Media`**:
   - Contains a protected attribute `title`.
   - The constructor initializes the title.
   - A pure virtual function `display()` enforces that derived classes must implement this method.

2. **Derived Class `Book`**:
   - Contains private attributes for `author` and `pages`.
   - The constructor initializes these attributes along with the title.
   - The `display()` method outputs the book's title, author, and number of pages.

3. **Derived Class `DVD`**:
   - Contains private attributes for `director` and `duration`.
   - The constructor initializes these attributes along with the title.
   - The `display()` method outputs the DVD's title, director, and duration in minutes.

4. **`main()` Function**:
   - Creates an array of `Media` pointers and initializes it with instances of `Book` and `DVD`.
   - A loop iterates through the array, displaying the details of each media item.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---

### **Module 5: Inheritance and Polymorphism Question 6**

**Question**:  
Create a class hierarchy for a `School` system with a base class `Person` and derived classes `Student` and `Teacher`. Each class should have methods to display information about the person, including name and role (student or teacher). Demonstrate polymorphism by creating an array of `Person` pointers and displaying their details.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

// Base class
class Person {
protected:
    string name;

public:
    Person(string n) : name(n) {}

    // Virtual function to display person details
    virtual void display() = 0; // Pure virtual function
    virtual ~Person() {}
};

// Derived class Student
class Student : public Person {
private:
    int grade;

public:
    Student(string n, int g) : Person(n), grade(g) {}

    // Implementing display method
    void display() override {
        cout << "Student Name: " << name << ", Grade: " << grade << endl;
    }
};

// Derived class Teacher
class Teacher : public Person {
private:
    string subject;

public:
    Teacher(string n, string s) : Person(n), subject(s) {}

    // Implementing display method
    void display() override {
        cout << "Teacher Name: " << name << ", Subject: " << subject << endl;
    }
};

int main() {
    const int size = 4;
    Person* people[size]; // Array of Person pointers

    // Creating Student and Teacher objects
    people[0] = new Student("Alice", 10); // Student
    people[1] = new Teacher("Mr. Smith", "Mathematics"); // Teacher
    people[2] = new Student("Bob", 11); // Another Student
    people[3] = new Teacher("Ms. Johnson", "Science"); // Another Teacher

    // Displaying person details
    for (int i = 0; i < size; i++) {
        people[i]->display();
    }

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete people[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Person`**:
   - Contains a protected attribute `name`.
   - The constructor initializes the name.
   - A pure virtual function `display()` enforces that derived classes must implement this method.

2. **Derived Class `Student`**:
   - Contains a private attribute for `grade`.
   - The constructor initializes the grade along with the name.
   - The `display()` method outputs the student's name and grade.

3. **Derived Class `Teacher`**:
   - Contains a private attribute for `subject`.
   - The constructor initializes the subject along with the name.
   - The `display()` method outputs the teacher's name and subject.

4. **`main()` Function**:
   - Creates an array of `Person` pointers and initializes it with instances of `Student` and `Teacher`.
   - A loop iterates through the array, displaying the details of each person.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---

### **Module 5: Inheritance and Polymorphism Question 7**

**Question**:  
Design a class hierarchy for a `Banking` system with a base class `Account` and derived classes `SavingsAccount` and `CurrentAccount`. Each account should have methods to deposit, withdraw, and display the account balance. Demonstrate polymorphism by creating an array of `Account` pointers and performing operations on each account.

**Program**:
```cpp
#include <iostream>
using namespace std;

// Base class
class Account {
protected:
    double balance;

public:
    Account(double initialBalance) : balance(initialBalance) {}

    // Virtual methods
    virtual void deposit(double amount) = 0; // Pure virtual function
    virtual void withdraw(double amount) = 0; // Pure virtual function
    virtual void display() = 0; // Pure virtual function
    virtual ~Account() {}
};

// Derived class SavingsAccount
class SavingsAccount : public Account {
public:
    SavingsAccount(double initialBalance) : Account(initialBalance) {}

    // Implementing deposit method
    void deposit(double amount) override {
        balance += amount;
        cout << "Deposited $" << amount << " to Savings Account." << endl;
    }

    // Implementing withdraw method
    void withdraw(double amount) override {
        if (amount <= balance) {
            balance -= amount;
            cout << "Withdrew $" << amount << " from Savings Account." << endl;
        } else {
            cout << "Insufficient balance in Savings Account." << endl;
        }
    }

    // Implementing display method
    void display() override {
        cout << "Savings Account Balance: $" << balance << endl;
    }
};

// Derived class CurrentAccount
class CurrentAccount : public Account {
public:
    CurrentAccount(double initialBalance) : Account(initialBalance) {}

    // Implementing deposit method
    void deposit(double amount) override {
        balance += amount;
        cout << "Deposited $" << amount << " to Current Account." << endl;
    }

    // Implementing withdraw method
    void withdraw(double amount) override {
        if (amount <= balance) {
            balance -= amount;
            cout << "Withdrew $" << amount << " from Current Account." << endl;
        } else {
            cout << "Insufficient balance in Current Account." << endl;
        }
    }

    // Implementing display method
    void display() override {
        cout << "Current Account Balance: $" << balance << endl;
    }
};

int main() {
    const int size = 3;
    Account* accounts[size]; // Array of Account pointers

    // Creating SavingsAccount and CurrentAccount objects
    accounts[0] = new SavingsAccount(1000.0); // Savings account with initial balance of $1000
    accounts[1] = new CurrentAccount(2000.0); // Current account with initial balance of $2000
    accounts[2] = new SavingsAccount(1500.0); // Another Savings account

    // Performing operations
    accounts[0]->deposit(200);
    accounts[0]->withdraw(100);
    accounts[1]->deposit(500);
    accounts[1]->withdraw(2500); // Attempting to withdraw more than the balance
    accounts[2]->deposit(300);

    // Displaying account balances
    for (int i = 0; i < size; i++) {
        accounts[i]->display();
    }

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete accounts[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Account`**:
   - Contains a protected attribute `balance` to keep track of the account balance.
   - The constructor initializes the balance.
   - Three pure virtual functions: `deposit()`, `withdraw()`, and `display()`, which must be implemented by derived classes.

2. **Derived Class `SavingsAccount`**:
   - Implements the `deposit()` method to increase the balance by the deposited amount.
   - Implements the `withdraw()` method to decrease the balance by the withdrawn amount, checking for sufficient balance.
   - The `display()` method outputs the current balance.

3. **Derived Class `CurrentAccount`**:
   - Implements the `deposit()` method similar to `SavingsAccount`.
   - Implements the `withdraw()` method similar to `SavingsAccount`.
   - The `display()` method outputs the current balance.

4. **`main()` Function**:
   - Creates an array of `Account` pointers and initializes it with instances of `SavingsAccount` and `CurrentAccount`.
   - Performs deposit and withdrawal operations on each account, demonstrating polymorphism.
   - Displays the balances of all accounts after the operations.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---

### **Module 5: Inheritance and Polymorphism Question 8**

**Question**:  
Implement a class hierarchy for a `Company` system with a base class `Employee` and derived classes `FullTimeEmployee` and `PartTimeEmployee`. Each employee should have methods to calculate salary based on their type and display their information. Demonstrate polymorphism by creating an array of `Employee` pointers and calculating the total salary for all employees.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

// Base class
class Employee {
protected:
    string name;

public:
    Employee(string n) : name(n) {}

    // Virtual functions
    virtual double calculateSalary() = 0; // Pure virtual function
    virtual void display() = 0; // Pure virtual function
    virtual ~Employee() {}
};

// Derived class FullTimeEmployee
class FullTimeEmployee : public Employee {
private:
    double monthlySalary;

public:
    FullTimeEmployee(string n, double salary) : Employee(n), monthlySalary(salary) {}

    // Implementing calculateSalary method
    double calculateSalary() override {
        return monthlySalary; // Full-time salary is fixed monthly salary
    }

    // Implementing display method
    void display() override {
        cout << "Full-Time Employee: " << name << ", Monthly Salary: $" << monthlySalary << endl;
    }
};

// Derived class PartTimeEmployee
class PartTimeEmployee : public Employee {
private:
    double hourlyRate;
    int hoursWorked;

public:
    PartTimeEmployee(string n, double rate, int hours) : Employee(n), hourlyRate(rate), hoursWorked(hours) {}

    // Implementing calculateSalary method
    double calculateSalary() override {
        return hourlyRate * hoursWorked; // Part-time salary is calculated based on hours worked
    }

    // Implementing display method
    void display() override {
        cout << "Part-Time Employee: " << name << ", Hourly Rate: $" << hourlyRate 
             << ", Hours Worked: " << hoursWorked << ", Total Salary: $" << calculateSalary() << endl;
    }
};

int main() {
    const int size = 3;
    Employee* employees[size]; // Array of Employee pointers

    // Creating FullTimeEmployee and PartTimeEmployee objects
    employees[0] = new FullTimeEmployee("Alice", 4000); // Full-time employee with a monthly salary of $4000
    employees[1] = new PartTimeEmployee("Bob", 20, 80); // Part-time employee with hourly rate of $20 and worked 80 hours
    employees[2] = new FullTimeEmployee("Charlie", 4500); // Another Full-time employee

    double totalSalary = 0.0;

    // Displaying employee details and calculating total salary
    for (int i = 0; i < size; i++) {
        employees[i]->display();
        totalSalary += employees[i]->calculateSalary(); // Accumulate total salary
    }

    cout << "Total Salary of all employees: $" << totalSalary << endl;

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete employees[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Employee`**:
   - Contains a protected attribute `name`.
   - The constructor initializes the name.
   - Two pure virtual functions: `calculateSalary()` and `display()`, which must be implemented by derived classes.

2. **Derived Class `FullTimeEmployee`**:
   - Contains a private attribute for `monthlySalary`.
   - The constructor initializes the monthly salary along with the name.
   - The `calculateSalary()` method returns the fixed monthly salary.
   - The `display()` method outputs the employee's name and salary.

3. **Derived Class `PartTimeEmployee`**:
   - Contains private attributes for `hourlyRate` and `hoursWorked`.
   - The constructor initializes these attributes along with the name.
   - The `calculateSalary()` method calculates the salary based on hours worked and hourly rate.
   - The `display()` method outputs the employee's name, hourly rate, hours worked, and total salary.

4. **`main()` Function**:
   - Creates an array of `Employee` pointers and initializes it with instances of `FullTimeEmployee` and `PartTimeEmployee`.
   - A loop iterates through the array, displaying details of each employee and calculating the total salary.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---


### **Module 5: Inheritance and Polymorphism Question 9**

**Question**:  
Design a class hierarchy for a `MediaLibrary` system with a base class `MediaItem` and derived classes `Song` and `Podcast`. Each class should have methods to display details about the media item, including title, duration, and genre. Demonstrate polymorphism by creating an array of `MediaItem` pointers and displaying their details.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

// Base class
class MediaItem {
protected:
    string title;
    double duration; // in minutes

public:
    MediaItem(string t, double d) : title(t), duration(d) {}

    // Virtual function to display media details
    virtual void display() = 0; // Pure virtual function
    virtual ~MediaItem() {}
};

// Derived class Song
class Song : public MediaItem {
private:
    string artist;
    string genre;

public:
    Song(string t, double d, string a, string g) : MediaItem(t, d), artist(a), genre(g) {}

    // Implementing display method
    void display() override {
        cout << "Song Title: " << title << ", Artist: " << artist 
             << ", Duration: " << duration << " minutes, Genre: " << genre << endl;
    }
};

// Derived class Podcast
class Podcast : public MediaItem {
private:
    string host;

public:
    Podcast(string t, double d, string h) : MediaItem(t, d), host(h) {}

    // Implementing display method
    void display() override {
        cout << "Podcast Title: " << title << ", Host: " << host 
             << ", Duration: " << duration << " minutes" << endl;
    }
};

int main() {
    const int size = 4;
    MediaItem* mediaCollection[size]; // Array of MediaItem pointers

    // Creating Song and Podcast objects
    mediaCollection[0] = new Song("Shape of You", 4.5, "Ed Sheeran", "Pop"); // Song
    mediaCollection[1] = new Podcast("Tech Talk", 30.0, "Alice Smith"); // Podcast
    mediaCollection[2] = new Song("Blinding Lights", 3.5, "The Weeknd", "R&B"); // Another Song
    mediaCollection[3] = new Podcast("History Hour", 45.0, "John Doe"); // Another Podcast

    // Displaying media details
    for (int i = 0; i < size; i++) {
        mediaCollection[i]->display();
    }

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete mediaCollection[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `MediaItem`**:
   - Contains protected attributes `title` and `duration`.
   - The constructor initializes these attributes.
   - A pure virtual function `display()` enforces that derived classes must implement this method.

2. **Derived Class `Song`**:
   - Contains private attributes for `artist` and `genre`.
   - The constructor initializes these attributes along with the title and duration.
   - The `display()` method outputs the song's title, artist, duration, and genre.

3. **Derived Class `Podcast`**:
   - Contains a private attribute for `host`.
   - The constructor initializes the host along with the title and duration.
   - The `display()` method outputs the podcast's title, host, and duration.

4. **`main()` Function**:
   - Creates an array of `MediaItem` pointers and initializes it with instances of `Song` and `Podcast`.
   - A loop iterates through the array, displaying the details of each media item.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---



### **Module 5: Inheritance and Polymorphism Question 10**

**Question**:  
Create a class hierarchy for a `Vehicle` system with a base class `Vehicle` and derived classes `Car` and `Motorcycle`. Each class should have methods to display details about the vehicle, including make, model, and year. Demonstrate polymorphism by creating an array of `Vehicle` pointers and displaying their details.

**Program**:
```cpp
#include <iostream>
#include <string>
using namespace std;

// Base class
class Vehicle {
protected:
    string make;
    string model;
    int year;

public:
    Vehicle(string m, string mo, int y) : make(m), model(mo), year(y) {}

    // Virtual function to display vehicle details
    virtual void display() = 0; // Pure virtual function
    virtual ~Vehicle() {}
};

// Derived class Car
class Car : public Vehicle {
private:
    int doors;

public:
    Car(string m, string mo, int y, int d) : Vehicle(m, mo, y), doors(d) {}

    // Implementing display method
    void display() override {
        cout << "Car Make: " << make << ", Model: " << model 
             << ", Year: " << year << ", Doors: " << doors << endl;
    }
};

// Derived class Motorcycle
class Motorcycle : public Vehicle {
private:
    bool hasSidecar;

public:
    Motorcycle(string m, string mo, int y, bool sidecar) 
        : Vehicle(m, mo, y), hasSidecar(sidecar) {}

    // Implementing display method
    void display() override {
        cout << "Motorcycle Make: " << make << ", Model: " << model 
             << ", Year: " << year << ", Has Sidecar: " << (hasSidecar ? "Yes" : "No") << endl;
    }
};

int main() {
    const int size = 4;
    Vehicle* vehicles[size]; // Array of Vehicle pointers

    // Creating Car and Motorcycle objects
    vehicles[0] = new Car("Toyota", "Camry", 2022, 4); // Car
    vehicles[1] = new Motorcycle("Harley-Davidson", "Street 750", 2020, false); // Motorcycle
    vehicles[2] = new Car("Honda", "Civic", 2021, 4); // Another Car
    vehicles[3] = new Motorcycle("Yamaha", "YZF-R3", 2019, true); // Another Motorcycle

    // Displaying vehicle details
    for (int i = 0; i < size; i++) {
        vehicles[i]->display();
    }

    // Clean up memory
    for (int i = 0; i < size; i++) {
        delete vehicles[i];
    }

    return 0;
}
```

### **Explanation of the Code**:
1. **Base Class `Vehicle`**:
   - Contains protected attributes `make`, `model`, and `year`.
   - The constructor initializes these attributes.
   - A pure virtual function `display()` enforces that derived classes must implement this method.

2. **Derived Class `Car`**:
   - Contains a private attribute for `doors`.
   - The constructor initializes the doors along with the make, model, and year.
   - The `display()` method outputs the car's make, model, year, and number of doors.

3. **Derived Class `Motorcycle`**:
   - Contains a private attribute for `hasSidecar`.
   - The constructor initializes the sidecar status along with the make, model, and year.
   - The `display()` method outputs the motorcycle's make, model, year, and whether it has a sidecar.

4. **`main()` Function**:
   - Creates an array of `Vehicle` pointers and initializes it with instances of `Car` and `Motorcycle`.
   - A loop iterates through the array, displaying the details of each vehicle.
   - Finally, it cleans up dynamically allocated memory using `delete`.

---


