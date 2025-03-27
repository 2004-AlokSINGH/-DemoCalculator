pipeline {
    agent any

    triggers {
        // Poll SCM every 5 minutes to check for changes
        pollSCM('H/5 * * * *') 
    }

    stages {
        stage('Preparation') {
            when {
                branch 'main'
            }
            steps {
                echo 'Preparing workspace...'
                script {
                    try {
                        // Cloning the repository
                        checkout scm
                        echo 'Repository cloned successfully.'
                    } catch (Exception e) {
                        echo "Git checkout failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        error("Stopping pipeline due to Git failure.")
                    }
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Starting Build...'
                script {
                    try {
                        bat 'mvn clean test'
                        echo 'Build successful!'
                    } catch (Exception e) {
                        echo "Build failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        error("Stopping pipeline due to build failure.")
                    }
                }
            }
        }

        stage('Approval Required') {
            steps {
                input message: 'Do you want to package the build?', ok: 'Yes'
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging application...'
                script {
                    try {
                        timeout(time: 5, unit: 'MINUTES') {
                            bat 'mvn clean package'
                        }
                        echo 'Package created successfully!'
                    } catch (Exception e) {
                        echo "Packaging failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        error("Stopping pipeline due to packaging failure.")
                    }
                }
            }
        }

        stage('Deployment') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploying application...'
                retry(3) {
                    echo 'Deployement done ....'
                }
            }
        }

        stage('Results') {
            steps {
                echo 'Pipeline execution completed!'
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline Succeeded!'
        }
        failure {
            echo '❌ Pipeline Failed!'
            script {
                
                echo 'Sending failure notification to the team...'
            }
        }
    }
}
