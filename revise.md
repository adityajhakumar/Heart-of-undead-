Single File Programming Question
Problem Statement



Create a program for converting distances from meters to feet and yards. The program includes a base class 'Distance', which stores the distance value in meters, and two derived classes: 'FeetDistance' and 'YardDistance'. Each derived class performs a specific conversion and displays the converted distance.



Class Hierarchy:



The base class 'Distance' contains a distance value in meters and provides a method to display it in meters.



The derived class 'FeetDistance' calculates and displays the distance in feet based on the conversion factor (1 meter = 3.28084 feet).



The derived class 'YardDistance' calculates and displays the distance in yards based on the conversion factor (1 meter = 1.09361 yards).

Input format :
The input consists of a single line with a double-precision floating-point value representing the distance in meters.

Output format :
The program displays the following information:



"Distance in meters: X m", where X is the distance in meters with two decimal places.

"Distance in feet: Y ft", where Y is the distance in feet with two decimal places, converted from meters using a conversion factor of 3.28084.

"Distance in yards: Z yd", where Z is the distance in yards with two decimal places, converted from meters using a conversion factor of 1.09361.



Refer to the sample outputs for the formatting specifications.

Code constraints :
1.0 <= distance In Meters <= 1000.0

Sample test cases :
Input 1 :
5.00
Output 1 :
Distance in meters: 5.00m
Distance in feet: 16.40ft
Distance in yards: 5.47yd
Input 2 :
10.25
Output 2 :
Distance in meters: 10.25m
Distance in feet: 33.63ft
Distance in yards: 11.21yd
Note :
The program will be evaluated only after the “Submit Code” is clicked.
Extra spaces and new line characters in the program output will result in the failure of the test case.

Here's a simple C++ program that meets the requirements. I've used assignment in the constructors, as requested, and included comments to explain each part of the code.

```cpp
#include <iostream>
#include <iomanip> // for controlling output formatting
using namespace std;

// Base class 'Distance' that stores the distance in meters
class Distance {
protected:
    double meters; // distance value in meters

public:
    // Constructor to initialize meters using assignment
    Distance(double m) {
        meters = m; // assignment to member variable
    }

    // Function to display the distance in meters
    void displayMeters() const {
        cout << "Distance in meters: " << fixed << setprecision(2) << meters << "m" << endl;
    }
};

// Derived class 'FeetDistance' for converting and displaying distance in feet
class FeetDistance : public Distance {
public:
    // Constructor to initialize the base class 'Distance' with meters value
    FeetDistance(double m) : Distance(m) {}

    // Function to convert meters to feet and display
    void displayFeet() const {
        double feet = meters * 3.28084; // Conversion: 1 meter = 3.28084 feet
        cout << "Distance in feet: " << fixed << setprecision(2) << feet << "ft" << endl;
    }
};

// Derived class 'YardDistance' for converting and displaying distance in yards
class YardDistance : public Distance {
public:
    // Constructor to initialize the base class 'Distance' with meters value
    YardDistance(double m) : Distance(m) {}

    // Function to convert meters to yards and display
    void displayYards() const {
        double yards = meters * 1.09361; // Conversion: 1 meter = 1.09361 yards
        cout << "Distance in yards: " << fixed << setprecision(2) << yards << "yd" << endl;
    }
};

int main() {
    double meters;

    // Taking input for distance in meters from the user
    cin >> meters;

    // Creating objects of each derived class with the given meters value
    Distance baseDistance(meters);
    FeetDistance feetDistance(meters);
    YardDistance yardDistance(meters);

    // Displaying the distance in meters, feet, and yards
    baseDistance.displayMeters();
    feetDistance.displayFeet();
    yardDistance.displayYards();

    return 0;
}
```

### Explanation of the Code

1. **Class `Distance` (Base Class)**:
   - Stores the distance value in meters.
   - Constructor initializes the `meters` variable using assignment.
   - `displayMeters()` function prints the distance in meters with two decimal places.

