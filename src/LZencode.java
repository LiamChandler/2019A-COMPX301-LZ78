//	Liam Chandler	ID:	1286559
//	Daniel Bartley  ID: 1331132

import java.util.ArrayList;

public class LZencode
{
	private static encodeNode root = new encodeNode(0);		// Create root node for trie.
	private static encodeNode currNode = root;				// Pointer for the current position in the trie.
	private static int count = 0;							// Count of how many items are within the trie.

	public static void main(String[] args)
	{
		//	Loop until end of file is reached.
		boolean run = true;
		while (run)
		{
			try
			{
				int b = System.in.read();	// Read first byte.

				char S = (char) b;			// Convert to a character.

				// Check if the current character is part of the current node.
				int tmp = currNode.checkIfChar(S);
				if (tmp == -1)
				{	// If not add it to the node and print the node and mismatched character then move back to start
					count++;
					currNode.addNode(count, S);

					System.out.println(currNode.getIndex());
					System.out.println(S);

					currNode = root;
				} else
				{	// If so then move to the node corresponding to the current character.
					currNode = currNode.getNode(tmp);
				}

				//	Check if there is any bytes left in the file.
				if (System.in.available() == 0)
					run = false;	// If not stop loop
			}
			catch (Exception e)
			{
				e.printStackTrace();
				run = false;	// Stop loop if error occurs
			}
		}
		System.out.println(currNode.getIndex());	//	Print the last node that was reached.
		System.out.flush();	// Make sure that all written data is pushed to the console.
	}
}

// Node to be used in a multi-way trie for encoding a LZ78 compression system
class encodeNode
{
	private int index;											//	This nodes index.
	private String c = null;									//	This nodes character
	private ArrayList<encodeNode> children = new ArrayList<>();	//	An ArrayList of this nodes children

	private encodeNode(int Index, char C)
	{
		index = Index;
		c = String.valueOf(C);
	}

	encodeNode(int Index)
	{
		index = Index;
	}

	// Returns the index of a child node that matches the given character if one exists.
	int checkIfChar(char c)
	{
		for (encodeNode n:children)
		{
			if (c==n.getChar())
				return n.getIndex();
		}
		return -1;
	}

	//	Returns the child with the given index.
	encodeNode getNode(int index)
	{
		for (encodeNode n:children)
		{
			if(n.getIndex()== index)
				return n;
		}
		return null;
	}

	// Add a node with the given info to the current node.
	void addNode(int index, char c)
	{
		children.add(new encodeNode(index,c));
	}

	//	Returns the value of the character
	private char getChar()
	{
		return c.charAt(0);
	}

	//	Returns the value of the index
	int getIndex()
	{
		return index;
	}
}
