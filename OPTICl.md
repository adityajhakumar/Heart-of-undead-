# Optical Fiber Communications (BECE318L) - Digital Assignment-1
**Semester:** WIN 2024-25  
**Subject Code:** BECE318L  
**Instructor:** [Instructor Name]  

---

## Introduction

This document provides detailed solutions to the assignment questions for **Optical Fiber Communications (BECE318L)**. The topics covered include key fiber optics concepts such as refractive index, numerical aperture, transmission loss, propagation delay, and bandwidth.

---

## Assignment Questions and Detailed Solutions

### 1. **Relative Refractive Index Difference & Numerical Aperture**

#### Definitions

- **Relative Refractive Index Difference (Δ):**
  \[
  \Delta = \frac{n_1 - n_2}{n_1}
  \]
  Where:
  - \(n_1\) = refractive index of the core.
  - \(n_2\) = refractive index of the cladding.

- **Numerical Aperture (NA):**
  \[
  NA = \sqrt{n_1^2 - n_2^2} = n_1 \sqrt{2\Delta}
  \]
  Where:
  - \(n_1\) = refractive index of the core.
  - \(n_2\) = refractive index of the cladding.

#### Given Data:
- **Acceptance Angle in Air:** \(\theta_a = 22^\circ\)
- **Relative Refractive Index Difference:** \(\Delta = 3\% = 0.03\)

#### Solution:
1. **Calculate the Numerical Aperture (NA):**
   \[
   NA = \sin(\theta_a) = \sin(22^\circ) = 0.3746
   \]
2. **Use the formula \(NA = n_1 \sqrt{2\Delta}\) to solve for \(n_1\).**

#### Critical Angle (\(\theta_c\)):
Use Snell’s Law:
\[
\theta_c = \sin^{-1}\left(\frac{n_2}{n_1}\right)
\]
This helps calculate the critical angle at the core-cladding interface.

---

### 2. **Velocity of Light & Numerical Aperture Calculation**

#### Given Data:
- **Velocity of light in core:** \(v_1 = 2.01 \times 10^8 \, \text{m/s}\)
- **Critical Angle:** \(\theta_c = 80^\circ\)
- **Velocity of light in vacuum:** \(c = 2.998 \times 10^8 \, \text{m/s}\)

#### Solution:
1. **Refractive Index of the Core:**
   \[
   n_1 = \frac{c}{v_1} = \frac{2.998 \times 10^8}{2.01 \times 10^8} = 1.49
   \]
2. **Refractive Index of the Cladding:**
   \[
   n_2 = n_1 \cos(\theta_c) = 1.49 \times \cos(80^\circ) = 0.258
   \]
3. **Numerical Aperture (NA):**
   \[
   NA = \sqrt{n_1^2 - n_2^2} = \sqrt{1.49^2 - 0.258^2} = 1.48
   \]

---

### 3. **Light Transmission in Optical Fiber**

#### Ray Theory in Optical Fiber:
Light is transmitted through **total internal reflection** in optical fibers. This means light reflects off the core-cladding interface, staying within the core as it travels.

#### Acceptance Angle:
The **acceptance angle** (\(\theta_a\)) is the maximum angle at which light can enter the fiber and still be guided along the core.
\[
NA = n_0 \times \sin(\theta_a)
\]
Where:
- \(n_0\) is the refractive index of the surrounding medium (usually air).

#### Given Data for Fiber:
- **Numerical Aperture (NA):** 0.20
- **Cladding Refractive Index (\(n_2\)):** 1.59
- **Refractive Index of Surrounding Medium (Water):** \(n_0 = 1.33\)

#### Solution:
1. **Acceptance Angle in Water:**
   \[
   \sin(\alpha) = \frac{NA}{n_0} = \frac{0.20}{1.33} = 0.1504
   \]
   \[
   \alpha = \sin^{-1}(0.1504) = 8.65^\circ
   \]
