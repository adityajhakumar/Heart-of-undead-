# README: Digital Assignment-1
## **Optical Fiber Communications (BECE318L)**
### **Semester: WIN 2024-25**

## **Introduction**
This document provides a detailed explanation of the concepts and numerical problems related to Optical Fiber Communications. The assignment covers fundamental topics such as numerical aperture, refractive index, attenuation, bandwidth limitations, and propagation delay.

## **Assignment Questions and Solutions**

### **1. Relative Refractive Index Difference & Numerical Aperture**
#### **Definition**
The relative refractive index difference \(\Delta\) is defined as:
\[
\Delta = \frac{n_1 - n_2}{n_1}
\]
where \( n_1 \) is the core refractive index and \( n_2 \) is the cladding refractive index.

The numerical aperture (NA) is related to \(\Delta\) by:
\[
NA = \sqrt{n_1^2 - n_2^2} = n_1 \sqrt{2\Delta}
\]

#### **Given Data:**
- Acceptance angle in air: \( \theta_a = 22^\circ \)
- Relative refractive index difference: \( \Delta = 3\% = 0.03 \)

#### **Solution:**
\[
NA = \sin(\theta_a) = \sin(22^\circ) = 0.3746
\]
\[
\text{Using } NA = n_1 \sqrt{2\Delta}, \text{ solving for } n_1
\]
\[
\theta_c = \sin^{-1} \left( \frac{n_2}{n_1} \right)
\]
where \( \theta_c \) is the critical angle at the core-cladding interface.

### **2. Velocity of Light & Numerical Aperture Calculation**
#### **Given Data:**
- Velocity of light in core: \( v_1 = 2.01 \times 10^8 \) m/s
- Critical angle: \( 80^\circ \)
- Velocity of light in vacuum: \( c = 2.998 \times 10^8 \) m/s

#### **Solution:**
\[
n_1 = \frac{c}{v_1} = \frac{2.998 \times 10^8}{2.01 \times 10^8} = 1.49
\]
\[
n_2 = n_1 \cos(\theta_c) = 1.49 \cos(80^\circ) = 1.49 \times 0.1736 = 0.258
\]
\[
NA = \sqrt{n_1^2 - n_2^2}
\]

### **3. Light Transmission in Optical Fiber**
#### **Explanation with Diagram**
(Include diagram showing total internal reflection)

### **4. Optical Power Calculation**
#### **Given Data:**
- Fiber length: 15 km
- Fiber loss: 1.5 dB/km
- Connector attenuation: 0.8 dB/km
- Target power at detector: 0.3 μW

#### **Solution:**
Total loss:
\[
L = (15 \times 1.5) + (15 \times 0.8) = 22.5 + 12 = 34.5 \text{ dB}
\]
\[
P_{in} = P_{out} \times 10^{(L/10)} = 0.3 \times 10^{(34.5/10)}
\]
\[
= 0.3 \times 10^{3.45} \approx 0.3 \times 2818 = 845.4
\]
Required power: **845.4 μW**

### **5. Bandwidth & Pulse Broadening**
#### **Given Data:**
- Link length: 4.5 km
- Bandwidth: 3.1 MHz
- Material dispersion: 90 ps/nm/km

#### **Solution:**
\[
\sigma_t = \frac{1}{2\pi B} = \frac{1}{2\pi \times 3.1 \times 10^6} = 51.2 \text{ ns/km}
\]

### **6. Power Loss Calculation**
#### **Given Data:**
- Attenuation coefficient: \( \alpha = 0.2 \) dB/km
- Distance: 100 km

#### **Solution:**
\[
\text{Power loss} = \alpha \times L = 0.2 \times 100 = 20 \text{ dB}
\]

### **7. Propagation Delay Calculation**
#### **Given Data:**
- Fiber length: 100 km
- Refractive index: \( n = 1.48 \)

#### **Solution:**
\[
V = \frac{c}{n} = \frac{3 \times 10^8}{1.48} = 2.03 \times 10^8 \text{ m/s}
\]
\[
T = \frac{L}{V} = \frac{100 \times 10^3}{2.03 \times 10^8} = 0.492 \text{ ms}
\]

### **8. Final Received Power Calculation**
#### **Given Data:**
- Attenuation: \( 0.3 \) dB/km
- Initial Power: 10 mW
- Distance: 150 km

#### **Solution:**
\[
P_{out} = P_{in} \times 10^{-\frac{\alpha L}{10}} = 10 \times 10^{-4.5}
\]
\[
= 10 \times 3.16 \times 10^{-5} = 0.316 \text{ mW}
\]

### **9. Numerical Aperture & Acceptance Angle Calculation**
#### **Given Data:**
- \( n_1 = 1.48 \), \( n_2 = 1.46 \)

#### **Solution:**
\[
NA = \sqrt{n_1^2 - n_2^2} = \sqrt{1.48^2 - 1.46^2} = 0.242
\]
\[
\theta_a = \sin^{-1}(NA) = \sin^{-1}(0.242) = 14.02^\circ
\]

### **10. Group Delay Calculation**
#### **Given Data:**
- Refractive index: \( n = 1.46 \)
- Length: 80 km

#### **Solution:**
\[
T = \frac{nL}{c} = \frac{1.46 \times 80 \times 10^3}{3 \times 10^8}
\]
\[
= 0.389 \text{ ms}
\]

## **Conclusion**
This document provides detailed solutions and calculations for fundamental problems in Optical Fiber Communications, reinforcing key concepts such as numerical aperture, attenuation, power loss, and propagation delay.

