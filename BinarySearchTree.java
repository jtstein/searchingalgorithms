
public class BinarySearchTree {
	public static  Node root;
	public static int size = 0; // size of tree. (increments if insert function is called)
	int comparisons = 0;
	int assignments = 0;
	
	int getComparisons()
	{
		return comparisons;
	}
	int getAssignments()
	{
		return assignments;
	}
	
	public BinarySearchTree(){
		this.root = null;
	}
	
	public boolean find(String w){
		Node current = root;
		comparisons++;
		while(current!=null){
			if(current.word.compareTo(w) == 0){
				current.occurrence++; // if we found the word, increment the occurrence
				return true;
			}else if(current.word.compareTo(w) > 0){
				current = current.left;
			}else{
				current = current.right;
			}
		}
		return false;
	}
	
	public Node get(String w){ // return's the node if word is found, otherwise returns null
		Node current = root;
		while(current!=null){
			if(current.word.compareTo(w) == 0){
				return current;
			}else if(current.word.compareTo(w) > 0){
				current = current.left;
			}else{
				current = current.right;
			}
			comparisons++;
		}
		return null;
	}
	
	
	
	
	public boolean delete(String w){
		Node parent = root;
		Node current = root;
		
		boolean isLeftChild = false;
		while(current.word.compareTo(w) != 0){
			parent = current;
			if(current.word.compareTo(w)>0){
				isLeftChild = true;
				current = current.left;
			}else{
				isLeftChild = false;
				current = current.right;
			}
			if(current == null){
				return false;
			}
		}
		//if i am here that means we have found the node
		//Case 1: if node to be deleted has no children
		if(current.left==null && current.right==null){
			if(current==root){
				root = null;
			}
			if(isLeftChild ==true){
				parent.left = null;
			}else{
				parent.right = null;
			}
		}
		//Case 2 : if node to be deleted has only one child
		else if(current.right==null){
			if(current==root){
				root = current.left;
			}else if(isLeftChild){
				parent.left = current.left;
			}else{
				parent.right = current.left;
			}
		}
		else if(current.left==null){
			if(current==root){
				root = current.right;
			}else if(isLeftChild){
				parent.left = current.right;
			}else{
				parent.right = current.right;
			}
		}else if(current.left!=null && current.right!=null){
			
			//now we have found the minimum element in the right sub tree
			Node successor = getSuccessor(current);
			if(current==root){
				root = successor;
			}else if(isLeftChild){
				parent.left = successor;
			}else{
				parent.right = successor;
			}			
			successor.left = current.left;

		}	
		comparisons++;
		return true;		
	}
	
	public Node getSuccessor(Node deleleNode){
		Node successsor =null;
		Node successsorParent =null;
		Node current = deleleNode.right;
		while(current!=null){
			successsorParent = successsor;
			successsor = current;
			current = current.left;
		}
		//check if successor has the right child, it cannot have left child for sure
		// if it does have the right child, add it to the left of successorParent.
//		successsorParent
		if(successsor!=deleleNode.right){
			successsorParent.left = successsor.right;
			successsor.right = deleleNode.right;
		}
		comparisons++;
		return successsor;
	}
	public void insert(String w){
		this.size++; // increment size of tree
		assignments++;
		comparisons++;
		Node newNode = new Node(w);
		if(root==null){
			root = newNode;
			return;
		}
		
		Node current = root;
		Node parent = null;
		while(true){
			parent = current;
			if(0<current.word.compareTo(w)){				
				current = current.left;
				if(current==null){
					parent.left = newNode;
					return;
				}
			}else{
				current = current.right;
				if(current==null){
					parent.right = newNode;
					return;
				}
			}
			comparisons++;
		}
	}
	public void display(Node root){
		if(root!=null){
			display(root.left);
			System.out.print(" " + root.word);
			display(root.right);
		}
	}
	public static void main(String arg[]){

	}
}

class Node{
	
	String word;
	int occurrence;
	
	Node left;
	Node right;	
	public Node(String w){
		this.occurrence = 0;
		this.word = w;
		left = null;
		right = null;
	}
}