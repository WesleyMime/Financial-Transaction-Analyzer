helm install kafka \
    --set persistence.enabled=false,zookeeper.persistence.enabled=false,listeners.client.protocol=plaintext,listeners.controller.protocol=plaintext,listeners.interbroker.protocol=plaintext \
    oci://registry-1.docker.io/bitnamicharts/kafka
sleep 15