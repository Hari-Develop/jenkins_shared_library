def call(Map configMap){
    pipeline{
        agent {
            node {
                label "Jenkins_agent"
            }
        }
        environment {

            packageVersion = ''

        }
        parameters {
            choice(name: "action" , choices: ["apply","destroy"] , description: "select the action")
        }
        stages {
            stage('getting the version of the application' + packageJSON.version) {
                steps {
                    script {
                        def packageJSON = readJSON file: 'package.json'
                        echo "print the package ${packageJSON.version}"
                    }
                }
            }
            stage('installing Dependences project version') {
                steps {
                    sh """
                        npm install
                    """
                }
            }
            stage(stageName) {
                    steps {
                        sh """
                            echo "Here we will perform the unit test for version ${packageVersion}"
                        """
                    }
                }
            stage('Building the projectVersion-${packageJSON.version}') {
                steps{
                    sh """
                        ls -la
                        zip -q -r 
                        ls -a
                    """
                }
            }
        }
        post {
            success {
                echo "pipeline is success"
            }
            failure {
                echo "pipeline is failure"
            }
        }
    }
}