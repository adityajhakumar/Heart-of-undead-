

### Programming Question

**1 Problem Statement**

Implement a program that utilizes multiple inheritance to assess John's health. 

Create a class `BMI` with weight and height as attributes and create a method `calculateBMI()` to compute the BMI. Create a class `BloodPressure` with systolic and diastolic pressures as attributes to compute health metrics.

In the `Health` class, inherit from both classes `BMI` and `BloodPressure` to provide an integrated health assessment. Input John's weight, height, systolic, and diastolic values and determine his health status.

Check health status based on Body Mass Index (BMI) and blood pressure readings:

- Underweight with Low Blood Pressure: `BMI < 18.5 && systolic < 90 && diastolic < 60`
- Healthy Weight with Normal Blood Pressure: `BMI >= 18.5 and BMI < 24.9 && systolic >= 90 && systolic <= 120 && diastolic >= 60 && diastolic <= 80`
- Overweight with High Blood Pressure: `BMI >= 25.0 and BMI < 29.9 && systolic > 120 && diastolic > 80`
- Health Risk Undefined: Otherwise

Note: `BMI = weight / height^2`

**Input format**:
- The first line of input consists of a double value, representing the individual's weight in kilograms.
- The second consists of a double value, representing the height in meters.
- The third line consists of an integer, representing the systolic blood pressure measurement.
- The fourth line consists of an integer, representing the diastolic blood pressure measurement.

**Output format**:
- The first line of output prints the BMI result, rounded off to two decimal places.
- The second line prints the overall health status.

**Code constraints**:
In this scenario, the test cases fall under the following constraints:
- `10.0 ≤ weight ≤ 100.0`
- `0.5 ≤ height ≤ 2.5`
- `40 ≤ systolic, diastolic blood pressure ≤ 200`

**Sample test cases**:
- Input 1:
  ```
  48.0
  1.65
  80
  50
  ```
  Output 1:
  ```
  BMI: 17.63
  Health Status: Underweight with Low Blood Pressure
  ```

- Input 2:
  ```
  70.0
  1.75
  115
  75
  ```
  Output 2:
  ```
  BMI: 22.86
  Health Status: Healthy Weight with Normal Blood Pressure
  ```

- Input 3:
  ```
  90.0
  1.70
  135
  90
  ```
  Output 3:
  ```
  BMI: 31.14
  Health Status: Health Risk Undefined
  ```

- Input 4:
  ```
  40.6
  1.26
  121
  92
  ```
  Output 4:
  ```
  BMI: 25.57
  Health Status: Overweight with High Blood Pressure
  ```

### C++ Code Implementation

```cpp
#include <iostream>
#include <iomanip> // For std::setprecision

// Class to calculate Body Mass Index
class BMI {
protected:
    double weight; // Weight in kilograms
    double height; // Height in meters
public:
    // Constructor to initialize weight and height
    BMI(double w, double h) : weight(w), height(h) {}

    // Method to calculate BMI
    double calculateBMI() {
        return weight / (height * height);
    }
};

// Class to represent Blood Pressure readings
class BloodPressure {
protected:
    int systolic;   // Systolic pressure
    int diastolic;  // Diastolic pressure
public:
    // Constructor to initialize blood pressure values
    BloodPressure(int s, int d) : systolic(s), diastolic(d) {}
};

// Class to assess overall health using multiple inheritance
class Health : public BMI, public BloodPressure {
public:
    // Constructor to initialize base classes
    Health(double w, double h, int s, int d) : BMI(w, h), BloodPressure(s, d) {}

    // Method to assess health status
    std::string assessHealth() {
        double bmi = calculateBMI(); // Calculate BMI
        std::string status;

        // Check health status based on BMI and blood pressure
        if (bmi < 18.5 && systolic < 90 && diastolic < 60) {
            status = "Underweight with Low Blood Pressure";
        } else if (bmi >= 18.5 && bmi < 24.9 && systolic >= 90 && systolic <= 120 && diastolic >= 60 && diastolic <= 80) {
            status = "Healthy Weight with Normal Blood Pressure";
        } else if (bmi >= 25.0 && bmi < 29.9 && systolic > 120 && diastolic > 80) {
            status = "Overweight with High Blood Pressure";
        } else {
            status = "Health Risk Undefined";
        }

        // Output BMI and health status
        std::cout << "BMI: " << std::fixed << std::setprecision(2) << bmi << std::endl;
        return status;
    }
};

int main() {
    double weight, height; // Variables for weight and height
    int systolic, diastolic; // Variables for blood pressure

    // Input data
    std::cin >> weight >> height >> systolic >> diastolic;

    // Create a Health object
    Health john(weight, height, systolic, diastolic);

    // Output health status
    std::cout << "Health Status: " << john.assessHealth() << std::endl;

    return 0;
}
```

