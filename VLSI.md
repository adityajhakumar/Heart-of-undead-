### Numerical-Based Questions

1. **Design Hierarchy:**
   - Given a VLSI design where the overall system is divided into 4 submodules, each of which is further divided into 3 smaller modules, calculate the total number of smallest modules.

2. **Chip Yield:**
   - If 800 chips are fabricated on a wafer, and 720 of them pass the testing process, calculate the yield of the wafer.

3. **Scaling:**
   - A MOSFET is scaled by reducing its dimensions by a factor of 2. If the original gate capacitance was 5 pF, what will be the new gate capacitance after scaling?

4. **Velocity Saturation:**
   - Given that the saturation velocity of electrons in a MOSFET is \(10^7\) cm/s, calculate the electric field required to reach velocity saturation if the mobility of electrons is \(1200\) cm\(^2\)/V·s.

5. **Body Effect:**
   - Calculate the threshold voltage shift in a MOSFET with a body effect coefficient of 0.2 V\(^{0.5}\) when the substrate bias is increased by 0.5V.

6. **Drain-Induced Barrier Lowering (DIBL):**
   - For a MOSFET with a threshold voltage of 0.7V and a DIBL factor of 0.15 V/V, calculate the new threshold voltage when the drain voltage increases by 2V.

7. **Channel Length Modulation:**
   - Given a MOSFET with a channel length modulation parameter \(λ = 0.02\) V\(^{-1}\), calculate the change in drain current if the drain-source voltage increases by 1V.

8. **Capacitance-Voltage Characteristics:**
   - Calculate the capacitance of a MOS capacitor with an oxide thickness of 5 nm, dielectric constant \(ε_{ox} = 3.9\), and gate area of \(100 \mu m^2\).

9. **Power Consumption:**
   - Calculate the dynamic power consumption of a MOSFET with a capacitance of 10 pF, supply voltage of 3.3V, and a switching frequency of 1 MHz.

10. **Design Quality - Testability:**
    - If a test vector set has a coverage of 95% on a chip with 1000 possible faults, how many faults are likely to go undetected?

11. **Modularity:**
    - If a VLSI design has 6 modules, each with 3 inputs and 2 outputs, calculate the total number of possible input-output connections.

12. **MOSFET Threshold Voltage:**
    - A MOSFET has a threshold voltage of 0.6V. If the gate-source voltage is increased by 0.2V, calculate the increase in drain current assuming the MOSFET is in saturation.

13. **Scaling Impact:**
    - If the channel length of a MOSFET is scaled down by a factor of 3, calculate the percentage reduction in the drain current assuming velocity saturation effects dominate.

14. **Oxide Thickness Scaling:**
    - If the oxide thickness of a MOSFET is reduced from 10 nm to 2 nm, calculate the change in the capacitance if the gate area remains the same.

15. **Electrical Stress:**
    - A MOSFET operates at a voltage of 5V. If the oxide breakdown voltage is 12V, calculate the safety margin as a percentage of the breakdown voltage.

### Important Derivations in the Module

1. **Derivation of Drain Current in the Saturation Region:**
   - **Expression:** \(I_D = \frac{1}{2} μ_n C_{ox} \frac{W}{L} (V_{GS} - V_T)^2 (1 + λV_{DS})\)
   - **Derivation:** Starts with the basic MOSFET equations and includes the effects of channel length modulation.

2. **Capacitance-Voltage Characteristics of a MOS Capacitor:**
   - **Expression:** \(C_{ox} = \frac{ε_{ox}}{t_{ox}} A\)
   - **Derivation:** Based on the parallel plate capacitor model, deriving the capacitance as a function of oxide thickness and gate area.

3. **Body Effect on Threshold Voltage:**
   - **Expression:** \(V_T = V_{T0} + γ(\sqrt{V_{SB} + 2φ_F} - \sqrt{2φ_F})\)
   - **Derivation:** Using the depletion region model to derive the change in threshold voltage due to body bias.

4. **Drain-Induced Barrier Lowering (DIBL):**
   - **Expression:** \(ΔV_T = S \times ΔV_{DS}\)
   - **Derivation:** Involves analyzing the effect of drain voltage on the threshold voltage through potential barrier lowering.

5. **Mobility Degradation:**
   - **Expression:** \(μ = μ_0 \times (1 + θV_{GS})^{-1}\)
   - **Derivation:** Explains how increased vertical electric fields reduce carrier mobility.

6. **Velocity Saturation in MOSFET:**
   - **Expression:** \(v_{sat} = \frac{μE_{crit}}{1 + (μE_{crit}/v_{sat})}\)
   - **Derivation:** Deriving the relationship between electric field, carrier velocity, and saturation velocity.

7. **Channel Length Modulation:**
   - **Expression:** \(I_D = \frac{1}{2} μ_n C_{ox} \frac{W}{L} (V_{GS} - V_T)^2 (1 + λV_{DS})\)
   - **Derivation:** Explains how the effective channel length decreases with increasing \(V_{DS}\), leading to an increase in \(I_D\).

8. **Scaling Theory:**
   - **Expression:** \(S = \frac{L_{new}}{L_{old}} = \frac{V_{new}}{V_{old}}\)
   - **Derivation:** Discusses how scaling affects various MOSFET parameters like gate length, voltage, and capacitance.

9. **Threshold Voltage Derivation:**
   - **Expression:** \(V_T = V_{FB} + 2φ_F + \frac{Q_B}{C_{ox}}\)
   - **Derivation:** Shows how the threshold voltage is calculated based on flat-band voltage, Fermi potential, and the charge in the depletion region.

10. **Electrical Stress and Oxide Breakdown:**
    - **Expression:** \(V_{ox} = V_{gs} - V_{th}\)
    - **Derivation:** Involves calculating the stress across the oxide layer and its impact on device reliability.

### Theory-Based Questions

1. Explain the importance of the design hierarchy in VLSI and how it impacts the overall design process.

2. Discuss the different levels of integration in ICs and their significance in VLSI design.

3. What is the significance of Moore's Law in the context of VLSI design?

4. Compare and contrast the top-down and bottom-up approaches in VLSI design flow.

5. Explain the role of regularity, modularity, and locality in hierarchical VLSI design.

6. How does the concept of design quality influence the VLSI design process? Discuss testability, yield, and manufacturability.

7. Describe the impact of scaling on MOSFET performance. What are the advantages and challenges of scaling?

8. Explain the non-ideal effects in MOSFETs, such as velocity saturation, mobility degradation, and channel length modulation.

9. Discuss the effects of the body effect and Drain-Induced Barrier Lowering (DIBL) on MOSFET operation.

10. Explain the concept of capacitance-voltage characteristics in MOS capacitors and its significance in VLSI design.
