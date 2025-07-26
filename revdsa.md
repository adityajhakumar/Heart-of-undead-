
## 🔧 Section: `java.util.Arrays` – The Big Toolbox

### 🔹 1. `toString(array)` – Print 1D arrays

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        System.out.println(Arrays.toString(a));
        // prints "[1, 2, 3, 4]" – nicely formatted
    }
}
```

**Explanation**:

* `Arrays.toString(a)` returns a string like `"[1, 2, 3, 4]"`.
* Perfect for logging 1D arrays. Doesn’t work for nested arrays (only prints object references).

**Output:**

```
[1, 2, 3, 4]
```

---

### 🔹 2. `deepToString(array)` – Print multidimensional arrays recursively

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] grid = {{1, 2}, {3, 4}};
        System.out.println(Arrays.deepToString(grid));
        // prints "[[1, 2], [3, 4]]" – recursive array printer
    }
}
```

**Explanation**:

* `deepToString` traverses nested arrays (2D, 3D, etc.) and prints elements at all levels.
* It avoids printing object IDs and shows actual contents.

**Output:**

```
[[1, 2], [3, 4]]
```

---

### 🔹 3. `equals(a, b)` – Compare two 1D arrays

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2};
        int[] b = {1, 2};
        System.out.println(Arrays.equals(a, b));
        // prints "true" because both arrays have same length and elements
    }
}
```

**Explanation**:

* `equals` checks element‑by‑element equality for **1D arrays only**.
* Returns `true` if same length & each pair of elements compares `==`.
* For nested arrays, use `deepEquals`.

**Output:**

```
true
```

---

### 🔹 4. `fill(array, value)` – Fill all elements with the same value

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = new int[5];
        Arrays.fill(a, 42);
        System.out.println(Arrays.toString(a));
        // prints "[42, 42, 42, 42, 42]"
    }
}
```

**Explanation**:

* `fill(a, 42)` sets every element of `a` to `42`.
* Useful for initialization or resetting values.

**Output:**

```
[42, 42, 42, 42, 42]
```

---

### 🔹 5. `sort(array)` – Sort array of primitives or objects

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {4, 2, 5, 1};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        // prints "[1, 2, 4, 5]"
    }
}
```

**Explanation**:

* For primitives, uses Dual-Pivot Quicksort.
* For `Object[]`, uses TimSort (stable sort).
* Sorts in-place in ascending order.

**Output:**

```
[1, 2, 4, 5]
```

---

### 🔹 6. `binarySearch(array, key)` – Search in a sorted array

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7, 9};
        int idx = Arrays.binarySearch(a, 5);
        System.out.println(idx);
        // prints "2" – index of the key
    }
}
```

**Explanation**:

* Requires the array to be **sorted**.
* Returns index of `key` if found.
* If not found, returns `-(insertion point) - 1`.

**Output:**

```
2
```

---

### 🔹 7. `copyOf(original, newLength)` – Resize or expand/shrink

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = Arrays.copyOf(a, 5);
        System.out.println(Arrays.toString(b));
        // prints "[1, 2, 3, 0, 0]"
    }
}
```

**Explanation**:

* If `newLength > orig.length`, extra slots = default values (`0`, `false`, `null`).
* If `newLength < orig.length`, array is truncated.

**Output:**

```
[1, 2, 3, 0, 0]
```

---

### 🔹 8. `copyOfRange(original, fromInclusive, toExclusive)` – Slice of array

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {10, 20, 30, 40, 50};
        int[] b = Arrays.copyOfRange(a, 1, 4);
        System.out.println(Arrays.toString(b));
        // prints "[20, 30, 40]"
    }
}
```

**Explanation**:

* Copies elements from index `1` up to `3` (exclusive `4`).
* If `to` > length, default values fill remaining.

**Output:**

```
[20, 30, 40]
```

---

### 🔹 9. `setAll(array, lambda)` – Initialize each element via function

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = new int[5];
        Arrays.setAll(a, i -> i * i);
        System.out.println(Arrays.toString(a));
        // prints "[0, 1, 4, 9, 16]"
    }
}
```

**Explanation**:

* `setAll(a, i -> f(i))` sets `a[i] = f(i)` for each index `i`.
* Functional style initialization—great for patterns like `i*i` or sequences.

**Output:**

```
[0, 1, 4, 9, 16]
```

---

### 🔹 10. `parallelPrefix(array, operator)` – Cumulative (prefix) operation

```java
import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};
        Arrays.parallelPrefix(a, Integer::sum);
        System.out.println(Arrays.toString(a));
        // prints "[1, 3, 6, 10]"
    }
}
```

**Explanation**:

* Transforms `a` so that each `a[i] = sum(a[0]...a[i])`.
* Uses parallel threads—efficient on large arrays.

**Output:**

```
[1, 3, 6, 10]
```

---

### 🔹 11. `parallelSort(array)` – Multi-threaded sorting

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {9, 5, 7, 1, 3};
        Arrays.parallelSort(a);
        System.out.println(Arrays.toString(a));
        // prints "[1, 3, 5, 7, 9]"
    }
}
```

