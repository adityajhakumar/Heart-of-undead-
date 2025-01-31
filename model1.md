**Detailed Solutions for Model CAT1.pdf**

### **Q1 (a): Variable Declarations and Assignments**
```c
#include <stdio.h>

int main() {
    // Integer variables
    int u = 0421;  // Octal representation (421 in octal = 273 in decimal)
    int y = 0xfa01; // Hexadecimal representation (fa01 in hex = 64001 in decimal)
    
    // One-dimensional character array
    char message[] = "We are safe.";
    
    // Double precision variable
    double reading1 = 3.56218e-4; // Scientific notation
    
    return 0;
}
```

### **Q1 (b): Expression Evaluation**
Given:
```c
float value = (i - 3 * j) % (c + 2 * d) / (x - y);
```
With:
```c
int i = 10, j = 5;
float x = 0.005, y = -0.01;
char c = 'c', d = 'd'; // ASCII values: c=99, d=100
```
Substituting ASCII values:
```c
float value = (10 - 3*5) % (99 + 2*100) / (0.005 - (-0.01));
```
- `(10 - 15) = -5`
- `(99 + 200) = 299`
- `(-5 % 299) = -5` (modulus of negative numbers follows implementation-defined behavior)
- `(0.005 - (-0.01)) = 0.015`
- `-5 / 0.015 = -333.33` (Floating point division)

### **Q2 (a): Code Output Prediction**
#### **Given Code:**
```c
#include <stdio.h>
int main() {
    int k, num = 65;
    k = ( num > 5 ? ( num <= 10 ? 100 : 200 ) : 500 );
    printf("%c %d\n", num, k);
    return 0;
}
```
**Execution:**
- `num = 65`, `num > 5` is `true`, so evaluate `(num <= 10 ? 100 : 200)`.
- `num <= 10` is `false`, so result is `200`.
- Output: `A 200` (ASCII 65 = 'A')

### **Q2 (b): Escape Sequence Output**
#### **Given Code:**
```c
#include <stdio.h>
int main () {
    printf("Hello!\bHow are you?\n");
    printf("I\tam feeling good.\n");
    printf("I am feeling\r good\n");
    printf("\nI am feeling \'good\'\n");
    return 0;
}
```
**Output:**
```
HellHow are you?
I    am feeling good.
 good
I am feeling 'good'
```

### **Q3 (a): Counting Ages in Range 50-60**
```c
#include <stdio.h>
int main() {
    int ages[100], count = 0;
    for (int i = 0; i < 100; i++) {
        scanf("%d", &ages[i]);
        if (ages[i] >= 50 && ages[i] <= 60) count++;
    }
    printf("Count: %d\n", count);
    return 0;
}
```

### **Q3 (b): Count Positive and Negative Numbers**
```c
#include <stdio.h>
int main() {
    int num, pos = 0, neg = 0;
    do {
        scanf("%d", &num);
        if (num > 0) pos++;
        else if (num < 0) neg++;
    } while (num != 0);
    printf("Positives: %d, Negatives: %d\n", pos, neg);
    return 0;
}
```

### **Q4: Diamond Pattern**
```c
#include <stdio.h>
void printDiamond(int n) {
    for (int i = 1; i <= n; i += 2) {
        for (int j = 0; j < (n - i) / 2; j++) printf(" ");
        for (int j = 1; j <= i; j++) printf("%d", j);
        printf("\n");
    }
    for (int i = n - 2; i >= 1; i -= 2) {
        for (int j = 0; j < (n - i) / 2; j++) printf(" ");
        for (int j = 1; j <= i; j++) printf("%d", j);
        printf("\n");
    }
}
int main() {
    printDiamond(5);
    return 0;
}
```

### **Q5: Output Prediction (Array Processing)**
```c
int a, b = 0, d=0;
static int c[10] = {2, 3, 8, 0, 1, 4, 5, 6, 9, 7};
for (a = 0; a < 10; ++a){
    if ((c[a] % 2) == 1) { // Odd numbers
        b += c[a];
        printf("%d, ", c[a]);
    }
}
if(a % 2 == 0) d += a;
printf("\n%d\n", b);
printf("%d", d);
```
**Output:**
```
3, 1, 5, 9, 7,
25
10
```

### **Q6: String Pointer Manipulation**
```c
#include <stdio.h>
#include <string.h>
int main() {
    char *p = "Hello world";
    printf("%d\n", *p);  // ASCII of 'H' (72)
    printf("%c\n", *p + 1); // 'I' (H+1)
    printf("%c\n", *(p+1)); // 'e'
    for (int t = strlen(p)-1; t > 1; t--) printf("%c", p[t]);
    return 0;
}
```
**Output:**
```
72
I
e
dlrow o
```

### **Q7: Chebyshev Polynomials**
```c
#include <stdio.h>
void chebyshev(int n, float x) {
    float T0 = 1, T1 = x, Tn;
    printf("T0=%.2f T1=%.2f ", T0, T1);
    for (int i = 2; i <= n; i++) {
        Tn = 2 * x * T1 - T0;
        printf("T%d=%.2f ", i, Tn);
        T0 = T1;
        T1 = Tn;
    }
    printf("\n");
}
int main() {
    float x;
    int n;
    scanf("%d %f", &n, &x);
    if (n > 0 && x >= -1 && x <= 1) chebyshev(n, x);
    return 0;
}
```

This provides a detailed explanation and solutions for each question. Let me know if you need modifications!

