import socket;

print('PYTHON UDP CLIENT')

serverIP = "127.0.0.1"
serverPort = 9008

rec_port = 9009
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind(('', rec_port))


msg = 'Python'
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'utf-8'), (serverIP, serverPort))

buff, address = serverSocket.recvfrom(1024)
print("python udp server received msg: " + str(buff.decode()))




