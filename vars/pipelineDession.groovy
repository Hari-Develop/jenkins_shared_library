#!groovy

def decidePipeline(Map configMap){
    application = configMap.get("application")
    switch(application) {
        case "nodejs":
            nodejs(configMap) 
            break
        case "java":
            java(configMap)
            break
        case "nodeEKS":
            nodeEKS(configMap)
            break
        default:
            error "The application pipline is not found"
    }
}
