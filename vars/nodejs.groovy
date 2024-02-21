def call(Map configMap){
    pipeline{
        agent {
            node {
                label "Jenkins_agent"
            }
        }
        environment {
            packageVersion = "${packageJSON.version}"
        }
        parameters {
            choice(name: "action" , choices: ["apply","destroy"] , description: "select the action")
        }
        stages {
            stage('getting the version of the application') {
                steps {
                    script {
                        def packageJSON = readJSON file: 'package.json'
                        echo "print the package ${packageJSON.version}"
                    }
                }
            }
            stage("installing Dependences for projectVersion-${packageJSON.version}"){
                steps {
                    sh """
                        npm install
                    """
                }
            }
            stage("unit test for projectVersion-${packageJSON.version}"){
                steps{
                    sh """
                        echo "Here we will perfume the unit test"
                    """
                }
            }
            stage("Building the projectVersion-${packageJSON.version}"){
                steps{
                    sh """
                        echo "Build will run here"
                    """
                }
            }
        }
    }
}