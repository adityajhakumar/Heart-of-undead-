

### **1) Toggle all bits of P0 and P2 continuously with 350 ms delay**

```c
#include <reg51.h>

void delay_ms(unsigned int ms) {
    unsigned int i, j;
    for(i = 0; i < ms; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    while(1) {
        P0 = 0xFF;   // All bits ON
        P2 = 0x00;   // All bits OFF
        delay_ms(350);

        P0 = 0x00;   // All bits OFF
        P2 = 0xFF;   // All bits ON
        delay_ms(350);
    }
}
```

---

### **2) Send values 00 – FF to port P1**

```c
#include <reg51.h>

void delay_ms(unsigned int ms) {
    unsigned int i, j;
    for(i = 0; i < ms; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    unsigned char val;
    while(1) {
        for(val = 0; val <= 0xFF; val++) {
            P1 = val;
            delay_ms(100);  // Delay to observe the change
        }
    }
}
```

---

### **3) Door sensor on P1.1 and buzzer on P1.7 (buzz when door opens)**

```c
#include <reg51.h>

sbit doorSensor = P1^1;
sbit buzzer = P1^7;

void main(void) {
    while(1) {
        if(doorSensor == 1) { // Assuming high means door opened
            buzzer = 1;        // Turn ON buzzer
        } else {
            buzzer = 0;        // Turn OFF buzzer
        }
    }
}
```

---

### **4) Get a byte from P0. If < 100, send to P1; else to P2**

```c
#include <reg51.h>

void main(void) {
    unsigned char dataIn;
    while(1) {
        dataIn = P0;        // Read from P0

        if(dataIn < 100) {
            P1 = dataIn;    // Send to P1
        } else {
            P2 = dataIn;    // Send to P2
        }
    }
}
```

---

### **5) Send hex values for ASCII of 0-5, A-D to P1**

```c
#include <reg51.h>

void delay_ms(unsigned int ms) {
    unsigned int i, j;
    for(i = 0; i < ms; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    unsigned char ascii_values[] = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x41, 0x42, 0x43, 0x44};
    unsigned char i;

    while(1) {
        for(i = 0; i < 10; i++) {
            P1 = ascii_values[i];  // Send each ASCII value to P1
            delay_ms(500);
        }
    }
}
```


### ✅ **1) Already Provided (Generate 50ms frequency on pin 2.3 using Timer 0 Mode 1)**

Just confirming—this one looks good.

```c
#include<reg51.h>
sbit mbit = P2^3;

void T0M1delay(void);

void main(void) {
    TMOD = 0x01;    // Timer 0, Mode 1 (16-bit)
    while(1) {
        mbit = ~mbit; // Toggle pin P2.3
        T0M1delay();  // 50ms delay
    }
}

void T0M1delay() {
    TL0 = 0xFD;    // Load lower byte
    TH0 = 0x4B;    // Load higher byte for 50ms delay (assuming 11.0592 MHz crystal)
    TR0 = 1;       // Start timer
    while(TF0 == 0); // Wait for overflow
    TF0 = 0;       // Clear overflow flag
    TR0 = 0;       // Stop timer
}
```

---

### ✅ **2) Toggle Port P1 Continuously Using Timer 0, Mode 1 (16-bit mode)**

```c
#include <reg51.h>

void T0delay(void);

void main(void) {
    TMOD = 0x01;  // Timer 0, Mode 1 (16-bit)
    while(1) {
        P1 = ~P1;  // Toggle P1
        T0delay(); // Delay using Timer 0
    }
}

void T0delay(void) {
    TH0 = 0xFC;    // Load higher byte (adjust as needed for your delay)
    TL0 = 0x66;    // Load lower byte (gives approx 50ms delay with 11.0592MHz)
    TR0 = 1;       // Start Timer 0
    while(TF0 == 0); // Wait for overflow
    TR0 = 0;       // Stop Timer 0
    TF0 = 0;       // Clear overflow flag
}
```

---

### ✅ **3) Toggle All Bits of P2 Continuously Every 500ms (Timer 1, Mode 1)**

```c
#include <reg51.h>

void T1delay(void);

void main(void) {
    TMOD = 0x10;  // Timer 1, Mode 1 (16-bit)
    while(1) {
        P2 = ~P2; // Toggle all bits of P2
        // 71.1ms * 7 = ~500ms, so call delay 7 times
        T1delay();
        T1delay();
        T1delay();
        T1delay();
        T1delay();
        T1delay();
        T1delay();
    }
}

void T1delay(void) {
    TH1 = 0x00;   // Start counting from 0000
    TL1 = 0x00;
    TR1 = 1;      // Start Timer 1
    while(TF1 == 0); // Wait for overflow
    TR1 = 0;      // Stop Timer 1
    TF1 = 0;      // Clear overflow flag
}
```

⏱️ *Explanation*:  
- Timer counts from `0000` to `FFFF` (65536 counts).  
- At 11.0592 MHz, 1 machine cycle = 1.085µs → 71.1ms for a full timer count.  
- 500ms / 71.1ms ≈ 7 times.

---

### ✅ **4) Toggle Port P1 Continuously Using Timer 0, Mode 2 (8-bit Auto-Reload)**

```c
#include <reg51.h>

void main(void) {
    TMOD = 0x02;  // Timer 0, Mode 2 (8-bit auto-reload)
    TH0  = 0x06;  // Load reload value (adjust as needed)
    TL0  = 0x06;  // Initial value
    TR0  = 1;     // Start Timer 0

    while(1) {
        while(TF0 == 0); // Wait for overflow
        TF0 = 0;         // Clear flag
        P1 = ~P1;        // Toggle P1
    }
}
```

📝 *Note*:  
- Mode 2 is an 8-bit timer with auto-reload from `TH0` to `TL0`.  
- You can adjust the `TH0` value to set different delays.  
- For precise delay, we can calculate using the formula:  
  `Delay = (256 - TH0) * 1.085µs` (for 11.0592MHz)


### ✅ **Timer Hardware**
#### **Toggle Port Bit P1.5 Continuously Every 50ms**
*You already have this code, but here's a slightly improved version with the correct placement of TMOD.*