### Explanation of the Code
- **BMI Class**: Contains attributes for weight and height, and a method to calculate BMI.
- **BloodPressure Class**: Contains attributes for systolic and diastolic pressures.
- **Health Class**: Inherits from both `BMI` and `BloodPressure` to combine their functionality. It includes a method to assess overall health status based on BMI and blood pressure readings.
- **Main Function**: Takes input for weight, height, and blood pressure values, creates a `Health` object, and outputs the health status.



### Programming Question

**2 Problem Statement**

Amar needs a program to calculate order costs with discount options for James. Help Amar to write a program that uses multi-level inheritance.

- **Class Order** - Holds item price, quantity, and discount as attributes.
- **Class FinalOrder** - Derived from Order class which calculates the total cost for James with a given item price, quantity, and discount percentage.
- **Class DiscountedOrder** - Derived from FinalOrder class which calculates the final cost for James, considering an additional discount on top of the regular discount.

**Formulas used**:
- Total Cost = (Item Price × Quantity) - (Item Price × Quantity × Discount / 100.0)
- Final Cost = (Total Cost) - (Total Cost × Additional Discount / 100.0)

**Input format**:
- The input consists of four space-separated values: a double value representing the price of the item, an integer representing the quantity of the item, a double value representing the discount to be applied, and a double value representing the additional discount to be applied.

**Output format**:
- The first line displays "Total Cost: " followed by a double value representing the total cost for James's order rounded to two decimal places.
- The second line displays "Final Cost: " followed by a double value representing the final cost for James's order, rounded to two decimal places.

**Code constraints**:
In this scenario, the test cases fall under the following constraints:
- `0.01 ≤ Price ≤ 1000`
- `1 ≤ Quantity ≤ 100`
- `0 ≤ Discount ≤ 100`
- `0 ≤ Additional Discount Percentage ≤ 100`

**Sample test cases**:
- Input 1:
  ```
  75.00 5 15.5 5.5
  ```
  Output 1:
  ```
  Total Cost: 316.88
  Final Cost: 299.45
  ```

- Input 2:
  ```
  45.00 7 1.5 6.7
  ```
  Output 2:
  ```
  Total Cost: 310.27
  Final Cost: 289.49
  ```

- Input 3:
  ```
  40.00 3 20.2 10.4
  ```
  Output 3:
  ```
  Total Cost: 95.76
  Final Cost: 85.80
  ```

### C++ Code Implementation

```cpp
#include <iostream>
#include <iomanip> // For std::setprecision

// Class to hold order details
class Order {
protected:
    double itemPrice; // Price of the item
    int quantity;     // Quantity of the item
    double discount;  // Discount percentage
public:
    // Constructor to initialize item price, quantity, and discount
    Order(double price, int qty, double disc) : itemPrice(price), quantity(qty), discount(disc) {}
};

// Class to calculate total cost, derived from Order
class FinalOrder : public Order {
public:
    // Constructor to initialize base class
    FinalOrder(double price, int qty, double disc) : Order(price, qty, disc) {}

    // Method to calculate total cost
    double calculateTotalCost() {
        return (itemPrice * quantity) - (itemPrice * quantity * discount / 100.0);
    }
};

// Class to calculate final cost, derived from FinalOrder
class DiscountedOrder : public FinalOrder {
private:
    double additionalDiscount; // Additional discount percentage
public:
    // Constructor to initialize base class and additional discount
    DiscountedOrder(double price, int qty, double disc, double addDisc)
        : FinalOrder(price, qty, disc), additionalDiscount(addDisc) {}

    // Method to calculate final cost
    double calculateFinalCost() {
        double totalCost = calculateTotalCost(); // Get total cost from FinalOrder
        return totalCost - (totalCost * additionalDiscount / 100.0);
    }
};

int main() {
    double price, discount, additionalDiscount; // Variables for item price, discount, additional discount
    int quantity; // Variable for quantity

    // Input data
    std::cin >> price >> quantity >> discount >> additionalDiscount;

    // Create a DiscountedOrder object
    DiscountedOrder order(price, quantity, discount, additionalDiscount);

    // Output total and final cost
    std::cout << "Total Cost: " << std::fixed << std::setprecision(2) << order.calculateTotalCost() << std::endl;
    std::cout << "Final Cost: " << std::fixed << std::setprecision(2) << order.calculateFinalCost() << std::endl;

    return 0;
}
```

