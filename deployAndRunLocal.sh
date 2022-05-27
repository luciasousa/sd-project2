cd /home/lucia/test/Restaurant/dirGeneralRepos/
xterm  -T "General Repository" -hold -e "java serverSide.main.ServerGeneralRepos 22153" &
cd /home/lucia/test/Restaurant/dirKitchen/
xterm  -T "Kitchen" -hold -e "java serverSide.main.ServerKitchen 22152 localhost 22153" &
cd /home/lucia/test/Restaurant/dirTable/
xterm  -T "Bar" -hold -e "java serverSide.main.ServerTable 22151 localhost 22153" &
cd /home/lucia/test/Restaurant/dirBar/
xterm  -T "Table" -hold -e "java serverSide.main.ServerBar 22154 localhost 22153 localhost 22151 localhost 22152" &
sleep 1
cd /home/lucia/test/Restaurant/dirChef/
xterm  -T "Chef" -hold -e "java clientSide.main.ClientChef localhost 22153 localhost 22152 localhost 22154" &
cd /home/lucia/test/Restaurant/dirStudent/
xterm  -T "Student" -hold -e "java clientSide.main.ClientStudent localhost 22153 localhost 22151 localhost 22154" &
sleep 1
cd /home/lucia/test/Restaurant/dirWaiter/
xterm  -T "Waiter" -hold -e "java clientSide.main.ClientWaiter localhost 22153 localhost 22152 localhost 22151 localhost 22154" &