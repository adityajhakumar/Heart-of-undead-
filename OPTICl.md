# Digital Assignment-1
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
![image](https://github.com/user-attachments/assets/55bcd78a-8190-46c1-bf32-dce666313152)
![image](https://github.com/user-attachments/assets/6dbfa6ff-7b0d-48da-a135-6b0b6ee5ef91)


A light ray is launched into a fiber as shown in the diagram. The incident ray I1 enters the fiber at the angle \( \theta_a \). I1 is refracted upon entering the fiber and is transmitted to the core-cladding interface. The ray then strikes the core-cladding interface at the critical angle \( \theta_c \). I1 is totally reflected back into the core and continues to propagate along with the fiber. The incident ray I2 enters the fiber at an angle greater than \( \theta_a \). Again, I2 is refracted upon entering the fiber and is transmitted to the core-cladding interface. I2 strikes the core-cladding interface at an angle less than the critical angle \( \theta_c \). I2 is refracted into the cladding and is eventually lost. The light ray incident on the fiber core must be within the acceptance cone defined by the angle \( \theta_a \).

The acceptance angle \( \theta_a \) is the maximum angle to the axis of the fiber that light entering the fiber is propagated. The value of the angle of acceptance \( \theta_a \) depends on fiber properties and transmission conditions. The acceptance angle is related to the refractive indices of the core, cladding, and medium surrounding the fiber. The numerical aperture (NA) is a measurement of the ability of an optical fiber to capture light. The NA is also used to define the acceptance cone of an optical fiber.

The relationship between the acceptance angle and the numerical aperture is given by:
\[
NA = n_0 \times \sin \theta_a = \sqrt{n_1^2 - n_2^2}
\]
where:
- \( n_1 \) = Refractive index of the core
- \( n_2 \) = Refractive index of the cladding
- \( n_0 \) = Refractive index of the surrounding medium

#### **Numerical Calculation for Given Fiber Parameters:**

An optical fiber has a numerical aperture of 0.20 and a cladding refractive index of 1.59. Determine:

#### (a) The acceptance angle for the fiber in water (refractive index 1.33):
\[
\sin \alpha = \frac{NA}{n_0} = \frac{0.2}{1.33}
\]
\[
\alpha = \sin^{-1} (0.15) = 8.65^\circ
\]
So, the acceptance angle in water is **8.65°**.

#### (b) The critical angle at the core-cladding interface:
To find \( \theta_c \), we first determine \( n_1 \):
\[
NA^2 = n_1^2 - n_2^2
\]
\[
n_1^2 = NA^2 + n_2^2 = (0.2)^2 + (1.59)^2 = 2.5681
\]
\[
n_1 = \sqrt{2.5681} = 1.602
\]
Now, using Snell's law:
\[
\sin \theta_c = \frac{n_2}{n_1} = \frac{1.59}{1.602} = 0.9925
\]
\[
\theta_c = \sin^{-1} (0.9925) = 82.978^\circ
\]
So, the critical angle for the core-cladding interface is **82.978°**.

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

