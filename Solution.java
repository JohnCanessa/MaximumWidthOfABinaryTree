import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * Maximum width of a binary tree
 * https://www.geeksforgeeks.org/maximum-width-of-a-binary-tree/
 */


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


public class Solution {


    /**
     * This function populates a BT in level order as 
     * specified by the array.
     * This function supports 'null' values.
     */
    static TreeNode populateTree(String[] arr) {
    
        // **** root for the BT ****
        TreeNode root = null;
    
        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();
    
        // **** traverse the array of values inserting nodes 
        //      one at a time into the BST ****
        for (String strVal : arr)
            root = insertValue(root, strVal, q);
    
        // **** clear the queue (the garbage collector will do this) ****
        q = null;
    
        // **** return the root of the BST ****
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
     * This function inserts the next value into the specified BST.
     * This function is called repeatedly from the populateTree method.
     * This function supports 'null' value.
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
        
        // **** return the root of the BST ****
        return root;
    }

   
    /*
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
    static int treeHeight(TreeNode root) {

        // **** base case ****
        if (root == null)
            return 0;

        // **** height of left sub tree ****
        int leftH = treeHeight(root.left) + 1;

        // **** height of right sub tree ****
        int rightH = treeHeight(root.right) + 1;

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
        System.out.println("maxBTWidth <<< width: " + width + " maxWidth: " + maxWidth);

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

                // ???? ????
                if (width != 0)
                    System.out.println("maxBTWidth <<< width: " + width + " maxWidth: " + maxWidth);
            }
        }

        // **** ****
        return maxWidth;
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

        // **** populate the BT ****
        root = populateTree(arr);

        // ???? display the BT ????
        System.out.println("main <<< root: ");
        System.out.print(bfsTraversal(root));

        // ???? display the BT height ????
        System.out.println("main <<< height: " + treeHeight(root));

        // **** find and display the BT maximum width ****
        System.out.println("main <<< maxWidth: " + maxBTWidth(root));
    }
}