package dsa.impl;

import dsa.iface.IPosition;
import dsa.impl.AVLTree.AVLPosition;

public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {

   public void insert( T value ) {
      // TODO: Implement the insert(...) method.
      super.insert(value);
   }

   public boolean contains( T value ) {
      // TODO: Implement the contains(...) method.

      AVLPosition findP = (AVLPosition)super.find(root(),value);
      if(findP == null)
         return false;
      splay(findP);
      return true;
   }

   public void remove( T value ) {
      // TODO: Implement the remove(...) method.
      super.remove(value);
   }

   private void splay( IPosition<T> n ) {
      // TODO: Implement the splay(...) method.
      AVLPosition findP = (AVLPosition)super.find(root(),n.element() );
      AVLPosition parentP;
      while(!isRoot(findP)) {
         parentP =(AVLTree<T>.AVLPosition) parent(findP);
         if(findP == parentP.left) {
            if(isRoot(parentP))
               this.root = LL(parentP);
            else
               parentP.parent = LL(parentP);
         }
         else if(findP == parentP.right) {
            if(isRoot(parentP))
               this.root = RR(parentP);
            else
               parentP.parent = LL(parentP);
         }
         findP=parentP;
      }
   }

   private AVLPosition LL(AVLPosition rootP )   {
      if(rootP == null)
         return rootP;
      AVLPosition leftP = (AVLPosition)rootP.left;
      rootP.left = (AVLPosition)leftP.right;
      if(rootP.left != null)
         rootP.left.parent = rootP;
      leftP.right = (AVLPosition)rootP;
      if(leftP.right != null)
         leftP.right.parent = leftP;
      if(rootP.left != null && ((AVLPosition)rootP.left).height + 1 > rootP.height)
         rootP.height = ((AVLPosition)rootP.left).height + 1;
      if(leftP.right != null && ((AVLPosition)leftP.right).height + 1 > leftP.height)
         leftP.height = ((AVLPosition)leftP.right).height + 1;
      if(isRoot(rootP))
         this.root = leftP;
      return leftP;
   }
   private AVLPosition RR(AVLPosition rootP )   {
      AVLPosition rightP = (AVLPosition)rootP.right;
      rootP.right = (AVLPosition)rightP.left;
      if(rootP.right != null)
         rootP.right.parent = rootP;
      rightP.left = (AVLPosition)rootP;
      if(rightP.left != null)
         rightP.left.parent = rightP;
      if(rootP.right != null && ((AVLPosition)rootP.right).height + 1 > rootP.height)
         rootP.height = ((AVLPosition)rootP.right).height + 1;
      if(rightP.left != null && ((AVLPosition)rightP.left).height + 1 > rightP.height)
         rightP.height = ((AVLPosition)rightP.left).height + 1;
      if(isRoot(rootP))
         this.root = rightP;
      return rightP;
   }
}
