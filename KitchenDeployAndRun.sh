echo "Transfering data to the kitchen node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp dirKitchen.zip sd105@l040101-ws05.ua.pt:test/Restaurant
echo "Decompressing data sent to the kitchen node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'cd test/Restaurant ; unzip -uq dirKitchen.zip'
echo "Executing program at the kitchen node."
sshpass -f password ssh sd105@l040101-ws05.ua.pt 'cd test/Restaurant/dirKitchen ; java serverSide.main.ServerKitchen 22001 l040101-ws05.ua.pt 22000'
