---

```java
import java.util.*;

public class ArrayProblems {
    static Scanner sc = new Scanner(System.in);
    
    // Function to create array
    static int[] createArray() {
        System.out.print("Enter size of array: ");
        int n = sc.nextInt();
        int arr[] = new int[n];
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        return arr;
    }

    // 1. Find smallest element
    static void smallest(int[] arr) {
        int min = arr[0];
        for (int val : arr) if (val < min) min = val;
        System.out.println("Smallest element: " + min);
    }

    // 2. Find largest element
    static void largest(int[] arr) {
        int max = arr[0];
        for (int val : arr) if (val > max) max = val;
        System.out.println("Largest element: " + max);
    }

    // 3. Second smallest and second largest
    static void secondSmallestLargest(int[] arr) {
        Arrays.sort(arr);
        int n = arr.length;
        System.out.println("Second smallest: " + arr[1]);
        System.out.println("Second largest: " + arr[n - 2]);
    }

    // 4. Reverse array
    static void reverse(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[n - i - 1];
            arr[n - i - 1] = temp;
        }
        System.out.println("Reversed array: " + Arrays.toString(arr));
    }

    // 5. Count frequency
    static void frequency(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : arr) map.put(x, map.getOrDefault(x, 0) + 1);
        System.out.println("Element Frequencies: " + map);
    }

    // 6. Increasing-Decreasing arrangement
    static void incDec(int[] arr) {
        Arrays.sort(arr);
        System.out.println("Increasing order: " + Arrays.toString(arr));
        System.out.print("Decreasing order: [");
        for (int i = arr.length - 1; i >= 0; i--) {
            System.out.print(arr[i]);
            if (i != 0) System.out.print(", ");
        }
        System.out.println("]");
    }

    // 7. Sum of elements
    static void sum(int[] arr) {
        int s = 0;
        for (int x : arr) s += x;
        System.out.println("Sum of elements: " + s);
    }

    // 8. Rotate array by K (right rotation)
    static void rotateByK(int[] arr) {
        int n = arr.length;
        System.out.print("Enter K: ");
        int k = sc.nextInt();
        k = k % n;
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) temp[(i + k) % n] = arr[i];
        System.out.println("Array after rotation: " + Arrays.toString(temp));
    }

    // 9. Average
    static void average(int[] arr) {
        int sum = 0;
        for (int x : arr) sum += x;
        System.out.println("Average: " + (double) sum / arr.length);
    }

    // 10. Median
    static void median(int[] arr) {
        Arrays.sort(arr);
        double median;
        int n = arr.length;
        if (n % 2 == 0)
            median = (arr[n/2 - 1] + arr[n/2]) / 2.0;
        else
            median = arr[n/2];
        System.out.println("Median: " + median);
    }

    // 11. Remove duplicates from sorted
    static void removeDupSorted(int[] arr) {
        int n = arr.length;
        int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] != arr[i + 1]) arr[j++] = arr[i];
        }
        arr[j++] = arr[n - 1];
        System.out.print("Array after removing duplicates: ");
        for (int i = 0; i < j; i++) System.out.print(arr[i] + " ");
        System.out.println();
    }

    // 12. Remove duplicates from unsorted
    static void removeDupUnsorted(int[] arr) {
        LinkedHashSet<Integer> set = new LinkedHashSet<>();
        for (int x : arr) set.add(x);
        System.out.println("Array after removing duplicates: " + set);
    }

    // 13. Add element in array
    static void addElement(int[] arr) {
        System.out.print("Enter element to add: ");
        int x = sc.nextInt();
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = x;
        System.out.println("New array: " + Arrays.toString(newArr));
    }

    // 14. Repeating elements
    static void repeating(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : arr) map.put(x, map.getOrDefault(x, 0) + 1);
        System.out.print("Repeating elements: ");
        for (int x : map.keySet()) if (map.get(x) > 1) System.out.print(x + " ");
        System.out.println();
    }

    // 15. Non-repeating elements
    static void nonRepeating(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : arr) map.put(x, map.getOrDefault(x, 0) + 1);
        System.out.print("Non-repeating elements: ");
        for (int x : map.keySet()) if (map.get(x) == 1) System.out.print(x + " ");
        System.out.println();
    }

    // 16. Symmetric pairs
    static void symmetricPairs() {
        System.out.print("Enter number of pairs: ");
        int n = sc.nextInt();
        int[][] arr = new int[n][2];
        System.out.println("Enter pairs:");
        for (int i = 0; i < n; i++) {
            arr[i][0] = sc.nextInt();
            arr[i][1] = sc.nextInt();
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        System.out.println("Symmetric pairs:");
        for (int i = 0; i < n; i++) {
            int first = arr[i][0], second = arr[i][1];
            if (map.containsKey(second) && map.get(second) == first)
                System.out.println("(" + first + "," + second + ")");
            else
                map.put(first, second);
        }
    }

    // 17. Maximum product subarray
    static void maxProductSubarray(int[] arr) {
        int maxProd = arr[0], minProd = arr[0], result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < 0) {
                int temp = maxProd;
                maxProd = minProd;
                minProd = temp;
            }
            maxProd = Math.max(arr[i], arr[i] * maxProd);
            minProd = Math.min(arr[i], arr[i] * minProd);
            result = Math.max(result, maxProd);
        }
        System.out.println("Maximum product subarray: " + result);
    }

    // 18. Replace each element by its rank
    static void rankReplace(int[] arr) {
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        HashMap<Integer, Integer> rank = new HashMap<>();
        int r = 1;
        for (int x : sorted) if (!rank.containsKey(x)) rank.put(x, r++);
        for (int i = 0; i < arr.length; i++) arr[i] = rank.get(arr[i]);
        System.out.println("Array with ranks: " + Arrays.toString(arr));
    }

    // 19. Sort by frequency
    static void sortByFrequency(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : arr) map.put(x, map.getOrDefault(x, 0) + 1);
        Arrays.sort(arr, 0, arr.length);
        Integer[] boxed = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        Arrays.sort(boxed, (a, b) -> {
            int freqCompare = map.get(b) - map.get(a);
            return freqCompare == 0 ? a - b : freqCompare;
        });
        System.out.println("Sorted by frequency: " + Arrays.toString(boxed));
    }

    // 20. Check subset
    static void subsetCheck(int[] arr1, int[] arr2) {
        HashSet<Integer> set = new HashSet<>();
        for (int x : arr1) set.add(x);
        boolean subset = true;
        for (int x : arr2) if (!set.contains(x)) subset = false;
        System.out.println(subset ? "Yes, second array is subset." : "No, it is not a subset.");
    }

    public static void main(String[] args) {
        System.out.println("===== ARRAY PROBLEMS MENU =====");
        int[] arr = createArray();

        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("1.Smallest  2.Largest  3.Second Smallest/Largest");
            System.out.println("4.Reverse  5.Frequency  6.Inc/Dec order  7.Sum  8.Rotate");
            System.out.println("9.Average  10.Median  11.Remove Dup Sorted  12.Remove Dup Unsorted");
            System.out.println("13.Add Element  14.Repeating  15.Non-Repeating  16.Symmetric Pairs");
            System.out.println("17.Max Product Subarray  18.Rank Replace  19.Sort by Freq  20.Subset");
            System.out.println("0.Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> smallest(arr);
                case 2 -> largest(arr);
                case 3 -> secondSmallestLargest(arr.clone());
                case 4 -> reverse(arr.clone());
                case 5 -> frequency(arr);
                case 6 -> incDec(arr.clone());
                case 7 -> sum(arr);
                case 8 -> rotateByK(arr.clone());
                case 9 -> average(arr);
                case 10 -> median(arr.clone());
                case 11 -> removeDupSorted(arr.clone());
                case 12 -> removeDupUnsorted(arr);
                case 13 -> addElement(arr);
                case 14 -> repeating(arr);
                case 15 -> nonRepeating(arr);
                case 16 -> symmetricPairs();
                case 17 -> maxProductSubarray(arr);
                case 18 -> rankReplace(arr.clone());
                case 19 -> sortByFrequency(arr.clone());
                case 20 -> {
                    System.out.println("Enter 2nd array:");
                    int[] arr2 = createArray();
                    subsetCheck(arr, arr2);
                }
                case 0 -> { System.out.println("Exiting..."); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
```

