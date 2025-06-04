1. **A detailed explanation of its logic.**
2. **A representative Java implementation** (with in‐line comments to explain each step).

You can pick and choose which pattern(s) to focus on, but having code + commentary for each will help cement how to recognize and implement the core idea.

---

## 1. Sliding Window

Maintain a “window” (usually `[left … right]`) over an array/string and expand or contract it based on whether the current window satisfies some condition (sum, count, uniqueness, etc.). This yields O(n) solutions for many subarray/substring problems.

### When to Use

* You need the **best/longest/shortest** contiguous subarray or substring that meets a property.
* Example conditions: sum ≥ K, exactly K distinct chars, no repeating characters, etc.

### Key Insight

Keep two pointers, `left` and `right`, to define the window. Move `right` forward one step at a time, update your window‐state (sum, frequency map, etc.), and **shrink** by moving `left` forward as soon as the window is invalid or you’ve found a candidate.

---

### Example Problem:

**LeetCode 3 – Longest Substring Without Repeating Characters**

> Given a string *s*, find the length of the longest substring **without repeating characters**.

#### Logic Outline

1. Use a fixed‐size frequency array or map (`lastSeen[char]`) to track the last index at which each character appeared.
2. Maintain two pointers, `start` and `end`, to represent the current sliding window `[start…end]`.
3. As `end` moves forward, if we see a character `c` that’s already in our window (i.e., `lastSeen[c] ≥ start`), we must move `start` to `lastSeen[c] + 1` to ensure the substring `[start…end]` has no duplicates.
4. At each step, update the answer as `maxLen = max(maxLen, end - start + 1)`.
5. Update `lastSeen[c] = end` for the new position of `c`.

#### Java Implementation

```java
public class SlidingWindowExamples {
    /**
     * LeetCode #3: Longest Substring Without Repeating Characters
     * Returns the length of the longest substring without repeating characters.
     */
    public int lengthOfLongestSubstring(String s) {
        // Edge case
        if (s == null || s.length() == 0) return 0;

        // Array to store last index at which each char appeared (-1 means not seen)
        int[] lastSeen = new int[256];
        Arrays.fill(lastSeen, -1);

        int maxLen = 0;
        int start = 0;  // left pointer of window

        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);

            // If c was seen and lastSeen[c] is within current window,
            // move start to lastSeen[c] + 1
            if (lastSeen[c] >= start) {
                start = lastSeen[c] + 1;
            }

            // Update last seen index of c
            lastSeen[c] = end;

            // Calculate window length and update max
            maxLen = Math.max(maxLen, end - start + 1);
        }
        return maxLen;
    }

    public static void main(String[] args) {
        SlidingWindowExamples solver = new SlidingWindowExamples();
        System.out.println(solver.lengthOfLongestSubstring("abcabcbb")); // 3 ("abc")
        System.out.println(solver.lengthOfLongestSubstring("bbbbb"));    // 1 ("b")
        System.out.println(solver.lengthOfLongestSubstring("pwwkew"));   // 3 ("wke")
    }
}
```

> **Explanation:**
>
> * We keep `lastSeen[c]` = index of last occurrence of character `c`.
> * When we advance `end` and see `c`, if `c` was seen at an index ≥ `start`, we know the window `[start…end-1]` contained `c`, so we must slide `start` to `lastSeen[c] + 1` to ensure no duplicates.
> * We always compute `currentWindowLen = end - start + 1` and update `maxLen`.

---

## 2. Two Pointers

Use two indices that move through the data (often from opposite ends) to replace an O(n²) nested loop with an O(n) scan. Common when the array/string is sorted (or can be sorted) and you look for pairs/triplets or for palindrome checks.

### When to Use

* **Sorted array**: find pairs/triplets summing to a target (e.g. 3Sum).
* **Palindrome checks**: compare left/right ends of string.
* **Merging two sorted lists/arrays**: single pass.
* **Linked list cycle detection (fast & slow pointers)** also belongs in this family.

---

### Example Problem:

**LeetCode 15 – 3Sum**

> Given an integer array nums, find all unique triplets `[a, b, c]` such that `a + b + c = 0`.

#### Logic Outline

1. Sort the array.
2. For each index `i` from `0` to `n-3`, treat `nums[i]` as the first element `a`.
3. Use two pointers:

   * `left = i + 1`
   * `right = n - 1`
     Attempt to find `b = nums[left]`, `c = nums[right]` so that `a + b + c = 0`.
4. If `sum = a + b + c` is too small, increment `left`. If too large, decrement `right`. If exactly `0`, record the triplet and skip duplicates by moving both pointers past any equal neighbors.
5. Repeat until `left >= right`. Then move `i` to the next distinct element and repeat.

#### Java Implementation

```java
public class TwoPointersExamples {
    /**
     * LeetCode #15: 3Sum
     * Returns a list of unique triplets [a,b,c] where a + b + c == 0.
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) return result;

        Arrays.sort(nums);  // 1. Sort the array

        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate values for i
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int a = nums[i];
            int left = i + 1;                // second pointer
            int right = nums.length - 1;     // third pointer

            while (left < right) {
                int b = nums[left];
                int c = nums[right];
                int sum = a + b + c;

                if (sum == 0) {
                    // Found a valid triplet
                    result.add(Arrays.asList(a, b, c));

                    // Move left and right to next distinct elements
                    while (left < right && nums[left] == b) left++;
                    while (left < right && nums[right] == c) right--;
                }
                else if (sum < 0) {
                    left++;    // sum too small → increase b
                }
                else {
                    right--;   // sum too large → decrease c
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        TwoPointersExamples solver = new TwoPointersExamples();
        int[] arr = new int[]{-1, 0, 1, 2, -1, -4};
        System.out.println(solver.threeSum(arr));
        // Output: [[-1, -1, 2], [-1, 0, 1]]
    }
}
```

> **Explanation:**
>
> * Sorting ensures we can move `left` and `right` inward in a disciplined way.
> * We skip duplicates at `i` to avoid repeating the same triplet.
> * Inside the while‐loop, if the sum is too small, we move `left` up (to increase `b`); if too large, we move `right` down (to decrease `c`).

---

## 3. Fast & Slow Pointers (Cycle Detection)

Also called **Floyd’s Cycle–Finding Algorithm**. Use two pointers (`slow`, `fast`) moving at different speeds through a linked structure (array or linked list). If they ever meet, there’s a cycle. If `fast` reaches the end, no cycle.

### When to Use

* **Detecting a loop** in a linked list.
* **Finding the start of the cycle** (Linked List Cycle II).
* **Finding the middle node** of a list (by letting `fast` move twice as fast as `slow` until `fast` hits end).

---

### Example Problem:

**LeetCode 141 – Linked List Cycle**

> Given a singly linked list, determine if it has a cycle.

#### Logic Outline

1. Initialize `slow = head`, `fast = head`.
2. Iterate while `fast` and `fast.next` are not null:

   * Move `slow = slow.next` (one step).
   * Move `fast = fast.next.next` (two steps).
   * If at any point `slow == fast`, there is a cycle → return `true`.
3. If the loop terminates (fast reached end), return `false`.

#### Java Implementation

