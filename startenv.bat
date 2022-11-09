del /F /S /q D:\kafka_2.13-3.2.3\log
start /b D:\apache-zookeeper-3.8.0-bin\bin\zkServer.cmd
timeout /t 3
start /b D:\kafka_2.13-3.2.3\bin\windows\Kafka-server-start.bat D:\kafka_2.13-3.2.3\config\server.properties