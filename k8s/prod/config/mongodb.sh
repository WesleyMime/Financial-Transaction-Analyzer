helm install mongodb \
    --set architecture=replicaset,replicaCount=1,auth.rootPassword=,auth.username=,auth.password=,auth.database=fta \
    oci://registry-1.docker.io/bitnamicharts/mongodb
sleep 15