```java
/**
 * LeetCode #141: Linked List Cycle
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}

public class FastSlowPointersExamples {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        // Move fast by 2, slow by 1
        while (fast != null && fast.next != null) {
            slow = slow.next;           // one step
            fast = fast.next.next;      // two steps

            if (slow == fast) {         // they met → cycle
                return true;
            }
        }
        return false;  // fast hit the end → no cycle
    }

    public static void main(String[] args) {
        // Example: create a list 1->2->3->4->2 (cycle at node 2)
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = head.next; // pointer back to node with val=2

        FastSlowPointersExamples solver = new FastSlowPointersExamples();
        System.out.println(solver.hasCycle(head)); // true
    }
}
```

> **Explanation:**
>
> * `slow` moves one step at a time; `fast` moves two.
> * If they ever point to the same node, we’ve detected a cycle.
> * If `fast` or `fast.next` becomes `null`, we exit → no cycle.

---

## 4. Merge Intervals

Sort the intervals by start time and then iterate, merging any that overlap. Often used in scheduling problems or when you must coalesce overlapping ranges.

### When to Use

* You have a list of intervals / time ranges and need to merge overlapping ones.
* You want to insert a new interval and re‐merge.
* You need to count how many intervals overlap at a point (meeting room problems).

---

### Example Problem:

**LeetCode 56 – Merge Intervals**

> Given an array of intervals `[start, end]`, merge all overlapping intervals and return a new list of disjoint intervals.

#### Logic Outline

1. Sort `intervals` by the start time (i.e. `intervals[i][0]`).
2. Initialize an empty result list `merged`.
3. Iterate over each interval `curr = intervals[i]`:

   * If `merged` is empty or `merged.get(last).end < curr.start`, there’s no overlap → just append `curr` to `merged`.
   * Otherwise, they overlap: set `merged.get(last).end = max(merged.get(last).end, curr.end)`.
4. Return `merged`.

#### Java Implementation

```java
import java.util.*;

public class MergeIntervalsExamples {
    /**
     * LeetCode #56: Merge Intervals
     * Input: intervals: array of [start,end]
     * Output: merged array of non-overlapping intervals.
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return new int[0][2];

        // 1. Sort intervals by their start value
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> mergedList = new ArrayList<>();
        for (int[] curr : intervals) {
            // If the list is empty or no overlap, add it
            if (mergedList.isEmpty() || mergedList.get(mergedList.size() - 1)[1] < curr[0]) {
                mergedList.add(curr);
            } else {
                // Overlap exists, merge by extending the end
                mergedList.get(mergedList.size() - 1)[1] = 
                    Math.max(mergedList.get(mergedList.size() - 1)[1], curr[1]);
            }
        }
        // Convert List<int[]> back to int[][]
        return mergedList.toArray(new int[mergedList.size()][]);
    }

    public static void main(String[] args) {
        MergeIntervalsExamples solver = new MergeIntervalsExamples();
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] merged = solver.merge(intervals);
        // Expected output: [[1,6],[8,10],[15,18]]
        for (int[] in : merged) {
            System.out.println(Arrays.toString(in));
        }
    }
}
```

> **Explanation:**
>
> * We **sort** by ascending start time.
> * Each new interval either **starts after** (`>`) the last merged interval’s end (no overlap) → append,
>   or **overlaps** → merge by updating the end to `max(currentEnd, existingEnd)`.

---

## 5. Cyclic Sort

When an array contains numbers in the range `[1…n]` (or `[0…n-1]`), you can place each number at the “correct index” by repeatedly swapping `nums[i]` with `nums[nums[i] - 1]` (or `nums[i]` with `nums[nums[i]]`). After this in-place rearrangement, any index `i` such that `nums[i] != i+1` indicates a missing or duplicate. This runs in O(n) and uses O(1) extra space.

### When to Use

* The input is a permutation (or close to it) of the numbers `1…n` (or `0…n-1`).
* You need to find missing or duplicate elements in O(n) time and O(1) space.
* Examples: “First Missing Positive”, “Find All Numbers Disappeared in an Array”.

---

### Example Problem:

**LeetCode 268 – Missing Number**

> Given an array containing `n` distinct numbers in the range `[0…n]`, return the only number in `[0…n]` that is missing.

#### Logic Outline

1. Iterate `i` from `0` to `n-1`:

   * While `nums[i] < n` AND `nums[i] != i`, swap `nums[i]` with `nums[ nums[i] ]`.
   * (i.e., place each element `x` at index `x` whenever possible).
2. After this loop, one of three things holds at index `i`:

   * If `nums[i] == i`, that index is “correct.”
   * Otherwise (must be `nums[i] == n` or a placeholder), the missing number is `i`.

#### Java Implementation

```java
public class CyclicSortExamples {
    /**
     * LeetCode #268: Missing Number
     * Given nums containing n distinct numbers in [0..n], find the missing number.
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int i = 0;

        // 1. Place each number x (< n) at index x by swapping
        while (i < n) {
            int curr = nums[i];
            if (curr < n && curr != i) {
                // Swap nums[i] with nums[curr]
                int temp = nums[curr];
                nums[curr] = curr;
                nums[i] = temp;
            } else {
                i++;
            }
        }

        // 2. The first i where nums[i] != i is the missing number
        for (i = 0; i < n; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        // All indices [0..n-1] are correct, so missing = n
        return n;
    }

    public static void main(String[] args) {
        CyclicSortExamples solver = new CyclicSortExamples();
        System.out.println(solver.missingNumber(new int[]{3,0,1})); // 2
        System.out.println(solver.missingNumber(new int[]{0,1}));   // 2
    }
}
```

> **Explanation:**
>
> * During the `while` loop, each number `x` (< n) ends up at index `x`.
> * If at position `i` the number isn’t `i`, then `i` is missing from the array; otherwise, if all match, `n` is missing.

---

## 6. In‐Place Reversal (Linked List)

Reverse a singly linked list (or a portion thereof) by manipulating pointers directly, using O(1) extra space.

### When to Use

* You need to reverse a whole linked list.
* You need to reverse sublists of size `k` (e.g. “Reverse every k-group”).
* Many problems ask to “reorder” a list in‐place without extra arrays.

---

### Example Problem:

**LeetCode 206 – Reverse Linked List**

> Reverse a singly linked list and return the head of the reversed list.

#### Logic Outline

1. Maintain two pointers: `prev` (initially `null`) and `curr` (initially `head`).
2. While `curr != null`:

   * Store `nextTemp = curr.next`.
   * Set `curr.next = prev` (reverse pointer).
   * Move `prev = curr`.
   * Move `curr = nextTemp`.
3. At the end, `prev` points to the new head of the reversed list.

#### Java Implementation

```java
/**
 * LeetCode #206: Reverse Linked List
 * Definition for singly-linked list:
 */
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}

public class InPlaceReverseExamples {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextTemp = curr.next;  // save next
            curr.next = prev;               // reverse pointer
            prev = curr;                    // move prev forward
            curr = nextTemp;                // move curr forward
        }
        // prev is new head
        return prev;
    }

    // Helper to print list
    private void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        InPlaceReverseExamples solver = new InPlaceReverseExamples();
        // Example: 1->2->3->4->null
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);

        System.out.print("Original: ");
        solver.printList(head);

        ListNode reversed = solver.reverseList(head);
        System.out.print("Reversed: ");
        solver.printList(reversed);
    }
}
```

