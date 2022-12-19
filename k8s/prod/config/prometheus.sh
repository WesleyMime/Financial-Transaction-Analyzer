helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm install prometheus prometheus-community/kube-prometheus-stack

# Config to access grafana via external-ip
kubectl patch svc prometheus-grafana -p '{"spec": {"type": "LoadBalancer"}}'