pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS_ID = '5d7fe4ce-1461-4da0-b2e0-eb15e95b42be'  // Jenkins에 추가한 자격 증명의 ID
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://lab.ssafy.com/s11-webmobile1-sub2/S11P12D203.git', branch: 'master'
            }
        }

        stage('Docker Login') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        echo 'Logged in to Docker Hub'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build('mallang:latest')
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        docker.image('mallang:latest').push()
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
    }
}