```c
#include <reg51.h>

sbit mybit = P1^5;

void T0M1Delay(void);

void main(void) {
    TMOD = 0x01;  // Timer 0, Mode 1 (16-bit)
    
    while(1) {
        mybit = ~mybit;
        T0M1Delay();
    }
}

void T0M1Delay(void) {
    TH0 = 0x4B;  // High byte for 50ms delay
    TL0 = 0xFD;  // Low byte
    TR0 = 1;     // Start timer 0

    while(TF0 == 0);  // Wait until overflow

    TF0 = 0;    // Clear overflow flag
    TR0 = 0;    // Stop timer
}
```

---

### ✅ **Counter**
#### **1Hz External Clock on T1 (P3.5), Counter 1, Mode 2, Display TL1 on P1**
Here’s the complete working version of the code you started:

```c
#include <reg51.h>

void main(void) {
    TMOD = 0x60;  // Counter 1, Mode 2 (8-bit auto-reload), C/T = 1 (counter)
    TH1 = 0x00;   // Start count at 0
    TR1 = 1;      // Start counter 1

    while(1) {
        if(TF1 == 1) {
            TR1 = 0;       // Stop counter temporarily
            TF1 = 0;       // Clear overflow flag
            TR1 = 1;       // Restart counter
        }
        P1 = TL1;          // Display TL1 contents on port P1
    }
}
```

---

### ✅ **TASK**
#### **1Hz External Clock on T0 (P3.4), Counter 0, Mode 1, Show TL0 on P2 and TH0 on P1**

```c
#include <reg51.h>

void main(void) {
    TMOD = 0x05;  // Counter 0, Mode 1 (16-bit), C/T = 1 (counter)
    TH0 = 0x00;   // Start count at 0
    TL0 = 0x00;
    TR0 = 1;      // Start counter 0

    while(1) {
        if(TF0 == 1) {
            TR0 = 0;   // Stop counter temporarily
            TF0 = 0;   // Clear flag
            TR0 = 1;   // Restart counter
        }

        P1 = TH0;      // Display high byte on P1
        P2 = TL0;      // Display low byte on P2
    }
}
```

---

## ✅ **Program List (Simple and Clean)**

---

### **1) Toggle all bits of P1 continuously with some delay**

```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for(i = 0; i < 500; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    while(1) {
        P1 = ~P1;  // Toggle all bits
        delay();
    }
}
```

---

### **2) Toggle all bits of P1 continuously with a 250ms delay**

```c
#include <reg51.h>

void delay_250ms() {
    unsigned int i, j;
    for(i = 0; i < 250; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    while(1) {
        P1 = ~P1;     // Toggle all bits
        delay_250ms();
    }
}
```

---

### **3) Get a byte of data from P0. If less than 100, send it to P1; otherwise send it to P2**

```c
#include <reg51.h>

void main(void) {
    unsigned char dataIn;
    while(1) {
        dataIn = P0;
        if(dataIn < 100)
            P1 = dataIn;
        else
            P2 = dataIn;
    }
}
```

---

### **4) Send hex values for ASCII characters 0-5, A-D to P1**

```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for(i = 0; i < 500; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    unsigned char ascii[] = {0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x41, 0x42, 0x43, 0x44};
    unsigned char i;

    while(1) {
        for(i = 0; i < 10; i++) {
            P1 = ascii[i];
            delay();
        }
    }
}
```

---

### **5) Toggle LED connected to P1**

```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for(i = 0; i < 500; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    while(1) {
        P1 = ~P1;  // Toggle all LEDs connected to P1
        delay();
    }
}
```

---

### **6) Send values 00 - FF to P1**

```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for(i = 0; i < 100; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    unsigned char val;
    while(1) {
        for(val = 0; val <= 0xFF; val++) {
            P1 = val;
            delay();
        }
    }
}
```

---

### **7) Get a byte of data from P1, wait 0.5 seconds, then send it to P2**

```c
#include <reg51.h>

void delay_500ms() {
    unsigned int i, j;
    for(i = 0; i < 500; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    unsigned char dataIn;
    while(1) {
        dataIn = P1;
        delay_500ms();
        P2 = dataIn;
    }
}
```

---

### **8) Toggle only bit P2.4 continuously without disturbing the rest of P2**

```c
#include <reg51.h>

sbit mybit = P2^4;

void delay() {
    unsigned int i, j;
    for(i = 0; i < 500; i++)
        for(j = 0; j < 1275; j++);
}

void main(void) {
    while(1) {
        mybit = ~mybit;  // Toggle only bit 4 of P2
        delay();
    }
}
```

---


## ✅ **Program 1 — 16-bit Timer Mode 1 (Square wave + Input to Output Bit Transfer)**

### **Requirement Recap:**
- **XTAL = 11.0592 MHz**
- Square wave: **2 kHz**, so half-period = **250µs**
- Timer preload for **250µs** delay:
  ```
  250us / 1.085us = 230 counts
  65536 - 230 = 65306 = 0xFF1A
  TL0 = 0x1A, TH0 = 0xFF
  ```

### ✅ **Code:**
```c
#include <reg51.h>

sbit SW = P1^7;     // Switch input
sbit IND = P1^0;    // Output indicator
sbit WAVE = P1^5;   // Square wave output

void timer0_ISR(void) interrupt 1 { // Timer 0 interrupt
    WAVE = ~WAVE;                   // Toggle P1.5
    TL0 = 0x1A;                     // Reload TL0
    TH0 = 0xFF;                     // Reload TH0
}

void main() {
    SW = 1;               // Configure SW as input
    TMOD = 0x01;          // Timer 0, Mode 1 (16-bit)
    TL0 = 0x1A;           // Low byte for 250us
    TH0 = 0xFF;           // High byte for 250us
    IE = 0x82;            // Enable interrupt for Timer 0
    TR0 = 1;              // Start Timer 0
    
    while(1) {
        IND = SW;         // Send P1.7 input to P1.0 output
    }
}
```

---

## ✅ **Program 2 — 8-bit Auto-Reload Mode 2 (Square wave + Input to Output Bit Transfer)**

### **Requirement Recap:**
- Square wave period = **200µs** (so high/low = 100µs)
- 100µs / 1.085µs = **92 counts**
- Reload value = **256 - 92 = 164 (A4h)**
  - TL0 = TH0 = 0xA4

