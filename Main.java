// 1. Loop Detection in a Linked List
import java.util.*;

class LoopDetection {
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public static boolean detectLoop(Node head) {
        HashSet<Node> visited = new HashSet<>();
        Node current = head;
        while (current != null) {
            if (visited.contains(current)) return true;
            visited.add(current);
            current = current.next;
        }
        return false;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = head; // creates a loop

        System.out.println("Loop Detected: " + detectLoop(head));
    }
}

// 2. Sort a Bitonic Doubly Linked List
class SortBitonicDLL {
    static class Node {
        int data;
        Node next, prev;

        Node(int data) {
            this.data = data;
            this.next = this.prev = null;
        }
    }

    public static Node merge(Node first, Node second) {
        if (first == null) return second;
        if (second == null) return first;
        
        if (first.data < second.data) {
            first.next = merge(first.next, second);
            first.next.prev = first;
            first.prev = null;
            return first;
        } else {
            second.next = merge(first, second.next);
            second.next.prev = second;
            second.prev = null;
            return second;
        }
    }

    public static Node split(Node head) {
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node temp = slow.next;
        slow.next = null;
        return temp;
    }

    public static Node sort(Node head) {
        if (head == null || head.next == null) return head;
        Node second = split(head);
        head = sort(head);
        second = sort(second);
        return merge(head, second);
    }

    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(10);
        head.next = new Node(20);
        head.next.prev = head;
        head.next.next = new Node(5);
        head.next.next.prev = head.next;
        head.next.next.next = new Node(30);
        head.next.next.next.prev = head.next.next;

        System.out.println("Original List:");
        printList(head);

        head = sort(head);

        System.out.println("Sorted List:");
        printList(head);
    }
}

// 3. Segregate Even & Odd Nodes in a Linked List
class SegregateEvenOdd {
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public static Node segregate(Node head) {
        Node evenStart = null, evenEnd = null, oddStart = null, oddEnd = null;
        Node current = head;

        while (current != null) {
            if (current.data % 2 == 0) {
                if (evenStart == null) {
                    evenStart = current;
                    evenEnd = evenStart;
                } else {
                    evenEnd.next = current;
                    evenEnd = evenEnd.next;
                }
            } else {
                if (oddStart == null) {
                    oddStart = current;
                    oddEnd = oddStart;
                } else {
                    oddEnd.next = current;
                    oddEnd = oddEnd.next;
                }
            }
            current = current.next;
        }

        if (evenStart == null || oddStart == null) return head;
        
        evenEnd.next = oddStart;
        oddEnd.next = null;
        return evenStart;
    }

    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(17);
        head.next = new Node(15);
        head.next.next = new Node(8);
        head.next.next.next = new Node(12);
        head.next.next.next.next = new Node(10);
        head.next.next.next.next.next = new Node(5);

        System.out.println("Original List:");
        printList(head);

        head = segregate(head);

        System.out.println("After Segregation:");
        printList(head);
    }
}

// 4. Merge Sort for Doubly Linked List
class MergeSortDLL {
    static class Node {
        int data;
        Node next, prev;

        Node(int data) {
            this.data = data;
            this.next = this.prev = null;
        }
    }

    public static Node split(Node head) {
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node second = slow.next;
        slow.next = null;
        if (second != null) second.prev = null;
        return second;
    }

    public static Node merge(Node first, Node second) {
        if (first == null) return second;
        if (second == null) return first;

        if (first.data < second.data) {
            first.next = merge(first.next, second);
            if (first.next != null) first.next.prev = first;
            first.prev = null;
            return first;
        } else {
            second.next = merge(first, second.next);
            if (second.next != null) second.next.prev = second;
            second.prev = null;
            return second;
        }
    }

    public static Node mergeSort(Node head) {
        if (head == null || head.next == null) return head;

        Node second = split(head);
        head = mergeSort(head);
        second = mergeSort(second);

        return merge(head, second);
    }

    public static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(4);
        head.next = new Node(3);
        head.next.prev = head;
        head.next.next = new Node(2);
        head.next.next.prev = head.next;
        head.next.next.next = new Node(1);
        head.next.next.next.prev = head.next.next;

        System.out.println("Original List:");
        printList(head);

        head = mergeSort(head);

        System.out.println("Sorted List:");
        printList(head);
    }
}

// 5. Minimum Stack
class MinimumStack {
    static class MinStack {
        Stack<Integer> stack;
        Stack<Integer> minStack;

        MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        void push(int x) {
            stack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
        }

        int pop() {
            if (stack.isEmpty()) return -1;
            int popped = stack.pop();
            if (popped == minStack.peek()) {
                minStack.pop();
            }
            return popped;
        }

        int getMin() {
            if (minStack.isEmpty()) return -1;
            return minStack.peek();
        }
    }

    public static void main(String[] args) {
        MinStack stack = new MinStack();
        stack.push(10);
        stack.push(20);
        stack.push(5);
        System.out.println("Minimum: " + stack.getMin());
        stack.pop();
        System.out.println("Minimum: " + stack.getMin());
    }
}

