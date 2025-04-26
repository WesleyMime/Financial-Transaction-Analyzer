helm install mongodb \
    --set auth.rootPassword=secretpassword,auth.username=secret-user,auth.password=secret-password,auth.database=fta \
    oci://registry-1.docker.io/bitnamicharts/mongodb