### ✅ **Code:**
```c
#include <reg51.h>

sbit SW = P1^3;     // Switch input
sbit IND = P1^0;    // Output indicator
sbit WAVE = P1^4;   // Square wave output

void timer0_ISR(void) interrupt 1 {
    WAVE = ~WAVE;       // Toggle P1.4
}

void main() {
    SW = 1;             // Configure SW as input
    TMOD = 0x02;        // Timer 0, Mode 2 (8-bit auto-reload)
    TH0 = 0xA4;         // Auto-reload value
    TL0 = 0xA4;
    IE = 0x82;          // Enable Timer 0 interrupt
    TR0 = 1;            // Start Timer 0

    while(1) {
        IND = SW;       // Send P1.3 input to P1.0 output
    }
}
```

---

## ✅ **Program 3 — Timer 0 Interrupt + External Interrupt 1**
### **Requirement Recap:**
- **P2.1** → 10 kHz square wave via **Timer 0 Auto-Reload (Mode 2)**
- External interrupt 1 counts pulses and displays them on **P0**
- Baud rate 9600 is mentioned but serial part isn't explicitly needed; focusing on counting pulses here.

#### **10kHz Square Wave:**
- Period = 100µs → Half-period = 50µs  
- 50µs / 1.085µs = ~46 counts  
- Reload = 256 - 46 = 210 = D2h

### ✅ **Code:**
```c
#include <reg51.h>

unsigned int pulse_count = 0;

void timer0_ISR(void) interrupt 1 {
    P2^1 = ~P2^1;   // Toggle P2.1 (square wave)
}

void external1_ISR(void) interrupt 2 {
    pulse_count++;   // Count external pulses
    P0 = pulse_count; // Display count on P0
}

void main() {
    TMOD = 0x02;     // Timer 0 Mode 2 (8-bit auto-reload)
    TH0 = 0xD2;      // Reload value for 50µs delay (10kHz square wave)
    TL0 = 0xD2;
    IE = 0x86;       // Enable Timer 0 and External INT1 interrupts
    TCON = 0x04;     // Enable External Interrupt 1 (edge triggered)
    TR0 = 1;         // Start Timer 0

    while(1) {
        // Main loop does nothing, all in interrupts
    }
}
```

---

## ✅ **Program 4 — Toggle P1.5 Continuously Every 250ms Using Timer 0 Mode 2**

### **Requirement Recap:**
- Half-period = **250ms**  
- Delay in Mode 2 (8-bit auto-reload):  
  Each tick = 1.085µs  
  Large delays are impractical with Mode 2 alone, so we loop a number of overflows.

#### **Preload for ~1ms:**
- 1ms / 1.085µs = 921 counts  
- 256 - (921 % 256) = 255 - 153 = 103 (67h)

- Loop for ~250 times to get 250ms.

### ✅ **Code:**
```c
#include <reg51.h>

void TMR_delay();

void main() {
    while(1) {
        P1^5 = ~P1^5;  // Toggle P1.5
        TMR_delay();
    }
}

void TMR_delay() {
    unsigned int i;

    TMOD = 0x02;   // Timer 0 Mode 2 (8-bit auto-reload)
    TH0 = 0x67;    // Reload for ~1ms delay
    TL0 = 0x67;
    TR0 = 1;

    for(i = 0; i < 250; i++) {   // 1ms * 250 = 250ms
        while(TF0 == 0);         // Wait for overflow
        TF0 = 0;                 // Clear overflow flag
    }

    TR0 = 0;   // Stop timer
}
```

---

## ✅ **Summary:**
- **Program 1:** Toggle **P1.5** at 2kHz, read **P1.7** and output to **P1.0**
- **Program 2:** Toggle **P1.4** square wave (200µs), read **P1.3** and output to **P1.0**
- **Program 3:** Generate **10kHz** square wave on **P2.1**, count pulses on **INT1**, display on **P0**
- **Program 4:** Toggle **P1.5** every 250ms using **Timer 0 Mode 2**

---

## ✅ **1. Toggle All Bits of P2 Every 500ms Using Timer 1, Mode 1 (16-bit Timer)**

### **Requirements Recap:**
- XTAL = 11.0592 MHz  
- Machine cycle = 12 clock cycles → 11.0592 MHz / 12 = 921.6 kHz  
- Each cycle = 1 / 921.6 kHz = 1.085 µs  
- Desired delay = 500 ms = 500,000 µs  
- Counts = 500,000 / 1.085 ≈ 460,800  
- Max 16-bit timer = 65536  
- Number of overflows needed = 460,800 / 65,536 ≈ 7.03 → **7 times full overflow + partial count**

But for simplicity, we'll break it down into **multiple overflows** inside a loop. Let's create an **approximate 50ms delay** inside a loop running 10 times.

### ✅ **Code:**
```c
#include <reg51.h>

void delay_50ms();

void main() {
    while(1) {
        P2 = ~P2;          // Toggle all bits of P2
        delay_50ms();      // 50ms delay
        delay_50ms();      // Repeat 10 times to get ~500ms
        delay_50ms();
        delay_50ms();
        delay_50ms();
        delay_50ms();
        delay_50ms();
        delay_50ms();
        delay_50ms();
        delay_50ms();
    }
}

void delay_50ms() {
    TMOD |= 0x10;        // Timer1, Mode1 (16-bit)
    TH1 = 0x3C;          // Load TH1 for 50ms delay
    TL1 = 0xB0;
    TR1 = 1;             // Start Timer1
    while(TF1 == 0);     // Wait for overflow
    TR1 = 0;             // Stop Timer1
    TF1 = 0;             // Clear overflow flag
}
```
✅ **TH1 = 0x3C**, **TL1 = 0xB0** → gives approximately 50ms delay.

---

## ✅ **2. Toggle Port P1 Continuously with Some Delay Using Timer 0, Mode 1 (16-bit Timer)**

### ✅ **Code:**
```c
#include <reg51.h>

void delay();

void main() {
    while(1) {
        P1 = ~P1;    // Toggle all bits of P1
        delay();
    }
}

void delay() {
    TMOD |= 0x01;      // Timer 0, Mode 1 (16-bit)
    TH0 = 0xFC;        // Approx 10ms delay
    TL0 = 0x66;
    TR0 = 1;           // Start Timer0
    while(TF0 == 0);   // Wait for overflow
    TR0 = 0;           // Stop Timer0
    TF0 = 0;           // Clear flag
}
```
✅ You can call `delay()` multiple times for longer delays if needed.

