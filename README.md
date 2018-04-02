AUTHORS:
	Colin	Shaw	
	Connor 	Henderson
	Jon	Perry
	
=================================================
INSTALLATION:
	Visit https://github.com/bluekickshaw/sushigo 
	From the repository you have two options:
		1) For the whole project, clone the repository
		2) Run "gradle build" to build the project
			-> this places the resources in the wrong area due to misconfiguration in the build.gradle file,
			and unfortunately could not be corrected in time; a working .jar artifact generated from intelliJ has
			been provided alongside semi-working build.gradle file
		2) To ues the application download the .jar file, and run it from the terminal with "java -jar sushigo.jar"

USAGE:
	When starting the application, there are two pathways that a user may choose to take. The first path is that of a server; 
	the user may choose to start a server on their machine. Open lobbies will be displayed, as well as the amount of users 
	currently in the lobby. When a lobby is started, it will be removed from the servers lobby list (as it will have migrated 
	off the server). Additionally, the amount of users on the server will be displayed. If the server wishes to end the 
	server, they should press the end server button. If the user tries to create a server when the port is in use, then the 
	program will display a message and terminate. The primary server uses the ports 8080, but the the users will attempt to 
	create lobbies over an available port in the range 8080-8090, so it would be best to ensure that any/all of these are 
	forwarded on your machine.	

	If the user decides to become join a server, they will have an option of choosing the IP of the server they would like to 
	join. By default the user will attempt to join a server that is created on their own machine; hosting a lobby on a machine 
	that is running the server will cause the server to only send the host's InetAddress, a user should take care to only host 
	a server and a lobby on the same machine if they intend to be the only person running that particular lobby, for the sake 
	of testing for example. 
	
NOTE:
	Colin worked on setting up initial classes(player, cards, hand), majority of calculate points methods, calculating
	total points, GameDriver, and gamedriver meshing.

	Connor worked on server/network logic/UI as well as game driver meshing.

	Jon worked on the games UI/controllers, Turn, Deck, calculateMakiRolls, gamedriver meshing.

	On Friday march 30, Colin and Jon worked in pair programming with Jon pushing the code. On Saturday March 31st and
	Sunday April 1st, we all worked within the same room putting everything together, fixing bugs as they came up, and
	discussing/trying strategies to fix problems. Any work done in these two days, although not in the commit
	messages, was done with the 3 of us doing "pair" programming.


BUGS & LIMITATIONS: 
	1. Chopsticks did not make it into game by the deadline. It was prioritized to get everything meshed together as well as
	solve any base problems prohibiting the basic functionality of the game
	2. Occasionally a card slot will become 'corrupted' and permanently display as empty for the duration of the game,
	despite the rest of the game treating it as if there were a functioning card underneath.This bug appeared recently and is 
	inconsistent, so we've had difficulty pinning down the underlying cause. It appears more frequently when larger amount of 
	UI elements are on the screen, so it would be worth investigating whether or not this happens when played between two 
	clients when not being run locally. Unfortunately due to time constraints and the recent nature of this bug, we've been 
	unable to squash it.
	3.
	

