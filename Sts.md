
### 1. Binary Palindrome
```java
public class BinaryPalindrome {
    public static boolean isBinaryPalindrome(int n) {
        int reversed = 0, original = n;
        while (n > 0) {
            reversed = (reversed << 1) | (n & 1);
            n >>= 1;
        }
        return original == reversed;
    }

    public static void main(String[] args) {
        int n = 9;
        System.out.println(isBinaryPalindrome(n));
    }
}
```

### 2. Booth's Algorithm (for string matching)
```java
public class BoothAlgorithm {
    public static int leastRotation(String s) {
        s = s + s;
        int[] f = new int[s.length()];
        int k = 0;
        for (int j = 1; j < s.length(); j++) {
            char sj = s.charAt(j);
            int i = f[j - k - 1];
            while (i != -1 && sj != s.charAt(k + i + 1)) {
                if (sj < s.charAt(k + i + 1)) {
                    k = j - i - 1;
                }
                i = f[i];
            }
            if (sj != s.charAt(k + i + 1)) {
                if (sj < s.charAt(k)) k = j;
                f[j - k] = -1;
            } else {
                f[j - k] = i + 1;
            }
        }
        return k;
    }

    public static void main(String[] args) {
        String s = "bbaaccaadd";
        System.out.println(leastRotation(s));
    }
}
```

### 3. Euclid's Algorithm (for GCD)
```java
public class EuclidAlgorithm {
    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        int a = 56, b = 98;
        System.out.println(gcd(a, b));
    }
}
```

### 4. Karatsuba Algorithm (for multiplication)
```java
public class KaratsubaAlgorithm {
    public static long karatsuba(long x, long y) {
        int n = Math.max(Long.toString(x).length(), Long.toString(y).length());
        if (n == 1) return x * y;
        n = (n / 2) + (n % 2);

        long a = x / (long) Math.pow(10, n);
        long b = x % (long) Math.pow(10, n);
        long c = y / (long) Math.pow(10, n);
        long d = y % (long) Math.pow(10, n);

        long ac = karatsuba(a, c);
        long bd = karatsuba(b, d);
        long adPlusBc = karatsuba(a + b, c + d) - ac - bd;

        return ac * (long) Math.pow(10, 2 * n) + (adPlusBc * (long) Math.pow(10, n)) + bd;
    }

    public static void main(String[] args) {
        long x = 1234, y = 5678;
        System.out.println(karatsuba(x, y));
    }
}
```

### 5. Longest Sequence of 1 After Flipping a Bit
```java
public class LongestSequenceAfterFlip {
    public static int longestSequence(int n) {
        if (~n == 0) return Integer.BYTES * 8;
        int maxLen = 0, currentLen = 0, prevLen = 0;
        while (n != 0) {
            if ((n & 1) == 1) currentLen++;
            else {
                prevLen = (n & 2) == 0 ? 0 : currentLen;
                currentLen = 0;
            }
            maxLen = Math.max(maxLen, prevLen + currentLen + 1);
            n >>= 1;
        }
        return maxLen;
    }

    public static void main(String[] args) {
        int n = 1775;
        System.out.println(longestSequence(n));
    }
}
```

### 6. Swap Two Nibbles in a Byte
```java
public class SwapNibbles {
    public static int swapNibbles(int x) {
        return ((x & 0x0F) << 4) | ((x & 0xF0) >> 4);
    }

    public static void main(String[] args) {
        int x = 100;
        System.out.println(swapNibbles(x));
    }
}
```

### 7. Block Swap Algorithm (for array rotation)
```java
public class BlockSwapAlgorithm {
    public static void rotate(int[] arr, int d) {
        int n = arr.length;
        if (d == 0 || d == n) return;
        if (d > n) d %= n;
        swap(arr, 0, d, n - d);
    }

    private static void swap(int[] arr, int start, int d, int n) {
        if (n == d) {
            for (int i = 0; i < n; i++) {
                int temp = arr[start + i];
                arr[start + i] = arr[start + n + i];
                arr[start + n + i] = temp;
            }
            return;
        }
        if (n < d) {
            for (int i = 0; i < n; i++) {
                int temp = arr[start + i];
                arr[start + i] = arr[start + d + i];
                arr[start + d + i] = temp;
            }
            swap(arr, start, d - n, n);
        } else {
            for (int i = 0; i < d; i++) {
                int temp = arr[start + i];
                arr[start + i] = arr[start + n - d + i];
                arr[start + n - d + i] = temp;
            }
            swap(arr, start + d, d, n - d);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        int d = 2;
        rotate(arr, d);
        for (int num : arr) System.out.print(num + " ");
    }
}
```

