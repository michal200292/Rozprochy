import socket;

serverPort = 9008
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind(('', serverPort))
buff = []

print('PYTHON UDP SERVER')

while True:

    buff, address = serverSocket.recvfrom(1024)
    rec_msg = str(buff, 'cp1250')
    print("python udp server received msg: " + rec_msg)

    msg = "Ping " + ("python" if rec_msg[0] == 'P' else "java") 
    serverSocket.sendto(bytes(msg, 'utf-8'), (address[0], 9009))



