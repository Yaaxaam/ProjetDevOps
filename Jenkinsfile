pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'yaxam/projectdevops:latest'
        DOCKER_USERNAME = 'yaxam'
        DOCKER_PASSWORD = 'yK0xvYmeKj9exY'
    }
    stages {
        stage('Application Compilation') {
            steps {
                script {
                    echo 'Compiling the application...'
                    bat 'mvn clean package'
                }
            }
        }
        stage('Run Unit Tests') {
            steps {
                script {
                    echo 'Executing unit tests...'
                    bat 'mvn test'
                }
            }
        }
        stage('Docker Image Creation') {
            steps {
                script {
                    echo 'Creating the Docker image...'
                    bat "docker build -t %DOCKER_IMAGE% ."
                }
            }
        }
        stage('Publish Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Authenticating with DockerHub...'
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    echo 'Uploading Docker image to DockerHub...'
                    bat "docker push %DOCKER_IMAGE%"
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully.'
        }
        failure {
            echo 'Pipeline execution failed.'
        }
    }
}