> **Explanation:**
>
> * `prev` always trails `curr`.
> * On each iteration, redirect `curr.next` to `prev`, then advance both pointers by one step using `nextTemp`.

---

## 7. Stack (LIFO)

Use a stack to solve problems where the most recent (last) item must be processed first. Common interview uses include balanced‐parentheses checks, evaluating postfix expressions, or a “min stack” that returns the minimum element in O(1).

### When to Use

* You need a **LIFO** data flow (e.g., parse nested structures, maintain “last seen” state).
* Evaluate arithmetic expressions in Reverse Polish Notation.
* Implement a “MinStack” (support both push/pop and retrieving min in O(1)).

---

### Example Problem:

**LeetCode 20 – Valid Parentheses**

> Given a string containing just the characters `()[]{} `, determine if the input string is valid. An input is valid if:
>
> 1. Open brackets are closed by the same type of brackets.
> 2. Open brackets are closed in the correct order.

#### Logic Outline

1. Initialize an empty stack of characters.
2. Traverse each character `c` in the string:

   * If `c` is an opening bracket (`(`, `[`, `{`), push it onto the stack.
   * Else (it’s a closing bracket), check if the stack is empty → invalid. Otherwise, pop the top of the stack (call it `topChar`) and verify that `(topChar, c)` form a matching pair. If not, invalid.
3. At the end, if the stack is empty, the string is valid; otherwise, invalid.

#### Java Implementation

```java
import java.util.*;

public class StackExamples {
    /**
     * LeetCode #20: Valid Parentheses
     * Returns true if brackets are balanced and correctly ordered.
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        // Map of matching pairs
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');

        for (char c : s.toCharArray()) {
            // If it's an opening bracket, push onto stack
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                // c is a closing bracket: must match top of stack
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    return false;
                }
            }
        }
        // If all pairs matched, stack should be empty
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        StackExamples solver = new StackExamples();
        System.out.println(solver.isValid("()[]{}"));   // true
        System.out.println(solver.isValid("([)]"));     // false
        System.out.println(solver.isValid("{[]}"));     // true
    }
}
```

> **Explanation:**
>
> * We push only opening brackets.
> * On seeing a closing bracket, we pop and check if it matches the corresponding opening bracket (via a map).
> * If at the end the stack is empty, all brackets matched correctly.

---

## 8. Monotonic Stack

A “monotonic stack” is a stack that maintains its elements in strictly increasing (or decreasing) order. As you iterate through the input, you pop from the stack until the top is either larger (or smaller) than the current element. Useful for “Next Greater Element” or “Largest Rectangle in Histogram.”

### When to Use

* You need to find the **next/previous greater or smaller element** for each position.
* You need to calculate spans or ranges (histogram area, rainwater trapping).
* Typical interview problems: Next Greater Element, Daily Temperatures, Trapping Rain Water.

---

### Example Problem:

**LeetCode 739 – Daily Temperatures**

> Given an array of daily temperatures `T`, return an array `answer` such that `answer[i]` is the number of days you have to wait after day *i* to get a warmer temperature. If there is no such future day, put `0` instead.

#### Logic Outline

1. Use a stack of indices; the stack will be **monotonically decreasing** by temperature.
2. Iterate `i` from `0` to `n-1`:

   * While stack not empty **and** `T[i] > T[stack.peek()]`, we’ve found the next warmer day for index `stack.peek()`. Let `j = stack.pop()`. Then `answer[j] = i - j`.
   * Push `i` onto the stack.
3. Any indices left in the stack have no warmer future day → `answer[...]` defaulted to `0`.

#### Java Implementation

```java
import java.util.*;

public class MonotonicStackExamples {
    /**
     * LeetCode #739: Daily Temperatures
     * For each day i, find how many days until a warmer temperature.
     */
    public int[] dailyTemperatures(int[] T) {
        int n = T.length;
        int[] answer = new int[n];
        Stack<Integer> stack = new Stack<>(); 
        // Stack holds indices of days, temperatures strictly decreasing

        for (int i = 0; i < n; i++) {
            // While current temp T[i] > temp at top index, we’ve found next warmer
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int prevIndex = stack.pop();
                answer[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        // Any index left has no warmer day → answer[...] stays 0
        return answer;
    }

    public static void main(String[] args) {
        MonotonicStackExamples solver = new MonotonicStackExamples();
        int[] T = {73,74,75,71,69,72,76,73};
        System.out.println(Arrays.toString(solver.dailyTemperatures(T)));
        // Output: [1,1,4,2,1,1,0,0]
    }
}
```

> **Explanation:**
>
> * We keep a stack of indices where the daily temperature is **monotonically decreasing** from bottom to top.
> * When we see a day `i` with `T[i] > T[stack.peek()]`, that means `i` is the next warmer day for `stack.peek()`. We pop and record `answer[prevIndex] = i - prevIndex`.
> * Then push `i` to possibly serve as “future warmer day” for subsequent indices.

---

## 9. Hash Map (Hashing)

Whenever you need **O(1)** lookups for counts, indices, or to check existence, a hash map (or array for limited key‐range) is the default pattern. Problems like Two Sum, Group Anagrams, or subarray sum = K rely on hashing.

### When to Use

* You need to detect duplicates or store counts of elements.
* You want to find if a complement exists (Two Sum).
* You’re doing frequency‐based sliding‐window or anagram grouping.

---

### Example Problem:

**LeetCode 1 – Two Sum**

> Given an integer array `nums` and a target `t`, return indices `[i, j]` such that `nums[i] + nums[j] = t`. Exactly one solution exists, do not use same element twice.

#### Logic Outline

1. Create a `HashMap<Integer, Integer>`, mapping number → index.
2. Iterate `i` from `0` to `n-1`:

   * Compute `complement = target - nums[i]`.
   * If `complement` is in the map, return `[map.get(complement), i]`.
   * Otherwise, put `nums[i] → i` into the map.
3. (Per problem constraints, you will always find a solution.)

#### Java Implementation

```java
import java.util.*;

public class HashMapExamples {
    /**
     * LeetCode #1: Two Sum
     * Returns indices of two numbers that add up to target.
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        // key = number, value = index

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{ map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public static void main(String[] args) {
        HashMapExamples solver = new HashMapExamples();
        int[] nums = {2,7,11,15};
        int target = 9;
        int[] ans = solver.twoSum(nums, target);
        System.out.println(Arrays.toString(ans)); // [0,1]
    }
}
```

> **Explanation:**
>
> * As you scan each `nums[i]`, look up `target - nums[i]` in constant time.
> * If found, you have your pair. Otherwise, store `nums[i]` for future lookups.

---

## 10. Tree BFS (Level‐Order Traversal)

Breadth‐First Search on a tree uses a queue to process nodes level by level. Used for level‐order outputs, shortest path (on unweighted trees), or for “connect next right pointers.”

### When to Use

* You need to process a tree level‐by‐level (e.g. `levelOrder`, `zigzagLevelOrder`).
* You need to find the minimum depth (first leaf encountered in BFS).
* You need to connect siblings horizontally on the same level.

---

### Example Problem:

**LeetCode 102 – Binary Tree Level Order Traversal**

> Return the level‐order traversal of its nodes’ values. (i.e., from left to right, level by level).

#### Logic Outline