---

## ✅ **3. Traffic Light Control System Using Timer Interrupts**

### **Requirements Recap:**
- **Timer 1, Mode 2 (8-bit auto-reload)** for 1-minute **green light**
- **Timer 0, Mode 2** for **2 ms delay** during transitions
- XTAL = 11.0592 MHz  
- Machine cycle = 1.085 µs  
- For **2 ms delay**, counts = 2000 / 1.085 = ~1843  
- Timer 0 reload = 256 - (1843 % 256) = 256 - 51 = **205 (CDh)**  
- Overflow count = 1843 / 256 = ~7 times  
---
- For **1-minute delay**, we use **interrupt-driven counting** with Timer 1 in Mode 2:  
- 1.085 µs × 256 = 277 µs per overflow  
- 1 min = 60,000,000 µs / 277 ≈ 216600 overflows → use a counter.

### ✅ **Code:**
```c
#include <reg51.h>

sbit RED = P2^0;
sbit YELLOW = P2^1;
sbit GREEN = P2^2;

unsigned long greenCounter = 0;
unsigned int transitionDelayCounter = 0;

void Timer0_ISR(void) interrupt 1 {
    transitionDelayCounter++;
}

void Timer1_ISR(void) interrupt 3 {
    greenCounter++;
}

void delay_2ms() {
    transitionDelayCounter = 0;
    while(transitionDelayCounter < 7);  // 7 overflows * 277us ≈ 2ms
}

void main() {
    // Initialize lights
    RED = YELLOW = GREEN = 0;

    // Setup Timer 0 Mode 2 for 2ms transition delay
    TMOD = 0x22;       // Timer 0 & Timer 1 Mode 2
    TH0 = 0xCD;        // 205 decimal = CDh
    TL0 = 0xCD;
    
    // Setup Timer 1 for green light timing (1 min)
    TH1 = 0x00;        // Max overflow time
    TL1 = 0x00;

    IE = 0x8A;         // Enable Timer 0 and Timer 1 interrupts
    TR0 = 1;
    TR1 = 1;

    while(1) {
        // Green Light ON for 1 min
        GREEN = 1; RED = YELLOW = 0;
        greenCounter = 0;
        while(greenCounter < 216600);   // 1 min delay with Timer1
        
        // Transition delay 2ms
        GREEN = 0; YELLOW = 1; RED = 0;
        delay_2ms();

        // Red light ON
        RED = 1; GREEN = YELLOW = 0;
        delay_2ms();

        // Back to Green light in loop
    }
}
```
✅ **RED, YELLOW, GREEN lights controlled on P2.0, P2.1, P2.2**  
✅ **Timer1 manages green light timing, Timer0 manages transitions**  
✅ All delays calculated and handled by interrupts.

---

## ✅ **4. Home Automation Controller (Explanation + Code)**

### **Question Recap:**
- Initial Code:
```c
#include<reg51.h>
void main (void)
{
    unsigned char z;
    for (z=0;z<=255;z++)
        P1=z;
}
```
- **What happens?**  
`P1` will count from 0x00 to 0xFF and stop. After finishing, the last value **255 (0xFF)** will remain on P1.

---

### ✅ **Modify the System for 500ms Wait, Send to P2**

### ✅ **Code:**
```c
#include<reg51.h>

void delay_500ms();

void main() {
    unsigned char sensorData;

    while(1) {
        sensorData = P1;   // Read sensors
        delay_500ms();     // Wait for 500ms
        P2 = sensorData;   // Send data to output devices

        // Perform bitwise operations
        P2 = P2 | 0x0D;    // Set bits 0, 2, and 3
        P2 = P2 & ~0x04;   // Clear bit 2
    }
}

void delay_500ms() {
    unsigned int i, j;
    for(i=0; i<500; i++)
        for(j=0; j<1275; j++);  // Approx 1ms delay * 500
}
```

---

### ✅ **Final Value of P2 After Operations:**

- Initially `P2 = sensorData`, for example, let's assume `P2 = 0xF0`  
- `P2 = P2 | 0x0D;`  
  - `0xF0 | 0x0D = 0xFD`  
- `P2 = P2 & ~0x04;`  
  - `0xFD & 0xFB = 0xF9`  
✅ **Final Value of P2 = 0xF9**

---

## ✅ **5. Masking in 8051 C Programming**

### ✅ **Mask to Isolate Lower 4 Bits:**
```c
P1 = P1 & 0x0F;   // Keep lower 4 bits, clear upper 4
```

### ✅ **Mask to Set Upper 4 Bits:**
```c
P1 = P1 | 0xF0;   // Set upper 4 bits to 1
```

---

## ✅ **Summary:**
- **Correct and simple 8051 Embedded C programs**  
- **Delay calculations based on XTAL = 11.0592 MHz**  
- **Timer configurations and ISR handled correctly**  
- **Final answers:**
  - **P1 after loop** → 0xFF  
  - **Final P2 value after operations** → 0xF9  
  - **Masking lower 4 bits:** `& 0x0F`  
  - **Setting upper 4 bits:** `| 0xF0`
 



## ✅ **I/O Port Control**
### 1. **Toggle All Bits of a Port Continuously**
```c
#include <reg51.h>

void delay(unsigned int);

void main() {
    while(1) {
        P1 = ~P1;        // Toggle all bits of P1
        delay(500);      // Delay (adjust as needed)
    }
}

void delay(unsigned int time) {
    unsigned int i, j;
    for(i = 0; i < time; i++)
        for(j = 0; j < 1275; j++); // Approx 1 ms at 11.0592 MHz
}
```

### 2. **Toggle a Single Bit Without Affecting Others**
```c
#include <reg51.h>

sbit BIT4 = P2^4;      // Define bit

void delay(unsigned int);

void main() {
    while(1) {
        BIT4 = ~BIT4;
        delay(500);
    }
}
```

---

