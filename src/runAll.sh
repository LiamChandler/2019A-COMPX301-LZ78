echo "Running LZencode"
java LZencode < BrownCorpus.txt > outputEncode.txt
echo "Finished running LZencode now running LZpack"
java LZpack < outputEncode.txt > outputPack.txt
echo "Finished running LZpack now running LZunpack"
java LZunpack < outputPack.txt > outputUnpack.txt
echo "Finished running LZunpack now running LZdecode"
java LZdecode < outputUnpack.txt > outputDecode.txt
echo "Finished running LZdecode"