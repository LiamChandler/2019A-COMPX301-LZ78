//	Liam Chandler	ID:	1286559
//	Daniel Bartley  ID: 1331132

import java.util.ArrayList;
import java.util.Scanner;

public class LZdecode
{
	static ArrayList<decodeNode> nodes = new ArrayList<>();		// List building the trie
	static ArrayList<Integer> output = new ArrayList<>();		// Data to output

	public static void main(String[] args)
	{
		nodes.add(new decodeNode());		// Create root node in a linked list
		String buff = null;					// Buffer to check content of the next input
		int index;

		Scanner sc = new Scanner(System.in);
		while (sc.hasNext())
		{
			String Char = null;
			if(buff == null)	// If buffer is null read from stream.
				index = Integer.valueOf(sc.nextLine());		// Read next index
			else	//	Else use the value stored in the buffer.
			{
				index = Integer.valueOf(buff);				// Read next index
				buff = null;
			}
			if(sc.hasNext())
			{
				Char = sc.nextLine();						// Read next mismatched character
			}

			if(Char != null)
			{
				if (!Char.equals(""))									// Store character if it exists
					nodes.add(new decodeNode(Char.charAt(0), index));
				else {
					buff = sc.nextLine();								// Otherwise check if needs to store a line feed character or carriage return and store next line in the buffer
					if (!buff.equals(""))
						nodes.add(new decodeNode(0xD, index));
					else {
						nodes.add(new decodeNode('\n', index));
						buff = null;
					}
				}
			}
			else
				nodes.add(new decodeNode(index));		// Create end node for the last pattern in the file
		}

		decodeNode next;
		for(int d = nodes.size()-1; d>=0;d--)		// Loop through every node
		{
			next = nodes.get(d);
			while (!next.isRoot())			// Follow each nodes path
			{
				if(next.isEndNode())
					next = nodes.get(next.dump(false));		// Don't output end nodes character
				else
					next = nodes.get(next.dump(true));			// Print nodes character
			}
		}
		for (int i = output.size()-1; i >=0; i--)		// Loop through output
		{
			System.out.write(output.get(i));		// Print output to file
		}

		System.out.flush();	//	Flush all output to the console.
	}
}

class decodeNode
{
	private int c;									//	Stored character
	private boolean root = false, endNode = false;
	private int parentIndex;

	decodeNode(int C, int Parent)		// Main constructor for non-root/end nodes
	{
		c = C;
		parentIndex = Parent;
	}

	decodeNode(int Parent)				// Constructor for end nodes, where no character appears
	{
		parentIndex = Parent;
		endNode = true;
	}

	decodeNode()		// Constructor for root node
	{
		root = true;
	}

	int dump(boolean Print)		// Add character to the output array
	{
		if(Print)
			LZdecode.output.add(c);
		return parentIndex;				// Returns parent so that path can be followed
	}

	boolean isRoot()
	{
		return root;
	}

	boolean isEndNode()
	{
		return endNode;
	}
}