**Explanation**:

* Splits large arrays, sorts chunks in threads, merges results.
* Beneficial for big data; overhead may outweigh benefit for small arrays.

**Output:**

```
[1, 3, 5, 7, 9]
```

---

### 🔹 12. `stream(array)` – Convert to `Stream` or primitive stream

```java
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        IntStream s = Arrays.stream(a);
        int sum = s.sum();
        System.out.println(sum);
        // prints "6"
    }
}
```

**Explanation**:

* `Arrays.stream(a)` allows using stream APIs: `sum()`, `average()`, `map()`, `filter()`, etc.
* Lightweight functional programming support.

**Output:**

```
6
```

---

### 🔹 13. `mismatch(a, b)` – First index where two arrays differ

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 4};
        int diff = Arrays.mismatch(a, b);
        System.out.println(diff);
        // prints "2" because a[2] != b[2]
    }
}
```

**Explanation**:

* Returns index of first unequal element.
* If equal up to the shorter length, return smaller length.
* Returns `-1` if arrays are identical.

**Output:**

```
2
```

---

### 🔹 14. `deepEquals(a, b)` – Compare nested arrays recursively

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] a = {{1, 2}, {3, 4}};
        int[][] b = {{1, 2}, {3, 4}};
        System.out.println(Arrays.deepEquals(a, b));
        // prints "true" – deep content equality
    }
}
```

**Explanation**:

* Compares nested arrays element-by-element, recursively.
* Useful for comparing 2D+ arrays.

**Output:**

```
true
```

---

### 🔹 15. `hashCode(a)` / `deepHashCode(a)` – Compute hash codes for arrays

```java
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int code = Arrays.hashCode(a);
        System.out.println(code);
        // prints an integer (depends on algorithm)
    }
}
```

**Explanation**:

* `hashCode(a)` computes a content-based hash for 1D arrays.
* `deepHashCode` works recursively on nested arrays.
* Note: Using arrays as map keys is error-prone; `List` is safer.

**Output:**

```
30817  // (will vary)
```

---

### 📋 Summary Table (w/ Purpose & Sample Output)

| Method                      | Purpose                              | Sample Output                     |
| --------------------------- | ------------------------------------ | --------------------------------- |
| `toString`                  | Print 1D arrays                      | `[1, 2, 3]`                       |
| `deepToString`              | Print nested arrays                  | `[[1, 2], [3, 4]]`                |
| `equals`                    | Compare two 1D arrays                | `true` / `false`                  |
| `deepEquals`                | Compare nested arrays                | `true`                            |
| `sort`                      | Sort in ascending order              | `[1, 2, 4, 5]`                    |
| `binarySearch`              | Find key in sorted array             | `2` (or negative insertion index) |
| `copyOf`                    | Resize or extend array               | `[1, 2, 3, 0, 0]`                 |
| `copyOfRange`               | Slice part of array                  | `[20, 30, 40]`                    |
| `fill`                      | Fill with constant                   | `[42, 42, 42, 42]`                |
| `setAll`                    | Initialize via index-based function  | `[0, 1, 4, 9, 16]`                |
| `parallelPrefix`            | Cumulative / prefix operation        | `[1, 3, 6, 10]`                   |
| `parallelSort`              | Multi-threaded sort for large arrays | `[1, 3, 5, 7, 9]`                 |
| `stream`                    | Convert to Stream for functional ops | `6` (sum)                         |
| `mismatch`                  | First differing index                | `2`                               |
| `hashCode` / `deepHashCode` | Content-based hash code              | (integer, e.g. 30817)             |

---




* **List → Array**
* **String → Array**
* **Multidimensional & Jagged (ragged) Arrays**
* **Stacks/Queues → Array**
* **Parallel operations** (`parallelSort`, `parallelPrefix`)
* **Handling Array Store Exception, covariance**
* **Dynamic (user-input) based** not hard‑coded
* **Line-by-line explanations included as comments**

---

## 🧪 Java Demo: `UltimateArraysDemo.java`

