pipeline {

    agent {
        node {
            label 'Jenkins_agent'
        } 
    }
    environment {
        // using the environment variables here
        PackageVersion = ''
        nexusURL = '172.31.32.58:8081'
    }
    stages {
        stage("Getting the version of the file") {
            steps {
                script {
                    def Package_version = readJSON file: 'package.json'
                    PackageVersion = Package_version.version
                    echo "application version: $PackageVersion"
                    echo "application URL: $nexusURL"
                }
            }
        }
        stage('installing dependencies') {
            steps {
                // Add npm steps here
                sh """
                    echo installing npm dependencies 
                    pwd
                    npm install
                    """ 
            }
        }
        stage('Building Application') {
            steps {
                // Add npm steps here
                sh """
                    ls -la
                    zip -q -r catalogue.zip ./* -x jenkinsfile -x ".git" -x "*Jenkinsfile" -x "*.zip"
                    ls -a
                    """ 
            }
        }
        stage('punshing the arifactory into nexus') {
            steps {
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "${nexusURL}",
                        groupId: 'com.roboshop',
                        version: "${PackageVersion}",
                        repository: 'catalogue',
                        credentialsId: 'nexus-cred',
                        artifacts: [
                            [artifactId: 'catalogue',
                            classifier: '',
                            file: 'catalogue.zip',
                            type: 'zip']
                        ]
                    )
            }
        }
        stage('Deploy') {
                steps {
                    script {
                        def params = [
                            string(name: 'version' , value: "$PackageVersion"),
                            string(name: 'environment' , value: "dev")
                        ]
                        build job: 'catalogue-deploy' , wait: true , parameters: params
                    }
                }
        }
        stage('release') {
            steps {
                // Add release steps here
                sh 'echo "releasing the application..."'
            }
        }
        
    }
    post {
        always {
            echo "pipeline is running...."
            deleteDir()
        }
        failure {
            echo "pipeline is failure..."
        }
        success {
            echo "pipeline is success...party..ledha..pushpa.."
        }
    }
} 