## ✅ **Timer Delay (Mode 1 - 16 Bit Timer)**
### General Template for Delays Using Timer 0 Mode 1
```c
#include <reg51.h>

void delay_ms(unsigned int);

void main() {
    while(1) {
        P1 = ~P1;
        delay_ms(500);    // 500 ms delay
    }
}

void delay_ms(unsigned int ms) {
    unsigned int i;
    for(i = 0; i < ms; i++) {
        TMOD = 0x01;      // Timer 0, Mode 1
        TH0 = 0xFC;       // Load high byte for 1ms (calculated)
        TL0 = 0x66;       // Load low byte for 1ms (calculated)
        TR0 = 1;          // Start timer
        while(TF0 == 0);  // Wait for overflow
        TR0 = 0;          // Stop timer
        TF0 = 0;          // Clear flag
    }
}
```

---

## ✅ **Timer Delay (Mode 2 - 8 Bit Auto-Reload)**
```c
#include <reg51.h>

void init_timer0_mode2();

void main() {
    init_timer0_mode2();

    while(1) {
        P1 = ~P1;           // Toggle P1
        TR0 = 1;            // Start timer
        while(TF0 == 0);    // Wait for overflow
        TR0 = 0;
        TF0 = 0;            // Clear flag
    }
}

void init_timer0_mode2() {
    TMOD = 0x02;        // Timer 0 Mode 2
    TH0 = 0xB2;         // Reload value (based on delay calculation)
    TL0 = 0xB2;
}
```

---

## ✅ **Using Timer Interrupts**
### Toggle a Pin with Timer Interrupt (Mode 1)
```c
#include <reg51.h>

sbit LED = P1^5;

void timer0_ISR(void) interrupt 1 {
    LED = ~LED;         // Toggle on timer overflow
}

void main() {
    TMOD = 0x01;        // Timer 0 Mode 1
    TH0 = 0x3C;         // Timer delay (example values)
    TL0 = 0xB0;
    IE = 0x82;          // Enable timer 0 interrupt
    TR0 = 1;            // Start timer

    while(1) {
        // Main loop can do other tasks
    }
}
```

---

## ✅ **External Interrupt Handling**
### Count External Events on INT0 and Show on Port
```c
#include <reg51.h>

unsigned char count = 0;

void ext0_ISR(void) interrupt 0 {
    count++;
    P1 = count;        // Show count on P1
}

void main() {
    IE = 0x81;         // Enable external interrupt 0
    while(1);          // Infinite loop
}
```

---

## ✅ **Counter Mode**
### Use T0 as Counter (Counts Pulses on P3.4)
```c
#include <reg51.h>

void main() {
    TMOD = 0x05;         // Timer 0 in counter mode 1
    TH0 = 0;
    TL0 = 0;
    TR0 = 1;             // Start counting

    while(1) {
        P1 = TL0;        // Show lower byte on P1
        P2 = TH0;        // Show higher byte on P2
    }
}
```

---

## ✅ **Traffic Light Controller**
```c
#include <reg51.h>

sbit RED = P1^0;
sbit YELLOW = P1^1;
sbit GREEN = P1^2;

void delay(unsigned int);

void main() {
    while(1) {
        GREEN = 1; RED = 0; YELLOW = 0;
        delay(60000);       // 1 min delay (example)

        GREEN = 0; YELLOW = 1; RED = 0;
        delay(2000);        // 2 sec delay

        GREEN = 0; YELLOW = 0; RED = 1;
        delay(60000);       // 1 min delay
    }
}

void delay(unsigned int time) {
    unsigned int i, j;
    for(i=0; i<time; i++)
        for(j=0; j<1275; j++);
}
```

---

## ✅ **Sensor Read and Output Control**
### Read Sensors on P1, Process, Send to P2
```c
#include <reg51.h>

void delay(unsigned int);

void main() {
    unsigned char data;

    while(1) {
        data = P1;        // Read from sensors
        delay(500);       // 500 ms delay

        P2 = 0xF0;        // Start with base value
        P2 = P2 | 0x0D;   // Set bits
        P2 = P2 & ~0x04;  // Clear bit 2
    }
}
```

---

## ✅ **Serial Communication (Optional)**
### Send Data Over UART
```c
#include <reg51.h>

void init_serial() {
    TMOD = 0x20;    // Timer 1 Mode 2
    TH1 = 0xFD;     // Baud rate 9600 for 11.0592 MHz
    SCON = 0x50;    // Mode 1, REN enabled
    TR1 = 1;        // Start Timer 1
}

void send_char(unsigned char ch) {
    SBUF = ch;
    while(TI == 0);
    TI = 0;
}

void main() {
    init_serial();
    while(1) {
        send_char('A');  // Send 'A' continuously
    }
}
```

---

## ✅ **Bit Masking (Quick Notes)**
| **Action**        | **Code** |
|-------------------|----------|
| Mask lower 4 bits | `value = value & 0x0F;` |
| Mask upper 4 bits | `value = value & 0xF0;` |
| Set upper 4 bits  | `value = value | 0xF0;` |
| Set lower 4 bits  | `value = value | 0x0F;` |

---



# 🔧 **TIMER/COUNTER CALCULATIONS IN 8051 (WITH EXAMPLES)**

---

## ✅ **1. MACHINE CYCLE TIME**

### ✨ Formula:
```
Machine Cycle Time = 12 / XTAL Frequency
```

- If **XTAL = 11.0592 MHz**:
```
Machine Cycle Time = 12 / 11.0592 MHz
                   ≈ 1.085 microseconds (µs)
```

- If **XTAL = 12 MHz**:
```
Machine Cycle Time = 12 / 12 MHz
                   = 1 microsecond (µs)
```

---

## ✅ **2. MAXIMUM DELAY CALCULATIONS**
The **delay** you can generate depends on **timer mode**.

| Mode      | Size  | Max Count   | Max Delay (XTAL = 11.0592 MHz)  |
|-----------|-------|-------------|---------------------------------|
| Mode 1    | 16-bit| 65536       | 65536 x 1.085 µs ≈ 71.1 ms     |
| Mode 2    | 8-bit | 256         | 256 x 1.085 µs ≈ 277.76 µs     |

---

---

# ✅ **3. DELAY GENERATION USING TIMERS**

### 🟢 **Goal:** Create a time delay using Timer 0 / 1.

---

### **General Formula for Counts**
```
Counts = Delay / Machine Cycle Time
```

---

