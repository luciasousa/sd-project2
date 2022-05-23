echo "Transfering data to the student node."
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp dirStudent.zip sd105@l040101-ws08.ua.pt:test/Restaurant
echo "Decompressing data sent to the student node."
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'cd test/Restaurant ; unzip -uq dirStudent.zip'
echo "Executing program at the student node."
sshpass -f password ssh sd105@l040101-ws08.ua.pt 'cd test/Restaurant/dirStudent ; java clientSide.main.ClientStudent l040101-ws07.ua.pt 22000 l040101-ws04.ua.pt 22001 l040101-ws05.ua.pt 22001'