---



```java
import java.util.*;

public class NumberProblems {
    static Scanner sc = new Scanner(System.in);

    // 1. Check Palindrome
    static boolean isPalindrome(int n) {
        int temp = n, rev = 0;
        while (n > 0) {
            rev = rev * 10 + n % 10;
            n /= 10;
        }
        return temp == rev;
    }

    // 2. Palindrome in range
    static void palindromeRange() {
        System.out.print("Enter range (start end): ");
        int a = sc.nextInt(), b = sc.nextInt();
        System.out.print("Palindrome numbers: ");
        for (int i = a; i <= b; i++)
            if (isPalindrome(i)) System.out.print(i + " ");
        System.out.println();
    }

    // 3. Prime check
    static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) return false;
        return true;
    }

    // 4. Prime range
    static void primeRange() {
        System.out.print("Enter range (start end): ");
        int a = sc.nextInt(), b = sc.nextInt();
        System.out.print("Prime numbers: ");
        for (int i = a; i <= b; i++)
            if (isPrime(i)) System.out.print(i + " ");
        System.out.println();
    }

    // 5. Armstrong number
    static boolean isArmstrong(int n) {
        int temp = n, sum = 0, digits = String.valueOf(n).length();
        while (n > 0) {
            int d = n % 10;
            sum += Math.pow(d, digits);
            n /= 10;
        }
        return sum == temp;
    }

    // 6. Perfect number
    static boolean isPerfect(int n) {
        int sum = 1;
        for (int i = 2; i <= n / 2; i++)
            if (n % i == 0) sum += i;
        return sum == n && n != 1;
    }

    // 7. Even or odd
    static void evenOdd(int n) {
        System.out.println(n + " is " + (n % 2 == 0 ? "Even" : "Odd"));
    }

    // 8. Positive or negative
    static void posNeg(int n) {
        if (n > 0) System.out.println("Positive");
        else if (n < 0) System.out.println("Negative");
        else System.out.println("Zero");
    }

    // 9. Sum of N natural
    static void sumNatural() {
        System.out.print("Enter N: ");
        int n = sc.nextInt();
        System.out.println("Sum = " + n * (n + 1) / 2);
    }

    // 10. Sum of AP series
    static void sumAP() {
        System.out.print("Enter a, d, n: ");
        int a = sc.nextInt(), d = sc.nextInt(), n = sc.nextInt();
        double sum = n / 2.0 * (2 * a + (n - 1) * d);
        System.out.println("Sum of AP series: " + sum);
    }

    // 11. Sum of GP series
    static void sumGP() {
        System.out.print("Enter a, r, n: ");
        double a = sc.nextDouble(), r = sc.nextDouble();
        int n = sc.nextInt();
        double sum = a * (1 - Math.pow(r, n)) / (1 - r);
        System.out.println("Sum of GP series: " + sum);
    }

    // 12. Greatest of two
    static void greatestTwo() {
        System.out.print("Enter two numbers: ");
        int a = sc.nextInt(), b = sc.nextInt();
        System.out.println("Greatest: " + (a > b ? a : b));
    }

    // 13. Greatest of three
    static void greatestThree() {
        System.out.print("Enter three numbers: ");
        int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
        System.out.println("Greatest: " + Math.max(a, Math.max(b, c)));
    }

    // 14. Leap year
    static void leapYear() {
        System.out.print("Enter year: ");
        int y = sc.nextInt();
        boolean leap = (y % 4 == 0 && y % 100 != 0) || (y % 400 == 0);
        System.out.println(leap ? "Leap Year" : "Not Leap Year");
    }

    // 15. Reverse digits
    static void reverseDigits() {
        System.out.print("Enter number: ");
        int n = sc.nextInt(), rev = 0;
        while (n > 0) {
            rev = rev * 10 + n % 10;
            n /= 10;
        }
        System.out.println("Reversed: " + rev);
    }

    // 16. Max and Min digit
    static void maxMinDigit() {
        System.out.print("Enter number: ");
        int n = sc.nextInt(), max = 0, min = 9;
        while (n > 0) {
            int d = n % 10;
            if (d > max) max = d;
            if (d < min) min = d;
            n /= 10;
        }
        System.out.println("Max digit: " + max + ", Min digit: " + min);
    }

    // 17. Fibonacci
    static void fibonacci() {
        System.out.print("Enter N: ");
        int n = sc.nextInt();
        int a = 0, b = 1;
        System.out.print("Fibonacci: ");
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            int c = a + b;
            a = b;
            b = c;
        }
        System.out.println();
    }

    // 18. Factorial
    static void factorial() {
        System.out.print("Enter number: ");
        int n = sc.nextInt();
        long fact = 1;
        for (int i = 1; i <= n; i++) fact *= i;
        System.out.println("Factorial: " + fact);
    }

    // 19. Power
    static void power() {
        System.out.print("Enter base and exponent: ");
        double a = sc.nextDouble(), b = sc.nextDouble();
        System.out.println("Result: " + Math.pow(a, b));
    }

    // 20. Factors
    static void factors() {
        System.out.print("Enter number: ");
        int n = sc.nextInt();
        System.out.print("Factors: ");
        for (int i = 1; i <= n; i++)
            if (n % i == 0) System.out.print(i + " ");
        System.out.println();
    }

    // 21. Prime factors
    static void primeFactors() {
        System.out.print("Enter number: ");
        int n = sc.nextInt();
        System.out.print("Prime factors: ");
        for (int i = 2; i <= n; i++)
            while (n % i == 0) {
                System.out.print(i + " ");
                n /= i;
            }
        System.out.println();
    }

    // 22. Strong number
    static void strongNumber() {
        System.out.print("Enter number: ");
        int n = sc.nextInt(), temp = n, sum = 0;
        while (n > 0) {
            int d = n % 10;
            int fact = 1;
            for (int i = 1; i <= d; i++) fact *= i;
            sum += fact;
            n /= 10;
        }
        System.out.println(temp == sum ? "Strong Number" : "Not Strong Number");
    }

    // 23. Automorphic number
    static void automorphic() {
        System.out.print("Enter number: ");
        int n = sc.nextInt();
        int sq = n * n;
        String num = String.valueOf(n);
        String sqr = String.valueOf(sq);
        System.out.println(sqr.endsWith(num) ? "Automorphic" : "Not Automorphic");
    }

    // 24. GCD
    static void gcd() {
        System.out.print("Enter two numbers: ");
        int a = sc.nextInt(), b = sc.nextInt();
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        System.out.println("GCD: " + a);
    }

    // 25. LCM
    static void lcm() {
        System.out.print("Enter two numbers: ");
        int a = sc.nextInt(), b = sc.nextInt();
        int gcd = a, temp = b;
        while (temp != 0) {
            int t = temp;
            temp = gcd % temp;
            gcd = t;
        }
        System.out.println("LCM: " + (a * b) / gcd);
    }

    // 26. Harshad number
    static void harshad() {
        System.out.print("Enter number: ");
        int n = sc.nextInt(), sum = 0, temp = n;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        System.out.println(temp % sum == 0 ? "Harshad" : "Not Harshad");
    }

    // 27. Abundant number
    static void abundant() {
        System.out.print("Enter number: ");
        int n = sc.nextInt(), sum = 1;
        for (int i = 2; i <= n / 2; i++)
            if (n % i == 0) sum += i;
        System.out.println(sum > n ? "Abundant" : "Not Abundant");
    }

    // 28. Sum of digits
    static void sumDigits() {
        System.out.print("Enter number: ");
        int n = sc.nextInt(), sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        System.out.println("Sum of digits: " + sum);
    }

    // 29. Sum in range
    static void sumRange() {
        System.out.print("Enter start and end: ");
        int a = sc.nextInt(), b = sc.nextInt(), sum = 0;
        for (int i = a; i <= b; i++) sum += i;
        System.out.println("Sum = " + sum);
    }

    // 30. Permutations (nPr)
    static void permutations() {
        System.out.print("Enter N and R: ");
        int n = sc.nextInt(), r = sc.nextInt();
        int factN = 1, factNR = 1;
        for (int i = 1; i <= n; i++) factN *= i;
        for (int i = 1; i <= n - r; i++) factNR *= i;
        System.out.println("Permutations (nPr): " + factN / factNR);
    }

    // 31. Add two fractions
    static void addFractions() {
        System.out.print("Enter numerator1, denominator1, numerator2, denominator2: ");
        int n1 = sc.nextInt(), d1 = sc.nextInt(), n2 = sc.nextInt(), d2 = sc.nextInt();
        int num = n1 * d2 + n2 * d1;
        int den = d1 * d2;
        int gcd = num, temp = den;
        while (temp != 0) {
            int t = temp;
            temp = gcd % temp;
            gcd = t;
        }
        num /= gcd; den /= gcd;
        System.out.println("Sum = " + num + "/" + den);
    }

    // 32. Replace 0 with 1
    static void replaceZero() {
        System.out.print("Enter number: ");
        int n = sc.nextInt();
        String res = String.valueOf(n).replace('0', '1');
        System.out.println("After replacing 0s: " + res);
    }

    // 33. Sum of two primes
    static void sumOfTwoPrimes() {
        System.out.print("Enter number: ");
        int n = sc.nextInt();
        boolean found = false;
        for (int i = 2; i <= n / 2; i++) {
            if (isPrime(i) && isPrime(n - i)) {
                System.out.println(n + " = " + i + " + " + (n - i));
                found = true;
            }
        }
        if (!found) System.out.println("Cannot be expressed as sum of two primes.");
    }

    // 34. Area of circle
    static void areaCircle() {
        System.out.print("Enter radius: ");
        double r = sc.nextDouble();
        System.out.println("Area = " + Math.PI * r * r);
    }

    // 35. Roots of quadratic equation
    static void quadraticRoots() {
        System.out.print("Enter a, b, c: ");
        double a = sc.nextDouble(), b = sc.nextDouble(), c = sc.nextDouble();
        double d = b * b - 4 * a * c;
        if (d > 0) {
            double r1 = (-b + Math.sqrt(d)) / (2 * a);
            double r2 = (-b - Math.sqrt(d)) / (2 * a);
            System.out.println("Roots are real and distinct: " + r1 + " , " + r2);
        } else if (d == 0) {
            double r = -b / (2 * a);
            System.out.println("Roots are equal: " + r);
        } else {
            double real = -b / (2 * a);
            double imag = Math.sqrt(-d) / (2 * a);
            System.out.println("Complex roots: " + real + " ± " + imag + "i");
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== NUMBER PROBLEMS MENU =====");
            System.out.println("""
1.Palindrome Check
2.Palindrome in Range
3.Prime Check
4.Prime Range
5.Armstrong
6.Perfect
7.Even/Odd
8.Positive/Negative
9.Sum of N Natural
10.Sum of AP
11.Sum of GP
12.Greatest of 2
13.Greatest of 3
14.Leap Year
15.Reverse Digits
16.Max/Min Digit
17.Fibonacci
18.Factorial
19.Power
20.Factors
21.Prime Factors
22.Strong Number
23.Automorphic
24.GCD
25.LCM
26.Harshad
27.Abundant
28.Sum of Digits
29.Sum in Range
30.Permutations (nPr)
31.Add Two Fractions
32.Replace 0 with 1
33.Sum of Two Primes
34.Area of Circle
35.Quadratic Roots
0.Exit
""");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            if (ch == 0) break;

            System.out.print("Enter number if needed: ");
            int n = (ch == 1 || ch == 3 || ch == 5 || ch == 6 || ch == 7 || ch == 8 ||
                     ch == 22 || ch == 23 || ch == 26 || ch == 27 || ch == 28 || ch == 32 || ch == 33) ? sc.nextInt() : 0;

            switch (ch) {
                case 1 -> System.out.println(isPalindrome(n) ? "Palindrome" : "Not Palindrome");
                case 2 -> palindromeRange();
                case 3 -> System.out.println(isPrime(n) ? "Prime" : "Not Prime");
                case 4 -> primeRange();
                case 5 -> System.out.println(isArmstrong(n) ? "Armstrong" : "Not Armstrong");
                case 6 -> System.out.println(isPerfect(n) ? "Perfect" : "Not Perfect");
                case 7 -> evenOdd(n);
                case 8 -> posNeg(n);
                case 9 -> sumNatural();
                case 10 -> sumAP();
                case 11 -> sumGP();
                case 12 -> greatestTwo();
                case 13 -> greatestThree();
                case 14 -> leapYear();
                case 15 -> reverseDigits();
                case 16 -> maxMinDigit();
                case 17 -> fibonacci();
                case 18 -> factorial();
                case 19 -> power();
                case 20 -> factors();
                case 21 -> primeFactors();
                case 22 -> strongNumber();
                case 23 -> automorphic();
                case 24 -> gcd();
                case 25 -> lcm();
                case 26 -> harshad();
                case 27 -> abundant();
                case 28 -> sumDigits();
                case 29 -> sumRange();
                case 30 -> permutations();
                case 31 -> addFractions();
                case 32 -> replaceZero();
                case 33 -> sumOfTwoPrimes();
                case 34 -> areaCircle();
                case 35 -> quadraticRoots();
                default -> System.out.println("Invalid choice!");
            }
        }
        System.out.println("Exiting...");
    }
}
```

