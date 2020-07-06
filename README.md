# 2019A-COMPX301-LZ78
A set of LZ78 compression and decompression routines

LZencode takes a file as std input and outputs the corisponding LZ78 encoding scheme to std out 
LZpack then packs the encoding scheme using bit code rather than characters to increase compression
LZunpack turns the bit code back to characters
LZdecode decompresses it back into a file

The runAll.sh file runs the set of routines in the correct order saving the intermediate states into files in the correct order resuting in the file being compressed and decompressed.
runSimple.sh does the same as runAll.sh but exculdes the packing/unpacking to test if the encode and decode function correctly

Repository stuctured for [Intellij idea](https://www.jetbrains.com/idea/)
