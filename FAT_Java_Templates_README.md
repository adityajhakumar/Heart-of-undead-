# Java Coding Templates – FAT Preparation

This repository contains **must-know Java code templates** designed to help you solve most of the FAT (Final Assessment Test) questions using **basic concepts only**. These templates cover input/output, sorting, searching, dynamic programming, frequency analysis, and more.

---

## Table of Contents

1. [Basic Input/Output Template](#1-basic-inputoutput-template)
2. [Sorting an Array](#2-sorting-an-array)
3. [Two Pointer Technique](#3-two-pointer-technique)
4. [Reverse an Array](#4-reverse-an-array)
5. [Frequency Count Template](#5-frequency-count-template)
6. [Prefix Sum Template](#6-prefix-sum-template)
7. [DP Table – LCS](#7-dp-table--lcs)
8. [Subarray Brute Force](#8-subarray-brute-force)
9. [Stack Usage Template](#9-stack-usage-template)

---

## 1. Basic Input/Output Template
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();
        for(int i = 0; i < n; i++) System.out.print(arr[i] + " ");
    }
}
```
**Used In**: All questions as foundational structure

---

## 2. Sorting an Array
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for(int i = 0; i < n; i++) arr[i] = sc.nextInt();
        Arrays.sort(arr);
        for(int val : arr) System.out.print(val + " ");
    }
}
```
**Used In**: L-2, L-10, L-30

---

## 3. Two Pointer Technique
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int left = 0, right = arr.length - 1;
        while(left < right) {
            // Your logic here
            left++;
            right--;
        }
    }
}
```
**Used In**: L-3, L-31

---

## 4. Reverse an Array
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        for(int i = arr.length - 1; i >= 0; i--) {
            System.out.print(arr[i] + " ");
        }
    }
}
```
**Used In**: L-3, L-31

---

## 5. Frequency Count Template
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 3, 3, 3};
        HashMap<Integer, Integer> freq = new HashMap<>();
        for(int x : arr) freq.put(x, freq.getOrDefault(x, 0) + 1);
        for(int key : freq.keySet())
            System.out.println(key + " -> " + freq.get(key));
    }
}
```
**Used In**: L-25, L-26, L-27

---

## 6. Prefix Sum Template
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int[] prefix = new int[arr.length];
        prefix[0] = arr[0];
        for(int i = 1; i < arr.length; i++) prefix[i] = prefix[i-1] + arr[i];
        System.out.println(Arrays.toString(prefix));
    }
}
```
**Used In**: L-32, L-33

---

## 7. DP Table – LCS
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String s1 = "abc", s2 = "ac";
        int n = s1.length(), m = s2.length();
        int[][] dp = new int[n+1][m+1];
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= m; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1))
                    dp[i][j] = 1 + dp[i-1][j-1];
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
        System.out.println(dp[n][m]);
    }
}
```
**Used In**: L-29

---

## 8. Subarray Brute Force
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        for(int i = 0; i < arr.length; i++) {
            for(int j = i; j < arr.length; j++) {
                for(int k = i; k <= j; k++) {
                    System.out.print(arr[k] + " ");
                }
                System.out.println();
            }
        }
    }
}
```
**Used In**: L-8, L-11, L-33

---

## 9. Stack Usage Template
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(10);
        stack.push(20);
        System.out.println(stack.peek()); // 20
        stack.pop();
        System.out.println(stack.peek()); // 10
    }
}
```
**Used In**: L-5, L-6, L-7, L-8, L-12
