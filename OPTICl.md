Here’s a detailed README file for your Optical Fiber Communications assignment with expanded explanations for each answer:

---

# **Optical Fiber Communications (BECE318L) - Digital Assignment-1**
**Semester:** WIN 2024-25  
**Subject Code:** BECE318L  
**Instructor:** [Instructor Name]  

---

## **Introduction**

This document contains detailed solutions to the assignment questions for the course **Optical Fiber Communications**. The topics covered in the assignment include fundamental concepts such as relative refractive index difference, numerical aperture (NA), velocity of light in the core, critical angles, transmission loss, propagation delay, and bandwidth limitations in optical fibers.

---

## **Assignment Questions and Detailed Solutions**

### **1. Relative Refractive Index Difference & Numerical Aperture**

#### **Definition of Relative Refractive Index Difference and Numerical Aperture**

The **relative refractive index difference** (\(\Delta\)) of an optical fiber is defined as:
\[
\Delta = \frac{n_1 - n_2}{n_1}
\]
where:
- \(n_1\) is the refractive index of the core.
- \(n_2\) is the refractive index of the cladding.

The **numerical aperture** (NA) is the measure of the ability of an optical fiber to capture light and is related to \(\Delta\) by the following equation:
\[
NA = \sqrt{n_1^2 - n_2^2} = n_1 \sqrt{2\Delta}
\]
Where:
- \(n_1\) is the refractive index of the core.
- \(n_2\) is the refractive index of the cladding.

#### **Given Data:**
- **Acceptance Angle in Air:** \(\theta_a = 22^\circ\)
- **Relative Refractive Index Difference:** \(\Delta = 3\% = 0.03\)

#### **Solution:**
First, we calculate the Numerical Aperture (NA) from the given acceptance angle:
\[
NA = \sin(\theta_a) = \sin(22^\circ) = 0.3746
\]
Next, we use the formula \(NA = n_1 \sqrt{2\Delta}\) and solve for \(n_1\).

For the **critical angle** \(\theta_c\), we use Snell’s law:
\[
\theta_c = \sin^{-1} \left( \frac{n_2}{n_1} \right)
\]
This equation helps us calculate the critical angle at the core–cladding interface.

---

### **2. Velocity of Light & Numerical Aperture Calculation**

#### **Given Data:**
- **Velocity of light in core:** \(v_1 = 2.01 \times 10^8 \, \text{m/s}\)
- **Critical Angle:** \(\theta_c = 80^\circ\)
- **Velocity of light in vacuum:** \(c = 2.998 \times 10^8 \, \text{m/s}\)

#### **Solution:**
We start by calculating the refractive index of the core:
\[
n_1 = \frac{c}{v_1} = \frac{2.998 \times 10^8}{2.01 \times 10^8} = 1.49
\]
Next, we compute \(n_2\) using the relationship:
\[
n_2 = n_1 \cos(\theta_c) = 1.49 \times \cos(80^\circ) = 1.49 \times 0.1736 = 0.258
\]
Finally, we use the formula for the numerical aperture:
\[
NA = \sqrt{n_1^2 - n_2^2} = \sqrt{1.49^2 - 0.258^2} = 1.48
\]

---

### **3. Light Transmission in Optical Fiber**

#### **Explanation of Ray Theory:**
In optical fibers, light is transmitted through **total internal reflection**. The incident light enters the fiber core at an angle smaller than the critical angle and is reflected back into the core. The light continues to propagate along the core with multiple reflections.

#### **Acceptance Angle:**
The acceptance angle \(\theta_a\) is the maximum angle at which light can enter the fiber and still be guided along the core. The relationship between the acceptance angle and the numerical aperture is given by:
\[
NA = n_0 \times \sin(\theta_a)
\]
where:
- \(n_0\) is the refractive index of the surrounding medium (usually air).

#### **Given Data for Fiber:**
- **Numerical Aperture (NA):** 0.20
- **Cladding Refractive Index (\(n_2\)):** 1.59
- **Refractive Index of Surrounding Medium (Water):** \(n_0 = 1.33\)

#### **Solution:**
For the **Acceptance Angle** in water:
\[
\sin \alpha = \frac{NA}{n_0} = \frac{0.20}{1.33} = 0.1504
\]
\[
\alpha = \sin^{-1}(0.1504) = 8.65^\circ
\]

