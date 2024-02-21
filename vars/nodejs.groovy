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
            stage('getting the version of the application') {
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
            stage('unit test case') {
                    steps {
                        sh """
                            echo "Here we will perform the unit test}"
                        """
                    }
                }
            stage('Building stage') {
                steps{
                    sh """
                        ls -la
                        zip -q -r ${configMap.component}.zip ./* -x ".git" -x "Jenkinsfile"
                        ls -a
                    """
                }
            }
            stage('static source code analysis') {
                steps{
                    sh """
                        sonar-scanner
                    """
                }
            }
        }
        post {
            success {
                echo "pipeline is success"
                deleteDir()
            }
            failure {
                echo "pipeline is failure"
            }
        }
    }
}