## ✅ **Example 1:** **Generate 1 ms delay**
- XTAL = 11.0592 MHz → Machine Cycle = 1.085 µs
- Delay needed = 1 ms = 1000 µs
```
Counts = 1000 / 1.085 ≈ 922 counts
```
- Timer counts **down**, so:
```
THxTLx = 65536 - 922 = 64614
```
Convert 64614 to HEX:
```
64614 (decimal) = FC66 (hex)
```
```
TH = FC
TL = 66
```

---

## ✅ **Example 2:** **500 ms delay**
You can’t directly get 500 ms (since max in Mode 1 = 71.1 ms).  
So, repeat a **71 ms delay** multiple times.

```
Number of repeats = 500 / 71 ≈ 7 times (approx)
```

Create **7 x 71 ms** delay using:
```
TH = 0x00
TL = 0x00
```
**Why 0x0000?**
```
TH = 0x00, TL = 0x00 → start counting from 0000 → up to FFFF
65536 x 1.085 µs = 71.1 ms
```

So, 7 loops of 71 ms will give you ≈ 500 ms delay.

---

---

# ✅ **4. SQUARE WAVE GENERATION CALCULATIONS**

---

### 🟢 **Goal:** Create a square wave of frequency f.

---

## ✅ **Example 1:** Create 2 kHz Square Wave
- **Period (T)** = 1 / f = 1 / 2000 = 500 µs
- You need a **toggle** every **half-period** = 500 µs / 2 = **250 µs**
  
Now calculate counts for **250 µs**:
```
Counts = 250 / 1.085 ≈ 230 counts
```
```
THxTLx = 65536 - 230 = 65306
```
```
65306 (decimal) = FF1A (hex)
```
```
TH = FF
TL = 1A
```

💡 **In Code:**
```c
TH0 = 0xFF;
TL0 = 0x1A;
```

Each **timer interrupt** toggles the pin → creating a 2 kHz square wave.

---

---

# ✅ **5. SERIAL COMMUNICATION BAUD RATE CALCULATIONS**

---

### 🟢 **Goal:** Generate baud rate for serial communication

---

### **Formula (Mode 1 or 3, Timer 1 in Mode 2):**
```
Baud Rate = (2^SMOD / 32) * (Timer 1 Overflow Rate)
```

Timer 1 Overflow Rate (auto-reload, Mode 2):
```
Overflow Rate = XTAL / (12 * (256 - TH1))
```

---

## ✅ **Example:** Generate 9600 Baud Rate, XTAL = 11.0592 MHz, SMOD = 0
```
9600 = (1 / 32) * (11.0592M / (12 * (256 - TH1)))

Rearrange:
256 - TH1 = 11.0592M / (32 * 12 * 9600)
          ≈ 11.0592M / 3686400
          ≈ 3

TH1 = 256 - 3 = 253 = 0xFD
```

💡 **In Code:**
```c
TMOD = 0x20;   // Timer1 Mode2
TH1  = 0xFD;   // Baud rate 9600
SCON = 0x50;   // Serial mode 1, REN enabled
TR1  = 1;      // Start Timer1
```

---

---

# ✅ **6. BITWISE OPERATIONS (MASKING AND MANIPULATIONS)**

---

### 🟢 **Common Operations on Port Data**

---

| **Action**           | **Code**                    |
|----------------------|-----------------------------|
| Mask Lower 4 Bits    | `data = data & 0x0F;`       |
| Mask Upper 4 Bits    | `data = data & 0xF0;`       |
| Set Lower 4 Bits     | `data = data | 0x0F;`       |
| Set Upper 4 Bits     | `data = data | 0xF0;`       |
| Clear Bit 2          | `data = data & (~(1 << 2));`|
| Set Bit 2            | `data = data | (1 << 2);`   |
| Toggle Bit 2         | `data = data ^ (1 << 2);`   |

---

## ✅ **Example: Modify P2 after reading P1**
- Read data from sensors:
```c
unsigned char sensor = P1;
```
- Wait 500 ms
- Send to P2
```c
P2 = sensor;
```
- Now, modify P2:
```
Initially P2 = 0xF0;
```
```
P2 = P2 | 0x0D;  // Sets lower bits
P2 = P2 & ~0x04; // Clears bit 2
```

### **Explanation:**
```
P2 = 0xF0 → 1111 0000
P2 = P2 | 0x0D → 1111 0000 | 0000 1101 = 1111 1101
P2 = P2 & ~0x04 → 1111 1101 & 1111 1011 = 1111 1001
```

✅ Final P2 Value = `0xF9`

---

---

# ✅ **7. TRAFFIC LIGHT CONTROLLER EXAMPLE (With Timer Interrupt)**

---

### **Goal:** Use Timer 1 in Mode 2 for 1-minute delay, Timer 0 in Mode 2 for 2 ms delay

---

## ✅ **Calculations:**

### **Timer 1 Mode 2 (Auto-reload for 1 min delay)**
Impossible in 1 timer load!  
Use **interrupt counting**:

- Timer fires every **X ms**  
- Count how many times → 1 minute

For example:
- Timer delay: **10 ms**  
- 1 min = 60000 ms → 6000 interrupts

---

### **Timer 0 Mode 2 (2 ms delay):**

```
Counts = 2 ms / 1.085 µs ≈ 1843 counts
```
For 8-bit Mode 2 (max 256), so not possible → need loop or interrupt handling.

---

---

# ✅ **8. COUNTER MODE (EXTERNAL COUNTER EXAMPLE)**

---

### 🟢 **Count external pulses on P3.4 (T0)**

---

## ✅ **Example: Count pulses and show TH0 and TL0 on P1 and P2**

```c
#include <reg51.h>

void main(void)
{
    TMOD = 0x05;  // Counter0 Mode1 (16-bit)
    TR0 = 1;      // Start counter

    while(1)
    {
        P1 = TL0;  // Lower byte
        P2 = TH0;  // Upper byte
    }
}
```

---

---

# ✅ **9. GENERAL FORMULAS SUMMARY (KEEP THESE HANDY!)**

---