2. **Class `FeetDistance` (Derived Class)**:
   - Inherits from `Distance`.
   - Constructor initializes the base class `Distance` with the provided meters value.
   - `displayFeet()` function calculates the distance in feet using the conversion factor \(1 \text{ meter} = 3.28084 \text{ feet}\) and displays it.

3. **Class `YardDistance` (Derived Class)**:
   - Inherits from `Distance`.
   - Constructor initializes the base class `Distance` with the provided meters value.
   - `displayYards()` function calculates the distance in yards using the conversion factor \(1 \text{ meter} = 1.09361 \text{ yards}\) and displays it.

4. **`main()` Function**:
   - Reads the distance in meters from user input.
   - Creates instances of `Distance`, `FeetDistance`, and `YardDistance`.
   - Calls the display functions to show the converted distances.

### Sample Output

For input `5.00`:

```
Distance in meters: 5.00m
Distance in feet: 16.40ft
Distance in yards: 5.47yd
```



### Problem Statement

Create a program that calculates and displays the area and perimeter of a rectangle and a square.

1. **Shape Class (Base Class)**:
   - `length` (protected): Represents the length of the shape.
   - `width` (protected): Represents the width of the shape.
   - `calculateArea()`: Computes and returns the area.
   - `calculatePerimeter()`: Computes and returns the perimeter.

2. **Rectangle Class (Inherits from Shape)**:
   - Constructor to initialize length and width.

3. **Square Class (Inherits from Shape)**:
   - Constructor to set length and width to the same value (side length).

### Solution Code in C++

```cpp
#include <iostream>
using namespace std;

// Base class 'Shape' that holds attributes for length and width
class Shape {
protected:
    double length; // length of the shape
    double width;  // width of the shape

public:
    // Function to calculate area (length * width)
    double calculateArea() const {
        return length * width;
    }

    // Function to calculate perimeter (2 * (length + width))
    double calculatePerimeter() const {
        return 2 * (length + width);
    }
};

// Derived class 'Rectangle' inheriting from 'Shape'
class Rectangle : public Shape {
public:
    // Constructor to initialize length and width of the rectangle
    Rectangle(double l, double w) {
        length = l; // set the length of the rectangle
        width = w;  // set the width of the rectangle
    }
};

// Derived class 'Square' inheriting from 'Shape'
class Square : public Shape {
public:
    // Constructor to set both length and width to the side of the square
    Square(double side) {
        length = side; // set length to side
        width = side;  // set width to side, making it a square
    }
};

int main() {
    double rectLength, rectWidth, squareSide;

    // Taking input for the dimensions of the rectangle and the side of the square
    cin >> rectLength >> rectWidth >> squareSide;

    // Create Rectangle and Square objects with given dimensions
    Rectangle rectangle(rectLength, rectWidth);
    Square square(squareSide);

    // Display the area and perimeter for the rectangle
    cout << "Rectangle Area: " << rectangle.calculateArea() << endl;
    cout << "Rectangle Perimeter: " << rectangle.calculatePerimeter() << endl;

    // Display the area and perimeter for the square
    cout << "Square Area: " << square.calculateArea() << endl;
    cout << "Square Perimeter: " << square.calculatePerimeter() << endl;

    return 0;
}
```

### Explanation of the Code

1. **Class `Shape` (Base Class)**:
   - **Attributes**: `length` and `width`, which are protected and accessible to derived classes.
   - **`calculateArea()`**: Returns the area by multiplying `length` and `width`.
   - **`calculatePerimeter()`**: Returns the perimeter using \(2 \times (\text{length} + \text{width})\).

2. **Class `Rectangle` (Derived Class)**:
   - **Constructor**: Accepts length and width as parameters and assigns them to the base class's `length` and `width` attributes.

3. **Class `Square` (Derived Class)**:
   - **Constructor**: Accepts the side length of the square and assigns it to both `length` and `width`, making it a square.

