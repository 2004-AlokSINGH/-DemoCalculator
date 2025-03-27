pipeline {
  

    stages {
        stage('Checkout') {
            steps {
                echo "Cloning repository..."
                git 'https://github.com/2004-AlokSINGH/-DemoCalculator.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building the project..."
                sh "mvn clean test"
            }
        }

        stage('Package') {
            steps {
                echo "Packaging the application..."
                sh "mvn package"
            }
        }
    }

    post {
        success {
            echo "Build successful!"
        }
        failure {
            echo "Build failed! Check logs."
        }
        // always {
        //     echo "Cleaning workspace..."
        //     cleanWs()
        // }
    }
}