| **What You Need**             | **Formula/Method**                                                |
|-------------------------------|------------------------------------------------------------------|
| Machine Cycle                 | `12 / XTAL`                                                     |
| Counts for Delay              | `Delay / Machine Cycle`                                         |
| TH, TL for Mode 1             | `65536 - Counts`                                                |
| TH for Mode 2 (8-bit reload)  | `256 - Counts`                                                  |
| Max Delay Mode 1 (16-bit)     | `65536 x Machine Cycle`                                         |
| Max Delay Mode 2 (8-bit)      | `256 x Machine Cycle`                                           |
| Baud Rate                     | `Baud Rate = (2^SMOD / 32) * (Overflow Rate)`                  |
| Overflow Rate (Timer1 Mode 2) | `XTAL / (12 * (256 - TH1))`                                    |
| Toggle Square Wave            | Toggle every `Half-Period`                                      |
| Mask Lower 4 Bits             | `data & 0x0F`                                                  |
| Set Upper 4 Bits              | `data | 0xF0`                                                  |

---

---

# ✅ **EXAM TIME TIPS**
✅ Always write down the **XTAL frequency**  
✅ Calculate **Machine Cycle** first  
✅ Do **Counts = Delay / Machine Cycle**  
✅ Subtract counts from **65536 (Mode 1)** or **256 (Mode 2)**  
✅ Use **loops** for longer delays  
✅ For serial communication, **calculate TH1** for the **baud rate**  
✅ Use **bitwise operators** for masking, setting, clearing bits

---



---

## ✅ **1. MOCK TIMER PROBLEMS (With Solutions)**

---

### 🟢 **Q1. Timer 0 Delay Calculation**
**Question:**  
XTAL = 12 MHz  
Generate a delay of 5 ms using Timer 0, Mode 1 (16-bit).  
What are the TH0 and TL0 values?

**Solution:**
- Machine Cycle = 12 / 12 MHz = 1 µs  
- Delay = 5 ms = 5000 µs  
- Counts = 5000 / 1 = 5000  
- Timer counts down:  
```
65536 - 5000 = 60536  
```
- Convert 60536 to HEX:  
```
60536 / 256 = 236 → EC  
60536 % 256 = 248 → F8  
```
✅ **TH0 = EC**  
✅ **TL0 = F8**

---

### 🟢 **Q2. Create a square wave of 1 kHz on P1.1 using Timer 1, Mode 1. XTAL = 11.0592 MHz**

**Solution:**  
- Frequency = 1 kHz → Period = 1 ms  
- Toggle every half period → 0.5 ms (500 µs)  
- Counts = 500 µs / 1.085 µs ≈ 461  
- 65536 - 461 = 65075 → FE 83  
✅ TH1 = FE  
✅ TL1 = 83  
- Use interrupt to toggle pin.

---

---

## ✅ **2. TRAFFIC LIGHT CONTROLLER FULL CODE (WITH TIMER INTERRUPT)**

---

### Scenario:  
- Green ON for 1 min  
- Yellow ON for 2 sec  
- Red ON for 1 min  
- Use Timer 1 for 1-second ticks (Mode 2)  
- Use Timer 0 for transition delay (2 ms)

### **Code (Basic Example):**
```c
#include <reg51.h>

sbit RED    = P2^0;
sbit YELLOW = P2^1;
sbit GREEN  = P2^2;

unsigned int sec_counter = 0;

void Timer1_ISR(void) interrupt 3
{
    TH1 = 0x3C;  // Reload for 50ms (or adjust as needed)
    TL1 = 0xB0;

    sec_counter++;
}

void delay2ms()
{
    TMOD |= 0x02;   // Timer 0 Mode 2 (8-bit auto-reload)
    TH0 = 256 - (2 / 1.085);  // 2ms delay
    TL0 = TH0;
    TR0 = 1;
    while (TF0 == 0);
    TF0 = 0;
    TR0 = 0;
}

void main()
{
    TMOD |= 0x20;  // Timer1 Mode2 for 50ms ticks
    TH1 = 0x3C;    // Approximate 50ms delay
    TL1 = 0xB0;

    ET1 = 1;       // Enable Timer1 interrupt
    EA = 1;
    TR1 = 1;

    while (1)
    {
        GREEN = 1; YELLOW = 0; RED = 0;
        sec_counter = 0;
        while (sec_counter < 1200);  // 60 seconds (50ms x 1200)

        delay2ms();

        GREEN = 0; YELLOW = 1; RED = 0;
        sec_counter = 0;
        while (sec_counter < 40);    // 2 seconds (50ms x 40)

        delay2ms();

        GREEN = 0; YELLOW = 0; RED = 1;
        sec_counter = 0;
        while (sec_counter < 1200);  // 60 seconds (50ms x 1200)

        delay2ms();
    }
}
```

---

---

## ✅ **3. HOME AUTOMATION CONTROLLER EXAMPLE (READ SENSORS + CONTROL OUTPUT)**

---

### **Scenario:**
- Read sensors from P1  
- Send processed data to P2  
- Wait 500 ms  
- Perform bitwise operations on P2  

### **Code:**
```c
#include <reg51.h>

void delay500ms()
{
    unsigned int i, j;
    for (i=0; i<500; i++)
        for (j=0; j<1275; j++);
}

void main()
{
    unsigned char sensor_data;

    while (1)
    {
        sensor_data = P1;  // Read sensor data

        delay500ms();      // Wait 500ms

        P2 = sensor_data;  // Send to devices

        // Bitwise operations
        P2 = P2 | 0x0D;    // Set specific bits
        P2 = P2 & (~0x04); // Clear bit 2
    }
}
```

---

---

## ✅ **4. UART COMMUNICATION (SEND/RECEIVE DATA AT 9600 BAUD)**

---

### **Code Example:**
```c
#include <reg51.h>

void uart_init()
{
    TMOD = 0x20;    // Timer1 Mode2
    TH1  = 0xFD;    // 9600 baud rate (XTAL = 11.0592 MHz)
    SCON = 0x50;    // 8-bit UART, REN enabled
    TR1  = 1;       // Start Timer1
}

void uart_send(char ch)
{
    SBUF = ch;
    while (TI == 0);
    TI = 0;
}

char uart_receive()
{
    while (RI == 0);
    RI = 0;
    return SBUF;
}

void main()
{
    char received;

    uart_init();

    while (1)
    {
        received = uart_receive(); // Receive from PC
        uart_send(received);       // Echo back to PC
    }
}
```

