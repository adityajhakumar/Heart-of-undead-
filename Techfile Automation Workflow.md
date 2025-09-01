
# Automated Techfile Generation Workflow

## Overview

This project automates the creation of a **Technology File (Techfile)** used in **Physical Design Automation**.

Traditionally, techfiles are written manually, often reaching **11,000–12,000 lines of code**.
They describe **layers, vias, spacing rules, and constraints** required by foundry design tools (DRC, LVS, P\&R).

Manually building such a file is error-prone and time-consuming.
Our workflow automates the process using:

* **100–300 Ruby DSL files** as input
* A **Preprocessor** that structures the rules
* An **AI Agent (Qwen3-Coder-480B-A35B)** for synthesis
* A **Validation and Correction Loop** to ensure correctness

The final output is a **validated techfile** ready for use in chip design flows.

---

## 🔄 End-to-End Workflow

### **1. Input – Ruby DSL Files**

* Source files: `100–300 Ruby files`.
* Each file encodes small chunks of technology information, such as:

  * **Layer definitions** (`layer "M1" type metal thickness 0.07`)
  * **Via definitions** (`via "V1" connects M1 to M2`)
  * **Spacing rules** (`spacing M1 0.14`)
  * **Electrical rules** (timing, resistance, capacitance).

👉 Think of these as **ingredients** for the final recipe.

---

### **2. Preprocessor (Normalization Layer)**

The **Preprocessor** is a translation and cleanup step.

**What it does:**

1. **Collect Files** – Scans the directory for all Ruby DSL files.
2. **Parse Syntax** – Reads Ruby code line-by-line using a parser (`Ripper`, `ruby_parser`, or regex).
3. **Extract Data** – Pulls out key primitives: layers, vias, rules, constraints.
4. **Normalize Format** – Converts into **structured JSON/YAML** for consistency.

**Example**:

*Ruby DSL (input):*

```ruby
layer "M1" do
  type "metal"
  thickness 0.07
end

via "V1" do
  connects "M1", "M2"
end

spacing "M1", 0.14
```

*Preprocessed JSON (output):*

```json
{
  "layers": [
    {"name": "M1", "type": "metal", "thickness": "0.07"}
  ],
  "vias": [
    {"name": "V1", "connects": ["M1", "M2"]}
  ],
  "rules": [
    {"type": "spacing", "layer": "M1", "value": "0.14"}
  ]
}
```

**Why this matters:**

* **Removes Ruby syntax noise**
* **Standardizes format** → easy for AI to process
* **Ensures completeness** (no missing layers/vias)

---

### **3. AI Agent (Qwen3-Coder-480B-A35B)**

The preprocessed data is fed into a **large AI model** optimized for coding and repository-scale reasoning.

**Capabilities:**

* Handles **262k tokens context** (entire datasets at once).
* Uses **Mixture-of-Experts (MoE)** architecture for efficient code synthesis.
* Specializes in **long-context reasoning** and **agent workflows**.

**What it does here:**

* Reads structured JSON rules.
* Expands them into full **techfile code** (\~11k–12k LOC).
* Ensures correct **syntax and semantics** per industry formats (e.g., Cadence, Synopsys, Mentor).

---

### **4. Validation Step**

Every generated draft must be **validated**.

* **Checks performed:**

  * **DRC (Design Rule Check):** Verifies spacing, layers, vias follow process rules.
  * **LVS (Layout vs. Schematic):** Ensures layout rules match logical netlist.
  * **Syntax Checking:** Ensures file format is compatible with EDA tools.

👉 If the output passes → continue.
👉 If it fails → enter the correction loop.

---

### **5. Correction Loop**

If validation fails:

1. Errors are collected into an **Error Report** (e.g., missing via definition, incorrect spacing).
2. The **AI Agent** consumes this report and regenerates the techfile section.
3. The loop repeats until the techfile passes validation.

This ensures **robustness** and reduces manual debugging.

---

### **6. Assembly of Final Techfile**

