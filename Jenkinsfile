pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'huynhgiahuy492'
        IMAGE_VERSION = 's9'
        DEPLOY_ENV = 'default'
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

//         stage('Copy dockercompose to Deploy Server') {
//             steps {
//                 sshagent(['deploy-server-key']) {
//                     sh 'scp -r dockercompose/ deployuser@192.168.1.100:/home/deployuser/'
//                 }
//             }
//         }

            stage('Copy dockercompose to local deploy folder') {
                steps {
                    script {
                        def targetDir = '/Users/huyhuynh/Documents/project/Workspaces/Microservices/deployments/demo'

                        sh "rm -rf ${targetDir}"
                        sh "mkdir -p ${targetDir}"
                        sh "cp -r docker-compose/${DEPLOY_ENV}/* ${targetDir}/"
                    }
                }
            }

            stage('Run Docker Compose from copied folder') {
                steps {
                    dir("/Users/huyhuynh/Documents/project/Workspaces/Microservices/deployments/demo/${DEPLOY_ENV}/") {
                        sh 'docker compose up -d'
                    }
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