---


---

```java
import java.util.*;

public class MixedProblems {
    static Scanner sc = new Scanner(System.in);

    // ==================== NUMBER SYSTEM ====================

    // 1. Binary to Decimal
    static void binaryToDecimal() {
        System.out.print("Enter Binary: ");
        String bin = sc.next();
        int dec = Integer.parseInt(bin, 2);
        System.out.println("Decimal = " + dec);
    }

    // 2. Binary to Octal
    static void binaryToOctal() {
        System.out.print("Enter Binary: ");
        String bin = sc.next();
        int dec = Integer.parseInt(bin, 2);
        String oct = Integer.toOctalString(dec);
        System.out.println("Octal = " + oct);
    }

    // 3. Decimal to Binary
    static void decimalToBinary() {
        System.out.print("Enter Decimal: ");
        int dec = sc.nextInt();
        String bin = Integer.toBinaryString(dec);
        System.out.println("Binary = " + bin);
    }

    // 4. Decimal to Octal
    static void decimalToOctal() {
        System.out.print("Enter Decimal: ");
        int dec = sc.nextInt();
        System.out.println("Octal = " + Integer.toOctalString(dec));
    }

    // 5. Octal to Binary
    static void octalToBinary() {
        System.out.print("Enter Octal: ");
        String oct = sc.next();
        int dec = Integer.parseInt(oct, 8);
        System.out.println("Binary = " + Integer.toBinaryString(dec));
    }

    // 6. Octal to Decimal
    static void octalToDecimal() {
        System.out.print("Enter Octal: ");
        String oct = sc.next();
        int dec = Integer.parseInt(oct, 8);
        System.out.println("Decimal = " + dec);
    }

    // 7. Number to Words
    static void numberToWords() {
        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        String[] words = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
        System.out.print("In Words: ");
        for (char c : String.valueOf(n).toCharArray()) {
            System.out.print(words[c - '0'] + " ");
        }
        System.out.println();
    }

    // ==================== SORTING ====================

    static void printArray(int[] arr) {
        System.out.println("Sorted Array: " + Arrays.toString(arr));
    }

    // 1. Bubble Sort
    static void bubbleSort() {
        int[] arr = inputArray();
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }
        printArray(arr);
    }

    // 2. Selection Sort
    static void selectionSort() {
        int[] arr = inputArray();
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j] < arr[min]) min = j;
            int temp = arr[i]; arr[i] = arr[min]; arr[min] = temp;
        }
        printArray(arr);
    }

    // 3. Insertion Sort
    static void insertionSort() {
        int[] arr = inputArray();
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        printArray(arr);
    }

    // 4. Quick Sort
    static void quickSort() {
        int[] arr = inputArray();
        quickSortHelper(arr, 0, arr.length - 1);
        printArray(arr);
    }

    static void quickSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortHelper(arr, low, pi - 1);
            quickSortHelper(arr, pi + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++)
            if (arr[j] < pivot) {
                i++;
                int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
            }
        int t = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = t;
        return i + 1;
    }

    // 5. Merge Sort
    static void mergeSort() {
        int[] arr = inputArray();
        mergeSortHelper(arr, 0, arr.length - 1);
        printArray(arr);
    }

    static void mergeSortHelper(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSortHelper(arr, l, m);
            mergeSortHelper(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int[] L = new int[n1], R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    static int[] inputArray() {
        System.out.print("Enter array size: ");
        int n = sc.nextInt();
        int[] arr = new int[n];
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        return arr;
    }

    // ==================== STRING PROBLEMS ====================

    // 1. Check palindrome
    static void stringPalindrome() {
        System.out.print("Enter String: ");
        String s = sc.nextLine();
        String rev = new StringBuilder(s).reverse().toString();
        System.out.println(s.equalsIgnoreCase(rev) ? "Palindrome" : "Not Palindrome");
    }

    // 2. Count vowels, consonants, spaces
    static void countVowels() {
        System.out.print("Enter String: ");
        String s = sc.nextLine().toLowerCase();
        int v = 0, c = 0, sp = 0;
        for (char ch : s.toCharArray()) {
            if ("aeiou".indexOf(ch) != -1) v++;
            else if (ch == ' ') sp++;
            else if (Character.isLetter(ch)) c++;
        }
        System.out.println("Vowels=" + v + ", Consonants=" + c + ", Spaces=" + sp);
    }

    // 3. ASCII of character
    static void asciiValue() {
        System.out.print("Enter character: ");
        char ch = sc.next().charAt(0);
        System.out.println("ASCII value: " + (int) ch);
    }

    // 4. Remove vowels
    static void removeVowels() {
        System.out.print("Enter String: ");
        String s = sc.nextLine();
        System.out.println("After removing vowels: " + s.replaceAll("(?i)[aeiou]", ""));
    }

    // 5. Remove spaces
    static void removeSpaces() {
        System.out.print("Enter String: ");
        String s = sc.nextLine();
        System.out.println("Without spaces: " + s.replaceAll(" ", ""));
    }

    // 6. Reverse a string
    static void reverseString() {
        System.out.print("Enter String: ");
        String s = sc.nextLine();
        System.out.println("Reversed: " + new StringBuilder(s).reverse());
    }

    // 7. Frequency of characters
    static void freqOfChar() {
        System.out.print("Enter String: ");
        String s = sc.nextLine().toLowerCase();
        HashMap<Character, Integer> map = new HashMap<>();
        for (char ch : s.toCharArray())
            if (Character.isLetter(ch))
                map.put(ch, map.getOrDefault(ch, 0) + 1);
        System.out.println("Character Frequencies: " + map);
    }

    // 8. Anagram check
    static void anagram() {
        System.out.print("Enter first string: ");
        String s1 = sc.nextLine().replaceAll("\\s", "").toLowerCase();
        System.out.print("Enter second string: ");
        String s2 = sc.nextLine().replaceAll("\\s", "").toLowerCase();
        char[] a1 = s1.toCharArray(), a2 = s2.toCharArray();
        Arrays.sort(a1); Arrays.sort(a2);
        System.out.println(Arrays.equals(a1, a2) ? "Anagram" : "Not Anagram");
    }

    // 9. Change case
    static void changeCase() {
        System.out.print("Enter String: ");
        String s = sc.nextLine();
        StringBuilder sb = new StringBuilder();
        for (char ch : s.toCharArray()) {
            if (Character.isUpperCase(ch)) sb.append(Character.toLowerCase(ch));
            else sb.append(Character.toUpperCase(ch));
        }
        System.out.println("After case change: " + sb);
    }

    // 10. Reverse words
    static void reverseWords() {
        System.out.print("Enter Sentence: ");
        String[] words = sc.nextLine().split(" ");
        Collections.reverse(Arrays.asList(words));
        System.out.println("Reversed words: " + String.join(" ", words));
    }

    // ========== MAIN MENU ==========
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("""
1.Binary to Decimal
2.Binary to Octal
3.Decimal to Binary
4.Decimal to Octal
5.Octal to Binary
6.Octal to Decimal
7.Number to Words
8.Bubble Sort
9.Selection Sort
10.Insertion Sort
11.Quick Sort
12.Merge Sort
13.String Palindrome
14.Count Vowels/Consonants
15.ASCII Value
16.Remove Vowels
17.Remove Spaces
18.Reverse String
19.Frequency of Characters
20.Anagram Check
21.Change Case
22.Reverse Words
0.Exit
""");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (ch) {
                case 1 -> binaryToDecimal();
                case 2 -> binaryToOctal();
                case 3 -> decimalToBinary();
                case 4 -> decimalToOctal();
                case 5 -> octalToBinary();
                case 6 -> octalToDecimal();
                case 7 -> numberToWords();
                case 8 -> bubbleSort();
                case 9 -> selectionSort();
                case 10 -> insertionSort();
                case 11 -> quickSort();
                case 12 -> mergeSort();
                case 13 -> stringPalindrome();
                case 14 -> countVowels();
                case 15 -> asciiValue();
                case 16 -> removeVowels();
                case 17 -> removeSpaces();
                case 18 -> reverseString();
                case 19 -> freqOfChar();
                case 20 -> anagram();
                case 21 -> changeCase();
                case 22 -> reverseWords();
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
```

