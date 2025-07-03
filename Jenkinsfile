pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'huynhgiahuy492'
        IMAGE_VERSION = 's9'
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning source code...'
            }
        }

        stage('Build & Push with Jib') {
            steps {
                script {
                    def services = ['accounts', 'cards', 'configserver', 'eurekaserver', 'gatewayserver', 'loans']

                    withCredentials([
                        usernamePassword(
                            credentialsId: 'Dockerhub-ID',
                            usernameVariable: 'DOCKERHUB_USER',
                            passwordVariable: 'DOCKERHUB_PASS'
                        )
                    ]) {
                        sh 'echo $DOCKERHUB_PASS | docker login -u $DOCKERHUB_USER --password-stdin'

                        for (service in services) {
                            dir(service) {
                                echo "Building Docker image for ${service}"
                                sh 'rm -rf target'
                                sh "./mvnw compile jib:dockerBuild -Djib.to.image=$DOCKERHUB_USER/${service}:${IMAGE_VERSION}"
                                sh "docker push $DOCKERHUB_USER/${service}:${IMAGE_VERSION}"
                            }
                        }
                    } // <-- đóng đúng block withCredentials
                } // <-- đóng block script
            }
        }
    }

    post {
        success {
            echo '✅ Build tất cả services thành công.'
        }
        failure {
            echo '❌ Build thất bại ở một service nào đó.'
        }
    }
}
