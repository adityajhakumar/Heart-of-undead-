import java.util.*;

public class z_sts {
    public static void Simple_sieve() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the limit");
        int n = sc.nextInt();
        boolean[] arr = new boolean[n + 1];
        sc.close();

        for (int i = 0; i <= n; i++) {
            arr[i] = true;
        }

        for (int i = 2; i*i <= n ; i++) {
            if (arr[i]) {
            for (int j = i*i; j <= n; j += i) {
                    arr[j] = false;
            }
        }
        }

        for (int i=2;i<=n;i++){
            if(arr[i]==true){
                System.out.println(i);
            }
        }
    }

    public static void segmented_sieve(int l,int h) {
        
        boolean[] arr = new boolean[h + 1];

        for (int i = 0; i <= h; i++) {
            arr[i] = true;
        }

        for (int i=2;i*i<=h;i++){
            int x=(l/i)*i;
            if (x<l){
                x+=i;
            }
            for (int j=x+x;j<=h;j+=i){
                arr[j]=false;
            }
        }

        for (int i=l;i<=h;i++){
            if(arr[i]==true){
                System.out.println(i);
            }
        }
    }

    public static void toggle() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter n : ");
        int n=sc.nextInt();
        sc.close();
        boolean[] sw=new boolean[n+1];
        for(int i=1;i<=n;i++){
            for (int j=i;j<=n;j+=i){
                if (sw[j]==true){
                    sw[j]=false;
                }
                else{
                    sw[j]=true;
                }

            }
        }
        int c=0;
        int o=0;
        for (int i=1;i<=n;i++){
            if(sw[i]==true){
                o++;
            }
            else{
                c++;
            }
            System.out.println(sw[i]);
        }
        System.out.println("Open : "+o+" Close : "+c);

    }

    public static void Euler() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Read n : ");
        int n=sc.nextInt();
        sc.close();
        
        int count=0;

        for (int i=1;i<=n;i++){
            if (gcd(i,n)==1){
                count+=i;
            }
        }

        System.out.print("Total sum of co-prime numbers are "+count);
    }
    
    public static void remainder() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Read n : ");
        int n=sc.nextInt();
        int[] nums=new int[n];
        int[] rems=new int[n];
        System.out.println("Enter nums : ");
        for (int i=0;i<n;i++){
            nums[i]=sc.nextInt();
        }
        System.out.println("Enter rems : ");
        for (int i=0;i<n;i++){
            rems[i]=sc.nextInt();
        }
        int ans=1;

        while (true){
            int i;
            for (i=1;i<n;i++){
                if (ans%nums[i]!=rems[i]){
                    break;
                }
            }
            if (i==n){
                System.out.print(ans);
                break;
            }
            ans++;
        }

    }

    public static void strobo() {
        Scanner sc=new Scanner(System.in);
        System.out.print("Read n : ");
        String num=sc.next();
        sc.close();
        Map<Character,Character> map = new HashMap<Character,Character>();
        map.put('6','9');
        map.put('9','6');
        map.put('0','0');
        map.put('1','1');
        map.put('8','8');
        int l=0;
        int r=num.length()-1;
        while (l <= r){
            if (!map.containsKey(num.charAt(l))){
                System.out.println("No");
                return;
            }
            if (map.get(num.charAt(l)) !=  num.charAt(r)){
                System.out.println("No");
                return;
            }
            l++;
            r--;
        }
        System.out.println("Yes");
    }

    public static List<String> numdef(int n, int length) {
        if (n==0) return Collections.singletonList("");
        if (n==1) return  Arrays.asList("0", "1", "8");
        
        List<String> middles = numdef(n-2,length);
        List<String> result = new ArrayList<>();

        for (String middle : middles){
            if (n!=length) result.add('0'+middle+'0');
            result.add('8'+middle+'8');
            result.add('1'+middle+'1');
            result.add('9'+middle+'6');
            result.add('6'+middle+'9');
        }
        return result;

        

    }

    // Cat-2
    public static int gcd(int a,int b) {
        if (b==0){
            return a;
        }
        else{
            return gcd(b,a%b);
        }
    }


    public static void leader(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the limit");
        int n = sc.nextInt();
        int[] arr=new int[n+1];
        for  (int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        int[] result = new int[n+1];
        int max = Integer.MIN_VALUE;
        int k=0;
        for (int i=n-1;i>=0;i--){
            if (max<arr[i]){
                max=arr[i];
                result[k]=arr[i];
                k+=1;
            }
        }
        for (int i=k-1;i>=0;i--){
            System.out.print(result[i]+" ");
        }
    }

    public static void majority_element() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i=0;i<n;i++){
            arr[i]= sc.nextInt();
        }
        
        int el=Integer.MIN_VALUE;
        int count=0;
        for (int i=0;i<n;i++){
            if (count==0){
                el=arr[i];
                count+=1;
            }
            else if (arr[i]==el){
                count++;
            }
            else
                count--;
        }
        System.out.print(el);
        
    }

    public static void binary_palindrome() {
        Scanner sc=new Scanner(System.in);
        int num = sc.nextInt();
        String binary = Integer.toBinaryString(num);
        int l=0;
        int h=binary.length()-1;
        while (l<h){
            if (binary.charAt(l)!=binary.charAt(h)){
                System.out.print("no");
                return ;
            }
            l++;
            h--;
        }
        System.out.print("yes");
    }

    public static void hour_glass() {
        Scanner sc = new Scanner(System.in);
        List<int[]> rows = new ArrayList<>();


        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                break;
            }
            String[] values = line.trim().split(" ");
            int[] row = new int[values.length];
            for (int j = 0; j < values.length; j++) {
                row[j] = Integer.parseInt(values[j]);
            }
            rows.add(row);
        }


        int R = rows.size();
        int C = rows.isEmpty() ? 0 : rows.get(0).length;
        int[][] mat = new int[R][C];
        for (int i = 0; i < R; i++) {
            mat[i] = rows.get(i);
        }


        int max_sum = Integer.MIN_VALUE; 
        int sum;

        // Calculate hourglass sums
        for (int i = 0; i < R - 2; i++) {
            for (int j = 0; j < C - 2; j++) {
                sum = (mat[i][j] + mat[i][j + 1] + mat[i][j + 2]) + 
                      (mat[i + 1][j + 1]) +                       
                      (mat[i + 2][j] + mat[i + 2][j + 1] + mat[i + 2][j + 2]); 

                max_sum = Math.max(max_sum, sum);
            }
        }

        System.out.println(max_sum);
    }

    public static void max_ones() {
        Scanner sc=new Scanner(System.in);
        int x=sc.nextInt();
        String b=Integer.toBinaryString(x);
        int m=0;
        int prev=0;
        int cur=0;
        for (int i=0;i<b.length();i++){
            if (b.charAt(i)=='1'){
                cur++;
            }
            else{
                m=Math.max(m,prev+cur+1);
                prev=cur;
                cur=0;
            }
        }
        m = Math.max(m, prev + cur+1);
        System.out.print(m);
    }

    public static void swapNibbles() {
        Scanner scanner = new Scanner(System.in);
        int b = scanner.nextInt();
        scanner.close();
        int highNibble = (b & 0xF0) >> 4; 
        int lowNibble = (b & 0x0F) << 4;          
        System.out.println(lowNibble | highNibble);
    }


    public static void Equilibrium() {
        Scanner sc = new Scanner(System.in);
        String val = sc.nextLine();
        String[] v = val.trim().split(" ");
        int[] arr=new int[v.length];
        for (int i=0;i<v.length;i++){
            arr[i]=Integer.parseInt(v[i]);
        }

        int totalSum = 0;
        int leftSum = 0;
        int maxSum = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            totalSum += arr[i];
        }

        for (int i = 0; i < arr.length; i++) {
            totalSum -= arr[i]; 
            if (leftSum == totalSum && leftSum > maxSum) {
                maxSum = leftSum;
            }
            leftSum += arr[i];
        }

        if (maxSum == Integer.MIN_VALUE) {
            System.out.println("No Equilibrium Point Found.");
        } else {
            System.out.println(maxSum);
        }
    }

    
    
    public static long karatsubaMultiply(long x, long y) { // n^log2(3)
        if (x < 10 || y < 10) {
            return x * y;
        }

        int n = Math.max(Long.toString(x).length(), Long.toString(y).length());
        int half = (n + 1) / 2;

        long a = x / (long) Math.pow(10, half);
        long b = x % (long) Math.pow(10, half);
        long c = y / (long) Math.pow(10, half);
        long d = y % (long) Math.pow(10, half);

        long ac = karatsubaMultiply(a, c);
        long bd = karatsubaMultiply(b, d);
        long adbc = karatsubaMultiply(a + b, c + d) - ac - bd;

        return (long) (ac * Math.pow(10, 2 * half) + adbc * Math.pow(10, half) + bd);
    }

    public static void max_product() {
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int ms=-1000;
        int[] arr=new int[n];
        for (int i=0;i<n;i++){
            arr[i]=sc.nextInt();
        }
        int pre=1;
        int post=1;
        for (int i=0;i<n;i++){
            if (pre==0){
                pre=1;
            }
            if (post==0){
                post=1;
            }
            pre=pre*arr[i];
            post=post*arr[n-i-1];
            ms=Math.max(ms,Math.max(pre,post));
        }
        System.out.print(ms);
    }

    public static  void lexi_pal(){
        Scanner sc=new Scanner(System.in);
        String s=sc.nextLine();
        char[] arr = s.toCharArray();
        int i=0;
        int j=s.length()-1;
        while (i<j){
            if (arr[i]!=arr[j]){
                if (arr[i]<arr[j]){
                    arr[j]=arr[i];
                }
                else{
                    arr[i]=arr[j];
                }
            }
            i++;
            j--;
        }
        System.out.println(new String(arr));

    }

    public static int booth(int n1, int n2) {
        int Q = n2;
        int A = n1;
        int P = 0;
        int count = Integer.SIZE;            
        while (count > 0) {
            if ((Q & 1) == 1) {
                P += A;
            }
            A <<= 1;
            count--;
            Q >>= 1;
        }
        return P;
    }

    public static void swap(int arr[], int fi, int si, int d) {
        int i, temp;
        for (i = 0; i < d; i++) {
            temp = arr[fi + i];
            arr[fi + i] = arr[si + i];
            arr[si + i] = temp;
        }
    }

    public static void leftRotate(int arr[], int d, int n) {
        int i, j;
        if (d == 0 || d == n)
            return;

        if (d > n)
            d = d % n;
        i = d;
        j = n - d;
        while (i != j) {
            if (i < j) /* A is shorter */ {
                swap(arr, d - i, d + j - i, i);
                j -= i;
            } else /* B is shorter */ {
                swap(arr, d - i, d, j);
                i -= j;
            }
        }
        /* Finally, block swap A and B */
        swap(arr, d - i, d, i);
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[low];
        int i = low + 1;
        for (int j = low + 1; j <= high; j++) {
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, low, i - 1);
        return i - 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    public static String moveAllHyphens(String str) {
        String hyp="";
        String result = "";

        
        for (char c : str.toCharArray()) {
            if (c == '-') {
                hyp+=c;
            } else {
                result += c;
            }
        }

        
        return hyp + result;
    }

    static int numberOfPaths(int m, int n) {
        if (m == 1 || n == 1) {
            return 1;
        }
        return numberOfPaths(m - 1, n) + numberOfPaths(m, n - 1);
    }

    static boolean isSafe(int[][] maze, int x, int y, int n, int m) {
        return (x >= 0 && x < n && y >= 0 && y < m && maze[x][y] == 1);
    }


    static boolean solveMazeUtil(int[][] maze, int x, int y, int n, int m, int[][] solution) {
        
        if (x == n - 1 && y == m - 1) {
            solution[x][y] = 1;
            return true;
        }

        
        if (isSafe(maze, x, y, n, m)) {
            
            solution[x][y] = 1;

            // Move forward in the x direction
            if (solveMazeUtil(maze, x + 1, y, n, m, solution)) {
                return true;
            }

            // Move downward in the y direction
            if (solveMazeUtil(maze, x, y + 1, n, m, solution)) {
                return true;
            }

            // If none of the above movements work, backtrack
            solution[x][y] = 0;
        }
        return false;
    }

    // Function to solve the maze problem
    static void solveMaze(int[][] maze, int n, int m) {
        int[][] solution = new int[n][m];

        if (!solveMazeUtil(maze, 0, 0, n, m, solution)) {
            System.out.println("No Path Found");
        } else {
            // Print the solution path
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(solution[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    static int josephus(int n, int k) {
        if (n == 1) {
            return 0;
        } else {
            return (josephus(n - 1, k) + k) % n;
        }
    }

    static boolean isSafe(int[][] graph, int[] path, int pos, int v) {
        if (graph[path[pos - 1]][v] == 0) {
            return false;
        }

        for (int i = 0; i < pos; i++) {
            if (path[i] == v) {
                return false;
            }
        }

        return true;
    }
    
    static boolean hamiltonianPathUtil(int[][] graph, int[] path, int pos, int n) {
        if (pos == n) {
            return true;
        }

        for (int v = 0; v < n; v++) {
            if (isSafe(graph, path, pos, v)) {
                path[pos] = v;

                if (hamiltonianPathUtil(graph, path, pos + 1, n)) {
                    return true;
                }

                path[pos] = -1;
            }
        }

        return false;
    }

    static boolean isHamiltonianPath(int n, int[][] graph) {
        int[] path = new int[n];
        Arrays.fill(path, -1);

        path[0] = 0;

        return hamiltonianPathUtil(graph, path, 1, n);
    }

    static long[] weights = new long[26];

    static void calculateWeights() {
        weights[0] = 1;
        for (int i = 1; i < 26; i++) {
            weights[i] = (i + 1) * weights[i - 1] + weights[i - 1];
        }
    }

    static String smallestString(int totalWeight) {
        char[] result = new char[totalWeight];
        int index = 0;

        for (int i = 25; i >= 0; i--) {
            while (totalWeight >= weights[i]) {
                result[index++] = (char) ('A' + i);
                totalWeight -= weights[i];
            }
        }

        char[] finalResult = new char[index];
        for (int i = 0; i < index; i++) {
            finalResult[i] = result[index - i - 1];
        }

        return new String(finalResult);
    }

    public static boolean isValidMove(int x, int y, int N, int[][] board) {
        return (x >= 0 && x < N && y >= 0 && y < N && board[x][y] == -1);
    }

    public static boolean solveKnightTour(int x, int y, int movei, int N, int[][] board) {
        int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
        if (movei == N * N) {
            return true;
        }
        for (int k = 0; k < 8; k++) {
            int newX = x + dx[k];
            int newY = y + dy[k];
            if (isValidMove(newX, newY, N, board)) {
                board[newX][newY] = movei;
                if (solveKnightTour(newX, newY, movei + 1, N, board)) {
                    return true;
                }
                board[newX][newY] = -1;
            }
        }
        return false;
    }

    public static void printBoard(int N, int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // Simple_sieve();

        // Scanner sc = new Scanner(System.in);
        // System.out.println("Enter the lower limit");
        // int m = sc.nextInt();
        // System.out.println("Enter the upper limit");
        // int n = sc.nextInt();
        // segmented_sieve(m,n);
        // sc.close();

        // Scanner sc = new Scanner(System.in);
        // System.out.println("Enter the first number");
        // int m = sc.nextInt();
        // System.out.println("Enter the second number");
        // int n = sc.nextInt();
        // System.out.println("The GCD between  " + m + " and " + n + " is " + gcd(m, n));
        // sc.close();

        // toggle();

        // Euler();

        // remainder();

        // strobo();

        // Scanner sc=new Scanner(System.in);
        // System.out.print("Read n : ");
        // int n = sc.nextInt();
        // sc.close();
        // System.out.println(numdef(n, n));
        // int x=5;
        // System.out.println(x++ + ++x);

        // leader();

        // majority_element();

        // binary_palindrome();

        // hour_glass();

        // max_ones();

        // swapNibbles();

        // Equilibrium();

        // Scanner sc = new Scanner(System.in);
        // System.out.println("enter");
        // String binaryString1 = sc.nextLine();
        // String binaryString2 = sc.nextLine();
        // long num1 = Long.parseLong(binaryString1, 2);
        // long num2 = Long.parseLong(binaryString2, 2);
        // long result = karatsubaMultiply(num1, num2);
        // System.out.println(result);

        // max_product();

        // lexi_pal();

        // Scanner scanner = new Scanner(System.in);
        // int n1 = scanner.nextInt();
        // int n2 = scanner.nextInt();
        // int product = booth(n1, n2);
        // System.out.println(product);
        // scanner.close();

        // Scanner sc = new Scanner(System.in);
        // String val = sc.nextLine();
        // String[] v = val.trim().split(" ");
        // int n = v.length;
        // int[] arr = new int[n];

        // for (int j = 0; j < n; j++) {
        //     arr[j] = Integer.parseInt(v[j]);
        // }

        // int d = sc.nextInt();
        // d = d % n;
        // leftRotate(arr, d, n);

        // for (int i = 0; i < n; i++) {
        //     System.out.print(arr[i] + (i < n - 1 ? " " : ""));
        // }
    
        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter the number of elements: ");
        // int n = scanner.nextInt();
        // int[] arr = new int[n];

        // System.out.print("Enter the elements: ");
        // for (int i = 0; i < n; i++) {
        //     arr[i] = scanner.nextInt();
        // }

        // quickSort(arr, 0, arr.length - 1);
        // // selectionSort(arr);

        // System.out.println("Sorted array: " + Arrays.toString(arr));

        // Scanner scanner = new Scanner(System.in);
        // String input = scanner.nextLine();
        // System.out.println(moveAllHyphens(input));

        // Scanner s=new Scanner(System.in);
        // int apple=s.nextInt();
        // int c=0,sum=0;
        // while(sum<apple){
        //     c++;
        //     sum+=(12*c*c);
        // }
        // System.out.print(8*c);

        // Scanner scanner = new Scanner(System.in);

        // int m = scanner.nextInt();
        // int n = scanner.nextInt();
        // System.out.println(numberOfPaths(m, n));

        // Scanner scanner = new Scanner(System.in);

        
        // int n = scanner.nextInt();
        // int m = scanner.nextInt();

        
        // int[][] maze = new int[n][m];
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < m; j++) {
        //         maze[i][j] = scanner.nextInt();
        //     }
        // }

        
        // solveMaze(maze, n, m);

        // Scanner scanner = new Scanner(System.in);
        // int n = scanner.nextInt();
        // int k = scanner.nextInt();
        // int safePosition = josephus(n, k) + 1;
        // System.out.println(safePosition);

        // Scanner scanner = new Scanner(System.in);
        // int t = scanner.nextInt();

        // for (int i = 0; i < t; i++) {
        //     int n = scanner.nextInt();
        //     int m = scanner.nextInt();

        //     int[][] graph = new int[n][n];
        //     for (int j = 0; j < m; j++) {
        //         int u = scanner.nextInt() - 1; // Adjust for 0-based indexing
        //         int v = scanner.nextInt() - 1; // Adjust for 0-based indexing
        //         graph[u][v] = 1;
        //         graph[v][u] = 1;
        //     }

        //     if (isHamiltonianPath(n, graph)) {
        //         System.out.println(1);
        //     } else {
        //         System.out.println(0);
        //     }

            
        // }

        // calculateWeights();
        // Scanner scanner = new Scanner(System.in);
        // int totalWeight = scanner.nextInt();
        // System.out.println(smallestString(totalWeight));

        int N = 8;
        int[][] board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = -1;
            }
        }
        board[0][0] = 0;
        if (solveKnightTour(0, 0, 1, N, board)) {
            printBoard(N, board);
        } else {
            System.out.println("Solution does not exist");
        }
    
    }
}   
