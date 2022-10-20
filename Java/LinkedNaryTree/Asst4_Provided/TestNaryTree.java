import java.util.Iterator;

public class TestNaryTree {

	public static void main(String[] args) {

		LinkedNaryTree<String> sTree = new LinkedNaryTree<String>();

		// ---------------------------------------------------------------------------
		// Test 1 - Empty tree.
		// ---------------------------------------------------------------------------
		
		if (sTree.toString().equals("Tree is empty.")) {
			System.out.println("Test 1 Passed");
		} else {
			System.out.println("Test 1 Failed");
		}

		// ---------------------------------------------------------------------------
		// Test 2 - size() of a small tree
		// ---------------------------------------------------------------------------
		
		NaryTreeNode<Integer> iRoot = new NaryTreeNode<Integer>(25);
		
		NaryTreeNode<Integer> i1 = new NaryTreeNode<Integer>(11);
		NaryTreeNode<Integer> i2 = new NaryTreeNode<Integer>(-23);
		NaryTreeNode<Integer> i3 = new NaryTreeNode<Integer>(67);
		NaryTreeNode<Integer> i4 = new NaryTreeNode<Integer>(-7);
		NaryTreeNode<Integer> i5 = new NaryTreeNode<Integer>(88);
		NaryTreeNode<Integer> i6 = new NaryTreeNode<Integer>(47);
		NaryTreeNode<Integer> i7 = new NaryTreeNode<Integer>(91);
		NaryTreeNode<Integer> i8 = new NaryTreeNode<Integer>(-34);
		NaryTreeNode<Integer> i9 = new NaryTreeNode<Integer>(15);
		
		iRoot.addChild(i1);
		iRoot.addChild(i2);
		iRoot.addChild(i3);
		
		i1.addChild(i4);
		i1.addChild(i5);
		
		i2.addChild(i6);
		
		i4.addChild(i7);
		i4.addChild(i8);
		i4.addChild(i9);
		
		LinkedNaryTree<Integer> iTree = new LinkedNaryTree<Integer>(iRoot);

		if (iTree.size(iTree.getRoot()) == 10) {
			System.out.println("Test 2 Passed");
		} else {
			System.out.println("Test 2 Failed");
		}
		
		
		// ---------------------------------------------------------------------------
		// Test 3 - sequence of a small tree
		// ---------------------------------------------------------------------------
		
		String seq = getIntegerSequence(iTree);

		if (seq.equals("25 11 -7 91 -34 15 88 -23 47 67 ")) {
			System.out.println("Test 3 Passed");
		} else {
			System.out.println("Test 3 Failed");
		}
		
		
		// ---------------------------------------------------------------------------
		// Test 4 - size() of a large tree
		// ---------------------------------------------------------------------------
		
		NaryTreeNode<String> sRoot = new NaryTreeNode<String>("Earth");
		
		NaryTreeNode<String> c1 = new NaryTreeNode<String>("South America");
		NaryTreeNode<String> c2 = new NaryTreeNode<String>("Asia");
		NaryTreeNode<String> c3 = new NaryTreeNode<String>("Europe");
		NaryTreeNode<String> c4 = new NaryTreeNode<String>("North America");
		
		NaryTreeNode<String> n1 = new NaryTreeNode<String>("Brazil");
		NaryTreeNode<String> n2 = new NaryTreeNode<String>("Colombia");
		NaryTreeNode<String> n3 = new NaryTreeNode<String>("Peru");
		NaryTreeNode<String> n4 = new NaryTreeNode<String>("Venezuela");
		
		NaryTreeNode<String> n5 = new NaryTreeNode<String>("China");
		NaryTreeNode<String> n6 = new NaryTreeNode<String>("India");
		NaryTreeNode<String> n7 = new NaryTreeNode<String>("South Korea");
		
		NaryTreeNode<String> n8 = new NaryTreeNode<String>("Germany");
		NaryTreeNode<String> n9 = new NaryTreeNode<String>("Spain");
		NaryTreeNode<String> n10 = new NaryTreeNode<String>("France");
		NaryTreeNode<String> n11 = new NaryTreeNode<String>("Italy");
		NaryTreeNode<String> n12 = new NaryTreeNode<String>("Portugal");
		
		NaryTreeNode<String> n13 = new NaryTreeNode<String>("Canada");
		NaryTreeNode<String> n14 = new NaryTreeNode<String>("USA");
		NaryTreeNode<String> n15 = new NaryTreeNode<String>("Mexico");

		NaryTreeNode<String> t1 = new NaryTreeNode<String>("Sao Paulo");
		NaryTreeNode<String> t2 = new NaryTreeNode<String>("Madrid");
		NaryTreeNode<String> t3 = new NaryTreeNode<String>("Barcelona");
		NaryTreeNode<String> t4 = new NaryTreeNode<String>("Venice");
		NaryTreeNode<String> t5 = new NaryTreeNode<String>("London");
		NaryTreeNode<String> t6 = new NaryTreeNode<String>("Vancouver");
		NaryTreeNode<String> t7 = new NaryTreeNode<String>("New York");
		
		
		
		sRoot.addChild(c1);
		sRoot.addChild(c2);
		sRoot.addChild(c3);
		sRoot.addChild(c4);
		
		c1.addChild(n1);
		c1.addChild(n2);
		c1.addChild(n3);
		c1.addChild(n4);
		
		c2.addChild(n5);
		c2.addChild(n6);
		c2.addChild(n7);
		
		c3.addChild(n8);
		c3.addChild(n9);
		c3.addChild(n10);
		c3.addChild(n11);
		c3.addChild(n12);
		
		c4.addChild(n13);
		c4.addChild(n14);
		c4.addChild(n15);
		
		n1.addChild(t1);
		n9.addChild(t2);
		n9.addChild(t3);
		n11.addChild(t4);
		n13.addChild(t5);
		n13.addChild(t6);
		n14.addChild(t7);
		
		sTree = new LinkedNaryTree<String>(sRoot);

		if (sTree.size(sTree.getRoot()) == 27) {
			System.out.println("Test 4 Passed");
		} else {
			System.out.println("Test 4 Failed");
		}
		
		
		// ---------------------------------------------------------------------------
		// Test 5 - sequence of a large tree
		// ---------------------------------------------------------------------------

		seq = getStringSequence(sTree);
		
		if (seq.equals("Earth South America Brazil Sao Paulo Colombia Peru Venezuela Asia China India South Korea Europe Germany Spain Madrid Barcelona France Italy Venice Portugal North America Canada London Vancouver USA New York Mexico ")) {
			System.out.println("Test 5 Passed");
		} else {
			System.out.println("Test 5 Failed");
		}

	}
	
	
	private static String getStringSequence (LinkedNaryTree<String> tree) {
		Iterator<String> seq = tree.iteratorPreorder();
		String str = "";
		while (seq.hasNext()) {
			str += seq.next() + " ";
		}
		return str;
	}
	
	private static String getIntegerSequence (LinkedNaryTree<Integer> tree) {
		Iterator<Integer> seq = tree.iteratorPreorder();
		String str = "";
		while (seq.hasNext()) {
			str += seq.next() + " ";
		}
		return str;
	}

}
