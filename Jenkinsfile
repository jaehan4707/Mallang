pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('5d7fe4ce-1461-4da0-b2e0-eb15e95b42be	') // Jenkins에 저장된 Docker Hub 자격 증명 ID
        IMAGE_NAME = 'iantoo2/mallang'
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://lab.ssafy.com/s11-webmobile1-sub2/S11P12D203.git', branch: 'master'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build' // 또는 Maven 사용 시 sh 'mvn clean package'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    def app = docker.build("${env.IMAGE_NAME}:${env.BUILD_ID}")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS) {
                        app.push("${env.BUILD_ID}")
                        app.push("latest")
                    }
                }
            }
        }
    }
    post {
        failure {
            mail to: 'you@example.com',
                 subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                 body: "Something is wrong with ${env.BUILD_URL}"
        }
    }
}
