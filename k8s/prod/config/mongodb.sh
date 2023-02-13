helm install mongodb \
    --set architecture=replicaset,replicaCount=1,auth.rootPassword=,auth.username=,auth.password=,auth.database=fta \
    azure-marketplace/mongodb
sleep 15