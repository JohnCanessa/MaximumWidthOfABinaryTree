import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;


/**
 * Definition for a binary tree node.
 */
class TreeNode {
  
    // **** class members ****
    int         val;
    TreeNode    left;
    TreeNode    right;
  
    // **** constructors ****
    TreeNode() {}
  
    TreeNode(int val) { this.val = val; }
  
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    // **** ****
    @Override
    public String toString() {
        return "" + this.val;
    }
}


/**
 * Maximum width of a binary tree
 * https://www.geeksforgeeks.org/maximum-width-of-a-binary-tree/
 */
public class Solution {


    /**
     * Generate the number of nodes in th eBT at the specified level.
     */
    static int nodesInLevel(int level) {
        if (level < 1)
            return 0;
        else
            return (int)Math.pow(2.0, level - 1);
    }


    /**
     * Populate a binary tree using the specified array of integer and null values.
     */
    static TreeNode populateBT(String[] arr) {

        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();

        // **** ****
        TreeNode root = null;

        // **** begin and end of substring to process ****
        int b   = 0;
        int e   = 0;

        // **** loop once per binary tree level ****
        for (int level = 1; b < arr.length; level++) {

            // ???? ????
            // System.out.println("populateBT <<< level: " + level);

            // **** count of nodes at this level ****
            int count = nodesInLevel(level);

            // ???? ????
            // System.out.println("populateBT <<< count: " + count);

            // **** compute e ****
            e = b + count;

            // ??? ????
            // System.out.println("populateBT <<< b: " + b + " e: " + e);

            // **** generate sub array of strings ****
            String[] subArr = Arrays.copyOfRange(arr, b, e);

            // ???? ????
            // System.out.println("populateBT <<< subArr: " + Arrays.toString(subArr));

            // **** populate the specified level in the binary tree ****
            root = populateBTLevel(root, level, subArr, q);

            // **** update b ****
            b = e;

            // ???? ????
            // System.out.println("populateBT <<< b: " + b);
        }

        // **** return populated binary tree ****
        return root;
    }


    /**
     * Populate the specified level in the specified binary tree.
     */
    static TreeNode populateBTLevel(TreeNode root, int level, String[] subArr, Queue<TreeNode> q) {

        // **** populate binary tree root (if needed) ****
        if (root == null) {
            root = new TreeNode(Integer.parseInt(subArr[0]));
            q.add(root);
            return root;
        }

        // **** ****
        TreeNode child = null;

        // ???? ????
        // System.out.println("populateBTLevel <<< subArr: " + Arrays.toString(subArr));

        // **** traverse the sub array of node values ****
        for (int i = 0; (i < subArr.length) && (subArr[i] != null); i++) {

            // ???? ????
            // System.out.println("populateBTLevel <<< q: " + q.toString());

            // **** child node ****
            child = null;

            // **** create and attach child node (if needed) ****
            if (!subArr[i].equals("null"))
                child = new TreeNode(Integer.parseInt(subArr[i]));

            // **** add child to the queue ****
            q.add(child);

            // **** attach child node (if NOT null) ****
            if (child != null) {
                if (insertChild == Child.LEFT)
                    q.peek().left = child;
                else
                    q.peek().right = child;
            }

            // **** remove node from the queue (if needed) ****
            if (insertChild == Child.RIGHT) {

                // ???? ????
                // System.out.println("populateBTLevel <<< q: " + q.toString());

                q.remove();
            }

            // **** toggle insert for next child ****
            insertChild = (insertChild == Child.LEFT) ? Child.RIGHT : Child.LEFT;
        }

        // ???? ????
        // System.out.println("populateBTLevel <<< q: " + q.toString());

        // **** return root of binary tree ****
        return root;
    }