```java
import java.util.*;

public class UltimateArraysDemo {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    // 1. List<String> → String[]
    System.out.print("Enter comma‑separated words: ");
    String line = sc.nextLine();
    List<String> list = Arrays.asList(line.split(","));
    String[] words = list.toArray(new String[0]);
    // words now contains the List contents as an array
    System.out.println("words array: " + Arrays.toString(words));

    // 2. String → char[]
    System.out.print("Enter a string: ");
    String s = sc.nextLine();
    char[] chars = s.toCharArray();
    System.out.println("chars array: " + Arrays.toString(chars));

    // 3. Multidimensional and jagged arrays
    System.out.print("Enter number of rows for jagged array: ");
    int R = sc.nextInt();
    int[][] jagged = new int[R][];
    for (int i = 0; i < R; i++) {
      System.out.print("  row " + i + " length? ");
      int len = sc.nextInt();
      jagged[i] = new int[len];
      for (int j = 0; j < len; j++) {
        System.out.print("    element[" + i + "][" + j + "]? ");
        jagged[i][j] = sc.nextInt();
      }
    }
    System.out.println("Jagged array: " + Arrays.deepToString(jagged));

    // 4. Stack → Array : push inputs until empty line
    sc.nextLine();
    System.out.println("Enter numbers for stack, blank line to stop:");
    Deque<Integer> stack = new ArrayDeque<>();
    while (true) {
      String ln = sc.nextLine();
      if (ln.isBlank()) break;
      stack.push(Integer.parseInt(ln.trim()));
    }
    Integer[] stackArr = stack.toArray(new Integer[0]);
    System.out.println("Stack as array: " + Arrays.toString(stackArr));

    // 5. Queue → Array
    System.out.println("Enter numbers for queue, blank line to stop:");
    Deque<Integer> queue = new ArrayDeque<>();
    while (true) {
      String ln = sc.nextLine();
      if (ln.isBlank()) break;
      queue.add(Integer.parseInt(ln.trim()));
    }
    Integer[] queueArr = queue.toArray(new Integer[0]);
    System.out.println("Queue as array: " + Arrays.toString(queueArr));

    // 6. parallelSort
    Integer[] toSort = Arrays.copyOf(queueArr, queueArr.length);
    Arrays.parallelSort(toSort);
    System.out.println("parallelSort(queueArr): " + Arrays.toString(toSort));

    // 7. parallelPrefix on int[]
    int[] nums = Arrays.stream(queueArr).mapToInt(Integer::intValue).toArray();
    Arrays.parallelPrefix(nums, Integer::sum);
    System.out.println("parallelPrefix(nums): " + Arrays.toString(nums));

    // 8. Demonstrate covariance and ArrayStoreException
    try {
      Object[] oa = new String[2]; // covariance allowed
      oa[0] = "hello";
      oa[1] = 123;                // runtime fails with ArrayStoreException
    } catch (ArrayStoreException ex) {
      System.out.println("Caught ArrayStoreException: cannot store Integer into String[]");
    }

    sc.close();
  }
}
```

---

### 💬 Explanations (line-by-line in comments):

* **List → Array**: We read comma-separated words, split into a List, then use `list.toArray(new String[0])`. This is the **correct and idiomatic** way to convert when type matters. If the passed array is too small, a new one of the correct runtime type is created by `.toArray()` ([Medium][1], [Stack Overflow][2], [GeeksforGeeks][3], [GeeksforGeeks][4]).
* **String → char\[]**: `.toCharArray()` gives each character.
* **Jagged (ragged) array**: We allocate each row with variable length per row; uses `Arrays.deepToString` to print nested structure.
* **Stack & queue to Array**: We use `ArrayDeque` (recommended over legacy `Stack`), take user input into the structure, then convert with `.toArray(new Integer[0])`.
* **`parallelSort(Object[])`**: We sort the queue array in parallel; since it’s `Integer[]`, the Obj\[] overload applies. Java uses multi-threaded merge sort internally for large arrays ([GeeksforGeeks][5], [Baeldung][6]).
* **`parallelPrefix(int[], IntBinaryOperator)`**: Computes cumulative sum in-place: result\[i] = sum of elements 0..i. Efficient for large arrays using parallel reduction ([GeeksforGeeks][3]).
* **Array covariance & `ArrayStoreException`**: `Object[] oa = new String[2];` compiles fine, but writing non-String to it throws at runtime—a classic example ([Wikipedia][7]).

---

### ✅ What this demo includes:

* **Dynamic user input**, not fixed arrays.
* All major **List ↔ Array**, **String → Array**, **Multidimensional / Jagged arrays**, **Stack/Queue to Array** patterns.
* **Parallel operations**: sorting and prefix (cumulative) in parallel.
* **Runtime error handling**: showing covariance risk and `ArrayStoreException`.

---

## 🧠 What Are Parallel Operations?

Java’s `Arrays.parallelSort(...)` and `Arrays.parallelPrefix(...)` are **built-in, multithreaded** array operations:

* **`parallelSort`**: splits the array into subarrays, sorts each in parallel threads (via the Fork/Join framework), and merges them. Faster on large arrays; slower overhead on small ones ([GeeksforGeeks][3], [GeeksforGeeks][5]).
* **`parallelPrefix`**: performs cumulative operations (like sums, products, or any associative operator) **in parallel**, modifying the array in-place. Great for prefix sums and similar tasks on big data ([GeeksforGeeks][3]).

---


