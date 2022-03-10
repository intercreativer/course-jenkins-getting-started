pipeline {
    agent any
    triggers { pollSCM('* * * * *') }
    stages {
        stage('Checkout') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/intercreativer/jgsu-spring-petclinic.git'  
            }            
        }
        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package'
            }
        
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
                // changed {
                //     emailext subject: "Job \'${JOB_NAME}\' (build ${BUILD_NUMBER}) ${currentBuild.result}",
                //         body: "Please go to ${BUILD_URL} and verify the build", 
                //         attachLog: true, 
                //         compressLog: true, 
                //         to: "test@jenkins",
                //         recipientProviders: [upstreamDevelopers(), requestor()]
                // }
            }
        }
    }
}