1. If `root == null`, return empty list.
2. Initialize a `Queue<TreeNode> queue` and enqueue `root`.
3. While `queue` is not empty:

   * Let `size = queue.size()`. This is how many nodes are in the current level.
   * Create a `List<Integer> levelList` to store values of this level.
   * For `i` in `0…size-1`:

     * `node = queue.poll()`, add `node.val` to `levelList`.
     * If `node.left` != null, `queue.offer(node.left)`.
     * If `node.right` != null, `queue.offer(node.right)`.
   * Add `levelList` to the result list.
4. Return the result.

#### Java Implementation

```java
import java.util.*;

class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int x) { val = x; left = null; right = null; }
}

public class TreeBFSExamples {
    /**
     * LeetCode #102: Binary Tree Level Order Traversal
     * Returns a list of levels, where each level is a list of node values.
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> levelList = new ArrayList<>();

            // Process all nodes at the current level
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                levelList.add(node.val);

                if (node.left != null)  queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(levelList);
        }
        return result;
    }

    public static void main(String[] args) {
        TreeBFSExamples solver = new TreeBFSExamples();

        // Build test tree:      3
        //                     /   \
        //                    9     20
        //                         /  \
        //                        15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        List<List<Integer>> levels = solver.levelOrder(root);
        System.out.println(levels); // [[3], [9, 20], [15, 7]]
    }
}
```

> **Explanation:**
>
> * We push `root` into a queue.
> * Each iteration processes exactly `queue.size()` nodes (all in one level), appends their values to `levelList`, and enqueues their children for the next level.
> * Repeat until `queue` is empty.

---

## 11. Tree DFS (Preorder/Inorder/Postorder)

Depth‐First Search on a tree uses **recursion** (or an explicit stack) to explore as far as possible before backtracking. Each order (preorder, inorder, postorder) is useful for different tasks: computing subtree sums, validating BST, serialization, etc.

### When to Use

* You need to **visit all nodes** in a certain order (e.g. `inorder` in BST to get sorted order).
* You need to compute something bottom‐up (postorder), like subtree height or path sums.
* You need to solve “path‐sum,” “serialize/deserialize,” or “max path sum” problems.

---

### Example Problem:

**LeetCode 104 – Maximum Depth of Binary Tree**

> Given a binary tree, find its maximum depth (number of nodes along the longest path from root down to the farthest leaf).

#### Logic Outline (DFS – Recursion)

1. If `root == null`, return `0`.
2. Recursively compute `leftDepth = maxDepth(root.left)` and `rightDepth = maxDepth(root.right)`.
3. Return `1 + max(leftDepth, rightDepth)`.

#### Java Implementation

```java
public class TreeDFSExamples {
    /**
     * LeetCode #104: Maximum Depth of Binary Tree
     * Returns the height (max depth) of a binary tree.
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        // Depth is 1 (root itself) + max depth of left or right subtree
        int leftDepth  = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return 1 + Math.max(leftDepth, rightDepth);
    }

    public static void main(String[] args) {
        TreeDFSExamples solver = new TreeDFSExamples();

        // Build test tree:      3
        //                     /   \
        //                    9     20
        //                         /  \
        //                        15   7
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        System.out.println(solver.maxDepth(root)); // 3
    }
}
```

> **Explanation:**
>
> * We perform a classic DFS recursion.
> * Base case: an empty node has depth 0.
> * Otherwise, depth = `1 + max(depth(left), depth(right))`.

---

## 12. Graph Traversal (BFS / DFS)

Graph problems generalize tree BFS/DFS. In an adjacency‐list graph or matrix grid, you can use BFS (queue) to find shortest paths in unweighted graphs (e.g. “Number of Islands”), or DFS (recursion/stack) to explore connectivity, detect cycles, or enumerate paths.

### When to Use

* You have a grid or adjacency list, want to count connected components (“islands”), or find shortest unweighted path.
* You need to detect cycles in directed/undirected graphs.
* You want to perform a topological sort (requires DFS or Kahn’s algorithm).

---

### Example Problem:

**LeetCode 200 – Number of Islands**

> Given a 2D grid of `'1'` (land) and `'0'` (water), count the number of connected islands. Connectivity is horizontal/vertical only.

#### Logic Outline (DFS)

1. Iterate each cell `(r, c)` in the grid.
2. If `grid[r][c] == '1'`, we found an unvisited island → increment `count++`, then call `dfsMark(r, c)` to mark the entire island as visited (turn `'1'`→ `'0'`).
3. In `dfsMark(r, c)`:

   * If out of bounds or grid\[r]\[c] == '0', return.
   * Otherwise set `grid[r][c] = '0'` to mark visited, then recurse on its 4 neighbors.
4. Continue scanning. The final `count` is the number of islands.

#### Java Implementation

```java
public class GraphTraversalExamples {
    private int rows, cols;

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        rows = grid.length;
        cols = grid[0].length;

        int count = 0;
        // Scan each cell
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    dfsMark(grid, r, c);
                }
            }
        }
        return count;
    }

    private void dfsMark(char[][] grid, int r, int c) {
        // Bound check or water cell
        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] == '0') {
            return;
        }
        // Mark current as visited
        grid[r][c] = '0';

        // Recurse in 4 directions
        dfsMark(grid, r + 1, c);
        dfsMark(grid, r - 1, c);
        dfsMark(grid, r, c + 1);
        dfsMark(grid, r, c - 1);
    }

    public static void main(String[] args) {
        GraphTraversalExamples solver = new GraphTraversalExamples();
        char[][] grid = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println(solver.numIslands(grid)); // 3
    }
}
```

> **Explanation:**
>
> * We treat the grid as an undirected graph where each `'1'` is a node connected to its 4 neighbors that are also `'1'`.
> * Upon seeing `'1'`, we perform a **DFS flood‐fill** turning every connected `'1'` to `'0'`. That ensures each island is counted exactly once.

---

## 13. Topological Sort

Produces a linear ordering of vertices in a directed acyclic graph (DAG) such that for every directed edge `u → v`, node `u` comes before `v`. Implemented either via DFS (postorder) or Kahn’s algorithm (BFS with in‐degree counting). Common for course‐scheduling or build‐order problems.

### When to Use

* You have a DAG and want a valid ordering (e.g. finish all courses in “Course Schedule II”).
* You must detect if a cycle exists (if no valid topological order).
* Dependencies or precedence constraints.

---

### Example Problem:

**LeetCode 210 – Course Schedule II**

> There are `numCourses` labeled `0…numCourses-1`. Given prerequisites pairs `[ai, bi]` meaning “to take course `ai`, you must first take course `bi`,” return any valid ordering of courses or an empty array if it’s impossible.

#### Logic Outline (Kahn’s Algorithm – BFS)

1. Build adjacency list `graph` of size `numCourses`. Also build an array `inDegree[numCourses]` initially all zero.
2. For each pair `(ai, bi)`, add `ai` to `graph[bi]`, and increment `inDegree[ai]` by 1.
3. Initialize a queue with all courses `i` where `inDegree[i] == 0` (no prerequisites).
4. While queue not empty:

   * `u = queue.poll()`, add `u` to `orderList`.
   * For each neighbor `v` in `graph[u]`:

     * Decrement `inDegree[v]` by 1.
     * If `inDegree[v] == 0`, `queue.offer(v)`.
