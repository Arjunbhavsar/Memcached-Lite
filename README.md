# Memcached-Lite

### Background: 
1)	The memcached- lite is the Server which will provide the stable environment to save and retrieve data. 
2)	It will save the (Key, Value) pair and maintain the same repository for the all the clients connected to the server. 
3)	Server communicates with clients using TCP as well as UDP connections.
4)	In this assignment uses TCP connection to communicate with server. 
5)	It only allows the key value pair for get and set Commands.
6)	There are multiple parameter for both the tags 
A.	Set – It Has Key, Byte Size , Valid time and the final value as parameters
B.	Get – It has Key as parameter which will provide the value for that particular key if available.
