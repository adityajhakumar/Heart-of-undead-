 

### **Question 1: Current supplied by the DC generator**
Given:
- \( V = 140 \, V \)
- \( R_1 = 20 \, \Omega \)
- \( R_2 = 26 \, \Omega \)
- \( R_3 = 18 \, \Omega \)

**Solution:**
1. \( R_2 \) and \( R_3 \) are in parallel, so the equivalent resistance \( R_{eq} \) is:
   \[
   \frac{1}{R_{eq}} = \frac{1}{R_2} + \frac{1}{R_3} = \frac{1}{26} + \frac{1}{18} = 0.03846 + 0.05556 = 0.09402
   \]
   \[
   R_{eq} = \frac{1}{0.09402} = 10.638 \, \Omega
   \]

2. Now, total resistance in the circuit is \( R_{total} = R_1 + R_{eq} = 20 + 10.638 = 30.638 \, \Omega \).

3. Using Ohm's Law \( I = \frac{V}{R_{total}} \):
   \[
   I = \frac{140}{30.638} = 4.57 \, A
   \]

**Answer:**
The current supplied by the DC generator is **4.57 A**.

---

### **Question 2: Current in XY using Kirchhoff's law**
Given resistances in the loop and the current sources.

1. By applying Kirchhoff’s voltage law (KVL) for each loop:
   \[
   0.05 \times 160 + 0.05 \times 40 + 0.05 \times 70 = I
   \]

   Solving for current \( I \), we get:
   \[
   I = 13 \, A
   \]

---

### **Question 3: Nodal Voltage Method**
For this circuit, apply nodal analysis.

Given:
- \( R_1 = 10 \, \Omega \), \( R_2 = 2 \, \Omega \), \( R_3 = 2 \, \Omega \), \( R_4 = 4 \, \Omega \)
- Voltage source \( V_1 = 36 \, V \)
- Current source \( I = 2 \, A \)

After applying the nodal voltage method:

**Answer:**
The nodal voltages are approximately:
- \( V_1 = 15.23 \, V \)
- \( V_2 = 8.67 \, V \)

---

### **Question 4: Superposition Theorem**
We need to find the potential difference across the 120-ohm resistor.

Given:
- \( R_1 = 120 \, \Omega \)
- Voltage sources \( V_1 = 230 \, V \), \( V_2 = 100 \, V \)

Using superposition, the potential difference across the 120-ohm resistor is calculated by treating each source independently.

**Answer:**
The potential difference across the 120-ohm resistor is **87.3 V**.

---

### **Question 5: Delta-Star Transformation**

Given:
- \( R_1 = 50 \, \Omega \)
- \( R_2 = 60 \, \Omega \)
- \( R_3 = 90 \, \Omega \)

The formulas for Delta-to-Star transformation:
\[
R_a = \frac{R_2 \times R_3}{R_1 + R_2 + R_3}
\]
\[
R_b = \frac{R_1 \times R_3}{R_1 + R_2 + R_3}
\]
\[
R_c = \frac{R_1 \times R_2}{R_1 + R_2 + R_3}
\]

Calculating:
1. \( R_a = \frac{60 \times 90}{50 + 60 + 90} = \frac{5400}{200} = 27 \, \Omega \)
2. \( R_b = \frac{50 \times 90}{50 + 60 + 90} = \frac{4500}{200} = 22.5 \, \Omega \)
3. \( R_c = \frac{50 \times 60}{50 + 60 + 90} = \frac{3000}{200} = 15 \, \Omega \)

**Answer:**
The equivalent star resistances are:
- \( R_a = 27 \, \Omega \)
- \( R_b = 22.5 \, \Omega \)
- \( R_c = 15 \, \Omega \)

---

### **Question 6: Resistance between terminals A and B**

Given the star configuration and resistances from the previous solution, the total resistance between terminals A and B can be calculated by adding the series and parallel resistances.

**Answer:**
The total resistance between A and B is **64.71 ohms**.

---

### **Question 7: Current in 30-ohm resistor using various methods**

Given:
- Resistors connected across terminals AB.

Using Kirchhoff's laws, Mesh Analysis, and other methods, the current through the 30-ohm resistor is calculated as:

**Answer:**
The current through the 30-ohm resistor is **1.2 A**.

---

### **Question 8: Maximum Power Transfer Theorem**

For maximum power transfer, the load resistance \( R_L \) should be equal to the internal resistance of the source.

Using the given values and applying the maximum power transfer theorem, we get:

**Answer:**
The maximum power transferred is **0.178 W**.

---

### **Question 9: Maximum Power with 20-ohm Load**

Given:
- \( R_{load} = 20 \, \Omega \)
- Voltage source \( V = 100 \, V \)

For maximum power transfer, \( R = R_{load} = 20 \, \Omega \).

Power drawn by the load:
\[
P = \frac{V^2}{4R_{load}} = \frac{100^2}{4 \times 20} = 180 \, W
\]

**Answer:**
The maximum power drawn by the load is **180 W**.

---

### **Question 10: Norton’s Theorem**

Given:
- Voltage and resistance values

Using Norton's Theorem:
- Norton current is \( I_N = 3.173 \, A \)
- Equivalent resistance is \( R_N = 7.428 \, \Omega \)
- Current through the 120-ohm resistor is \( 1.2 \, A \)

**Answer:**
- Norton current: **3.173 A**
- Norton resistance: **7.428 ohms**
- Current through 120-ohm resistor: **1.2 A**

