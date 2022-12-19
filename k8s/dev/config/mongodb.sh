helm repo add azure-marketplace https://marketplace.azurecr.io/helm/v1/repo
helm install mongodb \
    --set auth.rootPassword=secretpassword,auth.username=secret-user,auth.password=secret-password,auth.database=fta \
    azure-marketplace/mongodb