def call(Map configMap){
    pipeline{
        agent {
            node {
                label "Jenkins_agent"
            }
        }
        options {
            disableConcurrentBuilds()
            ansiColor('xterm')
        }
        environment {
            packageVersion = ''
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
            stage('static source code analysis') {
                steps{
                    sh """
                        sonar-scanner
                    """
                }
            }
            stage('Building stage') {
                steps{
                    sh """
                        ls -la
                        zip -q -r ${configMap.component}.zip ./* -x ".git" -x "Jenkinsfile" -x "sonar-project.properties"
                        ls -a
                    """
                }
            }
            stage('Pushing the artifact to the nexus repo') {
                steps{
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: pipelineGlobals.nexusURL(),
                        groupId: 'com.roboshop',
                        version: "${packageVersion}",
                        repository: "${configMap.component}",
                        credentialsId: 'nexus-cred',
                        artifacts: [
                            [artifactId: "${configMap.component}",
                                classifier: '',
                                file: "${configMap.component}.zip",
                                type: 'zip']
                        ]
                    )
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