4. **`main()` Function**:
   - Reads user input for rectangle length, rectangle width, and square side.
   - Creates instances of `Rectangle` and `Square`.
   - Outputs the area and perimeter for both the rectangle and the square.

### Sample Output

**Input**:
```
5
3
4
```

**Output**:
```
Rectangle Area: 15
Rectangle Perimeter: 16
Square Area: 16
Square Perimeter: 16
```


Here’s a **README** and commented **C++ code** for the first question on converting a distance from meters to feet and yards.

---

### README

## Distance Conversion Program

### Description

This program converts a given distance in meters to both feet and yards, using a base class (`Distance`) and derived classes (`FeetDistance` and `YardDistance`). 

- **Distance Class**: Holds the distance in meters and displays it.
- **FeetDistance Class**: Inherits from `Distance` and converts the distance from meters to feet.
- **YardDistance Class**: Inherits from `Distance` and converts the distance from meters to yards.

### Formulae

1. **Feet Conversion**: `1 meter = 3.28084 feet`
2. **Yard Conversion**: `1 meter = 1.09361 yards`

### Input/Output Format

- **Input**: A single double value representing distance in meters.
- **Output**:
  ```
  Distance in meters: X m
  Distance in feet: Y ft
  Distance in yards: Z yd
  ```
  All output values are rounded to two decimal places.

### Example
**Input**:
```
5.00
```

**Output**:
```
Distance in meters: 5.00 m
Distance in feet: 16.40 ft
Distance in yards: 5.47 yd
```

### Constraints
- \(1.0 \leq \text{distance in meters} \leq 1000.0\)

---

### Code

```cpp
#include <iostream>
#include <iomanip> // for setting precision in output
using namespace std;

// Base class 'Distance' for storing and displaying distance in meters
class Distance {
protected:
    double meters; // stores the distance in meters

public:
    // Constructor to initialize distance in meters
    Distance(double m) : meters(m) {}

    // Method to display distance in meters
    void displayMeters() const {
        cout << fixed << setprecision(2); // setting precision to 2 decimal places
        cout << "Distance in meters: " << meters << " m" << endl;
    }
};

// Derived class 'FeetDistance' for converting meters to feet
class FeetDistance : public Distance {
public:
    // Constructor to initialize distance in meters for FeetDistance
    FeetDistance(double m) : Distance(m) {}

    // Method to calculate and display distance in feet
    void displayFeet() const {
        double feet = meters * 3.28084; // conversion factor
        cout << "Distance in feet: " << feet << " ft" << endl;
    }
};

// Derived class 'YardDistance' for converting meters to yards
class YardDistance : public Distance {
public:
    // Constructor to initialize distance in meters for YardDistance
    YardDistance(double m) : Distance(m) {}

    // Method to calculate and display distance in yards
    void displayYards() const {
        double yards = meters * 1.09361; // conversion factor
        cout << "Distance in yards: " << yards << " yd" << endl;
    }
};

int main() {
    double meters;

    // Taking input for distance in meters
    cin >> meters;

    // Creating objects for each class with the same distance
    Distance distance(meters);
    FeetDistance feetDistance(meters);
    YardDistance yardDistance(meters);

    // Displaying the distance in meters, feet, and yards
    distance.displayMeters();
    feetDistance.displayFeet();
    yardDistance.displayYards();

    return 0;
}
```

### Code Explanation

1. **Base Class `Distance`**:
   - Stores the distance in meters.
   - Has a method `displayMeters()` to show the distance in meters.

2. **Derived Class `FeetDistance`**:
   - Inherits from `Distance`.
   - Has a method `displayFeet()` which converts meters to feet using the factor `3.28084` and displays the result.

3. **Derived Class `YardDistance`**:
   - Inherits from `Distance`.
   - Has a method `displayYards()` which converts meters to yards using the factor `1.09361` and displays the result.

