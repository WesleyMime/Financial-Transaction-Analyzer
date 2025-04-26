helm install kafka \
    --set persistence.enabled=false,zookeeper.persistence.enabled=false \
    oci://registry-1.docker.io/bitnamicharts/kafka
sleep 15