* Once validated, all generated sections are **assembled** into a single **master techfile** (\~11k–12k lines).
* This final file is:

  * **Consistent**
  * **Validated**
  * **Ready for physical design automation workflows** (P\&R, DRC, LVS).

---

##  Workflow Diagram

The process can be visualized as follows:

![WhatsApp Image 2025-09-01 at 21 11 31_8cfb435c](https://github.com/user-attachments/assets/2989c55d-6530-4abb-82b5-8e8c056ae61c)


---

## Benefits

* **Automation**: Converts 300+ Ruby files into a validated 12k-line techfile.
* **Scalability**: Can handle new rules by simply re-running the pipeline.
* **Accuracy**: Validation and correction loop ensures correctness.
* **Productivity**: Reduces weeks of manual work into hours.
* **Reusability**: Preprocessor can adapt to different DSLs or foundry rules.

---

## Intended Audience

* **Non-technical users**: Can run the pipeline and obtain a techfile without deep coding knowledge.
* **Design Engineers**: Gain a validated, production-ready techfile for use in EDA tools.
* **Managers / Leads**: Ensure reliability, auditability, and speed in techfile delivery.

---

## Implementation Notes

* **Preprocessor:** Ruby (`Ripper`) or Python (`regex` + parser)
* **AI Agent:** Qwen3-Coder-480B-A35B (Alibaba Cloud endpoint)
* **Validation Tools:** Industry DRC/LVS tools (Calibre, Pegasus, etc.)
* **Storage:** JSON/YAML + Postgres/Vector DB for metadata indexing
* **Visualization:** Workflow diagrams generated with Graphviz/Mermaid

---

## How to Use

1. Place Ruby DSL files in the `/rules` directory.
2. Run the **Preprocessor** → produces `rules.json`.
3. Feed `rules.json` into the **AI Agent**.
4. Validate output with DRC/LVS tools.
5. If errors → run correction loop.
6. Assemble validated sections → `techfile_final.tf`.

---
# What kind of dataset?

Think in 5 layers. You don’t need all of them on day one, but this is the “industry standard” stack for coding/agent LLMs:

1. **Instruction/SFT (Supervised Fine-Tuning) dataset**
   Pairs of *task → ideal answer*. Here it’s:

* Ruby DSL (or its structured extract) → Techfile section
* Error report → corrected Techfile patch
* “Summarize constraints” → JSON/YAML spec

2. **Tool-use / function-calling dataset**
   Examples that teach the model *when and how* to call tools (DRC/LVS runner, parser, repo search, formatter). Each example shows:

* User request + context
* The tool schema
* The arguments the model should produce
* The tool’s result
* The final response the model should compose

3. **Long-context/repo reasoning dataset**
   “Read many files → produce a plan/changes.” Chunked corpora (100–300 Ruby files), with gold plans, section boundaries, and stitched outputs.

4. **Correction-loop dataset (feedback→fix)**
   Triples: *(draft output, validator log, corrected output)*. This is crucial for reliability.

5. **Eval/benchmark dataset**
   Held-out tasks for grading: syntax pass rate, DRC/LVS pass rate, rule coverage, token/cost, latency.

---

# Data formats (recommended)

Use **JSONL** (one JSON object per line). It’s the de-facto standard for SFT/agent training.

## 1) SFT: Ruby → Techfile section

```json
{"task_id":"L1",
 "input_format":"ruby_dsl",
 "input":"layer \"M1\" do\ntype \"metal\"\nthickness 0.07\nend\nspacing \"M1\", 0.14\n",
 "context": {"foundry":"FOUNDRY_X","node_nm":7,"target_format":"TechfileV1"},
 "output":"LAYER M1 TYPE METAL THICKNESS 0.07\nRULE SPACING M1 0.14\n"}
```

If you use a **structured preprocessor** (recommended), store that too:

```json
{"task_id":"L1-json",
 "input_format":"preprocessed_json",
 "input":{"layers":[{"name":"M1","type":"metal","thickness":0.07}],
          "rules":[{"type":"spacing","layer":"M1","value":0.14}]},
 "context":{"target_format":"TechfileV1"},
 "output":"LAYER M1 TYPE METAL THICKNESS 0.07\nRULE SPACING M1 0.14\n"}
```

## 2) Tool-use / function calling

```json
{
 "type":"tool_use",
 "messages":[
  {"role":"system","content":"You are a PDK techfile agent. Prefer tools over guessing."},
  {"role":"user","content":"Validate this section."},
  {"role":"assistant","tool_call":{
      "name":"run_drc",
      "arguments":{"techfile":"...section text...","testcells":["m1_space","via_v1"]}}
  },
  {"role":"tool","name":"run_drc","content":{
      "status":"fail",
      "errors":[{"code":"SPC-101","msg":"M1 min space 0.14 violated at x:120,y:80"}]
  }},
  {"role":"assistant","content":"Increasing M1 spacing to 0.14 as per SPC-101. Here is the corrected section:\n..."}
 ]
}
```

Define your tool schemas separately so the trainer/inference stack can register them:

```json
{"tool_schema":{"run_drc":{
  "description":"Run DRC on a snippet or full techfile",
  "parameters":{"type":"object","properties":{
     "techfile":{"type":"string"},
     "testcells":{"type":"array","items":{"type":"string"}}
  },"required":["techfile"]}}}}
```

## 3) Correction loop (draft→log→fix)

```json
{"type":"repair",
 "draft":"RULE SPACING M1 0.12",
 "validator_log":"SPC-101: Min spacing for M1 is 0.14",
 "gold_fix":"RULE SPACING M1 0.14",
 "rationale":"Increase to meet min spacing"}
```

## 4) Planning across many files

```json
{"type":"planning",
 "repo_summary":["rules/layers.rb","rules/vias.rb","rules/spacing.rb"],
 "question":"Produce layer+via sections for M1–M3 and V1–V2",
 "gold_plan":[
   "Parse layers.rb → M1..M3",
   "Parse vias.rb → V1..V2 connects",
   "Emit sections ordered by layer, then via"
 ],
 "gold_output":"LAYER M1 ...\nLAYER M2 ...\nVIA V1 CONNECTS M1 M2 ..."}
```

## 5) Evaluation items

```json
{"eval_id":"E-001",
 "input":"...preprocessed JSON...",
 "expected":"...techfile section...",
 "checks":["syntax_ok","covers_layers","drc_pass_minimal"]}
```

---

# How to build the dataset (pipeline)

1. **Collect corpora**

* All Ruby DSL files across projects/nodes foundries.
* Any existing gold techfiles (source of truth).
* Historical validator logs and the fixed outputs.

2. **Preprocess**

* Parse Ruby with **Ripper** (Ruby stdlib) or a robust custom parser.
* Normalize into **typed JSON**: `layers`, `vias`, `rules`, `materials`, `etch`, `density`, `antenna`, etc.
* Deduplicate, canonicalize units (nm/µm), resolve aliases.

3. **Align pairs**

* For each JSON chunk, align to the **exact techfile lines** that implement it.
* If alignment is fuzzy, write small matchers (regex anchors, section headers) to locate the gold span.

4. **Create scenarios**

* **Direct synthesis** (JSON→techfile)
* **Repair** (draft+log→fix)
* **Tool-use** (prompt→tool args→tool result→final)
* **Planning** (N files → ordered plan → stitched output)

5. **Split**

* Train / Dev / Test at **design-block level**, not line level, to prevent leakage.
* Keep certain foundries/nodes *only in test* if you want generalization.

6. **Quality gates**

* Lint JSON schema with JSON Schema.
* Lint techfile syntax with your formatter.
* Spot check 1–2% by an engineer.

7. **Privacy & licensing**

* Remove 3rd-party confidential content unless you have rights.
* Redact any sensitive identifiers.
* Add a dataset LICENSE and data provenance manifest.

---

# Dataset size targets (pragmatic)

* **SFT pairs (JSON→techfile)**: 8k–50k examples
* **Repair triples**: 5k–20k examples
* **Tool-use dialogues**: 2k–10k traces
* **Planning long-context**: 500–3k repos/chunks
* **Eval set**: 300–1,000 carefully curated items

Quality beats quantity; prefer fewer, well-aligned examples.

---

# Schemas you can adopt

### Preprocessed rules schema (v1)

```json
{
 "$schema":"https://example.com/techfile_schema_v1.json",
 "foundry":"FOUNDRY_X",
 "node_nm":7,
 "layers":[
   {"name":"M1","type":"metal","thickness_um":0.07,"min_width_um":0.04,"min_space_um":0.14}
 ],
 "vias":[
   {"name":"V1","from":"M1","to":"M2","cut_size_um":[0.04,0.04],"enclosure_um":{"M1":0.01,"M2":0.01}}
 ],
 "rules":[
   {"group":"spacing","layer":"M1","rule":"min_space","value_um":0.14}
 ]
}
```

### Tool trace schema (for training & auditing)

```json
{
 "trace_id":"T-2025-07-00123",
 "inputs":{"json_rules":"<…>"},
 "calls":[
   {"name":"emit_techfile","args":{"target_format":"TechfileV1"}},
   {"name":"run_drc","args":{"techfile":"<…>","testcells":["m1_space"]},"result":{"status":"fail","errors":[{"code":"SPC-101","msg":"M1 0.14"}]}},
   {"name":"apply_fix","args":{"error_code":"SPC-101"}}
 ],
 "final_output":"<validated section>"
}
```

---

# Do you need to *train* or can you use **RAG/agents**?

For many teams, you can **avoid finetuning** at first:

* Build a **Retrieval Augmented Generation** (RAG) index over your preprocessed JSON + prior techfile snippets.
* Provide **tool functions** (run\_drc, format\_techfile, diff\_apply).
* Use a strong coding model (e.g., Qwen3-Coder-480B-A35B) with **orchestration**.
  Then, once you’ve logged \~5–10k traces, fine-tune using the exact schemas above. This is cheaper and safer.

---

# Evaluation (industry-style)

Report these metrics on the held-out eval set:

* **Syntax Pass Rate**: % files that compile/parse without error.
* **DRC/LVS Pass Rate**: % designs that pass after generation.
* **Rule Coverage**: % required rules emitted (by category).
* **Edit Distance to Gold**: normalized Levenshtein for sections.
* **Time/Cost**: tokens in/out, wall-clock, tool invocations.
* **Safety/Compliance**: no undefined layers/vias; units consistent.

---

# Minimal scripts you’ll want

* `preprocess_rules/`

  * `parse_ruby.rb` → emits `rules.json`
  * `validate_json_schema.py`
* `dataset/`

  * `make_sft_jsonl.py` → builds `train.jsonl`, `dev.jsonl`, `test.jsonl`
  * `make_repair_jsonl.py`
  * `make_tool_traces_jsonl.py`
* `eval/`

  * `run_syntax_check.sh`
  * `run_drc_lvs.sh`
  * `score_eval.py`

Example **maker** for SFT JSONL (Python, pseudo-clean):

```python
import json, glob, pathlib

def load_preprocessed(path):
    return json.load(open(path))

def to_sft(example):
    return {
        "task_id": example["id"],
        "input_format": "preprocessed_json",
        "input": example["rules_json"],
        "context": {"foundry": example["foundry"], "node_nm": example["node_nm"], "target_format":"TechfileV1"},
        "output": example["gold_techfile_section"]
    }

out = open("train_sft.jsonl","w")
for p in glob.glob("preprocessed/train/*.json"):
    ex = load_preprocessed(p)
    out.write(json.dumps(to_sft(ex), ensure_ascii=False) + "\n")
out.close()
```

---

# Quick start checklist

* [ ] Define JSON schemas (rules, tools, traces).
* [ ] Build preprocessor → JSON.
* [ ] Align gold sections → produce SFT JSONL.
* [ ] Collect validator logs → build repair dataset.
* [ ] Record tool traces during normal runs (for tool-use data).
* [ ] Create eval set and scoring scripts.
* [ ] Start with RAG/agent; fine-tune later using your logs.

---

