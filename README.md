# DistributedChatRMI

A Java-based object-oriented distributed chat application using **Remote Method Invocation (RMI)**. This project is designed as part of laboratory practices to introduce distributed systems concepts and their implementation.

## Project Structure

```
DistributedChatRMI/
├── .git                # Version control metadata
├── .idea               # IntelliJ IDEA configuration files
├── faces/              # Contains RMI interfaces
├── impl/               # Implementations of the RMI interfaces
├── lib/                # External libraries for UI enhancements
├── out/                # Compiled class files
├── ui/                 # User interface (GUI) code for chat clients
├── utils/              # Utility classes for application logic
├── utils_rmi/          # RMI-specific utilities for easier configuration
├── ChatClient.java     # Chat client implementation with GUI
├── ChatRobot.java      # Autonomous chat bot logic
├── ChatServer.java     # Main chat server implementation
├── NameServer.java     # Centralized name server for locating services
├── LEEME-RMI.TXT       # Project notes (in Spanish)
└── DistributedChatRMI.iml  # IntelliJ project metadata
```

## Components

The application consists of three main components:
1. **NameServer**: A service that allows clients and servers to register and locate remote objects.
2. **ChatServer**: Manages chat channels and connected users.
3. **ChatClient**: A GUI-based client that enables users to join channels, send messages, and interact with other users.

### Optional Component
- **ChatRobot**: A bot that connects to a channel and responds automatically to specific messages.

## Prerequisites

1. **Java Development Kit (JDK)** installed (version 8 or above).
2. **RMI Registry**: Ensure the `rmiregistry` service is running.
3. **IntelliJ IDEA** (optional) for easier code navigation and project management.

## Installation and Setup

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd DistributedChatRMI
   ```

2. Compile the source files:
   ```bash
   javac -d out/ $(find . -name "*.java")
   ```

3. Start the RMI Registry (in a separate terminal):
   ```bash
   rmiregistry
   ```

4. Launch components in separate terminals as described below.

## Usage

### 1. Start the Name Server
Run the NameServer with a specified port (default: 9000):
```bash
java -cp out/ NameServer port=9000
```

### 2. Start the Chat Server
Run the ChatServer, specifying the NameServer details:
```bash
java -cp out/ ChatServer nsport=9000 serverName="TestServer"
```

### 3. Start Chat Clients
Run a ChatClient, specifying the NameServer and ChatServer details:
```bash
java -cp out/ ChatClient nsport=9000 serverName="TestServer" nick="User1"
```

Repeat for additional clients with unique nicknames.

### 4. Optional: Start a Chat Bot
Run a ChatRobot to simulate automated responses:
```bash
java -cp out/ ChatRobot nsport=9000 serverName="TestServer" channelName="#General" nick="Bot"
```

## Features

- **Distributed Communication**: Connect multiple clients to interact via a centralized server.
- **Channels**: Create and join chat rooms for group communication.
- **Private Messaging**: Send direct messages between users.
- **Extensibility**: Add bots or other automated systems using the provided API.

## Notes

- Ensure each component (NameServer, ChatServer, and ChatClients) runs in its terminal window.
- For testing in distributed setups, use `ssh` to deploy components across different machines.
- Refer to the `LEEME-RMI.TXT` file for further details and troubleshooting.

## Troubleshooting

1. **Port in use**: Ensure no other service is running on the specified port.
2. **RMI registry not found**: Verify the `rmiregistry` is running before launching components.
3. **Connection issues**: Check that all components use consistent `nsport` and `serverName` values.

## License

This project is for educational purposes and follows academic usage guidelines.
