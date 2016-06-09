set objFSO = CreateObject("Scripting.FileSystemObject")
pastaBackup = "Backup"
positivaOriginal = "Positivas"
negativaOriginal = "Negativas"
if objFSO.FolderExists(positivaOriginal)=false then
  objFSO.CreateFolder pastaBackup
end if
if objFSO.FolderExists(positivaOriginal)=false then
  objFSO.CreateFolder positivaOriginal
end if
if objFSO.FolderExists(negativaOriginal)=false then
  objFSO.CreateFolder negativaOriginal
end if
dNow = Now
ano = Right(Year(dNow),4)
mes = Right("00" & Month(dNow),2)
dia = Right("00" & Day(dNow),2)
hora = Right("00" & Hour(dNow),2)
minuto = Right("00" & Minute(dNow),2)
Segundo = Right("00" & Second(dNow),2)
novaPasta = pastaBackup & "\" & ano & "-" & mes & "-" & dia & "-" & hora & "-" & minuto & "-" & segundo & "\"
novaPositiva = novaPasta & "Positivas/"
novaNegativa = novaPasta & "Negativas/"
objFSO.CreateFolder novaPasta 
objFSO.CreateFolder novaPositiva 
objFSO.CreateFolder novaNegativa
objFSO.CopyFolder positivaOriginal,novaPasta,true
objFSO.CopyFolder negativaOriginal,novaPasta,true