5. If `orderList.size() == numCourses`, return it; else return `new int[0]` (cycle detected).

#### Java Implementation

```java
import java.util.*;

public class TopologicalSortExamples {
    /**
     * LeetCode #210: Course Schedule II
     * Returns one possible order to take courses, or empty array if impossible.
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        // Initialize adjacency list
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        // Build graph and in-degree array
        for (int[] pre : prerequisites) {
            int ai = pre[0], bi = pre[1];
            graph.get(bi).add(ai);
            inDegree[ai]++;
        }

        // Queue for all nodes with inDegree = 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int[] order = new int[numCourses];
        int idx = 0;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            order[idx++] = u;

            for (int v : graph.get(u)) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.offer(v);
                }
            }
        }

        // If we added all courses to order, return it; else cycle exists
        if (idx == numCourses) {
            return order;
        }
        return new int[0];
    }

    public static void main(String[] args) {
        TopologicalSortExamples solver = new TopologicalSortExamples();
        int numCourses = 4;
        int[][] prereqs = { {1,0}, {2,0}, {3,1}, {3,2} };
        // Valid orders include [0,1,2,3] or [0,2,1,3], etc.
        System.out.println(Arrays.toString(solver.findOrder(numCourses, prereqs)));
    }
}
```

> **Explanation:**
>
> * We compute in‐degrees and enqueue all courses with 0 in‐degree.
> * Each time we pop a course `u`, we decrement in‐degree of its neighbors, enqueuing those that fall to 0.
> * If we manage to “schedule” all courses, we return the ordering; otherwise, there was a cycle.

---

## 14. Subsets & Combinatorics (BFS)

Generate all subsets (the power set) of a given set **iteratively** by starting from the empty subset and inserting each new number into existing subsets to form new ones.

### When to Use

* You need to generate **all possible subsets** (power set) of a set or array.
* You want to build combinations level by level, rather than with recursion.

---

### Example Problem:

**LeetCode 78 – Subsets**

> Given an integer array `nums` of **unique** elements, return all possible subsets (the power set).

#### Logic Outline (BFS/Iterative)

1. Start with a `List<List<Integer>> subsets = [[]]` (only the empty set).
2. For each number `num` in `nums`:

   * Let `size = subsets.size()`.
   * For `i` in `0…size-1`, take `List<Integer> curr = subsets.get(i)`.
   * Create a new subset `temp = new ArrayList<>(curr)`, `temp.add(num)`, then `subsets.add(temp)`.
3. At the end, `subsets` contains all combinations.

#### Java Implementation

```java
public class SubsetsExamples {
    /**
     * LeetCode #78: Subsets
     * Returns all subsets (power set) of the input array.
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        subsets.add(new ArrayList<>());  // start with empty set

        for (int num : nums) {
            int size = subsets.size();
            for (int i = 0; i < size; i++) {
                // For each existing subset, clone it and add current num
                List<Integer> curr = subsets.get(i);
                List<Integer> temp = new ArrayList<>(curr);
                temp.add(num);
                subsets.add(temp);
            }
        }
        return subsets;
    }

    public static void main(String[] args) {
        SubsetsExamples solver = new SubsetsExamples();
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = solver.subsets(nums);
        System.out.println(result);
        // Output: [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]
    }
}
```

> **Explanation:**
>
> * We start with only the empty subset.
> * Each time we see a new number `num`, we take all existing subsets (size = `s`) and append `num` to produce `s` new subsets.
> * The result grows from `1 → 2 → 4 → 8` subsets iteratively.

---

## 15. Backtracking (DFS for Combinations/Permutations)

Backtracking generalizes DFS: you build partial solutions incrementally and **“backtrack”** when a partial solution cannot lead to a valid complete solution, exploring all possibilities in the process.

### When to Use

* You must **enumerate all possible solutions** (e.g. combinations, permutations, subsets with certain sum).
* You want to **prune** the search tree whenever a partial solution violates constraints.

---

### Example Problem:

**LeetCode 39 – Combination Sum**

> Given an array of **unique** candidate numbers `candidates` and a target number `target`, return all unique combinations where the chosen numbers sum to `target`. The same number may be chosen unlimited times.

#### Logic Outline (Backtracking)

1. Sort `candidates` (optional, helps with pruning).
2. Call recursive helper `backtrack(startIndex, currentSum, pathList)`.
3. In `backtrack(i, currSum, path)`:

   * If `currSum == target`, add a copy of `path` to the answer.
   * If `currSum > target`, return (prune).
   * Otherwise, loop `j` from `i` to `candidates.length-1`:

     * Add `candidates[j]` to `path`, call `backtrack(j, currSum + candidates[j], path)` (we allow reuse, so `start` is still `j`), then remove last element from `path` (backtrack).

#### Java Implementation

```java
import java.util.*;

public class BacktrackingExamples {
    private List<List<Integer>> result = new ArrayList<>();
    private int[] candidates;
    private int target;

    /**
     * LeetCode #39: Combination Sum
     * Returns all unique combinations in candidates that sum to target.
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        this.candidates = candidates;
        this.target = target;
        Arrays.sort(candidates);  // for pruning

        backtrack(0, 0, new ArrayList<>());
        return result;
    }

    /**
     * backtrack(startIndex, currentSum, currentPath)
     */
    private void backtrack(int startIndex, int currSum, List<Integer> path) {
        if (currSum == target) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (currSum > target) {
            return;  // prune, no need to explore further
        }

        for (int i = startIndex; i < candidates.length; i++) {
            int pick = candidates[i];
            // Add pick to path
            path.add(pick);
            // Since we can reuse the same element, i (not i+1)
            backtrack(i, currSum + pick, path);
            // Backtrack: remove last pick
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        BacktrackingExamples solver = new BacktrackingExamples();
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        System.out.println(solver.combinationSum(candidates, target));
        // Output: [[2,2,3], [7]]
    }
}
```

> **Explanation:**
>
> * We keep a `path` list that stores the current combination.
> * At each step, we either **pick** `candidates[i]` (and stay at `i` to allow reuse) or move on to `i+1`.
> * If `currSum` exceeds `target`, we backtrack immediately (prune).

---

## 16. Modified Binary Search

Extend classic binary search to handle rotated sorted arrays, 2D matrices, or “search on answer‐space” problems (e.g. Koko Eating Bananas). The main idea is to carefully define the “monotonic condition” and adjust left/right pointers accordingly.

### When to Use

* You have a **sorted** (possibly rotated) array and must search in O(log n).
* You want to find a minimal feasible value (e.g. minimum days, capacity), so you do binary search on the answer.

---

### Example Problem:

**LeetCode 33 – Search in Rotated Sorted Array**

> Suppose an array `nums` sorted in ascending order is rotated at some pivot. Search for `target` in O(log n) time. Return index or `-1` if not found.

#### Logic Outline

1. Standard binary‐search with a twist: at each step, we know either the left half or the right half is still “sorted.”
2. Let `mid = (low + high) / 2`.
3. If `nums[mid] == target`, return `mid`.
4. Otherwise, check if the left half `[low…mid]` is sorted (i.e. `nums[low] ≤ nums[mid]`):

   * If so, check if `target` lies in that range (`nums[low] ≤ target < nums[mid]`):

     * If yes, `high = mid - 1`.
     * Else `low = mid + 1`.