2. **Critical Angle:**
   \[
   n_1 = \sqrt{NA^2 + n_2^2} = \sqrt{0.20^2 + 1.59^2} = 1.602
   \]
   \[
   \sin(\theta_c) = \frac{n_2}{n_1} = \frac{1.59}{1.602} = 0.9925
   \]
   \[
   \theta_c = \sin^{-1}(0.9925) = 82.98^\circ
   \]

---

### 4. **Optical Power Calculation**

#### Given Data:
- **Fiber Length:** 15 km
- **Fiber Loss:** 1.5 dB/km
- **Connector Attenuation:** 0.8 dB/km
- **Target Power at Detector:** 0.3 μW

#### Solution:
1. **Total Loss:**
   \[
   L = (15 \times 1.5) + (15 \times 0.8) = 22.5 + 12 = 34.5 \text{ dB}
   \]
2. **Required Input Power:**
   \[
   P_{in} = P_{out} \times 10^{(L/10)} = 0.3 \times 10^{(34.5/10)} \approx 845.4 \, \mu W
   \]

---

### 5. **Bandwidth & Pulse Broadening**

#### Given Data:
- **Link Length:** 4.5 km
- **Bandwidth:** 3.1 MHz
- **Material Dispersion:** 90 ps/nm/km

#### Solution:
1. **RMS Pulse Broadening:**
   \[
   \sigma_t = \frac{1}{2\pi B} = \frac{1}{2\pi \times 3.1 \times 10^6} = 51.2 \, \text{ns/km}
   \]
2. **RMS Spectral Width:**
   \[
   \sigma_\lambda = \sigma_t \times \text{material dispersion parameter} = 51.2 \times 90 = 4608 \, \text{ps/nm}
   \]

---

### 6. **Power Loss Calculation**

#### Given Data:
- **Attenuation Coefficient:** \( \alpha = 0.2 \, \text{dB/km} \)
- **Distance:** 100 km

#### Solution:
1. **Power Loss:**
   \[
   \text{Power Loss} = \alpha \times L = 0.2 \times 100 = 20 \, \text{dB}
   \]

---

### 7. **Propagation Delay Calculation**

#### Given Data:
- **Fiber Length:** 100 km
- **Refractive Index:** \( n = 1.48 \)

#### Solution:
1. **Propagation Velocity:**
   \[
   V = \frac{c}{n} = \frac{3 \times 10^8}{1.48} = 2.03 \times 10^8 \, \text{m/s}
   \]
2. **Propagation Delay:**
   \[
   T = \frac{L}{V} = \frac{100 \times 10^3}{2.03 \times 10^8} = 0.492 \, \text{ms}
   \]

---

### 8. **Final Received Power Calculation**

#### Given Data:
- **Attenuation:** \( 0.3 \, \text{dB/km} \)
- **Initial Power:** 10 mW
- **Distance:** 150 km

#### Solution:
1. **Final Received Power:**
   \[
   P_{out} = P_{in} \times 10^{-\frac{\alpha L}{10}} = 10 \times 10^{-4.5} = 0.316 \, \text{mW}
   \]

---

### 9. **Numerical Aperture & Acceptance Angle Calculation**

#### Given Data:
- \(n_1 = 1.48\), \(n_2 = 1.46\)

#### Solution:
1. **Numerical Aperture (NA):**
   \[
   NA = \sqrt{n_1^2 - n_2^2} = \sqrt{1.48^2 - 1.46^2} = 0.242
   \]
2. **Acceptance Angle:**
   \[
   \theta_a = \sin^{-1}(NA) = \sin^{-1}(0.242) = 14.02^\circ
   \]

---

### 10. **Group Delay Calculation**

#### Given Data:
- **Refractive Index:** \( n = 1.46 \)
- **Length:** 80 km

#### Solution:
1. **Group Delay:**
   \[
   T = \frac{nL}{c} = \frac{1.46 \times 80 \times 10^3}{3 \times 10^8} = 0.389 \, \text{ms}
   \]

---

## Conclusion

This document provides a detailed solution to the **Optical Fiber Communications** assignment, covering key principles such as the refractive index, numerical aperture, fiber loss, and propagation delay. The solutions are based on standard fiber optic equations, which are essential for understanding and designing optical communication systems.

---

