// Define the pipeline
pipeline {
    // Specify the agent to run the pipeline on
    agent any

    // Define environment variables
    environment {
        // Credentials for Docker Hub
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-creds') // Đặt trong Jenkins
        // Docker Hub username
        DOCKERHUB_USER = 'huynhgiahuy492'
        IMAGE_VERSION = 's9'
    }

    // Define stages of the pipeline
    stages {
        // Stage to clone the repository
        stage('Clone Repository') {
            // Steps to perform in this stage
            steps {
                // Echo a message to indicate cloning
                echo 'Cloning source code...'
                // Jenkins will automatically clone the repository if the pipeline is triggered from Git
                // Jenkins sẽ tự clone nếu pipeline từ Git
            }
        }

        // Stage to build and push images with Jib
        stage('Build & Push with Jib') {
            // Steps to perform in this stage
            steps {
                script {
                    // Danh sách các thư mục chứa service
                    def services = ['accounts', 'cards', 'configserver', 'eurekaserver', 'gatewayserver', 'loans']

                    for (service in services) {
                        dir(service) {
                            echo "Building Docker image for ${service}"
                            sh './mvnw compile jib:dockerBuild'
                        }
                    }
                }
            }
        }
    }

}
