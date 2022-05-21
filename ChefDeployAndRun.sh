echo "Transfering data to the chef node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp dirChef.zip sd105@l040101-ws04.ua.pt:test/Restaurant
echo "Decompressing data sent to the chef node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'cd test/Restaurant ; unzip -uq dirChef.zip'
echo "Executing program at the chef node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'cd test/Restaurant/dirChef ; java clientSide.main.ClientChef l040101-ws06.ua.pt 22001 l040101-ws07.ua.pt 22000 stat 3'
