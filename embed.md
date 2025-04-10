

- 🔹 **Basic setup**  
- 🔹 **Inputs (Button, Keypad, Sensor)**  
- 🔹 **Outputs (LED, Buzzer, Servo, LCD)**  
- 🔹 **Main loop logic**  
- 🔹 **SPI / Communication block (if needed)**  

---

## 🔰 **Ultimate Easy Embedded C++ Template (STM32 + Mbed)**

### ✅ STEP 1: Include Required Headers

```cpp
#include "mbed.h"            // Core functions
#include "TextLCD.h"         // LCD display
#include "keypad.h"          // Keypad
```

---

### ✅ STEP 2: Define Hardware Pins

```cpp
// Outputs
DigitalOut led(PA_0);          // LED
DigitalOut buzzer(PA_1);       // Buzzer
PwmOut servo(PA_8);            // Servo motor

// Inputs
DigitalIn button(PB_0);        // Button
AnalogIn sensor(PA_3);         // Sensor (LDR, potentiometer, etc.)

// Keypad
Keypad keypad(PA_10, PB_3, PB_5, PB_4, PB_10, PA_8, PA_9, PC_7);  // R1-R4, C1-C4

// LCD
TextLCD lcd(PC_0, PC_1, PB_0, PA_4, PA_1, PA_0);  // rs, e, d4-d7

// Serial for Tera Term
Serial pc(USBTX, USBRX);  // USB serial
```

---

### ✅ STEP 3: Variables (Global)

```cpp
char key;                 // For keypad input
float analog_value;       // Sensor value (0.0 to 1.0)
int button_status = 0;    // Button state
```

---

### ✅ STEP 4: Setup (Inside `main()`)

```cpp
int main() {
    // Setup
    keypad.enablePullUp();      // Enable internal pull-ups for keypad
    servo.period_ms(20);        // Set servo period (20ms = 50Hz)
    lcd.cls();                  // Clear LCD
    pc.printf("System Started\n");

    // Display welcome
    lcd.locate(0, 0);
    lcd.printf("System Ready");
```

---

### ✅ STEP 5: Main Loop Logic

```cpp
    while (1) {
        // 1️⃣ Read Digital Input
        button_status = button.read();
        if (button_status == 1) {
            led = 1;
            buzzer = 1;
        } else {
            led = 0;
            buzzer = 0;
        }

        // 2️⃣ Read Analog Sensor
        analog_value = sensor.read();  // 0.0 to 1.0
        pc.printf("Sensor: %.2f\n", analog_value);

        // 3️⃣ Control Servo Based on Sensor
        if (analog_value > 0.5)
            servo.pulsewidth_us(2000);  // ~180 degrees
        else
            servo.pulsewidth_us(1000);  // ~0 degrees

        // 4️⃣ Keypad Check
        key = keypad.getKey();
        if (key != '\0') {
            lcd.cls();
            lcd.locate(0, 0);
            lcd.printf("Key: %c", key);
            pc.printf("Pressed Key: %c\n", key);
        }

        // 5️⃣ Delay
        wait(0.5);
    }
}
```

---

## 🚀 BONUS: SPI Add-on (Only if SPI is required)

### 🔸 **For Master**

```cpp
SPI spi(PB_15, PB_14, PB_13); // MOSI, MISO, SCK
DigitalOut cs(PB_12);         // Chip select

// In main() loop:
cs = 0;
spi.write('A');     // Send data
cs = 1;
wait(0.1);
```

### 🔸 **For Slave**

```cpp
SPISlave spi_slave(PB_15, PB_14, PB_13, PB_12); // MISO, MOSI, SCK, CS

// In main() loop:
if (spi_slave.receive()) {
    char data = spi_slave.read();
    spi_slave.reply(0x01);  // Example reply
}
```

---

## 🧠 MEMORIZATION TIP

Just remember this **structure flow**:

```
[Start]
→ Define all inputs & outputs
→ Setup (servo, LCD, serial)
→ Main Loop:
   → Read button / sensor
   → Update LED / buzzer / servo
   → Check keypad
   → Print to LCD / serial
[End]
```

---

## 📝 Ready-to-Memorize Short Template (Super Simple)