4. **Main Function**:
   - Reads a distance in meters.
   - Creates objects for `Distance`, `FeetDistance`, and `YardDistance`.
   - Calls display methods on each object to output distances in meters, feet, and yards.

---




Here's the **README** and the corresponding **C++ code** with comments for the third question on converting distances from meters to feet and yards.

---

### README

## Distance Conversion Program

### Description

This program converts a given distance in meters to both feet and yards. It uses a base class and derived classes to handle different conversions and display the results.

- **Distance Class**: Stores the input distance in meters and has a method to display it in meters.
- **FeetDistance Class**: Inherits from `Distance` and calculates the distance in feet.
- **YardDistance Class**: Inherits from `Distance` and calculates the distance in yards.

### Conversion Factors

- **Meters to Feet**: `1 meter = 3.28084 feet`
- **Meters to Yards**: `1 meter = 1.09361 yards`

### Input/Output Format

- **Input**: A single line containing a double value representing the distance in meters.
- **Output**:
  - The program outputs the following:
    ```
    Distance in meters: X m
    Distance in feet: Y ft
    Distance in yards: Z yd
    ```
  - Each value should be rounded to two decimal places.

### Example
**Input**:
```
5.00
```

**Output**:
```
Distance in meters: 5.00 m
Distance in feet: 16.40 ft
Distance in yards: 5.47 yd
```

### Constraints
- \(1.0 \leq \text{distance in meters} \leq 1000.0\)

---

### Code

```cpp
#include <iostream>
#include <iomanip> // for controlling output formatting
using namespace std;

// Base class 'Distance' that stores the distance in meters
class Distance {
protected:
    double meters; // distance in meters, accessible to derived classes

public:
    // Constructor to initialize the distance in meters
    Distance(double m) : meters(m) {}

    // Function to display distance in meters with two decimal places
    void displayMeters() const {
        cout << "Distance in meters: " << fixed << setprecision(2) << meters << " m" << endl;
    }
};

// Derived class 'FeetDistance' that converts meters to feet
class FeetDistance : public Distance {
public:
    // Constructor to initialize the distance in meters in the base class
    FeetDistance(double m) : Distance(m) {}

    // Function to calculate and display distance in feet
    void displayFeet() const {
        double feet = meters * 3.28084; // Conversion factor
        cout << "Distance in feet: " << fixed << setprecision(2) << feet << " ft" << endl;
    }
};

// Derived class 'YardDistance' that converts meters to yards
class YardDistance : public Distance {
public:
    // Constructor to initialize the distance in meters in the base class
    YardDistance(double m) : Distance(m) {}

    // Function to calculate and display distance in yards
    void displayYards() const {
        double yards = meters * 1.09361; // Conversion factor
        cout << "Distance in yards: " << fixed << setprecision(2) << yards << " yd" << endl;
    }
};

int main() {
    double meters;

    // Taking input for the distance in meters from the user
    cin >> meters;

    // Creating objects for each conversion class
    Distance dist(meters);
    FeetDistance feetDist(meters);
    YardDistance yardDist(meters);

    // Displaying distances in different units
    dist.displayMeters();
    feetDist.displayFeet();
    yardDist.displayYards();

    return 0;
}
```

---

### Code Explanation

1. **Distance Class**:
   - **Purpose**: Stores the distance in meters.
   - **Methods**: `displayMeters()` prints the distance in meters.

2. **FeetDistance Class**:
   - **Purpose**: Inherits from `Distance` and converts meters to feet.
   - **Methods**: `displayFeet()` calculates and displays the distance in feet using the conversion factor `3.28084`.

3. **YardDistance Class**:
   - **Purpose**: Inherits from `Distance` and converts meters to yards.
   - **Methods**: `displayYards()` calculates and displays the distance in yards using the conversion factor `1.09361`.

4. **Main Function**:
   - Reads the distance in meters.
   - Creates instances of `Distance`, `FeetDistance`, and `YardDistance`.
   - Calls `displayMeters()`, `displayFeet()`, and `displayYards()` to print the distance in each unit. 

