

# Assignment  
## 22BEC1074, 
##ADITYA KUMAR JHA

### 1. Serial Communication - Receiving a String and Displaying on LCD

**Question:**  
Write a program that receives a string via serial communication from a PC at 9600 baud rate and displays the received string on a 16x2 LCD using the 8051.

**Code:**
```asm
ORG 0000H
MAIN:
    MOV TMOD, #20H       ; Timer 1 in Mode 2 (8-bit auto-reload) for serial communication
    MOV TH1, #0FDH       ; Baud rate 9600 with 11.0592 MHz crystal
    MOV SCON, #50H       ; Mode 1, REN enabled
    SETB TR1             ; Start Timer 1
    MOV DPTR, #LCD_INIT  ; Initialize LCD
    ACALL WRITE_CMD
    MOV DPTR, #LCD_CLR   ; Clear LCD
    ACALL WRITE_CMD

RECEIVE:
    JNB RI, RECEIVE      ; Wait for data
    CLR RI               ; Clear receive interrupt flag
    MOV A, SBUF          ; Move received data to Accumulator
    MOV DPTR, #LCD_DATA  ; Set LCD to data mode
    ACALL WRITE_DATA
    SJMP RECEIVE         ; Keep receiving data

LCD_INIT:
    DB 38H               ; Function set: 8-bit, 2-line
    DB 0CH               ; Display ON, Cursor OFF
    DB 06H               ; Entry mode set
    DB 01H               ; Clear display
    RET

LCD_CLR:
    DB 01H               ; Clear display
    RET

LCD_DATA:
    DB 80H               ; Display on first line
    RET

WRITE_CMD:
    MOV P2, A            ; Send command to Port 2
    CLR P3.0             ; RS = 0 for command
    CLR P3.1             ; RW = 0 for write
    SETB P3.2            ; Enable = 1
    NOP                  ; Short delay
    CLR P3.2             ; Enable = 0
    RET

WRITE_DATA:
    MOV P2, A            ; Send data to Port 2
    SETB P3.0            ; RS = 1 for data
    CLR P3.1             ; RW = 0 for write
    SETB P3.2            ; Enable = 1
    NOP                  ; Short delay
    CLR P3.2             ; Enable = 0
    RET
END
```

### 2. Toggling LED Every 500 ms Using Timer 0

**Question:**  
Design a program where Timer 0 of the 8051 is used to toggle an LED every 500 ms. Explain how you would configure the timer and write the complete program.

**Code:**
```asm
ORG 0000H
MAIN:
    MOV TMOD, #01H       ; Timer 0 Mode 1 (16-bit timer)
    MOV TH0, #3CH        ; Load high byte for 500 ms delay
    MOV TL0, #0B0H       ; Load low byte for 500 ms delay
    SETB TR0             ; Start Timer 0

LOOP:
    JNB TF0, LOOP        ; Wait until Timer 0 overflows
    CLR TF0              ; Clear overflow flag
    CPL P1.0             ; Toggle LED on P1.0
    MOV TH0, #3CH        ; Reload Timer 0 for 500 ms
    MOV TL0, #0B0H
    SJMP LOOP            ; Repeat

END
```

### 3. Timer 0 Interrupt in Mode 1 for 1-Second Delay

**Question:**  
Write a program to configure Timer 0 in Mode 1 (16-bit timer) and generate a 1-second delay using Timer 0 interrupts. The program should toggle an LED connected to Port 1 each time the Timer 0 interrupt occurs.

**Code:**
```asm
ORG 0000H
    LJMP START          ; Jump to start of main program

ORG 000BH
TIMER0_ISR:
    CLR TF0             ; Clear Timer 0 interrupt flag
    CPL P1.0            ; Toggle LED on P1.0
    MOV TH0, #0B8H      ; Reload for 1-second delay
    MOV TL0, #0FCH
    RETI                ; Return from interrupt

START:
    MOV TMOD, #01H      ; Timer 0 Mode 1 (16-bit timer)
    MOV IE, #82H        ; Enable Timer 0 interrupt
    MOV TH0, #0B8H      ; Load Timer 0 for 1-second delay
    MOV TL0, #0FCH
    SETB TR0            ; Start Timer 0

HERE:
    SJMP HERE           ; Infinite loop

END
```
