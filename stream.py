import streamlit as st
import math

# Title of the calculator
st.title("Scientific Calculator")

# Input fields
expression = st.text_input("Enter your expression:", "")

# Define a function to evaluate expressions
def evaluate_expression(expression):
    try:
        # Safe evaluation
        result = eval(expression, {"__builtins__": None}, {
            "sin": math.sin,
            "cos": math.cos,
            "tan": math.tan,
            "sqrt": math.sqrt,
            "log": math.log,
            "exp": math.exp,
            "pi": math.pi,
            "e": math.e,
            "pow": math.pow,
            "factorial": math.factorial,
            "radians": math.radians,
            "degrees": math.degrees,
            # Add more math functions as needed
        })
        return result
    except Exception as e:
        return f"Error: {e}"

# If a user inputs an expression, evaluate it
if expression:
    result = evaluate_expression(expression)
    st.write("Result:", result)

# Buttons for common functions
col1, col2, col3, col4 = st.columns(4)

with col1:
    if st.button("sin"):
        st.write("Result:", evaluate_expression(f"sin({expression})"))

    if st.button("log"):
        st.write("Result:", evaluate_expression(f"log({expression})"))

with col2:
    if st.button("cos"):
        st.write("Result:", evaluate_expression(f"cos({expression})"))

    if st.button("sqrt"):
        st.write("Result:", evaluate_expression(f"sqrt({expression})"))

with col3:
    if st.button("tan"):
        st.write("Result:", evaluate_expression(f"tan({expression})"))

    if st.button("exp"):
        st.write("Result:", evaluate_expression(f"exp({expression})"))

with col4:
    if st.button("π"):
        st.write("Result:", math.pi)

    if st.button("e"):
        st.write("Result:", math.e)
