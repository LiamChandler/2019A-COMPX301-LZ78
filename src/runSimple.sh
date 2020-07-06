echo "Running LZencode"
java LZencode < BMPIMAGE.BMP > outputEncode.txt
echo "Finished LZencode now running LZdecode"
java LZdecode < outputEncode.txt > outputDecode.BMP
echo "Finished LZdecode"