cscript backup.vbs
rd Positivas /s /q
rd Negativas /s /q
dir "Imagens" /b /s *.BMP >Imagens.txt
mkdir Positivas
mkdir Negativas
java -jar CropAndPaintBlack.jar "Imagens.txt"
pause