//	Liam Chandler	ID:	1286559
//	Daniel Bartley  ID: 1331132

public class LZpack
{
	public static void main(String[] args)
	{
		int count = 0;
		int numBits, inIndex;
		bitPacker root = new bitPacker();	// Create first byte to be packed into
		bitPacker last = null;
		try
		{
			boolean run = true;
			while (run)			// While there is input
			{
				numBits = log(count);			// Number of bits required to store the max possible value of the index

				int b = System.in.read();		// Reading index
				String tmp = "";
				while (b != '\n')
				{
					tmp = tmp + (b - 48);		// Converting value from ascii
					b = System.in.read();		// Reading next digit of index
				}
				if (tmp.isEmpty())
					inIndex = 0x0;	// Set inIndex to 0 if tmp doesn't have a value otherwise
				else
					inIndex = Integer.valueOf(tmp);		// Else set it to the int value of tmp

				if (System.in.available() == 0)		// If there is no character to read
				{
					run = false;		// Stop the loop repeating
					last.pack(inIndex, numBits);		// Pack the index into the last bytes
				}
				else
				{
					char inByte = (char) System.in.read();		// Read the next character
					System.in.read();							// Skip new line character
					if(last==null)
					{
						last = root.pack(inIndex, numBits);		// Pack index
						root.pack(inByte);						// Pack byte
					}
					else
					{
						last = last.pack(inIndex, numBits);	// Pack index
						last.pack(inByte);				// Pack byte
					}
				}
				count++;
			}

			bitPacker tmp = root;
			while (tmp != null)
			{
				tmp = tmp.print();        // Print bytes to file, starting with the root
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.flush();
	}

	static int log(int x)		// Finds the log of a value to the base 2
	{
		if (x <= 0)
			return 1;
		return (int) (Math.log(x) / Math.log(2)) + 1;
	}
}

class bitPacker
{
	int pointer = 8;		// Which bit is being pointed to
	byte info = 0x00;		// Content of current byte
	bitPacker child = null;		// Next byte

	bitPacker(){}

	bitPacker pack(int i,int len)		// Packs an int, i, of number of bits, len, into the current byte
	{
		if(child ==null)		// If this is the newest byte
		{
			int tmp;
			if (pointer >= len)							// If number fits in current byte
				tmp = (byte)(i << (pointer - len));		// Shifts number into next free bit
			else
			{
				tmp = i >>> (len - pointer);		// Shifts number into next free bit
				child = new bitPacker();			// Creates the next byte
				child.pack(i, len - pointer);	// Packs remaining bits of the number into the next byte
			}
			info = (byte) (info | tmp);		// Store the number in the info
			pointer -= len;					// Shift the pointer
			return this;
		}
		else
		{
			return child.pack(i,len);		// Current byte is full, check next
		}
	}

	bitPacker pack(char b)		// Packs a character into the byte
	{
		return pack(b,8);
	}

	bitPacker print()		// Outputs the packed bytes
	{
		System.out.write(info);
		return child;
	}
}