5. If right half `[mid…high]` is sorted (i.e. `nums[mid] < nums[high]`):

   * If `target` lies in that range (`nums[mid] < target ≤ nums[high]`):

     * `low = mid + 1`.
     * Else `high = mid - 1`.
6. Repeat until found or `low > high`.

#### Java Implementation

```java
public class ModifiedBinarySearchExamples {
    /**
     * LeetCode #33: Search in Rotated Sorted Array
     * Returns the index of target if found, else -1.
     */
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // Check if left half is sorted
            if (nums[low] <= nums[mid]) {
                // If target lies in the sorted left half
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            // Otherwise, right half must be sorted
            else {
                // If target lies in the sorted right half
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ModifiedBinarySearchExamples solver = new ModifiedBinarySearchExamples();
        int[] nums = {4,5,6,7,0,1,2};
        System.out.println(solver.search(nums, 0)); // 4
        System.out.println(solver.search(nums, 3)); // -1
    }
}
```

> **Explanation:**
>
> * At each step, one half (`low…mid` or `mid…high`) is guaranteed sorted.
> * We test if `target` can lie in that sorted half; if yes, move `low`/`high` accordingly. Otherwise, search the other half.

---

## 17. Top K Elements (Heaps / Priority Queue)

When you need the Kth largest/smallest element or the K most frequent items, a **heap** (priority queue) is a natural fit. Use a **min‐heap of size K** to keep track of the top K elements as you scan the array (or a max‐heap for bottom K).

### When to Use

* You need the “Kth largest” or “K most frequent” from a large dataset in O(n log K) time.
* You need to maintain a running median of a data stream (two‐heap approach).

---

### Example Problem:

**LeetCode 215 – Kth Largest Element in an Array**

> Find the Kth largest (1-indexed) element in an unsorted array. (i.e. the element that would be at position `n-K` if you sorted ascending.)

#### Logic Outline

1. Maintain a **min‐heap** (`PriorityQueue<Integer>`) of size at most `K`.
2. As you iterate through each number `num` in `nums`:

   * Add `num` to the min‐heap.
   * If the heap’s size > `K`, poll (remove) the smallest element.
3. At the end, the heap’s root (poll or peek) is the Kth largest.

#### Java Implementation

```java
import java.util.*;

public class TopKHeapExamples {
    /**
     * LeetCode #215: Kth Largest Element in an Array
     * Returns the Kth largest element in the array.
     */
    public int findKthLargest(int[] nums, int k) {
        // Min-heap of size k
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            // If more than k elements, remove the smallest
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        // Root of min-heap is the Kth largest
        return minHeap.peek();
    }

    public static void main(String[] args) {
        TopKHeapExamples solver = new TopKHeapExamples();
        int[] nums = {3,2,1,5,6,4};
        int k = 2;
        System.out.println(solver.findKthLargest(nums, k)); // 5
    }
}
```

> **Explanation:**
>
> * We keep at most `k` elements in the min‐heap.
> * If the heap size exceeds `k`, we remove the smallest, ensuring that after processing all numbers, the heap contains exactly the top K largest, and the root is the Kth largest.

---

## 18. K-Way Merge (Heap / Multi-Way Merge)

When merging **K sorted lists** or arrays, a standard technique is to place the “head” of every list in a **min‐heap**. Repeatedly extract the smallest, add it to the merged output, and then insert the next element from the list from which you extracted.

### When to Use

* Merge K sorted arrays/lists into one sorted array.
* Find the smallest/largest range across K sorted lists.
* Used in external‐sorting or streaming merges.

---

### Example Problem:

**LeetCode 23 – Merge K Sorted Lists**

> Merge `K` sorted linked lists and return it as one sorted linked list.

#### Logic Outline

1. Create a `PriorityQueue<ListNode>` (min‐heap) that orders by `node.val`.
2. Insert the head of each non‐null list into the heap.
3. Initialize a dummy head `tail`. While the heap is not empty:

   * `node = heap.poll()` (smallest current head among all lists)
   * `tail.next = node`; `tail = node`.
   * If `node.next` exists, `heap.offer(node.next)`.
4. Return `dummy.next`.

#### Java Implementation

```java
import java.util.*;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}

public class KWayMergeExamples {
    /**
     * LeetCode #23: Merge K Sorted Lists
     * Merge K sorted linked lists into one sorted list.
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // Min-heap comparing ListNode by val
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.val, b.val)
        );

        // Step 1: Push head of each list into heap
        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        // Dummy head for result
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        // Step 2: Pop smallest, append to result, then push its next
        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            tail.next = node;
            tail = node;
            if (node.next != null) {
                minHeap.offer(node.next);
            }
        }
        return dummy.next;
    }

    // Helper to build list from array
    private static ListNode buildList(int[] arr) {
        ListNode dummy = new ListNode(0), tail = dummy;
        for (int x : arr) {
            tail.next = new ListNode(x);
            tail = tail.next;
        }
        return dummy.next;
    }

    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " → ");
            head = head.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        KWayMergeExamples solver = new KWayMergeExamples();
        ListNode[] lists = new ListNode[3];
        lists[0] = buildList(new int[]{1,4,5});
        lists[1] = buildList(new int[]{1,3,4});
        lists[2] = buildList(new int[]{2,6});
        ListNode merged = solver.mergeKLists(lists);
        printList(merged);
        // Expected: 1 → 1 → 2 → 3 → 4 → 4 → 5 → 6 → null
    }
}
```

> **Explanation:**
>
> * We push all list heads into a min‐heap sorted by node value.
> * Each extraction from the heap gives the next smallest node among all K lists.
> * After extracting `node`, we insert `node.next` (if exists) to keep the heap populated.
> * Merging is effectively O(N log K) where N = total number of nodes.

---

## 19. Bitwise XOR (Single Number Pattern)

Use properties of XOR (`a ^ a = 0`, `a ^ 0 = a`) to find a unique element when every other element occurs in pairs (or triplets, etc.) in linear time and O(1) extra space.

### When to Use

* You have an array where **every element except one (or two)** appears exactly twice (or thrice).
* You want to isolate the element(s) that don’t have a matching pair by XOR’ing all elements.

---

### Example Problem:

**LeetCode 136 – Single Number**

> Given a non‐empty array of integers where every element appears twice except for one, find that single one.

#### Logic Outline

1. Initialize `xorSum = 0`.
2. For each element `x` in `nums`, do `xorSum = xorSum ^ x`.
3. Since `a ^ a = 0`, all paired elements cancel out. The final `xorSum` is the single number.

#### Java Implementation

```java
public class BitwiseXorExamples {
    /**
     * LeetCode #136: Single Number
     * Return the element that appears only once.
     */
    public int singleNumber(int[] nums) {
        int xorSum = 0;
        for (int x : nums) {
            xorSum ^= x;  // XOR accumulates all elements
        }
        return xorSum;
    }

    public static void main(String[] args) {
        BitwiseXorExamples solver = new BitwiseXorExamples();
        int[] nums = {4,1,2,1,2};
        System.out.println(solver.singleNumber(nums)); // 4
    }
}
```

> **Explanation:**
>
> * Pairwise duplicates cancel out under XOR.
> * The final XOR of all elements is exactly the one that appeared once.

