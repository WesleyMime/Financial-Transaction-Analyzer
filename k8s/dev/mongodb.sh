helm repo add azure-marketplace https://marketplace.azurecr.io/helm/v1/repo
helm install mongodb --values mongodb.yaml azure-marketplace/mongodb