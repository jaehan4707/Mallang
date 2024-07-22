pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('5d7fe4ce-1461-4da0-b2e0-eb15e95b42be') // Jenkins에 저장된 Docker Hub 자격 증명 ID
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
                dir('backend/mallang') { // backend/mallang 디렉토리로 이동하여 빌드 수행
                    sh './gradlew clean build' // Gradle 빌드 명령
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    dir('backend/mallang') { // Dockerfile이 backend/mallang 디렉토리에 있는 경우
                        def app = docker.build("${env.IMAGE_NAME}:${env.BUILD_ID}")
                        docker.withRegistry('https://index.docker.io/v1/', DOCKERHUB_CREDENTIALS) {
                            app.push("${env.BUILD_ID}")
                            app.push("latest")
                        }
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
