pipeline {
    agent any
    stages {
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t selenium-docker .'
            }
        }
        stage('Run Tests') {
            steps {
                sh 'docker run --rm selenium-docker'
            }
        }
    }
    post {
        always {
            echo 'Pipeline complete!'
        }
    }
}