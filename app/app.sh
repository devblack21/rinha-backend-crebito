#!/bin/bash

java -Xms${JVM_XMS} -Xmx${JVM_XMX} -Duser.timezone=America/Sao_Paulo -Dspring.profiles.active=${PROFILE} -D64 -XX:+UseG1GC \
-XX:MaxRAM=${CONTAINER_MEMORY} -XX:NewSize=${NEW_SIZE} -XX:MaxNewSize=${MAX_NEW_SIZE} \
-XX:MinHeapFreeRatio=${MIN_HEAP_FREE_RATIO} -XX:MaxHeapFreeRatio=${MAX_HEAP_FREE_RATIO} \
-XX:+UnlockExperimentalVMOptions -XX:+UseNUMA -XX:+UseStringDeduplication -jar app.jar --server.port=${PORT}