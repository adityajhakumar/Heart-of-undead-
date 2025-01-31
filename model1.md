**MODEL CAT1 - DETAILED SOLUTIONS**

---

### **Continuous Assessment Test (CAT) – I**
#### **Programme:** B. Tech. (ECE/ECM)  
#### **Course Code & Title:** BECE320E - Embedded C Programming  
#### **Faculty:** Prof. Srinivasan R  
#### **Duration:** 90 Minutes  
#### **Max. Marks:** 50  

---

## **Q1 (a) Write appropriate declarations in C and assign the given initial values for each variable or array.**

**Solution:**
```c
#include <stdio.h>

int main() {
    int u = 0421;  // Octal representation of 421
    int y = 0xfa01; // Hexadecimal representation of fa01
    char message[] = "We are safe.";
    double reading1 = 3.56218e-4;
    
    printf("u = %d, y = %d\n", u, y);
    printf("message = %s\n", message);
    printf("reading1 = %lf\n", reading1);
    return 0;
}
```

---

## **Q1 (b) Evaluate the given expression.**
**Expression:**
```c
float value = (i - 3 * j) % (c + 2 * d) / (x - y);
```
Given values:
```c
int i=10, j=5;
float x = 0.005, y = -0.01;
char c = ‘c’, d = ‘d’;  // ASCII values: c = 99, d = 100
```

### **Solution:**
Step-by-step evaluation:
- `c + 2 * d = 99 + (2 * 100) = 299`
- `i - 3 * j = 10 - 15 = -5`
- `(-5) % 299 = -5`
- `x - y = 0.005 - (-0.01) = 0.015`
- `-5 / 0.015 = -333.33`

**Final Output:** `value = -333.33`

---

## **Q2 (a) Output of given code?**
**Code:**
```c
#include <stdio.h>
int main() {
    int k, num = 65;
    k = (num > 5 ? (num <= 10 ? 100 : 200) : 500);
    printf("%c %d\n", num, k);
    return 0;
}
```

### **Solution:**
- `num = 65`, so `(num > 5)` is true.
- Check `(num <= 10)`, which is false.
- Hence, `k = 200`.
- `printf("%c %d\n", num, k);` prints: `A 200`

**Output:**
```
A 200
```

---

## **Q2 (b) Output of given code?**
**Code:**
```c
#include <stdio.h>
int main() {
    printf("Hello!\bHow are you?\n");
    printf("I\tam feeling good.\n");
    printf("I am feeling\r good\n");
    printf("\nI am feeling \\'good\'\n");
    return 0;
}
```

### **Solution:**
- `\b` removes last character (`Hello!How are you?`)
- `\t` adds tab spacing (`I   am feeling good.`)
- `\r` moves cursor to start (`good` overwrites part of text)
- `\\` prints `\`, `\'` prints `'`

**Output:**
```
Hello!How are you?
I	am feeling good.
 good

I am feeling 'good'
```

---

## **Q3 (a) C program to count persons in the age group 50-60.**
```c
#include <stdio.h>
int main() {
    int age[100], count = 0;
    for(int i = 0; i < 100; i++) {
        scanf("%d", &age[i]);
        if(age[i] >= 50 && age[i] <= 60)
            count++;
    }
    printf("Number of persons in 50-60 age group: %d\n", count);
    return 0;
}
```

---

## **Q3 (b) C program to count positive and negative numbers.**
```c
#include <stdio.h>
int main() {
    int num, pos = 0, neg = 0;
    do {
        scanf("%d", &num);
        if(num > 0) pos++;
        else if(num < 0) neg++;
    } while(num != 0);
    printf("Positive: %d, Negative: %d\n", pos, neg);
    return 0;
}
```

---

## **Q4 C program to print diamond pattern.**
```c
#include <stdio.h>
void printDiamond(int n) {
    for(int i = 1; i <= n; i += 2) {
        for(int j = n; j > i; j -= 2) printf(" ");
        for(int k = 1; k <= i; k++) printf("%d", k);
        printf("\n");
    }
    for(int i = n - 2; i > 0; i -= 2) {
        for(int j = n; j > i; j -= 2) printf(" ");
        for(int k = 1; k <= i; k++) printf("%d", k);
        printf("\n");
    }
}
int main() {
    int n = 5;
    printDiamond(n);
    return 0;
}
```

**Example Output for n=3:**
```
  1
 123
12345
 123
  1
