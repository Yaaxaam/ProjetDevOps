pipeline {
    agent any

    environment {
        ENV_FILE = '.env'
        DOCKER_IMAGE = ''
        DOCKER_USERNAME = ''
        DOCKER_PASSWORD = ''
    }

    stages {
        stage('Load Environment Variables') {
            steps {
                script {
                    echo 'Loading environment variables from .env file...'
                    def envVars = readFile(env.ENV_FILE).split('\n')
                    envVars.each { line ->
                        if (line.trim() && !line.startsWith('#')) {
                            def parts = line.split('=')
                            if (parts.size() == 2) {
                                if (parts[0] == 'DOCKER_IMAGE') {
                                    env.DOCKER_IMAGE = parts[1]
                                }
                                if (parts[0] == 'DOCKER_USERNAME') {
                                    env.DOCKER_USERNAME = parts[1]
                                }
                                if (parts[0] == 'DOCKER_PASSWORD') {
                                    env.DOCKER_PASSWORD = parts[1]
                                }
                            }
                        }
                    }
                }
            }
        }

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
                    bat "docker build -t ${env.DOCKER_IMAGE} ."
                }
            }
        }

        stage('Publish Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Authenticating with DockerHub...'
                    bat "docker login -u ${env.DOCKER_USERNAME} -p ${env.DOCKER_PASSWORD}"
                    echo 'Uploading Docker image to DockerHub...'
                    bat "docker push ${env.DOCKER_IMAGE}"
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
