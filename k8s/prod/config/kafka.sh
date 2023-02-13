helm repo add azure-marketplace https://marketplace.azurecr.io/helm/v1/repo
helm install kafka \
    --set persistence.enabled=false,zookeeper.persistence.enabled=false \
    azure-marketplace/kafka
sleep 15