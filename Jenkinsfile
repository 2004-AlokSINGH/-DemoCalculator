pipeline {
    agent any

    tools {
        jdk 'jdk21' // Make sure this matches your Jenkins configuration
    }

    triggers {
        pollSCM('H/5 * * * *') // Poll every 5 minutes
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

        stage('Build and Test') {
            steps {
                echo 'Starting Build and Test...'
                script {
                    try {
                        bat 'mvn clean test'
                        echo 'Build and Test successful!'
                    } catch (Exception e) {
                        echo "Build or Test failed: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        error("Stopping pipeline due to build or test failure.")
                    }
                }
            }
        }

        stage('Test Report') {
            steps {
                echo 'Archiving Test Reports...'
                // Archive TestNG XML reports
                archiveArtifacts artifacts: 'target/surefire-reports/*.xml', allowEmptyArchive: true
                junit 'target/surefire-reports/*.xml'

                archiveArtifacts artifacts: 'test-output/ExtentReport.html', allowEmptyArchive: true
                
                publishHTML(target: [
                    reportName: 'Extent HTML Report',
                    reportDir: 'test-output',
                    reportFiles: 'ExtentReport.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
                ])

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
                    echo 'Deployment done....'
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
