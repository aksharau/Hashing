package backbencher;

public class BST {
	
	int data;
	BST left;
	BST right;
	

	public static BST insert(BST rootNode, int value)
	{
		if(rootNode == null) //first element
		{
			BST node = new BST();
			node.data = value;
			node.left = null;
			node.right = null;
			rootNode = node;
			
		}
		else {
			//This is not the first node
			if(value<rootNode.data) //this will go to left subtree
			{ 
				if(rootNode.left==null) {
					rootNode.left = insert(rootNode.left,value);
					}
				else
					insert(rootNode.left,value);
			}
			else //goes to the right subtree
			{
				if(rootNode.right==null)
				{
					rootNode.right = insert(rootNode.right,value);
				}
				else
					insert(rootNode.right,value);
			}
		}
		return rootNode;
	}
	

	public String inOrder(BST root)
	{
		if(root==null)
		{
			return "";
		}
		else
		{
			String toPrint = "";
			toPrint += inOrder(root.left);
			toPrint += " "+ root.data + ",";
			toPrint += inOrder(root.right);
			return toPrint;
		}
	}
	
	public String preOrder(BST root)
	{
		if(root==null)
		{
			return "";
		}
		else
		{
			String toPrint = "";
			toPrint += " "+ root.data + ",";
			toPrint += preOrder(root.left);
			toPrint += preOrder(root.right);
			return toPrint;
		}
	}
	

	public String postOrder(BST root)
	{
		if(root==null)
		{
			return "";
		}
		else
		{
			String toPrint = "";
			
			toPrint += postOrder(root.left);
			toPrint += postOrder(root.right);
			toPrint += " "+ root.data + ",";
			return toPrint;
		}
	}
	
	public int getNodeCount( BST root)
	{
		if(root == null)
			return 0;
		else
		{
			return 1+getNodeCount(root.left)+getNodeCount(root.right);
		}
	}
	
	public boolean isInTree(BST root,int value)
	{
		if(root == null)
			return false;
		else
			return ( (root.data==value) || isInTree(root.left,value) || isInTree(root.right,value));
	}
	
	
	public int getHeight(BST root)
	{
		if(root == null)
			return 0;
		else
			{
				int leftHeight= getHeight(root.left);
				int rightHeight= getHeight(root.right);
				return 1+ Integer.max(leftHeight,rightHeight);
			}
	}
	
	public int getMin(BST root)
	{
		if(root == null)
			return 0;
		else
		{
			if(root.left==null)
				return root.data;
			else return(getMin(root.left));
						
		}
	}
	
	public int getMax(BST root)
	{
		if(root == null)
			return 0;
		else
		{
			if(root.right==null)
				return root.data;
			else return(getMax(root.right));
						
		}
	}	
	
	public boolean isBST(BST root,int minVal,int maxVal)
	{
		if(root==null) //degenerate case, there is no tree
			return true;
		else
		{
			if((root.data<maxVal)&&(root.data>minVal))
			{
				return isBST(root.left,minVal,root.data) && isBST(root.right,root.data,maxVal);
			}
			else
				return false;
		}
	}
	
	
	public BST deleteValue(BST root, int value)
	{
		if(root == null) //nothing to delete
			return null;
		else
		{
			if(root.data==value)
			{
				if((root.left==null)&&(root.right==null)) //no child nodes
					return null;
				else
				{
					if(root.left==null) // this automatically means that root.right is not null
						root = root.right;
					else 
						if(root.right==null)
							root = root.left;
						else
						{
							//neither the left nor the right is null
							//get the largest value from the left subtree,set that value as the root data
							//delete the largest from the left subtree
							int maxLeft = getMax(root.left);
							root.data=maxLeft;
							root.left = deleteValue(root.left,maxLeft);
						}
					return root;
				}
			}
			else
			{
				//data was not found
				if(root.data<value)
					root.right= deleteValue(root.right,value);
				else
					root.left = deleteValue(root.left,value);
				
				return root;
						
			}
		}
	}
	
	public BST findNode(BST root,int value)
	{
		if(root == null)
			return null;
		else
			if(root.data==value)
				return root;
			else
			{
				BST ret= findNode(root.left,value);
				if (ret==null)
					ret = findNode(root.right,value);
				return ret;
			}
	}
	
	public int getInorderSuccessor(BST root,int value)
	{
		BST node = findNode(root,value);
		if(node==null)
			return -1; //empty tree
		else
		{
				if(node.right!=null) //means we need to get the min from the right subtree
					{
						return getMin(root.right);
					
					}
				else
				{
					//no right subtree, hence need to find the deepest
					//to the left of this node
					BST successor = null;
					BST ancestor = root;
					while(ancestor!=node)
					{
						if(value < ancestor.data) {
							successor = ancestor;
							ancestor = ancestor.left;
						}
						else
							ancestor = ancestor.right;
					}
					if(successor==null)
						return -1;
					else
						return successor.data;
				}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BST tree = null;
		tree = insert(tree,200);
		insert(tree,100);
		insert(tree,250);
		insert(tree,150);
		insert(tree,50);
		insert(tree,210);
		insert(tree,300);
		System.out.println("The inorder tree is : " + tree.inOrder(tree));
		System.out.println("The preorder tree is : " + tree.preOrder(tree));
		System.out.println("The postOrder tree is : " + tree.postOrder(tree));
		
		System.out.println("The node count is : " + tree.getNodeCount(tree));
		
		System.out.println("Is value 300 in the tree?: " + tree.isInTree(tree, 300));
		System.out.println("Is value 210 in the tree?: " + tree.isInTree(tree, 210));
		System.out.println("Is value 150 in the tree?: " + tree.isInTree(tree, 150));
		
		System.out.println("The height of the tree is: " + tree.getHeight(tree));
		
		System.out.println("The min value in the tree is: " + tree.getMin(tree));
		
		System.out.println("The max value in the tree is: " + tree.getMax(tree));
		
		System.out.println("The tree is BST: " + tree.isBST(tree,-1000,1000));
		
		BST someTree = new BST();
		someTree.data = 100;
		BST someTreeLeft = new BST();
		someTreeLeft.data = 150;
		someTreeLeft.left = null;
		someTreeLeft.right=null;
		
		BST someTreeRight = new BST();
		someTreeRight.data = 250;
		someTreeRight.left = null;
		someTreeRight.right=null;
		
		someTree.left = someTreeLeft;
		someTree.right=someTreeRight;
		
		System.out.println("The someTree is BST: " + someTree.isBST(someTree,-1000,1000));
		
		someTree.left=null;
		someTreeRight.left=someTreeLeft;

		System.out.println("The someTree is BST: " + someTree.isBST(someTree,-1000,1000));
		
	//	tree = tree.deleteValue(tree, 250);
		
		System.out.println("The preOrder tree is : " + tree.preOrder(tree));
		
		System.out.println("The inorder successor  tree is : " + tree.getInorderSuccessor(tree, 210));
	}

}
