package dsa.example;


import dsa.iface.IBinarySearchTree;
import dsa.iface.IBinaryTree;
import dsa.iface.IPosition;
import dsa.impl.BinarySearchTree;
import dsa.impl.SplayTree;
import dsa.util.TreePrinter;


public class SplayTreeStructureTest {

	public static void main( String[] args ) {

	      // I want to insert the following values into an AVL tree, in order.
	      int[] SplayOrder = new int[] { 1, 5, 4, 3, 2, 6 };
	      	   
	      int[] BSTOrder  = new int[] { 6, 3, 2, 5, 1 };
	      
	      // create my two trees
	      SplayTree<Integer> t1 = new SplayTree<>();
	      IBinarySearchTree<Integer> t2 = new BinarySearchTree<>();

	      // insert the values into the two trees 
	      for ( int v : SplayOrder )
	      {
	    	  t1.insert( v );
	    	  TreePrinter.printTree(t1);
	      }
	      
	      t1.remove(4);
	      
	      for ( int v : BSTOrder )
	         t2.insert( v );
	      System.out.println("Final: ");
	      TreePrinter.printTree(t1);
	      TreePrinter.printTree(t2);
	      System.out.println( "Is the AVL Tree in the expected shape? " + ( areEqual( t1, t1.root(), t2, t2.root() ) ? "YES! :-D" : "No! :-(" ) );
	   }

	   // check if two subtrees are equal (have the same shape and the same elements).
	   // to check a whole tree, pass in the tree roots as the IPosition objects.
	   private static <E extends Comparable<E>> boolean areEqual( IBinaryTree<E> t1, IPosition<E> p1, IBinaryTree<E> t2, IPosition<E> p2 ) {
	      // they're both external nodes, so they are equal.
	      if ( t1.isExternal( p1 ) && t2.isExternal( p2 ) )
	         return true;
	      // they are both internal, have the same element, and their left and right subtrees are also equal.
	      else if ( t1.isInternal( p1 ) && t2.isInternal( p2 ) ) {
	         return p1.element().equals( p2.element() ) && areEqual( t1, t1.left( p1 ), t2, t2.left( p2 ) ) && areEqual( t1, t1.right( p1 ), t2, t2.right( p2 ) );
	      }
	      // one is internal and the other is external: not the same tree.
	      else {
	         return false;
	      }
	   }
}