### Explanation of the Code
- **Order Class**: Contains attributes for item price, quantity, and discount percentage. It has a constructor for initialization.
- **FinalOrder Class**: Inherits from `Order` and includes a method to calculate the total cost based on the formula provided.
- **DiscountedOrder Class**: Inherits from `FinalOrder` and includes an additional discount attribute. It contains a method to calculate the final cost after applying the additional discount.
- **Main Function**: Takes input for the item price, quantity, discount, and additional discount, creates a `DiscountedOrder` object, and prints the total and final costs. 




### Programming Question

**3  Problem Statement**

You are given the cost and discount values for an electronic gadget and a mechanical device. 

Write a program using hierarchical inheritance to calculate their total costs after applying the discounts and print these costs.

The program should contain three classes:
- **Class Product** - Contains cost and discount attributes.
- **Class ElectronicGadget** - Derived from Product class for electronic gadgets with a method `calcTotalE()` to calculate the total cost specific to electronic gadgets.
- **Class MechanicalDevice** - Derived from Product class for mechanical devices with a method `calcTotalM()` to calculate the total cost specific to mechanical devices.

**Formula**: 
- Total Cost = Cost - (Cost * Discount)

**Input format**:
- The first line of input consists of two space-separated double values, representing the cost and discount for an electronic gadget.
- The second line consists of two space-separated double values, representing the cost and discount for a mechanical device.

**Output format**:
- The first line of output prints "Electronic Cost: Rs. X" where X is a double value, rounded off to two decimal places.
- The second line prints "Mechanical Cost: Rs. Y" where Y is a double value, rounded off to two decimal places.

**Code constraints**:
In this scenario, the test cases fall under the following constraints:
- `1.00 ≤ cost ≤ 5000.00`
- `0.00 ≤ discount ≤ 0.99`

**Sample test cases**:
- Input 1:
  ```
  100.5 0.5
  200.0 0.1
  ```
  Output 1:
  ```
  Electronic Cost: Rs. 50.25
  Mechanical Cost: Rs. 180.00
  ```

- Input 2:
  ```
  350.5 0.09
  759.4 0.15
  ```
  Output 2:
  ```
  Electronic Cost: Rs. 318.95
  Mechanical Cost: Rs. 645.49
  ```

### C++ Code Implementation

```cpp
#include <iostream>
#include <iomanip> // For std::setprecision

// Class to represent a product with cost and discount attributes
class Product {
protected:
    double cost;     // Cost of the product
    double discount; // Discount on the product
public:
    // Constructor to initialize cost and discount
    Product(double c, double d) : cost(c), discount(d) {}
};

// Class for Electronic Gadgets, derived from Product
class ElectronicGadget : public Product {
public:
    // Constructor to initialize base class
    ElectronicGadget(double c, double d) : Product(c, d) {}

    // Method to calculate total cost for electronic gadgets
    double calcTotalE() {
        return cost - (cost * discount); // Calculate total cost after discount
    }
};

// Class for Mechanical Devices, derived from Product
class MechanicalDevice : public Product {
public:
    // Constructor to initialize base class
    MechanicalDevice(double c, double d) : Product(c, d) {}

    // Method to calculate total cost for mechanical devices
    double calcTotalM() {
        return cost - (cost * discount); // Calculate total cost after discount
    }
};

int main() {
    double eCost, eDiscount; // Variables for electronic gadget cost and discount
    double mCost, mDiscount; // Variables for mechanical device cost and discount

    // Input data for electronic gadget
    std::cin >> eCost >> eDiscount;

    // Input data for mechanical device
    std::cin >> mCost >> mDiscount;

    // Create objects for electronic gadget and mechanical device
    ElectronicGadget gadget(eCost, eDiscount);
    MechanicalDevice device(mCost, mDiscount);

    // Output total costs
    std::cout << "Electronic Cost: Rs. " << std::fixed << std::setprecision(2) << gadget.calcTotalE() << std::endl;
    std::cout << "Mechanical Cost: Rs. " << std::fixed << std::setprecision(2) << device.calcTotalM() << std::endl;

    return 0;
}
```

### Explanation of the Code
- **Product Class**: This base class contains attributes for the cost and discount of a product, with a constructor to initialize these attributes.
- **ElectronicGadget Class**: This class derives from `Product` and includes a method to calculate the total cost after applying the discount specific to electronic gadgets.
- **MechanicalDevice Class**: This class also derives from `Product` and includes a method to calculate the total cost after applying the discount specific to mechanical devices.
- **Main Function**: Takes input for the costs and discounts of both the electronic gadget and mechanical device, creates corresponding objects, and prints the calculated total costs.



### Programming Question

**4 Problem Statement**

Help Sammy create a program to calculate rental costs for a car and a truck based on mileage and fuel consumption. The program defines three classes:

