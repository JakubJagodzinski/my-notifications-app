# My notifications app

A simple notifications application in client-server architecture written in Java.

## Contents
- [Server application](#server-application)
  - [Server welcome message](#server-welcome-message)
  - [Server info](#server-info)
  - [Print server time statistics](#print-server-time-statistics)
  - [List all connected clients](#list-all-connected-clients)
  - [List all queued requests](#list-all-queued-requests)
  - [Sample server logs](#sample-server-logs)
  - [Server shutdown](#server-shutdown)
- [Client application](#client-application)
  - [Main menu](#main-menu)
  - [Sending notification](#sending-notification)
  - [Show notifications history](#show-notifications-history)
- [Version](#version)
- [Author](#author)

## Server application
Server accepts requests from clients, then the requests are queued and resend to clients at desired time.
### Server welcome message
![Server](screenshots/server/server.png)

### Server info
![Server info](screenshots/server/server_info.png)

### Print server time statistics
![Time](screenshots/server/time.png)

### List all connected clients
![Clients](screenshots/server/clients.png)

### List all queued requests
![Requests](screenshots/server/requests.png)

### Sample server logs
![Logs](screenshots/server/logs.png)

### Server shutdown
![Server shutdown](screenshots/server/shutdown.png)

## Client application
Client can send notification request to server in following format:
<br>
message_content
<br>
dd:MM:yyyy HH.mm.ss

### Main menu
![Client menu](screenshots/client/client_menu.png)

### Sending notification
![Request send](screenshots/client/request_sent.png)

### Show notifications history
Client can see all its previously received notifications and also clear it
![History empty](screenshots/client/history_empty.png)
<br><br>
![History](screenshots/client/history.png)

## Version
1.0.0

## Author
Jakub Jagodziński