This structure maintains readability and modularity by handling each conversion separately through inheritance.



Here's the C++ code with comments for Question 1, where we calculate rental costs for a car and a motorcycle using inheritance.

### C++ Code with Comments

```cpp
#include <iostream>
#include <iomanip> // for setting decimal precision

// Base class for vehicles
class Vehicle {
protected:
    double dailyRate; // stores the daily rental rate for the vehicle

public:
    // Constructor to initialize the daily rate
    Vehicle(double rate) : dailyRate(rate) {}

    // Pure virtual function for calculating rental cost
    virtual double calculateCost(int days) = 0; // to be defined in derived classes
};

// Derived class for Car that inherits from Vehicle
class Car : public Vehicle {
public:
    // Constructor to initialize daily rate for Car
    Car(double rate) : Vehicle(rate) {}

    // Override calculateCost to compute cost for Car (no discount)
    double calculateCost(int days) override {
        return dailyRate * days; // rental cost formula for car
    }
};

// Derived class for Motorcycle that inherits from Vehicle
class Motorcycle : public Vehicle {
public:
    // Constructor to initialize daily rate for Motorcycle
    Motorcycle(double rate) : Vehicle(rate) {}

    // Override calculateCost to compute cost for Motorcycle with discount
    double calculateCost(int days) override {
        return dailyRate * days * 0.8; // rental cost formula for motorcycle with 20% discount
    }
};

int main() {
    double carRate, motorcycleRate;
    int carDays, motorcycleDays;

    // Input daily rental rates and number of days for each vehicle
    std::cin >> carRate >> motorcycleRate >> carDays >> motorcycleDays;

    // Create objects for Car and Motorcycle with specified rates
    Car car(carRate);
    Motorcycle motorcycle(motorcycleRate);

    // Calculate rental costs using the calculateCost method
    double carCost = car.calculateCost(carDays);
    double motorcycleCost = motorcycle.calculateCost(motorcycleDays);

    // Set output precision to 2 decimal places and display the results
    std::cout << std::fixed << std::setprecision(2);
    std::cout << "Total rental cost for the car: " << carCost << std::endl;
    std::cout << "Total rental cost for the motorcycle: " << motorcycleCost << std::endl;

    return 0;
}
```

### Explanation

- **Vehicle Class**: This is the base class containing a protected member `dailyRate` for the rental rate and a pure virtual function `calculateCost(int days)` that will be overridden by derived classes.
- **Car Class**: This derived class inherits from `Vehicle`. It implements `calculateCost(int days)` without a discount, simply multiplying `dailyRate` by the number of days.
- **Motorcycle Class**: Inherits from `Vehicle` and overrides `calculateCost(int days)` to apply a 20% discount by multiplying `dailyRate * days * 0.8`.
- **Main Function**:
  - Inputs are taken for the car and motorcycle daily rates and the number of days each is rented.
  - Objects for `Car` and `Motorcycle` are created with their respective rates.
  - The program then calculates and prints the total rental cost for both vehicles, formatted to two decimal places.

### Sample Input and Output

For input:
```
10.2
8.3
3
2
```

The output will be:
```
Total rental cost for the car: 30.60
Total rental cost for the motorcycle: 13.28
```

This solution meets the problem’s requirements with clear and organized comments for understanding.

Here's the C++ code with comments for Question 2, where we calculate the hypotenuse, area, and perimeter of a right triangle using inheritance.

### C++ Code with Comments

