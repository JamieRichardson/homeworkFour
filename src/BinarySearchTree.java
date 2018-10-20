public class BinarySearchTree<E extends Comparable<E>> {
    class Node {
        E value;
        Node leftChild = null;
        Node rightChild = null;

        Node(E value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if ((obj instanceof BinarySearchTree.Node) == false)
                return false;
            @SuppressWarnings("unchecked")
            Node other = (BinarySearchTree<E>.Node) obj;
            return other.value.compareTo(value) == 0 &&
                    other.leftChild == leftChild && other.rightChild == rightChild;
        }
    }

    protected static int depthCount;

    protected Node root = null;

    protected void visit(Node n) {
        System.out.println(n.value);
    }

    public boolean contains(E val) {
        return contains(root, val);
    }

    protected boolean contains(Node n, E val) {
        if (n == null) return false;

        if (n.value.equals(val)) {
            return true;
        } else if (n.value.compareTo(val) > 0) {
            return contains(n.leftChild, val);
        } else {
            return contains(n.rightChild, val);
        }
    }

    public boolean add(E val) {
        if (root == null) {
            root = new Node(val);
            return true;
        }
        return add(root, val);
    }

    protected boolean add(Node n, E val) {
        if (n == null) {
            return false;
        }
        int cmp = val.compareTo(n.value);
        if (cmp == 0) {
            return false; // this ensures that the same value does not appear more than once
        } else if (cmp < 0) {
            if (n.leftChild == null) {
                n.leftChild = new Node(val);
                return true;
            } else {
                return add(n.leftChild, val);
            }
        } else {
            if (n.rightChild == null) {
                n.rightChild = new Node(val);
                return true;
            } else {
                return add(n.rightChild, val);
            }
        }
    }

    public boolean remove(E val) {
        return remove(root, null, val);
    }

    protected boolean remove(Node n, Node parent, E val) {
        if (n == null) return false;

        if (val.compareTo(n.value) == -1) {
            return remove(n.leftChild, n, val);
        } else if (val.compareTo(n.value) == 1) {
            return remove(n.rightChild, n, val);
        } else {
            if (n.leftChild != null && n.rightChild != null) {
                n.value = maxValue(n.leftChild);
                remove(n.leftChild, n, n.value);
            } else if (parent == null) {
                root = n.leftChild != null ? n.leftChild : n.rightChild;
            } else if (parent.leftChild == n) {
                parent.leftChild = n.leftChild != null ? n.leftChild : n.rightChild;
            } else {
                parent.rightChild = n.leftChild != null ? n.leftChild : n.rightChild;
            }
            return true;
        }
    }

    protected E maxValue(Node n) {
        if (n.rightChild == null) {
            return n.value;
        } else {
            return maxValue(n.rightChild);
        }
    }


    /*********************************************
     *
     * IMPLEMENT THE METHODS BELOW!
     *
     *********************************************/


    // Method #1.
    public Node findNode(E val) {
        return findNode(root, val);
    }

    public Node findNode(Node n, E val) {
        if (n == null || val == null) {
            return null;
        } else if (n.value.compareTo(val) == 0) {
            return n;
        } else if (n.value.compareTo(val) > 0) {
            return findNode(n.leftChild, val);
        } else {
            return findNode(n.rightChild, val);
        }
    }

    // Method #2.
    protected int depth(E val) {
        depthCount = 0;
        if(findNode(val) == null) {
            return -1;
        }
        return depth(root, val);
    }

    protected int depth(Node n, E val) {
        if (n == null || n.value == null || val == null) {
            return -1;
        }
        if (n.value.equals(val)) {
            return depthCount;
        }
        if (n.value.compareTo(val) > 0) {
            depthCount++;
            depth(n.leftChild, val);
        } else {
            depthCount++;
            depth(n.rightChild, val);
        }
        return depthCount;
    }

    // Method #3.
    protected int height(E val) {
        if (val == null) {
            return -1;
        }
        final Node nodeToMeasure = findNode(val);
        if (nodeToMeasure == null) {
            return -1;
        }
        if (nodeToMeasure.leftChild == null && nodeToMeasure.rightChild == null) {
            return 0;
        }
        return height(nodeToMeasure, val);
    }

    protected int height(Node n, E val) {
        if (n == null || val == null) {
            return -1;
        }
        int heightLeft = -1;
        int heightRight = -1;
        if (n.leftChild != null && n.leftChild.value != val) {
            heightLeft = height(n.leftChild, val);
        }
        if (n.rightChild != null && n.rightChild.value != val) {
            heightRight = height(n.rightChild, val);
        }
        if (heightLeft > heightRight) {
            return heightLeft + 1;
        } else {
            return heightRight + 1;
        }
    }

    protected boolean traverseTreeDFS(Node n) {
        if(!isBalanced(n)) {
            return false;
        }
        if (root.leftChild != null) {
            traverseTreeDFS(root.leftChild);
        }
        if (root.rightChild != null) {
            traverseTreeDFS(root.rightChild);
        }
        return true;
    }

    // Method #4.
    protected boolean isBalanced(Node n) {
        if(n == null) {
            return false;
        }
        Node nodeToCheck = findNode(n.value);
        if (nodeToCheck == null || nodeToCheck.value == null) {
            return false;
        }
        if(nodeToCheck.leftChild == null && nodeToCheck.rightChild == null) {
            return true;
        }
        int leftValue = 0;
        if (nodeToCheck.leftChild != null) {
            leftValue = nodeToCheck.leftChild.value != null? height(n.leftChild.value) : -1;
        }
        int rightValue = nodeToCheck.rightChild.value != null? height(n.rightChild.value) : -1;
        return Math.abs((leftValue - rightValue)) <= 1;
    }

    // Method #5. .
    public boolean isBalanced() {
        return traverseTreeDFS(root);
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.add(8);
        tree.add(6);
        tree.add(4);
        tree.add(2);
        tree.add(16);
        tree.add(10);
        tree.add(9);
        tree.add(20);
        tree.add(12);
        if(tree.findNode(2) != null) {
            System.out.println("2 was found");
        } else {
            System.out.println("2 was not found");
        }
        System.out.println("Root is: " + tree.root.value);
        System.out.println("Depth of 44 is: " + tree.depth(44));
        System.out.println("Height of root is: " + tree.height(8));
        System.out.println("Leaf as input value for isbalanced is: " + tree.isBalanced(tree.findNode(16)));
    }
}