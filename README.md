# client-server-file-transfer

It uses socket programming. Client requests a file from server and server responds with the contents of the file.

How to Run:
1. Execute WebServer.java. Server starts listening on port 1010.
2. Execute SocketClient.java. Enter the method i.e. Get or Post.
3. Then enter filename with path which you want to send to server.
4. Server will send output with contents of file and status to the console.

How code works:
When you start server, it starts listening on port 1010.
Then client enter the method name and path of the file. 
Client sends encoded filename to server if the method is POST. Else it sends filename as it is.
Then server extracts the method and filename from the message.
if the method is post then server decodes the filename and sends the content of the 
file to the client. If the method is post then server doesnt decode the filename.