    /**
     * This function populates a BT in level order as specified by the array.
     * This function supports 'null' values.
     */
    static TreeNode populateTree(String[] arr) {
    
        // ???? ????
        // System.out.println("populateTree <<< arr: " + Arrays.toString(arr));

        // **** root for the BT ****
        TreeNode root = null;
    
        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();
    
        // **** traverse the array of values inserting nodes 
        //      one at a time into the BT ****
        for (String strVal : arr) {
            root = insertValue(root, strVal, q);

            // ???? ????
            // System.out.println("populateTree <<< q: " + q.toString());
        }
    
        // **** return the root of the binary tree ****
        return root;
    }


    /**
     * Enumerate which child in the node at the head of the queue 
     * (see populateTree function) should be updated.
     */
    enum Child {
        LEFT,
        RIGHT
    }
 
 
    // **** child turn to insert on node at head of queue ****
    static Child  insertChild = Child.LEFT;
 

    /**
     * This function inserts the next value into the specified BT.
     * This function is called repeatedly from the populateTree method.
     * This function supports 'null' values.
     */
    static TreeNode insertValue(TreeNode root, String strVal, Queue<TreeNode> q) {

        // **** node to add to the BST in this pass ****
        TreeNode node = null;
    
        // **** create a node (if needed) ****
        if (!strVal.equals("null"))
            node = new TreeNode(Integer.parseInt(strVal));
    
        // **** check is the BST is empty (this becomes the root node) ****
        if (root == null)
            root = node;
    
        // **** add node to left child (if possible) ****
        else if (insertChild == Child.LEFT) {
        
            // **** add this node as the left child ****
            if (node != null)
                q.peek().left = node; 
            
            // **** for next pass ****
            insertChild = Child.RIGHT;
        }
    
        // **** add node to right child (if possible) ****
        else if (insertChild == Child.RIGHT) {
        
            // **** add this node as a right child ****
            if (node != null)
                q.peek().right = node;
    
            // **** remove node from queue ****
            q.remove();
    
            // **** for next pass ****
            insertChild = Child.LEFT;
        }
    
        // **** add this node to the queue (if NOT null) ****
        if (node != null)
            q.add(node);
        
        // **** return the root of the binary tree ****
        return root;
    }


    /**
    * This method implements a breadth-first search traversal of a binary tree.
    * This method is iterative.
    * It displays all nodes at each level on a separate line.
    */
    static String bfsTraversal(TreeNode root) {
    
        // **** to generate string ****
        StringBuilder sb = new StringBuilder();
    
        // **** define the current and next queues ****
        List<TreeNode> currentQ = new LinkedList<TreeNode>();
        List<TreeNode> nextQ    = new LinkedList<TreeNode>();
    
        // **** add the root node to the current queue ****
        currentQ.add(root);
    
        // **** loop while the current queue has entries ****
        while (!currentQ.isEmpty()) {
    
            // **** remove the next node from the current queue ****
            TreeNode n = currentQ.remove(0);
    
            // **** display the node value ****
            if (n != null)
                sb.append(n.toString() + " ");
            else
                sb.append("null ");
    
            // **** add left and right children to the next queue ****
            if (n != null) {
                if (n.left != null)
                    nextQ.add(n.left);
                else
                    nextQ.add(null);
    
                if (n.right != null)
                    nextQ.add(n.right);
                else
                    nextQ.add(null);
            }
    
            // **** check if the current queue is empty (reached end of level) ****
            if (currentQ.isEmpty()) {
                
                // **** end of current level ****
                sb.append("\n");
    
                // **** check if we have all null nodes in the next queue ****
                boolean allNulls = true;
                for (TreeNode t : nextQ) {
                    if (t != null) {
                        allNulls = false;
                        break;
                    }
                }
    
                // **** point the current to the next queue ****
                currentQ = nextQ;
    
                // **** clear the next queue ****
                nextQ = new LinkedList<TreeNode>();
    
                // **** clear the current queue (all null nodes) ****
                if (allNulls)
                    currentQ = new LinkedList<TreeNode>();
            }
        }

        // **** return a string representation of the BT ****
        return sb.toString();
    }
    
    
    /**
     * Return the height of the specified BT.
     * Increments height by 1 descending into the BT.
     * This is a recursive call.
     */
    static int treeDepth(TreeNode root) {

        // **** base case ****
        if (root == null)
            return 0;

        // **** height of left sub tree ****
        int leftH = treeDepth(root.left) + 1;

        // **** height of right sub tree ****
        int rightH = treeDepth(root.right) + 1;

        // **** return the largest height ****
        return Math.max(leftH, rightH);
    }