---

## 20. Greedy Algorithms

Greedy picks the best local choice at each step, hoping to arrive at a global optimum. You must prove (or recognize) that this local choice strategy indeed yields a correct overall answer.

### When to Use

* You have interval or scheduling problems (choose earliest finishing time).
* You need to maximize or minimize some metric with a locally optimal choice each step.
* Examples: Interval Scheduling, Jump Game, Assign Cookies, etc.

---

### Example Problem:

**LeetCode 55 – Jump Game**

> Given an array `nums` where `nums[i]` represents the maximum jump length from index `i`, determine if you can reach the last index.

#### Logic Outline (Greedy)

1. Maintain a variable `maxReach = 0` (the furthest index we can reach so far).
2. Iterate `i` from `0` to `n-1`:

   * If `i > maxReach`, that means we cannot even reach index `i` → return `false`.
   * Otherwise, update `maxReach = max(maxReach, i + nums[i])`.
   * If `maxReach >= n-1`, return `true` (we can reach or pass the end).
3. If loop finishes without `maxReach >= n-1`, return `false`.

#### Java Implementation

```java
public class GreedyExamples {
    /**
     * LeetCode #55: Jump Game
     * Returns true if you can reach the last index.
     */
    public boolean canJump(int[] nums) {
        int maxReach = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (i > maxReach) {
                // We cannot reach index i
                return false;
            }
            maxReach = Math.max(maxReach, i + nums[i]);
            if (maxReach >= n - 1) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        GreedyExamples solver = new GreedyExamples();
        System.out.println(solver.canJump(new int[]{2,3,1,1,4})); // true
        System.out.println(solver.canJump(new int[]{3,2,1,0,4})); // false
    }
}
```

> **Explanation:**
>
> * We keep track of how far we can reach from any seen index.
> * If at any point `i` exceeds `maxReach`, we’re stuck → cannot progress.
> * If `maxReach` reaches or surpasses `n-1`, we can reach the end.

---

## 21. Dynamic Programming (0/1 Knapsack Pattern)

Dynamic Programming breaks a problem into overlapping subproblems. A core DP pattern is the **0/1 Knapsack**: decide whether to include each item (value, weight) to maximize value under weight capacity. Many “subset‐sum” or partition problems reduce to a knapsack‐like DP.

### When to Use

* You have “**choose or skip**” decisions with overlapping subproblems.
* You want to optimize either a **count** or **max/min** under some constraint.
* Examples: Coin Change, Partition Equal Subset Sum, Longest Increasing Subsequence, etc.

---

### Example Problem:

**LeetCode 322 – Coin Change**

> Given `coins[]` of different denominations and a total `amount`, return the fewest number of coins that make up that amount. If it’s not possible, return `-1`.

#### Logic Outline (1D DP)

1. Let `dp[i]` = minimum coins required to get amount `i`. Initialize `dp[0] = 0`, and `dp[i] = amount+1` (or `∞`) for `i > 0`.
2. For `i` from `1` to `amount`:

   * For each coin `c` in `coins`:

     * If `c ≤ i`, update `dp[i] = min(dp[i], dp[i - c] + 1)`.
3. If `dp[amount]` is still `> amount` (infinity), return `-1`, else return `dp[amount]`.

#### Java Implementation

```java
import java.util.*;

public class DynamicProgrammingExamples {
    /**
     * LeetCode #322: Coin Change
     * Returns fewest number of coins needed to make up amount, or -1 if impossible.
     */
    public int coinChange(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;

        // Build up dp from 1 to amount
        for (int i = 1; i <= amount; i++) {
            for (int c : coins) {
                if (c <= i) {
                    dp[i] = Math.min(dp[i], dp[i - c] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        DynamicProgrammingExamples solver = new DynamicProgrammingExamples();
        int[] coins = {1, 2, 5};
        int amount = 11;
        System.out.println(solver.coinChange(coins, amount)); // 3 (5+5+1)
    }
}
```

> **Explanation:**
>
> * `dp[i]` = min coins to make amount `i`.
> * We iterate all amounts up to `amount`, and for each, try every coin, looking at `dp[i - c]` (the subproblem).
> * If `dp[amount]` remains > `amount`, it means it was never updated → impossible.

---

## 22. Trie (Prefix Tree)

A **Trie** (prefix tree) stores strings in a tree structure where each node represents a character. It’s optimal for prefix‐based searches, autocomplete, and dictionary word lookups.

### When to Use

* You have a large dictionary of strings and need fast prefix queries (`insert`, `search`, `startsWith`).
* You want to search multiple words in a grid (Word Search II) by pruning with common prefixes.

---

### Example Problem:

**LeetCode 208 – Implement Trie (Prefix Tree)**

> Implement the following methods of a trie:
>
> * `void insert(String word)`
> * `boolean search(String word)`
> * `boolean startsWith(String prefix)`

#### Logic Outline

1. Each `TrieNode` has:

   * An array (or map) of children pointers of size 26 for lowercase letters.
   * A boolean `isWord` to mark the end of a valid word.
2. **Insert**: Traverse each character in `word`:

   * If the child node for that character is null, create it.
   * Move to that child.
   * After last char, mark `isWord = true`.
3. **Search**: Traverse characters; if any child is null, return `false`. At end, return `node.isWord`.
4. **startsWith**: Same traversal; but at end, just return `true` if we never hit a null (regardless of `isWord`).

#### Java Implementation

```java
class TrieNode {
    // Each node has up to 26 children (for 'a' to 'z')
    TrieNode[] children = new TrieNode[26];
    boolean isWord = false;
}

public class TrieExamples {
    private TrieNode root;

    /** Initialize your data structure here. */
    public TrieExamples() {
        root = new TrieNode();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                node.children[idx] = new TrieNode();
            }
            node = node.children[idx];
        }
        node.isWord = true;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                return false;
            }
            node = node.children[idx];
        }
        return node.isWord;
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) {
                return false;
            }
            node = node.children[idx];
        }
        return true;  // we only care that the prefix path exists
    }

    public static void main(String[] args) {
        TrieExamples trie = new TrieExamples();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // true
        System.out.println(trie.search("app"));     // false
        System.out.println(trie.startsWith("app")); // true
        trie.insert("app");
        System.out.println(trie.search("app"));     // true
    }
}
```

> **Explanation:**
>
> * Each node’s `children[0…25]` correspond to next letters `'a'…'z'`.
> * `insert` walks down/creates nodes for each character, marking the end.
> * `search` walks down; if a child is missing, word doesn’t exist; otherwise check `isWord` at the end.
> * `startsWith` just verifies the prefix path exists.

---

## 23. Union-Find (Disjoint Set)

Union‐Find (Disjoint Set Union – DSU) maintains a collection of disjoint sets and supports two operations:

* **find(x)**: returns the “representative” (root) of the set containing `x`.
* **union(x, y)**: merges the sets containing `x` and `y`.

With **path compression** and **union by rank/size**, both ops become nearly O(α(N)) ≈ O(1). Useful for connectivity/cycle detection in undirected graphs.

### When to Use

* You need to dynamically connect nodes and query whether two nodes are in the same component.
* You want to count connected components in an undirected graph.
* Examples: “Number of Provinces,” “Accounts Merge,” Kruskal’s MST.

---

### Example Problem:

