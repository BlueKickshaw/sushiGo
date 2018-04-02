AUTHORS:
	Colin	Shaw	
	Connor 	Henderson
	Jon	Perry
	
=================================================
INSTALLATION:
	Visit https://github.com/bluekickshaw/sushigo 
	From the repository you have two options:
		1) For the whole project, clone the repository
		2) To ues the application download the .jar file, and run it from the terminal with "java -jar sushigo.jar"
		3)	java -cp sushigo.jar server.Main

USAGE:
	When starting the application, there are two pathways that a user may choose to take. The first path is that of a server; the user may choose to start a server on their machine. Open lobbies will be displayed, as well as the amount of users currently in the lobby. When a lobby is started, it will be removed from the servers lobby list (as it will have migrated off the server). Additionally, the amount of users on the server will be displayed. If the server wishes to end the server, they should press the end server button. If the user tries to create a server when the port is in use, then the program will display a message and terminate. The primary server uses the ports 8080, but the the users will attempt to create lobbies over an available port in the range 8080-8090, so it would be best to ensure that any/all of these are forwarded on your machine.	
	If the user decides to become join a server, they will have an option of choosing the IP of the server they would like to join. By default the user will attempt to join a server that is created on their own machine; if they are joining a server on their local network then they should take care to join by that by entering the local IP of the host (this will have to be obtained from the host themselves). If the user is joining a server that is being hosted
	
NOTE:
	
