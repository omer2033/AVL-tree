

public class AVLTree <T extends Comparable<T>>{
    class Node{
    //balance factor
    int balanceFactor;
    // value which is contained within the node.
    T value;
    // height of the tree.
    int height;
    //Right and left children of this node.
    Node left, right;
    public Node(T value){
        this.value = value;
    }}
    //our root node
    private Node root;
    //to keep track of the number of nodes in a tree
    private int nodeCount = 0;
    //The height of tree is equal to number of edges between root node and the furthest leaf.
    //If tree contains  a single node it has a height of 0.
    public int height(){
        if (root==null) return 0;
        return root.height;

    }
    //returns the number of the nodes in a tree
    public int size(){
        return nodeCount;
    }

    //looks if the tree is empty or not
    public boolean isEmpty(){
        return size()==0;
    }

    public boolean contains(T value){
        return contains(root,value);
    }

    // the actual contains method which is called by method in above
    private boolean contains(Node node, T value){
        // if we hit the null it means it doesn't exist
        if(node == null)  return false;
        // compare current value to the value in the node
        int cmp = value.compareTo(node.value);
        //Dig into left subtree
        if(cmp< 0) return contains(node.left, value);
        //Dig into right subtree
        if(cmp> 0) return contains(node.right, value);
        //if we found return true
        return true;
    }

    //we're inserting a value to avl tree and checking to be sure it's not null
    public boolean insert(T value){
        if (value  == null) return false;
        //If the value is not already exist in a tree this codes will execute
        if(!contains(root,value)){
            root = insert(root,value);
            nodeCount++;
            return true;
        }
        return false;
    }

    private Node insert(Node node, T value){
    //base case
        if (node == null) return new Node(value);
        //Compare value to the value in the node
        int cmp = value.compareTo(node.value);
        //Insert node in left subtree
        if (cmp < 0){
            node.left = insert(node.left, value);
        }
        //Insert node in right subtree
        else {
            node.right = insert(node.right, value);
        }
        //update balance factor
        update(node);
        //re-balance tree
        return balance(node);
    }

    private void update(Node node){
        //if it's equal to null return -1 otherwise return node.left.height.
        int leftNodeHeight = (node.left ==  null) ? -1: node.left.height;
        int rightNodeHeight = (node.right == null) ? -1: node.right.height;
        //Update this node's height
        node.height = 1+ Math.max(leftNodeHeight,rightNodeHeight);
        //update balance factor
        node.balanceFactor = rightNodeHeight - leftNodeHeight;
    }

    private Node balance(Node node){
        //left heavy subtree
        if (node.balanceFactor == -2){
            //left-left case
            if (node.balanceFactor <=0){
                return leftLeftCase(node);
            }
            else {
                return leftRightCase(node);
            }
        }
        //right heavy subtree needs balancing
        else if(node.balanceFactor == +2){
            if (node.right.balanceFactor >= 0){
                return rightRightCase(node);
            }
            //right left case
            else {
                return rightLeftCase(node);
            }
        }
        return node;
    }
    private Node leftLeftCase(Node node){
        return rightRotation(node);
    }

    private Node leftRightCase(Node node){
        node.left = leftRotation(node.left);
        return leftLeftCase(node);
    }

    private Node rightRightCase(Node node){
        return leftRotation(node);
    }
    private Node rightLeftCase(Node node){
        node.right = rightRotation(node.right);
        return rightRightCase(node);
    }

    private Node leftRotation(Node node){
        Node newParent = node.right;
        node.right = newParent.left;
        newParent.left = node;
        update(node);
        update(newParent);
        return newParent;
    }

    private Node rightRotation(Node node){
        Node newParent = node.left;
        node.left = newParent.right;
        newParent.right = node;
        update(node);
        update(newParent);
        return newParent;
    }
    public boolean remove(T value){
        if (value == null) return false;
        if (contains(root, value)){
            root = remove(root,value);
            nodeCount--;
            return true;
        }
        return false;
    }
    private Node remove(Node node, T value){
        if (node == null) return null;
        int cmp = value.compareTo(node.value);
        //Dig into left sub tree if it's smaller than the current value
        if (cmp < 0){
            node.left = remove(node.left, value);
        }
        //Dig into right sub tree if it's greater than the current value
        else if (cmp > 0){
            node.right = remove(node.right, value);
        }
        // found the node we want to remove
        else {
            //This is the case with only right subtree or no subtree
            //In this situation we'll just swap the node we want to remove with its right child
            if (node.left == null){
                return node.right;
            }
            else if (node.right ==null){
                return node.left;
            }
            else {
                if (node.left.height > node.right.height){
                    T successorValue = findMax(node.left);
                    node.value = successorValue;
                    //find the largest node in the left subturee
                    node.left = remove(node.left, successorValue);
                }
                else {
                    T successorValue = findMin(node.right);
                    node.value = successorValue;
                    node.right = remove(node.right, successorValue);
                }
            }
        }
        update(node);
        return balance(node);
    }
    private T findMax(Node node){
        while (node.right != null)
            node = node.right;
            return node.value;
    }
    private T findMin(Node node){
        while (node.left != null)
            node = node.left;
        return node.value;
    }

    public static void main(String[] args) {
        AVLTree<Integer> avlTree = new AVLTree<>();
        int[] a = {30,80,50,40,20,60,70,10,90,95};
        for(int i : a){
            avlTree.insert(i);
        }
        System.out.println(avlTree.insert(5));
        System.out.println(avlTree.contains(80));
    }
}
