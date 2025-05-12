

## 📑 Table of Contents

1. [C Code Output Analysis](#1-c-code-output-analysis)
2. [Expression Evaluation Program](#2-evaluate-expression-program)
3. [8051 Special Function Registers (SFRs)](#3-special-function-registers-sfrs)
4. [Alphabetical Sorting of Usernames](#4-sort-usernames-alphabetically)
5. [Two-Player Number Guessing Game](#5-number-guessing-game)
6. [8051 Port Manipulation with P2 Conditions](#6-monitor-p2-and-control-p1)
7. [LM35 Interfacing with 8051 Using ADC0848](#7-lm35-temperature-sensor-interfacing)
8. [ATM Simulation in Embedded C](#8-atm-simulation)
9. [Serial Communication at 19200 Baud](#9-serial-communication-19200)
10. [LED Toggle Patterns Using Timer & External Switch](#10-toggle-leds-with-timer1)
11. [Flame Detection with LCD and Buzzer](#11-flame-detection-with-lcd)

---

## 1. ✅ C Code Output Analysis

### **A.**

```c
int x = 3;
int result = (--x >= 2) && (++x == 3);
printf("%02d", result);  // Output: 01
```

### **B.**

```c
int i = 4, j = 3, k = 0;
int y = i && j || k;
printf("%02d", y);  // Output: 01
```

### **C.**

```c
int d = 5, e = 9;
e = d ^ e; d = d ^ e; e = d ^ e;
printf("%u, %u", d, e);  // Output: 9, 5
```

### **D.**

```c
int a = -10, b = 5, c = 2;
int result = a + b * c / 2 - a % c;
printf("Result: %i\n", result);  // Output: -5
```

### **E.**

```c
char str[] = "Hello, World!";
printf("String: %-6s\n", str);  // Output: Hello, World!
```

---

## 2. 🧮 Evaluate Expression Program

### Expression:

$$
W = \frac{\sqrt{\sin(x) + |y| + e^x}}{\ln(x) - y^3}
$$

### **Code:**

```c
#include <stdio.h>
#include <math.h>

int main() {
    double x, y, W;

    printf("Enter x (> 0): ");
    scanf("%lf", &x);
    printf("Enter y: ");
    scanf("%lf", &y);

    if (x <= 0) {
        printf("x must be greater than 0.\n");
        return 1;
    }

    double numerator = sqrt(sin(x) + fabs(y) + exp(x));
    double denominator = log(x) - pow(y, 3);

    if (denominator == 0) {
        printf("Denominator is zero. Cannot compute W.\n");
        return 1;
    }

    W = numerator / denominator;
    printf("W = %lf\n", W);
    return 0;
}
```

---

## 3. 🧾 Special Function Registers (SFRs)

### **Bit-Addressable SFRs in 8051:**

| SFR  | Addr | Bit-Addressable | Description         |
| ---- | ---- | --------------- | ------------------- |
| ACC  | E0h  | Yes             | Accumulator         |
| B    | F0h  | Yes             | B register          |
| PSW  | D0h  | Yes             | Program Status Word |
| P0   | 80h  | Yes             | Port 0              |
| P1   | 90h  | Yes             | Port 1              |
| P2   | A0h  | Yes             | Port 2              |
| P3   | B0h  | Yes             | Port 3              |
| TCON | 88h  | Yes             | Timer Control       |
| SCON | 98h  | Yes             | Serial Control      |
| IE   | A8h  | Yes             | Interrupt Enable    |
| IP   | B8h  | Yes             | Interrupt Priority  |

### **Non-Bit-Addressable SFRs:**

| SFR  | Addr | Description       |
| ---- | ---- | ----------------- |
| SP   | 81h  | Stack Pointer     |
| DPL  | 82h  | Data Pointer Low  |
| DPH  | 83h  | Data Pointer High |
| TMOD | 89h  | Timer Mode        |
| TL0  | 8Ah  | Timer 0 LSB       |
| TH0  | 8Ch  | Timer 0 MSB       |
| TL1  | 8Bh  | Timer 1 LSB       |
| TH1  | 8Dh  | Timer 1 MSB       |
| SBUF | 99h  | Serial Buffer     |

---

## 4. 🔤 Sort Usernames Alphabetically

```c
#include <stdio.h>
#include <string.h>

int main() {
    char names[10][21], temp[21];
    for (int i = 0; i < 10; i++) {
        printf("Name %d: ", i + 1);
        scanf("%20s", names[i]);
    }

    for (int i = 0; i < 9; i++)
        for (int j = i + 1; j < 10; j++)
            if (strcmp(names[i], names[j]) > 0) {
                strcpy(temp, names[i]);
                strcpy(names[i], names[j]);
                strcpy(names[j], temp);
            }

    printf("\nSorted Usernames:\n");
    for (int i = 0; i < 10; i++)
        printf("%s\n", names[i]);
    return 0;
}
```

---

## 5. 🎯 Number Guessing Game (2 Players)

```c
#include <stdio.h>

int main() {
    int secret, guess, attempts = 10;
    printf("Player 1: Enter secret number (1-100): ");
    scanf("%d", &secret);

    for (int i = 1; i <= attempts; i++) {
        printf("Player 2, guess #%d: ", i);
        scanf("%d", &guess);
        if (guess == secret) {
            printf("Correct! Guessed in %d tries.\n", i);
            return 0;
        } else if (guess < secret) printf("Too low!\n");
        else printf("Too high!\n");
    }

    printf("Out of attempts! Secret was %d.\n", secret);
    return 0;
}
```

---

## 6. 🔄 Monitor P2 and Control P1 (8051 C)

```c
#include <reg51.h>

void delay() {
    int i, j;
    for(i = 0; i < 50; i++)
        for(j = 0; j < 255; j++);
}

void main() {
    while(1) {
        if (P2 < 0x40)
            P1 = (P1 & 0xF0) | (P2 & 0x0F);
        else if (P2 <= 0x80)
            P1 = (P1 & 0x0F) | 0xF0;
        else {
            P1 ^= 0xF0;
            delay();
        }
    }
}
```

---

## 7. 🌡️ LM35 Temperature Sensor Interfacing

**→ Using ADC0848, LM35, and 8051**

### **Steps:**

* LM35 → ADC input
* ADC output → 8051 (Port 1)
* Convert digital value to temperature
* Display on LCD

### **Code Snippet:**

```c
#include <reg51.h>

#define ADC_DATA P1
sbit ALE = P2^0;
sbit START = P2^1;
sbit EOC = P2^2;
sbit OE = P2^3;

void delay() { ... }

void adc_start() {
    ALE = 1; START = 1; delay();
    ALE = 0; START = 0;
}

unsigned char read_adc() {
    while (EOC == 0);
    OE = 1; delay(); OE = 0;
    return ADC_DATA;
}

void main() {
    float voltage, temp;
    unsigned char value;
    while(1) {
        adc_start();
        value = read_adc();
        voltage = value * 5.0 / 255;
        temp = voltage * 100;
        // Display `temp` on LCD here
    }
}
```

---

## 8. 🏧 ATM Simulation

```c
#include <stdio.h>

int main() {
    float balance = 0.0, amount;
    int choice;
    do {
        printf("\n1. Check\n2. Deposit\n3. Withdraw\n4. Exit\nChoice: ");
        scanf("%d", &choice);
        switch (choice) {
            case 1: printf("Balance: %.2f\n", balance); break;
            case 2: printf("Amount: "); scanf("%f", &amount); if (amount > 0) balance += amount; break;
            case 3: printf("Amount: "); scanf("%f", &amount); if (amount <= balance) balance -= amount; else printf("Insufficient!\n"); break;
            case 4: printf("Final Balance: %.2f\n", balance); break;
            default: printf("Invalid\n");
        }
    } while (choice != 4);
    return 0;
}
```

---

## 9. 📡 Serial Communication @19200 Baud (XTAL = 11.0592 MHz)

### Baud Rate Formula:

$$
TH1 = 256 - \frac{XTAL}{384 \times Baud} = 256 - \frac{11.0592 \times 10^6}{384 \times 19200} \approx 253
$$

### Code:

```c
#include <reg51.h>

void main() {
    SCON = 0x50;
    TMOD = 0x20;
    TH1 = 253;
    TR1 = 1;

    char *msg = "We are safe";
    while (*msg) {
        SBUF = *msg++;
        while (TI == 0);
        TI = 0;
    }

    while (1);
}
```

---

## 10. 💡 Toggle LEDs with Timer1 and Switch on P3.2

```c
#include <reg51.h>

sbit SWITCH = P3^2;

void delay() {
    for (unsigned int i = 0; i < 50000; i++);
}

void main() {
    P1 = 0x00;
    TMOD = 0x20;
    TH1 = 0xFA;
    TR1 = 1;

    while (1) {
        if (SWITCH == 0) {
            P1 = 0xAA; delay();
            P1 = 0x55; delay();
        }
    }
}
```

---

## 11. 🔥 Flame Detection with LCD & Buzzer (P2.3)

```c
#include <reg51.h>
#define LCD P0
sbit RS = P2^0; sbit RW = P2^1; sbit EN = P2^2;
sbit FLAME = P3^0; sbit BUZZER = P2^3;

void delay(unsigned int t) { ... }
void lcd_cmd(char cmd) { ... }
void lcd_data(char dat) { ... }
void lcd_init() { lcd_cmd(0x38); lcd_cmd(0x0C); lcd_cmd(0x01); }
void lcd_print(char *s) { while(*s) lcd_data(*s++); }

void main() {
    lcd_init(); BUZZER = 0;
    while (1) {
        if (FLAME) {
            lcd_cmd(0x01);
            lcd_print("Flame detected");
            BUZZER = 1;
        } else {
            lcd_cmd(0x01);
            lcd_print("No flame");
            BUZZER = 0;
        }
        delay(500);
    }
}
```

---

📎 **Note:** For circuit diagrams (Q7 & Q11), let me know if you'd like labeled schematics as image files.

Would you like this README exported as a PDF or formatted document for submission?
