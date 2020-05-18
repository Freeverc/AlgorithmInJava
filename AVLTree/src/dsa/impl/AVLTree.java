package dsa.impl;

import dsa.iface.IPosition;
import dsa.util.TreePrinter;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

	@Override
	public void insert( T element ) {
		// TODO: Implement the insert(...) method.
		AVLPosition findP = (AVLPosition)super.find(root(), element );
		AVLPosition findP0 = findP;
		if( isInternal(findP))
			return;
		expandExternal( findP, element);
		findP.height = 1;

		AVLPosition parentP;
		while(!isRoot(findP)) {
			parentP =(AVLTree<T>.AVLPosition) parent(findP);
			if(findP.height+1>parentP.height)
				parentP.height = findP.height + 1;
			findP=parentP;
		}
		restructure(findP);
	}

	@Override
	public boolean contains( T element ) {
		// TODO: Implement the contains(...) method.
		return super.contains(element); // this line is here just so that the class will compile. You should replace it.
	}

	@Override
	public void remove( T element ) {
		// TODO: Implement the remove(...) method.
		super.remove(element);
		gH((AVLPosition)root());
		restructure((AVLPosition)root());
	}

	private void restructure( IPosition<T> x ) {
		// Implement the restructure(...) method.
		if(x == null)
			return;
		AVLPosition rootP = (AVLPosition) x;
		AVLPosition parentP = (AVLPosition) parent(x);
		int flag;
		if(parentP == null)
			flag = 0;
		else if(x == parentP.left)
			flag = 1;
		else if(x == parentP.right)
			flag = 2;
		else
			flag = 0;

		restructure(((AVLPosition)rootP.left));
		restructure(((AVLPosition)rootP.right));
		int leftH = rootP.left == null ? 0 : ((AVLPosition)rootP.left).height;
		int rightH = rootP.right == null ? 0 : ((AVLPosition)rootP.right).height;
		if(leftH - rightH >= 2)
		{
			int lLeftH = rootP.left.left == null ? 0 : ((AVLPosition)rootP.left.left).height;
			int lRightH = rootP.left.right == null ? 0 : ((AVLPosition)rootP.left.right).height;
			if(lLeftH >= lRightH)
				rootP = LL(rootP);
			else
				rootP = LR(rootP);

			if(flag != 0) {
				rootP.parent = (AVLPosition)parentP;
				if(flag == 1)
					parentP.left = rootP;
				else if(flag == 2)
					parentP.right = rootP;
			}
		}
		else if( rightH -leftH >= 2)
		{
			int rLeftH = rootP.right.left == null ? 0 : ((AVLPosition)rootP.right.left).height;
			int rRightH = rootP.right.right == null ? 0 : ((AVLPosition)rootP.right.right).height;
			if(rLeftH >= rRightH)
				rootP = RL(rootP);
			else
				rootP = RR(rootP);

			if(flag != 0) {
				rootP.parent = (AVLPosition)parentP;
				if(flag == 1)
					parentP.left = rootP;
				else if(flag == 2)
					parentP.right = rootP;
			}
		}
		return;
	}

	public void gH(AVLPosition rootP)
	{
		if(rootP == null)
			return;
		gH((AVLPosition)rootP.left);
		gH((AVLPosition)rootP.right);
		int leftH = rootP.left == null ? 0 : ((AVLPosition)rootP.left).height;
		int rightH = rootP.right == null ? 0 : ((AVLPosition)rootP.right).height;
		rootP.height = (leftH > rightH ?leftH:rightH) + 1;
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
		rootP.height = 1;
		if(rootP.left != null && ((AVLPosition)rootP.left).height + 1 > rootP.height)
			rootP.height = ((AVLPosition)rootP.left).height + 1;
		if(rootP.right != null && ((AVLPosition)rootP.right).height + 1 > rootP.height)
			rootP.height = ((AVLPosition)rootP.right).height + 1;
		leftP.height = 1;
		if(leftP.left != null && ((AVLPosition)leftP.left).height + 1 > leftP.height)
			leftP.height = ((AVLPosition)leftP.left).height + 1;
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
		rootP.height = 1;
		if(rootP.left != null && ((AVLPosition)rootP.left).height + 1 > rootP.height)
			rootP.height = ((AVLPosition)rootP.left).height + 1;
		if(rootP.right != null && ((AVLPosition)rootP.right).height + 1 > rootP.height)
			rootP.height = ((AVLPosition)rootP.right).height + 1;
		rightP.height = 1;
		if(rightP.left != null && ((AVLPosition)rightP.left).height + 1 > rightP.height)
			rightP.height = ((AVLPosition)rightP.left).height + 1;
		if(rightP.right != null && ((AVLPosition)rightP.right).height + 1 > rightP.height)
			rightP.height = ((AVLPosition)rightP.right).height + 1;
		if(isRoot(rootP))
			this.root = rightP;
		return rightP;
	}
	private AVLPosition LR(AVLPosition rootP )   {
		AVLPosition leftP = (AVLPosition)rootP.left;
		rootP.left = RR(leftP);
		return LL(rootP);
	}
	private AVLPosition RL(AVLPosition rootP )   {
		AVLPosition rightP = (AVLPosition)rootP.right;
		rootP.right = LL(rightP);
		return RR(rootP);
	}

	@Override
	protected BTPosition newPosition( T element, BTPosition parent ) {
		return new AVLPosition( element, parent );
	}


	public class AVLPosition extends BTPosition {
		int height = 0;

		public AVLPosition( T element, BTPosition parent ) {
			super( element, parent );
			this.height = 0;
		}
	}
}
