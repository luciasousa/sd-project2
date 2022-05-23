echo "Transfering data to the table node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp dirTable.zip sd105@l040101-ws04.ua.pt:test/Restaurant
echo "Decompressing data sent to the table node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'cd test/Restaurant ; unzip -uq dirTable.zip'
echo "Executing program at the table node."
sshpass -f password ssh sd105@l040101-ws04.ua.pt 'cd test/Restaurant/dirTable ; java serverSide.main.ServerTable 22001 l040101-ws07.ua.pt 22000'