// 6. The Celebrity Problem
class CelebrityProblem {
    public static int findCelebrity(int[][] matrix, int n) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            stack.push(i);
        }

        while (stack.size() > 1) {
            int a = stack.pop();
            int b = stack.pop();

            if (matrix[a][b] == 1) {
                stack.push(b);
            } else {
                stack.push(a);
            }
        }

        int candidate = stack.pop();

        for (int i = 0; i < n; i++) {
            if (i != candidate && (matrix[candidate][i] == 1 || matrix[i][candidate] == 0)) {
                return -1;
            }
        }

        return candidate;
    }

    public static void main(String[] args) {
        int[][] matrix = {
            {0, 1, 1},
            {0, 0, 1},
            {0, 0, 0}
        };

        System.out.println("Celebrity is: " + findCelebrity(matrix, 3));
    }
}

// 7. Iterative Tower of Hanoi
class IterativeTowerOfHanoi {
    static class Move {
        int disk;
        char from, to;

        Move(int disk, char from, char to) {
            this.disk = disk;
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "Move disk " + disk + " from " + from + " to " + to;
        }
    }

    public static void towerOfHanoi(int n, char from, char to, char aux) {
        Stack<Move> stack = new Stack<>();
        stack.push(new Move(n, from, to));

        while (!stack.isEmpty()) {
            Move move = stack.pop();
            int disk = move.disk;
            char source = move.from;
            char destination = move.to;
            char auxiliary = (char) ('A' + 'B' + 'C' - source - destination);

            if (disk == 1) {
                System.out.println(move);
            } else {
                stack.push(new Move(disk - 1, auxiliary, destination));
                stack.push(new Move(1, source, destination));
                stack.push(new Move(disk - 1, source, auxiliary));
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        towerOfHanoi(n, 'A', 'C', 'B');
    }
}

// 8. Stock Span Problem
class StockSpan {
    public static int[] calculateSpan(int[] prices) {
        int n = prices.length;
        int[] span = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
                stack.pop();
            }

            span[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
            stack.push(i);
        }

        return span;
    }

    public static void main(String[] args) {
        int[] prices = {100, 80, 60, 70, 60, 75, 85};
        int[] span = calculateSpan(prices);

        System.out.println("Stock Spans: " + Arrays.toString(span));
    }
}

// 9. Priority Queue using Doubly Linked List
class PriorityQueueDLL {
    static class Node {
        int data;
        Node next, prev;

        Node(int data) {
            this.data = data;
            this.next = this.prev = null;
        }
    }

    static class PriorityQueue {
        Node head;

        void insert(int data) {
            Node newNode = new Node(data);
            if (head == null || head.data > data) {
                newNode.next = head;
                if (head != null) head.prev = newNode;
                head = newNode;
                return;
            }

            Node current = head;
            while (current.next != null && current.next.data <= data) {
                current = current.next;
            }

            newNode.next = current.next;
            if (current.next != null) current.next.prev = newNode;
            current.next = newNode;
            newNode.prev = current;
        }

        int pop() {
            if (head == null) return -1;
            int data = head.data;
            head = head.next;
            if (head != null) head.prev = null;
            return data;
        }

        boolean isEmpty() {
            return head == null;
        }
    }

    public static void main(String[] args) {
        PriorityQueue pq = new PriorityQueue();
        pq.insert(10);
        pq.insert(5);
        pq.insert(20);

        while (!pq.isEmpty()) {
            System.out.println(pq.pop());
        }
    }
}

// 10. Sort Without Extra Space
class SortWithoutExtraSpace {
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            while (arr[i] != i + 1) {
                int temp = arr[arr[i] - 1];
                arr[arr[i] - 1] = arr[i];
                arr[i] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {4, 3, 2, 1};
        sort(arr);
        System.out.println("Sorted Array: " + Arrays.toString(arr));
    }
}

// 11. Max Sliding Window
class MaxSlidingWindow {
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            if (!deque.isEmpty() && deque.peek() < i - k + 1) deque.poll();

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.pollLast();

            deque.offer(i);

            if (i >= k - 1) result[i - k + 1] = nums[deque.peek()];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] result = maxSlidingWindow(nums, 3);

        System.out.println("Max Sliding Window: " + Arrays.toString(result));
    }
}

// 12. Stack Permutations
class StackPermutations {
    public static boolean isPermutation(int[] input, int[] output) {
        Stack<Integer> stack = new Stack<>();
        int j = 0;

        for (int i = 0; i < input.length; i++) {
            stack.push(input[i]);

            while (!stack.isEmpty() && stack.peek() == output[j]) {
                stack.pop();
                j++;
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        int[] input = {1, 2, 3};
        int[] output = {3, 2, 1};

        System.out.println("Is Permutation: " + isPermutation(input, output));
    }
}