For the **Critical Angle** at the core-cladding interface:
First, we calculate \(n_1\) using the relation:
\[
NA^2 = n_1^2 - n_2^2 \implies n_1^2 = NA^2 + n_2^2 = 0.20^2 + 1.59^2 = 2.5681
\]
\[
n_1 = \sqrt{2.5681} = 1.602
\]
Now, using Snell’s law:
\[
\sin \theta_c = \frac{n_2}{n_1} = \frac{1.59}{1.602} = 0.9925
\]
\[
\theta_c = \sin^{-1}(0.9925) = 82.978^\circ
\]

---

### **4. Optical Power Calculation**

#### **Given Data:**
- **Fiber Length:** 15 km
- **Fiber Loss:** 1.5 dB/km
- **Connector Attenuation:** 0.8 dB/km
- **Target Power at Detector:** 0.3 μW

#### **Solution:**
The total loss is:
\[
L = (15 \times 1.5) + (15 \times 0.8) = 22.5 + 12 = 34.5 \text{ dB}
\]
Using the equation to calculate the required input power:
\[
P_{in} = P_{out} \times 10^{(L/10)} = 0.3 \times 10^{(34.5/10)} \approx 845.4 \, \mu W
\]

---

### **5. Bandwidth & Pulse Broadening**

#### **Given Data:**
- **Link Length:** 4.5 km
- **Bandwidth:** 3.1 MHz
- **Material Dispersion:** 90 ps/nm/km

#### **Solution:**
First, we calculate the **rms pulse broadening**:
\[
\sigma_t = \frac{1}{2\pi B} = \frac{1}{2\pi \times 3.1 \times 10^6} = 51.2 \, \text{ns/km}
\]
For the **rms spectral width** of the source, we use:
\[
\sigma_\lambda = \sigma_t \times \text{material dispersion parameter} = 51.2 \times 90 = 4608 \, \text{ps/nm}
\]

---

### **6. Power Loss Calculation**

#### **Given Data:**
- **Attenuation Coefficient:** \( \alpha = 0.2 \, \text{dB/km} \)
- **Distance:** 100 km

#### **Solution:**
\[
\text{Power Loss} = \alpha \times L = 0.2 \times 100 = 20 \, \text{dB}
\]

---

### **7. Propagation Delay Calculation**

#### **Given Data:**
- **Fiber Length:** 100 km
- **Refractive Index:** \( n = 1.48 \)

#### **Solution:**
The propagation velocity is:
\[
V = \frac{c}{n} = \frac{3 \times 10^8}{1.48} = 2.03 \times 10^8 \, \text{m/s}
\]
The propagation delay is:
\[
T = \frac{L}{V} = \frac{100 \times 10^3}{2.03 \times 10^8} = 0.492 \, \text{ms}
\]

---

### **8. Final Received Power Calculation**

#### **Given Data:**
- **Attenuation:** \( 0.3 \, \text{dB/km} \)
- **Initial Power:** 10 mW
- **Distance:** 150 km

#### **Solution:**
\[
P_{out} = P_{in} \times 10^{-\frac{\alpha L}{10}} = 10 \times 10^{-4.5} = 0.316 \, \text{mW}
\]

---

### **9. Numerical Aperture & Acceptance Angle Calculation**

#### **Given Data:**
- \(n_1 = 1.48\), \(n_2 = 1.46\)

#### **Solution:**
The Numerical Aperture (NA) is:
\[
NA = \sqrt{n_1^2 - n_2^2} = \sqrt{1.48^2 - 1.46^2} = 0.242
\]
The acceptance angle is:
\[
\theta_a = \sin^{-1}(NA) = \sin^{-1}(0.242) = 14.02^\circ
\]

---

### **10. Group Delay Calculation**

#### **Given Data:**
- **Refractive Index:** \( n = 1.46 \)
- **Length:** 80 km

#### **Solution:**
The group delay is:
\[
T = \frac{nL}{c} = \frac{1.46 \times 80 \times 10^3}{3 \times 10^8} = 0.389 \, \text{ms}
\]

---

## **Conclusion**

This document provides a comprehensive solution to the assignment, reinforcing key concepts in **Optical Fiber Communications**. The calculations and explanations are based on standard equations and principles of fiber optics, such as refractive index, numerical aperture, light transmission, attenuation, propagation delay, and group delay. These solutions are fundamental to understanding the behavior of light within optical fibers and the performance parameters crucial for designing efficient optical communication systems.

--- 

**End of README**

Let me know if you need any further elaboration or formatting adjustments!
