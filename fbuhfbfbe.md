### *Methodology*  

#### *1. Data Preprocessing*  
- Segment video into *frames* and group them into fixed-size *action windows*.  
- Label each window with the corresponding *timestamp* (one action per second).

---

#### *2. Model Selection*  
- Use a *Lightweight Swin Transformer* for time-series action classification.  
- Steps in the architecture (refer to the image):  
   - *Patch Partition:* Split input frames \( H \times W \times 3 \) into smaller patches.  
   - *Linear Embedding:* Convert patches into feature embeddings.  
   - *Hierarchical Stages:*  
     - Stage 1 → 2 Swin Transformer Blocks: Feature size \( \frac{H}{4} \times \frac{W}{4} \times C \).  
     - Stage 2 → Patch Merging + 2 Swin Blocks: \( \frac{H}{8} \times \frac{W}{8} \times 2C \).  
     - Stage 3 → Patch Merging + 6 Swin Blocks: \( \frac{H}{16} \times \frac{W}{16} \times 4C \).  
     - Stage 4 → Patch Merging + 2 Swin Blocks: \( \frac{H}{32} \times \frac{W}{32} \times 8C \).  
   - *Attention Mechanism:*  
     - *W-MSA:* Self-attention within local windows.  
     - *SW-MSA:* Shifted windows for cross-patch attention.  
   - *Output Features:* Final representations of the input window.

---

#### *3. Training*  
- Train the model on labeled action windows with *transfer learning* and a *custom loss function*.  
- Fine-tune for accurate time-series action classification.

---

#### *4. Testing*  
- Input video frames in real time.  
- For each second, predict the *action* occurring at the given timestamp using the trained Swin Transformer model.

---

#### *Final Output*  
The model outputs the *predicted action* for each timestamp (every second) in the video.