**LeetCode 547 – Number of Provinces**

> There are `n` cities (labeled 0…n-1) and an adjacency matrix `isConnected[i][j] == 1` if city `i` and city `j` are directly connected. A **province** is a group of directly or indirectly connected cities. Return the number of provinces.

#### Logic Outline (Union‐Find)

1. Initialize `parent[i] = i` for `i = 0…n-1` (each city is its own root). Also optionally `rank[i]=1`.
2. For every pair `(i, j)` where `isConnected[i][j] == 1`, call `union(i, j)`.
3. After all unions, count how many indices `i` satisfy `find(i) == i` (the root is itself). That count is the number of disjoint sets (provinces).

#### Java Implementation

```java
public class UnionFindExamples {
    private int[] parent;
    private int[] rank;

    public UnionFindExamples(int n) {
        parent = new int[n];
        rank = new int[n];
        // Initialize each node’s parent to itself, rank=1
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    // Find with path compression
    private int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // Union by rank
    private void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return;

        // Attach smaller rank under larger rank
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }

    /**
     * LeetCode #547: Number of Provinces
     * Returns number of disconnected groups (provinces).
     */
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        UnionFindExamples uf = new UnionFindExamples(n);

        // For each i<j, if connected, union them
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        // Count distinct roots
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (uf.find(i) == i) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        UnionFindExamples solver = new UnionFindExamples(0);
        int[][] isConnected = {
            {1,1,0},
            {1,1,0},
            {0,0,1}
        };
        System.out.println(solver.findCircleNum(isConnected)); // 2
    }
}
```

> **Explanation:**
>
> * We treat each city as a node in an undirected graph.
> * For every `isConnected[i][j] == 1`, we call `union(i, j)`.
> * Path compression (`find`) flattens the tree for future queries.
> * At the end, the number of distinct roots = number of provinces.

---

### ⏺ Summary of Patterns + Java Snippets

| Pattern                        | Key Idea                                                        | Representative LeetCode | Java Snippet Class             |
| ------------------------------ | --------------------------------------------------------------- | ----------------------- | ------------------------------ |
| **1. Sliding Window**          | Maintain a window `[l…r]` and expand/shrink based on condition  | #3, #424, #209          | `SlidingWindowExamples`        |
| **2. Two Pointers**            | Use two indices from ends or same array to avoid nested loops   | #15, #11, #167          | `TwoPointersExamples`          |
| **3. Fast/Slow Pointers**      | Detect cycles or find middle in O(n)                            | #141, #142, #876        | `FastSlowPointersExamples`     |
| **4. Merge Intervals**         | Sort intervals, then merge overlapping ranges                   | #56, #57, #435          | `MergeIntervalsExamples`       |
| **5. Cyclic Sort**             | Place each `x` at index `x` by swapping for missing/duplicates  | #268, #448, #287        | `CyclicSortExamples`           |
| **6. In‐Place Reverse**        | Reverse linked list by pointer manipulation                     | #206, #92, #25          | `InPlaceReverseExamples`       |
| **7. Stack**                   | Use a LIFO stack for parsing, evaluation, or “min-stack”        | #20, #155, #150         | `StackExamples`                |
| **8. Monotonic Stack**         | Maintain stack in increasing/decreasing order for range queries | #496, #739, #42         | `MonotonicStackExamples`       |
| **9. Hash Map**                | O(1) lookup for indices, counts, complements                    | #1, #217, #76           | `HashMapExamples`              |
| **10. Tree BFS**               | Level‐order traversal with a queue                              | #102, #107, #103        | `TreeBFSExamples`              |
| **11. Tree DFS**               | Recursively (or via stack) traverse tree for path sums, etc.    | #104, #112, #129        | `TreeDFSExamples`              |
| **12. Graph BFS/DFS**          | Explore connectivity, count islands, or find shortest paths     | #200, #133, #207        | `GraphTraversalExamples`       |
| **13. Topological Sort**       | Kahn’s (BFS) or DFS postorder for DAG ordering                  | #207, #210, #269        | `TopologicalSortExamples`      |
| **14. Subsets (BFS)**          | Iteratively build power set by adding one element at a time     | #78, #90, #46           | `SubsetsExamples`              |
| **15. Backtracking**           | DFS with path + prune to enumerate combinations/permutations    | #39, #46, #17           | `BacktrackingExamples`         |
| **16. Modified Binary Search** | Search in rotated array or binary‐search on answer              | #33, #153, #81          | `ModifiedBinarySearchExamples` |
| **17. Top K Elements**         | Keep a min‐heap of size K for Kth largest/frequent              | #215, #347, #703        | `TopKHeapExamples`             |
| **18. K-Way Merge**            | Min‐heap over heads of K sorted lists                           | #23, #378, #632         | `KWayMergeExamples`            |
| **19. Bitwise XOR**            | Use XOR to cancel paired elements, isolate unique ones          | #136, #137, #260        | `BitwiseXorExamples`           |
| **20. Greedy**                 | Make locally optimal choices (e.g. interval scheduling, jump)   | #55, #435, #452         | `GreedyExamples`               |
| **21. Dynamic Programming**    | Break into overlapping subproblems (Knapsack pattern)           | #322, #70, #516         | `DynamicProgrammingExamples`   |
| **22. Trie**                   | Prefix tree for fast prefix lookups/autocomplete                | #208, #212, #211        | `TrieExamples`                 |
| **23. Union-Find**             | DSU for connectivity / cycle detection                          | #323, #547, #721        | `UnionFindExamples`            |

---

### How to Use This Guide

1. **Study Each Pattern**:

   * Read the “When to Use” and “Logic Outline” closely.
   * Trace the Java code with a small example on paper to see pointer/index changes.

2. **Practice**:

   * Solve the **representative LeetCode problems** for each pattern until you can implement them from scratch.
   * Once comfortable with the template, pick a new problem that uses the same pattern and adapt your code.

3. **Pattern Recognition**:

   * During a timed mock interview or contest, the first step is to “recognize” which pattern this problem resembles.
   * Ask yourself: “Is it a contiguous subarray/substring question? → Sliding Window.
     “Is it on a sorted array that looks like pair sums? → Two Pointers.”
     “Is it a tree with levels being relevant? → Tree BFS.”
     And so on.

4. **Memorize the Template**:

   * Each pattern’s Java code follows a **template**. Once you memorize the skeleton, you can quickly fill in the details (e.g., window conditions, adjacency references, cube checks, etc.).

5. **Company Frequency**:

   * **Sliding Window**, **Two Pointers**, **Hash Map**, and **Tree/Graph traversals** are by far the most common in FAANG+ interviews.
   * **Greedy** questions (e.g. scheduling, jump game) are also very high yield.
   * **Complex DP** (e.g. 2D DP, bitmask DP) appears more at higher‐level rounds (e.g. Google, Microsoft top‐of‐resume) but less so in initial phone screens.
   * **Trie**, **Union‐Find**, and **Topological Sort** surface in mid‐level rounds at companies like Google, Amazon.
   * **Cyclic Sort** (missing/duplicates) is a quick O(n) trick that often appears in phone screens.

By systematically studying these **23 patterns**—each with a Java template you’ve seen above—you’ll be equipped to solve nearly any DSA problem encountered in product‐based company interviews. Good luck with your preparation!