    /**
     * Given a binary tree, write a function to get the maximum width of the given tree.
     * Width of a tree is maximum of widths of all levels.
     * Maximum complexity: O(n^2)
     */
    static int maxBTWidth(TreeNode root) {

        // **** ****
        int maxWidth            = Integer.MIN_VALUE;
        int width               = 0;
        List<TreeNode> currentQ = new LinkedList<TreeNode>();
        List<TreeNode> nextQ    = new LinkedList<TreeNode>();

        // **** prime the current queue ****
        currentQ.add(root);
        maxWidth = width = currentQ.size();

        // ???? ????
        // System.out.println("maxBTWidth <<< width: " + width + " maxWidth: " + maxWidth);

        // **** loop while the current queue is not empty ****
        while (!currentQ.isEmpty()) {

            // **** remove head node from the current queue ****
            TreeNode node = currentQ.remove(0);

            // **** add left child to next queue (if needed) ****
            if (node.left != null)
                nextQ.add(node.left);
            
            // **** add right child to next queue (if needed) ****
            if (node.right != null)
                nextQ.add(node.right);

            // **** swap queues (if needed) ****
            if (currentQ.isEmpty()) {
                currentQ    = nextQ;
                nextQ       = new LinkedList<TreeNode>();

                // **** update the max width (if needed) ****
                width = currentQ.size(); 
                if (maxWidth < width)
                    maxWidth = width;
            }
        }

        // **** ****
        return maxWidth;
    }


    // **** global variables  ****
    static int maxWidth;
    static HashMap<Integer, Integer> depthLeftPosition;


    /**
     * Auxiliary function.
     * Recursive function.
     */
    static void getWidth(TreeNode root, int depth, int position) {

        // ???? ????
        System.out.println("getWidth <<< depth: " + depth + " position: " + position);

        // // **** base case ****
        // if (root == null)
        //     return;

        // **** add leftmost position to the hash map ****
        depthLeftPosition.computeIfAbsent(depth, mappingFunction->position);

        // ???? ????
        System.out.println("getWidth <<< depthLeftPosition: " + depthLeftPosition.toString());

        // **** update the max width ****
        maxWidth = Math.max(maxWidth, position - depthLeftPosition.get(depth) + 1);

        // ???? ????
        System.out.println("getWidth <<< maxWidth: " + maxWidth);

        // **** recursive cases (base case included) ****
        if (root.left != null)
            getWidth(root.left, depth + 1, position * 2);

        if (root.right != null)
            getWidth(root.right, depth + 1, position * 2 + 1);
    }


    /**
     * Given a binary tree, write a function to get the maximum width of the given tree.
     * The maximum width of a tree is the maximum width among all levels.
     * 
     * The width of one level is defined as the length between the end-nodes 
     * (the leftmost and right most non-null nodes in the level, 
     * where the null nodes between the end-nodes are also counted into the length calculation.
     * 
     * It is guaranteed that the answer will in the range of 32-bit signed integer.
     */
    static int widthOfBinaryTree(TreeNode root) {

        // **** initialize global variable(s) ****
        maxWidth            = 0;
        depthLeftPosition   = new HashMap<Integer, Integer>();

        // **** start recursion ****
        getWidth(root, 1, 0);

        // **** return answer ****
        return maxWidth;
    }