```cpp
#include <iostream>
#include <iomanip> // for setting decimal precision
#include <cmath>   // for sqrt function

// Base class for Triangle
class Triangle {
protected:
    double height, base; // height and base of the triangle

public:
    // Constructor to initialize height and base
    Triangle(double h, double b) : height(h), base(b) {}

    // Function to calculate the area of the triangle
    double calculateArea() {
        return 0.5 * height * base; // area formula: (1/2) * base * height
    }
};

// Derived class Hypotenuse that inherits from Triangle
class Hypotenuse : public Triangle {
public:
    // Constructor to initialize height and base, passing them to the base class constructor
    Hypotenuse(double h, double b) : Triangle(h, b) {}

    // Function to calculate the hypotenuse of the triangle using Pythagorean theorem
    double calculateHypotenuse() {
        return sqrt(height * height + base * base); // hypotenuse formula: sqrt(a^2 + b^2)
    }

    // Function to calculate the perimeter of the triangle
    double calculatePerimeter() {
        return height + base + calculateHypotenuse(); // perimeter formula: height + base + hypotenuse
    }
};

int main() {
    double height, base;

    // Input height and base of the triangle
    std::cin >> height >> base;

    // Create a Hypotenuse object (which is also a Triangle object)
    Hypotenuse triangle(height, base);

    // Calculate the hypotenuse, area, and perimeter of the triangle
    double hypotenuse = triangle.calculateHypotenuse();
    double area = triangle.calculateArea();
    double perimeter = triangle.calculatePerimeter();

    // Output results with 2 decimal precision
    std::cout << std::fixed << std::setprecision(2);
    std::cout << "Hypotenuse: " << hypotenuse << std::endl;
    std::cout << "Area: " << area << std::endl;
    std::cout << "Perimeter: " << perimeter << std::endl;

    return 0;
}
```

### Explanation

- **Triangle Class**: This base class takes `height` and `base` as inputs and has a function `calculateArea()` to compute the area of the triangle.
- **Hypotenuse Class**: Inherits from `Triangle` and adds two additional methods:
  - `calculateHypotenuse()`: Calculates the hypotenuse using the formula \( \text{sqrt(height}^2 + \text{base}^2) \).
  - `calculatePerimeter()`: Calculates the perimeter by adding `height`, `base`, and `hypotenuse`.
- **Main Function**:
  - Takes inputs for the height and base of the triangle.
  - Creates a `Hypotenuse` object, which initializes both `height` and `base` in the `Triangle` class.
  - Calls `calculateHypotenuse()`, `calculateArea()`, and `calculatePerimeter()` to compute respective values.
  - Outputs the hypotenuse, area, and perimeter, formatted to two decimal places.

### Sample Input and Output

Given input:
```
10.0 15.5
```

Output will be:
```
Hypotenuse: 18.45
Area: 77.50
Perimeter: 43.95
```

This solution follows the requirements and includes detailed comments explaining each part of the code.


Certainly! Below is the complete README document with both questions, including the problem statements, code solutions, and detailed comments for each.

---

# Electricity and Vehicle Range Calculation Programs

## Question 1: Vehicle and Car Range Calculation

### Problem Statement

Joe is developing a program to compare the estimated ranges of gas and electric cars. He wants to help users make informed decisions about purchasing these vehicles. Help him write a program using multi-level inheritance.

The program features three classes:
- **class Vehicle**: Holds fuel capacity as an attribute.
- **class Car**: Derived from class Vehicle, it holds fuel efficiency as an attribute and calculates its range based on fuel capacity and efficiency.
- **class ElectricCar**: Derived from class Car, it holds battery capacity as an attribute and calculates its range using battery capacity and fuel efficiency.

### Formulas
- Car range = Fuel efficiency * Fuel capacity
- Electric car range = Battery capacity * Fuel efficiency

### Input Format
- The first line of input consists of two space-separated double values, representing the fuel capacity and fuel efficiency of a car.
- The second line consists of two space-separated double values, representing the battery capacity and fuel efficiency of an electric car.

### Output Format
- The first line of output prints "Car Estimated Range: X miles" where X is a double value, rounded off to one decimal place.
- The second line prints "Electric Car Estimated Range: Y miles" where Y is a double value, rounded off to one decimal place.

