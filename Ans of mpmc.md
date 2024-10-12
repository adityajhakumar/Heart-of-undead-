Here are the assembly language solutions for each part of the assignment:

### 1. Finding the Square Root of a Number
Assuming that the number is stored at memory offset 500h, and we store the result at memory offset 600h:

```asm
MOV AX, [500h]      ; Load the number from offset 500h
MOV BX, AX          ; Copy number to BX for division
MOV CX, 0           ; CX will hold the square root
SQRT_LOOP:
  INC CX            ; Increment CX
  MUL CX            ; AX = CX * CX
  CMP AX, BX        ; Compare with the original number
  JBE SQRT_LOOP     ; If AX <= BX, continue
  DEC CX            ; Go back one step, as it overshot
  MOV [600h], CX    ; Store the square root in memory offset 600h
```

### 2. Solving the Equation \( Y = \frac{(A + B)^2}{C} \)
Assuming A, B, and C are at memory offsets 501h, 502h, and 503h respectively:

```asm
MOV AL, [501h]      ; Load A
ADD AL, [502h]      ; Add B to A
MOV BL, AL          ; Store (A + B) in BL
MUL AL              ; Square the result (AX = (A + B)^2)
MOV CL, [503h]      ; Load C
DIV CL              ; Divide (AX) by C
MOV [600h], AL      ; Store the result in memory offset 600h
```

### 3. Finding the Sum of Cubes of Numbers in an Array
Assuming the array starts at offset 600h and contains 'n' numbers:

```asm
MOV SI, 600h        ; Start of the array
MOV CX, n           ; Number of elements in the array
MOV AX, 0           ; Initialize accumulator to hold the sum

SUM_LOOP:
  MOV BL, [SI]      ; Load each element
  MOV AL, BL        ; Copy to AL
  MUL BL            ; Cube the element (AX = AL * AL * AL)
  ADD AX, [SUM]     ; Add to sum
  ADD SI, 1         ; Move to next element in array
  LOOP SUM_LOOP     ; Repeat until all elements are processed
  MOV [602h], AX    ; Store the sum at offset 602h
```

### 4. Bubble Sort of an Array of 16-bit Integers
Assuming the array starts at a known offset, say 700h, and contains 'n' elements:

```asm
MOV CX, n           ; Number of elements
DEC CX              ; n-1 comparisons needed
SORT_OUTER:
  MOV SI, 700h      ; Start of the array
  MOV BX, CX        ; Inner loop counter

SORT_INNER:
  MOV AX, [SI]      ; Load current element
  MOV DX, [SI+2]    ; Load next element
  CMP AX, DX        ; Compare
  JBE NO_SWAP       ; If in order, skip swapping
  XCHG AX, DX       ; Swap the values
  MOV [SI], AX      ; Store the swapped values
  MOV [SI+2], DX
NO_SWAP:
  ADD SI, 2         ; Move to next pair
  DEC BX
  JNZ SORT_INNER    ; Inner loop ends

  DEC CX
  JNZ SORT_OUTER    ; Outer loop ends
```

### 5. Division of a 16-bit Number by an 8-bit Number
Assuming the 16-bit number is stored at 800h, and the 8-bit number at 801h:

```asm
MOV AX, [800h]      ; Load 16-bit number
MOV BL, [801h]      ; Load 8-bit divisor
DIV BL              ; Divide AX by BL
MOV [802h], AL      ; Store the quotient
MOV [803h], AH      ; Store the remainder
```

These programs use general assembly conventions for the 8086 microprocessor. Let me know if you need any further adjustments!