    /**
     * Given a binary tree, write a function to get the maximum width of the given tree.
     * The maximum width of a tree is the maximum width among all levels.
     * 
     * The width of one level is defined as the length between the end-nodes 
     * (the leftmost and right most non-null nodes in the level, 
     * where the null nodes between the end-nodes are also counted into the length calculation.
     * 
     * It is guaranteed that the answer will in the range of 32-bit signed integer.
     */
    static int widthOfBinaryTree1(TreeNode root) {

        // **** local variables ****
        int level               = 1;
        int width               = 1;
        List<TreeNode> currentQ = new LinkedList<TreeNode>();
        List<TreeNode> nextQ    = new LinkedList<TreeNode>();

        // **** initialize global variable(s) ****
        maxWidth    = 0;

        // **** get the depth of the binary tree ****
        int depth = treeDepth(root);

        // ???? ????
        // System.out.println("widthOfBinaryTree <<< depth: " + depth);

        // **** prime the current queue ****
        currentQ.add(root);

        // **** loop while the current queue is not empty ****
        while (!currentQ.isEmpty() && (level <= depth)) {

            // ???? ????
            // System.out.println("widthOfBinaryTree <<< level: " + level + " currentQ: " + currentQ.toString());

            // **** remove head node from the current queue ****
            TreeNode node = currentQ.remove(0);

            // **** process this node ****
            if (node == null) {
                nextQ.add(null);
                nextQ.add(null);
            } else {

                // **** add left child to next queue (if needed) ****
                if (node.left != null)
                    nextQ.add(node.left);
                else 
                    nextQ.add(null);

                // **** add right child to next queue (if needed) ****
                if (node.right != null)
                    nextQ.add(node.right);
                else
                    nextQ.add(null);
            }
  
            // **** swap queues (if needed) ****
            if (currentQ.isEmpty()) {

                // ???? ????
                // System.out.println("widthOfBinaryTree <<< level: " + level + " width: " + width + " maxWidth: " + maxWidth);

                // **** update max width (if needed) ****
                if (width > maxWidth)
                    maxWidth = width;

                // **** swap queues ****
                currentQ    = nextQ;
                nextQ       = new LinkedList<TreeNode>();

                // **** incremment level ****
                level++;

                // **** compute the width for the next level (if needed) ****
                if (level <= depth)
                    width = levelWidth(currentQ);
            }
        }

        // **** return the max width of the binary tree ****
        return maxWidth;
    }


    /**
     * Determine the width of the binary tree based on the contents of the queue.
     * Complexity: O(n)
     */
    static int  levelWidth(List<TreeNode> q) {

        // **** ****
        int first   = 0;
        int last    = 0;

        // **** look for first non-null node ****
        for (int i = 0; i < q.size(); i++) {
            if (q.get(i) != null) {
                first = i;
                break;
            }
        }

        // **** look for last non-null node ****
        for (int i = q.size() - 1; i >= 0; i--) {
            if (q.get(i) != null) {
                last = i;
                break;
            }
        }

        // **** return the width ****
        return last - first + 1;
    }


    /**
     * Test scaffolding.
     */
    public static void main(String[] args) {
        
        // **** BT root ****
        TreeNode root = null;

        // **** open scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read data for BT (in level order) ****
        String[] arr = sc.nextLine().split(",");

        // **** close scanner ****
        sc.close();

        // **** populate the binary tree ****
        // root = populateTree(arr);
        root = populateBT(arr);

        // ???? display the binary tree ????
        System.out.println("main <<< bfsTraversal: ");
        System.out.print(bfsTraversal(root));

        // **** find and display the binary tree maximum width ****
        System.out.println("main <<< maxBTWidth: " + maxBTWidth(root));

        // **** compute and display the binary tree maximum width ****
        System.out.println("main <<< widthOfBinaryTree1: " + widthOfBinaryTree1(root));

        // **** compute and display the binary tree maximum width ****
        System.out.println("main <<< widthOfBinaryTree: " + widthOfBinaryTree(root));

        // ???? ????
        System.out.println("main <<< depthLeftPosition: " + depthLeftPosition.toString());
    }
}