### Sample Test Cases
#### Input 1
```
10.5 25.0
80.0 4.5
```
#### Output 1
```
Car Estimated Range: 262.5 miles
Electric Car Estimated Range: 360.0 miles
```

#### Input 2
```
8.0 28.0
50.0 4.00
```
#### Output 2
```
Car Estimated Range: 224.0 miles
Electric Car Estimated Range: 200.0 miles
```

### Code Solution

```cpp
#include <iostream>
#include <iomanip>
using namespace std;

// Base class Vehicle
class Vehicle {
protected:
    double fuelCapacity; // Fuel capacity of the vehicle
public:
    // Constructor for Vehicle class
    Vehicle(double capacity) : fuelCapacity(capacity) {}
};

// Derived class Car inherits from Vehicle
class Car : public Vehicle {
protected:
    double fuelEfficiency; // Fuel efficiency of the car (miles per gallon)
public:
    // Constructor for Car class, initializing both fuelCapacity and fuelEfficiency
    Car(double capacity, double efficiency) : Vehicle(capacity), fuelEfficiency(efficiency) {}

    // Method to calculate and return the car's range
    double calculateRange() {
        return fuelCapacity * fuelEfficiency; // Range = fuel capacity * fuel efficiency
    }
};

// Derived class ElectricCar inherits from Car
class ElectricCar : public Car {
private:
    double batteryCapacity; // Battery capacity for the electric car
public:
    // Constructor for ElectricCar, initializing batteryCapacity as well as inherited attributes
    ElectricCar(double capacity, double efficiency, double batteryCap) 
        : Car(capacity, efficiency), batteryCapacity(batteryCap) {}

    // Method to calculate and return the electric car's range
    double calculateElectricRange() {
        return batteryCapacity * fuelEfficiency; // Range = battery capacity * fuel efficiency
    }
};

int main() {
    // Input values for fuel capacity and fuel efficiency for a gas car
    double fuelCapacity, fuelEfficiency;
    cin >> fuelCapacity >> fuelEfficiency;

    // Input values for battery capacity and fuel efficiency for an electric car
    double batteryCapacity, electricEfficiency;
    cin >> batteryCapacity >> electricEfficiency;

    // Create a Car object and calculate its range
    Car car(fuelCapacity, fuelEfficiency);
    double carRange = car.calculateRange();

    // Create an ElectricCar object and calculate its range
    ElectricCar electricCar(0, electricEfficiency, batteryCapacity); // Fuel capacity set to 0 as it's irrelevant for electric car
    double electricRange = electricCar.calculateElectricRange();

    // Output results with one decimal precision
    cout << fixed << setprecision(1);
    cout << "Car Estimated Range: " << carRange << " miles" << endl;
    cout << "Electric Car Estimated Range: " << electricRange << " miles" << endl;

    return 0;
}
```

---

## Question 2: Electricity Bill Calculation

### Problem Statement

Design a program with an `ElectricityBill` class that utilizes multiple inheritance, inheriting attributes from `Consumption`, `Rate`, and `ConnectionType` classes. This program calculates the total cost of electricity consumption based on consumed units and connection type, which can be either residential or commercial, each with its respective rate.

### Calculation Formula
- **Total Electricity Consumption Cost** = Units * Rate (where the rate is determined by the connection type)

### Input Format
- The first line of input consists of a double value, representing the total electricity units consumed.
- The second line consists of a double value, representing the rate per unit for residential connections.
- The third line consists of a double value, representing the rate per unit for commercial connections.
- The fourth line consists of a string, representing the Connection type (residential or commercial).

### Output Format
- The output displays the total electricity cost rounded to two decimal places.

### Sample Test Cases
#### Input 1
```
80.00
0.15
0.18
commercial
```
#### Output 1
```
14.40
```

#### Input 2
```
80.05
0.15
0.18
residential
```
#### Output 2
```
12.01
```

#### Input 3
```
100.00
0.70
0.80
residential
```
#### Output 3
```
70.00
```

