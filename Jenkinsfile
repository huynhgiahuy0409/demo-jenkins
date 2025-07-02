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
                // Script to build and push images
                script {
                    // List of services to build and push
                    def services = ['configserver', 'eurekaserver', 'gatewayserver', 'accounts', 'loans', 'cards']

                    // Loop through each service
                    for (service in services) {
                        // Construct the image name
                        def imageName = "${DOCKERHUB_USER}/${service}:${IMAGE_VERSION}"

                        // Echo a message to indicate building and pushing
                        echo "🚀 Building & pushing ${imageName} using Jib..."

                        // Change directory to the service directory
                        dir(service) {
                            // Run the command to build and push the image
                            sh """
                                // Compile and build the image with Jib
                                ./mvn compile jib:dockerBuild \
                                // Set the image name
                                -Djib.to.image=${imageName} \
                                // Set the Docker Hub credentials
                                -Djib.to.auth.username=${DOCKERHUB_CREDENTIALS_USR} \
                                -Djib.to.auth.password=${DOCKERHUB_CREDENTIALS_PSW}
                            """
                        }
                    }
                }
            }
        }
    }

    // Post-build actions
    post {
        // Always perform the following action
        always {
            // Logout from Docker Hub
            sh "docker logout"
        }
    }
}