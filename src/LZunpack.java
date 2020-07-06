//	Liam Chandler	ID:	1286559
//	Daniel Bartley  ID: 1331132

import java.util.ArrayList;

public class LZunpack
{	// Define global variables
	static int currentByte = 0, count = 0, numBits, currentBytePointer = 8;
	static ArrayList<Byte> bytes = new ArrayList<Byte>();	// List for all bytes in input.

	public static void main(String[] args)
	{
		try
		{	//	While there is a next byte read it into the ArrayList.
			boolean run = true;
			while (run)
			{
				bytes.add((byte) System.in.read());	// Add the next byte
				if (System.in.available() == 0)		// Check if there is another byte to be read
					run = false;
			}

			//	Loop until all bytes have been processed
			while (currentByte < bytes.size()-3)
			{
				numBits = log(count);	//	Calculate the min bits for the index

				int tmp = readByte(numBits);	//	Get the index from the byte array

				String index =String.valueOf(tmp);	//	Convert the index into characters

				char Char =(char) (readByte(8));	// Read the mismatched byte

				//	Print the read data to the console.
				System.out.println(index);
				System.out.println(Char);

				count++;	//	Increment the count.
			}
			//	Print the last index out with no mismatched byte.(prevent null characters)
			String index =String.valueOf((readByte(numBits)));
			System.out.println(index);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.flush();	//	Flush all printed data to the console.
	}

//	Calculates the log2 of the given number.
	static int log(long x)
	{
		if (x <= 0)	//	If not given a valid input return 1.
			return 1;
		return (int) (Math.log(x) / Math.log(2))+1;	//	Else return the calculated value
	}

//	Returns the information contained in the next bits defined by the length given.
	static int readByte(int length)
	{
		int tmp = bytes.get(currentByte);	// Make a local copy of the current byte

		//	Create a mask to prevent un-wanted information getting into the output
		int mask = 0;
		for (int i = length; i > 0; i--)
		{
			mask = mask << 1;	//	Slide left by one
			mask++;				//	Set least most significant bit to 1.
		}

		if (length < currentBytePointer)	//	If all of the data requested is contained within the current byte calculate it and return the value
		{
			tmp =((tmp >>> (currentBytePointer - length)) & mask);
			currentBytePointer -= length;
		} else	//	Else get what remains of the current byte and add the remaining requested amount from the next byte.
		{
			tmp =(tmp << (length - currentBytePointer));	//	Get what remains of this byte.
			int tmpTmp = currentBytePointer;				//	Save the byte pointer
			currentBytePointer = 8;							//	Reset the byte pointer

			if(currentByte < bytes.size()-1)				//	Check if there is a next byte
			{
				currentByte++;	//	Move pointer to next byte
				tmp = (tmp | readByte(length - tmpTmp)) & mask;	//	Get and add the next chunk of information to the other information.
			}
		//	Else leave the current information as is with remaining bits as "0"'s
		}
		return tmp;
	}
}