```

---

## **Q5 (a) Output of array processing code.**
**Final output:**
```
1, 3, 5, 9, 7,
25
10
```

---

## **Q6 (a) Output of string pointer operations.**
**Output:**
```
72
I
e
dlrow ol
```

---

## **Q7 C program for Chebyshev polynomials.**
```c
#include <stdio.h>
void chebyshev(int n, double x) {
    double T0 = 1, T1 = x, Tn;
    printf("T0 = %lf\nT1 = %lf\n", T0, T1);
    for(int i = 2; i <= n; i++) {
        Tn = 2 * x * T1 - T0;
        printf("T%d = %lf\n", i, Tn);
        T0 = T1;
        T1 = Tn;
    }
}
int main() {
    int n;
    double x;
    scanf("%d %lf", &n, &x);
    chebyshev(n, x);
    return 0;
}
```

---

**End of Model CAT1 Solutions**





**CAT1 MODEL - DETAILED SOLUTIONS**

---

### **Re-CAT – I**
#### **Programme:** B. Tech. (ECE/ECM)  
#### **Course Code & Title:** BECE320E - Embedded C Programming  
#### **Faculty:** Prof. Srinivasan R  
#### **Duration:** 90 Minutes  
#### **Max. Marks:** 50  

---

## **Q1 (a) What are the primary data types and their typical sizes in C?**

### **Solution:**
| Data Type  | Size (Bytes) | Range (Typical)  |
|------------|-------------|------------------|
| `char`     | 1           | -128 to 127      |
| `int`      | 4           | -2,147,483,648 to 2,147,483,647 |
| `float`    | 4           | ~3.4E-38 to ~3.4E+38 |
| `double`   | 8           | ~1.7E-308 to ~1.7E+308 |
| `short`    | 2           | -32,768 to 32,767 |
| `long`     | 4 or 8      | Depends on system |
| `long long` | 8          | -9 quintillion to 9 quintillion |

---

## **Q1 (b) Explain bitwise operators in C with examples.**

### **Solution:**
C provides six bitwise operators:
- `&` (AND)
- `|` (OR)
- `^` (XOR)
- `~` (NOT)
- `<<` (Left shift)
- `>>` (Right shift)

**Example:**
```c
#include <stdio.h>
int main() {
    int a = 5, b = 3;
    printf("a & b = %d\n", a & b);  // 1
    printf("a | b = %d\n", a | b);  // 7
    printf("a ^ b = %d\n", a ^ b);  // 6
    printf("~a = %d\n", ~a);        // -6
    printf("b << 1 = %d\n", b << 1); // 6
    printf("b >> 1 = %d\n", b >> 1); // 1
    return 0;
}
```

---

## **Q2 (a) C program to check if a given number is prime.**
```c
#include <stdio.h>
int isPrime(int num) {
    if (num < 2) return 0;
    for (int i = 2; i * i <= num; i++) {
        if (num % i == 0) return 0;
    }
    return 1;
}
int main() {
    int num;
    scanf("%d", &num);
    printf("%d is %s\n", num, isPrime(num) ? "Prime" : "Not Prime");
    return 0;
}
```

---

## **Q2 (b) Output of the given logical operations program.**

**Output:**
```
w = 1 x = 0 y = 1 z = 1
```

---

## **Q3 (a) C program to find numbers greater than 100 and less than 200, divisible by 7 but not 3.**
```c
#include <stdio.h>
int main() {
    int sum = 0;
    for (int i = 101; i < 200; i++) {
        if (i % 7 == 0 && i % 3 != 0) {
            printf("%d ", i);
            sum += i;
        }
    }
    printf("\nSum: %d\n", sum);
    return 0;
}
```

---

## **Q3 (b) C program to print ASCII values.**
```c
#include <stdio.h>
int main() {
    int i = 0;
    while (i <= 255) {
        printf("ASCII %d: %c\n", i, i);
        i++;
    }
    return 0;
}
```

---

## **Q4 (b) C program to find the smallest number of currency notes for Rs. N.**
```c
#include <stdio.h>
void minNotes(int N) {
    int notes[] = {100, 50, 10, 5, 2, 1};
    int count[6] = {0};
    for (int i = 0; i < 6; i++) {
        count[i] = N / notes[i];
        N %= notes[i];
        if (count[i] > 0)
            printf("%d x Rs. %d\n", count[i], notes[i]);
    }
}
int main() {
    int N;
    scanf("%d", &N);
    minNotes(N);
    return 0;
}
```

---

## **Q5 C program to print Floyd’s triangle.**
```c
#include <stdio.h>
int main() {
    int n, num = 1;
    scanf("%d", &n);
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= i; j++) {
            printf("%d ", num++);
        }
        printf("\n");
    }
    return 0;
}
```

---

## **Q6 C program to find the smallest and largest elements in an array.**
```c
#include <stdio.h>
int main() {
    int n, arr[100];
    scanf("%d", &n);
    for (int i = 0; i < n; i++) scanf("%d", &arr[i]);
    int min = arr[0], max = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] < min) min = arr[i];
        if (arr[i] > max) max = arr[i];
    }
    printf("Min: %d, Max: %d\n", min, max);
    return 0;
}
```

---

## **Q7 C program to compute sum, mean, variance, and standard deviation.**
```c
#include <stdio.h>
#include <math.h>
int main() {
    int n;
    double arr[100], sum = 0, mean, variance = 0, stddev;
    scanf("%d", &n);
    for (int i = 0; i < n; i++) {
        scanf("%lf", &arr[i]);
        sum += arr[i];
    }
    mean = sum / n;
    for (int i = 0; i < n; i++) {
        variance += pow(arr[i] - mean, 2);
    }
    variance /= n;
    stddev = sqrt(variance);
    printf("Sum: %lf\nMean: %lf\nVariance: %lf\nStandard Deviation: %lf\n", sum, mean, variance, stddev);
    return 0;
}
```

---

**End of CAT1 Model Solutions**

