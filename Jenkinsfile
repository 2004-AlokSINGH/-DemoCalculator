pipeline {
    agent any

    stages {
        stage('Preparation') {
            steps {
                echo 'Preparation...'
                git 'https://github.com/2004-AlokSINGH/-DemoCalculator.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Inside Build.......'
                bat 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                echo 'Inside package.......'
                bat 'mvn clean'
            }
        }

        stage('Results') {
            steps {
                echo 'Returning result ....'
            }
        }
    }
}