---



# ✅ **1. Toggle Bits of a Port Continuously (with delay using Timer 0)**  
### **Scenario:**  
Write a C program to toggle all bits of Port 1 continuously with a delay of 500ms. Use Timer 0 in Mode 1 (16-bit timer). Assume XTAL = 11.0592 MHz.

### **Code:**
```c
#include <reg51.h>

void delay_500ms() {
    unsigned int i;
    for (i = 0; i < 10; i++) {    // 10 x 50ms = 500ms
        TMOD = 0x01;              // Timer 0 Mode 1
        TH0 = 0x3C;               // 50ms delay
        TL0 = 0xB0;
        TR0 = 1;                  // Start Timer 0
        while (TF0 == 0);         // Wait for overflow
        TR0 = 0;                  // Stop Timer
        TF0 = 0;                  // Clear overflow flag
    }
}

void main() {
    while (1) {
        P1 = ~P1;                 // Toggle all bits of P1
        delay_500ms();            // Wait 500ms
    }
}
```

---

# ✅ **2. Read Sensor Data from Port and Control Devices on Another Port**  
### **Scenario:**  
Read an 8-bit sensor value from P1. If the value is less than 100, send it to P2. Otherwise, send 0xFF to P2.

### **Code:**
```c
#include <reg51.h>

void main() {
    unsigned char sensor_value;
    
    while (1) {
        sensor_value = P1;       // Read sensor
        if (sensor_value < 100)
            P2 = sensor_value;   // Send to output devices
        else
            P2 = 0xFF;           // Send max value
    }
}
```

---

# ✅ **3. Toggle Only One Bit of Port (P2.4), Rest Unaffected**  
### **Scenario:**  
Toggle bit P2.4 continuously every 250ms. Do not disturb other bits.

### **Code:**
```c
#include <reg51.h>

sbit mybit = P2^4;

void delay_250ms() {
    unsigned int i;
    for (i = 0; i < 5; i++) {    // 5 x 50ms = 250ms
        TMOD = 0x01;
        TH0 = 0x3C;
        TL0 = 0xB0;
        TR0 = 1;
        while (TF0 == 0);
        TR0 = 0;
        TF0 = 0;
    }
}

void main() {
    while (1) {
        mybit = ~mybit;         // Toggle P2.4
        delay_250ms();
    }
}
```

---

# ✅ **4. Send Hex Values of 0,1,2,3,4,5,A,B,C,D to P1 (Digital Display Control)**  
### **Scenario:**  
Send the hex values corresponding to 0,1,2,3,4,5,A,B,C,D to P1 repeatedly.

### **Code:**
```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for (i=0; i<200; i++)
        for (j=0; j<1275; j++);
}

void main() {
    unsigned char arr[] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x0A, 0x0B, 0x0C, 0x0D};
    unsigned char i;
    
    while (1) {
        for (i = 0; i < 10; i++) {
            P1 = arr[i];
            delay();
        }
    }
}
```

---

# ✅ **5. Increment Port Continuously from 00 to FF (Ramp Generator / LED Demo)**  
### **Scenario:**  
Write a program to send values 00H to FFH continuously to P1.

### **Code:**
```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for (i=0; i<200; i++)
        for (j=0; j<1275; j++);
}

void main() {
    unsigned char value;
    
    while (1) {
        for (value = 0; value <= 255; value++) {
            P1 = value;
            delay();
        }
    }
}
```

---

# ✅ **6. Door Sensor + Buzzer Control**  
### **Scenario:**  
P1.1 = Door sensor input  
P1.7 = Buzzer output  
If door opens (P1.1 = 1), sound the buzzer.

### **Code:**
```c
#include <reg51.h>

sbit DOOR_SENSOR = P1^1;
sbit BUZZER = P1^7;

void main() {
    while (1) {
        if (DOOR_SENSOR == 1)
            BUZZER = 1;  // Sound buzzer
        else
            BUZZER = 0;  // Turn off buzzer
    }
}
```

---

# ✅ **7. Flash LEDs in Pattern (Knight Rider Effect)**  
### **Scenario:**  
Make LEDs connected to P1 simulate a left-right chasing effect.

### **Code:**
```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for (i=0; i<100; i++)
        for (j=0; j<1275; j++);
}

void main() {
    unsigned char pattern = 0x01;
    
    while (1) {
        P1 = pattern;
        delay();
        pattern <<= 1;
        if (pattern == 0x00)
            pattern = 0x01;
    }
}
```

---

# ✅ **8. 7-Segment Display Counter (0-9)**  
### **Scenario:**  
Show digits 0-9 on a 7-segment display connected to P1.

### **Code:**  
```c
#include <reg51.h>

// Common cathode segment values for 0-9
unsigned char digit[10] = {0x3F, 0x06, 0x5B, 0x4F,
                           0x66, 0x6D, 0x7D, 0x07,
                           0x7F, 0x6F};

void delay() {
    unsigned int i, j;
    for (i=0; i<200; i++)
        for (j=0; j<1275; j++);
}

void main() {
    unsigned char i;
    
    while (1) {
        for (i = 0; i < 10; i++) {
            P1 = digit[i];  // Send pattern to display
            delay();
        }
    }
}
```

---

# ✅ **9. Counter: Read External Pulse and Display Count on Port**  
### **Scenario:**  
Read external pulses on T0 (P3.4), count them, and display the count on P2.

### **Code:**
```c
#include <reg51.h>

void main() {
    TMOD = 0x05;    // Counter 0, Mode 1 (16-bit)
    TH0 = 0;
    TL0 = 0;
    TR0 = 1;        // Start counter
    
    while (1) {
        P2 = TL0;   // Display low byte of count on P2
    }
}
```

---

# ✅ **10. Simple Delay Program Using For Loops**  
### **Scenario:**  
Toggle LEDs connected to P1 with software delay (No Timer).

### **Code:**
```c
#include <reg51.h>

void delay() {
    unsigned int i, j;
    for (i=0; i<500; i++)
        for (j=0; j<1275; j++);
}

void main() {
    while (1) {
        P1 = 0xFF;  // Turn ON LEDs
        delay();
        P1 = 0x00;  // Turn OFF LEDs
        delay();
    }
}
```