### Code Solution

```cpp
#include <iostream>
#include <iomanip>
#include <string>
using namespace std;

// Class Consumption holds the total units of electricity consumed
class Consumption {
protected:
    double units; // Total units of electricity consumed
public:
    // Constructor to initialize units
    Consumption(double u) : units(u) {}
};

// Class Rate holds the rate per unit for both residential and commercial connections
class Rate {
protected:
    double residentialRate; // Rate per unit for residential connections
    double commercialRate;  // Rate per unit for commercial connections
public:
    // Constructor to initialize residential and commercial rates
    Rate(double rRate, double cRate) : residentialRate(rRate), commercialRate(cRate) {}
};

// Class ConnectionType holds the type of connection (residential or commercial)
class ConnectionType {
protected:
    string connectionType; // Type of connection: residential or commercial
public:
    // Constructor to initialize connection type
    ConnectionType(string type) : connectionType(type) {}
};

// ElectricityBill class inherits from Consumption, Rate, and ConnectionType
class ElectricityBill : public Consumption, public Rate, public ConnectionType {
public:
    // Constructor initializes all attributes through multiple inheritance
    ElectricityBill(double u, double rRate, double cRate, string type) 
        : Consumption(u), Rate(rRate, cRate), ConnectionType(type) {}

    // Method to calculate the total cost of electricity consumption
    double calculateTotalCost() {
        // If connection type is residential, use residential rate
        if (connectionType == "residential") {
            return units * residentialRate; // Calculate cost for residential
        } 
        // Otherwise, use commercial rate
        else {
            return units * commercialRate; // Calculate cost for commercial
        }
    }
};

int main() {
    double units, residentialRate, commercialRate;
    string connectionType;

    // Input: total units, rates for residential and commercial, and connection type
    cin >> units >> residentialRate >> commercialRate >> connectionType;

    // Create an ElectricityBill object with the provided inputs
    ElectricityBill bill(units, residentialRate, commercialRate, connectionType);

    // Calculate total cost based on the connection type
    double totalCost = bill.calculateTotalCost();

    // Output the total cost, rounded to two decimal places
    cout << fixed << setprecision(2) << totalCost << endl;

    return 0;
}
```

### Explanation of the Code:

1. **Consumption Class**:
   - Stores `units`, representing the total electricity units consumed.
   - Constructor initializes the `units` attribute.

2. **Rate Class**:
   - Stores `residentialRate` and `commercialRate` for electricity rates per unit.
   - Constructor initializes both rates.

3. **ConnectionType Class**:
   - Stores `connectionType`, which could be either `"residential"` or `"commercial"`.
   - Constructor initializes `connectionType`.

4. **ElectricityBill Class**:
   - Inherits from `Consumption`, `Rate`, and `ConnectionType` using multiple inheritance.
   - Constructor initializes all attributes through the base class constructors.
   - Method `calculateTotalCost()`:
     - Checks the `connectionType`. If `"residential"`, it calculates the cost using `units * residentialRate`.
     - If `"commercial"`, it calculates the cost using `units * commercialRate`.

5. **Main Function**:
   - Reads input for units consumed, rates for residential and commercial, and connection type.
   - Creates an `ElectricityBill` object with these values.
   - Calls `calculateTotalCost()` to compute the electricity cost based on the connection type.
   - Outputs the total cost, formatted to two decimal places.

---

## Compilation and Execution

To compile and run each program, use the following commands in your terminal:

### For Vehicle Range Calculation:
```bash
g++ vehicle_range.cpp

 -o vehicle_range
./vehicle_range
```

### For Electricity Bill Calculation:
```bash
g++ electricity_bill.cpp -o electricity_bill
./electricity_bill
```

Ensure to replace `vehicle_range.cpp` and `electricity_bill.cpp` with the actual names of your source files.

--- 

Feel free to adjust any parts of the README to better fit your project's needs!

