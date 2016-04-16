# HouseKeeper
A java application made to handle the boring parts of college.
* Your attendance.
* Current Aggregate Score.
* Time Tables.
* and So much more....

### Current Features
* [x] **A multithreaded server-client system**
* [x] **A packet system that allows for parsing of different types Of files(Timetables, Text, StudentInfo etc.)**  
* [x] **A Database Management System**
  - Student Info
  - Time Tables
  - Attendance
  - Percentage
* [x] **A Private Chat System**
  - Connected clients can private message each other.
  - Check what users are connected
* [ ] **GUI**
  - Making in JavaFX

# Overview

 The codebase is roughly divided into two parts.
 - The Server
 - The Client

## Server
The server is an entity that allows multiple clients to connect to it and exchange meaningful information.
All database operations occur through the server.
The server performs the following functions:
- Authentication of Client
- Reading/Writing into The Database with regard to client's information
- Provides a platform for communication between different clients


The server is multithreaded in nature ,which means that multiple clients can connect to it and each client is given its separate "instance" of the server.
In layman's terms it means that a prospective client does not have to wait for the preceding client to finish its work. Both the client's preform their operations in a parallel fashion.

Here is a comprehensive list of the functions in the Server directory and their use:
### Server Class
### Variables
1. **Port**:
The port on which server is instantiated.
2. **Clients**:
A list of Connected Client Socket connections.
3. **connectedUsers**:
 A subset of the connected clients ,containing just the roll numbers of the clients.
4. **server**:
A Java Server Socket variable which is the main server variable.
5. **clientSocket**:
The socket of the client that connects.
6. **runningThread**:
 The thread that runs listens for incoming connections.
7. **threadPool**:
This consists of an array of threads that are ready and primed for incoming connections. Whenever a client clients, a worker thread from this thread pool array is given to the socket connection for it's further communication. Right now the default value is 10 threads. Could be extended to the 1000's. A benefit of a thread pool is that whenever a thread is finished with its work it returns to this array. This ensures that more threads than the prescribed limit are not used which could potentially crash the P.C.

#### 1. Server Constructor
Instantiates the server object. Starts the serverSocket on prescribed *port* and instantiates the JDBC connection to **mySQL**.

Gives this socket connection to the main listening thread called "Server" and Starts the thread.

#### 2. run()
This function is automatically run when a thread is started. So whenever server Socket is started , this function runs.
It runs an infinite while loop which continue-sly listens in for client connection requests in the form as the **waitForConnection()** function.

#### 3. waitForConnection()

This function listens for incoming conn. on getting a new connection it accepts it, and instantiates the clientConnect object. Which handles all further client communication.
This clientConnect object which contains the clientSocket is given to a worker thread plucked from the thread pool.

#### 4. findClientConnection(Roll Number)

A helper function used to find out the clientConnect object of a specific roll number.
Useful for private Messaging.

#### 5. addtoConnectedClientList(ClientConnect object)

Adds client to the connectedUsers List. Again, useful for messaging and broadcast facility.


### ClientConnect Class

## Coming Soon..
