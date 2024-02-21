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

def decidePipeline(Map configMap){
    application = configMap.get("application")
    switch(application) {
        case "nodejs":
            nodejs(configMap)
            break
        case "javaVm":
            javaVm(configMap)
            break
        case "pythonVm":
            pythonVM(configMap)
            break
        default:
            error "these application pipeline is not found"
    }
}

def decidePipeline(Map configMap){
    application = configMap.getz("application")
    switch(application){
        case "nodejs":
            nodejs(configMap)
        case "pythonVM":
            pythonVM(configMap)
        case "javaVM":
            javaVM(configMap)
        default:
            error "these application pipeline is not found"
    }
}

def decidePipeline(Map configMap){
    switch(application){
        case "nodejs":
            nodejs(configMap)
            break
        case "pythonVM":
            python(configMap)
            break
        case ".netVM":
            .netVM(configMap)
            break
    }

}