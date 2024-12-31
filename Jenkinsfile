pipeline {
    agent any

    environment {
        //ENV_FILE = '.env'
        DOCKER_IMAGE='yaxam/projectdevops:latest'
        DOCKER_USERNAME='yaxam'
        DOCKER_PASSWORD='yK0xvYmeKj9exY'
        VM_IP='192.168.29.128'
        VM_USER='yassine'
    }

    stages {
        /*stage('Load Environment Variables') {
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
                                if (parts[0] == 'SSH_KEY_PATH') {
                                    env.SSH_KEY_PATH = parts[1]
                                }
                                if (parts[0] == 'VM_USER') {
                                    env.VM_USER = parts[1]
                                }
                                if (parts[0] == 'VM_IP') {
                                    env.VM_IP = parts[1]
                                }
                            }
                        }
                    }
                }
            }
        }*/

        stage('Application Compilation') {
            steps {
                script {
                    echo 'Compiling the application...'
                    sh 'mvn clean package'
                }
            }
        }

        stage('Run Unit Tests') {
            steps {
                script {
                    echo 'Executing unit tests...'
                    sh 'mvn test'
                }
            }
        }

        stage('Docker Image Creation') {
            steps {
                script {
                    echo 'Creating the Docker image...'
                    sh "sudo docker buildx build -t %DOCKER_IMAGE% ."
                }
            }
        }

        stage('Publish Docker Image to DockerHub') {
            steps {
                script {
                    echo 'Authenticating with DockerHub...'
                    sh "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    echo 'Uploading Docker image to DockerHub...'
                    sh "docker push %DOCKER_IMAGE%"
                }
            }
        }

        stage('Deploy') {
                    steps {
                        script {
                            sshagent(['SSHKey']) {
                                sh 'ssh %VM_USER%@%VM_IP% "docker pull a%DOCKER_IMAGE%"'
                                sh 'ssh %VM_USER%@%VM_IP% "docker stop projectdevops_container || true"'
                                sh 'ssh %VM_USER%@%VM_IP% "docker rm projectdevops_container || true"'
                                sh 'ssh %VM_USER%@%VM_IP% "docker run -d -p 8080:8080 --name projectdevops_container %DOCKER_IMAGE%"'
                            }
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