---




You *can* use `sc.nextInt()` in a simple loop instead of making a separate function like `inputArray()`.

The `inputArray()` method I used earlier was just for **cleaner reuse** — because many sorting programs (Bubble, Selection, Insertion, etc.) all need the same array input format.

Let’s break it down 👇

---

### 🧠 Method 1: Using `inputArray()` (Reusable)

```java
static int[] inputArray() {
    System.out.print("Enter size of array: ");
    int n = sc.nextInt();
    int[] arr = new int[n];
    System.out.println("Enter " + n + " elements:");
    for (int i = 0; i < n; i++) {
        arr[i] = sc.nextInt(); // here we’re already using sc.nextInt
    }
    return arr;
}
```

✅ Advantage:

* You just call `int[] arr = inputArray();` anywhere (sorting, sum, etc.)
* Keeps main code cleaner.
* Reusable for multiple problems.

---

### 🧠 Method 2: Directly taking input using `sc.nextInt()` inside main or function

Here’s the same bubble sort without `inputArray()` 👇

```java
import java.util.*;

public class SimpleArrayInput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter size of array: ");
        int n = sc.nextInt();
        
        int[] arr = new int[n];
        System.out.println("Enter " + n + " elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();   // directly taking input here
        }

        // Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        System.out.println("Sorted Array: " + Arrays.toString(arr));
    }
}
```