### 8. Maximum Product Subarray
```java
public class MaxProductSubarray {
    public static int maxProduct(int[] nums) {
        int maxProduct = nums[0], minProduct = nums[0], result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temp = maxProduct;
            maxProduct = Math.max(nums[i], Math.max(maxProduct * nums[i], minProduct * nums[i]));
            minProduct = Math.min(nums[i], Math.min(temp * nums[i], minProduct * nums[i]));
            result = Math.max(result, maxProduct);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, -2, 4};
        System.out.println(maxProduct(nums));
    }
}
```

### 9. Maximum Sum of Hour Glass in Matrix
```java
public class MaxHourglassSum {
    public static int maxHourglassSum(int[][] mat) {
        int maxSum = Integer.MIN_VALUE;
        for (int i = 0; i <= mat.length - 3; i++) {
            for (int j = 0; j <= mat[0].length - 3; j++) {
                int sum = mat[i][j] + mat[i][j + 1] + mat[i][j + 2]
                        + mat[i + 1][j + 1]
                        + mat[i + 2][j] + mat[i + 2][j + 1] + mat[i + 2][j + 2];
                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[][] mat = {{1, 1, 1, 0, 0, 0}, {0, 1, 0, 0, 0, 0}, {1, 1, 1, 0, 0, 0},
                {0, 0, 2, 4, 4, 0}, {0, 0, 0, 2, 0, 0}, {0, 0, 1, 2, 4, 0}};
        System.out.println(maxHourglassSum(mat));
    }
}
```

### 10. Max Equilibrium Sum
```java
public class MaxEquilibriumSum {
    public static int maxEquilibriumSum(int[] arr) {
        int totalSum = 0, leftSum = 0, maxEquilibriumSum = 0;
        for (int num : arr) totalSum += num;
        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i];
            if (leftSum == totalSum) maxEquilibriumSum = Math.max(maxEquilibriumSum, leftSum

);
            leftSum += arr[i];
        }
        return maxEquilibriumSum;
    }

    public static void main(String[] args) {
        int[] arr = {-7, 1, 5, 2, -4, 3, 0};
        System.out.println(maxEquilibriumSum(arr));
    }
}
```

### 11. Leaders in Array
```java
import java.util.ArrayList;

public class LeadersInArray {
    public static ArrayList<Integer> findLeaders(int[] arr) {
        ArrayList<Integer> leaders = new ArrayList<>();
        int maxFromRight = arr[arr.length - 1];
        leaders.add(maxFromRight);
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] > maxFromRight) {
                leaders.add(arr[i]);
                maxFromRight = arr[i];
            }
        }
        return leaders;
    }

    public static void main(String[] args) {
        int[] arr = {16, 17, 4, 3, 5, 2};
        System.out.println(findLeaders(arr));
    }
}
```

### 12. Majority Element (Moore's Voting Algorithm)
```java
public class MajorityElement {
    public static int findMajorityElement(int[] arr) {
        int count = 0, candidate = -1;
        for (int num : arr) {
            if (count == 0) candidate = num;
            count += (num == candidate) ? 1 : -1;
        }
        count = 0;
        for (int num : arr) if (num == candidate) count++;
        return count > arr.length / 2 ? candidate : -1;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 1, 1, 2, 2};
        System.out.println(findMajorityElement(arr));
    }
}
```

### 13. Lexicographically First Palindromic String
```java
import java.util.Arrays;

public class LexicographicPalindrome {
    public static String firstPalindromic(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;
        char oddChar = '\0';
        StringBuilder half = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                if (oddChar != '\0') return "Not possible";
                oddChar = (char) (i + 'a');
            }
            char[] part = new char[freq[i] / 2];
            Arrays.fill(part, (char) (i + 'a'));
            half.append(part);
        }
        String halfStr = half.toString();
        return halfStr + (oddChar == '\0' ? "" : oddChar) + half.reverse().toString();
    }

    public static void main(String[] args) {
        String s = "racecar";
        System.out.println(firstPalindromic(s));
    }
}
```
