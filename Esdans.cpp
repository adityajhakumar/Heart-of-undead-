#include "mbed.h"
#include "TextLCD.h"
#include "ADXL345_I2C.h"
#include "DHT.h"
#include "CAN.h"
#include "rtos.h"

// **1. Communication Interfaces**
Serial pc(USBTX, USBRX);   // PC Serial Communication (Debugging)
Serial bt(D8, D9);         // HC-05 Bluetooth (TX, RX)
Serial esp(PC_10, PC_11);  // ESP8266 WiFi Module (TX, RX)
I2C i2c(PB_9, PB_8);       // I2C SDA, SCL
SPI spi(PB_5, PB_4, PB_3); // SPI MOSI, MISO, SCLK
CAN can1(PB_8, PB_9);      // CAN TX, RX

// **2. Sensors & Actuators**
AnalogIn lm35(A0);         // LM35 Temperature Sensor
DHT dht(D6, DHT11);        // DHT11 Humidity Sensor
DigitalIn pir(D7);         // PIR Motion Sensor (Detects Movement)
ADXL345_I2C accel(i2c);    // ADXL345 Accelerometer
DigitalOut buzzer(D2);     // Buzzer
DigitalOut led1(D3);       // LED
PwmOut motor(PB_0);        // DC Motor (PWM Speed Control)
TextLCD lcd(D7, D6, D5, D4, D3, D2); // LCD Display

// **3. Function Prototypes**
void read_temperature();
void read_humidity();
void motion_detection();
void fall_detection();
void uart_debug();
void bluetooth_communication();
void wifi_send_data();
void spi_transfer_temperature();
void i2c_multiple_devices();
void can_transmit();
void can_receive();
void pwm_motor_control();
void led_matrix_display();
void rtos_task_1();
void rtos_task_2();
void interrupt_handler();

Thread thread1;
Thread thread2;

// **Interrupts**
InterruptIn button(D4);

int main() {
    pc.printf("Embedded System Reference Code Running...\n");

    // **RTOS Threads**
    thread1.start(rtos_task_1);
    thread2.start(rtos_task_2);
    
    // **Interrupts**
    button.rise(&interrupt_handler);

    while (1) {
        read_temperature();
        read_humidity();
        motion_detection();
        fall_detection();
        uart_debug();
        bluetooth_communication();
        wifi_send_data();
        spi_transfer_temperature();
        i2c_multiple_devices();
        can_transmit();
        can_receive();
        pwm_motor_control();
        led_matrix_display();
        
        wait(1.0);
    }
}

// 📌 **1. Read Temperature from LM35**
void read_temperature() {
    float temp = lm35.read() * 100.0;
    pc.printf("Temperature: %.2f°C\n", temp);
}

// 📌 **2. Read Humidity from DHT11**
void read_humidity() {
    float humidity;
    int err = dht.readData();
    if (err == 0) {
        humidity = dht.ReadHumidity();
        pc.printf("Humidity: %.2f%%\n", humidity);
    }
}

// 📌 **3. Motion Detection (PIR Sensor)**
void motion_detection() {
    if (pir.read()) {
        pc.printf("Motion Detected!\n");
        buzzer = 1;
    } else {
        buzzer = 0;
    }
}

// 📌 **4. Fall Detection (Accelerometer)**
void fall_detection() {
    int x, y, z;
    accel.readXYZGravity(&x, &y, &z);
    if (abs(x) > 100 || abs(y) > 100) {
        buzzer = 1;
    } else {
        buzzer = 0;
    }
}

// 📌 **5. UART Debugging**
void uart_debug() {
    pc.printf("Debugging Data...\n");
}

// 📌 **6. Bluetooth HC-05 Communication**
void bluetooth_communication() {
    bt.baud(9600);
    bt.printf("Temp: %.2f°C\n", lm35.read() * 100.0);
}

// 📌 **7. WiFi Data Transmission (ESP8266)**
void wifi_send_data() {
    esp.printf("AT+CIPSEND=10\n");
    esp.printf("Temp: %.2f°C\n", lm35.read() * 100.0);
}

// 📌 **8. SPI Communication: Transfer Data**
void spi_transfer_temperature() {
    spi.format(8, 0);
    spi.frequency(1000000);
    spi.write(lm35.read() * 100.0);
}

// 📌 **9. I2C Communication: Multiple Devices**
void i2c_multiple_devices() {
    char data[2] = {0x00, 0xFF};
    i2c.write(0x52, data, 2);
}

// 📌 **10. CAN Bus: Transmit & Receive Data**
void can_transmit() {
    char data[8] = "TempData";
    CANMessage msg(1, data, sizeof(data));
    can1.write(msg);
}

void can_receive() {
    CANMessage msg;
    if (can1.read(msg)) {
        pc.printf("Received: %s\n", msg.data);
    }
}

// 📌 **11. PWM Motor Speed Control**
void pwm_motor_control() {
    motor = 0.5; // 50% Speed
}

// 📌 **12. LED Matrix Display (SPI)**
void led_matrix_display() {
    spi.write(0xF0);
}

// 📌 **13. RTOS Task 1**
void rtos_task_1() {
    while (1) {
        pc.printf("RTOS Task 1 Running...\n");
        wait(2.0);
    }
}

// 📌 **14. RTOS Task 2**
void rtos_task_2() {
    while (1) {
        pc.printf("RTOS Task 2 Running...\n");
        wait(3.0);
    }
}

// 📌 **15. Interrupt Handler**
void interrupt_handler() {
    pc.printf("Button Pressed! Interrupt Triggered!\n");
    buzzer = !buzzer;
}