- **Vehicle class** - Contains mileage covered and fuel consumed as attributes.
- **Car class** - Derived from Vehicle class and calculates the total rental cost for the Car using `calculateCarCost()` method.
- **Truck class** - Derived from Vehicle class and calculates the total rental cost for the Truck using `calculateTruckCost()` method.
- **Rental class** - Derived from Car and Truck class and displays the rental costs for both vehicles using `printCosts()` method.

**Formulas:**
- Car rental cost = \(0.1 \times X + 2.0 \times Y\)  
  Where \(X\) - Mileage covered by the Car; \(Y\) - Fuel consumed by the Car.
- Truck rental cost = \(1.5 \times (0.1 \times A + 2.0 \times B)\)  
  Where \(A\) - Mileage covered by the Truck; \(B\) - Fuel consumed by the Truck.

**Input format:**
- The first line consists of a double value representing the mileage covered by the car.
- The second line consists of a double value representing the fuel consumed by the car.
- The third line consists of a double value representing the mileage covered by the truck.
- The fourth line consists of a double value representing the fuel consumed by the truck.

**Output format:**
- The first line displays "Total Rental Cost for Car: " followed by the total rental cost for the car.
- The second line displays "Total Rental Cost for Truck: " followed by the total rental cost for the truck.

The rental costs are double values that are rounded to two decimal places.

**Code constraints:**
In this scenario, the test cases fall under the following constraints:
- \(0 \leq\) Mileage covered by car and truck \(\leq 100\)
- \(0 \leq\) Fuel consumed by Car and Truck \(\leq 50\)

**Sample test cases:**
- Input 1:
  ```
  25.5
  12.5
  45.0
  18.0
  ```
  Output 1:
  ```
  Total Rental Cost for Car: 27.55
  Total Rental Cost for Truck: 60.75
  ```

- Input 2:
  ```
  10.0
  5.0
  20.0
  15.0
  ```
  Output 2:
  ```
  Total Rental Cost for Car: 11.00
  Total Rental Cost for Truck: 48.00
  ```

- Input 3:
  ```
  5.5
  2.0
  10.0
  5.0
  ```
  Output 3:
  ```
  Total Rental Cost for Car: 4.55
  Total Rental Cost for Truck: 16.50
  ```

### C++ Code Implementation

```cpp
#include <iostream>
#include <iomanip> // For std::setprecision

// Vehicle class containing mileage covered and fuel consumed attributes
class Vehicle {
protected:
    double mileage; // Mileage covered
    double fuel;    // Fuel consumed

public:
    // Constructor to initialize mileage and fuel
    Vehicle(double m, double f) : mileage(m), fuel(f) {}
};

// Car class derived from Vehicle to calculate rental cost for a car
class Car : public Vehicle {
public:
    // Constructor to initialize the base class
    Car(double m, double f) : Vehicle(m, f) {}

    // Method to calculate the rental cost for the car
    double calculateCarCost() {
        return (0.1 * mileage) + (2.0 * fuel);
    }
};

// Truck class derived from Vehicle to calculate rental cost for a truck
class Truck : public Vehicle {
public:
    // Constructor to initialize the base class
    Truck(double m, double f) : Vehicle(m, f) {}

    // Method to calculate the rental cost for the truck
    double calculateTruckCost() {
        return 1.5 * ((0.1 * mileage) + (2.0 * fuel));
    }
};

// Rental class derived from Car and Truck to print rental costs
class Rental : public Car, public Truck {
public:
    // Constructor to initialize the base classes
    Rental(double carMileage, double carFuel, double truckMileage, double truckFuel)
        : Car(carMileage, carFuel), Truck(truckMileage, truckFuel) {}

    // Method to print the costs of both vehicles
    void printCosts() {
        std::cout << "Total Rental Cost for Car: " << std::fixed << std::setprecision(2) 
                  << calculateCarCost() << std::endl;
        std::cout << "Total Rental Cost for Truck: " << std::fixed << std::setprecision(2) 
                  << calculateTruckCost() << std::endl;
    }
};

int main() {
    double carMileage, carFuel;     // Variables for car mileage and fuel
    double truckMileage, truckFuel; // Variables for truck mileage and fuel

    // Input data for car mileage and fuel
    std::cin >> carMileage >> carFuel;

    // Input data for truck mileage and fuel
    std::cin >> truckMileage >> truckFuel;

    // Create a Rental object using the input data
    Rental rental(carMileage, carFuel, truckMileage, truckFuel);

    // Output the rental costs for both vehicles
    rental.printCosts();

    return 0;
}
```

### Notes
- The program uses `#include <iomanip>` to set the output precision to two decimal places.
- It calculates rental costs based on the mileage and fuel consumption for both vehicles using the specified formulas.
- The output is formatted as required, and input handling is done through standard input.