✅ **YES, you can — absolutely allowed**
In almost all placement or online coding platforms (like TCS NQT, Hackerrank, Infosys InfyTQ, etc.), **built-in sorting methods** like:

```java
Arrays.sort(arr);
```

or

```java
Collections.sort(list);
```

are **completely valid and accepted**.

---

### 💡 BUT there’s an important “understanding vs implementation” difference:

| Scenario                                                        | Can you use `Arrays.sort()`? | Explanation                                                           |
| --------------------------------------------------------------- | ---------------------------- | --------------------------------------------------------------------- |
| **TCS NQT / Infosys online coding**                             | ✅ Yes                        | They just test your logic + output. You can use built-in functions.   |
| **University lab exams (Data Structures / Algorithms)**         | ⚠️ Usually No                | They want you to *implement* Bubble, Selection, Merge, etc. manually. |
| **Technical interview (when they say “Implement Bubble Sort”)** | ❌ No                         | You must show you know the sorting logic, not just use a library.     |
| **Any real-world coding round / project**                       | ✅ Yes                        | Using built-ins is *preferred* because it’s optimized and clean.      |

---

### ✅ Example (Using `Arrays.sort()` in a coding test)

```java
import java.util.*;

public class SortArrayExample {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter size of array: ");
        int n = sc.nextInt();
        
        int[] arr = new int[n];
        System.out.println("Enter elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        Arrays.sort(arr); // 🔥 simplest, fastest, accepted in TCS

        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}
```

✅ Output:

```
Enter size of array: 5
Enter elements:
4 2 9 1 7
Sorted array: [1, 2, 4, 7, 9]
```

---

### ⚙️ Note:

* `Arrays.sort(arr)` → works for **primitive arrays** (int[], double[], etc.)
* `Arrays.sort(arr, Collections.reverseOrder())` → works for **objects** (Integer[], String[], etc.)

Example for descending order:

```java
Integer[] arr = {4, 2, 9, 1, 7};
Arrays.sort(arr, Collections.reverseOrder());
System.out.println(Arrays.toString(arr)); // [9, 7, 4, 2, 1]
```

---

