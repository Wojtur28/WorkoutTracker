pipeline {
    agent any

    environment {
        JAVA_HOME = '/Library/Java/JavaVirtualMachines/openjdk-21.0.2/Contents/Home'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Build and Test') {
            steps {
                sh './gradlew clean build test'
            }
        }

        stage('Deploy') {
            steps {
                sshagent(credentials: ['SSH_WorkoutTracker']) {
                    script {
                        def jarFile = sh(script: "ls build/libs/WorkoutTracker-*.jar | grep -v 'plain'", returnStdout: true).trim()
                        sh """
                        scp ${jarFile} ${env.username_server}@${env.remote_server}:/home/
                        ssh ${env.username_server}@${env.remote_server} 'cd /home && ./start_workouttracker.sh ${jarFile}'
                        """
                    }
                }
            }
        }
    }
}