```cpp
#include "mbed.h"
DigitalOut led(PA_0), buzzer(PA_1);
DigitalIn button(PB_0);
AnalogIn sensor(PA_3);
PwmOut servo(PA_8);
#include "TextLCD.h"
TextLCD lcd(PC_0, PC_1, PB_0, PA_4, PA_1, PA_0);
#include "keypad.h"
Keypad keypad(PA_10, PB_3, PB_5, PB_4, PB_10, PA_8, PA_9, PC_7);
Serial pc(USBTX, USBRX);

int main() {
    keypad.enablePullUp();
    servo.period_ms(20);
    lcd.cls();
    while (1) {
        led = button.read();
        buzzer = button.read();

        float value = sensor.read();
        if (value > 0.5) servo.pulsewidth_us(2000);
        else servo.pulsewidth_us(1000);

        char key = keypad.getKey();
        if (key != '\0') {
            lcd.cls();
            lcd.printf("Key: %c", key);
            pc.printf("Key: %c\n", key);
        }
        wait(0.5);
    }
}
```

---

Perfect question, bro 🔥 — now that you have the **skeleton/template**, the next step is to know **where and how to place the logic** when a question comes in.

---

## 🧠 **Understanding: Where to Place Logic in the Template**

You only need to worry about **two main areas**:

### 🔹 **(1) Inside the `main()` setup**
For:
- `servo.period_ms(20);`
- `keypad.enablePullUp();`
- `lcd.cls();`

💡 These are **once-only initializations**.

---

### 🔹 **(2) Inside the `while(1)` loop**
This is your **main logic area**, where everything happens **repeatedly**:
- Read inputs
- Process logic
- Show output

---

## 💡 Example: “Turn on LED when sensor value > 0.6”

✅ You place that logic like this:

```cpp
float val = sensor.read();
if (val > 0.6) {
    led = 1;
} else {
    led = 0;
}
```

👉 Place this **inside the `while(1)` loop**.

---

## 💡 Example 2: “If button is pressed, open gate using servo”

```cpp
if (button.read() == 1) {
    servo.pulsewidth_us(2000);  // open gate
    wait(2);                    // wait for vehicle
    servo.pulsewidth_us(500);   // close gate
}
```

Again, this goes inside `while(1)`

---

## 💡 Example 3: “When key ‘A’ is pressed, turn on buzzer”

```cpp
char key = keypad.getKey();
if (key == 'A') {
    buzzer = 1;
    wait(1);
    buzzer = 0;
}
```

Place this in the `while(1)` loop, inside the keypad block.

---

## 💡 Example 4: SPI Logic

If it’s a **master** device:

```cpp
cs = 0;
spi.write(0x01);  // send data
cs = 1;
```

If it’s a **slave**:

```cpp
if (spi_slave.receive()) {
    char val = spi_slave.read();
    spi_slave.reply(0xAB); // example reply
}
```

---

## 🔐 PASSWORD LOGIC EXAMPLE (4-digit)

```cpp
char password[4] = {'1','2','3','4'};
char input[4];
int count = 0;

while(count < 4) {
    char key = keypad.getKey();
    if (key != '\0') {
        input[count] = key;
        lcd.printf("*");
        count++;
        wait(0.5);
    }
}

bool correct = true;
for (int i = 0; i < 4; i++) {
    if (input[i] != password[i]) {
        correct = false;
        break;
    }
}

if (correct) {
    lcd.cls();
    lcd.printf("Access Granted");
    servo.pulsewidth_us(2000); // open
    wait(3);
    servo.pulsewidth_us(500);  // close
} else {
    lcd.cls();
    lcd.printf("Wrong Password");
    buzzer = 1;
    wait(2);
    buzzer = 0;
}
```

---

## 🚀 Final Tip: Use this **logic block map**

| Task Type            | Where to write logic          |
|---------------------|-------------------------------|
| Sensor value check  | `if(sensor.read() > x)`       |
| Button press        | `if(button.read() == 1)`      |
| Keypad input        | `char key = keypad.getKey();` |
| Servo movement      | `servo.pulsewidth_us(x);`     |
| LCD display         | `lcd.printf("text");`         |
| SPI communication   | `spi.write()` or `spi.read()` |
| Buzzer alert        | `buzzer = 1; wait(x);`        |

---

Want me to send you a **cheatsheet with logic blocks** ready for different tasks?

I'll even format it for fast memory recall. Just say the word. You're almost exam-ready! 💯
