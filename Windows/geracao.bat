cscript backup.vbs
rd Positivas /s /q
rd Negativas /s /q
cd Imagens
  dir /b /s *.BMP >Imagens.txt
  move Imagens.txt ..
cd ..
mkdir Positivas
mkdir Negativas
java -jar CropAndPaintBlack.jar -file "Imagens.txt" -lote 5
Data\createsamples.exe -info positivas.txt -vec vector.vec -num 204 -w 24 -h 24
Data\haartraining.exe -data Cascades -vec vector.vec -bg negativas.txt -npos 200 -nneg 2000 -nstages 15 -mem 1024 -mode ALL -w 24 -h 24 
pause