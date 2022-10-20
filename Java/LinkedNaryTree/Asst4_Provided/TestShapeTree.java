import java.util.Iterator;

public class TestShapeTree {

	public static void main(String[] args) {

		ShapeTree tree;
		boolean status;
		String seq;
		
		// ---------------------------------------------------------------------------
		// Test 1 - checkNode with exceeding max children
		// ---------------------------------------------------------------------------
		
		tree = new ShapeTree();
		
		NaryTreeNode<Shape> node = new NaryTreeNode<Shape>(new Shape("circle", "blue"));
		node.addChild(new NaryTreeNode<Shape>(new Shape("square", "yellow")));
		status = tree.checkNode(node, new Shape("triangle", "red"));
		if (!status) {
			System.out.println("Test 1 Passed");
		} else {
			System.out.println("Test 1 Failed");
		}
		
		// ---------------------------------------------------------------------------
		// Test 2 - checkNode with child colour matching parent colour
		// ---------------------------------------------------------------------------
		node = new NaryTreeNode<Shape>(new Shape("square", "blue"));
		
		status = tree.checkNode(node, new Shape("triangle", "blue"));
		
		if (!status) {
			System.out.println("Test 2 Passed");
		} else {
			System.out.println("Test 2 Failed");
		}
		
		// ---------------------------------------------------------------------------
		// Test 3 - checkNode with siblings of matching colour
		// ---------------------------------------------------------------------------
		
		node.addChild(new NaryTreeNode<Shape>(new Shape("triangle", "green")));
		status = tree.checkNode(node, new Shape("circle", "green"));
		
		if (!status) {
			System.out.println("Test 3 Passed");
		} else {
			System.out.println("Test 3 Failed");
		}
		
		// ---------------------------------------------------------------------------
		// Test 4 - checkNode with no rules broken
		// ---------------------------------------------------------------------------
		
		status = tree.checkNode(node, new Shape("circle", "purple"));
		
		if (status) {
			System.out.println("Test 4 Passed");
		} else {
			System.out.println("Test 4 Failed");
		}
		
		// ---------------------------------------------------------------------------
		// Test 5 - checkNode with no rules broken
		// ---------------------------------------------------------------------------
				
		status = tree.checkNode(node, new Shape("triangle", "yellow"));
				
		if (status) {
			System.out.println("Test 5 Passed");
		} else {
			System.out.println("Test 5 Failed");
		}
		
		
		// ---------------------------------------------------------------------------
		// Test 6 - creating a ShapeTree
		// ---------------------------------------------------------------------------
		tree = new ShapeTree();
		tree.addShapeNode(new Shape("star", "red"));
		
		//printTree(tree);
		
		seq = getSequence(tree);
		
		if (seq.equals("5 red ")) {
			System.out.println("Test 6 Passed");
		} else {
			System.out.println("Test 6 Failed");
		}
		
		
		
		// ---------------------------------------------------------------------------
		// Test 7 - creating a ShapeTree
		// ---------------------------------------------------------------------------
		
		tree.addShapeNode(new Shape("square", "red"));
		tree.addShapeNode(new Shape("circle", "red"));
		tree.addShapeNode(new Shape("triangle", "red"));
		
		//printTree(tree);
		
		seq = getSequence(tree);

		if (seq.equals("5 red ")) {
			System.out.println("Test 7 Passed");
		} else {
			System.out.println("Test 7 Failed");
		}
		
		
		// ---------------------------------------------------------------------------
		// Test 8 - creating a ShapeTree
		// ---------------------------------------------------------------------------
		
		tree = new ShapeTree();
		tree.addShapeNode(new Shape("circle", "blue"));
		tree.addShapeNode(new Shape("triangle", "red"));
		tree.addShapeNode(new Shape("star", "yellow"));
		tree.addShapeNode(new Shape("circle", "green"));
		tree.addShapeNode(new Shape("square", "purple"));
		tree.addShapeNode(new Shape("triangle", "blue"));
		
		//printTree(tree);
		
		seq = getSequence(tree);

		if (seq.equals("1 blue 3 red 5 yellow 3 blue 1 green 4 purple ")) {
			System.out.println("Test 8 Passed");
		} else {
			System.out.println("Test 8 Failed");
		}
		
		
		
		// ---------------------------------------------------------------------------
		// Test 9 - creating a ShapeTree
		// ---------------------------------------------------------------------------
		tree = new ShapeTree();
		tree.addShapeNode(new Shape("triangle", "yellow"));
		tree.addShapeNode(new Shape("square", "red"));
		tree.addShapeNode(new Shape("circle", "purple"));
		tree.addShapeNode(new Shape("star", "red"));
		tree.addShapeNode(new Shape("triangle", "purple"));

		//printTree(tree);
		
		seq = getSequence(tree);

		if (seq.equals("3 yellow 4 red 3 purple 1 purple 5 red ")) {
			System.out.println("Test 9 Passed");
		} else {
			System.out.println("Test 9 Failed");
		}
		
		
		// ---------------------------------------------------------------------------
		// Test 10 - creating a ShapeTree
		// ---------------------------------------------------------------------------
		
		tree = new ShapeTree();
		tree.addShapeNode(new Shape("star", "purple"));
		tree.addShapeNode(new Shape("circle", "purple"));
		tree.addShapeNode(new Shape("square", "blue"));
		tree.addShapeNode(new Shape("square", "purple"));
		tree.addShapeNode(new Shape("circle", "blue"));
		tree.addShapeNode(new Shape("triangle", "yellow"));
		tree.addShapeNode(new Shape("star", "green"));
		tree.addShapeNode(new Shape("circle", "blue"));
		tree.addShapeNode(new Shape("square", "blue"));
		tree.addShapeNode(new Shape("star", "blue"));
		
		//printTree(tree);
		
		seq = getSequence(tree);

		if (seq.equals("5 purple 4 blue 4 purple 1 blue 3 yellow 1 blue 5 green 4 blue ")) {
			System.out.println("Test 10 Passed");
		} else {
			System.out.println("Test 10 Failed");
		}
	}
	
	private static String getSequence (ShapeTree tree) {
		Iterator<Shape> seq = tree.getTree().iteratorPreorder();
		String str = "";
		while (seq.hasNext()) {
			str += seq.next() + " ";
		}
		return str;
	}
	
	
	private static void displayNode (NaryTreeNode<Shape> node, int depth) {
		for (int i = 0; i < depth; i++) {
			System.out.print("\t");
		}
		System.out.println(node);
		for (int i = 0; i < node.getNumChildren(); i++) {
			NaryTreeNode<Shape> c = node.getChild(i);
			displayNode(c, depth+1);
		}
	}
	
	public static void printTree (ShapeTree tree) {
		displayNode(tree.getRoot(), 0);
	}

}
